package com.marin.hrov

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.marin.hrov.feature.employeeDetails.presentation.EmployeeDetailsScreen
import com.marin.hrov.feature.employeeList.presentation.EmployeeListScreen
import com.marin.hrov.feature.renewals.presentation.RenewalsScreen
import com.marin.hrov.ui.nav.EmployeeDetails
import com.marin.hrov.ui.nav.EmployeeList
import com.marin.hrov.ui.nav.Navigator
import com.marin.hrov.ui.nav.Renewals
import com.marin.hrov.ui.nav.rememberNavigationState
import com.marin.hrov.ui.nav.topLevelRoutes
import com.marin.hrov.ui.theme.HROVTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HROVTheme {
                HROVApp()
            }
        }
    }
}

@Composable
fun HROVApp() {
    val navigationState = rememberNavigationState(
        startRoute = EmployeeList,
        topLevelRoutes = setOf(EmployeeList, Renewals)
    )
    val navigator = remember { Navigator(navigationState) }
    val entryProvider = entryProvider {
        entry<EmployeeList> {
            EmployeeListScreen(onOpenEmployeeDetails = { employeeId ->
                navigator.navigate(
                    route = EmployeeDetails(
                        employeeId
                    )
                )
            })
        }
        entry<EmployeeDetails> { entryKey ->
            EmployeeDetailsScreen(entryKey.employeeId)
        }
        entry<Renewals> {
            RenewalsScreen()
        }
    }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            topLevelRoutes.forEach { (route, navBarItem) ->
                item(
                    icon = {
                        Icon(
                            imageVector = navBarItem.icon,
                            contentDescription = navBarItem.description
                        )
                    },
                    label = { Text(navBarItem.description) },
                    selected = route == navigationState.topLevelRoute,
                    onClick = { navigator.navigate(route) }
                )
            }
        }
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NavDisplay(
                entries = navigationState.toDecoratedEntries(entryProvider),
                onBack = { navigator.goBack() },
                modifier = Modifier.padding(paddingValues = innerPadding)
            )
        }
    }
}
