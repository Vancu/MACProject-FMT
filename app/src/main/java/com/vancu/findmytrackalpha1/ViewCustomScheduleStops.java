package com.vancu.findmytrackalpha1;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class ViewCustomScheduleStops extends AppCompatActivity {

    ArrayList<Schedule> list = new ArrayList<>();
    ArrayList<Stop> stops = new ArrayList<>();
    ArrayList<String> stopNames = new ArrayList<>();
    ListView lvStops;
    Schedule scheduleInput;
    String name;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_custom_schedule_stops);

        try {
            FileInputStream fis = openFileInput("customScheduleList.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            list = (ArrayList<Schedule>)ois.readObject();
            ois.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        pos = getIntent().getExtras().getInt("pos");

        /*for(int i = 0; i < list.size(); i++)
        {
            if(list.get(i).name.equals(name))
            {
                stops = list.get(i).stops;
                break;
            }
        }*/

        stops = list.get(pos).stops;

        for(int i = 0; i < stops.size(); i++)
        {
            stopNames.add(stops.get(i).getName());
        }
        lvStops = findViewById(R.id.lvCustomStops);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,stopNames);
        lvStops.setAdapter(arrayAdapter);


    }

}
