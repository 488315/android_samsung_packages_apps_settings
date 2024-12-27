package com.samsung.android.settings.search;

import android.content.BroadcastReceiver;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;

import com.android.settings.Utils;
import com.android.settings.applications.manageapplications.CloneBackend$$ExternalSyntheticOutline0;
import com.android.settingslib.utils.ThreadUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SearchInitialize extends BroadcastReceiver {
    public static final /* synthetic */ int $r8$clinit = 0;

    @Override // android.content.BroadcastReceiver
    public final void onReceive(final Context context, Intent intent) {
        StringBuilder sb = new StringBuilder("onReceive() ");
        sb.append(intent.getAction() == null ? "null" : intent.getAction());
        Log.i("Settings_SearchInitialize", sb.toString());
        if ("com.sec.android.app.setupwizard.SETUPWIZARD_COMPLETE".equals(intent.getAction())
                || "com.sec.android.app.secsetupwizard.SETUPWIZARD_COMPLETE"
                        .equals(intent.getAction())
                || ("com.samsung.intent.action.LAZY_BOOT_COMPLETE".equals(intent.getAction())
                        && Utils.isDeviceProvisioned(context))) {
            final BroadcastReceiver.PendingResult goAsync = goAsync();
            ThreadUtils.postOnBackgroundThread(
                    new Runnable() { // from class:
                        // com.samsung.android.settings.search.SearchInitialize$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            SearchInitialize searchInitialize = SearchInitialize.this;
                            Context context2 = context;
                            BroadcastReceiver.PendingResult pendingResult = goAsync;
                            int i = SearchInitialize.$r8$clinit;
                            searchInitialize.getClass();
                            String str = Build.FINGERPRINT;
                            SharedPreferences sharedPreferences =
                                    context2.getSharedPreferences("search_indexing_pref", 0);
                            boolean z = sharedPreferences.getBoolean(str, false);
                            AbsAdapter$$ExternalSyntheticOutline0.m(
                                    "startIndexingIfNeeded. isFingerprintExist ",
                                    "Settings_SearchInitialize",
                                    z);
                            if (z) {
                                pendingResult.finish();
                                return;
                            }
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("indexingJobService", true);
                            try {
                                ContentProviderClient acquireUnstableContentProviderClient =
                                        context2.getContentResolver()
                                                .acquireUnstableContentProviderClient(
                                                        new Uri.Builder()
                                                                .scheme("content")
                                                                .authority(
                                                                        "com.samsung.android.settings.intelligence.search.provider.SettingSearchProvider")
                                                                .build());
                                if (acquireUnstableContentProviderClient != null) {
                                    try {
                                        acquireUnstableContentProviderClient.call(
                                                "com.samsung.android.settings.intelligence.search.provider.SettingSearchProvider",
                                                "startIndexing",
                                                null,
                                                bundle);
                                    } finally {
                                    }
                                }
                                sharedPreferences.edit().putBoolean(str, true).apply();
                                if (acquireUnstableContentProviderClient != null) {
                                    acquireUnstableContentProviderClient.close();
                                }
                            } catch (Exception e) {
                                CloneBackend$$ExternalSyntheticOutline0.m(
                                        e,
                                        new StringBuilder("startIndexingIfNeeded. "),
                                        "Settings_SearchInitialize");
                            }
                            pendingResult.finish();
                        }
                    });
        }
    }
}
