package com.katorabian.compose_news.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.katorabian.compose_news.domain.model.FeedPostItem
import com.katorabian.compose_news.domain.model.StatisticType

class MainViewModel: ViewModel() {

    private val sourceList: List<FeedPostItem> = MutableList(10) { FeedPostItem(it) }

    private val _feedPosts = MutableLiveData(sourceList)
    val feedPosts: LiveData<List<FeedPostItem>> = _feedPosts
    
    @Throws(IllegalStateException::class)
    fun updateCount(post: FeedPostItem, statisticType: StatisticType) {
        val oldPosts = feedPosts.value?.toMutableList()?: throw NullPointerException()
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
        _feedPosts.value = oldPosts.apply {
            replaceAll {
                if (it.id == newFeedPost.id)
                    newFeedPost
                else
                    it
            }
        }
    }

    fun removeItem(post: FeedPostItem) {
        val oldPosts = feedPosts.value?.toMutableList()?: throw NullPointerException()
        oldPosts.removeIf { it.id == post.id }
        _feedPosts.value = oldPosts
    }
}