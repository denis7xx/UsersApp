package com.test.users_app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.test.users_app.data.db.dao.PostDao
import com.test.users_app.data.db.dao.UserDao
import com.test.users_app.data.db.entity.PostEntity
import com.test.users_app.data.db.entity.UserEntity

@Database(entities = [UserEntity::class, PostEntity::class], version = 1, exportSchema = false)
abstract class UsersDatabase: RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getPostDao(): PostDao
}