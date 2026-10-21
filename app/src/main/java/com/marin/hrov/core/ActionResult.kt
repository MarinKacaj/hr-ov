package com.marin.hrov.core

sealed interface ActionResult<out T> {
    data class Success<T>(val data: T) : ActionResult<T>
    data class Error(val exception: Throwable) : ActionResult<Nothing>
    data object Loading : ActionResult<Nothing>
}
