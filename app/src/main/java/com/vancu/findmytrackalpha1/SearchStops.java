package com.vancu.findmytrackalpha1;

import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.SpinnerAdapter;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.Spinner;

public class SearchStops extends AppCompatActivity
{

    Spinner BusCompany, BusID, Ranges;

    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_stops);
        BusCompany = (Spinner) findViewById(R.id.busCompanyspinner);
        adapter = ArrayAdapter.createFromResource(this,R.array.buseCompanyarray,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        BusCompany.setAdapter(adapter);
        BusID = (Spinner) findViewById(R.id.CattrackIDspinner);
        Ranges = (Spinner) findViewById(R.id.Ranges);
        Ranges.setAdapter(adapter);
        BusID.setAdapter(adapter);
        Ranges.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int position,long id)
            {
                Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+" is selected",Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        BusID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int position,long id)
            {
                Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+" is selected",Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        BusCompany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int position,long id)
            {
                Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+" is selected",Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });



    }

}
