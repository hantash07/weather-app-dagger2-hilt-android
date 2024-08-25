package com.hantash.weatherapp.view.ui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.hantash.weatherapp.R
import com.hantash.weatherapp.model.data.WeatherResponse
import com.hantash.weatherapp.model.network.ResultAPI
import com.hantash.weatherapp.model.network.WeatherAPI
import com.hantash.weatherapp.model.repo.WeatherRepository
import com.hantash.weatherapp.model.utils.Constant
import com.hantash.weatherapp.model.utils.Constant.Companion.LOCATION_PERMISSION_REQUEST_CODE
import com.hantash.weatherapp.model.utils.DialogNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var tvCity: TextView
    private lateinit var tvTemperature: TextView
    private lateinit var tvCloud: TextView
    private lateinit var tvCondition: TextView
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private lateinit var dialogNavigator: DialogNavigator
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.swipe_refresh)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initView()

        dialogNavigator = DialogNavigator(supportFragmentManager)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        } else {
            getCurrentLocation()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getCurrentLocation()
            }
        }
    }

    private fun initView() {
        swipeRefresh = findViewById(R.id.swipe_refresh)
        tvCity = findViewById(R.id.tv_city)
        tvTemperature = findViewById(R.id.tv_temperature)
        tvCloud = findViewById(R.id.tv_cloud)
        tvCondition = findViewById(R.id.tv_condition)
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    val latLng = "$latitude,$longitude"
                    fetchCurrentWeather(latLng)
                    Log.d("app-debug", "LatLng: $latLng")
                } else {
                    Log.d("app-debug", "Unable to get location")
                }
            }
            .addOnFailureListener {
                Log.d("app-debug", "Failed to get location: ${it.message}")
            }
    }

    private fun fetchCurrentWeather(latLng: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val weatherAPI = retrofit.create(WeatherAPI::class.java)
        val weatherRepository = WeatherRepository(weatherAPI)

        coroutineScope.launch {
            showProgressIndicator()
            try {
                when(val result = weatherRepository.fetchCurrentWeather(latLng)) {
                    is ResultAPI.SUCCESS -> {
                        updateUI(result.weatherResponse)
                    }
                    is ResultAPI.FAILURE -> {
                        dialogNavigator.showServerError()
                    }
                }
            } finally {
                hideProgressIndicator()
            }
        }
    }

    private fun updateUI(weatherResponse: WeatherResponse) {
        val weather = weatherResponse.weather
        val location = weatherResponse.location
        tvCity.text = location.name
        tvTemperature.text = Math.round(weather.temperatureCel).toString()
        tvCloud.text = weather.condition.text
    }

    private fun showProgressIndicator() {
        swipeRefresh.isRefreshing = true
    }

    private fun hideProgressIndicator() {
        swipeRefresh.isRefreshing = false

    }
}