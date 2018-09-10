package io.github.anonymousname.mipop.api;

import android.app.Application;

import io.github.anonymousname.mipop.widget.MeterBack;
import io.github.anonymousname.mipop.widget.MeterHome;
import io.github.anonymousname.mipop.widget.MeterMenu;
import io.github.anonymousname.mipop.widget.MeterRecent;
import io.github.anonymousname.mipop.widget.Until;

public class MyApplication extends Application {
    public void onCreate() {
        super.onCreate();
        Until.initialPop(getApplicationContext());
        new MeterMenu(getApplicationContext());
        new MeterRecent(getApplicationContext());
        new MeterHome(getApplicationContext());
        new MeterBack(getApplicationContext());
    }
}
