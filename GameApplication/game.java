package com.ramimajdoub.bounceup.GameApplication;

// Created by acer on 14/03/2018

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.ramimajdoub.bounceup.R;

public class game extends Activity {

    GameView game_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // FULL SCREEN
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // DON'T GO TO SLEEP !
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // USE MEDIA VOLUME CONTROL
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        // VERTICAL ORIENTATION ONLY
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        game_view = new GameView(this);
        setContentView(game_view);

    }

    @Override
    public void onBackPressed() {
        game_view.pause();
        new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setMessage("ARE YOU SURE YOU WANT TO EXIT ?")
                .setPositiveButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        game_view.resume();
                    }
                })
                .setNegativeButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                game_view.resume();
                // Constant.exit_dialog_is_shown = false;
                // game_view.resume();
            }
        }).show();
    }

    @Override
    protected void onResume() {super.onResume();game_view.resume();}
    @Override
    protected void onPause() {super.onPause();game_view.pause();}
    @Override
    protected void onDestroy() {super.onDestroy();this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);}
}