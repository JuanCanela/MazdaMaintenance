package com.example.mazdamaintenance.Adapters

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mazdamaintenance.*
import com.example.mazdamaintenance.Entities.Users

import com.example.mazdamaintenance.ui.call.CallFragment
import kotlinx.android.synthetic.main.fragment_recordcall.view.*
import kotlinx.android.synthetic.main.fragment_recordcall.view.mylayout
import kotlinx.android.synthetic.main.fragment_recordcall.view.nombre
import kotlinx.android.synthetic.main.fragments_contacs_view.view.*

class ContactsAdapter(var data: List<Users>) : RecyclerView.Adapter<ContactsAdapter.viewHolder>() {
    private var oldData = emptyList<Users>()
    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nombre = itemView.nombre
        var layout = itemView.mylayout

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragments_contacs_view, parent, false)
        return viewHolder(itemView)

    }



    override fun onBindViewHolder(holder: viewHolder, position: Int) {
/*
        val recordId = data[position].id_record
        val email = data[position].email
        val sms = data[position].sms
        holder.call.text = data[position].call
        holder.userid.text = data[position].user_id
        holder.date.text = data[position].date
 */
        val  numero = data[position].telefono
        val  nombre = data[position].nombre
        val  email = data[position].email
        val apellido = data[position].apellido
        val idUser = data[position].id_user

        var latitud = data[position].latitud
        var longitud = data[position].longitud
        var nombreCompleto = "$nombre $apellido"
        holder.nombre.text = nombreCompleto

/*
        holder.itemView.setOnClickListener{
            val intent= Intent(holder.itemView.context, Menu::class.java)
            intent.putExtra("number",numero)
            holder.itemView.context.startActivity(intent)
        }
 */

        holder.itemView.btnViewCall.setOnClickListener{

            val intent= Intent(holder.itemView.context, CallActivity::class.java)
            intent.putExtra("number",numero)
            intent.putExtra("id",idUser)
            intent.putExtra("name",nombreCompleto)
            holder.itemView.context.startActivity(intent)
        }
        holder.itemView.btnViewSms.setOnClickListener{
            val intent= Intent(holder.itemView.context, SmsActivity::class.java)
            intent.putExtra("number",numero)
            intent.putExtra("id",idUser)
            intent.putExtra("name",nombreCompleto)
            holder.itemView.context.startActivity(intent)
        }
        holder.itemView.btnViewEmail.setOnClickListener{
            val intent= Intent(holder.itemView.context, EmailActivity::class.java)
            intent.putExtra("email",email)
            intent.putExtra("id",idUser)
            intent.putExtra("name",nombreCompleto)
            holder.itemView.context.startActivity(intent)
        }

        holder.itemView.setOnClickListener{
            val intent= Intent(holder.itemView.context, UpdateClient::class.java)
            intent.putExtra("nombre",nombre)
            intent.putExtra("apellido",apellido)
            intent.putExtra("email",email)
            intent.putExtra("number",numero)
            intent.putExtra("id",idUser)
            holder.itemView.context.startActivity(intent)
        }
        holder.itemView.btnGps.setOnClickListener{
            val intent= Intent(holder.itemView.context, GpsActivity::class.java)
            intent.putExtra("latitud",latitud)
            intent.putExtra("longitud",longitud)
            intent.putExtra("nombre",nombre)
            intent.putExtra("apellido",apellido)
            holder.itemView.context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return data.size

    }
    fun setData1(newData: List<Users>){
        oldData = newData
        notifyDataSetChanged()
    }
}