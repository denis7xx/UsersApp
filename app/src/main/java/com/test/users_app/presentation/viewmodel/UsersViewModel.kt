package com.test.users_app.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.users_app.data.network.response.Resource
import com.test.users_app.domain.GetUsersUseCase
import com.test.users_app.domain.model.User
import com.test.users_app.util.ExpressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(private val getUsersUseCase: GetUsersUseCase) :
    ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()
    val users = MutableLiveData<List<User>>()
    fun onCreate() {
        ExpressoIdlingResource.increment()
        viewModelScope.launch {
            isLoading.postValue(true)
            val response = getUsersUseCase()
            isLoading.postValue(false)
            when (response) {
                is Resource.Success -> {
                    users.postValue(response.data ?: emptyList())
                }
                else -> {
                    message.postValue(response?.message?.toString())
                }
            }
            ExpressoIdlingResource.decrement()
        }
    }
}