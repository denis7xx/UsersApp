package com.test.users_app.data.network

import com.test.users_app.data.model.PostModel
import com.test.users_app.data.network.response.Resource
import javax.inject.Inject

class PostService @Inject constructor(private val postApi: PostApi) {
    suspend fun getPosts(userId: Int): Resource<List<PostModel>> {
        val response = postApi.getPosts(userId)
        val post = response.body()
        return try {
            if (response.isSuccessful && post != null) {
                Resource.Success(post)
            } else {
                Resource.Error("An error occured")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occured")
        }
    }
}