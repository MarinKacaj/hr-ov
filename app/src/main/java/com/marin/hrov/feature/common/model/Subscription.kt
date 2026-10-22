package com.marin.hrov.feature.common.model

import androidx.compose.runtime.Immutable
import kotlinx.datetime.LocalDate
import java.math.BigDecimal

@Immutable
data class Subscription(
    val subscriptionId: String?,
    val subscriptionType: String?,
    val subscriptionTypeLabel: String?,
    val provider: String?,
    val subscriptionStatus: SubscriptionStatus?,
    val cardNumber: String?,
    val travelClass: String?,
    val zones: List<Int>?,
    val startDate: LocalDate?,
    val endDate: LocalDate?,
    val autoRenew: Boolean?,
    // since we'll perform addition to find the total, costPerMonth is a trusty BigDecimal here
    val costPerMonth: BigDecimal?,
    val currency: String?,
    val costCenter: String?,
    val lastUsedAt: String?,
    val daysUntilExpiry: Int?,
    val notes: String?
)