package org.milkyway.settingsinitializer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.provider.Settings;
import android.util.Log;

public class BootCompletedReceiver extends BroadcastReceiver {
    private final static String TAG = "BroadcastReceiver"
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            initializeSettings(context);
        }
    }

    private void initializeSettings(Context context) {
        Log.d(TAG, "Initializing system settings");
        Resources res = context.getResources();
        String[] keys = res.getStringArray(R.array.global_string_settings_keys);
        String[] values = res.getStringArray(R.array.global_string_settings_values);

        if (keys.length != values.length) {
            Log.e(TAG, "Different number of keys and values")
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
    }
}
