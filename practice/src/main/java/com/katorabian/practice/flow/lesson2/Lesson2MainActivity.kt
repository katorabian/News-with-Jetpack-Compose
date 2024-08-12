package com.katorabian.practice.flow.lesson2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.katorabian.practice.databinding.ActivityMainBinding

class Lesson2MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.buttonUsersActivity.setOnClickListener {
            startActivity(UsersActivity.newIntent(this))
        }
    }
}
