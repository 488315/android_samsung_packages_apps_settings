package com.samsung.android.settings.wifi.mobileap.datamodels.chart;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.wifi.mobileap.datamodels.WifiApDataUsageConfig;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;

import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiApProgressBarEntryConfig {
    public final WifiApDataUsageConfig mDataUsageConfig;
    public final String mProgressEntryName;

    public WifiApProgressBarEntryConfig(long j, String str) {
        WifiApSettingsUtils.convertToColorRGB("#FF0000");
        this.mProgressEntryName = ApnSettings.MVNO_NONE;
        this.mDataUsageConfig = new WifiApDataUsageConfig(0L);
        this.mProgressEntryName = str;
        this.mDataUsageConfig = new WifiApDataUsageConfig(j);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || WifiApProgressBarEntryConfig.class != obj.getClass()) {
            return false;
        }
        WifiApProgressBarEntryConfig wifiApProgressBarEntryConfig =
                (WifiApProgressBarEntryConfig) obj;
        return Double.valueOf((double) this.mDataUsageConfig.mUsageValueInBytes)
                        .equals(
                                Double.valueOf(
                                        (double)
                                                wifiApProgressBarEntryConfig
                                                        .mDataUsageConfig
                                                        .mUsageValueInBytes))
                && Objects.equals(
                        this.mProgressEntryName, wifiApProgressBarEntryConfig.mProgressEntryName);
    }

    public final String toString() {
        return " ** ProgressBarEntry ==> ProgressEntryName: "
                + this.mProgressEntryName
                + ", ProgressEntryValue(MB): "
                + this.mDataUsageConfig.getUsageValueInMB();
    }
}
