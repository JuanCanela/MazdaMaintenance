package com.example.mazdamaintenance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.mazdamaintenance.Entities.Users
import kotlinx.android.synthetic.main.activity_update_client.*
import kotlinx.android.synthetic.main.fragment_client.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UpdateClient : AppCompatActivity() {
    private lateinit var database: DatabaseMazda
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MaterialComponents_Light)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_client)

        database = Room.databaseBuilder(
            applicationContext, DatabaseMazda::class.java, "DatabaseMazda"
        ).build()

        val objectoIntent: Intent = intent
/*
        val numero  = objectoIntent.getStringExtra("number")
        val nombre  = objectoIntent.getStringExtra("nombre")
        val apellido  = objectoIntent.getStringExtra("apellido")
        val email  = objectoIntent.getStringExtra("email")


 */

        val id = objectoIntent.getIntExtra("id",0)
        val pos = id
        println(id)



        val nombre = DataObject.getData(pos).nombre
        val apellido = DataObject.getData(pos).apellido
        val numero = DataObject.getData(pos).telefono
        val email = DataObject.getData(pos).email
        println(nombre)
        println(apellido)
        println(email)

        txtUNombre.setText(nombre)
        txtUApellido.setText(apellido)
        txtUPhone.setText(numero)
        txtUEmail.setText(email)
        var direccion = "Calle Manzana 345 Col Ramirez"
        var lat = 18.139012
        var lng = -94.485191

        val usuario = Users(pos,nombre.toString(),apellido.toString(),numero.toString(),email.toString(),direccion,lat,lng)

        btn_Actualizar.setOnClickListener{
            DataObject.updateData(pos,nombre.toString(),apellido.toString(),numero.toString(),email.toString())
            GlobalScope.launch {
               // database.users().updateClient(Users(id,nombre.toString(),apellido.toString(),numero.toString(),email.toString()))
               // DataObject.listdata1 = database.users().getUser() as MutableList<Users>
                //DataObject.updateData(pos,nombre.toString(),apellido.toString(),numero.toString(),email.toString())
                //usuario.id_user = pos
                database.users().updateClient(pos,nombre.toString(),apellido.toString(),numero.toString(),email.toString())
                DataObject.listdata1 = database.users().getUser() as MutableList<Users>
            }


            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }

    }
}