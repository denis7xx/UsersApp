package com.test.users_app.domain

import com.test.users_app.data.db.entity.toDatabase
import com.test.users_app.data.network.PostRepository
import com.test.users_app.data.network.response.Resource
import com.test.users_app.domain.model.Post
import com.test.users_app.domain.model.toDomain
import javax.inject.Inject

class GetUserPostsUseCase @Inject constructor(private val repository: PostRepository) {
    suspend operator fun invoke(userId: Int): Resource<List<Post>>? {
        val posts = repository.getPostsFromDatabase(userId)
        return if (posts.isNotEmpty()) {
            Resource.Success(posts.map { it.toDomain() })
        } else {
            when (val response = repository.getPostsFromApi(userId)) {
                is Resource.Success -> {
                    if (response.data!!.isNotEmpty()) {
                        repository.insertPosts(response.data.map { it.toDatabase() })
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