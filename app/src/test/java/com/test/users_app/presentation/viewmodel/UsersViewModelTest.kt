package com.test.users_app.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.test.users_app.data.model.UserModel
import com.test.users_app.data.network.response.Resource
import com.test.users_app.domain.GetUsersUseCase
import com.test.users_app.domain.model.User
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UsersViewModelTest {
    @RelaxedMockK
    private lateinit var getUsersUseCase: GetUsersUseCase
    private lateinit var usersViewModel: UsersViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        usersViewModel = UsersViewModel(getUsersUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `when view_model is created get all users`() = runTest {
        val testResource = Resource.Success(listOf(User(1, "Test", "Test", "Test", "Test")))
        coEvery { getUsersUseCase() } returns testResource
        usersViewModel.onCreate()
        assert(usersViewModel.users.value == testResource.data)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }
}