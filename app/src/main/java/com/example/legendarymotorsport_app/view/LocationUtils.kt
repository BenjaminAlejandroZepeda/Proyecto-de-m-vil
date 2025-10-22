package com.example.legendarymotorsport_app.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*

private var locationCallback: LocationCallback? = null

fun startLocationUpdates(
    context: Context,
    fusedLocationClient: FusedLocationProviderClient,
    onLocationReceived: (Location) -> Unit
) {
    // Verificar permisos antes de iniciar
    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
        ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
    ) {
        return // No hay permisos, salir sin iniciar actualizaciones
    }

    val locationRequest = LocationRequest.Builder(
        Priority.PRIORITY_HIGH_ACCURACY, // Máxima precisión
        10_000L // cada 10 segundos
    )
        .setMinUpdateIntervalMillis(5_000L) // al menos cada 5 segundos si cambia
        .build()

    // Evitar duplicar callbacks
    if (locationCallback != null) {
        fusedLocationClient.removeLocationUpdates(locationCallback!!)
    }

    locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            result.lastLocation?.let(onLocationReceived)
        }
    }

    fusedLocationClient.requestLocationUpdates(
        locationRequest,
        locationCallback!!,
        Looper.getMainLooper()
    )
}


fun stopLocationUpdates(fusedLocationClient: FusedLocationProviderClient) {
    locationCallback?.let {
        fusedLocationClient.removeLocationUpdates(it)
        locationCallback = null
    }
}
