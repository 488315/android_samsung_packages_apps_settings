package com.samsung.android.scloud.lib.setting;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SamsungCloudRPCSettingV2 {
    public final SyncSettingProviderClient iSyncSetting;

    public SamsungCloudRPCSettingV2(Context context, String str, String str2) {
        Uri uri = RPCSyncSettingContract$Method.CONTENT_URI;
        SyncSettingProviderClient syncSettingProviderClient = new SyncSettingProviderClient();
        syncSettingProviderClient.applicationType = 1;
        syncSettingProviderClient.context = context;
        syncSettingProviderClient.rpcUri = uri;
        syncSettingProviderClient.authority = str;
        syncSettingProviderClient.settingProvider = str2;
        SyncSettingProviderClient.TAG = "[scsettingv2][2.0.27.0]";
        this.iSyncSetting = syncSettingProviderClient;
    }

    public final boolean getAutoSync() {
        SyncSettingProviderClient syncSettingProviderClient = this.iSyncSetting;
        syncSettingProviderClient.getClass();
        try {
            Log.i(SyncSettingProviderClient.TAG, "getAutoSync");
            Bundle bundle = new Bundle();
            bundle.putString("authority", syncSettingProviderClient.authority);
            bundle.putInt("application_type", syncSettingProviderClient.applicationType);
            bundle.putLong("library_version", 20170L);
            return syncSettingProviderClient
                    .context
                    .getContentResolver()
                    .call(syncSettingProviderClient.rpcUri, "get_auto_sync", (String) null, bundle)
                    .getBoolean("auto_sync", false);
        } catch (Exception e) {
            Log.e(SyncSettingProviderClient.TAG, e.getMessage());
            return false;
        }
    }

    public final void setAutoSync(boolean z) {
        SyncSettingProviderClient syncSettingProviderClient = this.iSyncSetting;
        if (syncSettingProviderClient != null) {
            try {
                Log.i(SyncSettingProviderClient.TAG, "setAutoSync");
                Bundle bundle = new Bundle();
                bundle.putString("authority", syncSettingProviderClient.authority);
                bundle.putBoolean("auto_sync", z);
                bundle.putLong("library_version", 20170L);
                syncSettingProviderClient
                        .context
                        .getContentResolver()
                        .call(
                                syncSettingProviderClient.rpcUri,
                                "set_auto_sync",
                                (String) null,
                                bundle);
            } catch (Exception e) {
                Log.e(SyncSettingProviderClient.TAG, e.getMessage());
            }
        }
    }

    public final void showSetting(FragmentActivity fragmentActivity) {
        SyncSettingProviderClient syncSettingProviderClient = this.iSyncSetting;
        if (syncSettingProviderClient != null) {
            try {
                Log.i(SyncSettingProviderClient.TAG, "showSetting - flag:536870912");
                SyncSettingProviderClient.SyncSettingIntent syncSettingIntent =
                        new SyncSettingProviderClient.SyncSettingIntent();
                syncSettingIntent.flags = 536870912;
                fragmentActivity.startActivityForResult(
                        syncSettingIntent.apply(syncSettingProviderClient.authority), 0);
            } catch (Exception e) {
                Log.e(SyncSettingProviderClient.TAG, e.getMessage());
            }
        }
    }
}
