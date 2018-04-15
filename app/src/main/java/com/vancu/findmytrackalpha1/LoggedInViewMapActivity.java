package com.vancu.findmytrackalpha1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.location.Location;

import com.google.gson.JsonObject;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;


import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerMode;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.PlaceAutocomplete;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.model.PlaceOptions;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.services.android.telemetry.location.LocationEngine;
import com.mapbox.services.android.telemetry.location.LocationEngineListener;
import com.mapbox.services.android.telemetry.location.LocationEnginePriority;
import com.mapbox.services.android.telemetry.location.LocationEngineProvider;
import com.mapbox.services.android.telemetry.location.LostLocationEngine;
import com.mapbox.services.android.telemetry.permissions.PermissionsManager;
import com.mapbox.services.android.telemetry.permissions.PermissionsListener;
import com.mapbox.services.commons.geojson.Feature;
import com.mapbox.services.commons.geojson.FeatureCollection;
//import com.mapbox.services.commons.geojson.Point;
import com.vancu.findmytrackalpha1.utils.BottomNavigationViewHelper;

import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.geojson.Point;

import java.util.Locale;

public class LoggedInViewMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    private static final int ACTIVITY_NUM = 1;
    private static final String MARKER_SOURCE = "markers-source";
    private static final String MARKER_STYLE_LAYER = "markers-style-layer";
    private static final String MARKER_IMAGE = "ic_action_name.png";

    private MapboxMap mapboxMap;
    private MapView mapView;

    private PermissionsManager permissionsManager;
    private PermissionsListener permissionsListener;
    private LocationLayerPlugin locationPlugin;
    private LocationEngine locationEngine;
    private LocationEngineListener locationEngineListener;
    private Location originLocation;

    private CarmenFeature home;
    private CarmenFeature work;
    private CarmenFeature MStreetStop;
    private CarmenFeature MCStop;
    private CarmenFeature MammothLakeRdStop;
    private CarmenFeature MuirPassStop;
    private CarmenFeature EmigrantPassStop;

    private String geojsonSourceLayerId = "geojsonSourceLayerId";
    private String symbolIconId = "symbolIconId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Mapbox access token is configured here. This needs to be called either in your application
        // object or in the same activity which contains the mapview.
        Mapbox.getInstance(this, "pk.eyJ1IjoidmFuY3U3NDEiLCJhIjoiY2pmcHJsb2ljMGxhazJ4cW9xZ2ZvN2R5ZSJ9.NstJVDuwKG9XDKQqAx-Jow");

        setContentView(R.layout.activity_logged_in_view_map);
        setupBottomNavBar();


	/* Map: This represents the map in the application. */
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        //Handles showing user location, and putting the stops on the map
        mapView.getMapAsync(this);
    }

    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationPlugin() {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            // Create a location engine instance
            initializeLocationEngine();

            locationPlugin = new LocationLayerPlugin(mapView, mapboxMap, locationEngine);
            locationPlugin.setLocationLayerEnabled(LocationLayerMode.COMPASS);
        } else {
            permissionsManager = new PermissionsManager(permissionsListener);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @SuppressWarnings( {"MissingPermission"})
    private void initializeLocationEngine() {
        locationEngine = new LocationEngineProvider(this).obtainBestLocationEngineAvailable();
        locationEngine.setPriority(LocationEnginePriority.HIGH_ACCURACY);
        locationEngine.activate();

        Location lastLocation = locationEngine.getLastLocation();
        if (lastLocation != null) {
            setCameraPosition(lastLocation);
        } else {
            locationEngine.addLocationEngineListener(locationEngineListener);
        }
    }

    private void setCameraPosition(Location location) {
        mapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(location.getLatitude(), location.getLongitude()), 16));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    //@Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            enableLocationPlugin();
        } else {
            Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    //@Override
    @SuppressWarnings( {"MissingPermission"})
    public void onConnected() {
        locationEngine.requestLocationUpdates();
    }

    //@Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            originLocation = location;
            setCameraPosition(location);
            locationEngine.removeLocationEngineListener(locationEngineListener);
        }
    }

    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        LoggedInViewMapActivity.this.mapboxMap = mapboxMap;
        enableLocationPlugin();
    /* Image: An image is loaded and added to the map. */
        Bitmap icon = BitmapFactory.decodeResource(
                LoggedInViewMapActivity.this.getResources(), R.drawable.ic_action_name);
        mapboxMap.addImage(MARKER_IMAGE, icon);
        Bitmap icon2 = BitmapFactory.decodeResource(
                LoggedInViewMapActivity.this.getResources(), R.drawable.ic_action_name2);
        //addMarkers();

        Icon convertedIcon = IconFactory.getInstance(LoggedInViewMapActivity.this).fromBitmap(icon);
        Icon convertedIcon2 = IconFactory.getInstance(LoggedInViewMapActivity.this).fromBitmap(icon2);

        //M Street Apts.
        mapboxMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.3246486998446,-120.47819020087968))
                .title(getString(R.string.draw_marker_options_title))
                .snippet(getString(R.string.draw_marker_options_snippet))
                .icon(convertedIcon));

        //Merced College Stop
        mapboxMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.33462266081097,-120.47795600888725))
                .title(getString(R.string.draw_marker_options_title))
                .snippet(getString(R.string.draw_marker_options_snippet))
                .icon(convertedIcon2));

        //Mammoth Lake Rd Stop
        mapboxMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.363253,-120.429428))
                .title(getString(R.string.draw_marker_options_title))
                .snippet(getString(R.string.draw_marker_options_snippet))
                .icon(convertedIcon));

        //Muir Pass Stop
        mapboxMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.365616,-120.426895))
                .title(getString(R.string.draw_marker_options_title))
                .snippet(getString(R.string.draw_marker_options_snippet))
                .icon(convertedIcon2));

        //Emigrant Pass Stop
        mapboxMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.363770,-120.430687))
                .title(getString(R.string.draw_marker_options_title))
                .snippet(getString(R.string.draw_marker_options_snippet))
                .icon(convertedIcon));


        initSearchFab();
        addUserLocations();

        // Add the symbol layer icon to map for future use
        Bitmap icon3 = BitmapFactory.decodeResource(
                LoggedInViewMapActivity.this.getResources(), R.drawable.ic_search_black_24dp);
        mapboxMap.addImage(symbolIconId, icon3);

        // Create an empty GeoJSON source using the empty feature collection
        setUpSource();

        // Set up a new symbol layer for displaying the searched location's feature coordinates
        //THIS IS FOR WHEN YOU WANT TO ADD A POINT AFTER A USER SEARCHES FOR A LOCATION, CURRENTLY LEFT UNUSED FOR NOW
        setupLayer();

    }

    private void initSearchFab() {
        FloatingActionButton searchFab = findViewById(R.id.fab_location_search);
        searchFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new PlaceAutocomplete.IntentBuilder()
                        .accessToken(Mapbox.getAccessToken())
                        .placeOptions(PlaceOptions.builder()
                                .backgroundColor(Color.parseColor("#EEEEEE"))
                                //.country(Locale)
                                //.proximity(Point.fromLngLat(lastLocation.getLongitude(),originLocation.getLatitude()))
                                .proximity(Point.fromLngLat(-120.47819020087968, 37.3246486998446))
                                .limit(10)
                                .addInjectedFeature(MStreetStop)
                                .addInjectedFeature(MCStop)
                                .addInjectedFeature(MammothLakeRdStop)
                                .addInjectedFeature(MuirPassStop)
                                .addInjectedFeature(EmigrantPassStop)
                                .build(PlaceOptions.MODE_CARDS))

                        .build(LoggedInViewMapActivity.this);
                startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
            }
        });
    }

    private void addUserLocations() {
        MStreetStop = CarmenFeature.builder().text("M Street Apt. (Northbound)")
                .geometry(Point.fromLngLat(-120.47819020087968, 37.3246486998446))
                .placeName("M Street, Merced, CA")
                .id("mapbox-sf")
                .properties(new JsonObject())
                .build();

        MCStop = CarmenFeature.builder().text("Merced College Bus Stop")
                .placeName("Merced College The Bus Terminal, Merced, CA")
                .geometry(Point.fromLngLat(-120.47795600888725, 37.33462266081097))
                .id("mapbox-dc")
                .properties(new JsonObject())
                .build();

        MammothLakeRdStop = CarmenFeature.builder().text("Mammoth Lake Road")
                .placeName("Scholar's Lane, Merced, CA")
                .geometry(Point.fromLngLat(-120.429428, 37.363253))
                .id("mapbox-dc")
                .properties(new JsonObject())
                .build();

        MuirPassStop = CarmenFeature.builder().text("Muir Pass (Student Activities and Athletics Center)")
                .placeName("Muir Pass Road, Merced, CA")
                .geometry(Point.fromLngLat(-120.426895, 37.365616))
                .id("mapbox-dc")
                .properties(new JsonObject())
                .build();

        EmigrantPassStop = CarmenFeature.builder().text("Merced College Bus Stop")
                .placeName("Emigrant Pass Road, Merced, CA")
                .geometry(Point.fromLngLat(-120.430687, 37.363770))
                .id("mapbox-dc")
                .properties(new JsonObject())
                .build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_AUTOCOMPLETE) {

            // Retrieve selected location's CarmenFeature
            CarmenFeature selectedCarmenFeature = PlaceAutocomplete.getPlace(data);

            // Create a new FeatureCollection and add a new Feature to it using selectedCarmenFeature above
            FeatureCollection featureCollection = FeatureCollection.fromFeatures(
                    new Feature[]{Feature.fromJson(selectedCarmenFeature.toJson())});

            // Retrieve and update the source designated for showing a selected location's symbol layer icon
            GeoJsonSource source = mapboxMap.getSourceAs(geojsonSourceLayerId);
            if (source != null) {
                source.setGeoJson(featureCollection);
            }

            // Move map camera to the selected location
            CameraPosition newCameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(((Point) selectedCarmenFeature.geometry()).latitude(),
                            ((Point) selectedCarmenFeature.geometry()).longitude()))
                    .zoom(16)
                    .build();
            mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(newCameraPosition), 4000);
        }
    }

    private void setUpSource() {
        GeoJsonSource geoJsonSource = new GeoJsonSource(geojsonSourceLayerId);
        mapboxMap.addSource(geoJsonSource);
    }

    private void setupLayer() {
        SymbolLayer selectedLocationSymbolLayer = new SymbolLayer("SYMBOL_LAYER_ID", geojsonSourceLayerId);
        selectedLocationSymbolLayer.withProperties(PropertyFactory.iconImage(symbolIconId));
        mapboxMap.addLayer(selectedLocationSymbolLayer);
    }

    /*
    private void addMarkers() {
        List<Feature> features = new ArrayList<>();
    // Source: A data source specifies the geographic coordinate where the image marker gets placed. //

        features.add(Feature.fromGeometry(Point.fromCoordinates(new double[] {-120.47819020087968,37.3246486998446})));
        FeatureCollection featureCollection = FeatureCollection.fromFeatures(features);
        GeoJsonSource source = new GeoJsonSource(MARKER_SOURCE, featureCollection);
        mapboxMap.addSource(source);
	// Style layer: A style layer ties together the source and image and specifies how they are displayed on the map. //
        SymbolLayer markerStyleLayer = new SymbolLayer(MARKER_STYLE_LAYER, MARKER_SOURCE)
                .withProperties(
                        PropertyFactory.iconAllowOverlap(true),
                        PropertyFactory.iconImage(MARKER_IMAGE)
                );
        mapboxMap.addLayer(markerStyleLayer);
    }
        */

    @Override
    @SuppressWarnings( {"MissingPermission"})
    protected void onStart() {
        super.onStart();

        if (locationEngine != null) {
            locationEngine.requestLocationUpdates();
        }

        if (locationPlugin != null) {
            locationPlugin.onStart();
        }
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
        if (locationEngine != null) {
            locationEngine.removeLocationUpdates();
        }
        if (locationPlugin != null) {
            locationPlugin.onStop();
        }
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
        if (locationEngine != null) {
            locationEngine.deactivate();
        }
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
