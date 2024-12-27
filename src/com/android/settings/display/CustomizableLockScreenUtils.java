package com.android.settings.display;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class CustomizableLockScreenUtils {
    static final String AFFORDANCE_NAME = "affordance_name";
    static final String ENABLED_FLAG = "is_custom_lock_screen_quick_affordances_feature_enabled";
    static final Uri FLAGS_URI;
    static final String LAUNCH_SOURCE_SETTINGS = "app_launched_settings";
    static final String NAME = "name";
    static final Uri SELECTIONS_URI;
    static final String VALUE = "value";
    static final String WALLPAPER_LAUNCH_SOURCE = "com.android.wallpaper.LAUNCH_SOURCE";

    static {
        Uri build =
                new Uri.Builder()
                        .scheme("content")
                        .authority("com.android.systemui.customization")
                        .build();
        FLAGS_URI = build.buildUpon().path("flags").build();
        SELECTIONS_URI =
                build.buildUpon()
                        .appendPath("lockscreen_quickaffordance")
                        .appendPath("selections")
                        .build();
    }

    public static boolean isFeatureEnabled(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent("android.intent.action.SET_WALLPAPER");
        intent.putExtra(WALLPAPER_LAUNCH_SOURCE, LAUNCH_SOURCE_SETTINGS);
        if (intent.resolveActivity(packageManager) != null) {
            try {
                Cursor query = context.getContentResolver().query(FLAGS_URI, null, null, null);
                try {
                    if (query == null) {
                        Log.w("CustomizableLockScreenUtils", "Cursor was null!");
                        if (query != null) {
                            query.close();
                        }
                        return false;
                    }
                    int columnIndex = query.getColumnIndex("name");
                    int columnIndex2 = query.getColumnIndex("value");
                    if (columnIndex != -1 && columnIndex2 != -1) {
                        while (query.moveToNext()) {
                            String string = query.getString(columnIndex);
                            int i = query.getInt(columnIndex2);
                            if (TextUtils.equals(ENABLED_FLAG, string)) {
                                Log.d(
                                        "CustomizableLockScreenUtils",
                                        "is_custom_lock_screen_quick_affordances_feature_enabled="
                                                + i);
                                boolean z = i == 1;
                                query.close();
                                return z;
                            }
                        }
                        Log.w(
                                "CustomizableLockScreenUtils",
                                "Flag with name"
                                    + " \"is_custom_lock_screen_quick_affordances_feature_enabled\""
                                    + " not found!");
                        query.close();
                        return false;
                    }
                    Log.w("CustomizableLockScreenUtils", "Cursor doesn't contain name or value!");
                    query.close();
                    return false;
                } finally {
                }
            } catch (Exception e) {
                Log.e(
                        "CustomizableLockScreenUtils",
                        "Exception while querying quick affordance content provider",
                        e);
            }
        }
        return false;
    }
}
