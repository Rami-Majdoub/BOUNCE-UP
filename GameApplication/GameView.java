package com.ramimajdoub.bounceup.GameApplication;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.ramimajdoub.bounceup.GameApplication.Activities.ActivityGame.GameActivity;
import com.ramimajdoub.bounceup.GameApplication.Activities.ActivityHome.HomeActivity;
import com.ramimajdoub.bounceup.GameApplication.Activities.ActivityLoading.LoadingActivity;
import com.ramimajdoub.bounceup.GameApplication.Activities.MyAppActivity;
import com.ramimajdoub.bounceup.GameApplication.SaveAndLoad.DataBase;

import static com.ramimajdoub.bounceup.GameApplication.Constant.*;

public class GameView extends SurfaceView implements Runnable {

    //DRAWING
    private Canvas canvas;
    private final SurfaceHolder ourHolder;
    private Thread gameThread = null;
    private volatile boolean CanDraw;

    public DataBase database = new DataBase(getContext());

    GameView(Context context) {
        super(context);
        ourHolder = getHolder();
        prevActivity = activity = loadingActivity;//homeActivity;
        activity.setup();
    }

    private MyAppActivity prevActivity;
    private MyAppActivity activity;
    public MyAppActivity homeActivity = new HomeActivity(this);
    public MyAppActivity gameActivity = new GameActivity(this);
    private MyAppActivity loadingActivity = new LoadingActivity(this);

    private void setActivity(MyAppActivity prevActivity) {
        activity = prevActivity;
        activity.setup();
    }

    public void goToActivityHome() { prevActivity = homeActivity;}
    public void goToActivityGame() { prevActivity = gameActivity;}

    @Override
    public boolean onTouchEvent(MotionEvent event) {return activity.onTouchEvent(event);}

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();

        while (CanDraw) {
            canvas = null;

            long timeThisFrame = System.currentTimeMillis() - startTime;
            if (timeThisFrame == 0) timeThisFrame = FrameTime;

            if (ourHolder != null && ourHolder.getSurface().isValid()) {

                synchronized (ourHolder) {
                    int framesToUpdate = (int)(timeThisFrame / FrameTime);
                    if(framesToUpdate > 5) framesToUpdate = 5;

                    startTime += framesToUpdate * FrameTime;

                    for (int frame = 0; frame < framesToUpdate; frame++)
                        activity.update();

                    canvas = ourHolder.lockCanvas();
                    activity.draw(canvas);
                }
                if (canvas != null) ourHolder.unlockCanvasAndPost(canvas);

            }

            if (timeThisFrame < FrameTime) {
                // SLEEP TO FORCE THE MAX_FPS
                try {Thread.sleep(FrameTime - timeThisFrame);}
                catch (InterruptedException e) {e.printStackTrace();}
            }

            if (activity != prevActivity)
                setActivity(prevActivity);
        }
    }
    public void pause() {
        // PAUSE GAME
        activity.pause();

        // PAUSE MUSIC
        // musicPlayer.pause();

        // STOP DRAWING
        CanDraw = false;

        try {gameThread.join();}
        catch (InterruptedException e) {Log.e("Error", "joining thread");}
    }
    public void resume() {
        gameThread = new Thread(this);
        gameThread.start();

        // RESUME DRAWING
        CanDraw = true;
        activity.resume();
    }
}