package com.marin.hrov.feature.employeeDetails.presentation

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.DEFAULT_ARGS_KEY
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.marin.hrov.feature.common.model.EmployeeInfo
import com.marin.hrov.feature.common.model.Subscription
import com.marin.hrov.feature.common.presentation.ErrorComponent
import com.marin.hrov.feature.common.presentation.LoadingComponent
import com.marin.hrov.feature.common.presentation.description

@Composable
fun EmployeeDetailsScreen(employeeId: String) {
    val viewModel: EmployeeDetailsViewModel = viewModel(
        factory = EmployeeDetailsViewModel.Factory,
        extras = MutableCreationExtras(
            (LocalViewModelStoreOwner.current as? HasDefaultViewModelProviderFactory)?.defaultViewModelCreationExtras
                ?: CreationExtras.Empty
        ).apply {
            val args = Bundle().apply {
                putString(EmployeeDetailsViewModel.PARAM_EMPLOYEE_ID, employeeId)
            }
            set(DEFAULT_ARGS_KEY, args)
        })

    val output by viewModel.outputFlow.collectAsStateWithLifecycle()
    EmployeeDetailsScreenContent(output)
}

@Composable
fun EmployeeDetailsScreenContent(output: EmployeeDetailsViewModel.Output) {
    when (output) {
        is EmployeeDetailsViewModel.Output.Error -> {
            ErrorComponent(errorDescription = output.description)
        }

        EmployeeDetailsViewModel.Output.Loading -> {
            LoadingComponent()
        }

        is EmployeeDetailsViewModel.Output.Success -> {
            EmployeeDetailsSuccess(employeeInfo = output.employeeInfo)
        }
    }
}

@Composable
fun EmployeeDetailsSuccess(employeeInfo: EmployeeInfo) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        AsyncImage(
            // no placeholders or any errors for simplicity - would be here if real app
            model = employeeInfo.avatarUrl,
            contentDescription = "Employee pic or a personalised image of their choice",
            modifier = Modifier
                .size(size = 150.dp) // would use MyTheme.size in a real app
                .clip(shape = MaterialTheme.shapes.medium)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Crop,
            onError = {
                Log.e("EmployeeDetailsScreen", it.result.toString())
            }
        )
        DetailPropertyNameValuePair(propertyName = "ID", value = employeeInfo.employeeId)
        DetailPropertyNameValuePair(propertyName = "First name", value = employeeInfo.firstName)
        DetailPropertyNameValuePair(propertyName = "Last name", value = employeeInfo.lastName)
        DetailPropertyNameValuePair(propertyName = "Email", value = employeeInfo.email)
        DetailPropertyNameValuePair(propertyName = "Phone", value = employeeInfo.phone)
        DetailPropertyNameValuePair(propertyName = "Job Title", value = employeeInfo.jobTitle)
        DetailPropertyNameValuePair(propertyName = "Department", value = employeeInfo.department)
        DetailPropertyNameValuePair(propertyName = "Office", value = employeeInfo.office)
        employeeInfo.managerName?.let {
            DetailPropertyNameValuePair(
                propertyName = "Manager",
                value = it
            )
        }
        DetailPropertyNameValuePair(
            propertyName = "Employment Status",
            value = employeeInfo.employmentStatus.description()
        )
        DetailPropertyNameValuePair(
            propertyName = "Employment start date",
            value = employeeInfo.employmentStartDate.toString()
        )
        employeeInfo.employmentEndDate?.let {
            DetailPropertyNameValuePair(propertyName = "Employment end date", value = it.toString())
        }
        if (employeeInfo.subscriptions.isNotEmpty()) {
            Text(
                text = "Subscriptions",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(all = 8.dp) // would have used MyTheme.spacing here
            )
        }
        employeeInfo.subscriptions.forEach { subscription ->
            SubscriptionCard(subscription)
        }
    }
}

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

@Composable
fun SubscriptionCard(subscription: Subscription) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp)
            .safeContentPadding()
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            subscription.subscriptionId?.let {
                DetailPropertyNameValuePair(
                    propertyName = "Subscription ID#",
                    value = it
                )
            }
            subscription.subscriptionType?.let {
                DetailPropertyNameValuePair(
                    propertyName = "Subscription Type",
                    value = it
                )
            }
            subscription.subscriptionTypeLabel?.let {
                DetailPropertyNameValuePair(
                    propertyName = "Subscription Type Label",
                    value = it
                )
            }
            subscription.provider?.let {
                DetailPropertyNameValuePair(
                    propertyName = "Provider",
                    value = it
                )
            }
            subscription.subscriptionStatus?.let {
                DetailPropertyNameValuePair(
                    propertyName = "Subscription Status",
                    value = it.toString()
                )
            }
            subscription.cardNumber?.let {
                DetailPropertyNameValuePair(
                    propertyName = "Card number",
                    value = it
                )
            }
            subscription.travelClass?.let {
                DetailPropertyNameValuePair(
                    propertyName = "Travel class",
                    value = it
                )
            }
            subscription.zones?.let {
                DetailPropertyNameValuePair(
                    propertyName = "Zones",
                    value = it.joinToString(separator = ", ")
                )
            }
            subscription.startDate?.let {
                DetailPropertyNameValuePair(
                    propertyName = "Start date",
                    value = it.toString()
                )
            }
            subscription.endDate?.let {
                DetailPropertyNameValuePair(
                    propertyName = "End date",
                    value = it.toString()
                )
            }
            DetailPropertyNameValuePair(
                propertyName = "Auto renew",
                value = when (subscription.autoRenew) {
                    true -> "Yes"
                    false -> "No"
                    null -> "Unknown"
                }
            )
            subscription.costPerMonth?.let {
                val currency = subscription.currency ?: ""
                DetailPropertyNameValuePair(
                    propertyName = "Cost per month",
                    value = "$currency $it" // a bit of handwaving here
                )
            }
            subscription.costCenter?.let {
                DetailPropertyNameValuePair(
                    propertyName = "Cost centre",
                    value = it
                )
            }
            subscription.lastUsedAt?.let {
                DetailPropertyNameValuePair(
                    propertyName = "Last used at",
                    value = it
                )
            }
        }
    }
}