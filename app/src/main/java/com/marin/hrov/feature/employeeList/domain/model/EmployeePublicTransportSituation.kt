package com.marin.hrov.feature.employeeList.domain.model

import com.marin.hrov.feature.common.model.EmploymentStatus
import java.math.BigDecimal

// employee with aggregated subscription orderly state
data class EmployeePublicTransportSituation(
    val employeeId: String,
    val firstName: String,
    val lastName: String,
    val avatarUrl: String,
    val jobTitle: String,
    val employmentStatus: EmploymentStatus,
    val areAllSubscriptionsInOrder: Boolean,
    val subscriptionsMonthlyTotal: BigDecimal,
    val currency: String
)