package com.example.mazdamaintenance

import androidx.room.*
import com.example.mazdamaintenance.Entities.Record
import com.example.mazdamaintenance.Entities.Users
@Dao
interface RecordDao {
    @Insert
    fun insertRcall(vararg record: Record)



    @Query("Delete from Record")
    suspend fun deleteAll()
/*
    @Query("SELECT * FROM Record")
    fun getRcall():List<Record>
 */

    @Query("SELECT * FROM Record WHERE call = '1'")
     fun getRcall():List<Record>

    @Query("SELECT * FROM Record WHERE sms = '1'")
    fun getRsms():List<Record>

    @Query("SELECT * FROM Record WHERE email = '1'")
    fun getRemail():List<Record>
}