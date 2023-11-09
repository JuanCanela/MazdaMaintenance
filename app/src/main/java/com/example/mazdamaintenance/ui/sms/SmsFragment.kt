package com.example.mazdamaintenance.ui.sms

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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mazdamaintenance.R
import kotlinx.android.synthetic.main.fragment_sms.*

class SmsFragment : Fragment() {

  private lateinit var smsViewModel: SmsViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    smsViewModel =
            ViewModelProvider(this).get(SmsViewModel::class.java)
    val root = inflater.inflate(R.layout.fragment_sms, container, false)
    return root
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