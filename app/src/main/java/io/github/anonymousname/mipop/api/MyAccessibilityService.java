package io.github.anonymousname.mipop.api;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by asd on 4/20/2017.
 */


public class MyAccessibilityService extends AccessibilityService {

    public static final int BACK = 1;
    public static final int HOME = 2;
    public static final int MENU = 3;
    public static final int RECENT = 4 ;
    private static final String TAG = "ICE";

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: 点击了");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

    }

    @Override
    public void onInterrupt() {

    }

    @SuppressLint("NewApi")
    @Subscribe
    public void onReceive(Integer action){
        switch (action){
            case BACK:
                performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                break;
            case HOME:
                performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
                break;
            case MENU:
                performGlobalAction(AccessibilityService.GLOBAL_ACTION_QUICK_SETTINGS);
            case RECENT:
                performGlobalAction(AccessibilityService.GLOBAL_ACTION_RECENTS);
        }
    }


}