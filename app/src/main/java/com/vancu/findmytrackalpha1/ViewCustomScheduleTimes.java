package com.vancu.findmytrackalpha1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class ViewCustomScheduleTimes extends AppCompatActivity {

    ArrayList<Schedule> list = new ArrayList<>();
    ArrayList<Time> times = new ArrayList<>();
    ArrayList<String> timesString = new ArrayList<>();
    ListView lvTimes;
    Schedule scheduleInput;
    String name;
    int pos;
    int pos2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_custom_schedule_times);

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
        pos2 = getIntent().getExtras().getInt("pos2");

        times = list.get(pos).stops.get(pos2).times;

        for(int i = 0; i < times.size(); i++)
        {
            timesString.add(times.get(i).getstringTime());
        }

        lvTimes = findViewById(R.id.lvCustomTimes);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,timesString);
        lvTimes.setAdapter(arrayAdapter);

        lvTimes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}
