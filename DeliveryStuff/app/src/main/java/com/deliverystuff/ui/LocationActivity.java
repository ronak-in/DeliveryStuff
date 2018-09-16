package com.deliverystuff.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.deliverystuff.R;
import com.deliverystuff.model.ModelDelivery;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationActivity extends BaseApp implements OnMapReadyCallback {

    public static String EXTRA_MODEL_DELIVERY = "EXTRA_MODEL_DELIVERY";

    private ImageView imgLogo;
    private TextView tvDescription, tvLocationName;
    private ModelDelivery modelDelivery;
    private GoogleMap mMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        imgLogo = (ImageView) findViewById(R.id.imgLogo);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvLocationName = (TextView) findViewById(R.id.tvLocationName);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (getIntent().getExtras() != null) {
            modelDelivery = (ModelDelivery) getIntent().getExtras().getSerializable(EXTRA_MODEL_DELIVERY);
            tvDescription.setText(modelDelivery.getDescription());
            tvLocationName.setText(modelDelivery.getLocation().getAddress());
            Glide.with(this)
                    .load(modelDelivery.getImageUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .thumbnail(0.1f)
                    .into(imgLogo);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); // get the reference of Toolbar
        setSupportActionBar(toolbar); // Setting/replace toolbar as the ActionBar
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.delivery_details);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        mMap.getUiSettings().setZoomControlsEnabled(true);
        LatLng deliLatLng = new LatLng(modelDelivery.getLocation().getLat(), modelDelivery.getLocation().getLng());
        mMap.addMarker(new
                MarkerOptions().position(deliLatLng).title(modelDelivery.getDescription()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(deliLatLng, 13));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}