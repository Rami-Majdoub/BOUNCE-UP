package com.ramimajdoub.bounceup.GameApplication.Activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.ramimajdoub.bounceup.GameApplication.GameView;

public abstract class MyAppActivity {

    protected GameView view;
    public MyAppActivity(GameView gameView) {
        view = gameView;
    }

    public Bitmap getImage(int img , float height , float width) {return getImage(img, (int) height, (int) width);}
    public Bitmap getImage(int img , int height , int width) {
        return Bitmap.createScaledBitmap(BitmapFactory.decodeResource(view.getResources(), img), width, height, false);}
    public Bitmap getImage(int img , int height) {
        return Bitmap.createScaledBitmap(BitmapFactory.decodeResource(view.getResources(), img), height, height, false);}
    public Bitmap getImage(int img) {
        return BitmapFactory.decodeResource(view.getResources(), img);}

    public abstract void setup();
    public abstract void update();
    public abstract void draw(Canvas canvas);

    public boolean OnTouchMove(float x, float y){return true;}
    public boolean OnTouchDown(float x,float y){return true;}
    public boolean OnTouchUp(float x,float y){return true;}
    public boolean OnMultiTouchDown(float x,float y){return true;}

    public boolean onTouchEvent(MotionEvent event){
        int action_index = event.getActionIndex();
        int action = event.getActionMasked();
        float x = event.getX(action_index);
        float y = event.getY(action_index);

        /*
        if (action == MotionEvent.ACTION_DOWN  || action == MotionEvent.ACTION_POINTER_DOWN) {
            // if (myActivity == MyActivity.GameActivity)
            OnTouchDown_game(x,y);

        }else if (action == MotionEvent.ACTION_MOVE) {
            // if (myActivity == MyActivity.GameActivity)
            OnTouchMove_game(x,y);
        }*/
        if (action == MotionEvent.ACTION_DOWN ) return OnTouchDown(x,y);
        else if (action == MotionEvent.ACTION_MOVE ) return OnTouchMove(x,y);
        else if (action == MotionEvent.ACTION_UP ) return OnTouchUp(x,y);
        else if (action == MotionEvent.ACTION_POINTER_DOWN )return OnMultiTouchDown(x,y);
        else return true;

    }
    public void pause(){}
    public void resume(){}
}
