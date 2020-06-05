package com.ramimajdoub.bounceup.GameApplication.Drawing;

/**
 * FIXME : getCenterX getCenterY getSize setText
 * this class is to calculate the center of drawing a text in a rectangle
 *
 */

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class FontHolder {

    // Keep the bounds
    public RectF rect = new RectF();

    // What to write
    private String text;

    private int size;
    public float centerX , centerY;
    private int color = Color.WHITE;

    public static Paint textPainter = new Paint();
    static {
        textPainter.setTextAlign(Paint.Align.CENTER);
    }

    public FontHolder(String text, int size, float left, float top, float right, float bottom) {
        rect.set(left, top, right, bottom);
        this.text = text;
        centerX = (left + right) / 2;
        centerY = (top + bottom) / 2;
        this.size = size;
    }
    public FontHolder(String text , float left , float top , float right , float bottom) {
        rect.set(left, top, right, bottom);
        set(text, left, top, right, bottom);
    }
    public FontHolder(String text , RectF rect) {
        this.rect.set(rect);
        set(text, rect.left, rect.top, rect.right, rect.bottom);
    }
    public FontHolder(String text , float left , float top , float right , float bottom , int size) {
        rect.set(left, top, right, bottom);
        set(text, left, top, right, bottom);
        this.size = size;
    }
    public FontHolder(String text ,RectF rect ,int left , int top , int right , int bottom) {
        this.rect.set(rect.left + left, rect.top + top, rect.right - right, rect.bottom - bottom);
        set(text, this.rect.left, this.rect.top, this.rect.right, this.rect.bottom);
    }
    public FontHolder(String text ,int left , int top , int right , int bottom
            ,int marginLeft , int marginTop , int marginRight , int marginBottom) {
        this.rect.set(left + marginLeft, top + marginTop, right - marginRight, bottom - marginBottom);
        set(text, left + marginLeft, top + marginTop, right - marginRight, bottom - marginBottom);
        this.rect.set(left, top, right, bottom);
    }
    private void set(String text , float left , float top , float right , float bottom) {
        this.text = text;
        centerX = (left + right) / 2;
        centerY = (top + bottom) / 2;

        // MUST BE AFTER centerY
        // Because it adds to the value of ' centerY '
        size = getFontSize(text, rect, this);
    }

    public void setText(int text) {this.text = String.valueOf(text);}
    public void setText(String text) {this.text = text;}

    public void setCenterX(float dtX) { centerX += dtX; }
    public void setCenterY(float dtY) { centerY += dtY; }

    public void setColor(int color) {this.color = color;}

    public void copy(FontHolder font) {
        rect.set(font.rect);
        this.size = font.size;
        this.centerX = font.centerX;
        this.centerY = font.centerY;
    }

    public void draw(Canvas canvas , int alpha) {
        // DEBUG
        // textPainter.setColor(Color.BLACK);
        // canvas.drawRect(rect, textPainter);

        // ALPHA AFTER COLOR !
        textPainter.setColor(color);
        textPainter.setAlpha(alpha);
        textPainter.setTextSize(size);
        canvas.drawText(text, centerX, centerY, textPainter);
    }

    public void draw(Canvas canvas) {
        draw(canvas, 255);
    }

    public void draw(Canvas canvas, float x, float y, Paint paint) {
        canvas.drawRoundRect(rect, x, y, paint);
        draw(canvas);
    }

    static private Rect rectOut = new Rect();
    private int getFontSize(String text, RectF rectIn , FontHolder font){return getFontSize(text, rectIn, font, 0, 0, 0, 0);}

    private int getFontSize(String text, RectF rectIn, FontHolder font,
                                   int leftMargin, int topMargin, int rightMargin, int bottomMargin) {

        float h = rectIn.height() - (topMargin + bottomMargin);
        float w = rectIn.width() - (leftMargin + rightMargin);

        int textSize = 0;
        do {
            textSize++;
            textPainter.setTextSize(textSize);
            textPainter.getTextBounds(text, 0, text.length(), rectOut);

        } while (rectOut.height() < h && rectOut.width() < w);

        font.centerY += rectOut.height() / 2;
        return (textSize - 1);
    }

}
