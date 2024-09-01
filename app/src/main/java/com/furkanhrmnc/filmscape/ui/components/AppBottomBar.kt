package com.furkanhrmnc.filmscape.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.furkanhrmnc.filmscape.navigation.components.BottomBarItems

/**
 * [BottomBarItems] sınıfından oluşan bir liste.
 *
 * Bu listeyi daha sonra navigasyon barında for döngüsü ile kullanacağız.
 */
val navList = listOf(
    BottomBarItems.Home,
    BottomBarItems.Actors)

@Composable
fun AppBottomBar(
    modifier: Modifier = Modifier,
    elevation: Dp = 2.dp,
    backgroundColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.secondary,
    navController: NavController
) {

    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar(
        modifier = modifier,
        tonalElevation = elevation,
        containerColor = backgroundColor,
        contentColor = contentColor
    ) {
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