package com.katorabian.compose_news.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.katorabian.compose_news.domain.model.FeedPostItem
import com.katorabian.compose_news.domain.model.StatisticType
import com.katorabian.compose_news.presentation.model.HomeScreenState

class MainViewModel: ViewModel() {

    private val sourceList: List<FeedPostItem> = MutableList(10) { FeedPostItem(it) }
    private val initialState: HomeScreenState.Posts = HomeScreenState.Posts(posts = sourceList)

    private val _screenState = MutableLiveData<HomeScreenState>(initialState)
    val screenState: LiveData<HomeScreenState> = _screenState
    
    @Throws(IllegalStateException::class)
    fun updateStatisticCount(post: FeedPostItem, statisticType: StatisticType) {
        val currentState = screenState.value
        if (currentState !is HomeScreenState.Posts) return

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
        _screenState.value = HomeScreenState.Posts(newPosts)
    }

    fun removePostItem(post: FeedPostItem) {
        val currentState = screenState.value
        if (currentState !is HomeScreenState.Posts) return

        val postsToUpdate = currentState.posts.toMutableList()
        postsToUpdate.removeIf { it.id == post.id }
        _screenState.value = HomeScreenState.Posts(postsToUpdate)
    }
}