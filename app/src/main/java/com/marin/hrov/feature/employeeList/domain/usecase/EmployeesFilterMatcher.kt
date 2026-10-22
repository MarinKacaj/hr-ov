package com.marin.hrov.feature.employeeList.domain.usecase

import com.marin.hrov.feature.employeeList.domain.model.EmployeePublicTransportSituation

fun EmployeePublicTransportSituation.matchesFilter(filter: Filter): Boolean {
    val anyNameMatches = if (filter.anyName != null) {
        firstName.contains(other = filter.anyName, ignoreCase = true)
                || lastName.contains(other = filter.anyName, ignoreCase = true)
    } else true
    val employmentStatusMatches = if (filter.employmentStatus != null) {
        employmentStatus == filter.employmentStatus
    } else true
    val isInOrder = filter.isTransportInOrder ?: true
    return anyNameMatches && employmentStatusMatches && isInOrder
}