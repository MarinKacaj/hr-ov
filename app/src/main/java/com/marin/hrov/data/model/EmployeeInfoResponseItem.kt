package com.marin.hrov.data.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

// could be called NetworkEmployeeInfo as well...
// just opting for a more general name since our network is fake
@Serializable
data class EmployeeInfoResponseItem(
    val employeeId: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val avatarUrl: String,
    val jobTitle: String,
    val department: String,
    val office: String,
    val managerId: String,
    val managerName: String,
    val employmentStartDate: LocalDate,
    val employmentEndDate: LocalDate?,
    val subscriptionId: String?,
    val subscriptionType: String?,
    val subscriptionTypeLabel: String?,
    val provider: String?,
    val subscriptionStatus: String?,
    val cardNumber: String?,
    val travelClass: String?,
    val zones: List<Int>,
    val startDate: LocalDate?,
    val endDate: LocalDate?,
    val autoRenew: Boolean?,
    val costPerMonth: Double?,
    val currency: String?,
    val costCenter: String?,
    val lastUsedAt: LocalDateTime?,
    val daysUntilExpiry: Int?,
    val notes: String?

)