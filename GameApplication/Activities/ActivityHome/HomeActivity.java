package com.ramimajdoub.bounceup.GameApplication.Activities.ActivityHome;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import com.ramimajdoub.bounceup.GameApplication.Activities.MyAppActivity;
import com.ramimajdoub.bounceup.GameApplication.Drawing.FontHolder;
import com.ramimajdoub.bounceup.GameApplication.Drawing.TextAlphaAnimation;
import com.ramimajdoub.bounceup.GameApplication.GameView;
import com.ramimajdoub.bounceup.GameApplication.SaveAndLoad.DataBase;
import com.ramimajdoub.bounceup.R;

import static com.ramimajdoub.bounceup.GameApplication.Constant.backgroundColor;
import static com.ramimajdoub.bounceup.GameApplication.Drawing.Screen.*;

public class HomeActivity extends MyAppActivity{
    public HomeActivity(GameView gameView) {
        super(gameView);
    }

    private DataBase database = view.database;

    // HOME
    private static final FontHolder REGION_TITLE_1 =
            new FontHolder("BOUNCE", x(82), y(115), x(396), y(169) );

    private static final FontHolder REGION_TITLE_2 =
            new FontHolder("UP", x(187), y(211), x(293), y(265) );

    private static final RectF REGION_HIGH_SCORE_IMG = new RectF(x(147), y(320), x(225), y(375) );
    private Bitmap imgHighScore = getImage(R.drawable.high_score, REGION_HIGH_SCORE_IMG.height(), REGION_HIGH_SCORE_IMG.width());

    private static final FontHolder HIGH_SCORE =
            new FontHolder("999", x(255), y(320), x(354), y(375) );

    private static final TextAlphaAnimation TAP_TO_PLAY =
            new TextAlphaAnimation("TAP TO START !", x(100), y(440), x(380), y(485));

    private static final RectF REGION_MUSIC = new RectF(x(60), y(545), x(160), y(645));
    private static final RectF REGION_RATE = new RectF(x(190), y(545), x(290), y(645));
    private static final RectF REGION_SHARE = new RectF(x(320), y(545), x(420), y(645));

    private Bitmap imgMusicOn = getImage(R.drawable.music_on, REGION_SHARE.height(), REGION_SHARE.width());
    private Bitmap imgMusicOff = getImage(R.drawable.music_off, REGION_SHARE.height(), REGION_SHARE.width());
    private Bitmap imgShare = getImage(R.drawable.share, REGION_SHARE.height(), REGION_SHARE.width());
    private Bitmap imgRate = getImage(R.drawable.rate, REGION_RATE.height(), REGION_RATE.width());

    @Override
    public void setup() {
        FontHolder.textPainter.setTypeface(Typeface.createFromAsset(
                view.getContext().getAssets(),"fonts/hemiHead.ttf"));
        HIGH_SCORE.setText(database.getHighScore());
        TAP_TO_PLAY.init();
    }

    @Override
    public void update() { TAP_TO_PLAY.update(); }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(backgroundColor);
        REGION_TITLE_1.draw(canvas);
        REGION_TITLE_2.draw(canvas);
        canvas.drawBitmap(imgHighScore, REGION_HIGH_SCORE_IMG.left, REGION_HIGH_SCORE_IMG.top, null);
        HIGH_SCORE.draw(canvas);
        TAP_TO_PLAY.draw(canvas);
        // if (view.database.isMusicActive())
        // canvas.drawBitmap(imgMusicOn, REGION_MUSIC.left, REGION_MUSIC.top, null);
        // else
        canvas.drawBitmap(imgMusicOff, REGION_MUSIC.left, REGION_MUSIC.top, null);
        canvas.drawBitmap(imgShare, REGION_SHARE.left, REGION_SHARE.top, null);
        canvas.drawBitmap(imgRate, REGION_RATE.left, REGION_RATE.top, null);
    }

    @Override
    public boolean OnTouchDown(float x, float y) {
        if (REGION_MUSIC.contains(x, y)) view.database.musicToggle();
        else if (REGION_SHARE.contains(x, y)) shareApp();
        else if (REGION_RATE.contains(x, y)) rateApp();
        else view.goToActivityGame();

        return super.OnTouchDown(x, y);
    }

    private final String packageName =  view.getContext().getPackageName();
    private final String playStoreUrl = "https://play.google.com/store/apps/details?id=" + packageName;
    private void rateApp(){
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);

        if (Build.VERSION.SDK_INT >= 21)
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        else goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

        try {view.getContext().startActivity(goToMarket);}
        catch (ActivityNotFoundException e) {view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(playStoreUrl)));}
    }
    private void shareApp() {
        final String appName = view.getResources().getString(R.string.app_name);
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,"OMG i scored " + view.database.getHighScore() + " at " + appName + "! Can you beat my score ? " + playStoreUrl);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, appName);
        sendIntent.setType("text/plain");
        view.getContext().startActivity(Intent.createChooser(sendIntent,"Share"));
    }
}
