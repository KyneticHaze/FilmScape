package com.furkanhrmnc.filmscape.util

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.furkanhrmnc.filmscape.navigation.BottomNavScreen

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {

    val navItems = listOf(
        BottomNavScreen.Favorites,
        BottomNavScreen.Search,
        BottomNavScreen.Person,
        BottomNavScreen.Account
    )

    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.secondary
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        navItems.forEach { bottomNavScreen ->
            val isSelected = currentRoute == bottomNavScreen.route
            NavigationBarItem(
                label = { Text(text = stringResource(id = bottomNavScreen.labelResId)) },
                selected = isSelected,
                onClick = {
                    if (currentRoute != bottomNavScreen.route) {
                        navController.navigate(bottomNavScreen.route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    } else {
                        navController.popBackStack()
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (isSelected) bottomNavScreen.selectedIcon else bottomNavScreen.unselectedIcon,
                        contentDescription = stringResource(id = bottomNavScreen.labelResId)
                    )
                }
            )
        }
    }
}