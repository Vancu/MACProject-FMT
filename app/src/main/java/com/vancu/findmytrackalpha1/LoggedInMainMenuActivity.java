package com.vancu.findmytrackalpha1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.vancu.findmytrackalpha1.utils.BottomNavigationViewHelper;
//import android.widget.Button;

public class LoggedInMainMenuActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private static final int ACTIVITY_NUM = 0;
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
        /*
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottomNavBar);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        */
        setupBottomNavBar();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();

            }
        });
    }

    //sets up the bottom navigation view for current activitiy.
    public void setupBottomNavBar()
    {
        BottomNavigationViewEx BottomNavEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavBar);
        BottomNavigationViewHelper.setupBottomNaviView(BottomNavEx);
        BottomNavigationViewHelper.enableNavigation(LoggedInMainMenuActivity.this, BottomNavEx);
        Menu menu = BottomNavEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

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

    public void openDialog(){
        Intent intent = new Intent(this,LoginRegisterActivity.class);
        startActivity(intent);
    }

}
