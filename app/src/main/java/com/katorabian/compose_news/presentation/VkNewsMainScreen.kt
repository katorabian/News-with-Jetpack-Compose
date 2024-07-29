package com.katorabian.compose_news.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.katorabian.compose_news.domain.NavigationItem
import com.katorabian.compose_news.domain.constant.ZERO_INT
import com.katorabian.compose_news.presentation.navigation.AppNavGraph
import com.katorabian.compose_news.presentation.viewModel.MainViewModel

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val navHostController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar(
                modifier = Modifier.height(58.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                val navBackStackEntry by navHostController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val items = listOf(
                    NavigationItem.Home,
                    NavigationItem.Favorite,
                    NavigationItem.Profile
                )
                items.forEach { item ->
                    NavigationBarItem(
                        modifier = Modifier.height(20.dp),
                        selected = currentRoute == item.screen.route,
                        onClick = { navHostController.navigate(item.screen.route) },
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
        AppNavGraph(
            navHostController = navHostController,
            homeScreenContent = { HomeScreen(viewModel = viewModel, paddingValues = paddingValues) },
            favoriteScreenContent = { TextCounter(name = "NavigationItem.Favorite") },
            profileScreenContent = { TextCounter(name = "NavigationItem.Profile") }
        )
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