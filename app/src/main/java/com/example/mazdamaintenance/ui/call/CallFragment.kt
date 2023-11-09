package com.example.mazdamaintenance.ui.call

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mazdamaintenance.*
import com.example.mazdamaintenance.Entities.Record
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat
import ir.mirrajabi.searchdialog.core.SearchResultListener
import kotlinx.android.synthetic.main.fragment_call.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CallFragment : Fragment() {

  private lateinit var callViewModel: CallViewModel


  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    callViewModel = ViewModelProvider(this).get(CallViewModel::class.java)
    val root = inflater.inflate(R.layout.fragment_call, container, false)

    if (arguments != null){
      val numFragment = requireArguments().getInt("number")
      val et:EditText = requireView().findViewById(R.id.txtPhone)
      et.setText(numFragment)
    }

    return root
  }
  @RequiresApi(Build.VERSION_CODES.O)
  override fun onResume() {
    super.onResume()
    doAsync {
      val database = DatabaseMazda.getDatabase(requireContext().applicationContext)


      btnLlamar.setOnClickListener{
        if (txtPhone.text.toString().isEmpty()){
          txtPhone.error = "Ingrese un Número Telefónico"
        }else{
          (activity as Menu?)?.requestPermission()

        }
        CoroutineScope(Dispatchers.IO).launch {
          val current = LocalDateTime.now()
          val date: LocalDateTime = current.minusHours(3)
          val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
          val fecha_creacion = date.format(formatter)
          val record = Record(0,"2","XXXX","1","0","0",fecha_creacion)
          database.record().insertRcall(record)
        }
      }
    }

    btnSearchC.setOnClickListener{

      SimpleSearchDialogCompat(requireContext(),"Buscar","Ingresa un nombre de cliente",null,
        initData(), SearchResultListener{ baseSearchDialogCompat, item, position ->
          Toast.makeText(requireContext(),item.title,Toast.LENGTH_LONG).show()
          txtPhone.setText(item.title)
          baseSearchDialogCompat.dismiss()
        }).show()

    }

  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    if (arguments != null){
      val numFragment = requireArguments().getInt("number")
      val et:EditText = requireView().findViewById(R.id.txtPhone)
      et.setText(numFragment)
    }
  }

  private fun initData(): ArrayList<SearchModel>? {
     val intent : Intent = Intent()

     val items = ArrayList<SearchModel>()
    /*
       intent.hasExtra("usuarios")
      val usuarios = intent.extras?.getSerializable("Users") as Users
      val email = usuarios.email
      println(email)
  */


      items.add(SearchModel("Juan Manuel Canela"))
      items.add(SearchModel("Itza Noheli Quintana"))
      items.add(SearchModel("Manolo Ortiz"))
      items.add(SearchModel("Mirtha Sac Nicte"))
      return items




  }
}