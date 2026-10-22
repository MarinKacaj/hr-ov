package com.marin.hrov.feature.common.presentation

import com.marin.hrov.feature.common.model.EmploymentStatus

fun EmploymentStatus?.description() = when (this) {
    EmploymentStatus.ACTIVE -> "Active employee"
    EmploymentStatus.OFFBOARDED -> "No longer with us"
    null -> "Unknown employment status"
}