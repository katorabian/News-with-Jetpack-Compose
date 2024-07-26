package com.katorabian.compose_news.other

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

    fun changeFollowedStatus(item: InstagramItem) {
        val modifiedList = _models.value?.toMutableList()
            ?: throw NullPointerException("InstagramViewModel::changeFollowedStatus")

        modifiedList.replaceAll {
            if (it == item) {
                it.copy(isFollowed = !it.isFollowed)
            } else {
                it
            }
        }
        _models.value = modifiedList
    }
}