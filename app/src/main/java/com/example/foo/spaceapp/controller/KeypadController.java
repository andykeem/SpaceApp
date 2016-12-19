package com.example.foo.spaceapp.controller;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.foo.spaceapp.R;

/**
 * Created by foo on 11/20/16.
 */

public class KeypadController extends InputController implements View.OnTouchListener {

    protected static final String TAG = "KeypadController";

    public KeypadController(View view) {
        view.findViewById(R.id.keypad_down).setOnTouchListener(this);
        view.findViewById(R.id.keypad_left).setOnTouchListener(this);
        view.findViewById(R.id.keypad_right).setOnTouchListener(this);
        view.findViewById(R.id.keypad_up).setOnTouchListener(this);
        view.findViewById(R.id.btn_fire).setOnTouchListener(this);
    }

    public boolean onTouch(View view, MotionEvent event) {
        int action = event.getActionMasked();
        if (action == MotionEvent.ACTION_DOWN) {
            switch (view.getId()) {
                case R.id.keypad_down:
                    mVerticalFactor += 1;
                    break;
                case R.id.keypad_left:
                    mHorizontalFactor -= 1;
                    break;
                case R.id.keypad_right:
                    mHorizontalFactor += 1;
                    break;
                case R.id.keypad_up:
                    mVerticalFactor -= 1;
                    break;
                case R.id.btn_fire:
                    mFiring = true;
                    break;
            }
        } else if (action == MotionEvent.ACTION_UP) {
            switch (view.getId()) {
                case R.id.keypad_down:
                    mVerticalFactor -= 1;
                    break;
                case R.id.keypad_left:
                    mHorizontalFactor += 1;
                    break;
                case R.id.keypad_right:
                    mHorizontalFactor -= 1;
                    break;
                case R.id.keypad_up:
                    mVerticalFactor += 1;
                    break;
                case R.id.btn_fire:
                    mFiring = false;
                    break;
            }
        }
        return false;
    }
}
