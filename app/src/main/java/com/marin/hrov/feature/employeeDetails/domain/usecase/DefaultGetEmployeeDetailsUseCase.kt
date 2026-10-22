package com.marin.hrov.feature.employeeDetails.domain.usecase

import com.marin.hrov.core.OVResult
import com.marin.hrov.feature.common.model.EmployeeInfo
import com.marin.hrov.feature.common.repository.EmployeeInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultGetEmployeeDetailsUseCase(
    private val employeesInfoRepository: EmployeeInfoRepository
) : GetEmployeeDetailsUseCase {

    override fun invoke(employeeId: String): Flow<OVResult<EmployeeInfo>> =
        employeesInfoRepository.employees().map { employeeInfoResult ->
            when (employeeInfoResult) {
                is OVResult.Error -> {
                    OVResult.Error(employeeInfoResult.exception)
                }

                OVResult.Loading -> {
                    OVResult.Loading
                }

                is OVResult.Success -> {
                    val employeeInfo =
                        employeeInfoResult.data.firstOrNull { it.employeeId == employeeId }
                    if (employeeInfo == null) {
                        OVResult.Error(exception = IllegalArgumentException("No employee with id $employeeId"))
                    } else {
                        OVResult.Success(data = employeeInfo)
                    }
                }
            }
        }
}