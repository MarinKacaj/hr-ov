package com.marin.hrov.di

import com.marin.hrov.feature.employeeDetails.domain.usecase.GetEmployeeDetailsUseCase
import com.marin.hrov.feature.employeeList.domain.usecase.GetEmployeesUseCase

data class Container(
    val getEmployeesUseCase: GetEmployeesUseCase,
    val getEmployeeDetailsUseCase: GetEmployeeDetailsUseCase
)