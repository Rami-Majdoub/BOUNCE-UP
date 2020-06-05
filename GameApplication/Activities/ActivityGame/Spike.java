package com.ramimajdoub.bounceup.GameApplication.Activities.ActivityGame;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import com.ramimajdoub.bounceup.GameApplication.geo.Vector2;

import static com.ramimajdoub.bounceup.GameApplication.Drawing.Screen.*;

public class Spike {
    private static final int W = x(75);
    private static final int H = y(100);
    private static final float velocityY = y(200);

    private Path path = new Path();
    private Vector2 p1, p2, p3;

    private static Paint paint = new Paint();
    static { paint.setStrokeWidth(x(5)); }
    public static void setColor(int color) { paint.setColor(color); }

    Spike(boolean right){
        if (right) {
            p1 = new Vector2(WIDTH, -H);
            p2 = new Vector2(WIDTH - W, -H / 2);
            p3 = new Vector2(WIDTH, 0);
        } else {
            p1 = new Vector2(0, -H);
            p2 = new Vector2(W, -H / 2);
            p3 = new Vector2(0, 0);
        }
        path.moveTo((float) p1.x, (float) p1.y);
        path.lineTo((float) p2.x, (float) p2.y);
        path.lineTo((float) p3.x, (float) p3.y);
        path.close();
    }

    public boolean collide(Ball ball) {
        return ball.collide(p1) || ball.collide(p2) || ball.collide(p3);
    }

    public void move(int fps) {
        p1.add(0, velocityY / fps);
        p2.add(0, velocityY / fps);
        p3.add(0, velocityY / fps);
        path.offset(0, velocityY / fps);
    }
    public void draw(Canvas canvas) {
        // canvas.drawLine((float) p1.x, (float) p1.y, (float) p2.x, (float) p2.y, paint);
        // canvas.drawLine((float) p2.x, (float) p2.y, (float) p3.x, (float) p3.y, paint);
        canvas.drawPath(path, paint);
    }

    public boolean isOutOfScreen(RectF rect) {
        return p1.y >= rect.bottom;
    }
}
