package com.example.foo.spaceapp.asset;

import com.example.foo.spaceapp.R;
import com.example.foo.spaceapp.collision.BodyType;
import com.example.foo.spaceapp.controller.InputController;
import com.example.foo.spaceapp.engine.GameEngine;
import com.example.foo.spaceapp.collision.ScreenGameObj;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by foo on 11/24/16.
 */

public class Player extends Sprite {

    protected static final int UNITS_TO_MOVE = 100;
    protected static final int NUM_BULLETS = 6;
    protected static final int TIME_BETWEEN_BULLET = 500;

    protected float mSpeedFactor;
    protected float mMaxX, mMaxY;

    protected List<Bullet> mBullets = new ArrayList<>();
    protected long mTimeSinceLastFire;

    public Player(GameEngine engine) {
        super(engine, R.drawable.shuttle);
        mSpeedFactor = (UNITS_TO_MOVE / 1000f) * mPixelFactor;

        mMaxX = engine.mMaxX - mWidth;
        mMaxY = engine.mMaxY - mHeight;

        this.initBulletPool();
        mBodyType = BodyType.Circular;
    }

    @Override
    public void start() {
        mX = mMaxX / 2;
        mY = mMaxY / 2;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine engine) {
        InputController input = engine.mInputController;
        mX += (mSpeedFactor * elapsedMillis * input.mHorizontalFactor);
        mY += (mSpeedFactor * elapsedMillis * input.mVerticalFactor);
        if (mX < 0) {
            mX = 0;
        } else if (mX > mMaxX) {
            mX = mMaxX;
        }
        if (mY < 0) {
            mY = 0;
        } else if (mY > mMaxY) {
            mY = mMaxY;
        }
        this.checkFiring(elapsedMillis, engine);
    }

    protected void initBulletPool() {
        for (int i = 0; i < NUM_BULLETS; i++) {
            mBullets.add(new Bullet(mGameEngine));
        }
    }

    protected void checkFiring(long elapsedMillis, GameEngine engine) {
        InputController in = engine.mInputController;
        if (in.mFiring && (mTimeSinceLastFire > TIME_BETWEEN_BULLET)) {
            Bullet b = this.getBullet();
            if (b == null) {
                return;
            }
            float x = mX + (mWidth / 2);
            float y = mY;
            b.init(this, x, y);
            engine.addGameObj(b, 1);
            mTimeSinceLastFire = 0;
        } else {
            mTimeSinceLastFire += elapsedMillis;
        }
    }

    protected Bullet getBullet() {
        if (mBullets.isEmpty()) {
            return null;
        }
        return mBullets.remove(0);
    }

    public void releaseBullet(Bullet b) {
        mBullets.add(b);
    }

    @Override
    public void onCollision(ScreenGameObj other, GameEngine engine) {
        if (other instanceof Asteroid) {
            this.removeFromGameEngine(engine);
            Asteroid a = (Asteroid) other;
            a.removeFromGameEngine(engine);
        }
    }
}
