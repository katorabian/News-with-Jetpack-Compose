package com.katorabian.compose_news.presentation.screen.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.map.Mapper
import com.katorabian.compose_news.data.network.ApiFactory
import com.katorabian.compose_news.domain.mapper.NewsFeedMapper
import com.katorabian.compose_news.domain.model.FeedPostItem
import com.katorabian.compose_news.domain.model.StatisticType
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class NewsFeedViewModel(application: Application): AndroidViewModel(application) {

    private val initialState = NewsFeedScreenState.Initial

    private val _screenState = MutableLiveData<NewsFeedScreenState>(initialState)
    val screenState: LiveData<NewsFeedScreenState> = _screenState

    private val mapper = NewsFeedMapper()

    init {
        loadRecommendation()
    }

    private fun loadRecommendation() {
        viewModelScope.launch {
            val storage = VKPreferencesKeyValueStorage(getApplication())
            val token = VKAccessToken.restore(storage) ?: return@launch

            val response = ApiFactory.apiService.loadRecommendation(token.accessToken)
            val feed = mapper.mapResponseToPosts(response)
            _screenState.postValue(
                NewsFeedScreenState.Posts(feed)
            )
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