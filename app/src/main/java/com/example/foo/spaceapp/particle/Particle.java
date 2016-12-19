package com.example.foo.spaceapp.particle;

import com.example.foo.spaceapp.asset.Sprite;
import com.example.foo.spaceapp.engine.GameEngine;

import java.util.List;

/**
 * Created by foo on 12/18/16.
 */

public class Particle extends Sprite {

    public float mSpeed;
    public float mSpeedX, mSpeedY;
    public float mRoationSpeed;

    protected ParticleSystem mParent;
    protected long mTimeToLive;

    public Particle(GameEngine engine, int drawableRes, ParticleSystem parent, long timeToLive) {
        super(engine, drawableRes);
        mParent = parent;
        mTimeToLive = timeToLive;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine engine) {

    }

    public void activate(List<ParticleModifier> modifiers) {

    }
}
