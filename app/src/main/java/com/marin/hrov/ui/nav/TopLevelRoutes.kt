package com.marin.hrov.ui.nav

import androidx.navigation3.runtime.NavKey
import com.marin.hrov.ui.icons.HrOvIcons

// explicitly qualified type params on purpose for a tiny bit of future proofing:
// we don't want the wrong type inferred when we add more
@Suppress("RemoveExplicitTypeArguments")
val topLevelRoutes = mapOf<NavKey, NavBarItem>(
    EmployeeList to NavBarItem(icon = HrOvIcons.personApron, description = "Employee list"),
    Renewals to NavBarItem(icon = HrOvIcons.refresh, description = "Renewals")
)