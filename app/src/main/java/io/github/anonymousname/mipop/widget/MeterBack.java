package io.github.anonymousname.mipop.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.MotionEvent;


import org.greenrobot.eventbus.EventBus;

import io.github.anonymousname.mipop.R;
import io.github.anonymousname.mipop.animation.AnimationParking;
import io.github.anonymousname.mipop.api.MyAccessibilityService;

public class MeterBack extends MeterBase {
    public static final String NAME = MeterBack.class.getSimpleName();
    private int changeX = 0;
    private int changeY = 0;
    private boolean hasMoved = false;
//    private int mTouchStartX = 0;
//    private int mTouchStartY = 0;

    public MeterBack(Context context) {
        super(context);
        Register(NAME, this);
        setSoundEffectsEnabled(true);
        setImageResource(R.drawable.back);
        setResId(R.drawable.back, R.drawable.back_pressed);
    }

    public void Click() {
        Log.i("Suhao.Click", "back click");
        playSoundEffect(0);
        new Thread() {
            public void run() {
                try {
                    EventBus.getDefault().post(MyAccessibilityService.BACK);
                    //Until.runRootCommand("input keyevent 4");
                    //new Instrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                    Log.i("shenzhan", "Back implement");
                } catch (Exception e) {
                    Log.d("shenzhan", e.toString());
                }
            }
        }.start();
    }

    public void LongClick() {
    }

    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Until.initialPop(getContext());
        AnimationParking.land();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY() - Until.STATUS_HEIGHT;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.changeX = rawX;
                this.changeY = rawY;
//                this.mTouchStartX = rawX;
//                this.mTouchStartY = rawY;
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = rawX - changeX;
                int offsetY = rawY - changeY;
                if ((Math.abs(offsetX) > 3) || (Math.abs(offsetY) > 3)) {
                    //AppLog.i("way", "baseX/offsetX = " + baseX + "/" + offsetX);
                    baseX = offsetX + baseX;
                    baseY = offsetY + baseY;
                    AnimationParking.updateAll(baseX, baseY);
                    changeX = rawX;
                    changeY = rawY;
                    moved();
                }
                break;

            case MotionEvent.ACTION_UP:
                if (!this.hasMoved) {
                }
                hasMoved = false;
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);

    }
}
