package io.github.anonymousname.mipop.widget;

import android.content.Context;
import android.util.Log;


import org.greenrobot.eventbus.EventBus;

import io.github.anonymousname.mipop.R;
import io.github.anonymousname.mipop.api.MyAccessibilityService;

public class MeterRecent extends MeterBase {
    public static final String NAME = MeterRecent.class.getSimpleName();

    public MeterRecent(Context context) {
        super(context);
        Register(NAME, this);
        setSoundEffectsEnabled(true);
        setImageResource(R.drawable.recent_selector);
        setResId(R.drawable.recent, R.drawable.recent_pressed);
    }

    public void Click() {
        Log.i("CLICK", "recent  click");
        playSoundEffect(0);
        new Thread() {
            public void run() {
                try {
                    EventBus.getDefault().post(MyAccessibilityService.RECENT);
                    //Until.runRootCommand("input keyevent 187");
                    //new Instrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_APP_SWITCH);
                    Log.i("shenzhan", "APP_SWITCH implement");
                    return;
                } catch (Exception e) {
                    Log.d("shenzhan", e.toString());
                }
            }
        }.start();
    }

    public void LongClick() {
        Log.i("Suhao", "recent  long click");
    }
}
