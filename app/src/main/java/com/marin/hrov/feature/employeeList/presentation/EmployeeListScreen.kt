package com.marin.hrov.feature.employeeList.presentation

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.marin.hrov.feature.common.presentation.ErrorComponent
import com.marin.hrov.feature.common.presentation.LoadingComponent
import com.marin.hrov.feature.employeeList.domain.usecase.Filter
import com.marin.hrov.ui.icons.HrOvIcons

@Composable
fun EmployeeListScreen(onOpenEmployeeDetails: (String) -> Unit) {
    val viewModel: EmployeeListViewModel = viewModel(factory = EmployeeListViewModel.Factory)
    val output by viewModel.outputFlow.collectAsStateWithLifecycle()
    val filter by viewModel.filterFlow.collectAsStateWithLifecycle()
    EmployeeListScreenContent(
        output,
        filter,
        onSearch = { searchTerm ->
            viewModel.onChangeSearchedName(query = searchTerm)
        },
        onOpenEmployeeDetails
    )
}

@Composable
fun EmployeeListScreenContent(
    output: EmployeeListViewModel.Output,
    filter: Filter,
    onSearch: (String) -> Unit,
    onOpenEmployeeDetails: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        SearchToolbar(
            searchQuery = filter.anyName ?: "",
            onSearchQueryChanged = onSearch,
            onSearchTriggered = onSearch
        )
        // some filter chips here if I had more time: in order or not, employment status, etc
        when (output) {
            is EmployeeListViewModel.Output.Error -> {
                ErrorComponent(errorDescription = output.description)
            }

            EmployeeListViewModel.Output.Loading -> {
                LoadingComponent()
            }

            is EmployeeListViewModel.Output.Success -> {
                EmployeeListSuccess(
                    successOutput = output,
                    onOpenEmployeeDetails
                )
            }
        }
    }
}

@Composable
fun EmployeeListSuccess(
    successOutput: EmployeeListViewModel.Output.Success,
    onOpenEmployeeDetails: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 8.dp)
    ) {
        item {
            Text(
                text = "(Unfiltered) Total monthly: ${successOutput.currency} ${successOutput.subscriptionsTotal}",
                style = MaterialTheme.typography.labelLarge
            )
        }
        items(
            items = successOutput.items,
            key = { employeeTransportSituation -> employeeTransportSituation.employeeId }
        ) {
            Spacer(modifier = Modifier.height(height = 16.dp))
            EmployeeCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = dropUnlessResumed { onOpenEmployeeDetails(it.employeeId) }),
                employeeSituationItem = it
            )
        }
    }
}

@Composable
fun EmployeeCard(
    modifier: Modifier = Modifier,
    employeeSituationItem: EmployeeListViewModel.Output.Success.EmployeeSituationItem
) {
    Card(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(all = 4.dp)
        ) {
            AsyncImage(
                // no placeholders or any errors for simplicity - would be here if real app
                model = employeeSituationItem.avatarUrl,
                contentDescription = "Employee pic or a personalised image of their choice",
                modifier = Modifier
                    .size(size = 50.dp) // would use MyTheme.size in a real app
                    .clip(shape = MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop,
                onError = {
                    Log.e("EmployeeListScreen", it.result.toString())
                },
            )
            Spacer(modifier = Modifier.width(width = 8.dp))
            Column(modifier = Modifier.weight(weight = 1.0f)) {
                Text(
                    text = employeeSituationItem.employmentStatusDescription,
                    style = MaterialTheme.typography.labelSmall
                )
                Text(
                    text = employeeSituationItem.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = employeeSituationItem.subtitle,
                    style = MaterialTheme.typography.titleSmall
                )
            }
            Text(
                text = if (employeeSituationItem.isInOrder) "OV in order" else "OV ISSUE",
                style = MaterialTheme.typography.bodyMedium,
                color = if (employeeSituationItem.isInOrder) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    MaterialTheme.colorScheme.error
                }
            )
        }
    }
}

@Composable
private fun SearchToolbar(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onSearchTriggered: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth(),
    ) {
        SearchTextField(
            onSearchQueryChanged = onSearchQueryChanged,
            onSearchTriggered = onSearchTriggered,
            searchQuery = searchQuery,
        )
    }
}

@Composable
private fun SearchTextField(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onSearchTriggered: (String) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val onSearchExplicitlyTriggered = {
        keyboardController?.hide()
        onSearchTriggered(searchQuery)
    }

    TextField(
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        leadingIcon = {
            Icon(
                imageVector = HrOvIcons.search,
                contentDescription = "Search by first name and last name",
                tint = MaterialTheme.colorScheme.onSurface,
            )
        },
        trailingIcon = {
            if (searchQuery.isNotEmpty()) {
                IconButton(
                    onClick = {
                        onSearchQueryChanged("")
                    },
                ) {
                    Icon(
                        imageVector = HrOvIcons.close,
                        contentDescription = "Close",
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        },
        onValueChange = {
            if ("\n" !in it) onSearchQueryChanged(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .focusRequester(focusRequester)
            .onKeyEvent {
                if (it.key == Key.Enter) {
                    if (searchQuery.isBlank()) return@onKeyEvent false
                    onSearchExplicitlyTriggered()
                    true
                } else {
                    false
                }
            }
            .testTag("searchTextField"),
        shape = RoundedCornerShape(32.dp),
        value = searchQuery,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                if (searchQuery.isBlank()) return@KeyboardActions
                onSearchExplicitlyTriggered()
            },
        ),
        maxLines = 1,
        singleLine = true,
    )
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}
