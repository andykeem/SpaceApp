package com.example.foo.spaceapp.engine;

import android.app.Activity;
import android.content.Context;

import com.example.foo.spaceapp.collision.BodyType;
import com.example.foo.spaceapp.collision.ScreenGameObj;
import com.example.foo.spaceapp.controller.InputController;
import com.example.foo.spaceapp.view.GameView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by foo on 11/10/16.
 */

public class GameEngine {

    protected static final float BASE_SCREEN_HEIGHT = 400f;
    public Context mContext;
    protected List<GameObj> mGameObjs = new ArrayList<>();
    protected List<GameObj> mObjsToAdd = new ArrayList<>();
    protected List<GameObj> mObjsToRemove = new ArrayList<>();
    protected List<List<GameObj>> mLayers = new ArrayList<List<GameObj>>();
    protected List<ScreenGameObj> mCollisionables = new ArrayList<>();
    protected UpdateThread mUpdateThrd;
    protected DrawThread mDrawThrd;
    public InputController mInputController;
    public GameView mGameView;
    public float mPixelFactor;
    public float mMaxX, mMaxY;
    public Random mRandom;

    public GameEngine(Context c, GameView gameView, int numLayers) {
        mContext = c;
        mGameView = gameView;

        mPixelFactor = (mGameView.getHeight() / BASE_SCREEN_HEIGHT);
        mMaxX = mGameView.getWidth() - mGameView.getPaddingLeft() - mGameView.getPaddingRight();
        mMaxY = mGameView.getHeight() - mGameView.getPaddingBottom() - mGameView.getPaddingTop();

        gameView.setGameObjs(mGameObjs);
        mRandom = new Random();

        for (int i = 0; i < numLayers; i++) {
            mLayers.add(new ArrayList<GameObj>());
        }
        gameView.setLayers(mLayers);
    }

    public void startGame() {
        this.stopGame();

        int numObjs = mGameObjs.size();
        for (int i = 0; i < numObjs; i++) {
            mGameObjs.get(i).start();
        }

        mUpdateThrd = new UpdateThread(this);
        mUpdateThrd.start();

        mDrawThrd = new DrawThread(this);
        mDrawThrd.start();
    }

    public void onUpdateOld(long elapsedMillis) {
        int numObjs = mGameObjs.size();
        for (int i = 0; i < numObjs; i++) {
            mGameObjs.get(i).onUpdate(elapsedMillis, this);
        }
        synchronized (mLayers) {
            if (!mObjsToRemove.isEmpty()) {
                mGameObjs.remove(mObjsToRemove.remove(0));
            }
            if (!mObjsToAdd.isEmpty()) {
                mGameObjs.add(mObjsToAdd.remove(0));
            }
        }
    }

    public void onUpdate(long elapsedMillis) {
        int numGameObjs = mGameObjs.size();
        for (int i = 0; i < numGameObjs; i++) {
            GameObj gameObj = mGameObjs.get(i);
            gameObj.onUpdate(elapsedMillis, this);
            gameObj.onPostUpdate(this);
        }
        this.checkCollisions();
        synchronized (mLayers) {
            while (!mObjsToRemove.isEmpty()) {
                GameObj obj = mObjsToRemove.remove(0);
                mGameObjs.remove(obj);
                mLayers.get(obj.mLayer).remove(obj);
                removeFromCollisionables(obj);
            }
            while(!mObjsToAdd.isEmpty()) {
                GameObj obj = mObjsToAdd.remove(0);
                this.addGameObjToLayer(obj);
            }
        }
    }

    /*protected Runnable mDrawRunnable = new Runnable() {
        @Override
        public void run() {
            synchronized (mGameObjs) {
                int numObjs = mGameObjs.size();
                for (int i = 0; i < numObjs; i++) {
                    mGameObjs.get(i).onDraw();
                }
            }
        }
    };*/

    public void onDraw() {
//        ((Activity) mContext).runOnUiThread(mDrawRunnable);
        mGameView.draw();
    }

    public void stopGame() {
        if (mUpdateThrd != null) {
            mUpdateThrd.stopGame();
        }
        if (mDrawThrd != null) {
            mDrawThrd.stopGame();
        }
    }

    public boolean isRunning() {
        return ((mUpdateThrd != null) && mUpdateThrd.isRunning());
    }

    public boolean isPaused() {
        return ((mUpdateThrd != null) && mUpdateThrd.isPaused());
    }

    public void addGameObj(GameObj obj, int layer) {
        obj.mLayer = layer;
        if (isRunning()) {
            mObjsToAdd.add(obj);
        } else {
//            mGameObjs.add(obj);
            this.addGameObjToLayer(obj);
        }
//        obj.onAddedToUiThread();
        ((Activity) mContext).runOnUiThread(obj.mOnAddedRunnable);
    }

    protected void addGameObjToLayer(GameObj obj) {
        int layer = obj.mLayer;
        if (layer >= 0) {
            if (mLayers.size() <= layer) {
                for (int i = 0; i < layer; i++) {
                    mLayers.add(new ArrayList<GameObj>());
                }
            }
            mLayers.get(layer).add(obj);
            mGameObjs.add(obj);
        }
        addToCollisionables(obj);
    }

    public void removeGameObj(GameObj obj) {
        if (isRunning()) {
            mObjsToRemove.add(obj);
        } else {
            mGameObjs.remove(obj);
            removeFromCollisionables(obj);
        }
//        obj.onRemovedFromUiThread();
        ((Activity) mContext).runOnUiThread(obj.mOnRemovedRunnable);
    }

    public void pauseGame() {
//        if (this.isRunning()) {
            if (mUpdateThrd != null) {
                mUpdateThrd.pause();
            }
            if (mDrawThrd != null) {
                mDrawThrd.pause();
            }
//        }
    }

    public void resumeGame() {
        if (this.isPaused()) {
            mUpdateThrd.resumeGame();
            mDrawThrd.resumeGame();
        }
    }

    public void setInputController(InputController controller) {
        mInputController = controller;
    }

    protected void checkCollisions() {
        int numCollisionables = mCollisionables.size();
        for (int i = 0; i < numCollisionables; i++) {
            ScreenGameObj a = mCollisionables.get(i);
            for (int j = (i + 1); j < numCollisionables; j++) {
                ScreenGameObj b = mCollisionables.get(j);
                if (a.checkCollision(b)) {
                    a.onCollision(b, this);
                    b.onCollision(a, this);
                }
            }
        }
    }

    protected void addToCollisionables(GameObj obj) {
        if (obj instanceof ScreenGameObj) {
            ScreenGameObj sgo = (ScreenGameObj) obj;
            if (sgo.mBodyType != BodyType.None) {
                mCollisionables.add(sgo);
            }
        }
    }

    protected void removeFromCollisionables(GameObj obj) {
        if (obj instanceof ScreenGameObj) {
            mCollisionables.remove(obj);
        }
    }
}
