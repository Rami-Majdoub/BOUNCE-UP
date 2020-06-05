package com.ramimajdoub.bounceup.GameApplication.Activities.ActivityGame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import static com.ramimajdoub.bounceup.GameApplication.Drawing.Screen.x;

public class ProgressBar {

    private int value;
    private final int MAX_VAL;

    private RectF rect = new RectF();
    private RectF rectBorders = new RectF();

    public void setEmpty() {
        value = 0;
        updateRect();
    }
    public void increase() {
        value ++;
        if (value > MAX_VAL) value = MAX_VAL;
        updateRect();
    }
    public void decrease() {
        value --;
        if (value < 0) value = 0;
        updateRect();
    }

    public boolean isFull(){ return value == MAX_VAL;}
    public boolean isEmpty(){ return value == 0;}

    private void updateRect() {
        rect.set(rectBorders);
        rect.right = rect.left + rectBorders.width() / MAX_VAL * value;
    }
    private Paint paint = new Paint();

    public void reset(int color) { paint.setColor(color); }

    ProgressBar(float left , float top , float right , float bottom, int maxVal) {
        rectBorders.set(left, top, right, bottom);
        updateRect();
        MAX_VAL = maxVal;

        paint.setStrokeWidth(x(5));
    }

    public void draw(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(rectBorders, rectBorders.height() / 2, rectBorders.height() / 2, paint);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(rect, rectBorders.height() / 2, rectBorders.height() / 2, paint);

    }

}
