package com.example.honeybee.utilService;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesClass {
    private static final String TMI_PREFERENCE = "tmi_service";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPreferencesClass(Context context) {
        sharedPreferences = context.getSharedPreferences(TMI_PREFERENCE, Activity.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
    }

    public int getValue_int(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    public void setValue_int(String key, int value) {
        editor.putInt(key, value).commit();
    }

    //string
    public String getValue_string(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void setValue_string(String key, String value) {
        editor.putString(key, value).commit();
    }

    //boolean
    public boolean getValue_boolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public void setValue_boolean(String key, boolean value) {
        editor.putBoolean(key, value).commit();
    }

    public void clear() {
        editor.clear().commit();
    }
}
