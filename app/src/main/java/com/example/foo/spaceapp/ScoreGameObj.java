package com.example.foo.spaceapp;

import android.graphics.Canvas;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.foo.spaceapp.engine.GameEngine;
import com.example.foo.spaceapp.engine.GameObj;

/**
 * Created by foo on 11/13/16.
 */

public class ScoreGameObj extends GameObj {

    protected TextView mTxtScore;
    protected long mScore;

    public ScoreGameObj(View view, int idRes) {
        mTxtScore = (TextView) view.findViewById(idRes);
    }

    @Override
    public void start() {
        mScore = 0;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine engine) {
        mScore += elapsedMillis;
    }

    @Override
    public void onDraw(Canvas canvas) {
        mTxtScore.setText(String.valueOf(mScore));
    }
}
