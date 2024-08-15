package com.katorabian.compose_news.presentation.screen.news

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katorabian.compose_news.common.extensions.mergeWith
import com.katorabian.compose_news.domain.annotation.Temp
import com.katorabian.compose_news.domain.model.FeedPostItem
import com.katorabian.compose_news.domain.usecase.ChangeLikeStatusUseCase
import com.katorabian.compose_news.domain.usecase.DeleteRecommendationUseCase
import com.katorabian.compose_news.domain.usecase.GetRecommendationUseCase
import com.katorabian.compose_news.domain.usecase.LoadNextRecommendationsUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsFeedViewModel @Inject constructor(
    getRecommendationsUC: GetRecommendationUseCase,
    private val loadNextDataUC: LoadNextRecommendationsUseCase,
    private val changeLikeStatusUC: ChangeLikeStatusUseCase,
    private val deletePostUC: DeleteRecommendationUseCase
): ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("NewsFeedViewModel", "Exception caught by exception handler")
    }

    private val recommendationsFlow = getRecommendationsUC.get()
    private val loadNextDataFlow = MutableSharedFlow<NewsFeedScreenState>()

    val screenState = recommendationsFlow
        .filter { it.isNotEmpty() }
        .map { NewsFeedScreenState.Posts(posts = it) as NewsFeedScreenState }
        .onStart { emit(NewsFeedScreenState.Loading) }
        .mergeWith(loadNextDataFlow)

    fun loadNextRecommendations() {
        viewModelScope.launch {
            loadNextDataFlow.emit(
                NewsFeedScreenState.Posts(
                    posts = recommendationsFlow.value,
                    nextDataIsLoading = true
                )
            )
            @Temp delay(500)
            loadNextDataUC.load()
        }
    }

    fun changeLikeStatus(feedPostItem: FeedPostItem) {
        viewModelScope.launch(exceptionHandler) {
            changeLikeStatusUC.revert(feedPostItem)
        }
    }

    fun removePostItem(post: FeedPostItem) {
        viewModelScope.launch(exceptionHandler) {
            deletePostUC.delete(post)
        }
    }
}