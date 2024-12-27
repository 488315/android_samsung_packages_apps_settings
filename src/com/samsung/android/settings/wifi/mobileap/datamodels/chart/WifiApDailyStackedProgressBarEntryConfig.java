package com.samsung.android.settings.wifi.mobileap.datamodels.chart;

import android.util.Log;

import com.samsung.android.settings.wifi.mobileap.datamodels.WifiApDataUsageConfig;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiApDailyStackedProgressBarEntryConfig {
    public final Calendar mCalendar;
    public final Date mDate;
    public final long mDateInMillis;
    public final List mProgressBarEntryConfigList;
    public WifiApDataUsageConfig mTotalUsed;

    public WifiApDailyStackedProgressBarEntryConfig() {
        this(null, System.currentTimeMillis());
    }

    public final WifiApDataUsageConfig getTotalUsage() {
        long j = 0;
        while (this.mProgressBarEntryConfigList.iterator().hasNext()) {
            j =
                    (long)
                            (j
                                    + ((WifiApProgressBarEntryConfig) r0.next())
                                            .mDataUsageConfig
                                            .mUsageValueInBytes);
        }
        WifiApDataUsageConfig wifiApDataUsageConfig = new WifiApDataUsageConfig(j);
        Log.i(
                "WifiApDailyStackedProgressBarEntryConfig",
                "Total Usage(MB): " + wifiApDataUsageConfig.getUsageValueInMB() + toString());
        return wifiApDataUsageConfig;
    }

    public final String toString() {
        StringBuffer stringBuffer =
                new StringBuffer(
                        "DailyStackedProgressBarEntry Date: "
                                + this.mDateInMillis
                                + "("
                                + this.mDate
                                + ") == >");
        Iterator it = this.mProgressBarEntryConfigList.iterator();
        while (it.hasNext()) {
            stringBuffer.append(((WifiApProgressBarEntryConfig) it.next()).toString());
        }
        return stringBuffer.toString();
    }

    public WifiApDailyStackedProgressBarEntryConfig(List list, long j) {
        this.mDateInMillis = 0L;
        this.mTotalUsed = new WifiApDataUsageConfig(0L);
        this.mDateInMillis = j;
        Date date = new Date(j);
        this.mDate = date;
        Calendar calendar = Calendar.getInstance();
        this.mCalendar = calendar;
        calendar.setTime(date);
        if (list != null) {
            this.mProgressBarEntryConfigList = list;
        } else {
            Log.i("WifiApDailyStackedProgressBarEntryConfig", "Adding empty progress List");
            this.mProgressBarEntryConfigList = new ArrayList();
        }
        Log.i(
                "WifiApDailyStackedProgressBarEntryConfig",
                "Created Entry for Calendar: "
                        + calendar.getTime()
                        + ", list Count: "
                        + this.mProgressBarEntryConfigList.size());
    }
}
