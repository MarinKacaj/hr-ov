package com.marin.hrov.data.model

import kotlinx.serialization.Serializable

@Serializable
data class MainResponse(
    val generatedAt: String, // we don't take this into consideration for the time being
    val count: Int, // if this is only about each employee once, Int is more than enough
    val results: List<EmployeeInfoResponseItem>
)