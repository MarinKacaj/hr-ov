package com.marin.hrov.di

import com.marin.hrov.feature.employeeDetails.domain.usecase.GetEmployeeDetailsUseCase
import com.marin.hrov.feature.employeeList.domain.usecase.GetEmployeesUseCase
import com.marin.hrov.feature.renewals.domain.usecase.GetRenewalsUseCase

data class Container(
    val getEmployeesUseCase: GetEmployeesUseCase,
    val getEmployeeDetailsUseCase: GetEmployeeDetailsUseCase,
    val getRenewalsUseCase: GetRenewalsUseCase
)