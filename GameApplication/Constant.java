package com.ramimajdoub.bounceup.GameApplication;

import android.graphics.Color;

/**
 * THIS CLASS CONTAINS ALL THE CONSTANT VALUES :
 * VARIABLES , IMAGES , STRINGS ...
 */
public class Constant {
    public static final int max_fps = 30;
    static final int FrameTime = 1000 / max_fps;

    public static int secToFrames(double s){ return (int)(s * max_fps); }


    public static int backgroundColor = Color.rgb(108, 108, 108);
}
