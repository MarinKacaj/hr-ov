package com.marin.hrov.feature.employeeDetails.domain.usecase

import com.marin.hrov.core.OVResult
import com.marin.hrov.feature.common.model.EmployeeInfo
import kotlinx.coroutines.flow.Flow

fun interface GetEmployeeDetailsUseCase {
    operator fun invoke(employeeId: String): Flow<OVResult<EmployeeInfo>>
}