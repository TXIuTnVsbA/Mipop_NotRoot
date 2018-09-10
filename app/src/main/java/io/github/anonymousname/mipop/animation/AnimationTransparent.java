package io.github.anonymousname.mipop.animation;

import android.os.Handler;
import android.view.View;

import io.github.anonymousname.mipop.widget.MeterBack;
import io.github.anonymousname.mipop.widget.MeterBase;
import io.github.anonymousname.mipop.widget.MeterHome;
import io.github.anonymousname.mipop.widget.MeterMenu;
import io.github.anonymousname.mipop.widget.MeterRecent;

public class AnimationTransparent {
    private static int currentAlpha = 255;
    private static int endAlpha = 100;
    private static int startAlpha = 255;
    private static long time4Trans = 2000L;
    private static Handler handler4Transparent = new Handler();
    private static int periodTime = 10;
    private static Runnable runnable4Transparent = new Runnable() {
        @Override
        public void run() {
            transparenting();
        }
    };

    public static void start() {
        periodTime = (int) (time4Trans / Math.abs(startAlpha - endAlpha));
        handler4Transparent.postDelayed(runnable4Transparent, 1L);
        MeterBase.MeterMap.get(MeterHome.NAME).setVisibility(View.GONE);
        MeterBase.MeterMap.get(MeterMenu.NAME).setVisibility(View.GONE);
        MeterBase.MeterMap.get(MeterRecent.NAME).setVisibility(View.GONE);
    }

    public static void stop() {
        currentAlpha = startAlpha;
        handler4Transparent.removeCallbacks(runnable4Transparent);
        MeterBase.MeterMap.get(MeterBack.NAME).setAlpha(startAlpha / 255.0f);
        MeterBase.MeterMap.get(MeterHome.NAME).setVisibility(View.VISIBLE);
        MeterBase.MeterMap.get(MeterMenu.NAME).setVisibility(View.VISIBLE);
        MeterBase.MeterMap.get(MeterRecent.NAME).setVisibility(View.VISIBLE);
    }

    private static void transparenting() {
        if (currentAlpha <= endAlpha) {
            return;
        }
        currentAlpha = currentAlpha - 1;
        MeterBase.MeterMap.get(MeterBack.NAME).setAlpha(currentAlpha / 255.0f);
        periodTime = (int) (time4Trans / Math.abs(startAlpha - endAlpha));
        handler4Transparent.postDelayed(runnable4Transparent, periodTime);
    }
}
