package com.test.users_app.data

import com.test.users_app.data.db.dao.UserDao
import com.test.users_app.data.db.entity.UserEntity
import com.test.users_app.data.model.UserModel
import com.test.users_app.data.network.UserService
import com.test.users_app.data.network.response.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(private val userService: UserService, private val userDao: UserDao){
    suspend fun getUsersFromApi(): Resource<List<UserModel>>{
        return withContext(Dispatchers.IO){
            userService.getUsers()
        }
    }
    suspend fun getUsersFromDatabase(): List<UserEntity>{
        return userDao.getUsers()
    }

    suspend fun insertUsers(users: List<UserEntity>) {
        userDao.insertUsers(users)
    }

}