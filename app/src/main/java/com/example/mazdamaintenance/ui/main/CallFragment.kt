package com.example.tablayoutkotlin.ui.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mazdamaintenance.MainActivity
import com.example.mazdamaintenance.Menu
import com.example.mazdamaintenance.R
import kotlinx.android.synthetic.main.fragment_call.*


class CallFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_call, container, false)

        val title: TextView = view.findViewById(R.id.subtitleTextView)
        title.text = resources.getStringArray(R.array.menu)[1]

        return view

    }


    override fun onResume() {
        super.onResume()
        btnLlamar.setOnClickListener{
            if (txtPhone.text.toString().isEmpty()){
                txtPhone.error = "Ingrese un Número Telefónico"
            }else{
                (activity as Menu?)?.requestPermission()
            }

        }
    }


}