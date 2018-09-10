package io.github.anonymousname.mipop.widget;

import android.content.Context;
import android.util.Log;


import org.greenrobot.eventbus.EventBus;

import io.github.anonymousname.mipop.R;
import io.github.anonymousname.mipop.api.MyAccessibilityService;

public class MeterMenu extends MeterBase {
    public static final String NAME = MeterMenu.class.getSimpleName();

    public MeterMenu(Context context) {
        super(context);
        Register(NAME, this);
        setSoundEffectsEnabled(true);
        setImageResource(R.drawable.menu_selector);
        setResId(R.drawable.menu, R.drawable.menu_pressed);
    }

    public void Click() {
        Log.i("CLICK", "menu click");
        playSoundEffect(0);
        new Thread() {
            public void run() {
                try {
                    //EventBus.getDefault().post(MyAccessibilityService.MENU);
                    //Until.runRootCommand("input keyevent 82");
                    //new Instrumentation().sendKeyDownUpSync(82);
                    Log.i("shenzhan", "MENU implement");
                } catch (Exception e) {
                    Log.d("HouJiong", e.toString());
                }
            }
        }.start();
    }

    public void LongClick() {
        Log.i("Suhao", "menu  long click");
    }
}
