package com.marin.hrov.feature.employeeList.domain.usecase

import com.marin.hrov.core.OVResult
import com.marin.hrov.feature.common.repository.EmployeesInfoRepository
import com.marin.hrov.feature.employeeList.domain.model.EmployeePublicTransportSituation
import com.marin.hrov.feature.employeeList.domain.model.extractPublicTransportSituation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.time.Clock

class DefaultGetEmployeesUseCase(
    private val employeesInfoRepository: EmployeesInfoRepository,
    private val clock: Clock
) : GetEmployeesUseCase {

    override fun invoke(filter: Filter): Flow<OVResult<List<EmployeePublicTransportSituation>>> =
        employeesInfoRepository.employees().map { employeeInfoResult ->
            when (employeeInfoResult) {
                is OVResult.Error -> OVResult.Error(employeeInfoResult.exception)
                OVResult.Loading -> OVResult.Loading
                is OVResult.Success -> OVResult.Success(
                    data = employeeInfoResult.data.map { employeeInfo ->
                        employeeInfo.extractPublicTransportSituation(clock)
                    }.filter { employeeInfo ->
                        employeeInfo.matchesFilter(filter)
                    }
                )
            }
        }
}
