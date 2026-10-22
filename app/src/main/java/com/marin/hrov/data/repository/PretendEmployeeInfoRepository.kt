package com.marin.hrov.data.repository

import com.marin.hrov.core.OVResult
import com.marin.hrov.data.network.EmployeesInfoVirtualApi
import com.marin.hrov.feature.common.model.EmployeeInfo
import com.marin.hrov.feature.common.model.toDomainModels
import com.marin.hrov.feature.common.repository.EmployeeInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PretendEmployeeInfoRepository(
    private val employeesInfoVirtualApi: EmployeesInfoVirtualApi
) : EmployeeInfoRepository {
    override fun employees(): Flow<OVResult<List<EmployeeInfo>>> = flow {
        emit(OVResult.Loading)
        val employeeInfoResult = employeesInfoVirtualApi.employeeInfoItems()
        val outcome: OVResult<List<EmployeeInfo>> = when (employeeInfoResult) {
            is OVResult.Error -> {
                OVResult.Error(employeeInfoResult.exception)
            }

            OVResult.Loading -> {
                OVResult.Loading
            }

            is OVResult.Success -> {
                OVResult.Success(
                    data = employeeInfoResult.data.toDomainModels()
                )
            }
        }
        emit(outcome)
    }
}