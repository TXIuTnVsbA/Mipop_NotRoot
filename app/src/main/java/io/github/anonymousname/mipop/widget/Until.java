package io.github.anonymousname.mipop.widget;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

public class Until {
    public static int BOTTOM_LINE;
    public static int EXPEND_LINE;
    public static int EXPEND_LINE_RIGHT;
    public static int EXPEND_OFFSET;
    public static int IMAGE_WIDTH;
    public static int MID_LINE;
    public static int MOVE_MAX_SIZE;
    public static int PARKING_LINE;
    public static int PARKING_LINE_RIGHT;
    public static int SCREEM_HEIGHT;
    public static int SCREEM_WIDTH;
    public static int SHRINK_LINE;
    public static int STATUS_HEIGHT;
    private static final int EXPEND_OFFSET_FACTOR = 10;
    private static final int IMAGE_HEIGHT_FACTOR = 6;
    private static final int MID_LINE_FACTOR = 2;
    private static final int MOVE_MAX_FACTOR = 5;
    private static float PARKING_FACTOR = 0.707F;
    private static final int SHRINK_FACTOR = 2;

    public static void initialPop(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(displayMetrics);
        SCREEM_WIDTH = displayMetrics.widthPixels;
        SCREEM_HEIGHT = displayMetrics.heightPixels;
        setStatusBarHeight(context);
        IMAGE_WIDTH = Math.min(SCREEM_WIDTH, SCREEM_HEIGHT)
                / IMAGE_HEIGHT_FACTOR;
        MID_LINE = (SCREEM_WIDTH - IMAGE_WIDTH) / MID_LINE_FACTOR;
        MOVE_MAX_SIZE = IMAGE_WIDTH / MOVE_MAX_FACTOR;
        EXPEND_OFFSET = IMAGE_WIDTH / EXPEND_OFFSET_FACTOR;
        EXPEND_LINE = (int) (1.414D * IMAGE_WIDTH) + EXPEND_OFFSET;
        SHRINK_LINE = IMAGE_WIDTH / SHRINK_FACTOR;
        PARKING_LINE = (int) (PARKING_FACTOR * IMAGE_WIDTH);
        BOTTOM_LINE = SCREEM_HEIGHT - STATUS_HEIGHT - IMAGE_WIDTH - EXPEND_LINE
                / 2;
        EXPEND_LINE_RIGHT = SCREEM_WIDTH - IMAGE_WIDTH - EXPEND_LINE;
        PARKING_LINE_RIGHT = SCREEM_WIDTH - IMAGE_WIDTH - PARKING_LINE;
    }

    private static void setStatusBarHeight(Context context) {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            STATUS_HEIGHT = context.getResources().getDimensionPixelSize(Integer.parseInt(c.getField("status_bar_height").get(c.newInstance()).toString()));
            Log.i("BAR", "sbar = " + STATUS_HEIGHT);
        } catch (Exception e1) {
            Log.i("BAR", "get status bar height fail");
            e1.printStackTrace();
        }
    }
}
