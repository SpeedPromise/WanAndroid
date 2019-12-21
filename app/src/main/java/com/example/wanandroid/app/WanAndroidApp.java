package com.example.wanandroid.app;

import android.app.Application;
import android.content.Context;

import com.example.wanandroid.core.DataManager;

public class WanAndroidApp extends Application {

    private static Context context;
    public DataManager mDataManager;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
