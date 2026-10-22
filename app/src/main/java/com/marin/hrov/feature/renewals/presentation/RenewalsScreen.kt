package com.marin.hrov.feature.renewals.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.marin.hrov.feature.common.presentation.DetailPropertyNameValuePair
import com.marin.hrov.feature.common.presentation.ErrorComponent
import com.marin.hrov.feature.common.presentation.LoadingComponent
import com.marin.hrov.feature.renewals.domain.model.EmployeeSubscriptionExtract

@Composable
fun RenewalsScreen() {
    val viewModel: RenewalsViewModel = viewModel(factory = RenewalsViewModel.Factory)
    val output by viewModel.outputFlow.collectAsStateWithLifecycle()
    RenewalsContent(output)
}

@Composable
fun RenewalsContent(output: RenewalsViewModel.Output) {
    when (output) {
        is RenewalsViewModel.Output.Error -> {
            ErrorComponent(errorDescription = output.description)
        }

        RenewalsViewModel.Output.Loading -> {
            LoadingComponent()
        }

        is RenewalsViewModel.Output.Success -> {
            RenewalsSuccess(successOutput = output)
        }
    }
}

@Composable
fun RenewalsSuccess(successOutput: RenewalsViewModel.Output.Success) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 8.dp)
    ) {
        item {
            Text(text = "Expired", style = MaterialTheme.typography.headlineSmall)
        }
        items(
            items = successOutput.expired,
            key = { extract -> "E_${extract.employeeId}_S${extract.subscriptionId}" }
        ) { extract ->
            Spacer(modifier = Modifier.height(height = 16.dp))
            RenewalEntryCard(extract)
        }
        item {
            HorizontalDivider()
            Spacer(modifier = Modifier.height(height = 16.dp))
        }

        item {
            Text(text = "Expire later this week", style = MaterialTheme.typography.headlineSmall)
        }
        items(
            items = successOutput.expiresThisWeek,
            key = { extract -> "E_${extract.employeeId}_S${extract.subscriptionId}" }
        ) { extract ->
            Spacer(modifier = Modifier.height(height = 16.dp))
            RenewalEntryCard(extract)
        }
        item {
            HorizontalDivider()
            Spacer(modifier = Modifier.height(height = 16.dp))
        }

        item {
            Text(text = "Expire later this month", style = MaterialTheme.typography.headlineSmall)
        }
        items(
            items = successOutput.expiresThisMonth,
            key = { extract -> "E_${extract.employeeId}_S${extract.subscriptionId}" }
        ) { extract ->
            Spacer(modifier = Modifier.height(height = 16.dp))
            RenewalEntryCard(extract)
        }
    }
}

@Composable
fun RenewalEntryCard(extract: EmployeeSubscriptionExtract) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp)
            .safeContentPadding()
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            extract.subscriptionId?.let {
                DetailPropertyNameValuePair(
                    propertyName = "Subscription ID#",
                    value = it
                )
            }
            extract.subscriptionTypeLabel?.let {
                DetailPropertyNameValuePair(
                    propertyName = "Subscription Type Label",
                    value = it
                )
            }
            extract.endDate?.let {
                DetailPropertyNameValuePair(
                    propertyName = "Subscription Expiry Date",
                    value = it.toString()
                )
            }
            DetailPropertyNameValuePair(propertyName = "Employee ID#", value = extract.employeeId)
            DetailPropertyNameValuePair(
                propertyName = "Employee Full Name",
                value = extract.employeeFullName
            )
        }
    }
}