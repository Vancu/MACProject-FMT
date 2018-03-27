package com.vancu.findmytrackalpha1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.view.View.OnClickListener;
import android.widget.Spinner;

public class SearchStops extends AppCompatActivity {

/*    Spinner BusCompany = (Spinner) findViewById(R.id.busCompanyspinner);
    Spinner BusID = (Spinner) findViewById(R.id.CattrackIDspinner);
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.buseCompanyarray,android.R.layout.simple_spinner_item);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    BusCompany.setAdapter(adapter);
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_stops);
    }

}
