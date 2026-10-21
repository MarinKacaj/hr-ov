package com.marin.hrov.feature.employeeList.domain.model

import com.marin.hrov.data.model.EmploymentStatusResponse
import com.marin.hrov.data.model.SubscriptionStatusResponse
import kotlinx.datetime.LocalDate

data class EmployeePublicTransportSituation(
    val employeeId: String,
    val firstName: String,
    val lastName: String,
    val avatarUrl: String,
    val jobTitle: String,
    val employmentStatus: EmploymentStatusResponse,
    val endDate: LocalDate?,
    val autoRenew: Boolean?,
    val subscriptionStatus: SubscriptionStatusResponse?
)