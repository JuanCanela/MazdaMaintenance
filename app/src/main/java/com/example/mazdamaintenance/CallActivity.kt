package com.example.mazdamaintenance

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.example.mazdamaintenance.Entities.Record
import kotlinx.android.synthetic.main.fragment_call.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CallActivity : AppCompatActivity() {
    private lateinit var database: DatabaseMazda

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MaterialComponents_Light)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_call)
        database = Room.databaseBuilder(
            applicationContext, DatabaseMazda::class.java, "DatabaseMazda"
        ).build()

        val objectoIntent: Intent = intent
        val data  = objectoIntent.getStringExtra("number")
        val id  = objectoIntent.getIntExtra("id",0)
        val fullname  = objectoIntent.getStringExtra("name")
        println(data)
        println(id)
        txtPhone.setText(data)

        btnLlamar.setOnClickListener{
            if (txtPhone.text.toString().isEmpty()){
                txtPhone.error = "Ingrese un Número Telefónico"
            }else{
                requestPermission()

            }
            CoroutineScope(Dispatchers.IO).launch {
                val current = LocalDateTime.now()
                val date: LocalDateTime = current.minusHours(3)
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val fecha_creacion = date.format(formatter)
                val record = Record(0,id.toString(),fullname.toString(),"1","0","0",fecha_creacion)
                database.record().insertRcall(record)
            }
        }
    }

    private fun call(phone: String) {
        startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:$phone")))
    }
    public fun requestPermission() {
        val phone = txtPhone.text.toString()
        //Pedir los permisos en tiempo de ejecución y comprobar el nivel de API Instalado
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            //Identificar si los permisos ya se encuentra habilidados
            when {
                ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED->{
                    call(phone)
                }else->solicictarLanzarPermisos.launch(Manifest.permission.CALL_PHONE)
            }
        }else{
            call(phone)
        }
    }

    private val solicictarLanzarPermisos = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            isGranted->
        val phone = txtPhone.text.toString()
        if (isGranted){
            call(phone)
        }else{
            Toast.makeText(this,"Necesitas habilitar este permiso", Toast.LENGTH_LONG).show()
        }
    }
}