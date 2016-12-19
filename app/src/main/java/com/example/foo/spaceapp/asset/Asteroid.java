package com.example.foo.spaceapp.asset;

import com.example.foo.spaceapp.collision.BodyType;
import com.example.foo.spaceapp.engine.GameEngine;
import com.example.foo.spaceapp.engine.GameObj;

import java.util.Random;

/**
 * Created by foo on 11/27/16.
 */

public class Asteroid extends Sprite {

    protected static final float UNITS_TO_MOVE = 100f;
    protected GameObj mParent;
    protected float mSpeedFactor;
    protected float mSpeedX, mSpeedY;
    protected Random mRand;

    public Asteroid(GameEngine engine, int drawableRes, GameObj parent) {
        super(engine, drawableRes);
        mSpeedFactor = (UNITS_TO_MOVE / 1000) * mPixelFactor;
        mParent = parent;
        mRand = mGameEngine.mRandom;
        mBodyType = BodyType.Circular;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine engine) {
        mX += mSpeedX * elapsedMillis;
        mY += mSpeedY * elapsedMillis;

        if (mY > mGameEngine.mMaxY) {
//            mGameEngine.removeGameObj(this);
//            ((GameController) mParent).addToAsteroidPool(this);
            this.removeFromGameEngine(engine);
        }

        mRotation += (mRotationSpeed * elapsedMillis);
        if (mRotation > 360) {
            mRotation = 0;
        } else if (mRotation < 0) {
            mRotation = 360;
        }
    }

    public void init() {
        double radian = (mRand.nextDouble() * Math.PI / 3) - Math.PI / 6;

        mSpeedX = (float) (Math.sin(radian) * mSpeedFactor);
        mSpeedY = (float) (Math.cos(radian) * mSpeedFactor);

        mX = mRand.nextInt((int) (mGameEngine.mMaxX / 2)) + (mGameEngine.mMaxX / 4);
        mY = -mHeight;

        mRotation = mRand.nextInt(360);
        float degree = (float) (radian * (180f / Math.PI));
        mRotationSpeed = degree / 250f;
    }

    @Override
    public void removeFromGameEngine(GameEngine engine) {
        super.removeFromGameEngine(engine);
        ((GameController) mParent).addToAsteroidPool(this);
    }
}
