package com.example.mazdamaintenance

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.room.Room
import com.example.mazdamaintenance.Entities.Record
import kotlinx.android.synthetic.main.fragment_call.*
import kotlinx.android.synthetic.main.fragment_recordcall.*
import kotlinx.android.synthetic.main.fragment_sms.*
import kotlinx.android.synthetic.main.fragment_sms.txtPhone
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SmsActivity : AppCompatActivity() {
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "com.example.mazdamaintenance"
    private val description = "Mensaje enviado"
    private lateinit var database: DatabaseMazda
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MaterialComponents_Light)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_sms)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intentHere = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this,0, intentHere, PendingIntent.FLAG_UPDATE_CURRENT)

        database = Room.databaseBuilder(
            applicationContext, DatabaseMazda::class.java, "DatabaseMazda"
        ).build()

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.SEND_SMS),111)
        }else{
            ShowMessage()
        }

        val objectoIntent: Intent = intent
        val numero  = objectoIntent.getStringExtra("number")
        val id  = objectoIntent.getIntExtra("id",0)
        val fullname  = objectoIntent.getStringExtra("name")
        println(numero)
        txtPhone.setText(numero)
        txtMensaje.focusable


        btnEnviarSMS.setOnClickListener{
            if (txtPhone.text.toString().isEmpty()){
                txtPhone.error = "Escriba un Número Telefónico, por favor"
            }else if (txtMensaje.text.toString().isEmpty()){
                txtMensaje.error = "Escriba un mensaje, por favor"
            }else{
                var sms = SmsManager.getDefault()
                sms.sendTextMessage(txtPhone.text.toString(),"ME",txtMensaje.text.toString(),null,null)
                Toast.makeText(this,"Mensaje enviado", Toast.LENGTH_LONG).show()
                txtPhone.setText("")
                txtMensaje.setText("")
                txtPhone.focusable

            CoroutineScope(Dispatchers.IO).launch {
                val current = LocalDateTime.now()
                val date: LocalDateTime = current.minusHours(3)
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val fecha_creacion = date.format(formatter)
                val record = Record(0,id.toString(),fullname.toString(),"0","1","0",fecha_creacion)
                database.record().insertRcall(record)
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    notificationChannel = NotificationChannel(channelId,description,
                        NotificationManager.IMPORTANCE_HIGH)
                    notificationChannel.enableLights(true)
                    notificationChannel.lightColor = Color.WHITE
                    notificationChannel.enableVibration(false)

                    notificationManager.createNotificationChannel(notificationChannel)
                    builder = Notification.Builder(this@SmsActivity,channelId)
                        .setContentTitle("El mensaje se envío con éxito ✔")
                        .setContentText("📩 Mensaje enviado a $fullname")
                        .setSmallIcon(R.mipmap.mazdalogo)
                        .setLargeIcon(BitmapFactory.decodeResource(this@SmsActivity.resources,R.mipmap.logo))
                        .setContentIntent(pendingIntent)

                }else{
                    builder = Notification.Builder(this@SmsActivity)
                        .setContentTitle("Registro de usuario")
                        .setContentText("📘Pendiente registrado")
                        .setSmallIcon(R.mipmap.mazdalogo)
                        .setLargeIcon(BitmapFactory.decodeResource(this@SmsActivity.resources,R.mipmap.logo))
                        .setContentIntent(pendingIntent)
                }
                notificationManager.notify(1234,builder.build())
                val intent = Intent(this@SmsActivity,Menu::class.java)
                startActivity(intent)
            }
            }

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
}