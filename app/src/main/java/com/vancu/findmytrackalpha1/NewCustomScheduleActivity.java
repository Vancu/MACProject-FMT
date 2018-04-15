package com.vancu.findmytrackalpha1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class NewCustomScheduleActivity extends AppCompatActivity {

    Spinner TimeDeparture, Repeat;

    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_custom_schedule);

        TimeDeparture = findViewById(R.id.TimeDepatureSpinner);
        adapter = ArrayAdapter.createFromResource(this,R.array.TimeDeparture,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TimeDeparture.setAdapter(adapter);
        TimeDeparture.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Repeat = findViewById(R.id.RepeatSpinner);
        adapter = ArrayAdapter.createFromResource(this,R.array.RepeatArray,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Repeat.setAdapter(adapter);
        Repeat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void bSearchResultsTest1(View view)
    {
        Intent intent = new Intent(this,ViewSearchResults.class);
        startActivity(intent);
    }
}
