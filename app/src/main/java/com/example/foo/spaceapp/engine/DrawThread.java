package com.example.foo.spaceapp.engine;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by foo on 11/10/16.
 */

public class DrawThread {

    protected static final int EXPECTED_FPS = 30;
    protected static final long TIME_BETWEEN_DRAWS = 1000 / EXPECTED_FPS;

    protected GameEngine mEngine;
    protected Timer mTimer;

    public DrawThread(GameEngine engine) {
        mEngine = engine;
    }

    public void start() {
        this.stopGame();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mEngine.onDraw();
            }
        }, 0, TIME_BETWEEN_DRAWS);
    }

    public void stopGame() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer.purge();
        }
    }

    public void pause() {
        this.stopGame();
    }

    public void resumeGame() {
        this.start();
    }
}
