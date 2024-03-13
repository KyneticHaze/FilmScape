package com.furkanhrmnc.filmscape.ui.screen.main.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavController
import com.furkanhrmnc.filmscape.ui.navigation.NavBarItem

@Composable
fun BottomBar(
    navController: NavController
) {

    val navList = listOf(
        NavBarItem.Home,
        NavBarItem.Person,
        NavBarItem.Favorite
    )

    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar {
        navList.forEachIndexed { index, navBarItem ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    navController.navigate(navBarItem.route)
                },
                icon = {
                    Icon(
                        imageVector = if (selectedItemIndex == index) navBarItem.selectedIcon else navBarItem.unselectedIcon,
                        contentDescription = navBarItem.title
                    )
                }
            )
        }
    }
}