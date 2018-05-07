package com.vancu.findmytrackalpha1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SelectSavedSchedule extends AppCompatActivity {

    String stopname;
    ListView lvCustomSchedule;
    ArrayList<Schedule> list = new ArrayList<>();
    ArrayList<String> schedulenames = new ArrayList<>();

    protected void writeObjectFile(ArrayList<Schedule> inputlist) {
        try {
            FileOutputStream fos = openFileOutput("customScheduleList.ser", this.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(inputlist);
            oos.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_saved_schedule);

        stopname = getIntent().getExtras().getString("nameStop");

        try
        {
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

        for(int i = 0; i < list.size(); i++)
        {
            schedulenames.add(list.get(i).getName());
        }


        lvCustomSchedule = findViewById(R.id.lvSelectCustomSchedules);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,schedulenames);
        lvCustomSchedule.setAdapter(arrayAdapter);

        lvCustomSchedule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SelectSavedSchedule.this,LoggedInScheduleActivity.class);
                Stop tempstop = new Stop(stopname);
                Time temptime = new Time();
                String time = getIntent().getExtras().getString("time");
                temptime.setStringTime(time);
                tempstop.addTime(temptime);
                list.get(position).addStop(tempstop);
                writeObjectFile(list);
                startActivity(intent);
            }
        });

    }
}
