package com.ramimajdoub.bounceup.GameApplication.Drawing;

import android.content.res.Resources;
import android.graphics.RectF;

public class Screen {
    private static final int H = Resources.getSystem().getDisplayMetrics().heightPixels;
    private static final int W = Resources.getSystem().getDisplayMetrics().widthPixels;

    public static final int HEIGHT = Math.max(H, W);
    public static final int WIDTH = Math.min(H, W);

    public static final int CENTER_X  = WIDTH / 2;
    public static final int CENTER_Y  = HEIGHT / 2;

    // GAME

    // LOADING
    public static final FontHolder LOADING =
            new FontHolder("Loading...", x(100), y(350), x(380), y(450) );

    // MY DEVICE RESOLUTION  480 * 800
    public static float x(float x) {return x * WIDTH / 480.0f;}
    public static float y(float y) {return y * HEIGHT / 800.0f;}
    public static int x(int x) {return x * WIDTH / 480;}
    public static int y(int y) {return y * HEIGHT / 800;}
}
