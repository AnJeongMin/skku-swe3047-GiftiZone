package edu.skku.cs.giftizone.gifticonMap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import edu.skku.cs.giftizone.R

class GifticonMapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gifticon_map)
        val mapFragment: SupportMapFragment = supportFragmentManager.findFragmentById(
            R.id.mapview) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val marker = LatLng(37.295881, 126.975931)
        mMap.addMarker(MarkerOptions().position(marker).title("MAP"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker,15F))
    }
}