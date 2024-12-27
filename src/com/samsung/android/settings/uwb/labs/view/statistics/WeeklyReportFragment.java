package com.samsung.android.settings.uwb.labs.view.statistics;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

import com.google.android.material.tabs.TabLayout;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WeeklyReportFragment extends Preference {
    public final WeeklyReportFragmentController mFragmentController;

    public WeeklyReportFragment(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        setLayoutResource(R.layout.sec_uwb_weekly_report_preference);
        this.mFragmentController = new WeeklyReportFragmentController(context);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        WeeklyReportFragmentController weeklyReportFragmentController = this.mFragmentController;
        weeklyReportFragmentController.getClass();
        TabLayout tabLayout = (TabLayout) preferenceViewHolder.findViewById(R.id.tab_layout);
        tabLayout.seslSetSubTabStyle();
        weeklyReportFragmentController.mWeeklyTime =
                new WeeklyTimeReport(weeklyReportFragmentController.mContext, preferenceViewHolder);
        weeklyReportFragmentController.mWeeklyUsage =
                new WeeklyUsageReport(
                        weeklyReportFragmentController.mContext, preferenceViewHolder);
        tabLayout.addOnTabSelectedListener$1(
                new TabLayout
                        .OnTabSelectedListener() { // from class:
                                                   // com.samsung.android.settings.uwb.labs.view.statistics.WeeklyReportFragmentController.1
                    public AnonymousClass1() {}

                    @Override // com.google.android.material.tabs.TabLayout.OnTabSelectedListener
                    public final void onTabSelected(TabLayout.Tab tab) {
                        int i = tab.position;
                        WeeklyReportFragmentController weeklyReportFragmentController2 =
                                WeeklyReportFragmentController.this;
                        weeklyReportFragmentController2.mTabIndexSelected = i;
                        weeklyReportFragmentController2.draw();
                    }

                    @Override // com.google.android.material.tabs.TabLayout.OnTabSelectedListener
                    public final void onTabUnselected(TabLayout.Tab tab) {}
                });
        updatePreference();
    }

    public final void updatePreference() {
        if (!this.mFragmentController.mStatisticsControl.mDataManager.getUwbUsage()) {
            Log.i("WeeklyReportFragment", "fail to read database.");
        }
        this.mFragmentController.draw();
        WeeklyTimeReport weeklyTimeReport = this.mFragmentController.mWeeklyTime;
        boolean z = false;
        if (weeklyTimeReport != null) {
            int i = 0;
            for (int i2 = 0; i2 < 7; i2++) {
                i = (int) (i + weeklyTimeReport.mDailyRangingCnt[i2]);
            }
            if (i > 3) {
                z = true;
            }
        }
        setVisible(z);
    }

    public WeeklyReportFragment(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setLayoutResource(R.layout.sec_uwb_weekly_report_preference);
        this.mFragmentController = new WeeklyReportFragmentController(context);
    }

    public WeeklyReportFragment(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setLayoutResource(R.layout.sec_uwb_weekly_report_preference);
        this.mFragmentController = new WeeklyReportFragmentController(context);
        Log.i("WeeklyReportFragment", "CREATE: WeeklyReportFragment");
    }

    public WeeklyReportFragment(Context context) {
        super(context);
        setLayoutResource(R.layout.sec_uwb_weekly_report_preference);
        this.mFragmentController = new WeeklyReportFragmentController(context);
    }
}
