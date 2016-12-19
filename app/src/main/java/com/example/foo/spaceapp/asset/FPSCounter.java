package com.example.foo.spaceapp.asset;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.foo.spaceapp.engine.GameEngine;
import com.example.foo.spaceapp.engine.GameObj;

/**
 * Created by foo on 11/25/16.
 */

public class FPSCounter extends GameObj {

    protected static final int TEXT_HEIGHT = 40;
    protected static final int TEXT_WIDTH = 80;

    protected float mPixelFactor;
    protected Paint mPaint;
    protected float mTextWidth, mTextHeight;
    protected float mFps;
    protected String mTextFps;
    protected long mTotalMillis;
    protected int mNumDraws;

    public FPSCounter(GameEngine engine) {
        mPixelFactor = engine.mPixelFactor;
        mPaint = new Paint();
        mPaint.setTextAlign(Paint.Align.CENTER);

        mTextHeight = TEXT_HEIGHT * mPixelFactor;
        mTextWidth = TEXT_WIDTH * mPixelFactor;
        float textSize = mTextHeight / 2.5f;

        mPaint.setTextSize(textSize);
    }

    @Override
    public void start() {
        mTotalMillis = 0;
        mTextFps = "0 FPS";
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine engine) {
        mTotalMillis += elapsedMillis;
        int oneSec = 1000;
        if (mTotalMillis > oneSec) {
            mFps = (mNumDraws * oneSec) / mTotalMillis;
            mTextFps = mFps + " FPS";
            mTotalMillis = 0;
            mNumDraws = 0;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        mPaint.setColor(Color.WHITE);
        canvas.drawRect(0, 0, mTextWidth, mTextHeight, mPaint);
        mPaint.setColor(Color.BLACK);
        canvas.drawText(mTextFps, (mTextWidth / 2), (mTextHeight / 1.5f), mPaint);
        mNumDraws++;
    }
}
