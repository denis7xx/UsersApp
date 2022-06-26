package com.test.users_app.domain.model

import com.test.users_app.data.db.entity.PostEntity
import com.test.users_app.data.model.PostModel

data class Post(
    val id: Int,
    val title: String,
    val body: String,
    val userId: Int
)
fun PostModel.toDomain() = Post(id, title, body, userId)
fun PostEntity.toDomain() = Post(id, title, body, userId)