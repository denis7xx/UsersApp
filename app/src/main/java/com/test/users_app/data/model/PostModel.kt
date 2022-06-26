package com.test.users_app.data.model

import com.google.gson.annotations.SerializedName
import com.test.users_app.data.db.entity.PostEntity

data class PostModel(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String,
    @SerializedName("userId") val userId: Int
)
fun PostEntity.toResource() = PostModel(id, title, body, userId)