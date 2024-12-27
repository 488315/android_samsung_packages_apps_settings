package com.samsung.android.settings.uwb.labs.view.statistics;

import android.content.Context;
import android.widget.TextView;

import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.uwb.labs.common.UwbLabsUtils;
import com.samsung.android.settings.uwb.labs.view.statistics.Chart.WeeklyTimeUsageBarChart;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WeeklyTimeReport extends WeeklyReport {
    public final long[] mDailyConnData;
    public final long[] mDailyRangingCnt;
    public final WeeklyTimeUsageBarChart mTimeBarChart;
    public final AnonymousClass1 timeBarSelectedListener;

    public WeeklyTimeReport(Context context, PreferenceViewHolder preferenceViewHolder) {
        super(context, preferenceViewHolder);
        WeeklyTimeUsageBarChart.ChartValueSelectedListener chartValueSelectedListener =
                new WeeklyTimeUsageBarChart
                        .ChartValueSelectedListener() { // from class:
                                                        // com.samsung.android.settings.uwb.labs.view.statistics.WeeklyTimeReport.1
                    @Override // com.samsung.android.settings.uwb.labs.view.statistics.Chart.WeeklyTimeUsageBarChart.ChartValueSelectedListener
                    public final void onValueSelected(int i) {
                        WeeklyReportData.mSelectedDay = i;
                        WeeklyTimeReport.this.updateInternal();
                    }
                };
        this.mDailyConnData = new long[] {0, 0, 0, 0, 0, 0, 0};
        this.mDailyRangingCnt = new long[] {0, 0, 0, 0, 0, 0, 0};
        WeeklyTimeUsageBarChart weeklyTimeUsageBarChart =
                (WeeklyTimeUsageBarChart) preferenceViewHolder.findViewById(R.id.time_barchart);
        this.mTimeBarChart = weeklyTimeUsageBarChart;
        weeklyTimeUsageBarChart.initialize();
        weeklyTimeUsageBarChart.callbackListener = chartValueSelectedListener;
    }

    public final void updateInternal() {
        String str;
        int i = 6 - WeeklyReportData.mSelectedDay;
        this.mGraphDate.setText((CharSequence) ((ArrayList) this.mWeeklyDate).get(i));
        this.mGraphText2.setText("Connected");
        this.mGraphText3.setText(R.string.uwb_ranging_count);
        TextView textView = this.mGraphValue2;
        long j = this.mDailyConnData[i];
        long hours = UwbLabsUtils.getHours(j);
        long minutes = UwbLabsUtils.getMinutes(j);
        if (hours == 0) {
            str = ApnSettings.MVNO_NONE + minutes + "m";
        } else {
            String str2 = ApnSettings.MVNO_NONE + hours + "h ";
            if (minutes != 0) {
                str = str2 + minutes + "m";
            } else {
                str = str2;
            }
        }
        textView.setText(str);
        this.mGraphValue3.setText(Long.toString(this.mDailyRangingCnt[i]));
        this.mGraphText1.setVisibility(8);
        this.mGraphValue1.setVisibility(8);
        this.mGraphDataImage1.setVisibility(8);
    }
}
