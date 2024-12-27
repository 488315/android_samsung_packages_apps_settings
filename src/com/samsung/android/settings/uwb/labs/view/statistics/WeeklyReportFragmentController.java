package com.samsung.android.settings.uwb.labs.view.statistics;

import android.content.Context;
import android.util.Log;

import com.android.settings.R;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import com.samsung.android.settings.uwb.labs.common.UwbLabsUtils;
import com.samsung.android.settings.uwb.labs.control.statistics.WeeklyReportController;
import com.samsung.android.settings.uwb.labs.view.statistics.Chart.WeeklyTimeUsageBarChart;
import com.samsung.android.util.SemLog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WeeklyReportFragmentController {
    public final Context mContext;
    public final WeeklyReportController mStatisticsControl;
    public WeeklyTimeReport mWeeklyTime;
    public WeeklyUsageReport mWeeklyUsage;
    public int mTabIndexSelected = 0;
    public final Calendar mCurrentDate = Calendar.getInstance();

    public WeeklyReportFragmentController(Context context) {
        Log.i("WeeklyReportFragmentController", "CREATE: WeeklyReportFragmentController");
        this.mContext = context;
        this.mStatisticsControl = new WeeklyReportController(context);
    }

    public final void draw() {
        int i = 7;
        int i2 = 0;
        WeeklyTimeReport weeklyTimeReport = this.mWeeklyTime;
        if (weeklyTimeReport == null) {
            return;
        }
        int i3 = this.mTabIndexSelected;
        WeeklyReportController weeklyReportController = this.mStatisticsControl;
        if (i3 == 0) {
            Arrays.fill(weeklyTimeReport.mDailyConnData, 0L);
            Arrays.fill(weeklyTimeReport.mDailyRangingCnt, 0L);
            long[] jArr = {0, 0, 0, 0, 0, 0, 0};
            System.arraycopy(weeklyReportController.getData(6), 0, jArr, 0, 7);
            int i4 = this.mCurrentDate.get(7) % 7;
            for (int i5 = 0; i5 < 7; i5++) {
                int convertWeekDay = UwbLabsUtils.convertWeekDay(i4);
                this.mWeeklyTime.mDailyConnData[i5] =
                        weeklyReportController.getData(convertWeekDay, 0);
                this.mWeeklyTime.mDailyRangingCnt[i5] = jArr[convertWeekDay];
                i4--;
                if (i4 < 1) {
                    i4 = 7;
                }
            }
            WeeklyTimeReport weeklyTimeReport2 = this.mWeeklyTime;
            weeklyTimeReport2.generateWeekData();
            WeeklyTimeUsageBarChart weeklyTimeUsageBarChart = weeklyTimeReport2.mTimeBarChart;
            weeklyTimeUsageBarChart.setVisibility(0);
            weeklyTimeUsageBarChart.mBarGraphTap = 0;
            ArrayList arrayList = new ArrayList();
            int i6 = -1;
            for (int i7 = 0; i7 < 7; i7++) {
                float f = (weeklyTimeReport2.mDailyConnData[6 - i7] / 3600000) % 3600001;
                arrayList.add(new BarEntry(i7, new float[] {f}));
                if (i6 < f) {
                    i6 = ((int) f) + 1;
                }
            }
            StringBuilder sb = new StringBuilder("ranging count - 0:");
            long[] jArr2 = weeklyTimeReport2.mDailyRangingCnt;
            sb.append(jArr2[0]);
            sb.append(" 1:");
            sb.append(jArr2[1]);
            sb.append(" 2:");
            sb.append(jArr2[2]);
            sb.append(" 3:");
            sb.append(jArr2[3]);
            SemLog.d("WeeklyTimeUsageBarChart", sb.toString());
            if (i6 <= 24) {
                int i8 = 0;
                while (true) {
                    if (i8 >= 3) {
                        break;
                    }
                    int i9 = i6 + i8;
                    if (i9 % 3 == 0) {
                        i2 = i9;
                        break;
                    }
                    i8++;
                }
            } else {
                i2 = 24;
            }
            weeklyTimeUsageBarChart.mMaxYValue = i2;
            BarDataSet barDataSet = new BarDataSet(arrayList);
            weeklyTimeUsageBarChart.mWeeklyBarDataSet = barDataSet;
            barDataSet.setColors(WeeklyTimeUsageBarChart.TIME_BAR_COLORS[1]);
            weeklyTimeUsageBarChart.mWeeklyIBarDataSet.clear();
            weeklyTimeUsageBarChart.mWeeklyIBarDataSet.add(
                    weeklyTimeUsageBarChart.mWeeklyBarDataSet);
            weeklyTimeUsageBarChart.mWeeklyBarData =
                    new BarData(weeklyTimeUsageBarChart.mWeeklyIBarDataSet);
            if (UwbLabsUtils.mDarkMode) {
                weeklyTimeUsageBarChart.mWeeklyBarDataSet.mHighLightColor =
                        EmergencyPhoneWidget.BG_COLOR;
            } else {
                weeklyTimeUsageBarChart.mWeeklyBarDataSet.mHighLightColor = -1;
            }
            weeklyTimeUsageBarChart.showWeeklyBarChart();
            weeklyTimeReport2.mGraphDataImage2.setImageDrawable(
                    weeklyTimeReport2.getPointDrawable(
                            R.color.sec_uwb_labs_weekly_report_time_usage_uwb_connected_color));
            weeklyTimeReport2.mGraphDataImage3.setImageDrawable(
                    weeklyTimeReport2.getPointDrawable(
                            R.color.sec_uwb_labs_weekly_report_time_usage_uwb_on_color));
            weeklyTimeReport2.updateInternal();
            this.mWeeklyUsage.mUsageBarChart.setVisibility(8);
            return;
        }
        WeeklyUsageReport weeklyUsageReport = this.mWeeklyUsage;
        for (int i10 = 0; i10 < 7; i10++) {
            Arrays.fill(weeklyUsageReport.mDailyUsageTime[i10], 0L);
            Arrays.fill(weeklyUsageReport.mDailyPackageUsageCnt[i10], 0L);
        }
        weeklyUsageReport.getClass();
        int i11 = this.mCurrentDate.get(7) % 7;
        long[] jArr3 = {0, 0, 0};
        for (int i12 = 0; i12 < 7; i12++) {
            System.arraycopy(
                    weeklyReportController.getData(UwbLabsUtils.convertWeekDay(i11) + 7),
                    0,
                    jArr3,
                    0,
                    3);
            WeeklyUsageReport weeklyUsageReport2 = this.mWeeklyUsage;
            if (i12 >= 7) {
                weeklyUsageReport2.getClass();
                Log.e("WeeklyUsageReport", "failed to update : wrong data");
            } else {
                long[] jArr4 = weeklyUsageReport2.mDailyPackageUsageCnt[i12];
                jArr4[0] = jArr3[0];
                jArr4[1] = jArr3[1];
                jArr4[2] = jArr3[2];
            }
            for (int i13 = 0; i13 <= 2; i13++) {
                long data =
                        weeklyReportController.getData(UwbLabsUtils.convertWeekDay(i11) + 100, i13);
                if (data != 0) {
                    data = (data / 60) / 1000;
                }
                WeeklyUsageReport weeklyUsageReport3 = this.mWeeklyUsage;
                weeklyUsageReport3.getClass();
                if (i12 >= 7 || i13 > 2) {
                    Log.e("WeeklyUsageReport", "failed to update : wrong data");
                } else {
                    weeklyUsageReport3.mDailyUsageTime[i12][i13] = data;
                }
            }
            i11--;
            if (i11 < 1) {
                i11 = 7;
            }
        }
        WeeklyUsageReport weeklyUsageReport4 = this.mWeeklyUsage;
        weeklyUsageReport4.generateWeekData();
        WeeklyTimeUsageBarChart weeklyTimeUsageBarChart2 = weeklyUsageReport4.mUsageBarChart;
        weeklyTimeUsageBarChart2.setVisibility(0);
        weeklyTimeUsageBarChart2.mBarGraphTap = 1;
        ArrayList arrayList2 = new ArrayList();
        int i14 = 0;
        int i15 = -1;
        while (i14 < i) {
            long[] jArr5 = weeklyUsageReport4.mDailyUsageTime[6 - i14];
            float f2 = jArr5[i2];
            float f3 = jArr5[1];
            float f4 = jArr5[2];
            arrayList2.add(new BarEntry(i14, new float[] {f4, f3, f2}));
            float f5 = f2 + f3 + f4;
            if (i15 < f5) {
                i15 = ((int) f5) + 1;
            }
            i14++;
            i = 7;
            i2 = 0;
        }
        weeklyTimeUsageBarChart2.mMaxYValue = i15;
        BarDataSet barDataSet2 = new BarDataSet(arrayList2);
        weeklyTimeUsageBarChart2.mWeeklyBarDataSet = barDataSet2;
        if (UwbLabsUtils.mDarkMode) {
            barDataSet2.setColors(WeeklyTimeUsageBarChart.USAGE_BAR_COLORS_DARK);
        } else {
            barDataSet2.setColors(WeeklyTimeUsageBarChart.USAGE_BAR_COLORS);
        }
        weeklyTimeUsageBarChart2.mWeeklyIBarDataSet.clear();
        weeklyTimeUsageBarChart2.mWeeklyIBarDataSet.add(weeklyTimeUsageBarChart2.mWeeklyBarDataSet);
        weeklyTimeUsageBarChart2.mWeeklyBarData =
                new BarData(weeklyTimeUsageBarChart2.mWeeklyIBarDataSet);
        if (UwbLabsUtils.mDarkMode) {
            weeklyTimeUsageBarChart2.mWeeklyBarDataSet.mHighLightColor =
                    EmergencyPhoneWidget.BG_COLOR;
        } else {
            weeklyTimeUsageBarChart2.mWeeklyBarDataSet.mHighLightColor = -1;
        }
        weeklyTimeUsageBarChart2.showWeeklyBarChart();
        weeklyUsageReport4.mGraphDataImage1.setImageDrawable(
                weeklyUsageReport4.getPointDrawable(
                        R.color.sec_uwb_labs_weekly_report_bar_color_1));
        weeklyUsageReport4.mGraphDataImage2.setImageDrawable(
                weeklyUsageReport4.getPointDrawable(
                        R.color.sec_uwb_labs_weekly_report_bar_color_2));
        weeklyUsageReport4.mGraphDataImage3.setImageDrawable(
                weeklyUsageReport4.getPointDrawable(
                        R.color.sec_uwb_labs_weekly_report_bar_color_3));
        weeklyUsageReport4.mGraphDataImage1.setVisibility(0);
        weeklyUsageReport4.mGraphDataImage3.setVisibility(0);
        weeklyUsageReport4.updateInternal$1();
        this.mWeeklyTime.mTimeBarChart.setVisibility(8);
    }
}
