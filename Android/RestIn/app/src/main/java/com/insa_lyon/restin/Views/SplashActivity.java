package com.insa_lyon.restin.Views;

/**
 * Created by Nico on 07/01/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, MapActivity.class);
        this.startActivity(intent);
        this.finish();
    }


}
