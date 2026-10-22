package com.marin.hrov.feature.renewals.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.marin.hrov.HrOvApp
import com.marin.hrov.core.OVResult
import com.marin.hrov.feature.renewals.domain.model.EmployeeSubscriptionExtract
import com.marin.hrov.feature.renewals.domain.usecase.GetRenewalsUseCase
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class RenewalsViewModel(getRenewalsUseCase: GetRenewalsUseCase) : ViewModel() {

    val outputFlow: StateFlow<Output> = getRenewalsUseCase.invoke().map { renewalsResult ->
        when (renewalsResult) {
            is OVResult.Error -> {
                Output.Error(description = renewalsResult.exception.message ?: "An error occurred")
            }

            OVResult.Loading -> {
                Output.Loading
            }

            is OVResult.Success -> {
                val immutableExpired = renewalsResult.data.expired.toPersistentList()
                val immutableExpiresThisWeek =
                    renewalsResult.data.expiresThisWeek.toPersistentList()
                val immutableExpiresThisMonth =
                    renewalsResult.data.expiresThisMonth.toPersistentList()
                Output.Success(
                    expired = immutableExpired,
                    expiresThisWeek = immutableExpiresThisWeek,
                    expiresThisMonth = immutableExpiresThisMonth
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
        data class Success(
            val expired: PersistentList<EmployeeSubscriptionExtract>,
            val expiresThisWeek: PersistentList<EmployeeSubscriptionExtract>,
            val expiresThisMonth: PersistentList<EmployeeSubscriptionExtract>
        ) : Output()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val getRenewalsUseCase =
                    (this[APPLICATION_KEY] as HrOvApp).container.getRenewalsUseCase
                RenewalsViewModel(getRenewalsUseCase)
            }
        }
    }
}