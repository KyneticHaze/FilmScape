package com.furkanhrmnc.filmscape.ui.screen.trending

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Sort
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.furkanhrmnc.filmscape.R
import com.furkanhrmnc.filmscape.navigation.Destinations
import com.furkanhrmnc.filmscape.util.MediaCard
import com.furkanhrmnc.filmscape.util.ScrollToUp
import com.furkanhrmnc.filmscape.util.Time
import com.furkanhrmnc.filmscape.util.UiEvent
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrendingScreen(
    modifier: Modifier = Modifier,
    viewModel: TrendingViewModel = koinInject(),
    navController: NavController,
) {

    val snackBarHostState = remember { SnackbarHostState() }
    val uiEvent = viewModel.uiEvent.collectAsState(initial = null).value

    LaunchedEffect(uiEvent) {
        when (uiEvent) {
            is UiEvent.ShowSnackbar -> snackBarHostState.showSnackbar(
                message = uiEvent.message,
                actionLabel = uiEvent.action
            )

            else -> Unit
        }
    }

    val trendMedias = viewModel.trendMedia.collectAsLazyPagingItems()
    val uiState by viewModel.uiState.collectAsState()
    val lazyGridState = rememberLazyGridState()
    val scrollToTop by remember { derivedStateOf { lazyGridState.firstVisibleItemIndex > 3 } }


    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.trending),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                actions = {
                    IconButton(onClick = viewModel::toggle) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.Sort,
                            contentDescription = stringResource(R.string.period),
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }

                    DropdownMenu(
                        expanded = uiState.expanded,
                        onDismissRequest = viewModel::toggle
                    ) {
                        Time.entries.forEach { time ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = time.name,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.tertiary
                                    )
                                },
                                onClick = {
                                    val currentTime = if (time == Time.DAY) Time.DAY else Time.WEEK
                                    viewModel.changePeriod(currentTime)
                                    viewModel.toggle()
                                }
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            ScrollToUp(
                scrollToTop = scrollToTop,
                lazyGridState = lazyGridState
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { scaffoldPadding ->
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            columns = GridCells.Fixed(3),
            state = lazyGridState,
            contentPadding = scaffoldPadding
        ) {
            items(
                count = trendMedias.itemCount,
                key = { id -> trendMedias[id]?.id ?: id }
            ) { index ->
                trendMedias[index]?.let { media ->
                    MediaCard(
                        media = media,
                        onCLick = { navController.navigate("${Destinations.DETAILS.route}?id=${media.id}&type=${media.type}") }
                    )
                }
            }
        }
    }
}