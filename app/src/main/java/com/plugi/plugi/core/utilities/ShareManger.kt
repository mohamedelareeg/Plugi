package com.plugi.plugi.core.utilities

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager


object ShareManger {
    fun shareToFaceBook(activity: Activity, details:String) {
        val type = "text/plain"

        // Create the new Intent using the 'Send' action.
        val share = Intent(Intent.ACTION_SEND)

        // Set the MIME type
        share.type = type
        share.setPackage("com.facebook.orca")
        // Add the URI to the Intent.
        share.putExtra(Intent.EXTRA_TEXT, details)
        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        val packageManager: PackageManager = activity.packageManager
        if (share.resolveActivity(packageManager) != null) {
            activity.startActivity(share)
            // Broadcast the Intent.
            activity.startActivity(Intent.createChooser(share, "Share to"))
         } else {

        }
    }
    fun shareToInstaGram(activity: Activity, details:String) {
        val type = "text/plain"

        // Create the new Intent using the 'Send' action.
        val share = Intent(Intent.ACTION_SEND)

        // Set the MIME type
        share.type = type
        share.setPackage("com.instagram.android")
        // Add the URI to the Intent.
        share.putExtra(Intent.EXTRA_TEXT, details)
        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        val packageManager: PackageManager = activity.packageManager
        if (share.resolveActivity(packageManager) != null) {
            activity.startActivity(share)
            // Broadcast the Intent.
            activity.startActivity(Intent.createChooser(share, "Share to"))
         } else {

        }
    }
    fun shareToTwitter(activity: Activity, details:String) {
        val type = "text/plain"

        // Create the new Intent using the 'Send' action.
        val share = Intent(Intent.ACTION_SEND)

        // Set the MIME type
        share.type = type
        share.setPackage("com.twitter.android")
        // Add the URI to the Intent.
        share.putExtra(Intent.EXTRA_TEXT, details)
        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        val packageManager: PackageManager = activity.packageManager
        if (share.resolveActivity(packageManager) != null) {
            activity.startActivity(share)
            // Broadcast the Intent.
            activity.startActivity(Intent.createChooser(share, "Share to"))
         } else {
        }
    }
    fun shareToWhatsApp(activity: Activity, details:String) {
        val type = "text/plain"

        // Create the new Intent using the 'Send' action.
        val share = Intent(Intent.ACTION_SEND)

        // Set the MIME type
        share.type = type
        share.setPackage("com.whatsapp")
        // Add the URI to the Intent.
        share.putExtra(Intent.EXTRA_TEXT, details)
        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        val packageManager: PackageManager = activity.packageManager
        if (share.resolveActivity(packageManager) != null) {
            activity.startActivity(share)
            // Broadcast the Intent.
            activity.startActivity(Intent.createChooser(share, "Share to"))
         } else {
        }
    }

}