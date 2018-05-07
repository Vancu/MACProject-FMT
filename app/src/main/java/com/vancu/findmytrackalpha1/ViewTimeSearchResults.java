package com.vancu.findmytrackalpha1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ViewTimeSearchResults extends AppCompatActivity {

    private ListView lvStopTimes;
    String stopName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_time_search_results);

        lvStopTimes = (ListView) findViewById(R.id.lvStopTimes);
        ArrayList<String> RealTimeStops = new ArrayList<String>();
        RealTimeStops = getIntent().getExtras().getStringArrayList("test");
        stopName = getIntent().getExtras().getString("nameofStop");

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                RealTimeStops);

        lvStopTimes.setAdapter(arrayAdapter);
        lvStopTimes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(ViewTimeSearchResults.this,SelectSavedSchedule.class);
                    intent.putExtra("nameStop",stopName);
                    intent.putExtra("time",parent.getItemAtPosition(position).toString());
                    startActivity(intent);
            }
        });

    }
}
