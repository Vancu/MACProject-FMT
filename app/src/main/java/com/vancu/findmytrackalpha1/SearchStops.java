package com.vancu.findmytrackalpha1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.Spinner;
import android.content.Intent;

public class SearchStops extends AppCompatActivity
{

    Spinner BusCompany, BusID, Ranges;

    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_stops);

        BusCompany = (Spinner) findViewById(R.id.busCompanyspinner);
        adapter = ArrayAdapter.createFromResource(this,R.array.buseCompanyarray,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BusCompany.setAdapter(adapter);

        BusID = (Spinner) findViewById(R.id.BusIDspinner);
        adapter = ArrayAdapter.createFromResource(this,R.array.emptySpinner,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BusID.setAdapter(adapter);

        Ranges = (Spinner) findViewById(R.id.Ranges);
        adapter = ArrayAdapter.createFromResource(this,R.array.MileRanges,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Ranges.setAdapter(adapter);

        BusCompany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int position,long id)
            {
                Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+" is selected",Toast.LENGTH_LONG).show();
                if(position == 0)
                {
                    //sets BusID to cattracks
                    adapter = ArrayAdapter.createFromResource(SearchStops.this,R.array.CattracksIDarray,android.R.layout.simple_spinner_item);
                    BusID.setAdapter(adapter);
                }
                else if(position == 1)
                {
                    adapter = ArrayAdapter.createFromResource(SearchStops.this,R.array.MercedTheBusIDarray,android.R.layout.simple_spinner_item);
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
                Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+" is selected",Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        Ranges.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
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

    public void bNewStopScheduleTest(View view)
    {
        Intent intent = new Intent(this,NewStopSchedule.class);
        startActivity(intent);
    }

}
