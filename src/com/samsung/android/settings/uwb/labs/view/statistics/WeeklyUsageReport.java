package com.samsung.android.settings.uwb.labs.view.statistics;

import android.content.Context;

import androidx.compose.runtime.ComposerKt$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.uwb.labs.view.statistics.Chart.WeeklyTimeUsageBarChart;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WeeklyUsageReport extends WeeklyReport {
    public final Context mContext;
    public final long[][] mDailyPackageUsageCnt;
    public final long[][] mDailyUsageTime;
    public final WeeklyTimeUsageBarChart mUsageBarChart;
    public final AnonymousClass1 usageBarSelectedListener;

    public WeeklyUsageReport(Context context, PreferenceViewHolder preferenceViewHolder) {
        super(context, preferenceViewHolder);
        WeeklyTimeUsageBarChart.ChartValueSelectedListener chartValueSelectedListener =
                new WeeklyTimeUsageBarChart
                        .ChartValueSelectedListener() { // from class:
                                                        // com.samsung.android.settings.uwb.labs.view.statistics.WeeklyUsageReport.1
                    @Override // com.samsung.android.settings.uwb.labs.view.statistics.Chart.WeeklyTimeUsageBarChart.ChartValueSelectedListener
                    public final void onValueSelected(int i) {
                        WeeklyReportData.mSelectedDay = i;
                        WeeklyUsageReport.this.updateInternal$1();
                    }
                };
        this.mContext = context;
        this.mDailyUsageTime =
                new long[][] {
                    new long[] {0, 0, 0},
                    new long[] {0, 0, 0},
                    new long[] {0, 0, 0},
                    new long[] {0, 0, 0},
                    new long[] {0, 0, 0},
                    new long[] {0, 0, 0},
                    new long[] {0, 0, 0}
                };
        this.mDailyPackageUsageCnt =
                new long[][] {
                    new long[] {0, 0, 0},
                    new long[] {0, 0, 0},
                    new long[] {0, 0, 0},
                    new long[] {0, 0, 0},
                    new long[] {0, 0, 0},
                    new long[] {0, 0, 0},
                    new long[] {0, 0, 0}
                };
        WeeklyTimeUsageBarChart weeklyTimeUsageBarChart =
                (WeeklyTimeUsageBarChart) this.mHolder.findViewById(R.id.usage_barchart);
        this.mUsageBarChart = weeklyTimeUsageBarChart;
        weeklyTimeUsageBarChart.initialize();
        weeklyTimeUsageBarChart.callbackListener = chartValueSelectedListener;
    }

    public final String appendUsageCnt(int i, int i2, long j) {
        return j == 0
                ? ApnSettings.MVNO_NONE
                : ComposerKt$$ExternalSyntheticOutline0.m(
                        " (", Long.toString(this.mDailyPackageUsageCnt[i][i2]), ")");
    }

    public final void updateInternal$1() {
        int i = 6 - WeeklyReportData.mSelectedDay;
        long[] jArr = this.mDailyUsageTime[i];
        long j = jArr[0];
        long j2 = jArr[1];
        long j3 = jArr[2];
        this.mGraphText1.setText("uwbtest" + appendUsageCnt(i, 0, j));
        this.mGraphText2.setText("settings" + appendUsageCnt(i, 1, j2));
        this.mGraphText3.setText("etc" + appendUsageCnt(i, 2, j3));
        this.mGraphText3.setVisibility(0);
        this.mGraphValue1.setText(((int) j) + this.mContext.getString(R.string.uwb_distance_unit));
        this.mGraphValue2.setText(((int) j2) + this.mContext.getString(R.string.uwb_distance_unit));
        this.mGraphValue3.setText(((int) j3) + this.mContext.getString(R.string.uwb_distance_unit));
        this.mGraphText1.setVisibility(0);
        this.mGraphText2.setVisibility(0);
        this.mGraphValue1.setVisibility(0);
        this.mGraphValue2.setVisibility(0);
        this.mGraphValue3.setVisibility(0);
        this.mGraphDate.setText((CharSequence) ((ArrayList) this.mWeeklyDate).get(i));
    }
}
