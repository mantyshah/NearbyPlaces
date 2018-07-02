package com.nearbyplaces.devsoftware.nearbyplaces;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWIndow implements GoogleMap.InfoWindowAdapter {


    private LayoutInflater layoutInflater;
    private View view;
    private Context context;

    public CustomInfoWIndow(Context context) {
        this.context = context;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.custom_info_window, null);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        TextView placeName, vincity;

        placeName = view.findViewById(R.id.placeName);
        placeName.setText(marker.getTitle());

        vincity = view.findViewById(R.id.vincity);
        vincity.setText(marker.getSnippet());



        return view;
    }
}
