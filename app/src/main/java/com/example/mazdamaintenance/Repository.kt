package com.example.mazdamaintenance

import com.example.mazdamaintenance.Entities.Users
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class Repository @Inject constructor(
    private val userDao: UsersDao
) {

    fun readData(): Flow<List<Users>> {
        return userDao.readData()
    }

    suspend fun insertData(user: Users) {
        userDao.insertAll(user)
    }

    fun searchDatabase(searchQuery: String): Flow<List<Users>> {
        return userDao.searchDatabase(searchQuery)
    }

}