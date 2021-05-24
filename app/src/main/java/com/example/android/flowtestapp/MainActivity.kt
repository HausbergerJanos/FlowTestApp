package com.example.android.flowtestapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.android.flowtestapp.databinding.ActivityMainBinding
import com.example.android.flowtestapp.util.Resource
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(MainActivityViewModel::class.java)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRefreshLayout()

        collectFlow()
    }

    private fun collectFlow() {
        lifecycleScope.launchWhenStarted {
            viewModel.textItems.collect { resource ->
                binding.swipeRefresh.isRefreshing = resource is Resource.Loading
                resource?.data?.let {
                    setText(it)
                }
            }
        }
    }

    private fun setRefreshLayout() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun setText(text: String) {
        val prev = binding.textView.text.toString()
        val next = prev + "\n" + text
        binding.textView.text = next
    }
}