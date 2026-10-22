package com.marin.hrov.feature.employeeList.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.marin.hrov.HrOvApp
import com.marin.hrov.core.OVResult
import com.marin.hrov.feature.common.model.EmploymentStatus
import com.marin.hrov.feature.common.presentation.description
import com.marin.hrov.feature.employeeList.domain.usecase.GetEmployeesUseCase
import com.marin.hrov.feature.employeeList.domain.usecase.Filter
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class EmployeeListViewModel(
    private val getEmployeesUseCase: GetEmployeesUseCase,
) : ViewModel() {

    // the name is a bit of Hungarian notation with extra steps,
    // but couldn't think of anything better
    private val _filterFlow: MutableStateFlow<Filter> = MutableStateFlow(value = Filter())
    val filterFlow: StateFlow<Filter> = _filterFlow
    // we could also put the filter in the saved state handle for longer survival,
    // but I think it's ok this way for now, given the overall simplicity of the app and timing

    // apparently, present tense is the way to go according to ktlint
    fun onChangeSearchedName(query: String) {
        _filterFlow.update { currentFilter -> currentFilter.copy(anyName = query) }
    }

    fun onToggleByOrderlyState(shouldBeInOrder: Boolean?) {
        _filterFlow.update { currentFilter ->
            currentFilter.copy(isTransportInOrder = shouldBeInOrder)
        }
    }

    fun onFilterByEmploymentStatus(employmentStatus: EmploymentStatus?) {
        _filterFlow.update { currentFilter ->
            currentFilter.copy(employmentStatus = employmentStatus)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val outputFlow: StateFlow<Output> = _filterFlow.flatMapLatest { filter: Filter ->
        getEmployeesUseCase.invoke(filter).map { employeesResult ->
            when (employeesResult) {
                // in real life, this description would have been
                // properly mapped onto a user-friendly string and logged to sth like Crashlytics
                is OVResult.Error -> Output.Error(
                    description = employeesResult.exception.message ?: "An error occurred"
                )

                OVResult.Loading -> Output.Loading
                is OVResult.Success -> Output.Success(
                    items = employeesResult.data.map { employeeInfo ->
                        Output.Success.EmployeeSituationItem(
                            employeeId = employeeInfo.employeeId,
                            title = "${employeeInfo.firstName} ${employeeInfo.lastName}",
                            subtitle = employeeInfo.jobTitle,
                            avatarUrl = employeeInfo.avatarUrl,
                            isInOrder = employeeInfo.areAllSubscriptionsInOrder,
                            employmentStatusDescription = employeeInfo.employmentStatus.description()
                        )
                    }.toPersistentList() // PersistentList is considered stable by compose
                )
            }
        }

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000L),
        initialValue = Output.Loading
    )

    sealed class Output {
        data object Loading : Output()
        data class Error(val description: String) : Output()
        // todo empty result set
        data class Success(val items: PersistentList<EmployeeSituationItem>) : Output() {
            data class EmployeeSituationItem(
                val employeeId: String,
                val title: String,
                val subtitle: String,
                val avatarUrl: String,
                val isInOrder: Boolean,
                val employmentStatusDescription: String
            )
        }
    }



    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val employeesUseCase = (this[APPLICATION_KEY] as HrOvApp).container.getEmployeesUseCase
                EmployeeListViewModel(employeesUseCase)
            }
        }
    }
}