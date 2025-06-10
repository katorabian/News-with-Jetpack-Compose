package com.katorabian.mvidecomposetest.ui.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.katorabian.mvidecomposetest.presentation.AddContactComponent
import com.katorabian.mvidecomposetest.presentation.ContactListComponent
import com.katorabian.mvidecomposetest.presentation.DefaultRootComponent
import com.katorabian.mvidecomposetest.presentation.EditContactComponent
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
                    is ContactListComponent -> {
                        Contacts(component = instance)
                    }
                    is AddContactComponent -> {
                        AddContact(component = instance)
                    }
                    is EditContactComponent -> {
                        EditContact(component = instance)
                    }
                }
            }
        }
    }
}