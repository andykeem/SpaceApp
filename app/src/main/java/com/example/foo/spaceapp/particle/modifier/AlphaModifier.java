package com.example.foo.spaceapp.particle.modifier;

import com.example.foo.spaceapp.particle.Particle;

/**
 * Created by foo on 12/18/16.
 */

public class AlphaModifier {

    protected int mInitVal;
    protected int mFinalVal;
    protected long mStartTime;
    protected long mEndTime;
    protected long mDuration;
    protected int mValueIncrement;

    public AlphaModifier(int initVal, int finalVal, long beforeEnd, long endTime) {
        mInitVal = initVal;
        mFinalVal = finalVal;
        mStartTime = endTime - beforeEnd;
        mEndTime = endTime;
        mDuration = endTime - mStartTime;
        mValueIncrement = finalVal - initVal;
    }

    public void apply(Particle p, long elapsedMillis) {
        if (elapsedMillis < mStartTime) {
            p.mAlpha = mInitVal;
        } else if (elapsedMillis > mEndTime) {
            p.mAlpha = mFinalVal;
        } else {
            float percentage = (mEndTime - elapsedMillis) * 1f / mDuration;
            int result = (int) (mValueIncrement + (percentage * mValueIncrement));
            p.mAlpha = result;
        }
    }
}
