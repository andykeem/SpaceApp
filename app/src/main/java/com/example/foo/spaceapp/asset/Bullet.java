package com.example.foo.spaceapp.asset;

import com.example.foo.spaceapp.R;
import com.example.foo.spaceapp.collision.BodyType;
import com.example.foo.spaceapp.controller.InputController;
import com.example.foo.spaceapp.engine.GameEngine;
import com.example.foo.spaceapp.engine.GameObj;
import com.example.foo.spaceapp.collision.ScreenGameObj;

/**
 * Created by foo on 11/24/16.
 */

public class Bullet extends Sprite {

    protected static final float UNIT_TO_MOVE = -300f;
    protected GameObj mParent;
    protected float mSpeedFactor;

    public Bullet(GameEngine engine) {
        super(engine, R.drawable.bullet);
        mSpeedFactor = UNIT_TO_MOVE / 1000f * mPixelFactor;
        mBodyType = BodyType.Rectangular;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine engine) {
        InputController in = engine.mInputController;
        mY += mSpeedFactor * elapsedMillis; // * in.mVerticalFactor;
        if (mY < -mHeight) {
//            engine.removeGameObj(this);
//            ((Player) mParent).releaseBullet(this);
            this.removeFromGameEngine(engine);
        }
    }

    public void init(GameObj parent, float x, float y) {
        mParent = parent;
        mX = x - (mWidth / 2);
        mY = y - (mHeight / 2);
    }

    @Override
    public void removeFromGameEngine(GameEngine engine) {
        super.removeFromGameEngine(engine);
        ((Player) mParent).releaseBullet(this);
    }

    @Override
    public void onCollision(ScreenGameObj other, GameEngine engine) {
        if (other instanceof Asteroid) {
            removeFromGameEngine(engine);
            Asteroid a = (Asteroid) other;
            a.removeFromGameEngine(engine);
        }
    }
}
