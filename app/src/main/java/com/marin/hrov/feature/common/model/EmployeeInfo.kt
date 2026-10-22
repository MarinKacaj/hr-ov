package com.marin.hrov.feature.common.model

import androidx.compose.runtime.Immutable
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import java.math.BigDecimal

@Immutable
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
    val subscriptions: List<Subscription>
)
