package com.vancu.findmytrackalpha1;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.vancu.findmytrackalpha1.utils.BottomNavigationViewHelper;

public class LoggedInScheduleActivity extends AppCompatActivity {

    private static final int ACTIVITY_NUM = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_schedule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();

                /*
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(LoggedInScheduleActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_add_stop, null);

                EditText mSchedule = (EditText) mView.findViewById(tSelectNewStop);
                Button bSearch = (Button) mView.findViewById(bSearchStop);
                Button bCustom = (Button) mView.findViewById(bCustomStop);
                Button bCancelStop = (Button) mView.findViewById(bCancel);

                bSearch.setOnClickListener(new View.OnClickListener(){
                    @Override


                });

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                */
            }
        });
        setupBottomNavBar();
    }

    //sets up the bottom navigation view for current activitiy.
    public void setupBottomNavBar()
    {
        BottomNavigationViewEx BottomNavEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavBar);
        BottomNavigationViewHelper.setupBottomNaviView(BottomNavEx);
        BottomNavigationViewHelper.enableNavigation(LoggedInScheduleActivity.this, BottomNavEx);
        Menu menu = BottomNavEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    public void openDialog()
    {
        SearchStopsDialog TestDialog = new SearchStopsDialog();
        TestDialog.show(getSupportFragmentManager(), "example Dialog");
    }

}
