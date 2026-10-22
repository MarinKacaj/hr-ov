package com.marin.hrov.data.network

import android.content.Context
import com.marin.hrov.core.OVResult
import com.marin.hrov.data.model.EmployeeInfoResponseItem
import com.marin.hrov.data.model.MainResponse
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class EmployeesInfoVirtualApi(
    val context: Context, // I promise I won't abuse this context ref
    val ioDispatcher: CoroutineDispatcher = Dispatchers.IO // just in case we test this one day
) {
    suspend fun employeeInfoItems(): OVResult<List<EmployeeInfoResponseItem>> =
        withContext(ioDispatcher) {
            try {
                val content = context.assets.open("employees.json").bufferedReader().use {
                    it.readText()
                }
                val mainResponse = Json.decodeFromString<MainResponse>(content)
                OVResult.Success(data = mainResponse.results)
            } catch (e: Exception) {
                // we don't want to get in the way of cancellation
                if (e is CancellationException) {
                    throw e
                }
                // log it to Crashlytics or sth
                OVResult.Error(e)
            }
        }
}