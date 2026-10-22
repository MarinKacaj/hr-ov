package com.marin.hrov.core

sealed interface OVResult<out T> {
    data class Success<T>(val data: T) : OVResult<T>
    data class Error(val exception: Throwable) : OVResult<Nothing>
    data object Loading : OVResult<Nothing>
}
