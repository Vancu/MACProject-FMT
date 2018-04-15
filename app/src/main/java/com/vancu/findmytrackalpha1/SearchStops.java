package com.vancu.findmytrackalpha1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.content.Intent;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.vancu.findmytrackalpha1.utils.BottomNavigationViewHelper;

public class SearchStops extends AppCompatActivity
{

    private static final int ACTIVITY_NUM = 3;
    Spinner BusCompany, BusID, Ranges;

    String BusCompanyString, BusIDString;

    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_stops);

        setupBottomNavBar();

        BusCompany = (Spinner) findViewById(R.id.busCompanyspinner);
        adapter = ArrayAdapter.createFromResource(this,R.array.busCompanyarray,android.R.layout.simple_spinner_item);
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
                BusCompanyString = parent.getItemAtPosition(position).toString();
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
                BusIDString = parent.getItemAtPosition(position).toString();
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

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }

    public void bSearchBusStops(View view)
    {
        Intent intent = new Intent(this,ViewSearchResults.class);
        intent.putExtra("BusCompany",BusCompanyString);
        intent.putExtra("BusID",BusIDString);
        startActivity(intent);
    }

    /*public void bNewStopScheduleTest(View view)
    {
        Intent intent = new Intent(this,NewStopSchedule.class);
        intent.putExtra("BusCompany",BusCompanyString);
        intent.putExtra("BusID",BusIDString);
        startActivity(intent);
    }*/

    public void bNewCustomScheduleTest(View view)
    {
        Intent intent = new Intent(this,NewCustomScheduleActivity.class);
        startActivity(intent);
    }

    //sets up the bottom navigation view for current activitiy.
    public void setupBottomNavBar()
    {
        BottomNavigationViewEx BottomNavEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavBar);
        BottomNavigationViewHelper.setupBottomNaviView(BottomNavEx);
        BottomNavigationViewHelper.enableNavigation(SearchStops.this, BottomNavEx);
        Menu menu = BottomNavEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

}
