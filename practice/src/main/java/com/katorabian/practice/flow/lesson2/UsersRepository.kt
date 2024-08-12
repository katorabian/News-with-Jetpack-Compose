package com.katorabian.practice.flow.lesson2

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

object UsersRepository {

    private val users = mutableListOf("Nick", "John", "Max")

    suspend fun addUser(user: String) {
        delay(10)
        users.add(user)
    }

    suspend fun loadUsers() = flow {
        while (true) {
            emit(users.toList())
            delay(500)
        }
    }
}
