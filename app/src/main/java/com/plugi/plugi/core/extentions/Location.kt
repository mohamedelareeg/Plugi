package com.plugi.plugi.core.extentions

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng
import java.util.*


fun LatLng.toAddress(context: Context): String {
    val addresses: List<Address?>
    val geocode: Geocoder = Geocoder(context, Locale.getDefault())
    addresses = geocode.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
    val address = addresses[0]
        ?.getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
    return address?:" "
}