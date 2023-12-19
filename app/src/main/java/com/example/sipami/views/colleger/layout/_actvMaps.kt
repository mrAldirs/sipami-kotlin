package com.example.sipami.views.colleger.layout

import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.sipami.R
import com.example.sipami.databinding.CMapsBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.GoogleMap.OnPoiClickListener
import com.google.android.gms.maps.GoogleMap.OnPolygonClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.*
import mumayank.com.airlocationlibrary.AirLocation

class _actvMaps : AppCompatActivity(), View.OnClickListener,
    OnMapReadyCallback,
    OnMarkerClickListener,
    OnPoiClickListener,
    OnMapLongClickListener,
    OnInfoWindowClickListener,
    OnPolygonClickListener {

    private lateinit var b: CMapsBinding
    val MAPBOX_TOKEN = "pk.eyJ1IjoibmFzeWFyeCIsImEiOiJjbDQ4MmtnZWIwbzU0M2lwaTc2anVwM2xmIn0.Mz7mkrr00kTYrroq-qRg0g"
    var URL = ""

    var marker: Marker? = null
    var lat : Double = 0.0; var lng : Double = 0.0;
    var airLoc : AirLocation? = null
    var gMap : GoogleMap? = null
    lateinit var mapFragment : SupportMapFragment

    var arrayMarkers = ArrayList<Marker>()
    var nomorLokasi = 1
    var markerId = ""
    var arrayLines = ArrayList<LatLng>()

    val arrayPoly1 = ArrayList<LatLng>()
    val arrayPoly2 = ArrayList<LatLng>()
    lateinit var poly1 : Polygon
    lateinit var poly2 : Polygon
    @Deprecated("Deprecated in Java")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = CMapsBinding.inflate(layoutInflater)
        setContentView(b.root)
        supportActionBar?.hide()

        mapFragment = supportFragmentManager.findFragmentById(R.id.mapsFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
        b.fabMap1.setOnClickListener(this)
        b.fabMap2.setOnClickListener(this)
        b.fabMap3.setOnClickListener(this)
        b.fabMapDrawPolyline.setOnClickListener(this)
        b.fabMapDrawPolygon.setOnClickListener(this)
        b.fabMapDrawCircle.setOnClickListener(this)

        b.chip.isChecked = true
    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fab ->{
                val ll = LatLng(lat,lng)
                gMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(ll,16.0f))
                b.editText.setText("My Position : LAT=$lat, LNG=$lng")
            }
            R.id.fabMap1 -> gMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
            R.id.fabMap2 -> gMap?.mapType = GoogleMap.MAP_TYPE_SATELLITE
            R.id.fabMap3 -> gMap?.mapType = GoogleMap.MAP_TYPE_TERRAIN
            R.id.fabMapDrawPolyline -> {
                arrayLines.clear()
                arrayLines.add(marker!!.position) // posisi saya
                arrayLines.add(LatLng(-7.802620539742753, 111.97982476675658))
                arrayLines.add(LatLng(-7.829171469957603, 111.98434064078425))
                arrayLines.add(LatLng(-7.801145280845825, 112.00845959559209))
                if (gMap != null) {
                    gMap!!.addPolyline(
                        PolylineOptions().addAll(arrayLines)
                        .clickable(true)
                        .width(10f))
                    gMap!!.addMarker(
                        MarkerOptions().position(arrayLines[0])
                        .title("Start")
                        .icon(BitmapDescriptorFactory.defaultMarker(
                            BitmapDescriptorFactory.HUE_GREEN)))
                    gMap!!.addMarker(
                        MarkerOptions().position(arrayLines[1])
                        .title("Mid 1")
                        .icon(BitmapDescriptorFactory.defaultMarker(
                            BitmapDescriptorFactory.HUE_BLUE)))

                    gMap!!.addMarker(
                        MarkerOptions().position(arrayLines[2])
                            .title("Mid 2")
                            .icon(BitmapDescriptorFactory.defaultMarker(
                                BitmapDescriptorFactory.HUE_RED)))
                    gMap!!.addMarker(
                        MarkerOptions().position(arrayLines[3])
                            .title("End")
                            .icon(BitmapDescriptorFactory.defaultMarker(
                                BitmapDescriptorFactory.HUE_YELLOW)))
                    gMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        LatLng(-7.810885745539356, 112.0041451127895), 13.0f))
                }
            }

            R.id.fabMapDrawPolygon -> {
                arrayPoly1.clear()
                arrayPoly2.clear()
                arrayPoly1.add(LatLng(-7.802620539742753, 111.97982476675658))
                arrayPoly1.add(LatLng(-7.829171469957603, 111.98434064078425))
                arrayPoly1.add(LatLng(-7.801145280845825, 112.00845959559209))
                arrayPoly2.add(LatLng(-7.802620539742753, 111.97982476675658))
                arrayPoly2.add(LatLng(-7.829171469957603, 111.98434064078425))
                arrayPoly2.add(LatLng(-7.801145280845825, 112.00845959559209))
                if (gMap != null) {
                    poly1 = gMap!!.addPolygon(
                        PolygonOptions().addAll(arrayPoly1)
                            .clickable(true)
                            .strokeColor(R.color.black)
                            .fillColor(R.color.black))
                    poly2 = gMap!!.addPolygon(
                        PolygonOptions().addAll(arrayPoly2)
                            .clickable(true)
                            .strokeColor(R.color.black)
                            .fillColor(R.color.black))
                }
            }

            R.id.fabMapDrawCircle -> {
                if (gMap != null && marker != null) {
                    val currentLatLng = LatLng(marker!!.position.latitude, marker!!.position.longitude)

                    gMap?.addCircle(
                        CircleOptions()
                            .center(currentLatLng)
                            .radius(1000.0)
                            .strokeColor(R.color.teal_700)
                            .fillColor(R.color.teal_200)
                    )
                    gMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 16.0f))
                }
            }
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        gMap = p0
        if (gMap != null){
            airLoc = AirLocation(this,true,true,
                object : AirLocation.Callbacks{
                    override fun onFailed(locationFailedEnum: AirLocation.LocationFailedEnum) {
                        Toast.makeText(this@_actvMaps, "Failed to get current location",
                            Toast.LENGTH_SHORT).show()
                        b.editText.setText("Failed to get current location")
                    }

                    override fun onSuccess(location: Location) {
                        lat = location.latitude; lng = location.longitude
                        val ll = LatLng(location.latitude, location.longitude)

                        marker = gMap!!.addMarker(MarkerOptions().position(ll).title("Hei! I'm here"))
                        gMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(ll,16.0f))
                        b.editText.setText("Lokasi saya : LAT=${location.latitude}," +
                                "LNG=${location.longitude}")
                    }
                })
        }

        gMap!!.setOnPoiClickListener(this)
        gMap!!.setOnMapLongClickListener(this)
        gMap!!.setOnMarkerClickListener(this)
        gMap!!.setOnInfoWindowClickListener(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        airLoc?.onActivityResult(requestCode,resultCode,data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        airLoc?.onRequestPermissionsResult(requestCode,
            permissions,grantResults)
        super.onRequestPermissionsResult(requestCode,
            permissions, grantResults)
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        markerId = p0.id
        gMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(p0.position,16.0f))
        p0.showInfoWindow()
        return true
    }

    override fun onPoiClick(p0: PointOfInterest) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(p0.name).setMessage(
            "Lat : ${p0.latLng.latitude}\n" +
                    "Lng : ${p0.latLng.longitude}\n" +
                    "Place ID : ${p0.placeId}\n"
        )
            .setNegativeButton("Keluar", null)
            .setPositiveButton("Buka GMaps") { _, _ ->
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("geo:${p0.latLng.latitude},${p0.latLng.longitude}?q=${p0.name}")
                )
                intent.setPackage("com.google.android.apps.maps")
                startActivity(intent)
            }
        builder.show()
    }

    override fun onMapLongClick(p0: LatLng) {
        if (gMap!=null) {
            arrayMarkers.add(gMap!!.addMarker(MarkerOptions().position(p0)
                .title("Lokasi ke-$nomorLokasi")
                .snippet("Lat : ${p0.latitude}\nLng : ${p0.longitude}")
                .icon(BitmapDescriptorFactory.defaultMarker(
                    BitmapDescriptorFactory.HUE_GREEN)))!!
            )
            nomorLokasi++
        }
    }

    override fun onInfoWindowClick(p0: Marker) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Marker akan dihapus ?")
            .setNegativeButton("HAPUS") { _, _ ->
                var i = 0
                while (i < arrayMarkers.size) {
                    if (arrayMarkers[i].id == markerId) {
                        arrayMarkers[i].remove()
                        arrayMarkers.removeAt(i)
                        break
                    }
                    i++
                }
            }
            .setPositiveButton("Cek marker di GMaps") { _, _ ->
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("geo:${p0.position.latitude},${p0.position.longitude}?q=${p0.title}")
                )
                intent.setPackage("com.google.android.apps.maps")
                startActivity(intent)
            }
    }

    override fun onPolygonClick(p0: Polygon) {
        when (p0.tag) {
            "poly1" -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Poly 1").setMessage(
                    "Jumlah penduduk = 20"
                ).setNeutralButton("Keluar", null)
                builder.show()
            }
            "poly2" -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Poly 2").setMessage(
                    "Jumlah penduduk = 30"
                ).setNeutralButton("Keluar", null)
                builder.show()
            }
        }
    }

}