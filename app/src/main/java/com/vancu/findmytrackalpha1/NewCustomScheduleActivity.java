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

public class NewCustomScheduleActivity extends AppCompatActivity {

    boolean boolSun, boolMon, boolTue, boolWed, boolThur, boolFri, boolSat;
    Spinner spinTimeDeparture, spinRepeat;
    EditText etNameForSchedule, etBusStopAddress;
    Button bSearch;
    ToggleButton tbSun, tbMon, tbTue, tbWed, tbThur, tbFri, tbSat;

    ArrayAdapter<CharSequence> adapter;

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

        spinTimeDeparture = findViewById(R.id.TimeDepatureSpinner);
        adapter = ArrayAdapter.createFromResource(this,R.array.TimeDeparture,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinTimeDeparture.setAdapter(adapter);
        spinTimeDeparture.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

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

        bSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Schedule tempSchedule = new Schedule(etNameForSchedule.getText().toString());
                Stop tempStop = new Stop(etBusStopAddress.getText().toString());
                Time tempTime = new Time();
            }
        });
    }

    public void bSearchResultsTest1(View view)
    {
        Intent intent = new Intent(this,ViewSearchResults.class);
        startActivity(intent);
    }
}
