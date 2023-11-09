package com.example.mazdamaintenance

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Room
import androidx.viewpager2.widget.ViewPager2
import com.example.tablayoutkotlin.ui.main.CollectionAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_sms.*


class MainActivity : AppCompatActivity() {
    private lateinit var database: DatabaseMazda
    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(800)
        setTheme(R.style.Theme_MaterialComponents_Light_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = Room.databaseBuilder(
            applicationContext, DatabaseMazda::class.java, "DatabaseMazda"
        ).build()

        cardSms.setOnClickListener{
            val intent= Intent(this, RecyclerViewContacs::class.java)
            startActivity(intent)
        }
        cardClient.setOnClickListener{
            val intent= Intent(this, Menu::class.java)
            startActivity(intent)
        }
        cardCall.setOnClickListener{
            val intent= Intent(this, CallActivity::class.java)
            startActivity(intent)
        }





    }






}