package com.vancu.findmytrackalpha1.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.vancu.findmytrackalpha1.LoggedInMainMenuActivity;
import com.vancu.findmytrackalpha1.LoggedInScheduleActivity;
import com.vancu.findmytrackalpha1.LoggedInSettingsActivity;
import com.vancu.findmytrackalpha1.LoggedInViewMapActivity;
import com.vancu.findmytrackalpha1.LoggedInViewSchedule;
import com.vancu.findmytrackalpha1.R;
import com.vancu.findmytrackalpha1.SearchStops;

/**
 * Created by Vancu on 4/1/18.
 */

public class BottomNavigationViewHelper {

    public static void setupBottomNaviView(BottomNavigationViewEx bottomNav)
    {
        bottomNav.enableAnimation(false);
        bottomNav.enableItemShiftingMode(false);
        bottomNav.enableShiftingMode(false);
    }

    public static void enableNavigation(final Context context, BottomNavigationViewEx view)
    {
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent intent0 = new Intent(context, LoggedInMainMenuActivity.class); //ACTIVITY_NUM = 0
                        context.startActivity(intent0);
                        return true;

                    case R.id.navigation_map:
                        Intent intent1 = new Intent(context, LoggedInViewMapActivity.class); //ACTIVITY_NUM = 1
                        context.startActivity(intent1);
                        return true;

                    case R.id.navigation_schedule:
                        Intent intent2 = new Intent(context, LoggedInViewSchedule.class); //ACTIVITY_NUM = 2
                        context.startActivity(intent2);
                        return true;

                    case R.id.navigation_stops:
                        Intent intent3 = new Intent(context, SearchStops.class); //ACTIVITY_NUM = 3
                        context.startActivity(intent3);
                        return true;

                    case R.id.navigation_settings:
                        Intent intent4 = new Intent(context, LoggedInSettingsActivity.class); //ACTIVITY_NUM = 4
                        context.startActivity(intent4);
                        return true;
                }
                return false;
            }
        });
    }
}
