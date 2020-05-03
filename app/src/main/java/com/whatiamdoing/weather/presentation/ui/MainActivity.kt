package com.whatiamdoing.weather.presentation.ui

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.faltenreich.skeletonlayout.Skeleton
import com.google.android.gms.location.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.whatiamdoing.weather.R
import com.whatiamdoing.weather.data.CurrentWeatherEntity
import com.whatiamdoing.weather.di.App
import com.whatiamdoing.weather.presentation.presenter.MainPresenter
import com.whatiamdoing.weather.utils.Constants.Others.REQUEST_CODE
import com.whatiamdoing.weather.utils.Constants.SharedPref.PREF_LAT
import com.whatiamdoing.weather.utils.Constants.SharedPref.PREF_LOCATION
import com.whatiamdoing.weather.utils.SharedPreference
import com.whatiamdoing.weather.utils.weatherCodeToImage
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), MainView {

    @InjectPresenter
    internal lateinit var presenter: MainPresenter
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    @Inject
    lateinit var sharedPreference: SharedPreference
    lateinit var skeleton: Skeleton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        skeleton = skeleton_layout
        skeleton.showSkeleton()
        App.appComponent.inject(this)
        swipeRefresh.setOnRefreshListener { requestLocation() }
    }

    override fun setData(data: CurrentWeatherEntity) {
        tv_locationName.text = resources.getString(
            R.string.name_country,
            data.location.country,
            data.location.region,
            data.location.name
        )
        tv_observationTime.text = resources.getString(
                R.string.observation_time,
                data.current.observation_time
            )
        tv_currentWeather.text = data.current.weather_descriptions.getOrNull(0)
        tv_temperature.text = resources.getString(
            R.string.temperature,
            data.current.temperature
        )
        tv_cloud.text = getString(
            R.string.cloud_cover,
            data.current.cloudcover
        )
        tv_windSpeed.text = getString(
            R.string.wind_speed,
            data.current.wind_speed
        )
        tv_humidity.text = getString(
            R.string.humidity,
            data.current.humidity
        )
        tv_pressure.text = getString(
            R.string.pressure,
            data.current.pressure
        )
        tv_visibility.text = getString(
            R.string.visibility,
            data.current.visibility
        )
        tv_feelsLike.text = getString(
            R.string.feels_like,
            data.current.feelslike
        )
        Glide.with(this).load(
            weatherCodeToImage(
                data.current.weather_code
            )
        ).centerCrop()
            .into(iv_weatherIcon)
        skeleton.showOriginal()
    }

    override fun showProgressBar() = with(swipeRefresh) { isRefreshing = true }

    override fun hideProgressBar() = with(swipeRefresh) { isRefreshing = false }

    override fun onError() =
        with(MaterialAlertDialogBuilder(this)) {
            setTitle(R.string.error_title)
            setMessage(R.string.error)
            setPositiveButton(R.string.update) { _: DialogInterface, _: Int -> getCurrentLocation() }
            setCancelable(false)
            create()
        }.show()

    override fun getCurrentLocation() {
        if (!isNetworkConnected()) {
            onError()
        } else if (prefsContainsLocation()) {
            val data = sharedPreference.getLocation()
            presenter.loadData(data.first, data.second)
        } else if (isPermissionsGiven())
            requestLocation()
        else
            requestPermissions()
    }

    private fun requestLocation() {
        if (!isLocationEnabled())
            startActivityForResult(
                Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),
                REQUEST_CODE
            )
        else
            getLocation()
    }

    private fun isLocationEnabled(): Boolean =
        (getSystemService(Context.LOCATION_SERVICE) as LocationManager).isProviderEnabled(
            LocationManager.GPS_PROVIDER
        )

    private fun isPermissionsGiven(): Boolean =
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ), ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) -> true
            else -> false
        }

    private fun isNetworkConnected(): Boolean =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetwork != null
        } else {
            TODO("VERSION.SDK_INT < M")
        }

    private fun requestPermissions() = ActivityCompat.requestPermissions(
        this,
        arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ),
        REQUEST_CODE
    )

    private fun getLocation() {
        val request = LocationRequest().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 1500
            fastestInterval = 1000
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationProviderClient.requestLocationUpdates(
            request,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {
            p0?.locations?.last()?.let {
                presenter.loadData(it.latitude.toFloat(), it.longitude.toFloat())
            }
            fusedLocationProviderClient.removeLocationUpdates(this)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) getCurrentLocation()
            else finish()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && isLocationEnabled())
            getLocation()
        else
            hideProgressBar()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun prefsContainsLocation(): Boolean =
        getSharedPreferences(PREF_LOCATION, Context.MODE_PRIVATE).contains(PREF_LAT)
}
