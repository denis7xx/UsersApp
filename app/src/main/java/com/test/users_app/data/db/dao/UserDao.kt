package com.test.users_app.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.users_app.data.db.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM user_table ORDER BY id ASC")
    suspend fun getUsers(): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(quotes: List<UserEntity>)

    @Query("DELETE FROM user_table")
    suspend fun deleteUsers()
}