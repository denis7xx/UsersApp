package com.test.users_app.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.test.users_app.data.model.PostModel

@Entity(tableName = "post_table", primaryKeys = ["id", "userId"])
data class PostEntity (
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "body") val body: String,
    @ColumnInfo(name = "userId") val userId: Int
)
fun PostModel.toDatabase() = PostEntity(id, title, body, userId)