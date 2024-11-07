package io.f104a.settingsinitializer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.provider.Settings;
import android.util.Log;

import io.f104a.PreferredApplicationSetter;
import io.f104a.settingsinitializer.R;

public class BootCompletedReceiver extends BroadcastReceiver {
    private final static String TAG = "SettingsInitializer.BroadcastReceiver";
    private final static String PROVISONED_KEY = "floraos_device_provisoned";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            initializeSettings(context);
            PreferredApplicationSetter.initializeSettings(context);
            Log.i(TAG, "Finished handling broadcast");
        }
    }

    private static void initializeGlobalSettings(Context context, Resources res) {
        Log.d(TAG, "Initializing global settings");
        String[] keys = res.getStringArray(R.array.global_string_settings_keys);
        String[] values = res.getStringArray(R.array.global_string_settings_values);

        if (keys.length != values.length) {
            Log.e(TAG, "Different number of keys and values for global string settings");
            return;
        }

        for (int i = 0; i < keys.length; i++) {
            try {
                Settings.Global.putString(context.getContentResolver(), keys[i], values[i]);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }
    }


    private static void initalizeSecureIntSettings(Context context, Resources res){
        Log.d(TAG, "Initializing secure integer settings");
        String[] keys = res.getStringArray(R.array.secure_int_settings_keys);
        int[] values = res.getIntArray(R.array.secure_int_settings_values);

        if (keys.length != values.length) {
            Log.e(TAG, "Different number of keys and values for secure int settings");
            return;
        }

        for (int i = 0; i < keys.length; i++) {
            try {
                Settings.Secure.putInt(context.getContentResolver(), keys[i], values[i]);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }
    }

    private static void initializeSecureSettings(Context context, Resources res) {
        Log.d(TAG, "Initializing secure settings");
        String[] keys = res.getStringArray(R.array.secure_string_settings_keys);
        String[] values = res.getStringArray(R.array.secure_string_settings_values);

        if (keys.length != values.length) {
            Log.e(TAG, "Different number of keys and values for secure string settings");
            return;
        }

        for (int i = 0; i < keys.length; i++) {
            try {
                Settings.Secure.putString(context.getContentResolver(), keys[i], values[i]);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }
        initalizeSecureIntSettings(context, res);
    }

    private void setProvisioned(Context context){
        Settings.Secure.putInt(context.getContentResolver(), PROVISONED_KEY, 1);
    }

    private boolean needInitialization(Context context){
        return Settings.Secure.getInt(context.getContentResolver(), PROVISONED_KEY, 0) == 0;
    }

    private void initializeSettings(Context context) {
        Resources res = context.getResources();
        if(!needInitialization(context){
            Log.i(TAG, "Do not need settings initialization");
            return;
        }
        initializeGlobalSettings(context, res);
        initializeSecureSettings(context, res);
        //TODO: set flag that device is provisoned
        Log.d(TAG, "Finished settings initialization");
    }

}
