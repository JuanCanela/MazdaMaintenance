package com.example.mazdamaintenance.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Users")
data class Users(
    @PrimaryKey(autoGenerate = true)
    var id_user:Int,
    var nombre:String,
    var apellido:String,
    var telefono:String,
    var email:String,
    var direccion:String,
    var latitud:Double,
    var longitud:Double,
): Serializable