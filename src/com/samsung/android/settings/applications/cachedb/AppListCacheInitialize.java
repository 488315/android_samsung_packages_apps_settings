package com.samsung.android.settings.applications.cachedb;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.settings.applications.manageapplications.CloneBackend$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AppListCacheInitialize extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        try {
            String action = intent.getAction();
            if (action == null) {
                action = "null";
            }
            Log.i("AppListCacheInitialize", "onReceive ".concat(action));
            Intent intent2 =
                    new Intent(
                            "com.samsung.android.settings.applications.cachedb.action.INITIALIZE");
            intent2.setComponent(
                    new ComponentName(
                            context.getPackageName(),
                            "com.samsung.android.settings.applications.cachedb.AppListCacheIntentService"));
            context.startService(intent2);
        } catch (Exception e) {
            CloneBackend$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("cannot start a service : "), "AppListCacheInitialize");
        }
    }
}
