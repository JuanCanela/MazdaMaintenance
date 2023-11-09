package com.example.mazdamaintenance

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mazdamaintenance.Entities.Record
import com.example.mazdamaintenance.Entities.Users

@Database(entities = [Users::class, Record::class],version=1)
abstract class DatabaseMazda : RoomDatabase() {

    abstract fun users():UsersDao
    abstract fun record():RecordDao

    companion object{
        @Volatile
        private var INSTANCE: com.example.mazdamaintenance.DatabaseMazda? = null

        fun getDatabase(context: Context): com.example.mazdamaintenance.DatabaseMazda {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    com.example.mazdamaintenance.DatabaseMazda::class.java,
                    "DatabaseMazda" ).build()

                INSTANCE = instance

                return instance
            }
        }
    }
}