package com.deliverystuff.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class SharedPrefsHelper {

    public static final String TAG = SharedPrefsHelper.class.getSimpleName();
    private static final String PREFS_NAME = "app_common";
    private static final String BASE_KEY_NAME = SharedPrefsHelper.class.getPackage().getName() + ".";
    private static final String KEY_JSON_DATA = BASE_KEY_NAME + "JSON_DATA";
    private final SharedPreferences prefs;
    private Context context;

    public SharedPrefsHelper(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public String getData() {
        return prefs.getString(KEY_JSON_DATA, "");
    }

    public void setData(String response) {
        if (response != null) {
            prefs.edit().putString(KEY_JSON_DATA, response).apply();
            Log.i(TAG, "Updated Data");
        }
    }

    @Provides
    @Singleton
    Context providesApplicationContext() {
        return context;
    }
}
