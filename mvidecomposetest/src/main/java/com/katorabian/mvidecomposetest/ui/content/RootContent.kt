package com.katorabian.mvidecomposetest.ui.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.katorabian.mvidecomposetest.presentation.DefaultRootComponent
import com.katorabian.mvidecomposetest.presentation.RootComponent
import com.katorabian.mvidecomposetest.ui.theme.MviDecomposeTestTheme

@Composable
fun RootContent(
   component: DefaultRootComponent
) {
    MviDecomposeTestTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Children(
                stack = component.stack
            ) {
                when (val instance = it.instance) {
                    is RootComponent.Child.AddContact -> {
                        AddContact(component = instance.component)
                    }
                    is RootComponent.Child.ContactList -> {
                        Contacts(component = instance.component)
                    }
                    is RootComponent.Child.EditContact -> {
                        EditContact(component = instance.component)
                    }
                }
            }
        }
    }
}