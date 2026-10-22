package com.marin.hrov.feature.renewals.domain.usecase

import com.marin.hrov.core.OVResult
import com.marin.hrov.feature.common.model.SubscriptionStatus
import com.marin.hrov.feature.common.repository.EmployeesInfoRepository
import com.marin.hrov.feature.renewals.domain.model.BucketedExpiryEmployeeSubscriptions
import com.marin.hrov.feature.renewals.domain.model.EmployeeSubscriptionExtract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

class DefaultGetRenewalsUseCase(
    private val employeesInfoRepository: EmployeesInfoRepository,
    private val clock: Clock
) : GetRenewalsUseCase {

    override fun invoke(): Flow<OVResult<BucketedExpiryEmployeeSubscriptions>> =
        employeesInfoRepository.employees().map { employeeInfoResult ->
            val now = clock.now()
            when (employeeInfoResult) {
                is OVResult.Error -> {
                    OVResult.Error(employeeInfoResult.exception)
                }

                OVResult.Loading -> {
                    OVResult.Loading
                }

                is OVResult.Success -> {
                    val employeeInfoEntries = employeeInfoResult.data
                    val extracts = employeeInfoEntries.flatMap { employeeInfo ->
                        employeeInfo.subscriptions.map { subscription ->
                            EmployeeSubscriptionExtract(
                                subscriptionId = subscription.subscriptionId,
                                subscriptionTypeLabel = subscription.subscriptionTypeLabel,
                                endDate = subscription.endDate,
                                subscriptionStatus = subscription.subscriptionStatus,
                                employeeId = employeeInfo.employeeId,
                                employeeFullName = "${employeeInfo.firstName} ${employeeInfo.lastName}",
                                autoRenew = subscription.autoRenew
                            )
                        }
                    }
                    val thisWeek = now.toLocalDateTime(
                        TimeZone.currentSystemDefault()
                    ).date.plus(value = 1, unit = DateTimeUnit.WEEK)
                    val thisMonth = now.toLocalDateTime(
                        TimeZone.currentSystemDefault()
                    ).date.plus(value = 1, unit = DateTimeUnit.MONTH)
                    val expired =
                        extracts.filter { it.subscriptionStatus == SubscriptionStatus.EXPIRED }
                    val expiresThisWeek = extracts.filter {
                        it.autoRenew != true && it.endDate != null && it.endDate < thisWeek
                    }.filter { it.subscriptionStatus != SubscriptionStatus.EXPIRED }
                    val expiresThisMonth = extracts.filter {
                        it.autoRenew != true && it.endDate != null && it.endDate < thisMonth && it.endDate > thisWeek
                    }
                    OVResult.Success(
                        data = BucketedExpiryEmployeeSubscriptions(
                            expired = expired,
                            expiresThisWeek = expiresThisWeek,
                            expiresThisMonth = expiresThisMonth
                        )
                    )
                }
            }
        }

}