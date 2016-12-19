package com.example.foo.spaceapp;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;

import com.example.foo.spaceapp.asset.Background;
import com.example.foo.spaceapp.asset.FPSCounter;
import com.example.foo.spaceapp.asset.GameController;
import com.example.foo.spaceapp.asset.Player;
import com.example.foo.spaceapp.controller.KeypadController;
import com.example.foo.spaceapp.engine.GameEngine;
import com.example.foo.spaceapp.view.GameView;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends BaseGameFragment {

    protected static final String TAG = "GameFragment";
    protected Button mBtnPause;
    protected GameEngine mGameEngine;

    public GameFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        ViewTreeObserver obs = view.getViewTreeObserver();
        obs.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            @SuppressWarnings("deprecation")
            public void onGlobalLayout() {
                ViewTreeObserver obs = getView().getViewTreeObserver();
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    obs.removeGlobalOnLayoutListener(this);
                } else {
                    obs.removeOnGlobalLayoutListener(this);
                }
                View v = getView();
                GameView gameView = (GameView) v.findViewById(R.id.view_game);
                mGameEngine = new GameEngine(v.getContext(), gameView, 4);
                mGameEngine.setInputController(new KeypadController(v));
                mGameEngine.addGameObj(new Background(mGameEngine), 0);
                mGameEngine.addGameObj(new GameController(mGameEngine), 2);
                mGameEngine.addGameObj(new FPSCounter(mGameEngine), 2);
                mGameEngine.addGameObj(new Player(mGameEngine), 3);
                mGameEngine.startGame();
            }
        });

        mBtnPause = (Button) view.findViewById(R.id.btn_pause);
        mBtnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPauseDialog();
            }
        });

        return view;
    }

    public void showPauseDialog() {
        mGameEngine.pauseGame();
        new AlertDialog.Builder(this.getContext())
                .setTitle("Pause")
                .setMessage("Game is paused, take your time.")
                .setNegativeButton(R.string.stop, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mGameEngine.stopGame();
                        ((MainActivity) getContext()).navigateBack();
                    }
                })
                .setPositiveButton(R.string.resume, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        mGameEngine.resumeGame();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        dialogInterface.dismiss();
                        mGameEngine.resumeGame();
                    }
                })
                .create()
                .show();
    }

    @Override
    public boolean onBackPressed() {
        Log.d(TAG, "onBackPressed() called..");
        if (mGameEngine.isRunning()){
            this.showPauseDialog();
            return true;
        }
        return false;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGameEngine != null) {
            if (mGameEngine.isRunning()) {
                this.showPauseDialog();
            }
        }
    }

    /*@Override
    public void onResume() {
        super.onResume();
        mGameEngine.resumeGame();
    }*/

    @Override
    public void onStop() {
        super.onStop();
        mGameEngine.stopGame();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGameEngine.stopGame();
    }
}
