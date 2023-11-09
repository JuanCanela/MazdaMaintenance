package com.example.mazdamaintenance
import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import android.view.Menu
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.mazdamaintenance.Adapters.ClientAdapter
import com.example.mazdamaintenance.Adapters.ContactsAdapter
import com.example.mazdamaintenance.ui.ContacsViewModel
import com.example.mazdamaintenance.ui.records.RecordViewModel
import kotlinx.android.synthetic.main.fragment_call.txtPhone
import kotlinx.android.synthetic.main.fragment_recordcallview.recycler_view
import kotlinx.android.synthetic.main.fragment_sms.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import androidx.annotation.RequiresApi
import com.example.mazdamaintenance.databinding.FragmentsContacsViewBinding


class Menu : AppCompatActivity(), SearchView.OnQueryTextListener {
    private lateinit var binding: FragmentsContacsViewBinding
    private lateinit var bindingMain: com.example.mazdamaintenance.Menu
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var database: DatabaseMazda
    private val mainViewModel: ContacsViewModel by viewModels()
    private val myAdapter: ContactsAdapter by lazy { ContactsAdapter(DataObject.getAllData()) }
    lateinit var recyclerViewAdapter: ClientAdapter
    private val fragmentViewModel: RecordViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
       // Thread.sleep(800)
        setTheme(R.style.Theme_MaterialComponents_Light_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        database = Room.databaseBuilder(
            this, DatabaseMazda::class.java, "DatabaseMazda"
        ).build()


       doAsync {

/*
          if (data != null){
              val args = Bundle()
              args.putString("text", data)

              val newFragment = ContactsFragment()
              newFragment.arguments = args

              val fm: FragmentManager = supportFragmentManager
              val fragmentTransaction: FragmentTransaction = fm.beginTransaction()
              fragmentTransaction.replace(
                  R.id.home,
                  newFragment
              )
              fragmentTransaction.commit()
          }

 */
           uiThread {

           }

       }

       // supportFragmentManager.beginTransaction().replace(R.id.nav_call,CallFragment()).commit()
/*
        val bundle = Bundle()
        bundle.putInt("number",0)
        val transaccion = supportFragmentManager.beginTransaction()
        val fragment1 = CallFragment()
        fragment1.arguments = bundle
        transaccion.replace(R.id.nav_call,fragment1)
        transaccion.addToBackStack(null)
        transaccion.commit()
 */
       // fragmentViewModel.setData(recycler_view)



/*
        val arrayAdapter: ArrayAdapter<*>
        val record1 = mutableListOf("Data","bien","funciona")
        val lvRecord = lista

        arrayAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,record1)
        lvRecord.adapter = arrayAdapter
 */

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.SEND_SMS),111)
        }else{
            ShowMessage()
        }

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
       // val navView: NavigationView = findViewById(R.id.nav_view)
        navView.itemIconTintList = null
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_Client, R.id.nav_call, R.id.nav_sms,R.id.nav_email,R.id.nav_Rcall,R.id.nav_Remail,R.id.nav_Rsms,R.id.nav_Contacts), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)

        navView.setupWithNavController(navController)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
/*
        val search = menu?.findItem(R.id.menu_search)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
*/
        return true
    }
    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if(query != null){
            searchDatabase(query)
        }
        return true
    }

    private fun searchDatabase(query: String) {
        val searchQuery = "%$query%"

        mainViewModel.searchDatabase(searchQuery).observe(this) { list ->
            list.let {
                myAdapter.setData1(it)
            }
        }
    }

    // Aqui ejecutaremos código para realizar la llamadas
    public fun requestPermission() {
        val phone = txtPhone.text.toString()
        //Pedir los permisos en tiempo de ejecución y comprobar el nivel de API Instalado
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            //Identificar si los permisos ya se encuentra habilidados
            when {
                ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED->{
                    call(phone)
                }else->solicictarLanzarPermisos.launch(Manifest.permission.CALL_PHONE)
            }
        }else{
            call(phone)
        }
    }

    private fun call(phone: String) {
        startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:$phone")))
    }

    private val solicictarLanzarPermisos = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            isGranted->
        val phone = txtPhone.text.toString()
        if (isGranted){
            call(phone)
        }else{
            Toast.makeText(this,"Necesitas habilitar este permiso", Toast.LENGTH_LONG).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==111 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            ShowMessage() // Muestra el mensaje que se recibe en el dispositivo
        }
    }

    private fun ShowMessage() {
        val phone = txtPhone
        val message = txtMensaje

        var br = object : BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.KITKAT){
                    for (sms in Telephony.Sms.Intents.getMessagesFromIntent(intent)){
                        phone.setText(sms.originatingAddress) //NÚMERO DE TELEFONO DE ORIGEN
                        message.setText(sms.displayMessageBody) // MENSAJE
                    }
                }
            }
        }
        registerReceiver(br, IntentFilter("android.provider.Telephony.SMS_RECEIVED"))
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    fun setRecycler(){
        recycler_view.adapter = ClientAdapter(DataObject.getAllData1())
        recycler_view.layoutManager = LinearLayoutManager(this)
    }


}