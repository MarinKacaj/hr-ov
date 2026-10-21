package com.marin.hrov.data.repository

import com.marin.hrov.core.ActionResult
import com.marin.hrov.feature.common.model.EmployeeInfo
import com.marin.hrov.feature.common.repository.EmployeeInfoRepository

class PretendEmployeeInfoRepository : EmployeeInfoRepository {
    override fun employees(): ActionResult<List<EmployeeInfo>> {
        return ActionResult.Loading // todo real *ahem* pretend stuff
    }
}