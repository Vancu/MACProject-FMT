package com.vancu.findmytrackalpha1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.location.Location;

import com.google.gson.JsonObject;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;


import com.mapbox.mapboxsdk.annotations.Marker;
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
import com.mapbox.mapboxsdk.style.layers.Layer;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.style.sources.VectorSource;
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

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import org.json.JSONArray;

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
    private boolean MapDone = false;
    private HashMap<String, List<MarkerOptions>> BusStopsbyID = new HashMap<String, List<MarkerOptions>>(); //string Bus ID,

    private String geojsonSourceLayerId = "geojsonSourceLayerId";
    private String symbolIconId = "symbolIconId";

    Spinner BusCompany, BusID, Ranges;

    String BusCompanyString, BusIDString, dataTable, scheduleName, TAG_TIME, TAG_COUNT, TAG_STOP;

    String TAG_NAME = "stops";

    String HttpUrl = "http://73.220.191.198:13379/PullBusRouteData.php";

    JSONArray stops = null;

    ArrayList<HashMap<String, String>> List;

    Vector<Vector<String>> Data = new Vector<Vector<String>>();

    ArrayAdapter<CharSequence> adapter;

    ProgressDialog progressDialog;

    JSONParser jParser = new JSONParser();

    int count;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List = new ArrayList<HashMap<String, String>>();

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


        //scheduleName = getIntent().getExtras().getString("schedule");
        BusCompany = (Spinner) findViewById(R.id.busCompanyspinner);
        adapter = ArrayAdapter.createFromResource(this,R.array.busCompanyarray,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BusCompany.setAdapter(adapter);

        BusID = (Spinner) findViewById(R.id.BusIDspinner);
        adapter = ArrayAdapter.createFromResource(this,R.array.emptySpinner,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BusID.setAdapter(adapter);


        BusCompany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int position,long id)
            {
                BusCompanyString = parent.getItemAtPosition(position).toString();
                if(position == 0)
                {
                    //sets BusID to cattracks
                    adapter = ArrayAdapter.createFromResource(LoggedInViewMapActivity.this,R.array.CattracksIDarray,android.R.layout.simple_spinner_item);
                    BusID.setAdapter(adapter);
                }
                else if(position == 1)
                {
                    adapter = ArrayAdapter.createFromResource(LoggedInViewMapActivity.this,R.array.MercedTheBusIDarray,android.R.layout.simple_spinner_item);
                    BusID.setAdapter(adapter);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        BusID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int position,long id)
            {
                BusIDString = parent.getItemAtPosition(position).toString();
                Data.removeAllElements();
                //List<Marker> busMarker = new List<Marker>;
                switch (BusIDString){
                    case "AB":
                        BusIDString = "AB";
                        dataTable = "cattracksAB";
                        //if(MapDone)
                        //    showBus(BusIDString);
                        break;
                    case "C1Blue":
                        BusIDString = "C1";
                        dataTable = "cattracksc1b";
                        //showBus(BusIDString);
                        break;
                    case "C1Gold":
                        BusIDString = "C1";
                        dataTable = "cattracksc1g";
                        //showBus(BusIDString);
                        break;
                    case "C2":
                        BusIDString = "C2";
                        dataTable = "cattracksc2";
                        //showBus(BusIDString);
                        break;
                    case "FastCat":
                        BusIDString = "FastCat";
                        dataTable = "cattracksfastcat";
                        //showBus(BusIDString);
                        break;
                    case "NiteCat":
                        BusIDString = "NiteCat";
                        dataTable = "cattracksnitecat";
                        //showBus(BusIDString);
                        break;
                    case "E":
                        BusIDString = "E";
                        dataTable = "cattrackse";
                        //showBus(BusIDString);
                        break;
                    case "E1":
                        BusIDString = "E1";
                        dataTable = "cattrackse1";
                        //showBus(BusIDString);
                        break;
                    case "E2":
                        BusIDString = "E2";
                        dataTable = "cattrackse2";
                        //showBus(BusIDString);
                        break;
                    case "G":
                        BusIDString = "G";
                        dataTable = "cattracksg";
                        //showBus(BusIDString);
                        break;
                    case "HeritageWeek":
                        BusIDString = "Heritage";
                        dataTable = "cattracksheritagemf";
                        //showBus(BusIDString);
                        break;
                    case "HeritageWeekend":
                        BusIDString = "Heritage";
                        dataTable = "cattracksheritagess";
                        //showBus(BusIDString);
                        break;
                    case "UCNorth":
                        BusIDString = "UCNorth";
                        dataTable = "ucbustimesnorth";
                   //showBus(BusIDString);
                        break;
                    case "UCSouth":
                        BusIDString = "UCSouth";
                        dataTable = "ucbustimessouth";
                   //showBus(BusIDString);
                        break;
                    default:
                        break;
                }
                if (!BusIDString.equals("Select Company") && !BusIDString.equals("Select Route"))
                {
                    LoadFunction(dataTable);
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (MapDone)
                        showBus(BusIDString);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });


    }

    public String addTimes(int index)
    {
        String all_route_time = "Time for " + BusIDString + " is... \n";
        for(int j = 1; j < Data.get(index).size(); j++)
        {
            String stoptime = Data.get(index).get(j);
            if (!stoptime.equals("11:59:59") && !stoptime.equals("23:59:59"))
                all_route_time += stoptime + " ";
        }
        return all_route_time;
    }

    public void showBus(String ID)
    {

        /*
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (mapboxMap.getMarkers().size() > 0)
            mapboxMap.removeMarker(mapboxMap.getMarkers().get(0));

        List<MarkerOptions> BusStopID = BusStopsbyID.get(ID);
        for(int i = 0; i < BusStopID.size(); i++)
        {
            MarkerOptions schedule = BusStopID.get(i);
            String all_Time_for_schedule = addTimes(i);
            schedule.snippet(all_Time_for_schedule);
            mapboxMap.addMarker(BusStopID.get(i));
        }

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

    public void LoadFunction(final String BusID) {
        class LoadAllData extends AsyncTask<String, String, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(LoggedInViewMapActivity.this);
                progressDialog.setMessage("Pulling data...");
                progressDialog.setIndeterminate(false);
                progressDialog.setCancelable(false);
                progressDialog.show();
            }
            protected String doInBackground(String... args) {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("BusID", BusID));
                JSONObject json = jParser.makeHttpRequest(HttpUrl, "GET", params);
                try {
                    stops = json.getJSONArray(TAG_NAME);
                    TAG_COUNT = "count";
                    for (int i = 0; i < stops.length(); i++) {
                        JSONObject x = stops.getJSONObject(i);
                        if (i == 0) {
                            count = x.getInt(TAG_COUNT) + 1;
                        } else {
                            TAG_STOP = "name";
                            String stopName = x.getString(TAG_STOP);
                            Data.add(i-1, new Vector<String>(count));
                            Vector<String> currentVec = Data.get(i-1);
                            for (int j = 0; j < (count); j++) {
                                if (j == 0) {
                                    currentVec.add(stopName);
                                } else {
                                    TAG_TIME = "time#" + j;
                                    String stopTime = x.getString(TAG_TIME);
                                    currentVec.add(stopTime);
                                }
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void onPostExecute(String file_url){
                progressDialog.dismiss();
            }
        }
        LoadAllData loadData = new LoadAllData();
        loadData.execute(BusID);
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

        Icon convertedIcon = IconFactory.getInstance(LoggedInViewMapActivity.this).fromBitmap(icon);

        //Mammoth Lake Rd Stop
        MarkerOptions Mammoth = new MarkerOptions()
                .position(new LatLng(37.363253,-120.429428))
                .title("Mammoth Lake Road")
                .snippet("Bus Company: CatTracks\nBus ID:A-B, C1, C2, FastCat, NiteCat, E, E1, E2, G, Heritage \n\nBus Company: TheBus \nBus ID: UC,")
                .icon(convertedIcon);

        //Mammoth Lake Rd Stop
        MarkerOptions MammothRepeat = new MarkerOptions()
                .position(new LatLng(37.363211,-120.429316))
                .title("Mammoth Lake Road (From Moraga)")
                .snippet("Bus Company: CatTracks\nBus ID:A-B, C1, C2, FastCat, NiteCat, E, E1, E2, G, Heritage \n\nBus Company: TheBus \nBus ID: UC,")
                .icon(convertedIcon);


        //Muir Pass Stop
        MarkerOptions Muir= new MarkerOptions()
                .position(new LatLng(37.365616,-120.426705))
                .title("Muir Pass/Students Activity Center")
                .snippet("Bus Company: CatTracks\nBus ID:A-B, C1, C2, FastCat, NiteCat, E, E1, E2, G, Heritage \n\nBus Company: TheBus \nBus ID: UC,")
                .icon(convertedIcon);

        //Muir Pass Stop
        MarkerOptions MuirRepeat= new MarkerOptions()
                .position(new LatLng(37.365679,-120.426916))
                .title("Muir Pass/Students Activity Center (From Moraga)")
                .snippet("Bus Company: CatTracks\nBus ID:A-B, C1, C2, FastCat, NiteCat, E, E1, E2, G, Heritage \n\nBus Company: TheBus \nBus ID: UC,")
                .icon(convertedIcon);

        //Emigrant Pass Stop
        MarkerOptions Emigrant= new MarkerOptions()
                .position(new LatLng(37.363770,-120.430687))
                .title("Emigrant Pass")
                .snippet("Bus Company: CatTracks\nBus ID:A-B, C1, C2, FastCat, NiteCat, E, E1, E2, G, Heritage \n\nBus Company: TheBus \nBus ID: UC,")
                .icon(convertedIcon);

        //Emigrant Pass Stop
        MarkerOptions EmigrantRepeat= new MarkerOptions()
                .position(new LatLng(37.363871,-120.430768))
                .title("Emigrant Pass (From Moraga)")
                .snippet("Bus Company: CatTracks\nBus ID:A-B, C1, C2, FastCat, NiteCat, E, E1, E2, G, Heritage \n\nBus Company: TheBus \nBus ID: UC,")
                .icon(convertedIcon);

        //Castle Air Park.
        MarkerOptions Castle= new MarkerOptions()
                .position(new LatLng(37.374616,-120.576773))
                .title("Castle Air Park")
                .snippet("Bus Company: CatTracks\nBus ID:A-B")
                .icon(convertedIcon);

        //Arrow Wood to School
        MarkerOptions arrowWood= new MarkerOptions()
                .position(new LatLng(37.352652,-120.476538))
                .title("Bellevue Ranch on Arrow Wood Dr.")
                .snippet("Bus Company: CatTracks\nBus ID: C1, C2, FastCat, E, E2, G")
                .icon(convertedIcon);

        //Arrow Wood from School
        MarkerOptions arrowWoodLeave= new MarkerOptions()
                .position(new LatLng(37.35255,-120.475696))
                .title("Bellevue Ranch on Arrow Wood Dr.")
                .snippet("Bus Company: CatTracks\nBus ID: C1, C2, FastCat, E, E2, G")
                .icon(convertedIcon);

        //Mercy Hospital/Tri-college.
        MarkerOptions Mercy= new MarkerOptions()
                .position(new LatLng(37.339273,-120.468734))
                .title("Mercy Hospital")
                .snippet("Bus Company: CatTracks\nBus ID: C1,  FastCat, \n\nBus Company: TheBus \nBus ID: UC,")
                .icon(convertedIcon);

        //Tri-College/Mercy. (actual stop is Tri-college)
        MarkerOptions TriCollege= new MarkerOptions()
                .position(new LatLng(37.335907,-120.469218))
                .title("Tri-College")
                .snippet("Bus Company: CatTracks\nBus ID: C1,  FastCat, \n\nBus Company: TheBus \nBus ID: UC,")
                .icon(convertedIcon);

        //El Portal Plaza & "G" Street (bus stop on "G")
        MarkerOptions G_Street_Portal= new MarkerOptions()
                .position(new LatLng(37.327082,-120.469025))
                .title("El Portal Plaza &  Street (bus stop on 'G'')")
                .snippet("Bus Company: CatTracks\nBus ID: C1, NiteCat, ")
                .icon(convertedIcon);

        //Rite Aid/Walgreens
        MarkerOptions Rite_Wall= new MarkerOptions()
                .position(new LatLng(37.324979,-120.468982))
                .title("Rite Aid/Walgreens")
                .snippet("Bus Company: CatTracks\nBus ID: C1, NiteCat, E, E1, ")
                .icon(convertedIcon);

        //Merced tbSun-Star/Staples (tbSun Star Sign)
        MarkerOptions SunStar= new MarkerOptions()
                .position(new LatLng(37.316207,-120.469580))
                .title("Merced Sun Star Sign")
                .snippet("Bus Company: CatTracks\nBus ID: C1")
                .icon(convertedIcon);

        //Alexander & "G" Street (bus stop on "G")
        MarkerOptions AlexG= new MarkerOptions()
                .position(new LatLng(37.315815,-120.469213))
                .title("Alexander & G Street (bus stop on G)")
                .snippet("Bus Company: CatTracks\nBus ID: C1")
                .icon(convertedIcon);

        //Swiss Colony Apts.
        MarkerOptions Swiss= new MarkerOptions()
                .position(new LatLng(37.318274,-120.474071))
                .title("Swiss Colony Apts.")
                .snippet("Bus Company: CatTracks\nBus ID: C1")
                .icon(convertedIcon);

        //College Green on Park @ 3040 Park Ave
        MarkerOptions ParkAve= new MarkerOptions()
                .position(new LatLng(37.318133,-120.473114))
                .title("College Green on Park @ 3040 Park Ave")
                .snippet("Bus Company: CatTracks\nBus ID: C1")
                .icon(convertedIcon);

        //Meadows Ave & Olivewood Dr. (Food Maxx) (ACTUALL STREET IS OLIVEWOOD)
        MarkerOptions Olivewood= new MarkerOptions()
                .position(new LatLng(37.318235,-120.490786))
                .title("Olivewood Dr. (Food Maxx, Southbound)")
                .snippet("Bus Company: CatTracks\nBus ID: C1, E,")
                .icon(convertedIcon);

        //Meadows Ave & Olivewood Dr. (Food Maxx) (ACTUAL STREET IS MEADOWS AVE.
        MarkerOptions Meadows= new MarkerOptions()
                .position(new LatLng(37.318112,-120.490558))
                .title("Meadows Ave (Food Maxx, Northbound)")
                .snippet("Bus Company: CatTracks\nBus ID: C1, E1")
                .icon(convertedIcon);

        //Walmart on Loughborough at "Pier One" bus stop
        MarkerOptions AtPierOne= new MarkerOptions()
                .position(new LatLng(37.317600,-120.499436))
                .title("Walmart on Loughborough at Pier One bus stop")
                .snippet("Bus Company: CatTracks\nBus ID: C1")
                .icon(convertedIcon);

        //Walmart on Loughborough across "Pier One"
        MarkerOptions acrossPierOne= new MarkerOptions()
                .position(new LatLng(37.317391,-120.499568))
                .title("Walmart on Loughborough accross Pier One bus stop")
                .snippet("Bus Company: CatTracks\nBus ID: C1, E1")
                .icon(convertedIcon);

        //Granville Luxury Apartments
        MarkerOptions Granville= new MarkerOptions()
                .position(new LatLng(37.315275,-120.502951))
                .title("Granville Luxury Apartments")
                .snippet("Bus Company: CatTracks\nBus ID: C1, E, E1")
                .icon(convertedIcon);

        //Ironstone Dr.
        MarkerOptions Ironstone= new MarkerOptions()
                .position(new LatLng(37.342663,-120.477819))
                .title("Ironstone Dr.")
                .snippet("Bus Company: CatTracks\nBus ID: C2, E, E2, G")
                .icon(convertedIcon);

        //"R" Street Village Apts.
        MarkerOptions rStreet= new MarkerOptions()
                .position(new LatLng(37.335605,-120.486243))
                .title("R Street Village Apts.")
                .snippet("Bus Company: CatTracks\nBus ID: C2, NiteCat, E, E2, G, Heritage ")
                .icon(convertedIcon);

        //El Redondo @ corner of Jenner before traffic circle
        MarkerOptions elRedondo= new MarkerOptions()
                .position(new LatLng(37.334307,-120.495182))
                .title("El Redondo @ corner of Jenner before traffic circle")
                .snippet("Bus Company: CatTracks\nBus ID: C2, E, E2, G")
                .icon(convertedIcon);

        //Buena Vista
        MarkerOptions buenaVista= new MarkerOptions()
                .position(new LatLng(37.326230,-120.502370))
                .title("Buena Vista")
                .snippet("Bus Company: CatTracks\nBus ID: C2, E, E2, ")
                .icon(convertedIcon);

        //Merced Mall / Target
        MarkerOptions Target= new MarkerOptions()
                .position(new LatLng(37.323377,-120.485577))
                .title("Merced Mall/Target")
                .snippet("Bus Company: CatTracks\nBus ID: C2, E, E1, E2")
                .icon(convertedIcon);

        //M Street Apts.
        MarkerOptions mStreet= new MarkerOptions()
                .position(new LatLng(37.3246486998446,-120.47819020087968))
                .title("M Street Apts.")
                .snippet(getString(R.string.draw_marker_options_snippet))
                .icon(convertedIcon);

        //Merced College Stop
        MarkerOptions mCollege= new MarkerOptions()
                .position(new LatLng(37.33462266081097,-120.47795600888725))
                .title("Merced College Stop")
                .snippet("Bus Company: CatTracks\nBus ID: A-B, C2, E, E1, E2, G, \n\nBus Company: TheBus \nBus ID: UC,")
                .icon(convertedIcon);

        //In-Shape
        MarkerOptions inShape= new MarkerOptions()
                .position(new LatLng(37.332007,-120.465768))
                .title("In-Shape")
                .snippet("Bus Company: CatTracks\nBus ID:  FastCat, E, E1, Heritage ")
                .icon(convertedIcon);

        //University Surgery Center (westbound)
        MarkerOptions uniSurgeryWest= new MarkerOptions()
                .position(new LatLng(37.332346,-120.451474))
                .title("University Surgery Center (westbound)")
                .snippet("Bus Company: CatTracks\nBus ID: FastCat, \n\nBus Company: TheBus \nBus ID: UC,")
                .icon(convertedIcon);

        //University Surgery Center (eastbound)
        MarkerOptions uniSurgeryEast= new MarkerOptions()
                .position(new LatLng(37.332081,-120.451704))
                .title("University Surgery Center (eastbound)")
                .snippet("Bus Company: CatTracks\nBus ID: FastCat, \n\nBus Company: TheBus \nBus ID: UC,")
                .icon(convertedIcon);

        //Starbucks/Promenade Center
        MarkerOptions starbucks= new MarkerOptions()
                .position(new LatLng(37.332208,-120.460500))
                .title("Starbucks/Promenade Center")
                .snippet("Bus Company: CatTracks\nBus ID: FastCat, E, E1, E2, \n\nBus Company: TheBus \nBus ID: UC,")
                .icon(convertedIcon);

        //Moraga Subdivision (westbound)
        MarkerOptions moragaWest= new MarkerOptions()
                .position(new LatLng(37.332342,-120.438691))
                .title("Moraga Subdivision (westbound)")
                .snippet("Bus Company: CatTracks\nBus ID: FastCat, E, E1, E2, \n\nBus Company: TheBus \nBus ID: UC,")
                .icon(convertedIcon);

        //Moraga Subdivision (eastbound)
        MarkerOptions moragaEast= new MarkerOptions()
                .position(new LatLng(37.332047,-120.437723))
                .title("Moraga Subdivision (eastbound)")
                .snippet("Bus Company: CatTracks\nBus ID: FastCat, E, E1, E2, \n\nBus Company: TheBus \nBus ID: UC,")
                .icon(convertedIcon);

        //Merced Mall Theater
        MarkerOptions mallTheater= new MarkerOptions()
                .position(new LatLng(37.320153,-120.480135))
                .title("Merced Mall Theater")
                .snippet("Bus Company: CatTracks\nBus ID: NiteCat, E, E1, ")
                .icon(convertedIcon);

        //Applebee's
        MarkerOptions applebees= new MarkerOptions()
                .position(new LatLng(37.318383,-120.497348))
                .title("Applebee's")
                .snippet("Bus Company: CatTracks\nBus ID: NiteCat, ")
                .icon(convertedIcon);

        //Hollywood Theatres Mainplace
        MarkerOptions hollyTheatre= new MarkerOptions()
                .position(new LatLng(37.320153,-120.480135))
                .title("Hollywood Theatres Mainplace")
                .snippet("Bus Company: CatTracks\nBus ID: NiteCat, E, E1, G \n\nBus Company: TheBus \nBus ID: UC,")
                .icon(convertedIcon);

        //Amtrak
        MarkerOptions amTrak= new MarkerOptions()
                .position(new LatLng(37.307610,-120.477389))
                .title("Amtrak")
                .snippet("Bus Company: CatTracks\nBus ID: E, E1, G, \n\nBus Company: TheBus \nBus ID: UC,")
                .icon(convertedIcon);

        //Merced Transpo
        MarkerOptions mTranspo= new MarkerOptions()
                .position(new LatLng(37.301829,-120.488223))
                .title("Merced Transport")
                .snippet("Bus Company: CatTracks\nBus ID: E, E1, G, \n\nBus Company: TheBus \nBus ID: UC,")
                .icon(convertedIcon);

        //The Bus MCAG Office
        MarkerOptions MCAG= new MarkerOptions()
                .position(new LatLng(37.307610,-120.477389))
                .title("The Bus - MCAG Office")
                .snippet("Bus Company: CatTracks\nBus ID: E, E1, G, \n\nBus Company: TheBus \nBus ID: UC,")
                .icon(convertedIcon);

        //M St. and Olive
        MarkerOptions mStOlive= new MarkerOptions()
                .position(new LatLng(37.318178,-120.478359))
                .title("M Street & Olive Avenue")
                .snippet("Bus Company: CatTracks\nBus ID: E, E1, G, \n\nBus Company: TheBus \nBus ID: UC,")
                .icon(convertedIcon);

        //Merced College UC Parking
        MarkerOptions mCollegeUCPark= new MarkerOptions()
                .position(new LatLng(37.338978,-120.476530))
                .title("Merced College - UC Parking")
                .snippet("Bus Company: CatTracks\nBus ID: E, E1, G, \n\nBus Company: TheBus \nBus ID: UC,")
                .icon(convertedIcon);

        //Paulson and Yosemite Avenue
        MarkerOptions PaulsonYosemite= new MarkerOptions()
                .position(new LatLng(37.332043,-120.460490))
                .title("Paulson and Yosemite Avenue")
                .snippet("Bus Company: CatTracks\nBus ID: E, E1, G, \n\nBus Company: TheBus \nBus ID: UC,")
                .icon(convertedIcon);



        List<MarkerOptions> AB = new ArrayList<>();
        AB.addAll(Arrays.asList(Muir, Emigrant, Castle, mCollege, Mammoth));
        BusStopsbyID.put("AB", AB);

        List<MarkerOptions> C1 = new ArrayList<>();

        C1.addAll(Arrays.asList(Granville, acrossPierOne, Meadows, Swiss, AlexG, Rite_Wall, G_Street_Portal, Mercy, arrowWood, Mammoth, Muir, Emigrant, TriCollege, G_Street_Portal, SunStar, ParkAve, Olivewood, AtPierOne));
        BusStopsbyID.put("C1", C1);

        List<MarkerOptions> C2 = new ArrayList<>();
        C2.addAll(Arrays.asList(rStreet, elRedondo, buenaVista, Target, mStreet, mCollege, Ironstone, arrowWood, Mammoth, Muir, Emigrant));
        BusStopsbyID.put("C2", C2);

        List<MarkerOptions> FastCat = new ArrayList<>();
        FastCat.addAll(Arrays.asList(moragaWest, uniSurgeryWest, starbucks, Mercy, arrowWood, Mammoth, Muir, Emigrant, arrowWoodLeave, TriCollege, inShape, uniSurgeryEast, moragaEast, MammothRepeat, MuirRepeat, EmigrantRepeat));
        BusStopsbyID.put("FastCat", FastCat);

        List<MarkerOptions> NiteCat = new ArrayList<>();
        NiteCat.addAll(Arrays.asList(Mammoth, Muir, Emigrant, rStreet, mallTheater, applebees, hollyTheatre, Rite_Wall, G_Street_Portal));
        BusStopsbyID.put("NiteCat", NiteCat);

        List<MarkerOptions> E = new ArrayList<>();
        E.addAll(Arrays.asList(Mammoth, Muir, Emigrant, moragaWest, starbucks, mCollege, Ironstone, arrowWood, rStreet, elRedondo, buenaVista, Target, mStreet, mallTheater, amTrak, hollyTheatre, Granville, Olivewood, Rite_Wall, inShape, moragaEast));
        BusStopsbyID.put("E", E);

        List<MarkerOptions> E1 = new ArrayList<>();
        E1.addAll(Arrays.asList(Mammoth, Muir, Emigrant, moragaWest, starbucks, mCollege, Target, mallTheater, amTrak, hollyTheatre, Granville, Olivewood, Rite_Wall, inShape, moragaEast));
        BusStopsbyID.put("E1", E1);

        List<MarkerOptions> E2 = new ArrayList<>();
        E2.addAll(Arrays.asList(Mammoth, Muir, Emigrant, moragaWest, starbucks, rStreet, elRedondo, buenaVista, Target, mStreet, mCollege, Ironstone, arrowWood));
        BusStopsbyID.put("E2", E2);

        List<MarkerOptions> G = new ArrayList<>();
        G.addAll(Arrays.asList(rStreet, elRedondo, mCollege, Ironstone, arrowWood, Mammoth, Muir, Emigrant, amTrak, hollyTheatre));
        BusStopsbyID.put("G", G);

        List<MarkerOptions> Heritage = new ArrayList<>();
        Heritage.addAll(Arrays.asList(rStreet, inShape, Mammoth, Muir, Emigrant));
        BusStopsbyID.put("Heritage", Heritage);

        List<MarkerOptions> UCSouth = new ArrayList<>();
        UCSouth.addAll(Arrays.asList(Muir, Emigrant, PaulsonYosemite, TriCollege, mCollege, mStOlive, amTrak, mTranspo));
        BusStopsbyID.put("UCSouth", UCSouth);

        List<MarkerOptions> UCNorth = new ArrayList<>();
        UCNorth.addAll(Arrays.asList(mTranspo, MCAG, amTrak, mStOlive, mCollege, mCollegeUCPark, PaulsonYosemite, Muir));
        BusStopsbyID.put("UCNorth", UCNorth);

        MapDone = true;
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
        //setupLayer();

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
                                //.proximity(Point.fromLngLat(originLocation.getLongitude(),originLocation.getLatitude()))
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
