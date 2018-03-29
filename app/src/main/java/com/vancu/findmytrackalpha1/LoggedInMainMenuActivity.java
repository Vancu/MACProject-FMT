package com.vancu.findmytrackalpha1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
//import android.widget.Button;

public class LoggedInMainMenuActivity extends AppCompatActivity {

    private TextView mTextMessage;
    //private Button schedule = (Button) findViewById(R.id.userSchedule);

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //schedule.setVisibility(View.GONE);
            switch (item.getItemId()) {
                case R.id.navigation_map:
                    mTextMessage.setText(R.string.title_map);
                    return true;
                case R.id.navigation_schedule:
                    mTextMessage.setText(R.string.title_user_schedule);
                   // schedule.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_stops:
                    mTextMessage.setText(R.string.title_stops);
                    return true;
                case R.id.navigation_settings:
                    mTextMessage.setText(R.string.title_settings);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_main_menu);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void bLoggedInViewSchedule(View view){
        Intent intent = new Intent (this, LoggedInScheduleActivity.class);
        startActivity(intent);
    }

    public void bLoggedInViewMaps(View view){
        Intent intent = new Intent (this, LoggedInViewMapActivity.class);
        startActivity(intent);
    }

    public void bLoggedInViewStops(View view){
        Intent intent = new Intent (this, SearchStops.class);
        startActivity(intent);
    }

    public void bLoggedInViewSettings(View view){
        Intent intent = new Intent (this, LoggedInSettingsActivity.class);
        startActivity(intent);
    }

    public void bLoggedInSearchStops(View view){
        Intent intent = new Intent(this,NewStopSchedule.class);
        startActivity(intent);
    }
    public void bLoggedInCustomStops(View view){
        Intent intent = new Intent(this,NewCustomScheduleActivity.class);
        startActivity(intent);
    }

}
