package com.samsung.android.settings.lockscreen;

import android.content.Intent;
import android.database.ContentObserver;
import android.icu.util.TimeZone;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Log;
import android.widget.CompoundButton;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.preference.SecPreferenceCategory;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.Utils;
import com.android.settings.datetime.timezone.SpannableUtil;
import com.android.settings.datetime.timezone.TimeZoneInfo;
import com.android.settings.datetime.timezone.TimeZoneSettings;
import com.android.settings.widget.SettingsMainSwitchBar;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.widget.SecUnclickablePreference;

import java.util.Calendar;
import java.util.Date;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class DualClockSettings extends TimeZoneSettings
        implements CompoundButton.OnCheckedChangeListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AnonymousClass1 mAlwaysOnScreenObserver =
            new ContentObserver(
                    new Handler()) { // from class:
                                     // com.samsung.android.settings.lockscreen.DualClockSettings.1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    DualClockSettings dualClockSettings = DualClockSettings.this;
                    int i = DualClockSettings.$r8$clinit;
                    dualClockSettings.updateCheckedUI();
                }
            };
    public SecUnclickablePreference mDescription;
    public SecHomeTimeZonePreference mHomeCityTimeZone;
    public SettingsMainSwitchBar mSwitchBar;

    @Override // com.android.settings.datetime.timezone.TimeZoneSettings,
              // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.datetime.timezone.TimeZoneSettings,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_dualclock_settings;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        SettingsMainSwitchBar settingsMainSwitchBar =
                ((SettingsActivity) getActivity()).mMainSwitch;
        this.mSwitchBar = settingsMainSwitchBar;
        settingsMainSwitchBar.addOnSwitchChangeListener(this);
        this.mSwitchBar.show();
        if (LockUtils.isSupportSubLockscreen()) {
            this.mSwitchBar.requestFocus();
        }
        super.onActivityCreated(bundle);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        Log.secD("DualClockSettings", "onCheckChanged desiredState" + z);
        Settings.System.putInt(getContentResolver(), "dualclock_menu_settings", z ? 1 : 0);
        this.mHomeCityTimeZone.setEnabled(z);
        TimeZoneSettings.setPreferenceCategoryVisible(
                (SecPreferenceCategory) findPreference("time_zone_region_preference_category"), z);
    }

    @Override // com.android.settings.datetime.timezone.TimeZoneSettings,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mIsHomeCity = true;
        if (getActivity().getIntent().getBooleanExtra("direct_lockscren", false)) {
            getActivity()
                    .getWindow()
                    .addFlags(NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
            getActivity().sendBroadcast(new Intent("intent.stop.app-in-app"));
        }
        SecUnclickablePreference secUnclickablePreference =
                (SecUnclickablePreference) findPreference("dualclock_description");
        this.mDescription = secUnclickablePreference;
        if (secUnclickablePreference != null) {
            getPreferenceScreen().removePreference(this.mDescription);
            this.mDescription.mPositionMode = 1;
            getPreferenceScreen().addPreference(this.mDescription);
        }
        this.mHomeCityTimeZone = (SecHomeTimeZonePreference) findPreference("homecity_timezone");
        Log.secD("DualClockSettings", "onCreate");
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.removeOnSwitchChangeListener(this);
            this.mSwitchBar.hide();
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        Log.secD("DualClockSettings", "onPause");
        getContentResolver().unregisterContentObserver(this.mAlwaysOnScreenObserver);
    }

    @Override // com.android.settings.datetime.timezone.TimeZoneSettings,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        Log.secD("DualClockSettings", "onResume");
        getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor("aod_mode"), true, this.mAlwaysOnScreenObserver);
        updateCheckedUI();
        String string = Settings.System.getString(getContentResolver(), "homecity_timezone");
        if (string == null || string.startsWith("Etc/GMT") || string.equals("Etc/UTC")) {
            String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            String salesCode = Utils.getSalesCode();
            String str2 = SystemProperties.get("gsm.sim.state");
            String str3 = SystemProperties.get("gsm.sim.state_1");
            StringBuilder m =
                    SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                            "mSalesCode : ", salesCode, "simState1 : ", str2, "simState2 : ");
            m.append(str3);
            Log.d("DualClockSettings", m.toString());
            if ("READY".contains(str2) || "READY".contains(str3)) {
                String id = Calendar.getInstance().getTimeZone().getID();
                if (!"GMT".equals(id)) {
                    Log.d("DualClockSettings", "hometz is : " + id);
                    Settings.System.putString(getContentResolver(), "homecity_timezone", id);
                    Settings.System.putInt(getContentResolver(), "homecity_from_user", 0);
                }
                string = Settings.System.getString(getContentResolver(), "homecity_timezone");
            }
        }
        DialogFragment$$ExternalSyntheticOutline0.m(
                "updatepreference tzHomeId : ", string, "DualClockSettings");
        CharSequence charSequence = ApnSettings.MVNO_NONE;
        if (string == null) {
            this.mHomeCityTimeZone.setSummary(ApnSettings.MVNO_NONE);
            return;
        }
        SecHomeTimeZonePreference secHomeTimeZonePreference = this.mHomeCityTimeZone;
        TimeZoneInfo format =
                (string.startsWith("Etc/GMT") || string.equals("Etc/UTC"))
                        ? null
                        : new TimeZoneInfo.Formatter(
                                        getResources().getConfiguration().getLocales().get(0),
                                        new Date())
                                .format(TimeZone.getFrozenTimeZone(string));
        if (format != null) {
            boolean z = Rune.SUPPORT_TEXT_REQUEST_TIMEZONE_SHANGHAI_TO_BEIJING_BY_CHINA;
            String str4 = format.mExemplarLocation;
            if (z && str4.equals(getActivity().getString(R.string.city_shanghai))) {
                str4 = getActivity().getString(R.string.city_beijing);
            }
            if (Rune.SUPPORT_TEXT_REQUEST_TIMEZONE_JERUSALEM_TO_TELAVIV_BY_TURKEY
                    && str4.equals(getActivity().getString(R.string.jerusalem))) {
                str4 = getActivity().getString(R.string.tel_aviv);
            }
            charSequence =
                    SpannableUtil.getResourcesText(
                            getActivity().getResources(),
                            R.string.zone_info_exemplar_location_and_offset,
                            str4,
                            format.mGmtOffset);
        }
        secHomeTimeZonePreference.setSummary(charSequence);
    }

    public final void updateCheckedUI() {
        boolean z = Settings.System.getInt(getContentResolver(), "dualclock_menu_settings", 0) != 0;
        this.mSwitchBar.setChecked(z);
        this.mHomeCityTimeZone.setEnabled(z);
        TimeZoneSettings.setPreferenceCategoryVisible(
                (SecPreferenceCategory) findPreference("time_zone_region_preference_category"), z);
        SecPreferenceUtils.applySummaryColor(findPreference("region"), true);
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "dual clock is : ", "DualClockSettings", z);
    }
}
