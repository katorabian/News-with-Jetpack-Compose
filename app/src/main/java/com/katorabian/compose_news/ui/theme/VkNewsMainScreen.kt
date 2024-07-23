package com.katorabian.compose_news.ui.theme

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun MainScreen() {
    Scaffold(
        bottomBar = {
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
    ) { it: PaddingValues -> }
}