package com.hantash.weatherapp.view.ui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hantash.weatherapp.R
import com.hantash.weatherapp.model.data.WeatherResponse
import com.hantash.weatherapp.model.network.ResultAPI
import com.hantash.weatherapp.model.utils.Constant
import com.hantash.weatherapp.model.utils.DialogNavigator
import com.hantash.weatherapp.model.utils.beautifyToString
import com.hantash.weatherapp.model.utils.toDateTime
import com.hantash.weatherapp.view.BaseActivity
import com.hantash.weatherapp.viewmodel.LocationViewModel
import com.hantash.weatherapp.viewmodel.MyViewModelFactory
import com.hantash.weatherapp.viewmodel.WeatherViewModel
import javax.inject.Inject

class MainActivity : BaseActivity() {
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var btnRefresh: AppCompatButton
    private lateinit var tvLocation: TextView
    private lateinit var tvLastUpdate: TextView
    private lateinit var tvTemperature: TextView
    private lateinit var tvCondition: TextView
    private lateinit var tvHumidity: TextView
    private lateinit var tvFeelsLike: TextView

    @Inject lateinit var viewModelFactory: MyViewModelFactory
    @Inject lateinit var dialogNavigator: DialogNavigator
    private lateinit var viewModel: WeatherViewModel
    private lateinit var locationViewModel: LocationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent.inject(this)
        setContentView(R.layout.activity_main)

        initView()

        //Viewmodel
        viewModel = ViewModelProvider(this, viewModelFactory).get(WeatherViewModel::class.java)
        locationViewModel = ViewModelProvider(this, viewModelFactory).get(LocationViewModel::class.java)

        //Observer Location
        locationViewModel.locationLiveData.observe(this) { location ->
            if (location != null) {
                getCurrentWeather(location)
            }
        }

        //Observer Weather
        viewModel.weatherLiveData.observe(this) { result ->
            when (result) {
                is ResultAPI.SUCCESS -> {
                    hideProgressIndicator()
                    updateUI(result.weatherResponse)
                }

                is ResultAPI.FAILURE -> {
                    hideProgressIndicator()
                    dialogNavigator.showServerError()
                }
            }
        }

        //Checking for Location Permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), Constant.LOCATION_PERMISSION_REQUEST_CODE)
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
        if (requestCode == Constant.LOCATION_PERMISSION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getCurrentLocation()
            }
        }
    }

    private fun initView() {
        swipeRefresh = findViewById(R.id.swipe_refresh)
        btnRefresh = findViewById(R.id.btn_refresh)
        tvLocation = findViewById(R.id.tv_location)
        tvLastUpdate = findViewById(R.id.tv_last_update)
        tvTemperature = findViewById(R.id.tv_temperature)
        tvCondition = findViewById(R.id.tv_condition)
        tvHumidity = findViewById(R.id.tv_humidity)
        tvFeelsLike = findViewById(R.id.tv_feels_like)

        swipeRefresh.isEnabled = false
        btnRefresh.visibility = View.GONE
        btnRefresh.setOnClickListener {
            getCurrentLocation()
        }
    }

    private fun updateUI(weatherResponse: WeatherResponse) {
        val weather = weatherResponse.weather
        val location = weatherResponse.location
        val tempStr = Math.round(weather.temperatureCel).toString()
        val feelsLike = Math.round(weather.feelLikeCel).toString()
        val lastUpdate = weather.lastUpdated.toDateTime()

        tvLocation.text = String.format("%s, %s", location.name, location.country)
        tvLastUpdate.text = lastUpdate.beautifyToString()
        tvTemperature.text = String.format("%s°", tempStr)
        tvCondition.text = String.format("Condition: %s", weather.condition.text)
        tvHumidity.text = String.format("Humidity: %s", weather.humidity)
        tvFeelsLike.text = String.format("Feels Like: %s°", feelsLike)
        btnRefresh.text = String.format("Last Update at: %s", lastUpdate.beautifyToString())
        btnRefresh.visibility = View.VISIBLE
    }

    private fun showProgressIndicator() {
        swipeRefresh.isRefreshing = true
    }

    private fun hideProgressIndicator() {
        swipeRefresh.isRefreshing = false
    }

    private fun getCurrentLocation() {
        locationViewModel.fetchCurrentLocation()
    }

    private fun getCurrentWeather(location: Location) {
        val lat = location.latitude
        val lon = location.longitude
        val latLng = "$lat,$lon"

        showProgressIndicator()
        viewModel.fetchCurrentWeather(latLng)

        Log.d("app-debug", "latLng:: $latLng")
    }
}