package com.test.users_app.data.network

import com.test.users_app.data.model.PostModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PostApi {
    @GET("/posts")
    suspend fun getPosts(@Query("userId") userId: Int): Response<List<PostModel>>
}