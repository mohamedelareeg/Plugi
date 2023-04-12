package com.plugi.plugi.core.extentions

import android.content.Context
import androidx.annotation.RawRes
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.MapStyleOptions
import java.lang.Exception

fun GoogleMap.changeMapStyle(context: Context,@RawRes style:Int){
    try {
        this.setMapStyle(MapStyleOptions.loadRawResourceStyle(context,style))
    }catch (e:Exception){}
}