package com.plugi.plugi.core.utilities;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.widget.TextView;


import com.plugi.plugi.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Common {
    public static String getAddressForTextView(Context context, double lat, double lng, TextView textView) {
        textView.setText(R.string.loading);
        Geocoder geocoder = new Geocoder(context, new Locale("ar"));
        String loc = " ";
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(lat,lng, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            if (addresses.size() > 0) {
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                loc = city + " , " + country;

            }
        } catch (IOException e) {

        }
        textView.setText(loc);
        return loc;
    }

}
