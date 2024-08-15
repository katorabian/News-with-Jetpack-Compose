package com.katorabian.compose_news.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component
interface ApplicationComponent {

    @Component.Factory
    interface Factory { // for providing all dependencies we need to create @Component

        fun create(
            @BindsInstance context: Context //in current case we need just 1 parameter for @Component creation
        ): ApplicationComponent
    }
}

// При создании ApplicationComponent мы передадим Context
// он будет добавлен в граф зависимостей
// и будет передан в DataModule и предоставлен во всех необходимых методах для создания зависимостей