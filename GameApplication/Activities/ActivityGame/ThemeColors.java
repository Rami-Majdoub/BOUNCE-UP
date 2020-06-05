package com.ramimajdoub.bounceup.GameApplication.Activities.ActivityGame;

import android.graphics.Color;

import static com.ramimajdoub.bounceup.GameApplication.Util.Util.random;

public class ThemeColors {
    private static int color1;
    private static int color2;

    public static void setNewTheme() {
        switch (random(4)){
            case 0:
                color1 = Color.rgb(119, 170, 255);
                color2 = Color.rgb(89, 241, 102);
                break;
            case 1:
                color1 = Color.rgb(255, 119, 119);
                color2 = Color.rgb(234, 241, 91);
                break;
            case 2:
                color1 = Color.rgb(89, 241, 102);
                color2 = Color.rgb(119, 170, 255);
                break;
            case 3:
                color1 = Color.rgb(234, 241, 91);
                color2 = Color.rgb(255, 119, 119);
                break;
        }
    }

    public static int getColor1() { return color1; }
    public static int getColor2() { return color2; }
}
