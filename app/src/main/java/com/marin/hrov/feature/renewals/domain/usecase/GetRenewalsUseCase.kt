package com.marin.hrov.feature.renewals.domain.usecase

import com.marin.hrov.core.OVResult
import com.marin.hrov.feature.renewals.domain.model.BucketedExpiryEmployeeSubscriptions
import kotlinx.coroutines.flow.Flow

fun interface GetRenewalsUseCase {
    operator fun invoke(): Flow<OVResult<BucketedExpiryEmployeeSubscriptions>>
}