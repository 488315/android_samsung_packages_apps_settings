package com.samsung.android.settings.wifi.develop;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

import com.google.android.material.tabs.TabLayout;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.wifi.develop.weeklyreport.WeeklyTimeUsageBarChart;
import com.samsung.android.settings.wifi.develop.weeklyreport.progressbar.WeeklyReportProgressbar;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiLabsWeeklyReportPreference extends Preference {
    public static final String[] MONTHS = {
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December"
    };
    public static final String[] WEEKS = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    public final AnonymousClass2 chartValueSelectedListener;
    public TextView mBand24Data;
    public ImageView mBand24Point;
    public TextView mBand5Data;
    public ImageView mBand5Point;
    public TextView mBand6Data;
    public ImageView mBand6Point;
    public TextView mBandMultiLinkData;
    public ImageView mBandMultiLinkPoint;
    public WeeklyReportProgressbar mBandStackProgressbar;
    public ImageView mConnectedPoint;
    public TextView mConnectedTx;
    public TextView mConnectedTxData;
    public final Context mContext;
    public TextView mDate;
    public PreferenceViewHolder mHolder;
    public long mLastUpdateTime;
    public SemWifiManager mSemWifiManager;
    public WeeklyReportProgressbar mStandardStackProgressbar;
    public TextView mStandardWifi4Data;
    public ImageView mStandardWifi4Point;
    public TextView mStandardWifi5Data;
    public ImageView mStandardWifi5Point;
    public TextView mStandardWifi6Data;
    public ImageView mStandardWifi6Point;
    public TextView mStandardWifi7Data;
    public ImageView mStandardWifi7Point;
    public int mTabIndexSelected;
    public WeeklyTimeUsageBarChart mTimeBarChart;
    public WeeklyTimeUsageBarChart mUsageBarChart;
    public final List mWeeklyDate;
    public ImageView mWifiOnPoint;
    public TextView mWifiOnRx;
    public TextView mWifiOnRxData;
    public final WeeklyReport mWr;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.develop.WifiLabsWeeklyReportPreference$2, reason: invalid class name */
    public final class AnonymousClass2 {
        public AnonymousClass2() {}
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class WeeklyReport {
        public final long[] mDailyConnectionTime = {0, 0, 0, 0, 0, 0, 0};
        public final long[] mDailyEnabledTime = {0, 0, 0, 0, 0, 0, 0};
        public final long[] mDailyRxBytes = {0, 0, 0, 0, 0, 0, 0};
        public final long[] mDailyTxBytes = {0, 0, 0, 0, 0, 0, 0};
        public final long[] mTotalConnectionTimeInBand = {0, 0, 0, 0};
        public final long[] mTotalRxBytesInBand = {0, 0, 0, 0};
        public final long[] mTotalTxBytesInBand = {0, 0, 0, 0};
        public final long[] mTotalBytesInBand = {0, 0, 0, 0};
        public final long[] mTotalConnectionTimeInStandard = {0, 0, 0, 0};
        public final long[] mTotalRxBytesInStandard = {0, 0, 0, 0};
        public final long[] mTotalTxBytesInStandard = {0, 0, 0, 0};
        public final long[] mTotalBytesInStandard = {0, 0, 0, 0};

        public WeeklyReport() {}

        public final String toString() {
            String str =
                    "###############################################################################\n"
                        + "WEEKLY REPORT\n";
            int i = 6;
            while (true) {
                WifiLabsWeeklyReportPreference wifiLabsWeeklyReportPreference =
                        WifiLabsWeeklyReportPreference.this;
                if (i < 0) {
                    StringBuilder m =
                            PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(
                                    AbstractResolvableFuture$$ExternalSyntheticOutline0.m(
                                            AbstractResolvableFuture$$ExternalSyntheticOutline0.m(
                                                    str, "\n"),
                                            "BAND\n"),
                                    "2.4GHz: ");
                    long[] jArr = this.mTotalConnectionTimeInBand;
                    m.append(jArr[0] / 3600000);
                    m.append("h ");
                    m.append((jArr[0] % 3600000) / 60000);
                    m.append("m / ");
                    Context context = wifiLabsWeeklyReportPreference.getContext();
                    long[] jArr2 = this.mTotalRxBytesInBand;
                    m.append(Formatter.formatShortFileSize(context, jArr2[0]));
                    m.append(" / ");
                    Context context2 = wifiLabsWeeklyReportPreference.getContext();
                    long[] jArr3 = this.mTotalTxBytesInBand;
                    m.append(Formatter.formatShortFileSize(context2, jArr3[0]));
                    m.append("\n");
                    StringBuilder m2 =
                            PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(
                                    m.toString(), "5GHz  : ");
                    m2.append(jArr[1] / 3600000);
                    m2.append("h ");
                    m2.append((jArr[1] % 3600000) / 60000);
                    m2.append("m / ");
                    m2.append(
                            Formatter.formatShortFileSize(
                                    wifiLabsWeeklyReportPreference.getContext(), jArr2[1]));
                    m2.append(" / ");
                    m2.append(
                            Formatter.formatShortFileSize(
                                    wifiLabsWeeklyReportPreference.getContext(), jArr3[1]));
                    m2.append("\n");
                    StringBuilder m3 =
                            PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(
                                    m2.toString(), "6GHz  : ");
                    m3.append(jArr[2] / 3600000);
                    m3.append("h ");
                    m3.append((jArr[2] % 3600000) / 60000);
                    m3.append("m / ");
                    m3.append(
                            Formatter.formatShortFileSize(
                                    wifiLabsWeeklyReportPreference.getContext(), jArr2[2]));
                    m3.append(" / ");
                    m3.append(
                            Formatter.formatShortFileSize(
                                    wifiLabsWeeklyReportPreference.getContext(), jArr3[2]));
                    m3.append("\n");
                    StringBuilder m4 =
                            PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(
                                    m3.toString(), "Multi-link  : ");
                    m4.append(jArr[3] / 3600000);
                    m4.append("h ");
                    m4.append((jArr[3] % 3600000) / 60000);
                    m4.append("m / ");
                    m4.append(
                            Formatter.formatShortFileSize(
                                    wifiLabsWeeklyReportPreference.getContext(), jArr2[3]));
                    m4.append(" / ");
                    m4.append(
                            Formatter.formatShortFileSize(
                                    wifiLabsWeeklyReportPreference.getContext(), jArr3[3]));
                    m4.append("\n");
                    StringBuilder m5 =
                            PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(
                                    AbstractResolvableFuture$$ExternalSyntheticOutline0.m(
                                            m4.toString(), "STANDARD\n"),
                                    "Wi-Fi 4: ");
                    long[] jArr4 = this.mTotalConnectionTimeInStandard;
                    m5.append(jArr4[0] / 3600000);
                    m5.append("h ");
                    m5.append((jArr4[0] % 3600000) / 60000);
                    m5.append("m / ");
                    Context context3 = wifiLabsWeeklyReportPreference.getContext();
                    long[] jArr5 = this.mTotalRxBytesInStandard;
                    m5.append(Formatter.formatShortFileSize(context3, jArr5[0]));
                    m5.append(" / ");
                    Context context4 = wifiLabsWeeklyReportPreference.getContext();
                    long[] jArr6 = this.mTotalTxBytesInStandard;
                    m5.append(Formatter.formatShortFileSize(context4, jArr6[0]));
                    m5.append("\n");
                    StringBuilder m6 =
                            PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(
                                    m5.toString(), "Wi-Fi 5: ");
                    m6.append(jArr4[1] / 3600000);
                    m6.append("h ");
                    m6.append((jArr4[1] % 3600000) / 60000);
                    m6.append("m / ");
                    m6.append(
                            Formatter.formatShortFileSize(
                                    wifiLabsWeeklyReportPreference.getContext(), jArr5[1]));
                    m6.append(" / ");
                    m6.append(
                            Formatter.formatShortFileSize(
                                    wifiLabsWeeklyReportPreference.getContext(), jArr6[1]));
                    m6.append("\n");
                    StringBuilder m7 =
                            PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(
                                    m6.toString(), "Wi-Fi 6: ");
                    m7.append(jArr4[2] / 3600000);
                    m7.append("h ");
                    m7.append((jArr4[2] % 3600000) / 60000);
                    m7.append("m / ");
                    m7.append(
                            Formatter.formatShortFileSize(
                                    wifiLabsWeeklyReportPreference.getContext(), jArr5[2]));
                    m7.append(" / ");
                    m7.append(
                            Formatter.formatShortFileSize(
                                    wifiLabsWeeklyReportPreference.getContext(), jArr6[2]));
                    m7.append("\n");
                    StringBuilder m8 =
                            PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(
                                    m7.toString(), "Wi-Fi 7: ");
                    m8.append(jArr4[3] / 3600000);
                    m8.append("h ");
                    m8.append((jArr4[3] % 3600000) / 60000);
                    m8.append("m / ");
                    m8.append(
                            Formatter.formatShortFileSize(
                                    wifiLabsWeeklyReportPreference.getContext(), jArr5[3]));
                    m8.append(" / ");
                    m8.append(
                            Formatter.formatShortFileSize(
                                    wifiLabsWeeklyReportPreference.getContext(), jArr6[3]));
                    m8.append("\n");
                    return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(
                            m8.toString(),
                            "###############################################################################");
                }
                StringBuilder m9 =
                        PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(str, "[");
                long[] jArr7 = this.mDailyConnectionTime;
                m9.append(jArr7[i] / 3600000);
                m9.append("h");
                StringBuilder m10 =
                        EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(
                                m9.toString());
                m10.append((jArr7[i] % 3600000) / 60000);
                m10.append("m / ");
                StringBuilder m11 =
                        EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(
                                m10.toString());
                long[] jArr8 = this.mDailyEnabledTime;
                m11.append(jArr8[i] / 3600000);
                m11.append("h");
                StringBuilder m12 =
                        EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(
                                m11.toString());
                m12.append((jArr8[i] % 3600000) / 60000);
                m12.append("m / ");
                StringBuilder m13 =
                        EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(
                                m12.toString());
                m13.append(
                        Formatter.formatShortFileSize(
                                wifiLabsWeeklyReportPreference.getContext(),
                                this.mDailyRxBytes[i]));
                m13.append(" / ");
                StringBuilder m14 =
                        EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(
                                m13.toString());
                m14.append(
                        Formatter.formatShortFileSize(
                                wifiLabsWeeklyReportPreference.getContext(),
                                this.mDailyTxBytes[i]));
                m14.append("]  ");
                str = m14.toString();
                i--;
            }
        }

        public final void update() {
            WifiLabsWeeklyReportPreference wifiLabsWeeklyReportPreference;
            long[] jArr = this.mDailyConnectionTime;
            Arrays.fill(jArr, 0L);
            long[] jArr2 = this.mDailyEnabledTime;
            Arrays.fill(jArr2, 0L);
            long[] jArr3 = this.mDailyRxBytes;
            Arrays.fill(jArr3, 0L);
            long[] jArr4 = this.mDailyTxBytes;
            Arrays.fill(jArr4, 0L);
            long[] jArr5 = this.mTotalConnectionTimeInBand;
            Arrays.fill(jArr5, 0L);
            long[] jArr6 = this.mTotalRxBytesInBand;
            Arrays.fill(jArr6, 0L);
            long[] jArr7 = this.mTotalTxBytesInBand;
            Arrays.fill(jArr7, 0L);
            long[] jArr8 = this.mTotalBytesInBand;
            Arrays.fill(jArr8, 0L);
            long[] jArr9 = this.mTotalConnectionTimeInStandard;
            Arrays.fill(jArr9, 0L);
            long[] jArr10 = this.mTotalRxBytesInStandard;
            Arrays.fill(jArr10, 0L);
            long[] jArr11 = this.mTotalTxBytesInStandard;
            Arrays.fill(jArr11, 0L);
            long[] jArr12 = this.mTotalBytesInStandard;
            Arrays.fill(jArr12, 0L);
            WifiLabsWeeklyReportPreference wifiLabsWeeklyReportPreference2 =
                    WifiLabsWeeklyReportPreference.this;
            if (wifiLabsWeeklyReportPreference2.mSemWifiManager == null) {
                return;
            }
            int i = 0;
            while (i < 7) {
                String dailyUsageInfo =
                        wifiLabsWeeklyReportPreference2.mSemWifiManager.getDailyUsageInfo(i);
                if (TextUtils.isEmpty(dailyUsageInfo)) {
                    wifiLabsWeeklyReportPreference = wifiLabsWeeklyReportPreference2;
                } else {
                    String[] split = dailyUsageInfo.split(" ");
                    wifiLabsWeeklyReportPreference = wifiLabsWeeklyReportPreference2;
                    if (split.length >= 25) {
                        try {
                            try {
                                jArr[i] = Long.valueOf(split[0]).longValue();
                                jArr2[i] = Long.valueOf(split[1]).longValue();
                                jArr3[i] = Long.valueOf(split[2]).longValue();
                                jArr4[i] = Long.valueOf(split[3]).longValue();
                                try {
                                    jArr5[0] = jArr5[0] + Long.valueOf(split[4]).longValue();
                                    jArr5[1] = jArr5[1] + Long.valueOf(split[5]).longValue();
                                    jArr5[2] = jArr5[2] + Long.valueOf(split[6]).longValue();
                                    jArr5[3] = jArr5[3] + Long.valueOf(split[7]).longValue();
                                    jArr6[0] = jArr6[0] + Long.valueOf(split[8]).longValue();
                                    jArr6[1] = jArr6[1] + Long.valueOf(split[9]).longValue();
                                    jArr6[2] = jArr6[2] + Long.valueOf(split[10]).longValue();
                                    jArr6[3] = jArr6[3] + Long.valueOf(split[11]).longValue();
                                    jArr7[0] = jArr7[0] + Long.valueOf(split[12]).longValue();
                                    jArr7[1] = jArr7[1] + Long.valueOf(split[13]).longValue();
                                    jArr7[2] = jArr7[2] + Long.valueOf(split[14]).longValue();
                                    jArr7[3] = jArr7[3] + Long.valueOf(split[15]).longValue();
                                    jArr8[0] = jArr6[0] + jArr7[0];
                                    jArr8[1] = jArr6[1] + jArr7[1];
                                    jArr8[2] = jArr6[2] + jArr7[2];
                                    jArr8[3] = jArr6[3] + jArr7[3];
                                    jArr9[0] = jArr9[0] + Long.valueOf(split[16]).longValue();
                                    jArr9[1] = jArr9[1] + Long.valueOf(split[17]).longValue();
                                    jArr9[2] = jArr9[2] + Long.valueOf(split[18]).longValue();
                                    jArr9[3] = jArr9[3] + Long.valueOf(split[19]).longValue();
                                    jArr10[0] = jArr10[0] + Long.valueOf(split[20]).longValue();
                                    jArr10[1] = jArr10[1] + Long.valueOf(split[21]).longValue();
                                    jArr10[2] = jArr10[2] + Long.valueOf(split[22]).longValue();
                                    jArr10[3] = jArr10[3] + Long.valueOf(split[23]).longValue();
                                    jArr11[0] = jArr11[0] + Long.valueOf(split[24]).longValue();
                                    jArr11[1] = jArr11[1] + Long.valueOf(split[25]).longValue();
                                    jArr11[2] = jArr11[2] + Long.valueOf(split[26]).longValue();
                                    jArr11[3] = jArr11[3] + Long.valueOf(split[27]).longValue();
                                    try {
                                        jArr12[0] = jArr10[0] + jArr11[0];
                                        jArr12[1] = jArr10[1] + jArr11[1];
                                        jArr12[2] = jArr10[2] + jArr11[2];
                                        jArr12[3] = jArr10[3] + jArr11[3];
                                    } catch (NumberFormatException e) {
                                        e = e;
                                        Log.e(
                                                "WifiLabsWeeklyReport.Preference",
                                                "NumberFormatException " + e);
                                        i++;
                                        wifiLabsWeeklyReportPreference2 =
                                                wifiLabsWeeklyReportPreference;
                                    }
                                } catch (NumberFormatException e2) {
                                    e = e2;
                                }
                            } catch (NumberFormatException e3) {
                                e = e3;
                            }
                        } catch (NumberFormatException e4) {
                            e = e4;
                        }
                    }
                }
                i++;
                wifiLabsWeeklyReportPreference2 = wifiLabsWeeklyReportPreference;
            }
        }
    }

    public WifiLabsWeeklyReportPreference(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mWr = new WeeklyReport();
        this.mTabIndexSelected = 0;
        this.mWeeklyDate = new ArrayList();
        this.chartValueSelectedListener = new AnonymousClass2();
        this.mContext = context;
    }

    public static String getTimeString(long j) {
        long j2 = j / 60;
        long j3 = (j2 / 60) / 1000;
        long j4 = (j2 / 1000) % 60;
        if (j3 == 0) {
            return ApnSettings.MVNO_NONE + j4 + "m";
        }
        if (j3 >= 24) {
            Log.d(
                    "WifiLabsWeeklyReport.Preference",
                    "It was over 24 hours. Change the time to 24h. previous hours : " + j3);
            return "24h";
        }
        String str = ApnSettings.MVNO_NONE + j3 + "h ";
        if (j4 == 0) {
            return str;
        }
        return str + j4 + "m";
    }

    public static String getTimeString$1(long j) {
        long j2 = j / 60;
        long j3 = (j2 / 60) / 1000;
        long j4 = (j2 / 1000) % 60;
        if (j3 == 0) {
            return ApnSettings.MVNO_NONE + j4 + "m";
        }
        String str = ApnSettings.MVNO_NONE + j3 + "h ";
        if (j4 == 0) {
            return str;
        }
        return str + j4 + "m";
    }

    public final void animateProgressBar(WeeklyReport weeklyReport, int i, boolean z) {
        if (i == 0) {
            long[] jArr = weeklyReport.mTotalConnectionTimeInBand;
            float f = jArr[0];
            float f2 = jArr[1];
            float f3 = jArr[2];
            float f4 = jArr[3];
            this.mBandStackProgressbar.setProgressbar(
                    new float[] {f, f2, f3, f4}, f + f2 + f3 + f4, z);
            WeeklyReportProgressbar weeklyReportProgressbar = this.mStandardStackProgressbar;
            weeklyReportProgressbar.mIsBandColor = false;
            long[] jArr2 = weeklyReport.mTotalConnectionTimeInStandard;
            float f5 = jArr2[0];
            float f6 = jArr2[1];
            float f7 = jArr2[2];
            float f8 = jArr2[3];
            weeklyReportProgressbar.setProgressbar(
                    new float[] {f5, f6, f7, f8}, f5 + f6 + f7 + f8, z);
            TextView textView = this.mBand24Data;
            long[] jArr3 = weeklyReport.mTotalConnectionTimeInBand;
            textView.setText(getTimeString$1(jArr3[0]));
            this.mBand5Data.setText(getTimeString$1(jArr3[1]));
            this.mBand6Data.setText(getTimeString$1(jArr3[2]));
            this.mBandMultiLinkData.setText(getTimeString$1(jArr3[3]));
            this.mStandardWifi4Data.setText(getTimeString$1(jArr2[0]));
            this.mStandardWifi5Data.setText(getTimeString$1(jArr2[1]));
            this.mStandardWifi6Data.setText(getTimeString$1(jArr2[2]));
            this.mStandardWifi7Data.setText(getTimeString$1(jArr2[3]));
        } else {
            long[] jArr4 = weeklyReport.mTotalBytesInBand;
            float f9 = jArr4[0];
            float f10 = jArr4[1];
            float f11 = jArr4[2];
            float f12 = jArr4[3];
            this.mBandStackProgressbar.setProgressbar(
                    new float[] {f9, f10, f11, f12}, f9 + f10 + f11 + f12, z);
            WeeklyReportProgressbar weeklyReportProgressbar2 = this.mStandardStackProgressbar;
            weeklyReportProgressbar2.mIsBandColor = false;
            long[] jArr5 = weeklyReport.mTotalBytesInStandard;
            float f13 = jArr5[0];
            float f14 = jArr5[1];
            float f15 = jArr5[2];
            float f16 = jArr5[3];
            weeklyReportProgressbar2.setProgressbar(
                    new float[] {f13, f14, f15, f16}, f13 + f14 + f15 + f16, z);
            TextView textView2 = this.mBand24Data;
            Context context = getContext();
            long[] jArr6 = weeklyReport.mTotalBytesInBand;
            textView2.setText(Formatter.formatShortFileSize(context, jArr6[0]));
            this.mBand5Data.setText(Formatter.formatShortFileSize(getContext(), jArr6[1]));
            this.mBand6Data.setText(Formatter.formatShortFileSize(getContext(), jArr6[2]));
            this.mBandMultiLinkData.setText(Formatter.formatShortFileSize(getContext(), jArr6[3]));
            this.mStandardWifi4Data.setText(Formatter.formatShortFileSize(getContext(), jArr5[0]));
            this.mStandardWifi5Data.setText(Formatter.formatShortFileSize(getContext(), jArr5[1]));
            this.mStandardWifi6Data.setText(Formatter.formatShortFileSize(getContext(), jArr5[2]));
            this.mStandardWifi7Data.setText(Formatter.formatShortFileSize(getContext(), jArr5[3]));
        }
        new LinearLayout.LayoutParams(-2, -2);
    }

    public final Drawable getPointDrawable$1(int i) {
        GradientDrawable gradientDrawable =
                (GradientDrawable) this.mContext.getDrawable(R.drawable.sec_wifi_labs_open_point);
        gradientDrawable.setColor(this.mContext.getColor(i));
        return gradientDrawable;
    }

    public final void initDate() {
        Calendar calendar = Calendar.getInstance();
        this.mWeeklyDate.clear();
        for (int i = 0; i < 7; i++) {
            this.mWeeklyDate.add(
                    WEEKS[calendar.get(7) - 1]
                            + ". "
                            + MONTHS[calendar.get(2)]
                            + " "
                            + calendar.get(5));
            calendar.add(5, -1);
        }
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        this.mHolder = preferenceViewHolder;
        this.mSemWifiManager =
                (SemWifiManager) this.mContext.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        this.mWifiOnRx = (TextView) preferenceViewHolder.findViewById(R.id.wifi_on_tv);
        this.mConnectedTx = (TextView) preferenceViewHolder.findViewById(R.id.connected_tv);
        this.mWifiOnRxData = (TextView) preferenceViewHolder.findViewById(R.id.wifi_on_time_tv);
        this.mConnectedTxData =
                (TextView) preferenceViewHolder.findViewById(R.id.connected_time_tv);
        this.mBandStackProgressbar =
                (WeeklyReportProgressbar)
                        preferenceViewHolder.findViewById(R.id.weekly_report_band_progress_bar);
        this.mStandardStackProgressbar =
                (WeeklyReportProgressbar)
                        preferenceViewHolder.findViewById(R.id.weekly_report_standard_progress_bar);
        this.mTimeBarChart =
                (WeeklyTimeUsageBarChart) this.mHolder.findViewById(R.id.time_barchart);
        this.mUsageBarChart =
                (WeeklyTimeUsageBarChart) this.mHolder.findViewById(R.id.usage_barchart);
        this.mTimeBarChart.initialize();
        this.mUsageBarChart.initialize();
        WeeklyTimeUsageBarChart weeklyTimeUsageBarChart = this.mTimeBarChart;
        AnonymousClass2 anonymousClass2 = this.chartValueSelectedListener;
        weeklyTimeUsageBarChart.callbackListener = anonymousClass2;
        this.mUsageBarChart.callbackListener = anonymousClass2;
        this.mWifiOnPoint = (ImageView) preferenceViewHolder.findViewById(R.id.wifi_on_img);
        this.mConnectedPoint = (ImageView) preferenceViewHolder.findViewById(R.id.connected_img);
        this.mBand24Point = (ImageView) preferenceViewHolder.findViewById(R.id.band_24_img);
        this.mBand5Point = (ImageView) preferenceViewHolder.findViewById(R.id.band_5_img);
        this.mBand6Point = (ImageView) preferenceViewHolder.findViewById(R.id.band_6_img);
        this.mBandMultiLinkPoint =
                (ImageView) preferenceViewHolder.findViewById(R.id.band_multi_link_img);
        this.mBand24Data = (TextView) preferenceViewHolder.findViewById(R.id.band_24_time_tv);
        this.mBand5Data = (TextView) preferenceViewHolder.findViewById(R.id.band_5_time_tv);
        this.mBand6Data = (TextView) preferenceViewHolder.findViewById(R.id.band_6_time_tv);
        this.mBandMultiLinkData =
                (TextView) preferenceViewHolder.findViewById(R.id.band_multi_link_time_tv);
        this.mStandardWifi4Point =
                (ImageView) preferenceViewHolder.findViewById(R.id.standard_4_img);
        this.mStandardWifi5Point =
                (ImageView) preferenceViewHolder.findViewById(R.id.standard_5_img);
        this.mStandardWifi6Point =
                (ImageView) preferenceViewHolder.findViewById(R.id.standard_6_img);
        this.mStandardWifi7Point =
                (ImageView) preferenceViewHolder.findViewById(R.id.standard_7_img);
        this.mStandardWifi4Data =
                (TextView) preferenceViewHolder.findViewById(R.id.standard_4_time_tv);
        this.mStandardWifi5Data =
                (TextView) preferenceViewHolder.findViewById(R.id.standard_5_time_tv);
        this.mStandardWifi6Data =
                (TextView) preferenceViewHolder.findViewById(R.id.standard_6_time_tv);
        this.mStandardWifi7Data =
                (TextView) preferenceViewHolder.findViewById(R.id.standard_7_time_tv);
        this.mWifiOnPoint.setImageDrawable(
                getPointDrawable$1(R.color.sec_wifi_labs_weekly_report_time_usage_wifi_on_color));
        this.mConnectedPoint.setImageDrawable(
                getPointDrawable$1(R.color.sec_wifi_labs_weekly_report_time_usage_connected_color));
        this.mBand24Point.setImageDrawable(
                getPointDrawable$1(R.color.sec_wifi_labs_weekly_report_band_24_color));
        this.mBand5Point.setImageDrawable(
                getPointDrawable$1(R.color.sec_wifi_labs_weekly_report_band_5_color));
        this.mBand6Point.setImageDrawable(
                getPointDrawable$1(R.color.sec_wifi_labs_weekly_report_band_6_color));
        this.mBandMultiLinkPoint.setImageDrawable(
                getPointDrawable$1(R.color.sec_wifi_labs_weekly_report_band_multi_link_color));
        this.mStandardWifi4Point.setImageDrawable(
                getPointDrawable$1(R.color.sec_wifi_labs_weekly_report_standard_wifi_4_color));
        this.mStandardWifi5Point.setImageDrawable(
                getPointDrawable$1(R.color.sec_wifi_labs_weekly_report_standard_wifi_5_color));
        this.mStandardWifi6Point.setImageDrawable(
                getPointDrawable$1(R.color.sec_wifi_labs_weekly_report_standard_wifi_6_color));
        this.mStandardWifi7Point.setImageDrawable(
                getPointDrawable$1(R.color.sec_wifi_labs_weekly_report_standard_wifi_7_color));
        TabLayout tabLayout = (TabLayout) preferenceViewHolder.findViewById(R.id.tab_layout);
        tabLayout.seslSetSubTabStyle();
        tabLayout.addOnTabSelectedListener$1(
                new TabLayout
                        .OnTabSelectedListener() { // from class:
                                                   // com.samsung.android.settings.wifi.develop.WifiLabsWeeklyReportPreference.1
                    @Override // com.google.android.material.tabs.TabLayout.OnTabSelectedListener
                    public final void onTabSelected(TabLayout.Tab tab) {
                        WifiLabsWeeklyReportPreference wifiLabsWeeklyReportPreference =
                                WifiLabsWeeklyReportPreference.this;
                        wifiLabsWeeklyReportPreference.mWr.update();
                        Log.i(
                                "WifiLabsWeeklyReport.Preference",
                                wifiLabsWeeklyReportPreference.mWr.toString());
                        int i = tab.position;
                        wifiLabsWeeklyReportPreference.animateProgressBar(
                                wifiLabsWeeklyReportPreference.mWr, i, true);
                        if (i == 0) {
                            wifiLabsWeeklyReportPreference.mTabIndexSelected = 0;
                            wifiLabsWeeklyReportPreference.mTimeBarChart.setVisibility(0);
                            wifiLabsWeeklyReportPreference.mUsageBarChart.setVisibility(8);
                            WeeklyTimeUsageBarChart weeklyTimeUsageBarChart2 =
                                    wifiLabsWeeklyReportPreference.mTimeBarChart;
                            WeeklyReport weeklyReport = wifiLabsWeeklyReportPreference.mWr;
                            weeklyTimeUsageBarChart2.updateBarGraph(
                                    0,
                                    weeklyReport.mDailyConnectionTime,
                                    weeklyReport.mDailyEnabledTime);
                            wifiLabsWeeklyReportPreference.mWifiOnPoint.setImageDrawable(
                                    wifiLabsWeeklyReportPreference.getPointDrawable$1(
                                            R.color
                                                    .sec_wifi_labs_weekly_report_time_usage_wifi_on_color));
                            wifiLabsWeeklyReportPreference.mConnectedPoint.setImageDrawable(
                                    wifiLabsWeeklyReportPreference.getPointDrawable$1(
                                            R.color
                                                    .sec_wifi_labs_weekly_report_time_usage_connected_color));
                            wifiLabsWeeklyReportPreference.mDate.setText(
                                    (CharSequence)
                                            ((ArrayList) wifiLabsWeeklyReportPreference.mWeeklyDate)
                                                    .get(
                                                            wifiLabsWeeklyReportPreference
                                                                    .mUsageBarChart
                                                                    .mShiftIndex));
                            wifiLabsWeeklyReportPreference.mWifiOnRx.setText("Wi-Fi On");
                            wifiLabsWeeklyReportPreference.mWifiOnRxData.setText(
                                    WifiLabsWeeklyReportPreference.getTimeString$1(
                                            wifiLabsWeeklyReportPreference
                                                    .mWr
                                                    .mDailyEnabledTime[
                                                    wifiLabsWeeklyReportPreference
                                                            .mUsageBarChart
                                                            .mShiftIndex]));
                            wifiLabsWeeklyReportPreference.mConnectedTx.setText("Connected");
                            wifiLabsWeeklyReportPreference.mConnectedTxData.setText(
                                    WifiLabsWeeklyReportPreference.getTimeString$1(
                                            wifiLabsWeeklyReportPreference
                                                    .mWr
                                                    .mDailyConnectionTime[
                                                    wifiLabsWeeklyReportPreference
                                                            .mUsageBarChart
                                                            .mShiftIndex]));
                            return;
                        }
                        wifiLabsWeeklyReportPreference.mTabIndexSelected = 1;
                        wifiLabsWeeklyReportPreference.mTimeBarChart.setVisibility(8);
                        wifiLabsWeeklyReportPreference.mUsageBarChart.setVisibility(0);
                        WeeklyTimeUsageBarChart weeklyTimeUsageBarChart3 =
                                wifiLabsWeeklyReportPreference.mUsageBarChart;
                        WeeklyReport weeklyReport2 = wifiLabsWeeklyReportPreference.mWr;
                        weeklyTimeUsageBarChart3.updateBarGraph(
                                1, weeklyReport2.mDailyTxBytes, weeklyReport2.mDailyRxBytes);
                        wifiLabsWeeklyReportPreference.mDate.setText(
                                (CharSequence)
                                        ((ArrayList) wifiLabsWeeklyReportPreference.mWeeklyDate)
                                                .get(
                                                        wifiLabsWeeklyReportPreference
                                                                .mTimeBarChart
                                                                .mShiftIndex));
                        wifiLabsWeeklyReportPreference.mWifiOnRx.setText("RX");
                        wifiLabsWeeklyReportPreference.mWifiOnRxData.setText(
                                Formatter.formatShortFileSize(
                                        wifiLabsWeeklyReportPreference.getContext(),
                                        wifiLabsWeeklyReportPreference
                                                .mWr
                                                .mDailyRxBytes[
                                                wifiLabsWeeklyReportPreference
                                                        .mTimeBarChart
                                                        .mShiftIndex]));
                        wifiLabsWeeklyReportPreference.mConnectedTx.setText("TX");
                        wifiLabsWeeklyReportPreference.mConnectedTxData.setText(
                                Formatter.formatShortFileSize(
                                        wifiLabsWeeklyReportPreference.getContext(),
                                        wifiLabsWeeklyReportPreference
                                                .mWr
                                                .mDailyTxBytes[
                                                wifiLabsWeeklyReportPreference
                                                        .mTimeBarChart
                                                        .mShiftIndex]));
                        wifiLabsWeeklyReportPreference.mWifiOnPoint.setImageDrawable(
                                wifiLabsWeeklyReportPreference.getPointDrawable$1(
                                        R.color.sec_wifi_labs_weekly_report_time_usage_rx_color));
                        wifiLabsWeeklyReportPreference.mConnectedPoint.setImageDrawable(
                                wifiLabsWeeklyReportPreference.getPointDrawable$1(
                                        R.color.sec_wifi_labs_weekly_report_time_usage_tx_color));
                    }

                    @Override // com.google.android.material.tabs.TabLayout.OnTabSelectedListener
                    public final void onTabUnselected(TabLayout.Tab tab) {}
                });
        this.mDate = (TextView) preferenceViewHolder.findViewById(R.id.weekly_report_date_tv);
        updateData(true);
    }

    public final void updateData(boolean z) {
        if (this.mHolder == null) {
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        if (z || currentTimeMillis - this.mLastUpdateTime >= 5000) {
            this.mLastUpdateTime = currentTimeMillis;
            this.mWr.update();
            Log.i("WifiLabsWeeklyReport.Preference", this.mWr.toString());
            initDate();
            if (this.mTabIndexSelected == 0) {
                this.mTimeBarChart.setVisibility(0);
                this.mUsageBarChart.setVisibility(8);
                this.mWifiOnRxData.setText(
                        getTimeString(this.mWr.mDailyEnabledTime[this.mTimeBarChart.mShiftIndex]));
                this.mConnectedTxData.setText(
                        getTimeString(
                                this.mWr.mDailyConnectionTime[this.mTimeBarChart.mShiftIndex]));
                this.mDate.setText(
                        (CharSequence)
                                ((ArrayList) this.mWeeklyDate).get(this.mTimeBarChart.mShiftIndex));
            } else {
                this.mTimeBarChart.setVisibility(8);
                this.mUsageBarChart.setVisibility(0);
                this.mWifiOnRxData.setText(
                        Formatter.formatShortFileSize(
                                getContext(),
                                this.mWr.mDailyRxBytes[this.mUsageBarChart.mShiftIndex]));
                this.mConnectedTxData.setText(
                        Formatter.formatShortFileSize(
                                getContext(),
                                this.mWr.mDailyTxBytes[this.mUsageBarChart.mShiftIndex]));
                this.mDate.setText(
                        (CharSequence)
                                ((ArrayList) this.mWeeklyDate)
                                        .get(this.mUsageBarChart.mShiftIndex));
            }
            WeeklyTimeUsageBarChart weeklyTimeUsageBarChart = this.mUsageBarChart;
            WeeklyReport weeklyReport = this.mWr;
            weeklyTimeUsageBarChart.updateBarGraph(
                    1, weeklyReport.mDailyTxBytes, weeklyReport.mDailyRxBytes);
            WeeklyTimeUsageBarChart weeklyTimeUsageBarChart2 = this.mTimeBarChart;
            WeeklyReport weeklyReport2 = this.mWr;
            weeklyTimeUsageBarChart2.updateBarGraph(
                    0, weeklyReport2.mDailyConnectionTime, weeklyReport2.mDailyEnabledTime);
            animateProgressBar(this.mWr, this.mTabIndexSelected, z);
        }
    }

    public WifiLabsWeeklyReportPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mWr = new WeeklyReport();
        this.mTabIndexSelected = 0;
        this.mWeeklyDate = new ArrayList();
        this.chartValueSelectedListener = new AnonymousClass2();
        this.mContext = context;
    }

    public WifiLabsWeeklyReportPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mWr = new WeeklyReport();
        this.mTabIndexSelected = 0;
        this.mWeeklyDate = new ArrayList();
        this.chartValueSelectedListener = new AnonymousClass2();
        this.mContext = context;
        initDate();
        setLayoutResource(R.layout.sec_wifi_labs_weekly_report_preference);
    }

    public WifiLabsWeeklyReportPreference(Context context) {
        super(context);
        this.mWr = new WeeklyReport();
        this.mTabIndexSelected = 0;
        this.mWeeklyDate = new ArrayList();
        this.chartValueSelectedListener = new AnonymousClass2();
        this.mContext = context;
    }
}
