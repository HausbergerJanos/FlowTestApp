package com.example.android.flowtestapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.flowtestapp.util.Resource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

    private val _refreshChannel = Channel<Unit>()
    private val refreshTrigger = _refreshChannel.receiveAsFlow()

    private val _textItems = refreshTrigger.flatMapLatest {
        getTextItems()
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)
    val textItems: StateFlow<Resource<String>?>
        get() = _textItems

    fun refresh() {
        viewModelScope.launch {
            _refreshChannel.send(Unit)
        }
    }

    private fun getTextItems(): Flow<Resource<String>> = flow {
        provideList().forEach {
            emit(Resource.Loading(it))
            delay(500)
        }
        emit(Resource.Success())
    }

    private fun provideList(): List<String> {
        return listOf("a", "b", "v")
    }

}