package com.example.tablayoutkotlin.ui.main


import android.os.Build
import android.os.Bundle
import android.telephony.SmsManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.mazdamaintenance.R

import kotlinx.android.synthetic.main.fragment_sms.*


class SmsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_sms, container, false)

        val title: TextView = view.findViewById(R.id.subtitleTextView)
        title.text = resources.getStringArray(R.array.menu)[0]

        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        btnEnviarSMS.setOnClickListener{
            if (txtPhone.text.toString().isEmpty()){
                txtPhone.error = "Escriba un Número Telefónico, por favor"
            }else if (txtMensaje.text.toString().isEmpty()){
                txtMensaje.error = "Escriba un mensaje, por favor"
            }else{
                var sms = SmsManager.getDefault()
                sms.sendTextMessage(txtPhone.text.toString(),"ME",txtMensaje.text.toString(),null,null)
                Toast.makeText(requireContext(),"Mensaje enviado", Toast.LENGTH_LONG).show()
                txtPhone.setText("")
                txtMensaje.setText("")
                txtPhone.focusable
            }
        }
    }

}