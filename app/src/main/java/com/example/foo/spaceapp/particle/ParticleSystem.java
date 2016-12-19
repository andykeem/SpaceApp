package com.example.foo.spaceapp.particle;

import com.example.foo.spaceapp.engine.GameEngine;
import com.example.foo.spaceapp.engine.GameObj;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by foo on 12/18/16.
 */

public class ParticleSystem extends GameObj {

    protected List<Particle> mParticlePool = new ArrayList<>();
    protected List<ParticleInitializer> mInitializer = new ArrayList<>();
    protected List<ParticleModifier> mModifiers = new ArrayList<>();
    protected GameEngine mGameEngine;
    protected int mDrawableRes;
    protected int mNumParticles;
    protected long mTimeToLive;
    protected Random mRandom;
    protected float mX, mY;

    public ParticleSystem(GameEngine engine, int drawableRes, int numParticles, long timeToLive) {
        mGameEngine = engine;
        mDrawableRes = drawableRes;
        mNumParticles = numParticles;
        mTimeToLive = timeToLive;
        mRandom = new Random();

        for (int i = 0; i < numParticles; i++) {
            mParticlePool.add(new Particle(mGameEngine, drawableRes, this, timeToLive));
        }
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine engine) {

    }

    public void activateParticle() {
        Particle p = mParticlePool.remove(0);
        int numInitializers = mInitializer.size();
        for (int i = 0; i < numInitializers; i++) {
            ParticleInitializer initializer = mInitializer.get(i);
            initializer.initParticle(p, mRandom);
        }
        p.activate(mModifiers);
    }

    public void oneShot(float x, float y) {
        mX = x;
        mY = y;
    }

    public void addInitializer(ParticleInitializer initializer) {
        mInitializer.add(initializer);
    }

    public void addModifier(ParticleModifier modifier) {
        mModifiers.add(modifier);
    }

    public ParticleSystem setFadeOut(int value) {
        addModifier(new AlphaModifier());
    }
}
