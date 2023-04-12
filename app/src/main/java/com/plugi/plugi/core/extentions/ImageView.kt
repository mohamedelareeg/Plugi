package com.plugi.plugi.core.extentions

import android.widget.ImageView
import com.plugi.plugi.R
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso


fun ImageView.loadServerImage(url : String?){
    url?.let {
        Picasso.get()
            .load(url)
            .networkPolicy(NetworkPolicy.OFFLINE)
            .fit()
            .centerInside()
//            .placeholder(R.mipmap.ic_launcher)
            .into(this, object : Callback {
                override fun onSuccess() {}

                override fun onError(e: Exception) {
                    Picasso.get()
                        .load(url)
//                        .placeholder(R.mipmap.ic_launcher)
                        .fit()
                        .centerInside()
                        .into(this@loadServerImage)
                }
            })
    }
}
