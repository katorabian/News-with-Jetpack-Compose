package com.katorabian.compose_news.presentation.screen.samples.instagram

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class InstagramViewModel: ViewModel() {

    private val initialList = mutableListOf<InstagramItem>().apply {
        repeat(500) {
            add(
                InstagramItem(
                    id = it,
                    title = "Title: $it",
                    isFollowed = Random.nextBoolean()
                )
            )
        }
    }

    private val _models = MutableLiveData<List<InstagramItem>>(initialList)
    val models: LiveData<List<InstagramItem>> = _models

    @Throws(NullPointerException::class)
    fun changeFollowedStatus(item: InstagramItem) {
        val modifiedList = _models.value?.toMutableList()
            ?: throw NullPointerException("InstagramViewModel::changeFollowedStatus")

        val iterator = modifiedList.listIterator()
        while (iterator.hasNext()) {
            val curr = iterator.next()
            if (curr == item) {
                iterator.set(
                    curr.copy(
                        isFollowed = !curr.isFollowed
                    )
                )
                break
            }
        }
        _models.value = modifiedList
    }

    fun removeItem(item: InstagramItem) {
        val modifiedList = _models.value?.toMutableList()
            ?: throw NullPointerException("InstagramViewModel::changeFollowedStatus")

        val iterator = modifiedList.listIterator()
        while (iterator.hasNext()) {
            val curr = iterator.next()
            if (curr == item) {
                iterator.remove()
                break
            }
        }
        _models.value = modifiedList
    }
}