package com.marin.hrov.feature.common.repository

import com.marin.hrov.core.OVResult
import com.marin.hrov.feature.common.model.EmployeeInfo
import kotlinx.coroutines.flow.Flow

interface EmployeesInfoRepository { // not a fun interface: perhaps we add more methods here
    fun employees(): Flow<OVResult<List<EmployeeInfo>>>
}