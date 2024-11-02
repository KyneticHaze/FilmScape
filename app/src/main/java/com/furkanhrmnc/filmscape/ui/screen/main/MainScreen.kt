package com.furkanhrmnc.filmscape.ui.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.furkanhrmnc.filmscape.R
import com.furkanhrmnc.filmscape.domain.model.Media
import com.furkanhrmnc.filmscape.navigation.components.BottomBarItems
import com.furkanhrmnc.filmscape.util.Category
import com.furkanhrmnc.filmscape.util.MediaListItem
import com.furkanhrmnc.filmscape.util.MovieTabs
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {

    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

    val navList = listOf(
        BottomBarItems.Home,
        BottomBarItems.Search,
        BottomBarItems.POPULAR,
        BottomBarItems.Favorite,
    )

    val scope = rememberCoroutineScope()
    val viewModel: MainViewModel = koinInject { parametersOf("movie", Category.POPULAR) }
    val medias = viewModel.medias.collectAsLazyPagingItems()
    val pagerState = rememberPagerState(pageCount = { MovieTabs.entries.size })
    val selectedTabIndex by remember { derivedStateOf { pagerState.currentPage } }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                modifier = Modifier,
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                },
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.tertiary
            ) {
                navList.forEachIndexed { index, navBarItem ->
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = {
                            selectedItemIndex = index
                            navController.navigate(navBarItem.route)
                        },
                        label = { Text(text = navBarItem.title) },
                        icon = {
                            Icon(
                                imageVector = if (selectedItemIndex == index) navBarItem.selectedIcon else navBarItem.unselectedIcon,
                                contentDescription = navBarItem.title,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = MaterialTheme.colorScheme.onTertiary
                        )
                    )
                }
            }
        },
    ) { scaffoldPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(scaffoldPadding)
        ) {
            Column {
                PrimaryTabRow(
                    selectedTabIndex = selectedTabIndex,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    MovieTabs.entries.forEachIndexed { index, movieTab ->
                        Tab(
                            selected = index == selectedTabIndex,
                            selectedContentColor = MaterialTheme.colorScheme.primary,
                            unselectedContentColor = MaterialTheme.colorScheme.secondary,
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(movieTab.ordinal)
                                }
                            },
                            text = {
                                Text(
                                    text = movieTab.category,
                                    style = MaterialTheme.typography.labelSmall,
                                    textAlign = TextAlign.Center
                                )
                            },
                            icon = {
                                Icon(
                                    imageVector = if (index == selectedTabIndex)
                                        movieTab.selectedIcon else movieTab.unSelectedIcon,
                                    contentDescription = "Tab Icon"
                                )
                            }
                        )
                    }
                }

                HorizontalPager(state = pagerState) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        MediaLazyPaging(
                            medias = medias,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MediaLazyPaging(
    modifier: Modifier = Modifier,
    medias: LazyPagingItems<Media>,
    navController: NavController,
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(medias.itemCount) { index ->
            medias[index]?.let { media ->
                MediaListItem(media = media, navController = navController)
            }
        }
    }
}