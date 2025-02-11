package com.example.sistema_solar_crud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.sistema_solar_crud.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val latitud = intent.getDoubleExtra("latitud", 0.0)
        val longitud = intent.getDoubleExtra("longitud", 0.0)
        with(mMap.uiSettings) {
            isZoomControlsEnabled = true  // Botones de zoom
            isZoomGesturesEnabled = true   // Zoom con gestos
            isCompassEnabled = true        // Brújula
            isMapToolbarEnabled = true     // Barra de herramientas de Google
        }
        val ubicacion = LatLng(latitud, longitud)
        mMap.addMarker(
            MarkerOptions()
                .position(ubicacion)
                .title("ubicacion")
                .snippet("Coordenadas: $latitud, $longitud")
        )
        // Mover cámara y aplicar zoom
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 15f))
        mMap.setOnMarkerClickListener { marker ->
            marker.showInfoWindow()
            true
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

}