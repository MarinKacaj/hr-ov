package com.marin.hrov.feature.common.presentation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DetailPropertyNameValuePair(propertyName: String, value: String) {
    Row(modifier = Modifier.padding(all = 8.dp)) { // no param modifier for simplicity
        Text(
            text = "$propertyName: ",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.alignByBaseline()
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.alignByBaseline()
        )
    }
}
