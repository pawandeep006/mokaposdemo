package com.example.mokaposdemo.utils;

import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.mokaposdemo.BaseApp;
import com.example.mokaposdemo.BuildConfig;
import com.google.gson.Gson;

public class Utils {

    public static void toast(String title) {
        toast(title, false);
    }

    public static void toast(String message, boolean isLong) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            return;
        }
        log("from toast-> " + message);

        if (isLong) {
            Toast.makeText(BaseApp.getContext(), message, Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(BaseApp.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public static synchronized void log(String value) {
        if (BuildConfig.isPrintLogs) {
            Log.e(new Throwable().getStackTrace()[1].getClassName(), "log: " + value);
        }
    }

    public static Gson getGson() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final Gson INSTANCE = new Gson();
    }
}
