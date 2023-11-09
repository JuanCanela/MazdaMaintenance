package com.example.mazdamaintenance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class GpsActivity : AppCompatActivity(),OnMapReadyCallback {
    private lateinit var mapa: GoogleMap

    override fun onMapReady(googleMap: GoogleMap) {
        mapa = googleMap
        marcaCreada()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MaterialComponents_Light)
        setContentView(R.layout.activity_gps)

        MapadeFragmento()

    }

    private fun MapadeFragmento() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.fragmentMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


   // var lat = 18.139012
    //var lng = -94.485191

    private fun marcaCreada() {
        val objectIntent: Intent = intent
        val nombre = objectIntent.getStringExtra("nombre")
        val apellido = objectIntent.getStringExtra("apellido")
        val lat = objectIntent.getDoubleExtra("latitud",0.0)
        val lng = objectIntent.getDoubleExtra("longitud",0.0)

        val userLocation = LatLng(lat,lng)
        mapa.addMarker(MarkerOptions().position(userLocation).title("Domicilio de: $nombre $apellido"))
        mapa.animateCamera(
            CameraUpdateFactory.newLatLngZoom(userLocation, 25f),
            1500,
            null
        )
    }
}