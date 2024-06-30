package com.example.mymap

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.mymap.databinding.ActivityCreateMapBinding
import com.google.android.gms.maps.model.Marker
import com.google.android.material.snackbar.Snackbar

private const val TAG ="CreateMapActivity"
class CreateMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityCreateMapBinding
    private var markers:MutableList<Marker> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        mapFragment.view?.let {
            Snackbar.make(it, "Long press to add a marker!", Snackbar.LENGTH_INDEFINITE)
                .setAction("Ok", {})
                .setTextColor(ContextCompat.getColor(this, android.R.color.white))
                .show()
        }
        supportActionBar?.title=intent.getStringExtra(EXTRA_USER_MAP)
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
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setOnInfoWindowClickListener {markerToDelete->
            Log.i(TAG,"OnInfoWindowClickListener-- delete this marker")
            markers.remove(markerToDelete)
            markerToDelete.remove()

        }

        mMap.setOnMapLongClickListener {latLng->
            Log.i(TAG,"onMapLongClickListner")
            showAlertDialog(latLng)


        }

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }


    private fun showAlertDialog(latLng: LatLng) {
        val placeFromView= LayoutInflater.from(this).inflate(R.layout.dialog_create_place,null)
        val dialog=AlertDialog.Builder(this)
            .setTitle("Create a marker")
            .setView(placeFromView)
            .setNegativeButton("Cancel",null)
            .setPositiveButton("ok", null)
            .show()

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener{
            val title=placeFromView.findViewById<EditText>(R.id.etTitle).text.toString()
            val description=placeFromView.findViewById<EditText>(R.id.etDescription).text.toString()

            if(title.trim().isEmpty() || description.trim().isEmpty()){
                Toast.makeText(this, "Place must have non-empty title and description", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val  marker=mMap.addMarker(MarkerOptions().position(latLng).title(title).snippet(description).draggable(true))
            markers.add(marker!!)
            dialog.dismiss()
        }

    }
}