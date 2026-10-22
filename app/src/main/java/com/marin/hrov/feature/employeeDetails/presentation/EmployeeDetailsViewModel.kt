package com.marin.hrov.feature.employeeDetails.presentation

import androidx.lifecycle.DEFAULT_ARGS_KEY
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.marin.hrov.HrOvApp
import com.marin.hrov.core.OVResult
import com.marin.hrov.feature.common.model.EmployeeInfo
import com.marin.hrov.feature.employeeDetails.domain.usecase.GetEmployeeDetailsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class EmployeeDetailsViewModel(
    private val employeeId: String,
    private val getEmployeeDetailsUseCase: GetEmployeeDetailsUseCase
) : ViewModel() {

    val outputFlow: StateFlow<Output> = getEmployeeDetailsUseCase.invoke(employeeId).map { result ->
        when (result) {
            is OVResult.Error -> Output.Error(
                description = result.exception.message ?: "An error occurred"
            )

            OVResult.Loading -> Output.Loading

            is OVResult.Success -> Output.Success(employeeInfo = result.data)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000L),
        initialValue = Output.Loading
    )

    sealed class Output {
        data object Loading : Output()
        data class Error(val description: String) : Output()
        data class Success(val employeeInfo: EmployeeInfo) : Output()
    }

    companion object {
        const val PARAM_EMPLOYEE_ID = "characterId"

        val Factory: ViewModelProvider.Factory = viewModelFactory {

            initializer {

                val app = (this[APPLICATION_KEY] as HrOvApp)
                val getEmployeeDetailsUseCase = app.container.getEmployeeDetailsUseCase
                val employeeId =
                    checkNotNull(this[DEFAULT_ARGS_KEY]).getString(PARAM_EMPLOYEE_ID, "")

                EmployeeDetailsViewModel(employeeId, getEmployeeDetailsUseCase)
            }
        }
    }
}