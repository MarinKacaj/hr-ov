package com.marin.hrov.feature.renewals.domain.model

data class BucketedExpiryEmployeeSubscriptions(
    val expired: List<EmployeeSubscriptionExtract>,
    val expiresThisWeek: List<EmployeeSubscriptionExtract>,
    val expiresThisMonth: List<EmployeeSubscriptionExtract>
)
