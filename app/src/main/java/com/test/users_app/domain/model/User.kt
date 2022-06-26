package com.test.users_app.domain.model

import com.test.users_app.data.db.entity.UserEntity
import com.test.users_app.data.model.UserModel

data class User(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val phone: String
)
fun UserModel.toDomain() = User(id, name, username, email, phone)
fun UserEntity.toDomain() = User(id, name, username, email, phone)