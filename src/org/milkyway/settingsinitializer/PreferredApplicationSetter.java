package org.milkyway.settingsinitializer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.util.Log;

import java.util.List;

import org.milkyway.settingsinitializer.R;

class PreferredApplicationSetter{
    private final static String TAG = "SettingsInitializer.PreferredApplicationSetter"
    /**
     * Initializes preffered applications from config
     * @param context context used for getting resources
     */
    public static void initializeSettings(Context context) {
        Resources resources = context.getResources();
        String packageName = resources.getString(R.string.default_launcher_package);
        String className = resources.getString(R.string.default_launcher_class);
        setDefaultLauncher(context, packageName, className);
        Log.i(TAG, "Setted default launcher " + packageName + "/" + className);
    }

    /**
     * Sets default launcher
     * @param context application-level context
     * @param packageName package name to set
     * @param className name of class to set
     */
    private static void setDefaultLauncher(Context context, String packageName, String className) {
        PackageManager packageManager = context.getPackageManager();

        IntentFilter filter = new IntentFilter(Intent.ACTION_MAIN);
        filter.addCategory(Intent.CATEGORY_HOME);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        ComponentName defaultLauncher = new ComponentName(packageName, className);
        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(
                new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME),
                PackageManager.MATCH_DEFAULT_ONLY);
        ComponentName[] componentNames = new ComponentName[resolveInfos.size()];
        for (int i = 0; i < resolveInfos.size(); i++) {
            ResolveInfo resolveInfo = resolveInfos.get(i);
            componentNames[i] = new ComponentName(resolveInfo.activityInfo.packageName,
                    resolveInfo.activityInfo.name);
        }
        packageManager.addPreferredActivity(filter, IntentFilter.MATCH_CATEGORY_EMPTY,
                componentNames, defaultLauncher);
    }
}