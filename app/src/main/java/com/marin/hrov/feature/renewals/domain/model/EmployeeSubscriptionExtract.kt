package com.marin.hrov.feature.renewals.domain.model

import androidx.compose.runtime.Immutable
import com.marin.hrov.feature.common.model.SubscriptionStatus
import kotlinx.datetime.LocalDate

@Immutable
data class EmployeeSubscriptionExtract(
    val subscriptionId: String?,
    val subscriptionTypeLabel: String?,
    val employeeFullName: String,
    val employeeId: String,
    val endDate: LocalDate?,
    val subscriptionStatus: SubscriptionStatus?,
    val autoRenew: Boolean?
)
