package com.example.foo.spaceapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.foo.spaceapp.engine.GameObj;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by foo on 11/25/16.
 */

public class SurfaceGameView extends SurfaceView implements GameView, SurfaceHolder.Callback {

    protected List<GameObj> mGameObjs;
    protected List<List<GameObj>> mLayers;
    protected boolean mReady;

    public SurfaceGameView(Context c) {
        super(c);
        this.getHolder().addCallback(this);
    }

    public SurfaceGameView(Context c, AttributeSet attrs) {
        super(c, attrs);
        this.getHolder().addCallback(this);
    }

    public void drawOld() {
        if (!mReady) return;
        Canvas canvas = this.getHolder().lockCanvas();
        if (canvas == null) return;
        canvas.drawRGB(0, 0, 0);
        synchronized (mGameObjs) {
            int numGameObjs = mGameObjs.size();
            for (int i = 0; i < numGameObjs; i++) {
                mGameObjs.get(i).onDraw(canvas);
            }
        }
        this.getHolder().unlockCanvasAndPost(canvas);
    }

    @Override
    public void draw() {
        if (!mReady) return;
        Canvas canvas = this.getHolder().lockCanvas();
        if (canvas == null) return;
        canvas.drawRGB(0, 0, 0);
        synchronized (mLayers) {
            int numLayers = mLayers.size();
            for (int i = 0; i < numLayers; i++) {
                List currLayer = mLayers.get(i);
                int numGameObjs = currLayer.size();
                for (int j = 0; j < numGameObjs; j++) {
                    GameObj gameObj = (GameObj) currLayer.get(j);
                    gameObj.onDraw(canvas);
                }
            }
        }
        this.getHolder().unlockCanvasAndPost(canvas);
    }

    @Override
    public void setGameObjs(List<GameObj> objs) {
        mGameObjs = objs;
    }

    @Override
    public void surfaceCreated(SurfaceHolder var1) {
        mReady = true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder var1, int var2, int var3, int var4) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder var1) {
        mReady = false;
    }

    @Override
    public void setLayers(List<List<GameObj>> layers) {
        mLayers = layers;
    }
}
