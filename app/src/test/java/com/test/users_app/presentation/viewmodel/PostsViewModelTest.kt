package com.test.users_app.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.test.users_app.data.model.PostModel
import com.test.users_app.data.network.response.Resource
import com.test.users_app.domain.GetUserPostsUseCase
import com.test.users_app.domain.GetUsersUseCase
import com.test.users_app.domain.model.Post
import com.test.users_app.domain.model.User
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PostsViewModelTest {
    @RelaxedMockK
    private lateinit var getUserPostsUseCase: GetUserPostsUseCase
    private lateinit var userPostsViewModel: PostsViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        userPostsViewModel = PostsViewModel(getUserPostsUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `when view_model is created get all users`() = runTest {
        val testResource = Resource.Success(listOf(Post(1, "Test", "Test", 1)))
        coEvery { getUserPostsUseCase(1) } returns testResource
        userPostsViewModel.onCreate(1)
        assert(userPostsViewModel.posts.value == testResource.data)
    }
}