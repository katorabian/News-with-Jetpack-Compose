package com.katorabian.compose_news.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.katorabian.compose_news.presentation.viewModel.MainViewModel
import com.katorabian.compose_news.domain.NavigationItem
import com.katorabian.compose_news.domain.StatisticType
import com.katorabian.compose_news.domain.constant.ZERO_INT

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(viewModel: MainViewModel) {
    val selectedNavItem by viewModel.selectedNavItem.observeAsState(NavigationItem.Home)
    Scaffold(
        bottomBar = {
            NavigationBar(
                modifier = Modifier.height(58.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                val items = listOf(
                    NavigationItem.Home,
                    NavigationItem.Favorite,
                    NavigationItem.Profile
                )
                items.forEach { item ->
                    NavigationBarItem(
                        modifier = Modifier.height(20.dp),
                        selected = selectedNavItem == item,
                        onClick = { viewModel.selectNavItem(item) },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(id = item.titleResId),
                                fontSize = 11.sp
                            )
                        },
                        colors = NavigationBarItemColors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                            selectedTextColor = MaterialTheme.colorScheme.onPrimary,
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
    ) { paddingValues ->
        when (selectedNavItem) {
            NavigationItem.Home -> {
                HomeScreen(viewModel = viewModel, paddingValues = paddingValues)
            }
            NavigationItem.Favorite -> {
                TextCounter(name = "NavigationItem.Favorite")
            }
            NavigationItem.Profile -> {
                TextCounter(name = "NavigationItem.Profile")
            }
        }

    }
}

@Composable
fun TextCounter(name: String) {
    var clickCount by remember { mutableStateOf(ZERO_INT) }
    Text(
        modifier = Modifier.clickable { clickCount++ },
        text = "$name Count: $clickCount",
        color = Color.Black
    )
}