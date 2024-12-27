package com.samsung.android.settings.wifi.develop.weeklyreport;

import android.content.Context;

import com.android.settings.R;

import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WeeklyDataUsageYAxisValueFormatter implements IAxisValueFormatter {
    public Context mContext;
    public String mDataUsageUnit;

    @Override // com.github.mikephil.charting.formatter.IAxisValueFormatter
    public final String getFormattedValue(float f) {
        float round = Math.round(f);
        String format =
                round >= 1000.0f
                        ? String.format("%.1f", Float.valueOf(round / 1000.0f))
                        : String.format("%d", Integer.valueOf(Math.round(round)));
        return f == 0.0f
                ? format
                : round >= 1000.0f
                        ? String.format(
                                this.mContext.getString(
                                        R.string.sec_wifi_weekly_report_value_in_gb),
                                format)
                        : "MB".equals(this.mDataUsageUnit)
                                ? String.format(
                                        this.mContext.getString(
                                                R.string.sec_wifi_weekly_report_value_in_mb),
                                        format)
                                : "h".equals(this.mDataUsageUnit)
                                        ? String.format(
                                                this.mContext.getString(
                                                        R.string
                                                                .sec_wifi_weekly_report_value_in_hour),
                                                format)
                                        : String.format(
                                                this.mContext.getString(
                                                        R.string
                                                                .sec_wifi_weekly_report_value_in_mb),
                                                format);
    }

    public final void setDataUsageUnit(String str) {
        if (str.contentEquals("MB")
                || str.contentEquals("GB")
                || str.contentEquals("h")
                || str.contentEquals("m")) {
            this.mDataUsageUnit = str;
        } else {
            this.mDataUsageUnit = "MB";
        }
    }
}
