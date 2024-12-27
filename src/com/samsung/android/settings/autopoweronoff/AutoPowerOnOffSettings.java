package com.samsung.android.settings.autopoweronoff;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.util.secutil.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.picker.app.SeslTimePickerDialog;
import androidx.picker.widget.SeslTimePicker;
import androidx.preference.Preference;
import androidx.preference.SecPreference;
import androidx.preference.SecPreferenceUtils;
import androidx.preference.SecSwitchPreference;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.system.ResetDashboardFragment;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.license.KnoxEnterpriseLicenseManager;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.settings.csc.CscParser;
import com.samsung.android.settings.general.AutoPowerOnOffPreferenceController;
import com.samsung.android.settings.general.BaseResetSettingsData;
import com.samsung.android.settings.general.OnResetSettingsDataListener;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AutoPowerOnOffSettings extends SettingsPreferenceFragment
        implements Preference.OnPreferenceChangeListener, SeslTimePickerDialog.OnTimeSetListener {
    public AutoPowerOnOffDaysPreference mAutoPowerOffDays;
    public SecSwitchPreference mAutoPowerOffSettings;
    public SecPreference mAutoPowerOffTime;
    public AutoPowerOnOffDaysPreference mAutoPowerOnDays;
    public SecSwitchPreference mAutoPowerOnSettings;
    public SecPreference mAutoPowerOnTime;
    public FragmentActivity mContext;
    public int mCurrentDialogId;
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass1();
    public static final OnResetSettingsDataListener RESET_SETTINGS_DATA = new AnonymousClass2();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.autopoweronoff.AutoPowerOnOffSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            ((ArrayList) nonIndexableKeys).add("sec_auto_power_on_off_description");
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.className = AutoPowerOnOffSettings.class.getName();
            searchIndexableResource.xmlResId = R.xml.sec_auto_power_on_off;
            return Arrays.asList(searchIndexableResource);
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return AutoPowerOnOffPreferenceController.isSupportAutoPowerOnOff();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DaysOfWeek {
        public static final int[] DAY_MAP = {0, 1, 2, 3, 4, 5, 6, 7};
        public int mDays;

        public DaysOfWeek(int i) {
            this.mDays = i;
        }
    }

    public static void registerAutoPowerOffAlarm(Context context) {
        Intent intent = new Intent("com.samsung.settings.action.SET_AUTO_POWER_OFF");
        intent.setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
        intent.putExtra("power_off_reg", true);
        intent.putExtra("by_user", true);
        context.sendBroadcast(intent);
    }

    public static void registerAutoPowerOnAlarm(Context context) {
        Intent intent = new Intent("com.samsung.settings.action.SET_AUTO_POWER_ON");
        intent.setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
        intent.putExtra("power_on_reg", true);
        intent.putExtra("by_user", true);
        context.sendBroadcast(intent);
    }

    public static String timeFormatToString(FragmentActivity fragmentActivity, int i, int i2) {
        Time time = new Time();
        time.hour = i;
        time.minute = i2;
        return DateUtils.formatDateTime(
                fragmentActivity,
                time.normalize(true),
                DateFormat.is24HourFormat(fragmentActivity) ? 641 : FileType.SSF);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return ResetDashboardFragment.class.getName();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 900;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getActivity();
        addPreferencesFromResource(R.xml.sec_auto_power_on_off);
        this.mAutoPowerOnSettings = (SecSwitchPreference) findPreference("auto_power_on_switch");
        this.mAutoPowerOnTime = (SecPreference) findPreference("auto_power_on_time");
        this.mAutoPowerOnDays = (AutoPowerOnOffDaysPreference) findPreference("auto_power_on_days");
        this.mAutoPowerOffSettings = (SecSwitchPreference) findPreference("auto_power_off_switch");
        this.mAutoPowerOffTime = (SecPreference) findPreference("auto_power_off_time");
        this.mAutoPowerOffDays =
                (AutoPowerOnOffDaysPreference) findPreference("auto_power_off_days");
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.DialogCreatable
    public final Dialog onCreateDialog(int i) {
        if (i == 1) {
            int i2 =
                    Settings.System.getInt(
                            this.mContext.getContentResolver(),
                            "auto_power_on_time",
                            KnoxEnterpriseLicenseManager.ERROR_LICENSE_DEACTIVATED);
            SeslTimePickerDialog seslTimePickerDialog =
                    new SeslTimePickerDialog(
                            getActivity(),
                            this,
                            i2 / 100,
                            i2 % 100,
                            DateFormat.is24HourFormat(getActivity()));
            seslTimePickerDialog.getWindow().setSoftInputMode(32);
            return seslTimePickerDialog;
        }
        if (i != 2) {
            return super.onCreateDialog(i);
        }
        int i3 =
                Settings.System.getInt(
                        this.mContext.getContentResolver(), "auto_power_off_time", 2300);
        SeslTimePickerDialog seslTimePickerDialog2 =
                new SeslTimePickerDialog(
                        getActivity(),
                        this,
                        i3 / 100,
                        i3 % 100,
                        DateFormat.is24HourFormat(getActivity()));
        seslTimePickerDialog2.getWindow().setSoftInputMode(32);
        return seslTimePickerDialog2;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        SecSwitchPreference secSwitchPreference = this.mAutoPowerOnSettings;
        if (secSwitchPreference != null) {
            secSwitchPreference.setOnPreferenceChangeListener(null);
        }
        SecSwitchPreference secSwitchPreference2 = this.mAutoPowerOffSettings;
        if (secSwitchPreference2 != null) {
            secSwitchPreference2.setOnPreferenceChangeListener(null);
        }
        AutoPowerOnOffDaysPreference autoPowerOnOffDaysPreference = this.mAutoPowerOnDays;
        if (autoPowerOnOffDaysPreference != null) {
            autoPowerOnOffDaysPreference.setOnPreferenceChangeListener(null);
        }
        AutoPowerOnOffDaysPreference autoPowerOnOffDaysPreference2 = this.mAutoPowerOffDays;
        if (autoPowerOnOffDaysPreference2 != null) {
            autoPowerOnOffDaysPreference2.setOnPreferenceChangeListener(null);
        }
        super.onPause();
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (preference.equals(this.mAutoPowerOnSettings)) {
            boolean booleanValue = ((Boolean) obj).booleanValue();
            Log.secD("AutoPowerOnOffSettings", "onPreferenceChange : mAutoPowerOnSettings");
            Settings.System.putInt(
                    this.mContext.getContentResolver(),
                    "auto_power_on_settings",
                    booleanValue ? 1 : 0);
            if (booleanValue) {
                registerAutoPowerOnAlarm(this.mContext);
            } else {
                FragmentActivity fragmentActivity = this.mContext;
                Intent intent = new Intent("com.samsung.settings.action.SET_AUTO_POWER_ON");
                intent.setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
                intent.putExtra("power_on_reg", false);
                fragmentActivity.sendBroadcast(intent);
            }
            LoggingHelper.insertEventLogging(900, 9001, booleanValue ? 1L : 0L);
            return true;
        }
        if (preference.equals(this.mAutoPowerOffSettings)) {
            boolean booleanValue2 = ((Boolean) obj).booleanValue();
            Log.secD("AutoPowerOnOffSettings", "onPreferenceChange : mAutoPowerOffSettings");
            Settings.System.putInt(
                    this.mContext.getContentResolver(),
                    "auto_power_off_settings",
                    booleanValue2 ? 1 : 0);
            if (booleanValue2) {
                registerAutoPowerOffAlarm(this.mContext);
            } else {
                FragmentActivity fragmentActivity2 = this.mContext;
                Intent intent2 = new Intent("com.samsung.settings.action.SET_AUTO_POWER_OFF");
                intent2.setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
                intent2.putExtra("power_off_reg", false);
                fragmentActivity2.sendBroadcast(intent2);
            }
            LoggingHelper.insertEventLogging(900, 9002, booleanValue2 ? 1L : 0L);
            return true;
        }
        if (preference.equals(this.mAutoPowerOnDays)) {
            DaysOfWeek daysOfWeek = (DaysOfWeek) obj;
            Log.secD(
                    "AutoPowerOnOffSettings",
                    "onPreferenceChange : mAutoPowerOnDays, value.getCoded() = "
                            + daysOfWeek.mDays);
            Settings.System.putInt(
                    this.mContext.getContentResolver(),
                    "auto_power_on_repeat_days",
                    daysOfWeek.mDays);
            registerAutoPowerOnAlarm(this.mContext);
            return true;
        }
        if (!preference.equals(this.mAutoPowerOffDays)) {
            return false;
        }
        DaysOfWeek daysOfWeek2 = (DaysOfWeek) obj;
        Log.secD(
                "AutoPowerOnOffSettings",
                "onPreferenceChange : mAutoPowerOffDays, value.getCoded() = " + daysOfWeek2.mDays);
        Settings.System.putInt(
                this.mContext.getContentResolver(),
                "auto_power_off_repeat_days",
                daysOfWeek2.mDays);
        registerAutoPowerOffAlarm(this.mContext);
        return true;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        if (preference.equals(this.mAutoPowerOnTime)) {
            Log.secD("AutoPowerOnOffSettings", "Show dialog : KEY_AUTO_POWER_ON_TIME");
            this.mCurrentDialogId = 1;
            removeDialog(1);
            showDialog(1);
        } else if (preference.equals(this.mAutoPowerOffTime)) {
            Log.secD("AutoPowerOnOffSettings", "Show dialog : KEY_AUTO_POWER_OFF_TIME");
            this.mCurrentDialogId = 2;
            removeDialog(2);
            showDialog(2);
        }
        return super.onPreferenceTreeClick(preference);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        boolean z =
                Settings.System.getInt(
                                this.mContext.getContentResolver(), "auto_power_on_settings", 0)
                        > 0;
        boolean z2 =
                Settings.System.getInt(
                                this.mContext.getContentResolver(), "auto_power_off_settings", 0)
                        > 0;
        int i =
                Settings.System.getInt(
                        this.mContext.getContentResolver(),
                        "auto_power_on_time",
                        KnoxEnterpriseLicenseManager.ERROR_LICENSE_DEACTIVATED);
        DaysOfWeek daysOfWeek =
                new DaysOfWeek(
                        Settings.System.getInt(
                                this.mContext.getContentResolver(),
                                "auto_power_on_repeat_days",
                                124));
        int i2 =
                Settings.System.getInt(
                        this.mContext.getContentResolver(), "auto_power_off_time", 2300);
        DaysOfWeek daysOfWeek2 =
                new DaysOfWeek(
                        Settings.System.getInt(
                                this.mContext.getContentResolver(),
                                "auto_power_off_repeat_days",
                                124));
        SecSwitchPreference secSwitchPreference = this.mAutoPowerOnSettings;
        if (secSwitchPreference != null) {
            secSwitchPreference.setChecked(z);
        }
        SecSwitchPreference secSwitchPreference2 = this.mAutoPowerOffSettings;
        if (secSwitchPreference2 != null) {
            secSwitchPreference2.setChecked(z2);
        }
        SecPreference secPreference = this.mAutoPowerOnTime;
        if (secPreference != null) {
            SecPreferenceUtils.applySummaryColor(secPreference, true);
            this.mAutoPowerOnTime.setSummary(timeFormatToString(this.mContext, i / 100, i % 100));
        }
        SecPreference secPreference2 = this.mAutoPowerOffTime;
        if (secPreference2 != null) {
            SecPreferenceUtils.applySummaryColor(secPreference2, true);
            this.mAutoPowerOffTime.setSummary(
                    timeFormatToString(this.mContext, i2 / 100, i2 % 100));
        }
        AutoPowerOnOffDaysPreference autoPowerOnOffDaysPreference = this.mAutoPowerOnDays;
        if (autoPowerOnOffDaysPreference != null) {
            SecPreferenceUtils.applySummaryColor(autoPowerOnOffDaysPreference, true);
            AutoPowerOnOffDaysPreference autoPowerOnOffDaysPreference2 = this.mAutoPowerOnDays;
            DaysOfWeek daysOfWeek3 = autoPowerOnOffDaysPreference2.mDaysOfWeek;
            daysOfWeek3.getClass();
            daysOfWeek3.mDays = daysOfWeek.mDays;
            autoPowerOnOffDaysPreference2.updateSummary(daysOfWeek);
        }
        AutoPowerOnOffDaysPreference autoPowerOnOffDaysPreference3 = this.mAutoPowerOffDays;
        if (autoPowerOnOffDaysPreference3 != null) {
            SecPreferenceUtils.applySummaryColor(autoPowerOnOffDaysPreference3, true);
            AutoPowerOnOffDaysPreference autoPowerOnOffDaysPreference4 = this.mAutoPowerOffDays;
            DaysOfWeek daysOfWeek4 = autoPowerOnOffDaysPreference4.mDaysOfWeek;
            daysOfWeek4.getClass();
            daysOfWeek4.mDays = daysOfWeek2.mDays;
            autoPowerOnOffDaysPreference4.updateSummary(daysOfWeek2);
        }
        SecSwitchPreference secSwitchPreference3 = this.mAutoPowerOnSettings;
        if (secSwitchPreference3 != null) {
            secSwitchPreference3.setOnPreferenceChangeListener(this);
        }
        SecSwitchPreference secSwitchPreference4 = this.mAutoPowerOffSettings;
        if (secSwitchPreference4 != null) {
            secSwitchPreference4.setOnPreferenceChangeListener(this);
        }
        AutoPowerOnOffDaysPreference autoPowerOnOffDaysPreference5 = this.mAutoPowerOnDays;
        if (autoPowerOnOffDaysPreference5 != null) {
            autoPowerOnOffDaysPreference5.setOnPreferenceChangeListener(this);
        }
        AutoPowerOnOffDaysPreference autoPowerOnOffDaysPreference6 = this.mAutoPowerOffDays;
        if (autoPowerOnOffDaysPreference6 != null) {
            autoPowerOnOffDaysPreference6.setOnPreferenceChangeListener(this);
        }
    }

    @Override // androidx.picker.app.SeslTimePickerDialog.OnTimeSetListener
    public final void onTimeSet(SeslTimePicker seslTimePicker, int i, int i2) {
        int i3 = this.mCurrentDialogId;
        if (i3 == 1) {
            SecPreference secPreference = this.mAutoPowerOnTime;
            if (secPreference != null) {
                secPreference.setSummary(timeFormatToString(this.mContext, i, i2));
            }
            Settings.System.putInt(
                    this.mContext.getContentResolver(), "auto_power_on_time", (i * 100) + i2);
            registerAutoPowerOnAlarm(this.mContext);
            return;
        }
        if (i3 == 2) {
            SecPreference secPreference2 = this.mAutoPowerOffTime;
            if (secPreference2 != null) {
                secPreference2.setSummary(timeFormatToString(this.mContext, i, i2));
            }
            Settings.System.putInt(
                    this.mContext.getContentResolver(), "auto_power_off_time", (i * 100) + i2);
            registerAutoPowerOffAlarm(this.mContext);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.autopoweronoff.AutoPowerOnOffSettings$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseResetSettingsData {
        @Override // com.samsung.android.settings.general.OnResetSettingsDataListener
        public final void resetSettings(Context context) {
            if (Rune.isChinaModel()
                    && SemFloatingFeature.getInstance()
                            .getBoolean(
                                    "SEC_FLOATING_FEATURE_SETTINGS_SUPPORT_AUTO_POWER_ON_OFF")) {
                Settings.System.putInt(context.getContentResolver(), "auto_power_on_settings", 0);
                Settings.System.putInt(
                        context.getContentResolver(),
                        "auto_power_on_time",
                        KnoxEnterpriseLicenseManager.ERROR_LICENSE_DEACTIVATED);
                Settings.System.putInt(
                        context.getContentResolver(), "auto_power_on_repeat_days", 124);
                Settings.System.putInt(context.getContentResolver(), "auto_power_off_settings", 0);
                Settings.System.putInt(context.getContentResolver(), "auto_power_off_time", 2300);
                Settings.System.putInt(
                        context.getContentResolver(), "auto_power_off_repeat_days", 124);
            }
        }

        @Override // com.samsung.android.settings.general.BaseResetSettingsData,
                  // com.samsung.android.settings.general.OnResetSettingsDataListener
        public final void loadCscSettings(Context context, CscParser cscParser) {}
    }
}
