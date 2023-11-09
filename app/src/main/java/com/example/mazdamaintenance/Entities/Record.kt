package com.example.mazdamaintenance.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Record")
class Record(
    @PrimaryKey(autoGenerate = true)
    var id_record:Int,
    var user_id:String,
    var fullname:String,
    var call:String,
    var sms:String,
    var email:String,
    val date:String,
)