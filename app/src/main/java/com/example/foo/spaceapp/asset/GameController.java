package com.example.foo.spaceapp.asset;

import com.example.foo.spaceapp.R;
import com.example.foo.spaceapp.engine.GameEngine;
import com.example.foo.spaceapp.engine.GameObj;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by foo on 11/27/16.
 */

public class GameController extends GameObj {

    protected static final int NUM_ASTEROIDS = 6;
    protected static final int TIME_BETWEEN_ENEMIES = 500;
    protected static final int[] DRAWABLE_IDS = {
            R.drawable.asteroid_0,
            R.drawable.asteroid_1,
            R.drawable.asteroid_2,
            R.drawable.asteroid_3,
            R.drawable.asteroid_4,
            R.drawable.asteroid_5,
            R.drawable.asteroid_6,
            R.drawable.asteroid_7,
            R.drawable.asteroid_8,
            R.drawable.asteroid_8,
            R.drawable.asteroid_10
    };
    protected List<Asteroid> mAsteroids = new ArrayList<>();
    protected long mCurrMillis;
    protected int mNumSpawnedEnemies;

    public GameController(GameEngine engine) {
        this.initAsteroidPool(engine);
    }

    public void onUpdate(long elapsedMillis, GameEngine engine) {
        mCurrMillis += elapsedMillis;
        long waveMillis = mNumSpawnedEnemies * TIME_BETWEEN_ENEMIES;
        if (mCurrMillis > waveMillis) {
            Asteroid a = this.getAsteroid();
            if (a == null) return;
            a.init();
            engine.addGameObj(a, 1);
            mNumSpawnedEnemies++;
        }
    }

    protected void initAsteroidPool(GameEngine engine) {
        for (int i = 0; i < NUM_ASTEROIDS; i++) {
            int drawableRes = this.getDrawableRes(i);
            mAsteroids.add(new Asteroid(engine, drawableRes, this));
        }
    }

    protected int getDrawableRes(int index) {
        return DRAWABLE_IDS[index];
    }

    protected Asteroid getAsteroid() {
        if (mAsteroids.isEmpty()) {
            return null;
        }
        return mAsteroids.remove(0);
    }

    protected void addToAsteroidPool(Asteroid a) {
        mAsteroids.add(a);
    }
}
