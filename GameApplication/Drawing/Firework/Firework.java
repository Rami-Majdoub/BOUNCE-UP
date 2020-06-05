package com.ramimajdoub.bounceup.GameApplication.Drawing.Firework;

import android.graphics.Canvas;
import static com.ramimajdoub.bounceup.GameApplication.Constant.max_fps;
import static com.ramimajdoub.bounceup.GameApplication.Drawing.Screen.x;

public class Firework {

    private final int PARTS = 15;

    private static final float PARTS_TIME_TO_DISAPPEAR = 0.75f;
    private static float PARTS_SPEED = (x(150) / PARTS_TIME_TO_DISAPPEAR) / max_fps;

    private Firework_Part[] fireworkParts = new Firework_Part[PARTS];

    public Firework() {
        for (int i = 0; i < fireworkParts.length; i++)
            fireworkParts[i] = new Firework_Part();
    }

    public void set(double x, double y, int color) {
        for (int i = 0; i < fireworkParts.length; i++){

            double alpha = - 2 * Math.PI / PARTS * i;

            fireworkParts[i].set(x, y,
                    Math.cos(alpha) * PARTS_SPEED,
                    Math.sin(alpha) * PARTS_SPEED,
                    color, (int) (PARTS_TIME_TO_DISAPPEAR * (float) max_fps));
        }
    }

    public void move() {
        for (Firework_Part firework_part : fireworkParts)
            firework_part.update();
    }
    public void draw(Canvas canvas) {
        for (Firework_Part firework_part : fireworkParts)
            firework_part.draw(canvas);
    }
    public boolean isCompleted(){ return fireworkParts[0].isCompleted(); }
}
