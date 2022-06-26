package com.test.users_app.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.users_app.data.network.response.Resource
import com.test.users_app.domain.GetUserPostsUseCase
import com.test.users_app.domain.model.Post
import com.test.users_app.util.ExpressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(private val getUserPostsUseCase: GetUserPostsUseCase) :
    ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()
    val posts = MutableLiveData<List<Post>>()
    fun onCreate(userId: Int) {
        ExpressoIdlingResource.increment()
        viewModelScope.launch {
            isLoading.postValue(true)
            val response = getUserPostsUseCase(userId)
            isLoading.postValue(false)
            when (response) {
                is Resource.Success -> {
                    posts.postValue(response.data ?: emptyList())
                }
                else -> {
                    message.postValue(response?.message?.toString())
                }
            }
            ExpressoIdlingResource.decrement()
        }
    }
}