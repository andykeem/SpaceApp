package com.example.foo.spaceapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.example.foo.spaceapp.engine.GameObj;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by foo on 11/24/16.
 */

public class StandardGameView extends View implements GameView {

    public List<GameObj> mGameObjs;
    public List<List<GameObj>> mLayers;

    public StandardGameView(Context context) {
        super(context);
    }

    public StandardGameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void draw() {
        this.postInvalidate();
    }

    @Override
    public void setGameObjs(List<GameObj> objs) {
        mGameObjs = objs;
    }

    public void onDrawOld(Canvas canvas) {
        super.onDraw(canvas);
        synchronized (mGameObjs) {
            int numGameObjs = mGameObjs.size();
            for (int i = 0; i < numGameObjs; i++) {
                mGameObjs.get(i).onDraw(canvas);
            }
        }
    }

    @Override
    public void setLayers(List<List<GameObj>> layers) {
        mLayers = layers;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.draw(canvas);
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
    }
}
