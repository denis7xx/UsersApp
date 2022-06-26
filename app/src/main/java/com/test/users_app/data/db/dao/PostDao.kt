package com.test.users_app.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.users_app.data.db.entity.PostEntity

@Dao
interface PostDao {
    @Query("SELECT * FROM post_table WHERE userId = :userId ORDER BY id ASC ")
    suspend fun getPosts(userId: Int): List<PostEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(quotes: List<PostEntity>)

    @Query("DELETE FROM post_table")
    suspend fun deletePost()
}