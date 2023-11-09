package com.example.mazdamaintenance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.mazdamaintenance.Entities.Users
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {
    private lateinit var database: DatabaseMazda
    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(1000)
        setTheme(R.style.Theme_AppCompat_Light_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splas_screen)
        database = Room.databaseBuilder(
            applicationContext, DatabaseMazda::class.java, "DatabaseMazda"
        ).build()
        GlobalScope.launch {
           // DataObject.listdata = database.record().getRcall() as MutableList<Record>
            DataObject.listdata1 = database.users().getUser() as MutableList<Users>
        }

    }
}