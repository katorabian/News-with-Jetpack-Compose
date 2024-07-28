package com.katorabian.compose_news.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.katorabian.compose_news.domain.FeedPostItem
import com.katorabian.compose_news.domain.StatisticType

class PostViewModel: ViewModel() {

    private val initialList: List<FeedPostItem> = MutableList(3) { FeedPostItem(it) }

    private val _feedList = MutableLiveData(initialList)
    val feedList: LiveData<List<FeedPostItem>> = _feedList

    @Throws(IllegalStateException::class)
    fun updateCount(post: FeedPostItem, statisticType: StatisticType) {
        val postsToUpdate = feedList.value?.toMutableList()?: throw NullPointerException()

        val postsIterator = postsToUpdate.listIterator()
        while(postsIterator.hasNext()) {
            val currPost = postsIterator.next()
            if (currPost.id == post.id) {

                val oldStatistics = currPost.statistics
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

                val postUpdated = currPost.copy(
                    statistics = newStatistics
                )
                postsIterator.set(postUpdated)
                break
            }
        }

        _feedList.value = postsToUpdate
    }
}