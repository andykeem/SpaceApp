package com.example.foo.spaceapp.engine;

import android.graphics.Canvas;

/**
 * Created by foo on 11/10/16.
 */

public abstract class GameObj {

    protected static final float BASE_SCREEN_HEIGHT = 400;

    protected Runnable mOnAddedRunnable = new Runnable() {
        @Override
        public void run() {
            onAddedToUiThread();
        }
    };

    protected Runnable mOnRemovedRunnable = new Runnable() {
        @Override
        public void run() {
            onRemovedFromUiThread();
        }
    };

    public int mLayer;
    public GameObj mParent;

    public void start() {

    }

    public abstract void onUpdate(long elapsedMillis, GameEngine engine);

    public void onDraw(Canvas canvas) {

    }

    public void onAddedToUiThread() {

    }

    public void onRemovedFromUiThread() {

    }

    public void onPostUpdate(GameEngine engine) {

    }

    public void removeFromGameEngine(GameEngine engine) {
        engine.removeGameObj(this);
    }

    public void addToGameEngine(GameEngine engine, int layer) {
        engine.addGameObj(this, layer);
    }
}
