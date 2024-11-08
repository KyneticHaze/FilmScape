package com.furkanhrmnc.filmscape.ui.screen.person

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.furkanhrmnc.filmscape.domain.model.Person
import com.furkanhrmnc.filmscape.navigation.Destinations
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonScreen(
    modifier: Modifier = Modifier,
    viewModel: PersonViewModel,
    navController: NavController,
) {
    val popularPersons = viewModel.popularPersons.collectAsLazyPagingItems()
    val scope = rememberCoroutineScope()
    val lazyGridState = rememberLazyGridState()
    val scrollToTop by remember { derivedStateOf { lazyGridState.firstVisibleItemIndex > 3 } }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Popular Actors",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
            })
        },
        floatingActionButton = {
            AnimatedVisibility(visible = scrollToTop) {
                FloatingActionButton(
                    onClick = {
                        scope.launch {
                            lazyGridState.animateScrollToItem(0)
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowUpward,
                        contentDescription = "Scroll To Top"
                    )
                }
            }
        }
    ) { paddingValues ->
        PopularPersonsGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            viewModel = viewModel,
            popularPersons = popularPersons,
            lazyGridState = lazyGridState,
            navController = navController
        )
    }
}

@Composable
fun PopularPersonsGrid(
    modifier: Modifier = Modifier,
    viewModel: PersonViewModel,
    popularPersons: LazyPagingItems<Person>,
    lazyGridState: LazyGridState,
    navController: NavController,
) {

    val error by viewModel.error.collectAsState()

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        state = lazyGridState
    ) {
        when {
            popularPersons.loadState.refresh is LoadState.Error -> {
                viewModel.onError((popularPersons.loadState.refresh as LoadState.Error).error)
            }

            popularPersons.loadState.append is LoadState.Error -> {
                viewModel.onError((popularPersons.loadState.append as LoadState.Error).error)
            }
        }

        if (error != null) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                ErrorItem(
                    errorMessage = error?.localizedMessage.orEmpty(),
                    onRetry = {
                        viewModel.onErrorConsumed()
                        popularPersons.retry()
                    }
                )
            }
        }

        items(
            count = popularPersons.itemCount,
            key = { index -> popularPersons[index]?.id ?: index }
        ) { index ->
            popularPersons[index]?.let { person ->
                PersonCard(
                    person = person,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun ErrorItem(
    errorMessage: String,
    onRetry: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Bir hata oluştu: $errorMessage", color = Color.Red)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetry) { Text(text = "Yeniden Dene") }
    }
}

@Composable
fun PersonCard(
    modifier: Modifier = Modifier,
    person: Person,
    navController: NavController,
) {
    Box(
        modifier = modifier
            .size(160.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { navController.navigate("${Destinations.PERSON_DETAILS.route}?id=${person.id}") },
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = person.profilePath,
            contentDescription = person.name,
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 6.dp),
            text = person.name,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.inversePrimary
        )
    }
}
