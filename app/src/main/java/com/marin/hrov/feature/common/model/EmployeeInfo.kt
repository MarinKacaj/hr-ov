package com.marin.hrov.feature.common.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import java.math.BigDecimal

data class EmployeeInfo(
    val employeeId: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val avatarUrl: String,
    val jobTitle: String,
    val department: String,
    val office: String,
    val managerId: String?,
    val managerName: String?,
    val employmentStatus: EmploymentStatus,
    val employmentStartDate: LocalDate,
    val employmentEndDate: LocalDate?,
    val subscriptionId: String?,
    val subscriptionType: String?,
    val subscriptionTypeLabel: String?,
    val provider: String?,
    val subscriptionStatus: SubscriptionStatus?,
    val cardNumber: String?,
    val travelClass: String?,
    val zones: List<Int>,
    val startDate: LocalDate?,
    val endDate: LocalDate?,
    val autoRenew: Boolean?,
    // since we'll perform addition to find the total, costPerMonth is a trusty BigDecimal here
    val costPerMonth: BigDecimal?,
    val currency: String?,
    val costCenter: String?,
    val lastUsedAt: LocalDateTime?,
    val daysUntilExpiry: Int?,
    val notes: String?
)
