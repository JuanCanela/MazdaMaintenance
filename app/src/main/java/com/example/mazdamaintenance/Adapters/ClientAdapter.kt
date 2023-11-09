package com.example.mazdamaintenance.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mazdamaintenance.Entities.Record
import com.example.mazdamaintenance.Menu
import com.example.mazdamaintenance.R
import com.example.mazdamaintenance.Entities.Users
import kotlinx.android.synthetic.main.fragment_recordcall.view.*
import kotlinx.android.synthetic.main.fragment_recordcall.view.fecha
import kotlinx.android.synthetic.main.fragment_recordcall.view.mylayout
import kotlinx.android.synthetic.main.record_email_view.view.*

class ClientAdapter(var data: List<Record>) : RecyclerView.Adapter<ClientAdapter.viewHolder>() {
    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nombre = itemView.nombre
        var date = itemView.fecha
        var fullname = itemView.nombre
        var layout = itemView.mylayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_recordcall, parent, false)
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
        holder.nombre.text = data[position].fullname
        holder.date.text = data[position].date
        holder.fullname.text =data[position].fullname
        /*
        holder.itemView.setOnClickListener{
            val intent= Intent(holder.itemView.context, Menu::class.java)
            intent.putExtra("number",numero)
            holder.itemView.context.startActivity(intent)
        }
        */

    }

    override fun getItemCount(): Int {
        return data.size

    }
}