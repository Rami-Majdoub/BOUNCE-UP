package com.ramimajdoub.bounceup.GameApplication.Activities.ActivityGame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.ramimajdoub.bounceup.GameApplication.Drawing.Firework.Firework;
import com.ramimajdoub.bounceup.GameApplication.geo.Vector2;

import static com.ramimajdoub.bounceup.GameApplication.Drawing.Screen.*;

public class Ball {

    private enum STATE {BORN, ALIVE, DIEING, DEAD}
    private STATE state;

    private float radius;
    private final int RADIUS = x(44);

    private float velocityX = x(10);
    private Vector2 centre = new Vector2(CENTER_X, y(500) + RADIUS);

    private Paint paint = new Paint();

    private Firework firework = new Firework();

    public void reset(){
        radius = 0;
        centre.x = CENTER_X;
        velocityX = Math.abs(velocityX);
        state = STATE.BORN;
    }
    public void setColor(int color) { paint.setColor(color); }

    public void update(){
        if (state == STATE.DEAD) return;

        if (state == STATE.BORN) {
            radius += x(2f);
            if (radius >= RADIUS) state = STATE.ALIVE;

        } else if (state == STATE.ALIVE) {
            centre.x += velocityX;
            if (centre.x - RADIUS < x(10) || centre.x + RADIUS > WIDTH - x(10))
                velocityX *= -1;

        } else if (state == STATE.DIEING){
            firework.move();
            if (firework.isCompleted()) state = STATE.DEAD;
            // radius -= x(2f);
            // if (radius < 0) radius = 0;
            // if (radius == 0) state = STATE.DEAD;
        }
    }

    public void draw(Canvas canvas){
        if (state == STATE.DEAD) return;
        if (state == STATE.DIEING) firework.draw(canvas);
        else canvas.drawCircle((float) centre.x, (float) centre.y, radius, paint);
    }

    public boolean isDead(){ return state == STATE.DEAD; }
    public boolean isDieing(){ return state == STATE.DIEING; }
    public boolean isAlive(){ return state == STATE.ALIVE; }

    public boolean collide(Vector2 point) {
        return centre.distanceSquared(point) <= RADIUS * RADIUS;
    }
    public void die() {
        firework.set(centre.x, centre.y, paint.getColor());
        state = STATE.DIEING;
    }
}
