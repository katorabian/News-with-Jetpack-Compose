package com.katorabian.compose_news.presentation.screen.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.katorabian.compose_news.data.repository.NewsFeedRepository
import com.katorabian.compose_news.domain.model.FeedPostItem
import com.katorabian.compose_news.domain.model.StatisticType
import kotlinx.coroutines.launch

class NewsFeedViewModel(application: Application): AndroidViewModel(application) {

    private val initialState = NewsFeedScreenState.Initial

    private val _screenState = MutableLiveData<NewsFeedScreenState>(initialState)
    val screenState: LiveData<NewsFeedScreenState> = _screenState

    private val repository = NewsFeedRepository(application)
    init {
        loadRecommendations()
    }

    private fun loadRecommendations() {
        viewModelScope.launch {
            val newsFeed = repository.loadRecommendations()
            _screenState.postValue(NewsFeedScreenState.Posts(newsFeed))
        }
    }

    fun loadNextRecommendations() {
        _screenState.value = NewsFeedScreenState.Posts(
            posts = repository.feedPosts,
            nextDataIsLoading = true
        )
        loadRecommendations()
    }

    fun changeLikeStatus(feedPostItem: FeedPostItem) {
        viewModelScope.launch {
            repository.changeLikeStatus(feedPostItem)
            _screenState.value = NewsFeedScreenState.Posts(posts = repository.feedPosts)
        }
    }

    @Throws(IllegalStateException::class)
    fun updateStatisticCount(post: FeedPostItem, statisticType: StatisticType) {
        val currentState = screenState.value
        if (currentState !is NewsFeedScreenState.Posts) return

        val oldPosts = currentState.posts.toMutableList()
        val oldStatistics = post.statistics
        val newStatistics = oldStatistics.toMutableList().apply {
            replaceAll { oldItem ->
                if (oldItem.type == statisticType) {
                    oldItem.copy(
                        count = oldItem.count + 1
                    )
                } else {
                    oldItem
                }
            }
        }

        val newFeedPost = post.copy(statistics = newStatistics)
        val newPosts = oldPosts.apply {
            replaceAll {
                if (it.id == newFeedPost.id)
                    newFeedPost
                else
                    it
            }
        }
        _screenState.value = NewsFeedScreenState.Posts(newPosts)
    }

    fun removePostItem(post: FeedPostItem) {
        val currentState = screenState.value
        if (currentState !is NewsFeedScreenState.Posts) return

        val postsToUpdate = currentState.posts.toMutableList()
        postsToUpdate.removeIf { it.id == post.id }
        _screenState.value = NewsFeedScreenState.Posts(postsToUpdate)
    }
}