package com.android.settings.datetime;

import android.app.Dialog;
import android.app.timedetector.TimeDetectorHelper;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.Utils;

import com.google.android.setupcompat.util.WizardManagerHelper;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.csc.CscParser;
import com.samsung.android.settings.general.BaseResetSettingsData;
import com.samsung.android.settings.general.OnResetSettingsDataListener;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DateTimeSettings extends DashboardFragment
        implements TimePreferenceController.TimePreferenceHost,
                DatePreferenceController.DatePreferenceHost {
    public FragmentActivity mActivity;
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.sec_date_time_prefs);
    public static final OnResetSettingsDataListener RESET_SETTINGS_DATA = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.datetime.DateTimeSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseResetSettingsData {
        @Override // com.samsung.android.settings.general.BaseResetSettingsData,
                  // com.samsung.android.settings.general.OnResetSettingsDataListener
        public final void loadCscSettings(Context context, CscParser cscParser) {
            if (cscParser.get("Settings.Main.Phone.DateTimeFormat.TimezoneUpdate") == null) {
                Log.i("DateTimeSettings", "Timezone Update is not found");
                return;
            }
            Log.w("DateTimeSettings", "Settings.Main.Phone.DateTimeFormat.TimezoneUpdate");
            int i = 0;
            if (!cscParser
                    .get("Settings.Main.Phone.DateTimeFormat.TimezoneUpdate")
                    .equalsIgnoreCase("on")) {
                if (cscParser
                        .get("Settings.Main.Phone.DateTimeFormat.TimezoneUpdate")
                        .equalsIgnoreCase("off")) {
                    Settings.Global.putInt(context.getContentResolver(), "auto_time", 0);
                    Settings.Global.putInt(context.getContentResolver(), "auto_time_zone", 0);
                    return;
                }
                return;
            }
            Settings.Global.putInt(context.getContentResolver(), "auto_time", 1);
            ContentResolver contentResolver = context.getContentResolver();
            String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            if (Utils.isWifiOnly(context) && Rune.isChinaModel()) {
                i = 1;
            }
            Settings.Global.putInt(contentResolver, "auto_time_zone", i ^ 1);
        }

        @Override // com.samsung.android.settings.general.OnResetSettingsDataListener
        public final void resetSettings(Context context) {
            Settings.Global.putInt(context.getContentResolver(), "auto_time_zone", 1);
            Settings.Global.putInt(context.getContentResolver(), "auto_time", 1);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SettingsObserver extends ContentObserver {
        public SettingsObserver(Handler handler, FragmentActivity fragmentActivity) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            super.onChange(z);
            DateTimeSettings dateTimeSettings = DateTimeSettings.this;
            BaseSearchIndexProvider baseSearchIndexProvider =
                    DateTimeSettings.SEARCH_INDEX_DATA_PROVIDER;
            boolean z2 =
                    Settings.Global.getInt(
                                    dateTimeSettings.getContentResolver(), "device_provisioned", 0)
                            == 0;
            if (DateTimeSettings.this.getActivity() == null || z2) {
                return;
            }
            AbsAdapter$$ExternalSyntheticOutline0.m(
                    "SettingsObserver onChange getAutoState() return : ",
                    "DateTimeSettings",
                    Settings.Global.getInt(
                                    DateTimeSettings.this.getContentResolver(), "auto_time", 0)
                            == 0);
            DateTimeSettings.this.updatePreferenceStates();
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.DialogCreatable
    public final int getDialogMetricsCategory(int i) {
        if (i != 0) {
            return i != 1 ? 0 : 608;
        }
        return 607;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "DateTimeSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 38;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_date_time_prefs;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        boolean isAnySetupWizard = WizardManagerHelper.isAnySetupWizard(getIntent());
        getSettingsLifecycle().addObserver(new TimeChangeListenerMixin(context, this));
        ((LocationTimeZoneDetectionPreferenceController)
                        use(LocationTimeZoneDetectionPreferenceController.class))
                .setFragment(this);
        ((AutoTimePreferenceController) use(AutoTimePreferenceController.class))
                .setDateAndTimeCallback(this);
        ((DatePreferenceController) use(DatePreferenceController.class)).setHost(this);
        ((TimePreferenceController) use(TimePreferenceController.class)).setHost(this);
        ((AutoTimeZonePreferenceController) use(AutoTimeZonePreferenceController.class))
                .setTimeAndDateCallback(this)
                .setFromSUW(isAnySetupWizard);
        ((TimeFormatPreferenceController) use(TimeFormatPreferenceController.class))
                .setTimeAndDateCallback(this)
                .setFromSUW(isAnySetupWizard);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mActivity = getActivity();
        new SettingsObserver(new Handler(), this.mActivity);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.DialogCreatable
    public final Dialog onCreateDialog(int i) {
        if (i == 0) {
            return ((DatePreferenceController) use(DatePreferenceController.class))
                    .buildDatePicker(getActivity(), TimeDetectorHelper.INSTANCE);
        }
        if (i == 1) {
            return ((TimePreferenceController) use(TimePreferenceController.class))
                    .buildTimePicker(getActivity());
        }
        throw new IllegalArgumentException();
    }
}
