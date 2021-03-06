package com.vancu.findmytrackalpha1;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.io.*;
import java.util.ArrayList;
import android.widget.EditText;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.vancu.findmytrackalpha1.utils.BottomNavigationViewHelper;

public class LoggedInViewSchedule extends AppCompatActivity {

    private static final int ACTIVITY_NUM = 2;
    ListView lvCustomSchedules;
    ArrayList<Schedule> list = new ArrayList<>();
    ArrayList<String> listshow = new ArrayList<>();
    String scheduleName;


    /*protected void writeObjectFile() {
        File file = new File(this.getFilesDir(), "customScheduleList.ser");
        try {
//            FileOutputStream fos = new FileOutputStream("customScheduleList.ser");
            FileOutputStream fos = openFileOutput("customScheduleList.ser", this.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
            oos.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void readObjectFile(Schedule temp){
        try {
            // see if file exists
            FileInputStream fis = openFileInput("customScheduleList.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            // add schedule to list if exists
            list = (ArrayList<Schedule>)ois.readObject();
            list.add(temp);
            writeObjectFile();
            ois.close();
        }
        catch (FileNotFoundException e) {
            writeObjectFile();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_schedule);
        setupBottomNavBar();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();

            }
        });

        //findViewById(R.id.scheduleInput);

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

        for(int i = 0; i < list.size(); i++)
        {
            for(int j = 0; j < list.get(i).stops.size(); j++)
            {
                listshow.add(list.get(i).name);
            }
        }

        lvCustomSchedules = findViewById(R.id.lvCustomSchedules);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listshow);
        lvCustomSchedules.setAdapter(arrayAdapter);

        lvCustomSchedules.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LoggedInViewSchedule.this,ViewCustomScheduleStops.class);
                intent.putExtra("pos", position);
                startActivity(intent);
            }
        });





    }

    //sets up the bottom navigation view for current activitiy.
    public void setupBottomNavBar()
    {
        BottomNavigationViewEx BottomNavEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavBar);
        BottomNavigationViewHelper.setupBottomNaviView(BottomNavEx);
        BottomNavigationViewHelper.enableNavigation(LoggedInViewSchedule.this, BottomNavEx);
        Menu menu = BottomNavEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    public void bNewStopScheduleTest(View view)
    {
        Intent intent = new Intent(LoggedInViewSchedule.this,SearchStops.class);
        Schedule temp = new Schedule();
        //temp.setName();
        startActivity(intent);
    }

    public void bNewCustomScheduleTest(View view)
    {
        Intent intent = new Intent(this,NewCustomScheduleActivity.class);
        startActivity(intent);
    }

    public void openDialog()
    {
        SearchStopsDialog TestDialog = new SearchStopsDialog();
        TestDialog.show(getSupportFragmentManager(), "example Dialog");
    }

}
