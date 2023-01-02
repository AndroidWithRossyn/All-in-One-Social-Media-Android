package com.itechnotion.allinone.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.multidex.MultiDexApplication;


/**
 * Created by Sunil on 23-Oct-16.
 */
public class SharedObjects extends MultiDexApplication {

    public static Context context;
    public PreferencesEditor preferencesEditor = new PreferencesEditor();
    Dialog alertDialog;
    public static String USER_JSON = "User_json";
    private Object id;

    public SharedObjects() {
    }

    public static Context getContext() {
        return context;
    }

    public SharedObjects(Context context) {
        this.context = context;

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public class PreferencesEditor {

        public void setPreference(String key, String value) {
            SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPreference.edit();
            editor.putString(key, value);
            editor.commit();
        }

        public void setBoolean(String key, boolean value) {
            SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPreference.edit();
            editor.putBoolean(key, value);
            editor.commit();
        }



        public String getPreference(String key) {
            try {
                SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
                return sharedPreference.getString(key, "");
            } catch (Exception exception) {
                return "";
            }
        }

        public Boolean getBoolean(String key) {
            try {
                SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
                return sharedPreference.getBoolean(key,true);
            } catch (Exception exception) {
                return false;
            }
        }

        public Boolean getBooleanRadio(String key) {
            try {
                SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
                return sharedPreference.getBoolean(key,false);
            } catch (Exception exception) {
                return false;
            }
        }

    }
}
