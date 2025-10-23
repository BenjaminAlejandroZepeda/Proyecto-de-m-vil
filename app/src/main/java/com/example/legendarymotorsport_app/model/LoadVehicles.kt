package com.example.legendarymotorsport_app.model

import android.content.Context
import org.json.JSONArray

fun loadVehiclesFromJson(context: Context): List<Vehicle> {
    val jsonString = context.assets.open("vehicles.json").bufferedReader().use { it.readText() }
    val jsonArray = JSONArray(jsonString)
    val vehicles = mutableListOf<Vehicle>()

    for (i in 0 until jsonArray.length()) {
        val obj = jsonArray.getJSONObject(i)

        val topSpeed = obj.getJSONObject("topSpeed")
        val topSpeedKmh = topSpeed.optInt("kmh", 0)

        val imagesObj = obj.getJSONObject("images")
        val images = VehicleImages(
            frontQuarter = imagesObj.optString("frontQuarter"),
            rearQuarter = imagesObj.optString("rearQuarter"),
            front = imagesObj.optString("front"),
            rear = imagesObj.optString("rear"),
            side = imagesObj.optString("side")
        )

        val vehicle = Vehicle(
            manufacturer = obj.optString("manufacturer"),
            model = obj.optString("model"),
            seats = obj.optInt("seats"),
            price = obj.optInt("price"),
            topSpeedKmh = topSpeedKmh,
            images = images
        )

        vehicles.add(vehicle)
    }

    return vehicles
}
