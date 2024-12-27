package com.android.settings.fuelgauge.batteryusage;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settings.fuelgauge.BatteryUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BatteryHistoryPreference extends Preference {
    public BatteryChartPreferenceController mChartPreferenceController;
    public BatteryChartView mDailyChartView;
    public BatteryChartView mHourlyChartView;

    public BatteryHistoryPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setLayoutResource(R.layout.battery_chart_graph);
        setSelectable(false);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        long currentTimeMillis = System.currentTimeMillis();
        TextView textView = (TextView) preferenceViewHolder.findViewById(R.id.companion_text);
        BatteryChartView batteryChartView =
                (BatteryChartView) preferenceViewHolder.findViewById(R.id.daily_battery_chart);
        this.mDailyChartView = batteryChartView;
        batteryChartView.setCompanionTextView(textView);
        BatteryChartView batteryChartView2 =
                (BatteryChartView) preferenceViewHolder.findViewById(R.id.hourly_battery_chart);
        this.mHourlyChartView = batteryChartView2;
        batteryChartView2.setCompanionTextView(textView);
        BatteryChartPreferenceController batteryChartPreferenceController =
                this.mChartPreferenceController;
        if (batteryChartPreferenceController != null) {
            batteryChartPreferenceController.setBatteryChartView(
                    this.mDailyChartView, this.mHourlyChartView);
        }
        BatteryUtils.logRuntime(currentTimeMillis, "BatteryHistoryPreference", "onBindViewHolder");
    }
}
