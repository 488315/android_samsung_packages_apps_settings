package com.samsung.android.settings.wifi.mobileap.datamodels.chart;

import android.util.Log;

import com.samsung.android.settings.wifi.mobileap.datamodels.WifiApDataUsageConfig;

import java.util.Calendar;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiApWeeklyStackedProgressBarEntryConfig {
    public List mDailyStackedProgressBarEntryConfigList;
    public Calendar mWeekOfYearCalendar;

    public final void addDailyStackedProgressBarEntryConfigList(
            WifiApDailyStackedProgressBarEntryConfig wifiApDailyStackedProgressBarEntryConfig) {
        Log.i(
                "WifiApWeeklyStackedProgressBarEntryConfig",
                "Adding daily stack progress"
                        + weekOfYearToString()
                        + "list: "
                        + wifiApDailyStackedProgressBarEntryConfig.toString());
        this.mDailyStackedProgressBarEntryConfigList.add(wifiApDailyStackedProgressBarEntryConfig);
    }

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

    public final WifiApDataUsageConfig getTotalUsage() {
        long j = 0;
        while (this.mDailyStackedProgressBarEntryConfigList.iterator().hasNext()) {
            j =
                    (long)
                            (j
                                    + ((WifiApDailyStackedProgressBarEntryConfig) r0.next())
                                            .mTotalUsed
                                            .mUsageValueInBytes);
        }
        WifiApDataUsageConfig wifiApDataUsageConfig = new WifiApDataUsageConfig(j);
        Log.i(
                "WifiApWeeklyStackedProgressBarEntryConfig",
                "Total Usage(MB): "
                        + wifiApDataUsageConfig.getUsageValueInMB()
                        + weekOfYearToString());
        return wifiApDataUsageConfig;
    }

    public final String weekOfYearToString() {
        return "CalendarWeekOfYear: "
                + this.mWeekOfYearCalendar.get(3)
                + "("
                + this.mWeekOfYearCalendar.getTime()
                + ") ";
    }
}
