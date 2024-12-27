package com.samsung.android.settings.wifi.mobileap.datamodels.chart;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiApMonthlyStackedProgressBarEntryConfig {
    public List mDailyStackedProgressBarEntryConfigList;

    public final WifiApDailyStackedProgressBarEntryConfig getTopEntryConfig() {
        WifiApDailyStackedProgressBarEntryConfig wifiApDailyStackedProgressBarEntryConfig =
                new WifiApDailyStackedProgressBarEntryConfig();
        double d = 0.0d;
        for (WifiApDailyStackedProgressBarEntryConfig wifiApDailyStackedProgressBarEntryConfig2 :
                this.mDailyStackedProgressBarEntryConfigList) {
            double d2 =
                    wifiApDailyStackedProgressBarEntryConfig2.getTotalUsage().mUsageValueInBytes;
            if (d <= d2) {
                wifiApDailyStackedProgressBarEntryConfig =
                        wifiApDailyStackedProgressBarEntryConfig2;
                d = d2;
            }
        }
        return wifiApDailyStackedProgressBarEntryConfig;
    }
}
