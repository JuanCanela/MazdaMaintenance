package com.example.mazdamaintenance

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mazdamaintenance.Entities.Users
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {
    @Query("SELECT * FROM Users")
    fun getAll(): LiveData<List<Users>>

    @Query("UPDATE Users SET nombre = :name ,apellido = :lastname,telefono = :number,email = :email  WHERE id_user = :id")
     suspend fun updateClient(id: Int,name:String,lastname:String,number:String,email:String)

    @Query("SELECT * FROM Users")
    fun getUser(): List<Users>

    @Query("SELECT * FROM  Users WHERE id_user = :id")
    fun get(id:Int): LiveData<Users>

    @Query("SELECT * FROM Users WHERE nombre LIKE :searchQuery OR apellido LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): Flow<List<Users>>

    @Query("SELECT * FROM Users ORDER BY apellido ASC")
    fun readData(): Flow<List<Users>>

    //@Query("SELECT * FROM  Users WHERE username = :username ")
   // fun get(username:String):List<Users>

   // @Query("SELECT * FROM Users Where password = :pass")
   // fun getpass(pass:String):List<Users>
    @Insert
    fun insertAll(vararg users: Users)

    @Update
    suspend fun update( users: Users)


}