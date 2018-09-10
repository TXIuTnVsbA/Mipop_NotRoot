package io.github.anonymousname.mipop.widget;

import android.content.Context;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import io.github.anonymousname.mipop.R;
import io.github.anonymousname.mipop.api.MyAccessibilityService;

public class MeterHome extends MeterBase {
    public static final String NAME = MeterHome.class.getSimpleName();

    public MeterHome(Context context) {
        super(context);
        Register(NAME, this);
        setSoundEffectsEnabled(true);
        setImageResource(R.drawable.home_selector);
        setResId(R.drawable.home, R.drawable.home_pressed);
    }

    public void Click() {
        Log.i("CLICK", "home   click");
        playSoundEffect(0);
        new Thread() {
            public void run() {
                try {
                    //Until.runRootCommand("input keyevent 3");
                    EventBus.getDefault().post(MyAccessibilityService.HOME);
                    //new Instrumentation().sendKeyDownUpSync(3);
                    Log.i("shenzhan", "Home implement");
                } catch (Exception e) {
                    Log.d("shenzhan", e.toString());
                }
            }
        }.start();
    }

    public void LongClick() {
        Log.i("Suhao", "home  long click");
    }
}
