package com.katorabian.compose_news

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxDefaults
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.katorabian.compose_news.other.InstagramProfileCard
import com.katorabian.compose_news.other.InstagramViewModel
import com.katorabian.compose_news.presentation.theme.ComposeNewsTheme
import com.katorabian.compose_news.presentation.viewModel.PostViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val postViewModel by viewModels<PostViewModel>()
    private val instagramViewModel by viewModels<InstagramViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InstagramTest(viewModel = instagramViewModel)
        }
/*
        setContent {
            ComposeNewsTheme {
                MainScreen(postViewModel)
            }
        }
*/
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InstagramTest(viewModel: InstagramViewModel) {
    ComposeNewsTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
        ) {
            val models = viewModel.models.observeAsState(emptyList())
            LazyColumn {
                items(models.value, key = { it.id }) { instagramItem ->
                    /* my version of half screen threshold */
                    val dismissThresholds = with(LocalDensity.current) {
                        LocalConfiguration.current.screenWidthDp.dp.toPx() * 0.5F
                    }

                    val dismissBoxState = rememberSwipeToDismissBoxState(
                        positionalThreshold = { dismissThresholds },
                        confirmValueChange = { value ->
                            /* make check and decision to complete dismiss */
                            val isDismissed = value in setOf(
                                SwipeToDismissBoxValue.StartToEnd,
                                SwipeToDismissBoxValue.EndToStart
                            )
                            /* remove local element */
                            if (isDismissed) viewModel.removeItem(instagramItem)

                            return@rememberSwipeToDismissBoxState isDismissed
                        }
                    )

                    SwipeToDismissBox(
                        state = dismissBoxState,
                        enableDismissFromEndToStart = true,
                        enableDismissFromStartToEnd = false,
                        backgroundContent = {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                                    .background(Color.Red.copy(alpha = 0.5F)),
                                contentAlignment = Alignment.CenterEnd,
                            ) {
                                 Text(
                                     modifier = Modifier.padding(16.dp),
                                     text = "Delete item",
                                     color = Color.White,
                                     fontSize = 24.sp
                                 )
                            }
                        },
                    ) {
                        InstagramProfileCard(
                            instagramItem = instagramItem,
                            onFollowedButtonClickListener = viewModel::changeFollowedStatus
                        )
                    }
                }
            }
        }
    }
}