package com.samsung.android.settingslib.applications.cachedb;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.HashMap;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class AppListCacheManager {
    public static HashMap getAppLabelCache(FragmentActivity fragmentActivity) {
        HashMap hashMap = new HashMap();
        if (!isValidCache(fragmentActivity)) {
            Log.d("AppListCacheManager", "getAppLabelCache : invalid cache!!");
            return hashMap;
        }
        try {
            Cursor query =
                    fragmentActivity
                            .getContentResolver()
                            .query(
                                    AppListCacheProviderContract.URI_APP_LIST,
                                    null,
                                    null,
                                    null,
                                    null);
            if (query != null) {
                while (query.moveToNext()) {
                    try {
                        hashMap.put(
                                query.getString(query.getColumnIndexOrThrow("package_name")),
                                query.getString(query.getColumnIndexOrThrow("app_title")));
                    } finally {
                    }
                }
            }
            if (query != null) {
                query.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("AppListCacheManager", "getAppLabelCache : " + hashMap.size());
        return hashMap;
    }

    public static boolean isValidCache(Context context) {
        String locale = Locale.getDefault().toString();
        String string =
                context.getSharedPreferences("AppListPreference", 0)
                        .getString("CachedLocale", ApnSettings.MVNO_NONE);
        Log.d("AppListCacheManager", "isValidCache() cached : " + string + " / current " + locale);
        return string.equals(locale);
    }
}
