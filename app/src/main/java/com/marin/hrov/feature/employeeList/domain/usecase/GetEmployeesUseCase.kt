package com.marin.hrov.feature.employeeList.domain.usecase

import com.marin.hrov.core.OVResult
import com.marin.hrov.feature.common.model.EmploymentStatus
import com.marin.hrov.feature.employeeList.domain.model.EmployeePublicTransportSituation
import kotlinx.coroutines.flow.Flow

fun interface GetEmployeesUseCase {
    operator fun invoke(filter: Filter): Flow<OVResult<List<EmployeePublicTransportSituation>>>
}

data class Filter(
    val anyName: String? = null,
    val employmentStatus: EmploymentStatus? = null,
    val isTransportInOrder: Boolean? = null
) {
    fun isAnyFilterActive() =
        anyName != null || employmentStatus != null || isTransportInOrder != null
}