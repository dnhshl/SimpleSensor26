package com.example.main.repositories

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * The SensorRepository handles the connection to the phone's hardware sensors.
 * 
 * Think of this as the "driver" or library that lets you talk to your sensors, 
 * similar to how you would include a library in Arduino.
 * 
 * It provides a "Flow" of data, which is like a continuous stream of sensor readings.
 */
class SensorRepository(context: Context) {

    // We get the SensorManager from the Android System
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    /**
     * Returns a list of all sensors available on this device.
     * Students can use this to see what hardware is available.
     */
    fun getAllSensors(): List<Sensor> {
        return sensorManager.getSensorList(Sensor.TYPE_ALL)
    }

    /**
     * Get a stream of data from a specific sensor.
     * 
     * @param sensorType The type of sensor, e.g., Sensor.TYPE_ACCELEROMETER.
     * @param delay How often to get updates. SENSOR_DELAY_NORMAL is usually enough.
     * @return A Flow of SensorEvent objects.
     */
    fun getSensorUpdates(
        sensorType: Int, 
        delay: Int = SensorManager.SENSOR_DELAY_NORMAL
    ): Flow<SensorEvent> = callbackFlow {
        
        // Find the specific sensor we want to use
        val sensor = sensorManager.getDefaultSensor(sensorType)
        
        if (sensor == null) {
            // If the sensor doesn't exist (e.g. no Gyroscope on this phone), we close the stream
            close(Exception("Sensor of type $sensorType not available on this device."))
            return@callbackFlow
        }

        // Define what happens when sensor data arrives
        val sensorListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.let {
                    // trySend pushes the new data into the Flow
                    trySend(it)
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                // Not used in most student projects, but must be implemented
            }
        }

        // Register our listener to start receiving hardware updates
        val success = sensorManager.registerListener(sensorListener, sensor, delay)
        if (!success) {
            close(Exception("Failed to register sensor listener"))
        }

        // This block runs when the Flow is cancelled (e.g. when the ViewModel is destroyed)
        // It's like turning off the sensor to save battery.
        awaitClose {
            sensorManager.unregisterListener(sensorListener)
        }
    }
}
