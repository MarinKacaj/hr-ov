package com.marin.hrov.feature.common.model

import com.marin.hrov.data.model.EmployeeInfoResponseItem
import com.marin.hrov.data.model.EmploymentStatusResponse
import com.marin.hrov.data.model.SubscriptionStatusResponse

fun List<EmployeeInfoResponseItem>.toDomainModels(): List<EmployeeInfo> {
    val employeeInfoById: Map<String, List<EmployeeInfoResponseItem>> =
        this.groupBy { info -> info.employeeId }
    val subscriptionsById = employeeInfoById.map { entry ->
        val employeeInfoResponseItems = entry.value
        entry.key to employeeInfoResponseItems.map { employeeInfoResponseItem ->
            Subscription(
                subscriptionId = employeeInfoResponseItem.subscriptionId,
                subscriptionType = employeeInfoResponseItem.subscriptionType,
                subscriptionTypeLabel = employeeInfoResponseItem.subscriptionTypeLabel,
                provider = employeeInfoResponseItem.provider,
                subscriptionStatus = employeeInfoResponseItem.subscriptionStatus.toDomainModel(),
                cardNumber = employeeInfoResponseItem.cardNumber,
                travelClass = employeeInfoResponseItem.travelClass,
                zones = employeeInfoResponseItem.zones,
                startDate = employeeInfoResponseItem.startDate,
                endDate = employeeInfoResponseItem.endDate,
                autoRenew = employeeInfoResponseItem.autoRenew,
                costPerMonth = employeeInfoResponseItem.costPerMonth?.toBigDecimal(),
                currency = employeeInfoResponseItem.currency,
                costCenter = employeeInfoResponseItem.costCenter,
                lastUsedAt = employeeInfoResponseItem.lastUsedAt,
                daysUntilExpiry = employeeInfoResponseItem.daysUntilExpiry,
                notes = employeeInfoResponseItem.notes
            )
        }
    }.toMap()
    return employeeInfoById.map { (id: String, employeeInfo: List<EmployeeInfoResponseItem>) ->
        // deconfliction strategy: first come, first served
        // some error handling and at least logging would be needed in a prod app
        employeeInfo.first().toDomainModel(
            subscriptions = subscriptionsById.getOrDefault(
                key = id,
                defaultValue = emptyList()
            )
        )
    }
}
fun EmployeeInfoResponseItem.toDomainModel(subscriptions: List<Subscription>) = EmployeeInfo(
    employeeId = employeeId,
    firstName = firstName,
    lastName = lastName,
    email = email,
    phone = phone,
    avatarUrl = avatarUrl,
    jobTitle = jobTitle,
    department = department,
    office = office,
    managerId = managerId,
    managerName = managerName,
    employmentStatus = this@toDomainModel.employmentStatus.toDomainModel(),
    employmentStartDate = employmentStartDate,
    employmentEndDate = employmentEndDate,
    subscriptions = subscriptions
)

fun EmploymentStatusResponse.toDomainModel(): EmploymentStatus = when (this) {
    EmploymentStatusResponse.ACTIVE -> EmploymentStatus.ACTIVE
    EmploymentStatusResponse.OFFBOARDED -> EmploymentStatus.OFFBOARDED
}

fun SubscriptionStatusResponse?.toDomainModel(): SubscriptionStatus? = when (this) {
    SubscriptionStatusResponse.ACTIVE -> SubscriptionStatus.ACTIVE
    SubscriptionStatusResponse.CANCELLED -> SubscriptionStatus.CANCELLED
    SubscriptionStatusResponse.PENDING -> SubscriptionStatus.PENDING
    SubscriptionStatusResponse.EXPIRED -> SubscriptionStatus.EXPIRED
    null -> null
}