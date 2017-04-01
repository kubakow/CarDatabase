package com.example.lukaszjarka.cardatabase;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            boolean isInDebug = com.facebook.stetho.BuildConfig.DEBUG;
            Stetho.initializeWithDefaults(this);
        }
    }



}
