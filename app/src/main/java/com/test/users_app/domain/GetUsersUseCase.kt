package com.test.users_app.domain

import com.test.users_app.data.UserRepository
import com.test.users_app.data.db.entity.toDatabase
import com.test.users_app.data.network.response.Resource
import com.test.users_app.domain.model.User
import com.test.users_app.domain.model.toDomain
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val repository: UserRepository){
    suspend operator fun invoke() :  Resource<List<User>>? {
       val users = repository.getUsersFromDatabase()
        return if(users.isNotEmpty()) {
            Resource.Success(users.map { it.toDomain() })
        } else {
            when (val response = repository.getUsersFromApi()) {
                is Resource.Success -> {
                    if (response.data!!.isNotEmpty()) {
                        repository.insertUsers(response.data.map { it.toDatabase() })
                    }
                    Resource.Success(response.data.map { it.toDomain() })
                }
                else -> {
                    Resource.Error(response.message.toString())
                }
            }
        }
    }
}