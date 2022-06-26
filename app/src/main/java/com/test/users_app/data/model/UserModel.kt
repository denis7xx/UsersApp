package com.test.users_app.data.model

import com.google.gson.annotations.SerializedName
import com.test.users_app.data.db.entity.UserEntity

data class UserModel (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: String
)
fun UserEntity.toResource() = UserModel(id, name, username, email, phone)