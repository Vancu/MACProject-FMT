package com.vancu.findmytrackalpha1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ViewTimeSearchResults extends AppCompatActivity {

    private ListView lvStopTimes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_time_search_results);

        lvStopTimes = (ListView) findViewById(R.id.lvStopTimes);
        ArrayList<String> RealTimeStops = new ArrayList<String>();
        RealTimeStops = getIntent().getExtras().getStringArrayList("test");

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                RealTimeStops);

        lvStopTimes.setAdapter(arrayAdapter);

    }
}
