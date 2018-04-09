package com.vancu.findmytrackalpha1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;


import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.services.commons.geojson.Feature;
import com.mapbox.services.commons.geojson.FeatureCollection;
import com.mapbox.services.commons.geojson.Point;
import com.vancu.findmytrackalpha1.utils.BottomNavigationViewHelper;

import java.util.ArrayList;
import java.util.List;

public class LoggedInViewMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int ACTIVITY_NUM = 1;
    private static final String MARKER_SOURCE = "markers-source";
    private static final String MARKER_STYLE_LAYER = "markers-style-layer";
    private static final String MARKER_IMAGE = "ic_action_name.png";

    private MapboxMap mapboxMap;
    private MapView mapView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_view_map);
        setupBottomNavBar();

        // Mapbox access token is configured here. This needs to be called either in your application
        // object or in the same activity which contains the mapview.
        Mapbox.getInstance(this, "pk.eyJ1IjoidmFuY3U3NDEiLCJhIjoiY2pmcHJsb2ljMGxhazJ4cW9xZ2ZvN2R5ZSJ9.NstJVDuwKG9XDKQqAx-Jow");

	/* Map: This represents the map in the application. */
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

    }

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search_in_map:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.searchnavigation, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search_in_map);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        // Configure the search info and add any event listeners...

        return super.onCreateOptionsMenu(menu);
    }
    */

    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        LoggedInViewMapActivity.this.mapboxMap = mapboxMap;
    /* Image: An image is loaded and added to the map. */
        Bitmap icon = BitmapFactory.decodeResource(
                LoggedInViewMapActivity.this.getResources(), R.drawable.ic_action_name);
        mapboxMap.addImage(MARKER_IMAGE, icon);
        Bitmap icon2 = BitmapFactory.decodeResource(
                LoggedInViewMapActivity.this.getResources(), R.drawable.ic_action_name2);
        addMarkers();

        Icon convertedIcon = IconFactory.getInstance(LoggedInViewMapActivity.this).fromBitmap(icon);
        Icon convertedIcon2 = IconFactory.getInstance(LoggedInViewMapActivity.this).fromBitmap(icon2);

        mapboxMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.3246486998446,-120.47819020087968))
                .title(getString(R.string.draw_marker_options_title))
                .snippet(getString(R.string.draw_marker_options_snippet))
                .icon(convertedIcon));

        mapboxMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.33462266081097,-120.47795600888725))
                .title(getString(R.string.draw_marker_options_title))
                .snippet(getString(R.string.draw_marker_options_snippet))
                .icon(convertedIcon2));

    }

    private void addMarkers() {
        List<Feature> features = new ArrayList<>();
    /* Source: A data source specifies the geographic coordinate where the image marker gets placed. */
        features.add(Feature.fromGeometry(Point.fromCoordinates(new double[] {-120.47819020087968,37.3246486998446})));
        FeatureCollection featureCollection = FeatureCollection.fromFeatures(features);
        GeoJsonSource source = new GeoJsonSource(MARKER_SOURCE, featureCollection);
        mapboxMap.addSource(source);
	/* Style layer: A style layer ties together the source and image and specifies how they are displayed on the map. */
        SymbolLayer markerStyleLayer = new SymbolLayer(MARKER_STYLE_LAYER, MARKER_SOURCE)
                .withProperties(
                        PropertyFactory.iconAllowOverlap(true),
                        PropertyFactory.iconImage(MARKER_IMAGE)
                );
        mapboxMap.addLayer(markerStyleLayer);
    }


    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }



    //sets up the bottom navigation view for current activitiy.
    public void setupBottomNavBar()
    {
        BottomNavigationViewEx BottomNavEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavBar);
        BottomNavigationViewHelper.setupBottomNaviView(BottomNavEx);
        BottomNavigationViewHelper.enableNavigation(LoggedInViewMapActivity.this, BottomNavEx);
        Menu menu = BottomNavEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

}
