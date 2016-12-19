package com.example.foo.spaceapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    protected static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = this.getSupportFragmentManager();
        Fragment f = fm.findFragmentById(R.id.fragment_container);
        if (f == null) {
            f = new MainFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, f)
                    .commit();
        }
    }

    public void navigateBack() {
        super.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed() called..");
        BaseGameFragment f = (BaseGameFragment) this.getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        Log.d(TAG, f.toString());
        boolean backPressed = f.onBackPressed();
        if (!backPressed) {
            super.onBackPressed();
        }
    }
}
