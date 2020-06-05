package com.ramimajdoub.bounceup.GameApplication.Drawing;

import android.graphics.Canvas;

import static com.ramimajdoub.bounceup.GameApplication.Constant.*;

// a text fading in and out
public class TextAlphaAnimation{
    private float Alpha , AlphaInit , AlphaInc;
    private FontHolder fontHolder;

    public TextAlphaAnimation(String text , int left , int top , int right , int bottom , float AlphaInit , float time) {
        fontHolder = new FontHolder(text, left, top, right, bottom);
        Alpha = this.AlphaInit = AlphaInit;
        AlphaInc = 255f / (time * max_fps);
    }
    public TextAlphaAnimation(String text, int left, int top, int right, int bottom) {
        fontHolder = new FontHolder(text, left, top, right, bottom);
        Alpha = AlphaInit = 0;
        AlphaInc = 255f / max_fps;
    }

    public void init() {
        Alpha = AlphaInit;
        AlphaInc = Math.abs(AlphaInc);
    }
    public void update() {
        Alpha += AlphaInc;
        if (Alpha <= 0) {
            Alpha = 0;
            AlphaInc *= -1;
        }
        if (Alpha >= 255) {
            Alpha = 255;
            AlphaInc *= -1;
        }
    }
    public void draw(Canvas canvas) {fontHolder.draw(canvas, (int) Alpha);}
}