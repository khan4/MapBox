package com.example.mapbox.mapactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mapbox.R;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.maps.SupportMapFragment;
import com.mapbox.mapboxsdk.plugins.annotation.CircleManager;
import com.mapbox.mapboxsdk.plugins.annotation.CircleOptions;
import com.mapbox.mapboxsdk.plugins.annotation.FillOptions;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.plugins.markerview.MarkerView;
import com.mapbox.mapboxsdk.plugins.markerview.MarkerViewManager;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;

import java.util.List;

import dagger.android.support.DaggerAppCompatActivity;

import static android.view.View.*;
import static com.example.mapbox.R.drawable.ic_arrow_head;
import static com.mapbox.mapboxsdk.style.layers.Property.ICON_ROTATION_ALIGNMENT_VIEWPORT;

public class MyMapActivity extends DaggerAppCompatActivity implements OnClickListener {

    private int id;
    private String userName,description;
    private Double lat,lon;
    private MapView mapView;
    private LatLng latLng;
    private TextView tvDetail;
    private ImageView close;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.access_token));
        setContentView(R.layout.activity_dummy);

        tvDetail = findViewById(R.id.tvDetail);
        close = findViewById(R.id.close);
        close.setVisibility(INVISIBLE);
        close.setOnClickListener(this);

      //  mapView = findViewById(R.id.mapView);

        Intent intent = getIntent();
        id = intent.getIntExtra("ID",-1);
        userName = intent.getStringExtra("NAME");
        description = intent.getStringExtra("DESCRIPTION");
        lon = intent.getDoubleExtra("LONGITUDE",-1);
        lat = intent.getDoubleExtra("LATITUDE",-1);

        latLng = new LatLng(lat,lon);


        final SupportMapFragment mapFragment;
        if (savedInstanceState == null) {

            final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            MapboxMapOptions options = MapboxMapOptions.createFromAttributes(this, null);
            options.camera(new CameraPosition.Builder()
                    .target(new LatLng(lat, lon))
                    .zoom(15)
                    .build());

            mapFragment = SupportMapFragment.newInstance(options);
            mapView = new MapView(this);
            transaction.add(R.id.container, mapFragment, "com.mapbox.map");
            transaction.commit();

        } else {
            mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentByTag("com.mapbox.map");
        }

        if (mapFragment != null) {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(@NonNull final MapboxMap mapboxMap) {

                    mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                        @Override
                        public void onStyleLoaded(@NonNull Style style) {

                             CircleOptions circleOptions = new CircleOptions();
                             circleOptions.withLatLng(latLng)
                                    .withCircleRadius(10f)
                                    .withCircleColor("#0066ff");
                            CircleManager circleManager = new CircleManager(mapView,mapboxMap,style);
                            circleManager.create(circleOptions);

                            circleManager.addClickListener(anything->
                                            tvDetail.setText("Id = " + id + "\nName = " + userName + "\nDescription = " + description +
                                                   "\nLongitude = " + lon + "\nLatitude = " + lat)

                                    );
                            circleManager.addClickListener(open ->
                                    tvDetail.setVisibility(VISIBLE)
                                    );
                            circleManager.addClickListener(another->
                                    close.setVisibility(VISIBLE)
                                    );
                        }
                    });
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.close:
                close.setVisibility(INVISIBLE);
                tvDetail.setVisibility(INVISIBLE);
                break;
        }
    }
}
