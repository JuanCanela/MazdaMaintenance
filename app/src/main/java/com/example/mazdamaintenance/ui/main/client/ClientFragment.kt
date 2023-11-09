package com.example.mazdamaintenance.ui.main.client

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.mazdamaintenance.*
import com.example.mazdamaintenance.Entities.Users
import kotlinx.android.synthetic.main.fragment_client.*
import kotlinx.android.synthetic.main.fragment_email.*
import kotlinx.android.synthetic.main.fragment_sms.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class ClientFragment : Fragment() {
  private lateinit var tablausuario: List<Users>
  private lateinit var clientViewModel: ClientViewModel
  @RequiresApi(Build.VERSION_CODES.O)
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    //clientViewModel = ViewModelProvider(this).get(ClientViewModel::class.java)
    val root = inflater.inflate(R.layout.fragment_client, container, false)

    doAsync {
      val database = DatabaseMazda.getDatabase(requireContext().applicationContext)

      var idUser: Int? = null
      val intent : Intent = Intent()
      if (intent.hasExtra("usuarios")) {
        val usuarios = intent.extras?.getSerializable("Users") as Users

        txtCEmail.setText(usuarios.email)
        txtNombre.setText(usuarios.nombre)
        txtApellido.setText(usuarios.apellido)
        txtCPhone.setText(usuarios.telefono)


        idUser = usuarios.id_user

      }

      uiThread {
        btn_Registrar.setOnClickListener{
          val msjSuccess = Toast.makeText(requireContext().applicationContext,"Registro Éxitoso 👍", Toast.LENGTH_SHORT)

          if (txtNombre.text.toString().isEmpty()){
            txtNombre.error = "Escriba un nombre, por favor"
          }else if (txtApellido.text.toString().isEmpty()){
            txtApellido.error = "Escriba un apellido, por favor"
          }else if (txtCPhone.text.toString().isEmpty()){
            txtCPhone.error = "Escriba un número telefónico, por favor"
          }else if (txtCEmail.text.toString().isEmpty()){
            txtCEmail.error = "Escriba un correo electrónico, por favor"
          }else{
            CoroutineScope(Dispatchers.IO).launch {

              var nombre: String = txtNombre.text.toString()
              var apellido :String = txtApellido.text.toString()
              var email :String = txtCEmail.text.toString()
              var phone :String = txtCPhone.text.toString()
              var direccion:String = txtAddress.text.toString()
              var latitud:String = txtLat.text.toString()
              var lat:Double = latitud.toDouble()
              var longitud:String = txtLng.text.toString()
              var lng:Double = longitud.toDouble()
              val usuario = Users(0,nombre,apellido,phone,email,direccion,lat,lng)



              if (idUser != null){
                CoroutineScope(Dispatchers.IO).launch {
                  usuario.id_user = idUser
                  database.users().update(usuario)
                  //this.finish()
                }
              }else
              {
                CoroutineScope(Dispatchers.IO).launch {
                  database.users().insertAll(usuario)
                  txtNombre.setText("")
                  txtApellido.setText("")
                  txtCPhone.setText("")
                  txtCEmail.setText("")
                  txtAddress.setText("")
                  txtLat.setText("")
                  txtLng.setText("")
                  txtNombre.focusable
                  msjSuccess.show()
                  Thread.sleep(800)
                  //this@Register.finish()
                  //startActivity(Intent(requireContext(),CreateCard::class.java))

                }
              }

            }
          }
        }

      }

    }
    return root
  }


  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

  }

  override fun onResume() {
    super.onResume()

  }
}