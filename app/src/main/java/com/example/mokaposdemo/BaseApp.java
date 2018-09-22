package com.example.mokaposdemo;

import android.content.Context;

import com.orm.SugarApp;

public class BaseApp extends SugarApp {

    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();
    }
}
