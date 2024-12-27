package com.samsung.android.settings.uwb.labs.view.statistics;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.picker.widget.SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settings.datetime.timezone.TimeZoneInfoPreferenceController$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.server.uwb.bigdata.db.UwbDbReader;
import com.samsung.android.settings.uwb.labs.common.Month;
import com.samsung.android.settings.uwb.labs.common.UwbLabsUtils;
import com.samsung.android.settings.uwb.labs.control.statistics.StatisticsSummaryController;
import com.samsung.android.settings.uwb.labs.data.StatisticsDataManager;

import java.util.ArrayList;
import java.util.Calendar;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class StatisticsSummaryFragment extends Preference {
    public final StatisticsSummaryFragmentController mFragmentController;

    public StatisticsSummaryFragment(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        setLayoutResource(R.layout.sec_uwb_labs_statistics_summary_preference);
        this.mFragmentController = new StatisticsSummaryFragmentController(context);
        Log.i("StatisticsSummaryFragment", "CREATE: StatisticsSummaryFragment");
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        this.mFragmentController.mHolder = preferenceViewHolder;
        updatePreference();
    }

    public final void updatePreference() {
        boolean z;
        TextView textView;
        String str;
        StatisticsDataManager statisticsDataManager =
                this.mFragmentController.mStatisticsControl.mDataManager;
        UwbDbReader uwbDbReader = statisticsDataManager.mUwbDbReader;
        uwbDbReader.getClass();
        ArrayList arrayList = null;
        try {
            Cursor rawQuery = uwbDbReader.mDb.rawQuery("SELECT * FROM uwb_state", null);
            if (rawQuery == null) {
                Log.e("UwbDbReader", "cursor is NULL");
            } else if (rawQuery.moveToFirst()) {
                ArrayList arrayList2 = new ArrayList();
                do {
                    arrayList2.add(
                            new UwbDbReader.UwbDbStateData(
                                    rawQuery.getInt(2), rawQuery.getLong(1)));
                } while (rawQuery.moveToNext());
                rawQuery.close();
                arrayList = arrayList2;
            } else {
                rawQuery.close();
            }
        } catch (SQLiteException e) {
            Log.e("UwbDbReader", "Error reading uwb.db file using SQLite3", e);
        }
        if (arrayList == null) {
            z = false;
        } else {
            UwbDbReader.UwbDbStateData uwbDbStateData =
                    (UwbDbReader.UwbDbStateData) arrayList.get(arrayList.size() - 1);
            StatisticsDataManager.mUwbState = uwbDbStateData.mUwbState;
            StatisticsDataManager.mStateChangedTime = uwbDbStateData.mStateChangedTime;
            z = true;
        }
        if (!(statisticsDataManager.getUwbUsage() & z)) {
            Log.i("StatisticsSummaryFragment", "fail to read database.");
        }
        StatisticsSummaryFragmentController statisticsSummaryFragmentController =
                this.mFragmentController;
        if (statisticsSummaryFragmentController.mHolder == null) {
            return;
        }
        StatisticsSummaryController statisticsSummaryController =
                statisticsSummaryFragmentController.mStatisticsControl;
        int data = (int) statisticsSummaryController.getData(0, 0);
        PreferenceViewHolder preferenceViewHolder = statisticsSummaryFragmentController.mHolder;
        View view = preferenceViewHolder.itemView;
        ImageView imageView = (ImageView) preferenceViewHolder.findViewById(R.id.clock_icon);
        ImageView imageView2 =
                (ImageView)
                        statisticsSummaryFragmentController.mHolder.findViewById(
                                R.id.connected_clock_icon);
        view.setBackgroundColor(
                statisticsSummaryFragmentController.mContext.getColor(
                        R.color.sec_uwb_labs_background_1));
        imageView.setImageResource(R.drawable.sec_uwb_labs_clock);
        imageView2.setImageResource(R.drawable.sec_uwb_labs_clock);
        statisticsSummaryFragmentController.startAnimation(data);
        TextView textView2 =
                (TextView)
                        statisticsSummaryFragmentController.mHolder.findViewById(
                                R.id.calendar_date);
        Calendar calendar = Calendar.getInstance();
        textView2.setText(
                Month.values()[calendar.get(2)].getName()
                        + " "
                        + calendar.get(5)
                        + UwbLabsUtils.toDateFormat(calendar.get(5)));
        TextView textView3 =
                (TextView)
                        statisticsSummaryFragmentController.mHolder.findViewById(
                                R.id.today_usage_1);
        TextView textView4 =
                (TextView)
                        statisticsSummaryFragmentController.mHolder.findViewById(
                                R.id.today_usage_2);
        TextView textView5 =
                (TextView)
                        statisticsSummaryFragmentController.mHolder.findViewById(
                                R.id.today_usage_3);
        TextView textView6 =
                (TextView)
                        statisticsSummaryFragmentController.mHolder.findViewById(
                                R.id.today_usage_4);
        long hours = UwbLabsUtils.getHours(statisticsSummaryController.getData(2, 0));
        long minutes = UwbLabsUtils.getMinutes(statisticsSummaryController.getData(2, 0));
        textView3.setText(Long.toString(hours));
        textView4.setText(R.string.uwb_distance_unit_h);
        if (minutes == 0) {
            textView5.setText(ApnSettings.MVNO_NONE);
            textView6.setText(ApnSettings.MVNO_NONE);
        } else {
            textView5.setText(Long.toString(minutes));
            textView6.setText(
                    statisticsSummaryFragmentController.mContext.getString(
                            R.string.uwb_distance_unit_m));
        }
        TextView textView7 =
                (TextView)
                        statisticsSummaryFragmentController.mHolder.findViewById(
                                R.id.daily_average_time);
        long data2 = statisticsSummaryController.getData(4, 0);
        if (data2 == 0) {
            textView = textView7;
        } else {
            textView = textView7;
            data2 = ((data2 / 60) / 1000) / 7;
        }
        long j = data2 / 60;
        long data3 = statisticsSummaryController.getData(4, 0);
        if (data3 != 0) {
            data3 = ((data3 / 60) / 1000) / 7;
        }
        long j2 = data3 % 60;
        if (j == 0) {
            StringBuilder sb = new StringBuilder(ApnSettings.MVNO_NONE);
            sb.append(j2);
            str =
                    SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0
                            .m(
                                    statisticsSummaryFragmentController.mContext,
                                    R.string.uwb_distance_unit_m,
                                    sb);
        } else {
            str =
                    ApnSettings.MVNO_NONE
                            + j
                            + statisticsSummaryFragmentController.mContext.getString(
                                    R.string.uwb_distance_unit_h)
                            + " ";
            if (j2 != 0) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str);
                sb2.append(j2);
                str =
                        SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0
                                .m(
                                        statisticsSummaryFragmentController.mContext,
                                        R.string.uwb_distance_unit_m,
                                        sb2);
            }
        }
        textView.setText(str);
        TextView textView8 =
                (TextView)
                        statisticsSummaryFragmentController.mHolder.findViewById(
                                R.id.compared_to_yesterday_diff_time);
        TextView textView9 =
                (TextView)
                        statisticsSummaryFragmentController.mHolder.findViewById(
                                R.id.compared_to_yesterday_diff_text);
        long data4 =
                statisticsSummaryController.getData(2, 0)
                        - statisticsSummaryController.getData(3, 0);
        if (data4 < 0) {
            data4 *= -1;
            textView9.setText(" less than yesterday");
        } else {
            textView9.setText(" more than yesterday");
        }
        long hours2 = UwbLabsUtils.getHours(data4);
        long minutes2 = UwbLabsUtils.getMinutes(data4);
        if (hours2 == 0) {
            textView8.setText(
                    Long.toString(minutes2)
                            + statisticsSummaryFragmentController.mContext.getString(
                                    R.string.uwb_distance_unit_m));
        } else if (minutes2 == 0) {
            textView8.setText(
                    Long.toString(hours2)
                            + statisticsSummaryFragmentController.mContext.getString(
                                    R.string.uwb_distance_unit_h));
        } else {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(Long.toString(hours2));
            TimeZoneInfoPreferenceController$$ExternalSyntheticOutline0.m(
                    statisticsSummaryFragmentController.mContext,
                    R.string.uwb_distance_unit_h,
                    sb3,
                    " ");
            sb3.append(Long.toString(minutes2));
            sb3.append(
                    statisticsSummaryFragmentController.mContext.getString(
                            R.string.uwb_distance_unit_m));
            textView8.setText(sb3.toString());
        }
        TextView textView10 =
                (TextView)
                        statisticsSummaryFragmentController.mHolder.findViewById(
                                R.id.connected_state_enter_time);
        TextView textView11 =
                (TextView)
                        statisticsSummaryFragmentController.mHolder.findViewById(
                                R.id.connected_state_enter_time_dark);
        LinearLayout linearLayout =
                (LinearLayout)
                        statisticsSummaryFragmentController.mHolder.findViewById(
                                R.id.current_state_time);
        LinearLayout linearLayout2 =
                (LinearLayout)
                        statisticsSummaryFragmentController.mHolder.findViewById(
                                R.id.connected_state);
        LinearLayout linearLayout3 =
                (LinearLayout)
                        statisticsSummaryFragmentController.mHolder.findViewById(
                                R.id.connected_state_dark);
        long data5 = statisticsSummaryController.getData(1, 0);
        if (data != 1) {
            linearLayout2.setVisibility(8);
            linearLayout3.setVisibility(8);
            TextView textView12 =
                    (TextView)
                            statisticsSummaryFragmentController.mHolder.findViewById(
                                    R.id.current_state_text);
            TextView textView13 =
                    (TextView)
                            statisticsSummaryFragmentController.mHolder.findViewById(
                                    R.id.current_state_enter_time);
            if (data == 1) {
                textView12.setText("UWB enabled at ");
            } else {
                textView12.setText("UWB disabled at ");
            }
            textView13.setText(
                    UwbLabsUtils.getReportFormat(statisticsSummaryController.getData(1, 0)));
            return;
        }
        linearLayout.setVisibility(8);
        String reportFormat = UwbLabsUtils.getReportFormat(data5);
        if (UwbLabsUtils.mDarkMode) {
            linearLayout2.setVisibility(8);
            linearLayout3.setVisibility(0);
            textView11.setText(reportFormat);
        } else {
            linearLayout3.setVisibility(8);
            linearLayout2.setVisibility(0);
            textView10.setText(reportFormat);
        }
    }

    public StatisticsSummaryFragment(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setLayoutResource(R.layout.sec_uwb_labs_statistics_summary_preference);
        this.mFragmentController = new StatisticsSummaryFragmentController(context);
        Log.i("StatisticsSummaryFragment", "CREATE: StatisticsSummaryFragment");
    }

    public StatisticsSummaryFragment(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setLayoutResource(R.layout.sec_uwb_labs_statistics_summary_preference);
        this.mFragmentController = new StatisticsSummaryFragmentController(context);
        Log.i("StatisticsSummaryFragment", "CREATE: StatisticsSummaryFragment");
    }

    public StatisticsSummaryFragment(Context context) {
        super(context);
        setLayoutResource(R.layout.sec_uwb_labs_statistics_summary_preference);
        this.mFragmentController = new StatisticsSummaryFragmentController(context);
        Log.i("StatisticsSummaryFragment", "CREATE: StatisticsSummaryFragment");
    }
}
