package com.marin.hrov.feature.common.repository

import com.marin.hrov.core.ActionResult
import com.marin.hrov.feature.common.model.EmployeeInfo

interface EmployeeInfoRepository { // not a fun interface: perhaps we add more methods here
    fun employees(): ActionResult<List<EmployeeInfo>>
}