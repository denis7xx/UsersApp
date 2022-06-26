package com.test.users_app.data.network

import com.test.users_app.data.db.dao.PostDao
import com.test.users_app.data.db.entity.PostEntity
import com.test.users_app.data.model.PostModel
import com.test.users_app.data.network.response.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PostRepository @Inject constructor(private val postService: PostService, private val postDao: PostDao) {
    suspend fun getPostsFromApi(userId: Int): Resource<List<PostModel>> {
        return withContext(Dispatchers.IO){
            postService.getPosts(userId)
        }
    }
    suspend fun getPostsFromDatabase(userId: Int): List<PostEntity>{
        return postDao.getPosts(userId)
    }

    suspend fun insertPosts(posts: List<PostEntity>) {
        postDao.insertPost(posts)
    }
}