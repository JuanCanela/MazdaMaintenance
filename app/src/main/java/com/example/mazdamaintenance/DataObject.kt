package com.example.mazdamaintenance

import com.example.mazdamaintenance.Entities.Record
import com.example.mazdamaintenance.Entities.Users

object DataObject {

    var listdata = mutableListOf<Record>()

    var listdata1 = mutableListOf<Users>()

    /*
    fun setData(user_id:Int,title: String, priority: String,done:Boolean) {
        listdata.add(Record(0,user_id,title,priority,done))
    }
     */

   fun getAllData1(): List<Record> {
        return listdata
    }

    fun getAllData(): List<Users> {
        return listdata1
    }

    fun getData(pos:Int): Users{
        return listdata1[pos-1]
    }

    fun deleteAll(){
        listdata.clear()
    }


    fun deleteData(pos:Int){
        listdata.removeAt(pos)
    }


    fun updateData(pos: Int,nombre: String, apellido: String,telefono:String,email:String)
    {
        //listdata1[pos].id_user = pos
        //listdata[pos].user_id= user_id
        listdata1[pos].nombre = nombre
        listdata1[pos].apellido = apellido
        listdata1[pos].telefono = telefono
        listdata1[pos].email = email
    }



}