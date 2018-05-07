package com.vancu.findmytrackalpha1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.vancu.findmytrackalpha1.utils.BottomNavigationViewHelper;

import java.io.File;

public class LoggedInSettingsActivity extends AppCompatActivity {

    private static final int ACTIVITY_NUM = 4;
    Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_settings);

        setupBottomNavBar();
    }

    //sets up the bottom navigation view for current activitiy.
    public void setupBottomNavBar()
    {
        BottomNavigationViewEx BottomNavEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavBar);
        BottomNavigationViewHelper.setupBottomNaviView(BottomNavEx);
        BottomNavigationViewHelper.enableNavigation(LoggedInSettingsActivity.this, BottomNavEx);
        Menu menu = BottomNavEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);


    }

    public void bDelete(View view) {
        String dir = getFilesDir().getAbsolutePath();
        File f0 = new File(dir,"customScheduleList.ser");
        boolean deleted = f0.delete();
    }
}
