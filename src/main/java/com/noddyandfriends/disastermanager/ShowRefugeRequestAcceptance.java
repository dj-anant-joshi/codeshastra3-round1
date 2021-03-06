package com.noddyandfriends.disastermanager;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import static com.noddyandfriends.disastermanager.util.Constants.REFUGE_ADDRESS;
import static com.noddyandfriends.disastermanager.util.Constants.REFUGE_LATITUDE;
import static com.noddyandfriends.disastermanager.util.Constants.REFUGE_LONGITUDE;
import static com.noddyandfriends.disastermanager.util.Constants.REFUGE_NAME;
import static com.noddyandfriends.disastermanager.util.Constants.USER_PREFS;

public class ShowRefugeRequestAcceptance extends Fragment {


    public ShowRefugeRequestAcceptance() {

    }
    private double latitude;
    private double longitude;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_show_refuge_request_acceptance, container, false);
        TextView refName = (TextView) rootView.findViewById(R.id.refuge_location_name);
        TextView refAddr = (TextView) rootView.findViewById(R.id.refuge_location_address);
        WebView mapsView = (WebView) rootView.findViewById(R.id.google_maps_webview);
        LocationManager manager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        Location location = null;
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission_group.LOCATION) != PackageManager.PERMISSION_DENIED){
            location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }else{
            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission_group.LOCATION}, getContext().getResources().getInteger(R.integer.PERMISSION_ACCESS_LOCATION));
        }
        SharedPreferences preferences = getActivity().getSharedPreferences(USER_PREFS, 0);


        refName.setText(preferences.getString(REFUGE_NAME, null));
        refAddr.setText(preferences.getString(REFUGE_ADDRESS, null));
        latitude = preferences.getLong(REFUGE_LATITUDE, 0);
        longitude = preferences.getLong(REFUGE_LONGITUDE, 0);

        Uri webUri = Uri.parse("http://maps.google.com/maps?saddr="+location.getLatitude()+","+location.getLongitude()+"&daddr="+latitude+","+longitude);
        mapsView.loadUrl(webUri.toString());
        return rootView;
    }
}
