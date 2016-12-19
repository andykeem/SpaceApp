package com.example.foo.spaceapp.asset;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.example.foo.spaceapp.collision.BodyType;
import com.example.foo.spaceapp.engine.GameEngine;
import com.example.foo.spaceapp.collision.ScreenGameObj;

/**
 * Created by foo on 11/24/16.
 */

public abstract class Sprite extends ScreenGameObj {

    protected GameEngine mGameEngine;
    protected float mPixelFactor;
    protected Bitmap mBitmap;
    protected Matrix mMatrix;
    public float mRotation;
    protected float mRotationSpeed;
    protected Paint mPaint;
    protected float mScale;
    public int mAlpha;

    protected Sprite(GameEngine engine, int drawableRes) {
        mGameEngine = engine;
        mPixelFactor = mGameEngine.mPixelFactor;

        Drawable drawable = ContextCompat.getDrawable(mGameEngine.mContext, drawableRes);
        BitmapDrawable bmDrawable = (BitmapDrawable) drawable;
        mBitmap = bmDrawable.getBitmap();

        mWidth = (int) (drawable.getIntrinsicWidth() * mPixelFactor);
        mHeight = (int) (drawable.getIntrinsicHeight() * mPixelFactor);

        mMatrix = new Matrix();
        mPaint = new Paint();

        mRadius = (Math.max(mWidth, mHeight) / 2);
        mScale = 1f;
        mAlpha = 255;
    }

    @Override
    public void onDraw(Canvas canvas) {
        if ((mX > canvas.getWidth()) || (mY > canvas.getHeight()) ||
                (mX < -mWidth) || (mY < -mHeight)) {
            return;
        }

        mPaint.setColor(Color.YELLOW);
        if (mBodyType == BodyType.Rectangular) {
            canvas.drawRect(mBoundingRect, mPaint);
        } else if (mBodyType == mBodyType.Circular) {
            float cx = mX + mWidth / 2,
                    cy = mY + mHeight / 2;
            canvas.drawCircle(cx, cy, mRadius, mPaint);
        }

        mMatrix.reset();

        float scale = (mScale * mPixelFactor);
        mMatrix.postScale(scale, scale);
        mMatrix.postTranslate(mX, mY);
        mMatrix.postRotate(mRotation, (mX + ((mWidth * mScale) / 2)), (mY + ((mHeight * mScale) / 2)));

        mPaint.setAlpha(mAlpha);
        canvas.drawBitmap(mBitmap, mMatrix, mPaint);
    }
}
