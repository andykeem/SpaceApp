package com.example.foo.spaceapp;

import android.support.v4.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseGameFragment extends Fragment {


    public BaseGameFragment() {
        // Required empty public constructor
    }

    public boolean onBackPressed() {
        return false;
    }
}
