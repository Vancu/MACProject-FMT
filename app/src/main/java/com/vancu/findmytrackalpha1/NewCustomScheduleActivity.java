package com.vancu.findmytrackalpha1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ToggleButton;
import android.widget.CompoundButton;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.io.File;

public class NewCustomScheduleActivity extends AppCompatActivity {

    String hello;
    boolean boolSun, boolMon, boolTue, boolWed, boolThur, boolFri, boolSat, PM;
    Spinner spinHour, spinMinute, spinRepeat, spinAMPM;
    EditText etNameForSchedule, etBusStopAddress;
    Button bSearch;
    ToggleButton tbSun, tbMon, tbTue, tbWed, tbThur, tbFri, tbSat;
    int min, hour;
    ArrayList<Schedule> list = new ArrayList<>();

    ArrayAdapter<CharSequence> adapter;

    protected void writeObjectFile() {
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
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_custom_schedule);

        boolSun = false;
        boolMon = false;
        boolTue = false;
        boolWed = false;
        boolThur = false;
        boolFri = false;
        boolSat = false;

        etNameForSchedule = findViewById(R.id.etNameSchedule);
        etBusStopAddress = findViewById(R.id.etBusStopAddress);
        bSearch = findViewById(R.id.bSearch);
        tbSun = findViewById(R.id.bSun);
        tbMon = findViewById(R.id.bMon);
        tbTue = findViewById(R.id.bTue);
        tbWed = findViewById(R.id.bWed);
        tbThur = findViewById(R.id.bThur);
        tbFri = findViewById(R.id.bFri);
        tbSat = findViewById(R.id.bSat);

        tbSun.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    boolSun = true;
                }
                else{
                    boolSun = false;
                }
            }
        });
        tbMon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    boolMon = true;
                }
                else{
                    boolMon = false;
                }
            }
        });
        tbTue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    boolTue = true;
                }
                else{
                    boolTue = false;
                }
            }
        });
        tbWed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    boolWed = true;
                }
                else{
                    boolWed = false;
                }
            }
        });
        tbThur.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    boolThur = true;
                }
                else{
                    boolThur = false;
                }
            }
        });
        tbFri.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    boolFri = true;
                }
                else{
                    boolFri = false;
                }
            }
        });
        tbSat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    boolSat = true;
                }
                else{
                    boolSat = false;
                }
            }
        });

        spinHour = findViewById(R.id.hourSpinner);
        adapter = ArrayAdapter.createFromResource(this,R.array.hours,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinHour.setAdapter(adapter);
        spinHour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                hour = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinMinute = findViewById(R.id.minuteSpinner);
        adapter = ArrayAdapter.createFromResource(this,R.array.minutes,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinMinute.setAdapter(adapter);
        spinMinute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                min = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinAMPM = findViewById(R.id.ampmSpinner);
        adapter = ArrayAdapter.createFromResource(this,R.array.AMPM,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinAMPM.setAdapter(adapter);
        spinAMPM.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).toString() == "PM") {
                    PM = true;
                }
                else
                {
                    PM = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinRepeat = findViewById(R.id.RepeatSpinner);
        adapter = ArrayAdapter.createFromResource(this,R.array.RepeatArray,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinRepeat.setAdapter(adapter);
        spinRepeat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void bCreate(View view) {
        Schedule tempSchedule = new Schedule(etNameForSchedule.getText().toString());
        Stop tempStop = new Stop(etBusStopAddress.getText().toString());
        Time tempTime = new Time();
        if(PM)
        {
            hour += 12;
        }
        tempTime.setHour(hour);
        tempTime.setMinute(min);
        tempStop.addTime(tempTime);
        tempSchedule.addStop(tempStop);
        readObjectFile(tempSchedule);
    }

}
