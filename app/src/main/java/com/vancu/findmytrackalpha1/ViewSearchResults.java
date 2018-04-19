package com.vancu.findmytrackalpha1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.text.TextUtils;
import java.util.HashMap;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.gson.JsonParser;

public class ViewSearchResults extends AppCompatActivity {

    String HttpUrl = "http://73.220.191.198:13379/PullBusRouteData.php";
    ArrayList<HashMap<String, String>> List;
    int count;
    HttpParse httpParse = new HttpParse();
    String BusCompany, BusID, TAG_TIME, TAG_STOP, TAG_COUNT;
    ProgressDialog progressDialog;
    Vector<Vector<String>> Data = new Vector<Vector<String>>();

    JSONParser jParser = new JSONParser();

    String TAG_NAME = "stops";

    JSONArray stops = null;
    private ListView lvStops;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_search_results);

        Bundle extras = getIntent().getExtras();
        BusCompany = extras.getString("BusCompany");
        BusID = extras.getString("BusID");

        List = new ArrayList<HashMap<String, String>>();

        switch (BusID){
            case "AB":
                BusID = "cattracksAB";
                break;
            case "C1Blue":
                BusID = "cattracksc1b";
                break;
            case "C1Gold":
                BusID = "cattracksc1g";
                break;
            case "C2":
                BusID = "cattracksc2";
                break;
            case "FastCat":
                BusID = "cattracksfastcat";
                break;
            case "NiteCat":
                BusID = "cattracksnitecat";
                break;
            case "E":
                BusID = "cattrackse";
                break;
            case "E1":
                BusID = "cattrackse1";
                break;
            case "E2":
                BusID = "cattrackse2";
                break;
            case "G":
                BusID = "cattracksg";
                break;
            case "HeritageWeek":
                BusID = "cattracksheritagemf";
                break;
            case "HeritageWeekend":
                BusID = "cattracksheritagess";
                break;
            case "UCNorth":
                BusID = "ucbustimesnorth";
                break;
            case "UCSouth":
                BusID = "ucbustimessouth";
                break;
            default:
                break;
        }
        LoadFunction(BusID);

        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        lvStops = (ListView) findViewById(R.id.lvStops);

        // Instanciating an array list (you don't need to do this,
        // you already have yours).
        final ArrayList<String> your_array_list = new ArrayList<String>();
        Vector<String> currVec = new Vector<String>();
        String name;
        for(int i = 0; i < Data.size(); i++)
        {
            currVec = Data.get(i);
            name = currVec.get(0);
            your_array_list.add(name);

        }

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                your_array_list );

        lvStops.setAdapter(arrayAdapter);

        lvStops.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent ViewTimeStop = new Intent (ViewSearchResults.this, ViewTimeSearchResults.class);

                ArrayList<String> TimeStops = new ArrayList<String>();
                Vector<String> currVec = new Vector<String>();
                String stoptime;
                for (int i = 1; i < Data.get(position).size(); i++)
                {
                    currVec = Data.get(position);
                    stoptime = currVec.get(i);
                    System.out.print(stoptime);
                    //Objects.equals("11:59:59", stoptime);
                    if (!stoptime.equals("11:59:59") && !stoptime.equals("23:59:59"))
                        TimeStops.add(stoptime);
                }
                ViewTimeStop.putExtra("test", TimeStops);
                ViewTimeStop.putExtra("nameofStop", parent.getItemAtPosition(position).toString());
                startActivity(ViewTimeStop);
            }
        });

    }

    public void LoadFunction(final String BusID) {
        class LoadAllData extends AsyncTask<String, String, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(ViewSearchResults.this);
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

}