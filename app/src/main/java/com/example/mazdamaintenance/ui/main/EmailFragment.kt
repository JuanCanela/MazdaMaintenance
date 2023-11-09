package com.example.mazdamaintenance.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mazdamaintenance.R
import kotlinx.android.synthetic.main.fragment_email.*



class EmailFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_email, container, false)

        //val title: TextView = view.findViewById(R.id.subtitleTextView)
        //title.text = resources.getStringArray(R.array.menu)[2]

        return view
    }

    override fun onResume() {
        super.onResume()
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
                    Toast.makeText(requireContext(), "La aplicación de correos no está instalada", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}