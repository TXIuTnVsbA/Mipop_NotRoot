package io.github.anonymousname.mipop.animation;

import android.os.Handler;
import android.view.View;

import io.github.anonymousname.mipop.widget.MeterBack;
import io.github.anonymousname.mipop.widget.MeterBase;
import io.github.anonymousname.mipop.widget.MeterHome;
import io.github.anonymousname.mipop.widget.MeterMenu;
import io.github.anonymousname.mipop.widget.MeterRecent;
import io.github.anonymousname.mipop.widget.Until;


public class AnimationParking {
    public static final boolean LEFT = true;
    public static final boolean RIGHT = false;
    private static String TAG = "AnimationParking";
    public static int baseX = MeterBase.baseX;
    public static int baseY = MeterBase.baseY;
    private static Handler handler4Parking = new Handler();
    private static Handler handler4PosCheck = new Handler();
    private static Handler handler4Shrink = new Handler();
    private static Handler handler4Turning = new Handler();
    private static int homeX;
    private static int homeY;
    public static boolean mAreaChanged = false;
    private static long mAutoUpdatePeriod = 10L;
    public static boolean mOriginSide = LEFT;
    private static long mParking2Shrink = 2000L;
    private static int mStep = 15;
    private static boolean mTimeOut;
    private static long mVelocityTime = 300L;
    private static int menuX;
    private static int menuY;
    private static int recentX;
    private static int recentY;
    private static Runnable runnable4Parking = new Runnable() {

        @Override
        public void run() {
            parking();
        }
    };
    private static Runnable runnable4PosCheck = new Runnable() {

        @Override
        public void run() {
            mTimeOut = true;
            velocityCheck = true;
            if (mAreaChanged) {
                mAreaChanged = false;
            }
        }
    };
    private static Runnable runnable4Shrink = new Runnable() {
        @Override
        public void run() {
            shrinking();
        }
    };
    private static Runnable runnable4Turning = new Runnable() {

        @Override
        public void run() {
            turning();
        }
    };
    private static boolean velocityCheck;

    private static void hideSub() {
        MeterBase.MeterMap.get(MeterRecent.NAME).setVisibility(View.INVISIBLE);
        MeterBase.MeterMap.get(MeterHome.NAME).setVisibility(View.INVISIBLE);
        MeterBase.MeterMap.get(MeterMenu.NAME).setVisibility(View.INVISIBLE);
    }

    private static void initial() {
        baseX = MeterBase.baseX;
        baseY = MeterBase.baseY;
    }

    public static void land() {
        if (!mOriginSide) {
            stop();
            baseX = Until.SCREEM_WIDTH - Until.IMAGE_WIDTH;
            updateAll(baseX, baseY);
        }
        if (MeterBase.baseY > Until.SCREEM_HEIGHT) {
            updateBottom(baseX, baseY);
        }
        shrinkStart();
    }

    private static void parking() {
        if (baseX < Until.MID_LINE) {
            parking2Margin(true);
        } else {
            parking2Margin(false);
        }
    }

    private static void parking2Margin(boolean isLeft) {
        int expendLine = Until.EXPEND_LINE;
        if (!isLeft) {
            expendLine = Until.SCREEM_WIDTH - Until.IMAGE_WIDTH
                    - Until.EXPEND_LINE;
        }
        int speed = mStep;
        if (baseX > expendLine) {
            speed = -mStep;
        }
        baseX = speed + baseX;
        updateAll(baseX, baseY);
        if (Math.abs(baseX - expendLine) <= mStep) {
            baseX = expendLine;
            updateAll(baseX, baseY);
            handler4Parking.removeCallbacks(runnable4Parking);
            handler4Turning.postDelayed(runnable4Turning, mParking2Shrink);
            return;
        }
        handler4Shrink.removeCallbacks(runnable4Shrink);
        handler4Parking.postDelayed(runnable4Parking, mAutoUpdatePeriod);
    }

    private static void posCalculateLeftX(int x, int y) {
        if (x <= Until.EXPEND_LINE) {
            recentX = x - Until.EXPEND_LINE;
            recentY = y;
            homeX = x / 2;
            homeY = y - x / 2;
            menuX = x / 2;
            menuY = y + x / 2;
            return;
        }
        if (mOriginSide) {
            if (x < Until.MID_LINE) {
                recentX = x - Until.EXPEND_LINE;
                recentY = y;
                homeX = x - Until.EXPEND_LINE / 2;
                homeY = y - Until.EXPEND_LINE / 2;
                menuX = x - Until.EXPEND_LINE / 2;
                menuY = y + Until.EXPEND_LINE / 2;
                return;
            }
            if (x < Until.MID_LINE + Until.SHRINK_LINE) {
                recentX = x - Until.EXPEND_LINE + Until.EXPEND_LINE
                        / Until.SHRINK_LINE * (x - Until.MID_LINE);
                recentY = y;
                homeX = x - Until.EXPEND_LINE / 2 + Until.EXPEND_LINE
                        / Until.SHRINK_LINE * (x - Until.MID_LINE) / 2;
                homeY = y - Until.EXPEND_LINE / 2 + Until.EXPEND_LINE
                        / Until.SHRINK_LINE * (x - Until.MID_LINE) / 2;
                menuX = x - Until.EXPEND_LINE / 2 + Until.EXPEND_LINE
                        / Until.SHRINK_LINE * (x - Until.MID_LINE) / 2;
                menuY = y + Until.EXPEND_LINE / 2 - Until.EXPEND_LINE
                        / Until.SHRINK_LINE * (x - Until.MID_LINE) / 2;
                return;
            }
        }
        mAreaChanged = true;
        recentX = x;
        recentY = y;
        homeX = x;
        homeY = y;
        menuX = x;
        menuY = y;
        hideSub();

    }

    private static void quickSlide() {
        handler4PosCheck.removeCallbacks(runnable4PosCheck);
        if (!mTimeOut) {
            velocityCheck = true;
        }
        if (mAreaChanged) {
            velocityCheck = false;
        }
    }

    private static void showOrHide(int x) {
        if (!velocityCheck) {
            hideSub();
        } else {
            if (mAreaChanged) {
                hideSub();
                return;
            }
            if ((x > Until.SCREEM_WIDTH - Until.IMAGE_WIDTH) || (x < 0)) {
                hideSub();
                return;
            }

            showSub();
        }
    }

    private static void showSub() {
        MeterBase.MeterMap.get(MeterRecent.NAME).setVisibility(View.VISIBLE);
        MeterBase.MeterMap.get(MeterHome.NAME).setVisibility(View.VISIBLE);
        MeterBase.MeterMap.get(MeterMenu.NAME).setVisibility(View.VISIBLE);
    }

    public static void shrinkStart() {
        handler4Parking.removeCallbacks(runnable4Parking);
        handler4Shrink.postDelayed(runnable4Shrink, mAutoUpdatePeriod);
    }

    private static void shrinking() {
        int speed = mStep;
        if (baseX < Until.MID_LINE) {
            speed = -speed;
        }
        baseX = speed + baseX;
        updateAll(baseX, baseY);
        if (baseX >= Until.SCREEM_WIDTH - Until.IMAGE_WIDTH) {
            baseX = Until.SCREEM_WIDTH - Until.IMAGE_WIDTH;
            updateAll(baseX, baseY);
            velocityCheck = false;
            mOriginSide = RIGHT;
            mAreaChanged = false;
            handler4Shrink.removeCallbacks(runnable4Shrink);
            AnimationTransparent.start();
            return;
        }
        if (baseX <= 1) {
            baseX = 0;
            updateAll(baseX, baseY);
            velocityCheck = false;
            mOriginSide = LEFT;
            mAreaChanged = false;
            handler4Shrink.removeCallbacks(runnable4Shrink);
            AnimationTransparent.start();
            return;
        }
        handler4Shrink.postDelayed(runnable4Shrink, mAutoUpdatePeriod);
    }

    public static void start() {
        quickSlide();
        initial();
        if (baseX <= 0) {
            mOriginSide = LEFT;
            mAreaChanged = false;
            velocityCheck = false;
            baseX = 0;
            AnimationTransparent.start();
            return;
        }
        if (baseX >= Until.SCREEM_WIDTH - Until.IMAGE_WIDTH) {
            mOriginSide = RIGHT;
            mAreaChanged = false;
            velocityCheck = false;
            baseX = Until.SCREEM_WIDTH - Until.IMAGE_WIDTH;
            AnimationTransparent.start();
            return;
        }
        updateTop(baseX, baseY);
        updateBottom(baseX, baseY);
        if (!mAreaChanged) {
            if ((baseX < Until.PARKING_LINE)
                    || (baseX > Until.PARKING_LINE_RIGHT)) {
                handler4Parking.removeCallbacks(runnable4Parking);
                handler4Shrink.postDelayed(runnable4Shrink, mAutoUpdatePeriod);
                return;
            }
            if ((mOriginSide) && (baseX > Until.MID_LINE)) {
                handler4Parking.removeCallbacks(runnable4Parking);
                handler4Shrink.postDelayed(runnable4Shrink, mAutoUpdatePeriod);
                return;
            }
            if ((!mOriginSide) && (baseX < Until.MID_LINE)) {
                handler4Parking.removeCallbacks(runnable4Parking);
                handler4Shrink.postDelayed(runnable4Shrink, mAutoUpdatePeriod);
                return;
            }
            handler4Shrink.removeCallbacks(runnable4Shrink);
            handler4Parking.postDelayed(runnable4Parking, mAutoUpdatePeriod);
            return;
        }
        handler4Parking.removeCallbacks(runnable4Parking);
        handler4Shrink.postDelayed(runnable4Shrink, mAutoUpdatePeriod);
    }

    public static void stop() {
        AnimationTransparent.stop();
        handler4Parking.removeCallbacks(runnable4Parking);
        handler4Shrink.removeCallbacks(runnable4Shrink);
        handler4Turning.removeCallbacks(runnable4Turning);
        mTimeOut = false;
        handler4PosCheck.postDelayed(runnable4PosCheck, mVelocityTime);
    }

    private static void turning() {
        handler4Shrink.postDelayed(runnable4Shrink, mAutoUpdatePeriod);
        handler4Turning.removeCallbacks(runnable4Turning);
    }

    public static void updateAll(int x, int y) {
        if (x < 0) {
            x = 0;
        }
        if (x > Until.SCREEM_WIDTH - Until.IMAGE_WIDTH) {
            x = Until.SCREEM_WIDTH - Until.IMAGE_WIDTH;
        }
        if (y < 0) {
            y = 0;
        }
        MeterBase.MeterMap.get(MeterBack.NAME).update(x, y);
        MeterBase.baseX = x;
        MeterBase.baseY = y;
        if (mOriginSide) {
            updateAllLeft(x, y);
        } else {
            updateAllRight(x, y);
        }
    }

    private static void updateAllLeft(int x, int y) {
        showOrHide(x);
        posCalculateLeftX(x, y);
        MeterBase.MeterMap.get(MeterRecent.NAME).update(recentX, recentY);
        MeterBase.MeterMap.get(MeterHome.NAME).update(homeX, homeY);
        MeterBase.MeterMap.get(MeterMenu.NAME).update(menuX, menuY);
    }

    private static void updateAllRight(int x, int y) {
        showOrHide(x);
        if (x >= Until.EXPEND_LINE_RIGHT) {
            recentX = x + Until.EXPEND_LINE;
            recentY = y;
            homeX = (x + Until.SCREEM_WIDTH - Until.IMAGE_WIDTH) / 2;
            homeY = y - (Until.SCREEM_WIDTH - Until.IMAGE_WIDTH - x) / 2;
            menuX = (x + Until.SCREEM_WIDTH - Until.IMAGE_WIDTH) / 2;
            menuY = y + (Until.SCREEM_WIDTH - Until.IMAGE_WIDTH - x) / 2;
        } else {
            if (x > Until.MID_LINE) {
                recentX = x + Until.EXPEND_LINE;
                recentY = y;
                homeX = x + Until.EXPEND_LINE / 2;
                homeY = y - Until.EXPEND_LINE / 2;
                menuX = x + Until.EXPEND_LINE / 2;
                menuY = y + Until.EXPEND_LINE / 2;
            } else if (x > Until.MID_LINE - Until.SHRINK_LINE) {
                recentX = x + Until.EXPEND_LINE + Until.EXPEND_LINE
                        / Until.SHRINK_LINE * (x - Until.MID_LINE);
                recentY = y;
                homeX = x + Until.EXPEND_LINE / 2 + Until.EXPEND_LINE
                        / Until.SHRINK_LINE * (x - Until.MID_LINE) / 2;
                homeY = y - Until.EXPEND_LINE / 2 - Until.EXPEND_LINE
                        / Until.SHRINK_LINE * (x - Until.MID_LINE) / 2;
                menuX = x + Until.EXPEND_LINE / 2 + Until.EXPEND_LINE
                        / Until.SHRINK_LINE * (x - Until.MID_LINE) / 2;
                menuY = y + Until.EXPEND_LINE / 2 + Until.EXPEND_LINE
                        / Until.SHRINK_LINE * (x - Until.MID_LINE) / 2;
            } else if (!mOriginSide) {
                mAreaChanged = true;
                recentX = x;
                recentY = y;
                homeX = x;
                homeY = y;
                menuX = x;
                menuY = y;
                hideSub();
            }
        }
        MeterBase.MeterMap.get(MeterRecent.NAME).update(recentX, recentY);
        MeterBase.MeterMap.get(MeterHome.NAME).update(homeX, homeY);
        MeterBase.MeterMap.get(MeterMenu.NAME).update(menuX, menuY);
    }

    private static void updateBottom(int x, int y) {
        if (y <= Until.BOTTOM_LINE) {
            return;
        }

        if (mOriginSide) {
            if (x >= Until.PARKING_LINE && x <= Until.MID_LINE) {
                int offsetY = Until.BOTTOM_LINE;
                baseX = x;
                baseY = offsetY;
                updateAll(x, offsetY);
            }
        } else {
            if (x <= Until.SCREEM_WIDTH - Until.PARKING_LINE
                    && x > Until.MID_LINE) {
                int offsetY = Until.BOTTOM_LINE;
                baseX = x;
                baseY = offsetY;
                updateAll(x, offsetY);
            }
        }
    }

    private static void updateTop(int x, int y) {
        if (y >= (int) (0.707D * Until.IMAGE_WIDTH)) {
            return;
        }

        if (mOriginSide) {
            if (x >= Until.PARKING_LINE && x <= Until.MID_LINE) {
                int offsetY = Until.EXPEND_LINE / 2;
                baseX = x;
                baseY = offsetY;
                updateAll(x, offsetY);
            }
        } else {
            if (x <= Until.SCREEM_WIDTH - Until.PARKING_LINE
                    && x > Until.MID_LINE) {
                int offsetY = Until.EXPEND_LINE / 2;
                baseX = x;
                baseY = offsetY;
                updateAll(x, offsetY);
            }
        }

    }
}
