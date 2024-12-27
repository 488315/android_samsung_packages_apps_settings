package com.samsung.android.settings.applications.cachedb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AppListCacheReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        String action = intent == null ? "null" : intent.getAction();
        Log.i(
                "AppListCacheReceiver",
                "onReceive START action : ".concat(action != null ? action : "null"));
        if (TextUtils.isEmpty(action)) {
            return;
        }
        action.getClass();
        if (action.equals("android.intent.action.LOCALE_CHANGED")) {
            AppListCacheIndexingManager appListCacheIndexingManager =
                    AppListCacheIndexingManager.getInstance(context);
            appListCacheIndexingManager.mWorkerExecutor.submit(
                    new AppListCacheIndexingManager.AnonymousClass1(
                            appListCacheIndexingManager, 1));
            AppListCacheIndexingManager.getInstance(context).startAsyncIndexing();
        } else if (action.equals("com.samsung.android.knox.intent.action.PACKAGE_NAME_CHANGE")) {
            String stringExtra =
                    intent.getStringExtra(
                            "com.samsung.android.knox.intent.extra.REAL_PACAKGE_NAME");
            if (TextUtils.isEmpty(stringExtra)) {
                Log.e("AppListCacheReceiver", "onReceive package name of knox intent is empty");
            } else {
                AppListCacheIndexingManager appListCacheIndexingManager2 =
                        AppListCacheIndexingManager.getInstance(context);
                appListCacheIndexingManager2.mWorkerExecutor.submit(
                        new AppListCacheIndexingManager.AnonymousClass2(
                                appListCacheIndexingManager2, stringExtra));
            }
        }
        Log.i("AppListCacheReceiver", "onReceive END");
    }
}
