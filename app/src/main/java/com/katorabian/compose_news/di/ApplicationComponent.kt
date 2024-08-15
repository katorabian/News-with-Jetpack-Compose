package com.katorabian.compose_news.di

import android.content.Context
import com.katorabian.compose_news.domain.annotation.Temp
import com.katorabian.compose_news.domain.model.FeedPostItem
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
    ]
)
interface ApplicationComponent {

    @Component.Factory
    interface Factory { // for providing all dependencies we need to create @Component

        fun create(
            @BindsInstance context: Context, //in current case we need just 1 parameter for @Component creation
            @BindsInstance @Temp("Comments screen will not work now") feedPost: FeedPostItem
        ): ApplicationComponent
    }
}

// При создании ApplicationComponent мы передадим Context
// он будет добавлен в граф зависимостей
// и будет передан в DataModule и предоставлен во всех необходимых методах для создания зависимостей