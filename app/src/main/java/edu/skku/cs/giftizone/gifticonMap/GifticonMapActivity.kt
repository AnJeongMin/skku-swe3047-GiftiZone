package edu.skku.cs.giftizone.gifticonMap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import edu.skku.cs.giftizone.R
import edu.skku.cs.giftizone.common.toast
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class GifticonMapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private val radius = 100000
    private val initLat = 37.295881
    private val initLng = 126.975931
    private val initZoom = 10F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gifticon_map)
        val mapFragment: SupportMapFragment = supportFragmentManager.findFragmentById(
            R.id.mapview) as SupportMapFragment
        mapFragment.getMapAsync(this)

        setSearchedMarker(intent.getStringExtra("provider")!!)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(initLat, initLng),initZoom))
    }

    private fun setSearchedMarker(query: String) {
        val client = OkHttpClient()
        val apiKey = "***REMOVED***"

        val url = HttpUrl.Builder()
            .scheme("https")
            .host("maps.googleapis.com")
            .addPathSegment("maps")
            .addPathSegment("api")
            .addPathSegment("place")
            .addPathSegment("nearbysearch")
            .addPathSegment("json")
            .addQueryParameter("location", "$initLat,$initLng")
            .addQueryParameter("radius", radius.toString())
            .addQueryParameter("keyword", query)
            .addQueryParameter("key", apiKey)
            .build()

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                val jsonObject = JSONObject(responseBody)
                val results = jsonObject.getJSONArray("results")

                runOnUiThread {
                    var message = "검색 결과가 없습니다."
                    if (results.length() >= 0)
                        message = "${radius / 1000}km 내에 ${results.length()}개의 ${query}이(가) 있습니다."
                    toast(this@GifticonMapActivity, message)
                }

                for (i in 0 until results.length()) {
                    val place = results.getJSONObject(i)
                    val name = place.getString("name")
                    val latitude = place.getJSONObject("geometry")
                        .getJSONObject("location")
                        .getDouble("lat")
                    val longitude = place.getJSONObject("geometry")
                        .getJSONObject("location")
                        .getDouble("lng")
                    val location = LatLng(latitude, longitude)
                    runOnUiThread {
                        mMap.addMarker(MarkerOptions().position(location).title(name))
                    }
                }
            }
        })
    }
}