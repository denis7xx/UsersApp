package com.test.users_app.domain

import com.test.users_app.data.UserRepository
import com.test.users_app.data.db.entity.PostEntity
import com.test.users_app.data.db.entity.UserEntity
import com.test.users_app.data.model.PostModel
import com.test.users_app.data.model.UserModel
import com.test.users_app.data.network.PostRepository
import com.test.users_app.data.network.response.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class GetUserPostsUseCaseTest {
    @RelaxedMockK
    private lateinit var repository: PostRepository
    lateinit var getUserPostsUseCase: GetUserPostsUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getUserPostsUseCase = GetUserPostsUseCase(repository)
    }

    @Test
    fun `when the database doesnt return anything then get values from Api`() = runBlocking {
        val testResource = Resource.Success(listOf(PostModel(1, "Test", "Test", 1)))
        coEvery { repository.getPostsFromDatabase(1) } returns emptyList()
        coEvery { repository.getPostsFromApi(1) } returns testResource
        getUserPostsUseCase(1)
        coVerify(exactly = 1) { repository.getPostsFromApi(1) }
        coVerify(exactly = 1) { repository.insertPosts(any()) }
    }

    @Test
    fun `when the database return something then get values from database`() = runBlocking {
        val testList = listOf(PostEntity(1, "Test", "Test", 1))
        coEvery { repository.getPostsFromDatabase(1) } returns testList
        getUserPostsUseCase(1)
        coVerify(exactly = 1) { repository.getPostsFromDatabase(1) }
        coVerify(exactly = 0) { repository.getPostsFromApi(1) }
    }

}