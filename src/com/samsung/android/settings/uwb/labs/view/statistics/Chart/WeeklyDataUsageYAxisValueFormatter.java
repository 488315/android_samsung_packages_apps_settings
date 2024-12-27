package com.samsung.android.settings.uwb.labs.view.statistics.Chart;

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
        String format = String.format("%d", Integer.valueOf(Math.round(round)));
        return f == 0.0f
                ? format
                : (round >= 24.0f || "m".equals(this.mDataUsageUnit))
                        ? format.concat("m")
                        : "h".equals(this.mDataUsageUnit)
                                ? String.format(
                                        this.mContext.getString(
                                                R.string.sec_wifi_weekly_report_value_in_hour),
                                        format)
                                : String.format(
                                        this.mContext.getString(
                                                R.string.sec_wifi_weekly_report_value_in_hour),
                                        format);
    }
}
