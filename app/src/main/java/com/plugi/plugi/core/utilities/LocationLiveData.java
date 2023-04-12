package com.plugi.plugi.core.utilities;


import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class LocationLiveData extends LiveData<Location> implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private  boolean getLocationUpdates = false;
    private GoogleApiClient googleApiClient;
    FusedLocationProviderClient fusedLocationProviderClient; //Global variable

    public LocationLiveData(Context context, boolean getLocationUpdates) {
        googleApiClient =
                new GoogleApiClient.Builder(context, this, this)
                        .addApi(LocationServices.API)
                        .build();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context); //initiate in onCreate
        this.getLocationUpdates = getLocationUpdates;
    }
    @Override
    protected void onActive() {
        googleApiClient.connect();
        Log.e("Location","onActive");
    }
    @Override
    protected void onInactive() {
        if (googleApiClient.isConnected()) {
            fusedLocationProviderClient.removeLocationUpdates(new LocationCallback());
        }
        googleApiClient.disconnect();
        Log.e("Location","onInactive");

    }
    @SuppressLint("MissingPermission")
    @Override
    public void onConnected(Bundle connectionHint) {
        // Try to immediately find a location
        Task<Location> lastLocation =
                fusedLocationProviderClient
                .getLastLocation();
        lastLocation.addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                setValue(task.getResult());
            }
        });
//        if (lastLocation != null) {
//            setValue(lastLocation.getResult());
//            Log.e("Location","lastLocation");
//
//        }
        if (getLocationUpdates) {
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(1000*20);
            locationRequest.setFastestInterval(1000*15);
//             locationRequest.setSmallestDisplacement(SMALLEST_DISPLACEMENT);
//            fusedLocationProviderClient.requestLocationUpdates(
//                    googleApiClient, locationRequest, this);
            fusedLocationProviderClient.requestLocationUpdates(locationRequest,new LocationCallback(){
                        @Override
                        public void onLocationResult(LocationResult locationResult) {
                            super.onLocationResult(locationResult);
                            setValue(locationResult.getLastLocation());
                        }

                        @Override
                        public void onLocationAvailability(LocationAvailability locationAvailability) {
                            super.onLocationAvailability(locationAvailability);
                        }
                    },
                    null);
        }
    }
    @Override
    public void onLocationChanged(Location location) {
        // Deliver the location changes
        setValue(location);

    }
    @Override
    public void onConnectionSuspended(int cause) {
        // Cry softly, hope it comes back on its own
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}