package io.github.anonymousname.mipop.widget;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;


import java.util.HashMap;
import java.util.Map;

import io.github.anonymousname.mipop.animation.AnimationParking;

public abstract class MeterBase extends ImageView {
    public static Map<String, MeterBase> MeterMap = new HashMap();
    public static int baseX = 0;
    public static int baseY = (Until.SCREEM_HEIGHT / 2);
    public static Context mContext;
    public static int mLeftMargin = 0;
    public static boolean mTouchDown = false;
    public static Paint paint = new Paint();
    private Handler handler4LongClick = new Handler();
    private boolean hasMoved = false;
    public boolean isLongClick = false;
    private final long mTime4LongClick = 1000L;
    public WindowManager mWindowManager = null;
    private int resId = 0;
    private int resIdPressed = 0;
    private Runnable runnable4LongClick = new Runnable() {
        public void run() {
            MeterBase.this.isLongClick = true;
            MeterBase.this.LongClick();
        }
    };
    public LayoutParams wmParams = new LayoutParams();

    protected abstract void Click();

    protected abstract void LongClick();

    public MeterBase(Context context) {
        super(context);
        mContext = context;
        this.mWindowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        this.wmParams.type = LayoutParams.TYPE_SYSTEM_ALERT;
        this.wmParams.format = PixelFormat.TRANSPARENT;
        this.wmParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        this.wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        this.wmParams.x = baseX;
        this.wmParams.y = baseY;
        this.wmParams.height = Until.IMAGE_WIDTH;
        this.wmParams.width = Until.IMAGE_WIDTH;
        this.mWindowManager.addView(this, this.wmParams);
    }

    public void Register(String key, MeterBase value) {
        MeterMap.put(key, value);
    }

    public void setResId(int id, int idDown) {
        this.resId = id;
        this.resIdPressed = idDown;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setImageResource(resIdPressed);
                handler4LongClick.postDelayed(runnable4LongClick, mTime4LongClick);
                AnimationParking.stop();
                return true;
            case MotionEvent.ACTION_MOVE:
                return true;
            case MotionEvent.ACTION_UP:
                setImageResource(resId);
                this.handler4LongClick.removeCallbacks(runnable4LongClick);
                if (!hasMoved) {
                    if (!isLongClick) {
                        Click();
                    }
                }
                if(isLongClick){
                }else if(hasMoved){
                }

                hasMoved = false;
                isLongClick = false;
                AnimationParking.start();
                return true;

            default:
                break;
        }
        AnimationParking.shrinkStart();
        return true;
    }

    public void update(int x, int y) {
        this.wmParams.x = x;
        this.wmParams.y = y;
        this.mWindowManager.updateViewLayout(this, this.wmParams);
    }

    public void moved() {
        this.hasMoved = true;
        this.handler4LongClick.removeCallbacks(this.runnable4LongClick);
    }

}
