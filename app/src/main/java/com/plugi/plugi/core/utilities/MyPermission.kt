package com.plugi.plugi.core.utilities

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.util.ArrayList


object MyPermission {

    val REQUEST_PERMISSION_CAMERA_INDEX = 0
    val REQUEST_PERMISSION_CAMERA_CODE = 2048
    val permissionCamera = Manifest.permission.CAMERA

    val REQUEST_PERMISSION_LOCATION_INDEX = 1
    val REQUEST_PERMISSION_LOCATION_CODE = 2049
    val permissionLocation = Manifest.permission.ACCESS_FINE_LOCATION


    val REQUEST_PERMISSION_STORAGE_READ_INDEX = 2
    val REQUEST_PERMISSION_STORAGE_READ_CODE = 2050
    val permissionStorageRead = Manifest.permission.READ_EXTERNAL_STORAGE

    val REQUEST_PERMISSION_STORAGE_WRITE_INDEX = 3
    val REQUEST_PERMISSION_STORAGE_WRITE_CODE = 2052
    val permissionStorageWrite = Manifest.permission.WRITE_EXTERNAL_STORAGE


    val REQUEST_PERMISSION_CONTACTS_INDEX = 4
    val REQUEST_PERMISSION_CONTACTS_CODE = 2051
    val permissionCONTACTS = Manifest.permission.READ_CONTACTS


    fun requestPermission(activity: Activity, index: Int): Int {
        val model = getPermisstionList()[index]
        return if (ContextCompat.checkSelfPermission(
                activity,
                model.permisstion
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(model.permisstion),
                model.code
            )
            model.code
        } else {
            -1
        }
    }
    fun requestPermission(activity: Fragment, index: Int): Int {
        val model = getPermisstionList()[index]
        return activity.activity?.let {
            return if (ContextCompat.checkSelfPermission(
                    it,
                    model.permisstion
                ) !== PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf<String>(model.permisstion),
                    model.code
                )
                model.code
            } else {
                -1
            }
        } ?: -1

    }
    private fun getPermisstionList(): ArrayList<PermisstionModel> {
        val list = ArrayList<PermisstionModel>()
        list.add(
            PermisstionModel(
                REQUEST_PERMISSION_CAMERA_INDEX,
                REQUEST_PERMISSION_CAMERA_CODE,
                permissionCamera
            )
        )
        list.add(
            PermisstionModel(
                REQUEST_PERMISSION_LOCATION_INDEX,
                REQUEST_PERMISSION_LOCATION_CODE,
                permissionLocation
            )
        )
        list.add(
            PermisstionModel(
                REQUEST_PERMISSION_STORAGE_READ_INDEX,
                REQUEST_PERMISSION_STORAGE_READ_CODE,
                permissionStorageRead
            )
        )
        list.add(
            PermisstionModel(
                REQUEST_PERMISSION_STORAGE_WRITE_INDEX,
                REQUEST_PERMISSION_STORAGE_WRITE_CODE,
                permissionStorageWrite
            )
        )
        list.add(
            PermisstionModel(
                REQUEST_PERMISSION_CONTACTS_INDEX,
                REQUEST_PERMISSION_CONTACTS_CODE,
                permissionCONTACTS
            )
        )
        return list
    }

    class PermisstionModel {
        var index: Int = 0
        var code: Int = 0
        var permisstion: String = ""

        constructor() {}

        constructor(index: Int, code: Int, permisstion: String) {
            this.index = index
            this.code = code
            this.permisstion = permisstion
        }
    }
    /*
    READ_CALENDAR
WRITE_CALENDAR
CAMERA
READ_CONTACTS
WRITE_CONTACTS
GET_ACCOUNTS
ACCESS_FINE_LOCATION
ACCESS_COARSE_LOCATION
RECORD_AUDIO
READ_PHONE_STATE
READ_PHONE_NUMBERS
CALL_PHONE
ANSWER_PHONE_CALLS
READ_CALL_LOG
WRITE_CALL_LOG
ADD_VOICEMAIL
USE_SIP
PROCESS_OUTGOING_CALLS
BODY_SENSORS
SEND_SMS
RECEIVE_SMS
READ_SMS
RECEIVE_WAP_PUSH
RECEIVE_MMS
READ_EXTERNAL_STORAGE
WRITE_EXTERNAL_STORAGE
     */


//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        Log.e("Req Code", "" + requestCode);
//        if (requestCode == REQUEST_CODE_PERMISSION) {
//            if (grantResults.length == 1 &&
//                    grantResults[0] == MockPackageManager.PERMISSION_GRANTED ) {
//                methodRedirect();
//            }
//            else{
//                this.getPermission();
//            }
//        }
//    }
}
