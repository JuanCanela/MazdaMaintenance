package com.example.mazdamaintenance

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.room.Room
import com.example.mazdamaintenance.Entities.Record
import kotlinx.android.synthetic.main.fragment_email.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class EmailActivity : AppCompatActivity() {
    private lateinit var database: DatabaseMazda
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MaterialComponents_Light)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_email)

        database = Room.databaseBuilder(
            applicationContext, DatabaseMazda::class.java, "DatabaseMazda"
        ).build()

        val objectoIntent: Intent = intent
        val email  = objectoIntent.getStringExtra("email")
        val id  = objectoIntent.getIntExtra("id",0)
        val fullname  = objectoIntent.getStringExtra("name")
        println(email)
        txtEmail.setText(email)
        txtAsunto.focusable


        btnEnviarEmail.setOnClickListener{
            var asunto = txtAsunto.text.toString()
            var mensaje = txtMensaje.text.toString()
            var email = txtEmail.text.toString()
            val correos = email.split(",".toRegex()).toTypedArray()
            if (email.isEmpty()){
                txtEmail.error = "Escriba un correo electrónico, por favor"
            }else if (asunto.isEmpty()){
                txtAsunto.error = "Escriba un asunto, por favor"
            }else if (mensaje.isEmpty()){
                txtMensaje.error = "Escriba un mensaje, por favor"
            }else{
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:")
                    putExtra(Intent.EXTRA_EMAIL,correos)
                    putExtra(Intent.EXTRA_SUBJECT,asunto)
                    putExtra(Intent.EXTRA_TEXT,mensaje)
                }
                try {
                    //Inicia el email
                    startActivity(Intent.createChooser(intent, "Choose Email Client..."))
                }
                catch (e: Exception){
                    Toast.makeText(this, "La aplicación de correos no está instalada", Toast.LENGTH_LONG).show()
                }
            }
            CoroutineScope(Dispatchers.IO).launch {
                val current = LocalDateTime.now()
                val date: LocalDateTime = current.minusHours(3)
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val fecha_creacion = date.format(formatter)
                val record = Record(0,id.toString(),fullname.toString(),"0","0","1",fecha_creacion)
                database.record().insertRcall(record)
            }
        }
    }
}