package com.samsung.android.settings.applications.cachedb;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AppListCacheIntentService extends IntentService {
    public AppListCacheIntentService() {
        super("AppListCacheIntentService");
    }

    @Override // android.app.IntentService
    public final void onHandleIntent(Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            Log.d("AppListCacheIntentService", "AppListCacheIntentService() action = " + action);
            if ("com.samsung.android.settings.applications.cachedb.action.INITIALIZE"
                    .equals(action)) {
                AppListCacheIndexingManager appListCacheIndexingManager =
                        AppListCacheIndexingManager.getInstance(getBaseContext());
                appListCacheIndexingManager.mWorkerExecutor.submit(
                        new AppListCacheIndexingManager.AnonymousClass1(
                                appListCacheIndexingManager, 1));
                AppListCacheIndexingManager.getInstance(getBaseContext()).startAsyncIndexing();
            } else {
                if (action == null) {
                    action = "null";
                }
                Log.d("AppListCacheIntentService", "Not support action = ".concat(action));
            }
        }
    }
}
