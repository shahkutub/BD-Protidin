package com.kalerkantho.Gcm;

import android.app.Application;

/**
 * Created by wlaptop on 9/3/2016.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
      //  Firebase.setAndroidContext(this);
    }
}
