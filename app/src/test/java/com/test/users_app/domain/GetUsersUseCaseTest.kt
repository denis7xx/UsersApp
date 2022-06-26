package com.test.users_app.domain

import com.test.users_app.data.UserRepository
import com.test.users_app.data.db.entity.UserEntity
import com.test.users_app.data.db.entity.toDatabase
import com.test.users_app.data.model.UserModel
import com.test.users_app.data.model.toResource
import com.test.users_app.data.network.response.Resource
import com.test.users_app.domain.model.User
import com.test.users_app.domain.model.toDomain
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetUsersUseCaseTest {
    @RelaxedMockK
    private lateinit var repository: UserRepository
    lateinit var getUsersUseCase: GetUsersUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getUsersUseCase = GetUsersUseCase(repository)
    }

    @Test
    fun `when the database doesnt return anything then get values from Api`() = runBlocking {
        val testResource = Resource.Success(listOf(UserModel(1, "Test", "Test", "Test", "Test")))
        coEvery { repository.getUsersFromDatabase() } returns emptyList()
        coEvery { repository.getUsersFromApi() } returns testResource
        getUsersUseCase()
        coVerify(exactly = 1) { repository.getUsersFromApi() }
        coVerify(exactly = 1) { repository.insertUsers(any()) }
    }

    @Test
    fun `when the database return something then get values from database`() = runBlocking {
        val testList = listOf(UserEntity(1, "Test", "Test", "Test", "Test"))
        coEvery { repository.getUsersFromDatabase() } returns testList
        getUsersUseCase()
        coVerify(exactly = 1) { repository.getUsersFromDatabase() }
        coVerify(exactly = 0) { repository.getUsersFromApi() }
    }

}