package com.marin.hrov.feature.employeeList.domain.model

import com.marin.hrov.feature.common.model.EmployeeInfo
import com.marin.hrov.feature.common.model.EmploymentStatus
import com.marin.hrov.feature.common.model.SubscriptionStatus
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

fun EmployeeInfo.extractPublicTransportSituation(clock: Clock): EmployeePublicTransportSituation {
    val now = clock.now() // could also take an instant as an argument instead... no real pref here
    val areAllSubscriptionsInOrder = subscriptions.all { subscription ->
        val hasNoSubscription = subscription.subscriptionStatus == null
        val hasActiveSubscription =
            subscription.subscriptionStatus == SubscriptionStatus.ACTIVE
        val isSubscriptionNotExpired = if (subscription.endDate != null) {
            subscription.endDate >= now.toLocalDateTime(TimeZone.currentSystemDefault()).date
        } else true
        val isInOrder = (hasNoSubscription
                || hasActiveSubscription && isSubscriptionNotExpired
                || hasActiveSubscription && subscription.autoRenew == true)
                && !(employmentStatus == EmploymentStatus.OFFBOARDED && hasActiveSubscription)
        isInOrder
    }

    return EmployeePublicTransportSituation(
        employeeId = employeeId,
        firstName = firstName,
        lastName = lastName,
        avatarUrl = avatarUrl,
        jobTitle = jobTitle,
        employmentStatus = employmentStatus,
        areAllSubscriptionsInOrder = areAllSubscriptionsInOrder
    )
}