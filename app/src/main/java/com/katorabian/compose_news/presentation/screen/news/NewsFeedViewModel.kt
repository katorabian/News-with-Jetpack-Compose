package com.katorabian.compose_news.presentation.screen.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.katorabian.compose_news.data.repository.NewsFeedRepository
import com.katorabian.compose_news.domain.annotation.Temp
import com.katorabian.compose_news.domain.model.FeedPostItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class NewsFeedViewModel(application: Application): AndroidViewModel(application) {

    private val initialState = NewsFeedScreenState.Initial

    private val _screenState = MutableLiveData<NewsFeedScreenState>(initialState)
    val screenState: LiveData<NewsFeedScreenState> = _screenState

    private val repository = NewsFeedRepository(application)
    init {
        _screenState.value = NewsFeedScreenState.Loading
        loadRecommendations()
    }

    private fun loadRecommendations() {
        viewModelScope.launch {
            repository.recommendations
                .filter { it.isNotEmpty() }
                .collect {
                    _screenState.value = NewsFeedScreenState.Posts(posts = it)
                }
        }
    }

    fun loadNextRecommendations() {
        _screenState.value = NewsFeedScreenState.Posts(
            posts = repository.feedPosts,
            nextDataIsLoading = true
        )
        viewModelScope.launch {
            @Temp delay(500)
            repository.loadNextData()
        }
    }

    fun changeLikeStatus(feedPostItem: FeedPostItem) {
        viewModelScope.launch {
            repository.changeLikeStatus(feedPostItem)
            _screenState.value = NewsFeedScreenState.Posts(posts = repository.feedPosts)
        }
    }

    fun removePostItem(post: FeedPostItem) {
        viewModelScope.launch {
            repository.deletePost(post)
            _screenState.value = NewsFeedScreenState.Posts(posts = repository.feedPosts)
        }
    }
}