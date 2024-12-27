package com.android.settings.display;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.hardware.display.ColorDisplayManager;
import android.hardware.display.NightDisplayListener;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;

import com.sec.ims.volte2.data.VolteConstants;

import java.time.LocalTime;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NightDisplaySettings extends DashboardFragment
        implements NightDisplayListener.Callback {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.night_display_settings);
    public ColorDisplayManager mColorDisplayManager;
    public NightDisplayListener mNightDisplayListener;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.display.NightDisplaySettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return ColorDisplayManager.isNightDisplayAvailable(context);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.DialogCreatable
    public final int getDialogMetricsCategory(int i) {
        if (i != 0) {
            return i != 1 ? 0 : 589;
        }
        return 588;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final String getLogTag() {
        return "NightDisplaySettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return VolteConstants.ErrorCode.NOT_ACCEPTABLE_HERE;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.night_display_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Context context = getContext();
        this.mColorDisplayManager =
                (ColorDisplayManager) context.getSystemService(ColorDisplayManager.class);
        this.mNightDisplayListener = new NightDisplayListener(context);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.DialogCreatable
    public final Dialog onCreateDialog(final int i) {
        if (i != 0 && i != 1) {
            return super.onCreateDialog(i);
        }
        LocalTime nightDisplayCustomStartTime =
                i == 0
                        ? this.mColorDisplayManager.getNightDisplayCustomStartTime()
                        : this.mColorDisplayManager.getNightDisplayCustomEndTime();
        Context context = getContext();
        return new TimePickerDialog(
                context,
                new TimePickerDialog
                        .OnTimeSetListener() { // from class:
                                               // com.android.settings.display.NightDisplaySettings$$ExternalSyntheticLambda0
                    @Override // android.app.TimePickerDialog.OnTimeSetListener
                    public final void onTimeSet(TimePicker timePicker, int i2, int i3) {
                        NightDisplaySettings nightDisplaySettings = NightDisplaySettings.this;
                        int i4 = i;
                        BaseSearchIndexProvider baseSearchIndexProvider =
                                NightDisplaySettings.SEARCH_INDEX_DATA_PROVIDER;
                        nightDisplaySettings.getClass();
                        LocalTime of = LocalTime.of(i2, i3);
                        if (i4 == 0) {
                            nightDisplaySettings.mColorDisplayManager
                                    .setNightDisplayCustomStartTime(of);
                        } else {
                            nightDisplaySettings.mColorDisplayManager.setNightDisplayCustomEndTime(
                                    of);
                        }
                    }
                },
                nightDisplayCustomStartTime.getHour(),
                nightDisplayCustomStartTime.getMinute(),
                DateFormat.is24HourFormat(context));
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        if ("night_display_end_time".equals(preference.getKey())) {
            writePreferenceClickMetric(preference);
            showDialog(1);
            return true;
        }
        if (!"night_display_start_time".equals(preference.getKey())) {
            return super.onPreferenceTreeClick(preference);
        }
        writePreferenceClickMetric(preference);
        showDialog(0);
        return true;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        this.mNightDisplayListener.setCallback(this);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        this.mNightDisplayListener.setCallback((NightDisplayListener.Callback) null);
    }
}
