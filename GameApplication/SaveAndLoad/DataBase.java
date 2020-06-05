package com.ramimajdoub.bounceup.GameApplication.SaveAndLoad;

// SAVE VALUES WITH THE SharedPreferences
// CHANGE THE VALUE AND SAVE IT !

import android.content.Context;
import android.content.SharedPreferences;

public class DataBase {

    // SAVED VALUES
    private int highScore;
    private boolean musicIsActive;

    // KEY VALUES
    private static final String KEY = "higher";
    private static final String KEY_SCORE = "SCORE";
    private static final String KEY_MUSIC = "MUSIC";

    private SharedPreferences pref;
    public DataBase(Context context) {
        pref = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        loadValues();
    }
    private void save(String s, int v){
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(s,v);
        editor.apply();//editor.commit();
    }
    private void save(String s, float v){
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat(s,v);
        editor.apply();//editor.commit();
    }
    private void save(String s, long v){
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong(s,v);
        editor.apply();//editor.commit();
    }
    private void save(String s, boolean v){
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(s,v);
        editor.apply();//editor.commit();
    }
    private void save(String s, String v){
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(s,v);
        editor.apply();//editor.commit();
    }

    private void loadValues() {
        highScore = pref.getInt(KEY_SCORE, 0);
        musicIsActive = pref.getBoolean(KEY_MUSIC, true);
    }

    public int getHighScore() {return highScore;}
    public boolean updateHighScore(int score) {
        if (score > highScore){
            highScore = score;
            save(KEY_SCORE, highScore);
            return true;
        }
        return false;
    }

    public boolean isMusicActive() {return musicIsActive;}
    public void musicToggle(){
        musicIsActive = !musicIsActive;
        save(KEY_MUSIC, musicIsActive);
    }
}
