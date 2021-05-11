package com.example.android.flowtestapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android.flowtestapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRefreshLayout()

        for (i in provideList()) {
            setText(i)
        }
    }

    private fun setRefreshLayout() {
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun setText(text: String) {
        val prev = binding.textView.text.toString()
        val next = prev + "\n" + text
        binding.textView.text = next
    }

    private fun provideList() =
        listOf("a", "b", "c")
}