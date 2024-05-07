package org.milkyway.settingsinitializer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.provider.Settings;
import android.util.Log;

public class BootCompletedReceiver extends BroadcastReceiver {
    private final static String TAG = "BroadcastReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            initializeSettings(context);
        }
    }

    private static void initializeGlobalSettings(Resources res) {
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


    private static void initalizeSecureIntSettings(Resource res){
        Lod.d("Initializing secure integer settings");
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

    private static void initializeSecureSettings(Resources res) {
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
        initalizeSecureIntSettings(res);
    }

    private void initializeSettings(Context context) {
        Resources res = context.getResources();
        initializeGlobalSettings(res);
        initializeSecureSettings(res);
        //TODO: set flag that device is provisoned
        Log.d(TAG, "Finished settings initialization");
    }

}
