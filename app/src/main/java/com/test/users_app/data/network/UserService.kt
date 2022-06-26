package com.test.users_app.data.network

import com.test.users_app.data.model.UserModel
import com.test.users_app.data.network.response.Resource
import javax.inject.Inject

class UserService @Inject constructor(private val usersApi: UsersApi) {
    suspend fun getUsers(): Resource<List<UserModel>> {
        val response = usersApi.getAllUsers()
        val users = response.body()
        return try {
            if (response.isSuccessful && users != null) {
                Resource.Success(users)
            } else {
                Resource.Error("Network error.")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error.")
        }
    }
}