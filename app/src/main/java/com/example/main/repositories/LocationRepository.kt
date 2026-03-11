package com.example.main.repositories

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * The LocationRepository handles GPS data.
 * 
 * It uses Google Play Services (FusedLocationProvider) to get the best 
 * possible location (GPS, WiFi, or Cell).
 */
class LocationRepository(context: Context) {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    /**
     * Get a stream of location updates.
     * 
     * @param intervalMillis How often to request an update (e.g., 5000ms = 5 seconds)
     */
    @SuppressLint("MissingPermission") 
    fun getLocationUpdates(intervalMillis: Long = 5000): Flow<Location> = callbackFlow {
        
        // 1. Try to get the LAST KNOWN location immediately
        // This prevents the "0.0" problem while waiting for the first GPS fix.
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let { trySend(it) }
        }

        // 2. Set up the request for continuous updates
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 
            intervalMillis
        ).apply {
            setMinUpdateDistanceMeters(0f) // Get updates even if the phone isn't moving
            setWaitForAccurateLocation(false) // Give us a "rough" position quickly, then refine it
        }.build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                for (location in result.locations) {
                    trySend(location)
                }
            }
        }

        // 3. Start requesting updates
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )

        // Stop updates when the Flow is cancelled (ViewModel is destroyed)
        awaitClose {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }
}
