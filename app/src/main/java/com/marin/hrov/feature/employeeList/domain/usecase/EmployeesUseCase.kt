package com.marin.hrov.feature.employeeList.domain.usecase

import com.marin.hrov.feature.employeeList.domain.model.EmployeePublicTransportSituation
import com.marin.hrov.data.model.EmploymentStatusResponse

fun interface EmployeesUseCase {
    operator fun invoke(filter: Filter): List<EmployeePublicTransportSituation>
}

data class Filter(
    val anyName: String? = null,
    val employmentStatus: EmploymentStatusResponse? = null,
    val isTransportInOrder: Boolean? = null
)