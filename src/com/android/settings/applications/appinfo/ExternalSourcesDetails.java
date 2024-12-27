package com.android.settings.applications.appinfo;

import android.app.AppOpsManager;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserManager;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.Settings;
import com.android.settings.applications.AppInfoWithHeader;
import com.android.settings.applications.AppStateInstallAppsBridge;
import com.android.settingslib.RestrictedSwitchPreference;

import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.widget.SecUnclickablePreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ExternalSourcesDetails extends AppInfoWithHeader
        implements Preference.OnPreferenceChangeListener {
    public AppOpsManager mAppOpsManager;
    public SecUnclickablePreference mDescriptionPref;
    public boolean mFromSecuritySettings;
    public AppStateInstallAppsBridge.InstallAppsState mInstallAppsState;
    public final AnonymousClass1 mRampartObserver =
            new ContentObserver(
                    new Handler(
                            Looper
                                    .getMainLooper())) { // from class:
                                                         // com.android.settings.applications.appinfo.ExternalSourcesDetails.1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    super.onChange(z);
                    ExternalSourcesDetails.this.refreshUi();
                }
            };
    public RestrictedSwitchPreference mSwitchPref;
    public UserManager mUserManager;

    @Override // com.android.settings.applications.AppInfoBase
    public final AlertDialog createDialog(int i) {
        return null;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getFragmentTitleResId(Context context) {
        return R.string.install_other_apps;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 808;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return this.mFromSecuritySettings ? "top_level_bio_and_security" : "top_level_apps";
    }

    @Override // com.android.settings.applications.AppInfoBase,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentActivity activity = getActivity();
        this.mAppOpsManager = (AppOpsManager) activity.getSystemService("appops");
        this.mUserManager = UserManager.get(activity);
        addPreferencesFromResource(R.xml.sec_external_sources_details);
        RestrictedSwitchPreference restrictedSwitchPreference =
                (RestrictedSwitchPreference) findPreference("external_sources_settings_switch");
        this.mSwitchPref = restrictedSwitchPreference;
        restrictedSwitchPreference.setOnPreferenceChangeListener(this);
        this.mDescriptionPref =
                (SecUnclickablePreference) findPreference("external_sources_settings_description");
        this.mSwitchPref.seslSetRoundedBg(15);
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.mFromSecuritySettings = arguments.getBoolean("fromSecuritySettings", false);
        }
        SALogging.insertSALog(Integer.toString(39010));
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        Boolean bool = (Boolean) obj;
        boolean booleanValue = bool.booleanValue();
        if (preference != this.mSwitchPref) {
            return false;
        }
        AppInfoWithHeader.insertEventLogging(39010, 3940, this.mPackageName, bool.booleanValue());
        AppStateInstallAppsBridge.InstallAppsState installAppsState = this.mInstallAppsState;
        if (installAppsState == null || booleanValue == installAppsState.canInstallApps()) {
            return true;
        }
        if (Settings.ManageAppExternalSourcesActivity.class
                .getName()
                .equals(getIntent().getComponent().getClassName())) {
            setResult(booleanValue ? -1 : 0);
        }
        this.mAppOpsManager.setMode(
                66, this.mPackageInfo.applicationInfo.uid, this.mPackageName, booleanValue ? 0 : 2);
        refreshUi();
        return true;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        getActivity()
                .getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("rampart_blocked_unknown_apps"),
                        false,
                        this.mRampartObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        getActivity().getContentResolver().unregisterContentObserver(this.mRampartObserver);
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0106  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0111  */
    @Override // com.android.settings.applications.AppInfoBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean refreshUi() {
        /*
            Method dump skipped, instructions count: 325
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.applications.appinfo.ExternalSourcesDetails.refreshUi():boolean");
    }
}
