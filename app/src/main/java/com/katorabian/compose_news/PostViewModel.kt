package com.katorabian.compose_news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.katorabian.compose_news.domain.FeedPostItem
import com.katorabian.compose_news.domain.StatisticItem

class PostViewModel: ViewModel() {

    private val _feedPost = MutableLiveData(FeedPostItem())
    val feedPost: LiveData<FeedPostItem> = _feedPost

    @Throws(IllegalStateException::class)
    fun updateCount(item: StatisticItem) {
        val oldStatistics = feedPost.value?.statistics?: throw IllegalStateException()
        val newStatistics = oldStatistics.toMutableList().apply {
            replaceAll { oldItem ->
                if (oldItem.type == item.type) {
                    oldItem.copy(
                        count = oldItem.count + 1
                    )
                } else {
                    oldItem
                }
            }
        }
        _feedPost.value = feedPost.value?.copy(
            statistics = newStatistics
        )
    }
}