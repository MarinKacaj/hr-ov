package com.marin.hrov.ui.nav

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object EmployeeList : NavKey

@Serializable
data object Renewals : NavKey

@Serializable
data class EmployeeDetails(val employeeId: String) : NavKey