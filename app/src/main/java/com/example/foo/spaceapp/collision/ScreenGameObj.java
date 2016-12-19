package com.example.foo.spaceapp.collision;

import android.graphics.Rect;

import com.example.foo.spaceapp.engine.GameEngine;
import com.example.foo.spaceapp.engine.GameObj;

/**
 * Created by foo on 12/14/16.
 */

public class ScreenGameObj extends GameObj {

    protected GameEngine mGameEngine;
    protected float mX, mY;
    protected float mWidth, mHeight;
    protected float mRadius;
    protected Rect mBoundingRect = new Rect(-1, -1, -1, -1);
    public BodyType mBodyType;

    @Override
    public void onUpdate(long elapsedMillis, GameEngine engine) {

    }

    public void onPostUpdate(GameEngine engine) {
        int left = (int) mX,
                top = (int) mY,
                right = (int) (mX + mWidth),
                bottom = (int) (mY + mHeight);
        mBoundingRect.set(left, top, right, bottom);
    }

    public void onCollision(ScreenGameObj other, GameEngine engine) {

    }

    public boolean checkCollision(ScreenGameObj other) {
        if ((mBodyType == BodyType.Circular) && (other.mBodyType == BodyType.Circular)) {
            return checkCircularCollision(other);
        } else if ((mBodyType == BodyType.Rectangular) && (other.mBodyType == BodyType.Rectangular)) {
            return checkRectangularCollision(other);
        }
        return checkMixedCollision(other);
    }

    protected boolean checkCircularCollision(ScreenGameObj other) {
        float x = mX + mWidth / 2;
        float x2 = other.mX + other.mWidth / 2;
        float y = mY + mHeight / 2;
        float y2 = other.mY + other.mHeight / 2;
        float dx = x - x2;
        float dy = y - y2;
        float squareX = dx * dx;
        float squareY = dy * dy;
        float squareDist = squareX + squareY;
        float radiusDist = mRadius + other.mRadius;
        float squareRadius = radiusDist * radiusDist;
        if (squareDist <= squareRadius) {
            return true;
        }
        return false;
    }

    protected boolean checkRectangularCollision(ScreenGameObj other) {
        return Rect.intersects(mBoundingRect, other.mBoundingRect);
    }

    protected boolean checkMixedCollision(ScreenGameObj other) {
        ScreenGameObj circ;
        ScreenGameObj rect;
        if (mBodyType == BodyType.Circular) {
            circ = this;
            rect = other;
        } else {
            circ = other;
            rect = this;
        }

        float circXPos = circ.mX + circ.mWidth / 2;
        float checkXPos = circXPos;
        if (circXPos < rect.mX) {
            checkXPos = rect.mX;
        } else if (circXPos > rect.mX + rect.mWidth) {
            checkXPos = rect.mX + rect.mWidth;
        }

        float circYPos = circ.mY + circ.mHeight / 2;
        float checkYPos = circYPos;
        if (circYPos < rect.mY) {
            checkYPos = rect.mY;
        } else if (circYPos > rect.mY + rect.mHeight) {
            checkYPos = rect.mY + rect.mHeight;
        }

        float dx = circXPos - checkXPos;
        float dy = circYPos - checkYPos;

        float squareX = dx * dx;
        float squareY = dy * dy;

        float squareDist = squareX + squareY;
        float radiusDist = mRadius + other.mRadius;
        float squareRadius = radiusDist * radiusDist;

        if (squareDist <= squareRadius) {
            return true;
        }
        return false;
    }
}
