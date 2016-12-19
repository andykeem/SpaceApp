package com.example.foo.spaceapp.engine;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by foo on 11/10/16.
 */

public class UpdateThread extends Thread {

    protected GameEngine mEngine;
    protected boolean mRunning;
    protected boolean mPaused;
    protected Lock mLock;

    public UpdateThread(GameEngine engine) {
        mEngine = engine;
        mLock = new ReentrantLock();
    }

    @Override
    public void run() {
        long prevMillis, currMillis, elapsedMillis;
        prevMillis = System.currentTimeMillis();
        while (mRunning) {
            currMillis = System.currentTimeMillis();
            elapsedMillis = currMillis - prevMillis;
            if (mPaused) {
                while (mPaused) {
                    try {
                        synchronized (mLock) {
                            mLock.wait();
                        }
                    } catch (InterruptedException e) {
                        // skip the exception..
                    }
                }
                currMillis = System.currentTimeMillis();
            }
            mEngine.onUpdate(elapsedMillis);
            prevMillis = currMillis;
        }
    }

    @Override
    public void start() {
        mRunning = true;
        super.start();
    }

    public void stopGame() {
        mRunning = false;
        this.resumeGame();
    }

    public void pause() {
        mPaused = true;
    }

    public void resumeGame() {
        if (mPaused) {
            mPaused = false;
            synchronized (mLock) {
                mLock.notify();
            }
        }
    }

    public boolean isRunning() {
        return mRunning;
    }

    public boolean isPaused() {
        return mPaused;
    }
}
