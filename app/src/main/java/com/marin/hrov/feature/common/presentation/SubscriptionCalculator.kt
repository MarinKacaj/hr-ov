package com.marin.hrov.feature.common.presentation

import com.marin.hrov.feature.common.model.EmployeeInfo
import com.marin.hrov.feature.common.model.SubscriptionStatus
import java.math.BigDecimal

fun EmployeeInfo.ongoingSubscriptionsTotalMonthly() =
    subscriptions.filter {
        it.costPerMonth != null && it.subscriptionStatus != SubscriptionStatus.EXPIRED && it.subscriptionStatus != SubscriptionStatus.CANCELLED
    }
        .fold(initial = BigDecimal.ZERO) { acc, curr ->
            acc + curr.costPerMonth!!
        }