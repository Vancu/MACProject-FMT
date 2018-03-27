package com.vancu.findmytrackalpha1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

public class LoggedInMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_main);
    }

    public void bStops(View view){
        Intent intent = new Intent (this, SearchStops.class);
        startActivity(intent);
    }
}
