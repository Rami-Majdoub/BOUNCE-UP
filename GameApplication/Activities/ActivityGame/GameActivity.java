package com.ramimajdoub.bounceup.GameApplication.Activities.ActivityGame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.ramimajdoub.bounceup.GameApplication.Activities.MyAppActivity;
import com.ramimajdoub.bounceup.GameApplication.Drawing.FontHolder;
import com.ramimajdoub.bounceup.GameApplication.Drawing.TextAlphaAnimation;
import com.ramimajdoub.bounceup.GameApplication.GameView;
import com.ramimajdoub.bounceup.R;

import java.util.ArrayList;

import static com.ramimajdoub.bounceup.GameApplication.Constant.backgroundColor;
import static com.ramimajdoub.bounceup.GameApplication.Constant.max_fps;
import static com.ramimajdoub.bounceup.GameApplication.Drawing.Screen.*;
import static com.ramimajdoub.bounceup.GameApplication.Util.Util.*;

public class GameActivity extends MyAppActivity{
    public GameActivity(GameView gameView) {
        super(gameView);
    }

    private enum Game_State {PLAYING , PAUSED , GAME_OVER}
    private Game_State gameState ;

    private final RectF REGION_GAME = new RectF(0, 0, WIDTH, HEIGHT);

    private final RectF REGION_GAME_OVER = new RectF(       x(100), y(180), x(380), y(430));
    private final RectF REGION_HIGH_SCORE_IMG = new RectF(  x(145), y(240), x(220), y(285));
    private FontHolder txtHighScore = new FontHolder("999", x(260), y(230), x(340), y(300));
    private FontHolder txtScore2 = new FontHolder("999",    x(200), y(310), x(280), y(380));

    private FontHolder REGION_PAUSED = new FontHolder("PAUSED",x(140), y(250), x(340), y(350));
    private TextAlphaAnimation REGION_RESUME = new TextAlphaAnimation("TAP TO RESUME",x(100), y(440), x(380), y(485));

    private float score;
    private final float SCORE_INC = 0.1f;
    private FontHolder txtScore1 = new FontHolder("999", x(200), y(30), x(280), y(100));
    private void updateScore() {
        score += SCORE_INC;
        txtScore1.setText((int) score);
    }

    private Bitmap imgHighScore = getImage(R.drawable.high_score, REGION_HIGH_SCORE_IMG.height(), REGION_HIGH_SCORE_IMG.width());

    private ProgressBar progressBar = new ProgressBar(x(100), y(730), x(380), y(760), 20);
    private boolean punechment;

    private Ball ball = new Ball();
    private ArrayList<Spike> spikes = new ArrayList<>();
    private boolean isDown;

    private int spikeRightCounter, spikeLeftCounter;

    // 25 100
    private void newSpikeRightCounter(){ spikeRightCounter = random(30, 75); }
    private void newSpikeLeftCounter(){ spikeLeftCounter = random(30, 75); }

    private void setTheme() {
        int color1 = ThemeColors.getColor1();
        int color2 = ThemeColors.getColor2();

        ball.setColor(color1);
        paintGameOverTab.setColor(color1);
        progressBar.reset(color1);

        Spike.setColor(color2);
        paintGameBorders.setColor(color2);
    }
    private void setNewTheme() {
        ThemeColors.setNewTheme();
        setTheme();
    }

    private void newGame() {
        ball.reset();

        setNewTheme();

        spikes.clear();
        spikes.add(new Spike(randomBool()));

        newSpikeLeftCounter();
        newSpikeRightCounter();

        progressBar.setEmpty();
        punechment = false;

        isDown = false;
        score = 0;
        gameState = Game_State.PLAYING;
    }

    @Override
    public void setup() {
        paintGameBorders.setStrokeWidth(x(20));
        paintGameBorders.setStyle(Paint.Style.STROKE);

        newGame();
    }

    private void addSpikes() {
        spikeRightCounter--;
        spikeLeftCounter--;

        if (spikeRightCounter == 0) {
            spikes.add(new Spike(true));
            newSpikeRightCounter();
        }
        if (spikeLeftCounter == 0) {
            spikes.add(new Spike(false));
            newSpikeLeftCounter();
        }
    }

    @Override
    public void update() {
        if (gameState != Game_State.PAUSED) {
            ball.update();


            if (ball.isDead()) gameState = Game_State.GAME_OVER;
            else {
                if (!ball.isDieing()) {
                    if (!isDown) {
                        for (Spike spike : spikes) spike.move(max_fps);
                        updateScore();
                        addSpikes();

                        progressBar.decrease();
                        if (progressBar.isEmpty()) punechment = false;
                    } else {
                        progressBar.increase();
                        if (progressBar.isFull()) {
                            isDown = false;
                            punechment = true;
                        }

                    }

                    for (int i = spikes.size() - 1; i >= 0; i--)
                        if (spikes.get(i).isOutOfScreen(REGION_GAME)) spikes.remove(i);

                    for(Spike spike : spikes)
                        if (spike.collide(ball)) {
                            ball.die();
                            view.database.updateHighScore((int) score);
                            txtScore2.setText((int) score);
                            txtHighScore.setText(view.database.getHighScore());
                            break;
                        }
                }
            }

        }
        else REGION_RESUME.update();
    }

    private Paint paintGameOverTab = new Paint();
    private Paint paintGameBorders = new Paint();

    private int colorPaused = Color.argb(150, 0, 0, 0);
    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(backgroundColor);
        if (gameState == Game_State.GAME_OVER) {
            canvas.drawRoundRect(REGION_GAME_OVER, REGION_GAME_OVER.width()/4,
                    REGION_GAME_OVER.height()/4, paintGameOverTab);

            canvas.drawBitmap(imgHighScore, REGION_HIGH_SCORE_IMG.left, REGION_HIGH_SCORE_IMG.top, null);
            txtScore2.draw(canvas);
            txtHighScore.draw(canvas);
        } else {
            txtScore1.draw(canvas);

            progressBar.draw(canvas);
        }

        canvas.drawRect(REGION_GAME, paintGameBorders);

        for(Spike spike : spikes)
            spike.draw(canvas);

        ball.draw(canvas);

        if (gameState == Game_State.PAUSED) {
            canvas.drawColor(colorPaused);
            REGION_PAUSED.draw(canvas);
            REGION_RESUME.draw(canvas);
        }
    }

    @Override
    public boolean OnTouchDown(float x, float y) {
        if (gameState == Game_State.PAUSED) gameState = Game_State.PLAYING;

        else {
            if (ball.isDead()) view.goToActivityHome();
            else if (ball.isAlive()){
                if (!punechment) isDown = true;
            }
        }
        return super.OnTouchDown(x, y);
    }

    @Override
    public boolean OnTouchUp(float x, float y) {
        isDown = false;
        return super.OnTouchUp(x, y);
    }

    @Override
    public void pause() {
        if (gameState == Game_State.PLAYING)
            gameState = Game_State.PAUSED;
        super.pause();
    }

    @Override
    public void resume() {
        REGION_RESUME.init();
        super.resume();
    }
}
