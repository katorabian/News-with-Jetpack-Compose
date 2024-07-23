package com.katorabian.compose_news.ui.theme

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch

@Preview
@Composable
fun MainScreen() {
    val snackBarHostState = SnackbarHostState()
    val scope = rememberCoroutineScope()
    val fabIsVisible = remember { mutableStateOf(true) }
    Scaffold(
        floatingActionButton = {
            if (fabIsVisible.value) FloatingActionButton(
                modifier = Modifier,
                onClick = {
                    scope.launch {
                        val actionRes = snackBarHostState.showSnackbar(
                            message = "This is snackBar",
                            actionLabel = "Hide FAB",
                            duration = SnackbarDuration.Long
                        )
                        if (actionRes == SnackbarResult.ActionPerformed) {
                            fabIsVisible.value = false
                        }
                    }
                }
            ) {
                Icon(Icons.Outlined.Create, contentDescription = null)
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        bottomBar = { BottomBar() }
    ) { it: PaddingValues -> }
}

@Preview
@Composable
fun BottomBar() {
    NavigationBar {
        val selectedItemPos = remember { mutableStateOf(0) }
        Log.d("COMPOSE_TEST", "NavigationBar: ${selectedItemPos.value}")
        val items = listOf(
            NavigationItem.Home,
            NavigationItem.Favorite,
            NavigationItem.Profile
        )
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemPos.value == index,
                onClick = { selectedItemPos.value = index },
                icon = {
                    Icon(item.icon, contentDescription = null)
                },
                label = {
                    Text(text = stringResource(id = item.titleResId))
                },
                colors = NavigationBarItemColors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    selectedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSecondary,
                    unselectedTextColor = MaterialTheme.colorScheme.onSecondary,
                    disabledIconColor = Color.Green,
                    disabledTextColor = Color.Green
                )
            )
        }
    }
}