package com.example.foo.spaceapp.asset;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.example.foo.spaceapp.R;
import com.example.foo.spaceapp.engine.GameEngine;
import com.example.foo.spaceapp.engine.GameObj;

/**
 * Created by foo on 12/4/16.
 */

public class Background extends GameObj {

    protected static final int UNITS_TO_MOVE = 20;
    protected float mPixelFactor;
    protected float mSpeedFactor;
    protected Bitmap mBitmap;
    protected float mWidth, mHeight;
    protected float mTargetWidth;
    protected float mPosX, mPosY;
    protected float mScreenW, mScreenH;
    protected Rect mSrc, mDst;

    public Background(GameEngine engine) {
        mPixelFactor = engine.mPixelFactor;
        mSpeedFactor = (UNITS_TO_MOVE / 1000f) * mPixelFactor;

        Context context = engine.mContext;
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.bg);
        BitmapDrawable bmd = (BitmapDrawable) drawable;
        mBitmap = bmd.getBitmap();

        mWidth = drawable.getIntrinsicWidth() * mPixelFactor;
        mHeight = drawable.getIntrinsicHeight() * mPixelFactor;

        mScreenW = engine.mMaxX;
        mScreenH = engine.mMaxY;

        mTargetWidth = Math.min(mWidth, mScreenW);

        mSrc = new Rect();
        mDst = new Rect();
    }

    @Override
    public void start() {
        mPosX = 0;
        mPosY = 0;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine engine) {
        mPosY += (mSpeedFactor * elapsedMillis);
    }

    @Override
    public void onDraw(Canvas canvas) {
        int l, t, r, b;
        if (mPosY < 0) {
            l = 0;
            t = (int) (-mPosY / mPixelFactor);
            r = (int) (mTargetWidth / mPixelFactor);
            b = (int) ((mScreenH - mPosY) / mPixelFactor);
            mSrc.set(l, t, r, b);
            l = 0;
            t = 0;
            r = (int) mTargetWidth;
            b = (int) mScreenH;
            mDst.set(l, t, r, b);
            canvas.drawBitmap(mBitmap, mSrc, mDst, null);
        } else {
            l = 0;
            t = 0;
            r = (int) (mTargetWidth / mPixelFactor);
            b = (int) ((mScreenH - mPosY) / mPixelFactor);
            mSrc.set(l, t, r, b);
            l = 0;
            t = (int) mPosY;
            r = (int) mTargetWidth;
            b = (int) mScreenH;
            mDst.set(l, t, r, b);
            canvas.drawBitmap(mBitmap, mSrc, mDst, null);

            // draw prevous block
            l = 0;
            t = (int) ((mHeight - mPosY) / mPixelFactor);
            r = (int) (mTargetWidth / mPixelFactor);
            b = (int) (mHeight / mPixelFactor);
            mSrc.set(l, t, r, b);
            l = 0;
            t = 0;
            r = (int) mTargetWidth;
            b = (int) mPosY;
            mDst.set(l, t, r, b);
            canvas.drawBitmap(mBitmap, mSrc, mDst, null);
        }

        if (mPosY > mScreenH) {
            mPosY -= mHeight;
        }
    }
}
