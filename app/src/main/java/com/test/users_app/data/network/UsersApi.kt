package com.test.users_app.data.network

import com.test.users_app.data.model.UserModel
import retrofit2.Response
import retrofit2.http.GET

interface UsersApi {
    @GET("/users")
    suspend fun getAllUsers(): Response<List<UserModel>>
}