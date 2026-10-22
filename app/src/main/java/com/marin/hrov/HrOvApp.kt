package com.marin.hrov

import android.app.Application
import com.marin.hrov.data.network.EmployeesInfoVirtualApi
import com.marin.hrov.data.repository.PretendEmployeesInfoRepository
import com.marin.hrov.di.Container
import com.marin.hrov.feature.employeeDetails.domain.usecase.DefaultGetEmployeeDetailsUseCase
import com.marin.hrov.feature.employeeList.domain.usecase.DefaultGetEmployeesUseCase
import com.marin.hrov.feature.renewals.domain.usecase.DefaultGetRenewalsUseCase
import kotlin.time.Clock

class HrOvApp : Application() {

    lateinit var container: Container

    override fun onCreate() {
        super.onCreate()
        // simple DI: no koin or hilt
        val employeesInfoVirtualApi = EmployeesInfoVirtualApi(this.applicationContext)
        val employeesInfoRepository = PretendEmployeesInfoRepository(employeesInfoVirtualApi)
        val getEmployeesUseCase = DefaultGetEmployeesUseCase(employeesInfoRepository, Clock.System)
        val getEmployeeDetailsUseCase = DefaultGetEmployeeDetailsUseCase(employeesInfoRepository)
        val getRenewalsUseCase = DefaultGetRenewalsUseCase(employeesInfoRepository, Clock.System)
        container = Container(getEmployeesUseCase, getEmployeeDetailsUseCase, getRenewalsUseCase)
    }
}