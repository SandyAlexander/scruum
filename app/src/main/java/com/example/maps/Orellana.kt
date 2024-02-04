package com.example.maps

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import android.Manifest
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class Orellana : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {

    private lateinit var map:GoogleMap
    companion object{
        const val REQUEST_CODE_lOCATION = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.maps)
        createFragment()
    }
    private fun createFragment(){
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    override fun onMapReady(googleMap: GoogleMap){
        map=googleMap
        createMarker()
        map.setOnMyLocationButtonClickListener(this)
        map.setOnMyLocationClickListener(this)
        enableLocation()
    }
    private fun createMarker(){
        val coordinates = LatLng(-0.465012, -76.990046)
        val marker =MarkerOptions().position(coordinates).title("Mi lugar fav")
        map.addMarker(marker)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordinates, 18f),
            4000,
            null
        )
    }
    private fun isLocationPermissionGranted()= ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    private fun enableLocation(){
        if(!::map.isInitialized) return
        if(isLocationPermissionGranted()){
            map.isMyLocationEnabled=true
        }else{
            requestLocationPermission()
        }
    }
    private fun requestLocationPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
          )
        ){
            Toast.makeText(this, "ve a ajustes",Toast.LENGTH_SHORT).show()
        } else{
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_lOCATION
            )
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        @Suppress("UNREACHABLE_CODE")
        when(requestCode) {
            REQUEST_CODE_lOCATION ->
                if (grantResults.isEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                    map.isMyLocationEnabled = true
            }else{
            Toast.makeText(this, "Para activar la localización ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        }else->{}

    }
        fun onResumeFragments() {
            super.onResumeFragments()
            if(!::map.isInitialized) return
            if(!isLocationPermissionGranted()){
                map.isMyLocationEnabled  = false
                Toast.makeText(this, "Para activar la localización ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
            }
        }
}

    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(this,"Botón pulsado", Toast.LENGTH_SHORT).show()
        return false
    }

    override fun onMyLocationClick(p0: Location) {
        Toast.makeText(this,"El pedido está en ${p0.latitude},${p0.longitude}", Toast.LENGTH_SHORT).show()
    }
}



