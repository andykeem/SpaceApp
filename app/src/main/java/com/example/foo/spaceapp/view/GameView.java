package com.example.foo.spaceapp.view;

import android.content.Context;

import com.example.foo.spaceapp.engine.GameObj;

import java.util.List;

/**
 * Created by foo on 11/24/16.
 */

public interface GameView {
    void draw();
    void setGameObjs(List<GameObj> objs);
    void setLayers(List<List<GameObj>> layers);
    // generic methods
    int getHeight();
    int getWidth();
    int getPaddingBottom();
    int getPaddingLeft();
    int getPaddingRight();
    int getPaddingTop();
    Context getContext();
}
