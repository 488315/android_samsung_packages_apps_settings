package com.samsung.android.wifitrackerlib;

import android.content.Context;
import android.os.SystemProperties;
import android.provider.Settings;
import android.telephony.SubscriptionManager;

import androidx.preference.Preference$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class SemWifiEntryFilter {
    public static final int[] SETTING_DEVELOPER_RSSI = {-73, -78, -127};
    public static final int[] SETTING_DEVELOPER_RSSI_5G = {-70, -75, -127};
    public final boolean CSC_WIFI_SUPPORT_VZW_EAP_AKA = "VZW".equals(SemWifiUtils.readSalesCode());
    public final boolean DISPLAY_SSID_STATUS_BAR_INFO =
            "SWC".equals(SystemProperties.get("ro.csc.sales_code"));
    public final Context mContext;
    public final boolean mIsDeveloperOptionOn;
    public int mWeakSignalRssi;
    public int mWeakSignalRssi5Ghz;

    public SemWifiEntryFilter(Context context) {
        this.mContext = context;
        this.mIsDeveloperOptionOn = SemWifiEntryFlags.isWifiDeveloperOptionOn(context);
        updateRssiFilter();
    }

    public final void updateRssiFilter() {
        if (this.mIsDeveloperOptionOn) {
            Context context = this.mContext;
            SubscriptionManager subscriptionManager = SemWifiUtils.mSubscriptionManager;
            int i =
                    Settings.Global.getInt(
                            context.getContentResolver(), "sec_wifi_developer_rssi_level", 1);
            this.mWeakSignalRssi = SETTING_DEVELOPER_RSSI[i];
            this.mWeakSignalRssi5Ghz = SETTING_DEVELOPER_RSSI_5G[i];
        } else {
            this.mWeakSignalRssi = -78;
            this.mWeakSignalRssi5Ghz = -75;
        }
        StringBuilder sb = new StringBuilder("mWeakSignalRssi: ");
        sb.append(this.mWeakSignalRssi);
        sb.append(", mWeakSignalRssi5Ghz: ");
        Preference$$ExternalSyntheticOutline0.m(sb, this.mWeakSignalRssi5Ghz, "SemWifiEntryFilter");
    }
}
