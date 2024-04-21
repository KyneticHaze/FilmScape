package com.furkanhrmnc.filmscape.ui.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.furkanhrmnc.filmscape.R
import com.furkanhrmnc.filmscape.ui.components.SwipeSection
import com.furkanhrmnc.filmscape.ui.components.BottomBar
import com.furkanhrmnc.filmscape.ui.components.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    mainUIState: MainUIState,
    navController: NavController
) {

    val refreshState = rememberPullToRefreshState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { TopBar(navController) },
        bottomBar = { BottomBar(navController) }
    ) { scaffoldPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .nestedScroll(refreshState.nestedScrollConnection)
                .padding(scaffoldPadding),
            contentAlignment = Alignment.BottomCenter
        ) {

            PullToRefreshContainer(
                state = refreshState,
                modifier = Modifier.align(Alignment.TopCenter),
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary
            )

            if (!refreshState.isRefreshing) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .background(MaterialTheme.colorScheme.surface)
                ) {

                    Spacer(modifier = Modifier.height(8.dp))

                    if (mainUIState.specialList.isEmpty()) {
                        Text(text = stringResource(id = R.string.special))
                    } else {
                        SwipeSection(
                            type = stringResource(id = R.string.special),
                            navController = navController,
                            mainUIState = mainUIState
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}