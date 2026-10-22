package com.marin.hrov.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.serialization.NavKeySerializer

@Composable
fun rememberNavKey(initialKey: NavKey): MutableState<NavKey> {
    return rememberSerializable(stateSerializer = NavKeySerializer()) {
        mutableStateOf(initialKey)
    }
}