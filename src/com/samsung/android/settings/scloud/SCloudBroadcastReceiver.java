package com.samsung.android.settings.scloud;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;

import java.util.Collections;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SCloudBroadcastReceiver extends BroadcastReceiver {
    public SCloudWifiDataManager mDataManager;

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if ("com.samsung.account.SAMSUNGACCOUNT_SIGNOUT_COMPLETED".equals(action)) {
            DialogFragment$$ExternalSyntheticOutline0.m(
                    "onReceive :: action : ", action, "WifiSettings.SCloudBroadcastReceiver");
            if (this.mDataManager == null) {
                this.mDataManager =
                        SCloudWifiDataManager.getInstance(context.getApplicationContext());
            }
            SCloudWifiDataManager sCloudWifiDataManager = this.mDataManager;
            sCloudWifiDataManager.getClass();
            sCloudWifiDataManager.setLastConfigKeys(
                    "sCloudLastRemovedWifiConfigKeys", Collections.emptySet());
            sCloudWifiDataManager.setLastConfigKeys(
                    "sCloudLastAddOrUpdatedWifiConfigKeys", Collections.emptySet());
            sCloudWifiDataManager.mUpdatedProfiles.clear();
            sCloudWifiDataManager.mSavedConfigs = null;
            SharedPreferences.Editor edit = this.mDataManager.mSharedPreference.edit();
            edit.putLong("sCloudLastSyncTimeStamp", 0L);
            edit.apply();
        }
    }
}
