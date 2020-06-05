package com.ramimajdoub.bounceup.GameApplication.Drawing.Firework;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.ramimajdoub.bounceup.GameApplication.geo.Vector2;

import static com.ramimajdoub.bounceup.GameApplication.Drawing.Screen.x;

public class Firework_Part {
    public final int PARTS_RADIUS =x(5);

    private Vector2 center = new Vector2();
    private Vector2 velocity = new Vector2();

    private float alpha, alphaDec;
    private int color, movesLeft;

    public void set(double x, double y, double velocityX, double velocityY,
                    int color, int moves) {
        center.set(x, y);
        velocity.set(velocityX, velocityY);
        this.color = color;
        movesLeft = moves;

        alpha = 255;
        alphaDec = 255f / moves;
    }

    private static Paint paint = new Paint();

    public void draw(Canvas canvas) {
        paint.setColor(color);
        paint.setAlpha((int) alpha);
        canvas.drawCircle((float) center.x, (float) center.y, PARTS_RADIUS, paint);
    }

    private void updateAlpha() {
        alpha -= alphaDec;
        if (alpha < 0) alpha = 0;
    }
    public void update() {
        center.add(velocity);
        updateAlpha();
        movesLeft --;
    }
    public boolean isCompleted(){ return movesLeft == 0; }
}
