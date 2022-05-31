package com.example.mannayoclient

import android.R
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.mannayoclient.apiClient.ApiClient
import com.example.mannayoclient.databinding.ActivityMapBinding
import com.example.mannayoclient.dto.CategoryResult
import com.example.mannayoclient.dto.Document
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MapActivity : AppCompatActivity() {
    lateinit var binding:ActivityMapBinding
    private val ACCESS_FINE_LOCATION = 1000
    var restaurantList: ArrayList<Document> = ArrayList() // 음식점 FD6
    var count = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(checkLocationService()){
            permissionCheck()
        }else{
            Toast.makeText(this,"Turn On GPS please",Toast.LENGTH_SHORT).show()
        }
        val lm: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        @SuppressLint("MissingPermission")
        val userNowLocation : Location? = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

        val uLatitude = userNowLocation?.latitude
        val uLongitude = userNowLocation?.longitude

        var mapView = MapView(this)
        var mapViewContainer = binding.KakaoMapView as ViewGroup
        mapViewContainer.addView(mapView)
        startTracking(mapView)

//        requestSearch(uLongitude!!.toDouble(), uLatitude!!.toDouble(),count,mapView)

        while(count<=3){
            var tmp = requestSearch(uLongitude!!.toDouble(), uLatitude!!.toDouble(), count,mapView)
            if(tmp){
                break
            }
            count+=1
        }

//        drawMarker(restaurantList)

    }

    @SuppressLint("MissingPermission")
    private fun startTracking(mapView: MapView) {
        mapView.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading

        val lm: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val userNowLocation : Location? = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

        val uLatitude = userNowLocation?.latitude
        val uLongitude = userNowLocation?.longitude
        Log.d("hello",uLatitude.toString())
        Log.d("hello",uLongitude.toString())
        val uNowPosition = MapPoint.mapPointWithGeoCoord(uLatitude!!,uLongitude!!)

        val marker = MapPOIItem()
        marker.itemName = "현 위치"
        marker.mapPoint =uNowPosition
        marker.markerType = MapPOIItem.MarkerType.BluePin
        marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin
        mapView.addPOIItem(marker)

    }

    //권한 체크
    private fun permissionCheck() {
        val preference = getPreferences(MODE_PRIVATE)
        val isFirstCheck = preference.getBoolean("isFirstPermissionCheck", true)
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 권한이 없는 상태
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                // 권한 거절
                val builder = AlertDialog.Builder(this)
                builder.setMessage("현재 위치를 확인하시려면 위치 권한을 허용해주세요.")
                builder.setPositiveButton("확인") { dialog, which ->
                    ActivityCompat.requestPermissions(this,
                        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), ACCESS_FINE_LOCATION)
                }
                builder.setNegativeButton("취소") { dialog, which ->

                }
                builder.show()
            } else {
                if (isFirstCheck) {
                    // 최초 권한 요청
                    preference.edit().putBoolean("isFirstPermissionCheck", false).apply()
                    ActivityCompat.requestPermissions(this,
                        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), ACCESS_FINE_LOCATION)
                } else {
                    val builder = AlertDialog.Builder(this)
                    builder.setMessage("현재 위치를 확인하시려면 설정에서 위치 권한을 허용해주세요.")
                    builder.setPositiveButton("설정으로 이동") { dialog, which ->
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:$packageName"))
                        startActivity(intent)
                    }
                    builder.setNegativeButton("취소") { dialog, which ->

                    }
                    builder.show()
                }
            }
        } else {

        }

    }


    // 권한 요청
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == ACCESS_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "위치 권한이 승인되었습니다", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this, "위치 권한이 거절되었습니다", Toast.LENGTH_SHORT).show()

            }
        }
    }

    //GPS가 켜져있는지 확인하기
    private fun checkLocationService() : Boolean{
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    //내 위치 반경 1km안의 음식점 찾아내기
    private fun requestSearch(x:Double, y:Double,count:Int,mapView: MapView) :Boolean {
        var tmp: Boolean = false
        restaurantList.clear()
            ApiClient.service.getSearchCategory("KakaoAK b04a77fbc1bf26b47a85d2a003eeb8a9",
                "FD6",
                x,
                y,
                500, count).enqueue(object : Callback<CategoryResult> {
                override fun onResponse(
                    call: Call<CategoryResult>,
                    response: Response<CategoryResult>,
                ) {
                    assert(response.body() != null)
                    if (response.body()!!.getDocuments() != null) {
                        Log.d(TAG, "Restaurant Success")
                        restaurantList.addAll(response.body()!!.getDocuments())
                        for (i in restaurantList) {

                            Log.d(TAG, i.toString())
                        }
                        Log.d(TAG,
                            "-----------------------------------------------------------------")
                        tmp = response.body()!!.getMeta().getIsEnd()
                        drawMarker(restaurantList,mapView)

                    }

                }

                override fun onFailure(call: Call<CategoryResult>, t: Throwable) {
                    Log.d("error", "error!!!!!!!!!!!!!!!!!!!!!!!")
                }
            })

            return tmp
    }

    private fun drawMarker(documents: ArrayList<Document>,mapView: MapView){
        var tagNum = 10
        for (document in documents) {
            val marker = MapPOIItem()
            marker.itemName = document.getPlaceName()
            marker.tag = tagNum + 1
            val x: Double = document.getY().toDouble()
            val y: Double = document.getX().toDouble()
            //카카오맵은 참고로 new MapPoint()로  생성못함. 좌표기준이 여러개라 이렇게 메소드로 생성해야함
            val mapPoint = MapPoint.mapPointWithGeoCoord(x, y)
            marker.mapPoint = mapPoint
            marker.markerType = MapPOIItem.MarkerType.BluePin // 마커타입을 커스텀 마커로 지정.
            marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin

            mapView.addPOIItem(marker)
        }
    }
}