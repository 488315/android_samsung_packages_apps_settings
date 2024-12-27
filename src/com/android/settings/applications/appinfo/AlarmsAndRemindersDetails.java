package com.android.settings.applications.appinfo;

import android.app.AppOpsManager;
import android.content.pm.PackageInfo;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.Settings;
import com.android.settings.applications.AppInfoWithHeader;
import com.android.settings.applications.AppStateAlarmsAndRemindersBridge;
import com.android.settingslib.RestrictedSwitchPreference;
import com.android.settingslib.applications.AppUtils;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;

import com.samsung.android.settings.logging.SALogging;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AlarmsAndRemindersDetails extends AppInfoWithHeader
        implements Preference.OnPreferenceChangeListener {
    public AppOpsManager mAppOpsManager;
    public AppStateAlarmsAndRemindersBridge.AlarmsAndRemindersState mPermissionState;
    public RestrictedSwitchPreference mSwitchPref;
    public volatile Boolean mUncommittedState;

    @Override // com.android.settings.applications.AppInfoBase
    public final AlertDialog createDialog(int i) {
        return null;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1869;
    }

    @Override // com.android.settings.applications.AppInfoBase,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mAppOpsManager = (AppOpsManager) getActivity().getSystemService(AppOpsManager.class);
        if (bundle != null) {
            this.mUncommittedState = (Boolean) bundle.get("uncommitted_state");
            if (this.mUncommittedState != null
                    && Settings.AlarmsAndRemindersAppActivity.class
                            .getName()
                            .equals(getIntent().getComponent().getClassName())) {
                setResult(this.mUncommittedState.booleanValue() ? -1 : 0);
            }
        }
        addPreferencesFromResource(R.xml.sec_alarms_and_reminders);
        RestrictedSwitchPreference restrictedSwitchPreference =
                (RestrictedSwitchPreference) findPreference("alarms_and_reminders_switch");
        this.mSwitchPref = restrictedSwitchPreference;
        restrictedSwitchPreference.setOnPreferenceChangeListener(this);
        this.mSwitchPref.seslSetRoundedBg(15);
        SALogging.insertSALog(Integer.toString(39011));
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        if (getActivity().isChangingConfigurations()
                || this.mPermissionState == null
                || this.mUncommittedState == null
                || this.mUncommittedState.booleanValue() == this.mPermissionState.isAllowed()) {
            return;
        }
        boolean booleanValue = this.mUncommittedState.booleanValue();
        this.mAppOpsManager.setUidMode(
                "android:schedule_exact_alarm",
                this.mPackageInfo.applicationInfo.uid,
                booleanValue ? 0 : 2);
        boolean booleanValue2 = this.mUncommittedState.booleanValue();
        String str = this.mPackageName;
        MetricsFeatureProvider metricsFeatureProvider = this.mMetricsFeatureProvider;
        FragmentActivity activity = getActivity();
        metricsFeatureProvider.getClass();
        metricsFeatureProvider.action(
                MetricsFeatureProvider.getAttribution(activity),
                1752,
                1869,
                booleanValue2 ? 1 : 0,
                str);
        this.mUncommittedState = null;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (preference != this.mSwitchPref) {
            return false;
        }
        Boolean bool = (Boolean) obj;
        this.mUncommittedState = bool;
        if (Settings.AlarmsAndRemindersAppActivity.class
                .getName()
                .equals(getIntent().getComponent().getClassName())) {
            setResult(this.mUncommittedState.booleanValue() ? -1 : 0);
        }
        refreshUi();
        AppInfoWithHeader.insertEventLogging(39011, 3941, this.mPackageName, bool.booleanValue());
        return true;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (this.mUncommittedState != null) {
            bundle.putObject("uncommitted_state", this.mUncommittedState);
        }
    }

    @Override // com.android.settings.applications.AppInfoBase
    public final boolean refreshUi() {
        PackageInfo packageInfo = this.mPackageInfo;
        if (packageInfo == null || packageInfo.applicationInfo == null) {
            return false;
        }
        this.mPermissionState =
                new AppStateAlarmsAndRemindersBridge(getActivity(), null, null)
                        .createPermissionState(
                                this.mPackageInfo.applicationInfo.uid, this.mPackageName);
        if (AppUtils.isSystemUidApp(this.mAppEntry.info.uid)) {
            this.mSwitchPref.setChecked(true);
            this.mSwitchPref.setEnabled(false);
            return true;
        }
        this.mSwitchPref.setEnabled(this.mPermissionState.shouldBeVisible());
        this.mSwitchPref.setChecked(
                this.mUncommittedState != null
                        ? this.mUncommittedState.booleanValue()
                        : this.mPermissionState.isAllowed());
        return true;
    }
}
