package com.ramimajdoub.bounceup.GameApplication.Util;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.ParcelUuid;

import java.util.Random;

public class Util {

    private static float[] colorHsv = new float[3];
    private static Random rnd = new Random();

    /**
     * @param alpha in [0..255]
     */
    public static int brightColor(int alpha) {
        colorHsv[0] = random(360);
        colorHsv[1] = 1;//random(0.4f, 1f);//rnd.nextFloat()% 0.6f + 0.4f ;
        colorHsv[2] = 0.73333f;//random(0.7f , 0.9f); //rnd.nextFloat()% 0.2f + 0.7f;
        return Color.HSVToColor(alpha, colorHsv);
    }
    public static boolean randomBool(){return rnd.nextBoolean();}
    public static int random(int max){return random(0, max);}
    public static int random(int min, int max) {return rnd.nextInt(max - min) + min;}


    // RECT MANIPULATION
    public static boolean pointInRect(
            double left, double top, double right, double bottom, double x, double y) {
        return left <= x && x <= right &&
                top <= y && y <= bottom;
    }
    public static void moveRect(RectF rect, float dtX, float dtY) {
        rect.left += dtX;
        rect.top += dtY;
        rect.right += dtX;
        rect.bottom += dtY;
    }
    public static boolean rectsColliding(RectF rect1, RectF rect2) {
        return rect1.intersects(rect2.left, rect2.top, rect2.right, rect2.bottom);
    }

    public static float widthDividedBy(RectF rect, int n) { return rect.width() / n; }
    public static float widthDividedBy(RectF rect, int n, float margin) {
        return rect.width() / n * (1 - margin) ;
    }

    // MATH
    public static float map (float n, float start1, float stop1, float start2, float stop2) {
        return ((n - start1) / (stop1 - start1)) * (stop2 - start2) + start2;
    }
    public static double map (double n, double start1, double stop1, double start2, double stop2) {
        return ((n - start1) / (stop1 - start1)) * (stop2 - start2) + start2;
    }


    // IMAGE MANIPULATION
    public static Bitmap scaleBitmap(Bitmap img, int h) {
        return Bitmap.createScaledBitmap(img, h, h, false);
    }
    public static Bitmap scaleBitmap(Bitmap img, int w, int h) {
        return Bitmap.createScaledBitmap(img, w, h, false);
    }
    public static Bitmap scaleBitmap(Bitmap img, float h) {
        return Bitmap.createScaledBitmap(img, (int) h, (int) h, false);
    }
    public static Bitmap scaleBitmap(Bitmap img, float w, float h) {
        return Bitmap.createScaledBitmap(img, (int) w, (int) h, false);
    }
    public static Bitmap scaleBitmap(Bitmap img, RectF rect) {
        return Bitmap.createScaledBitmap(img, (int) rect.width(), (int) rect.height(), false);
    }
    public static Bitmap rotateBitmap(Bitmap source , float degree){
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
}
