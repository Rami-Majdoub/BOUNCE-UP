package com.ramimajdoub.bounceup.GameApplication.Activities.ActivityLoading;

import android.graphics.Canvas;
import android.graphics.Color;

import com.ramimajdoub.bounceup.GameApplication.Activities.ActivityGame.GameActivity;
import com.ramimajdoub.bounceup.GameApplication.Activities.ActivityHome.HomeActivity;
import com.ramimajdoub.bounceup.GameApplication.Activities.MyAppActivity;
import com.ramimajdoub.bounceup.GameApplication.GameView;

public class LoadingActivity extends MyAppActivity {
    public LoadingActivity(GameView gameView) {super(gameView);}

    @Override
    public void setup() {
    }

    @Override
    public void update() { if (draw) view.goToActivityHome(); }

    private boolean draw = false;

    @Override
    public void draw(Canvas canvas) {
        if (!draw) {
            draw = true;
            view.homeActivity = new HomeActivity(view);
            view.gameActivity = new GameActivity(view);
            canvas.drawColor(Color.WHITE);
        }
    }
}
