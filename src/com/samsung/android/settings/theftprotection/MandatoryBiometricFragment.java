package com.samsung.android.settings.theftprotection;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.appcompat.widget.SeslToggleSwitch;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.widget.SettingsMainSwitchBar;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.samsung.android.settings.theftprotection.location.LocationStorage;
import com.samsung.android.settings.theftprotection.logging.Log;
import com.samsung.android.settings.theftprotection.utils.NecessaryElementChecker;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class MandatoryBiometricFragment extends DashboardFragment
        implements SeslToggleSwitch.OnBeforeCheckedChangeListener {
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass2();
    public Context mContext;
    public final AnonymousClass1 mObserver =
            new ContentObserver(
                    new Handler(
                            Looper
                                    .getMainLooper())) { // from class:
                                                         // com.samsung.android.settings.theftprotection.MandatoryBiometricFragment.1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    MandatoryBiometricFragment mandatoryBiometricFragment =
                            MandatoryBiometricFragment.this;
                    StatusLogger$StatusLoggingProvider statusLogger$StatusLoggingProvider =
                            MandatoryBiometricFragment.STATUS_LOGGING_PROVIDER;
                    mandatoryBiometricFragment.updatePreferenceStates();
                }
            };
    public SettingsMainSwitchBar mSwitchBar;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.theftprotection.MandatoryBiometricFragment$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            ArrayList arrayList = new ArrayList();
            int size =
                    ((ArrayList) LocationStorage.InstanceHolder.INSTANCE.loadLocationData()).size();
            String valueOf = String.valueOf(54208);
            String valueOf2 = String.valueOf(size);
            StatusData statusData = new StatusData();
            statusData.mStatusValue = valueOf2;
            statusData.mStatusKey = valueOf;
            arrayList.add(statusData);
            return arrayList;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return ApnSettings.MVNO_NONE;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 54103;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.mandatory_biometric_settings;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        SettingsMainSwitchBar settingsMainSwitchBar =
                ((SettingsActivity) getActivity()).mMainSwitch;
        this.mSwitchBar = settingsMainSwitchBar;
        settingsMainSwitchBar.show();
        this.mSwitchBar.getSwitch().mOnBeforeListener = this;
    }

    @Override // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        Log.d(
                "MandatoryBiometricFragment",
                "onActivityResult: requestCode=" + i + ", resultCode=" + i2);
        if (i == 1001) {
            if (i2 == -1) {
                TheftProtectionUtils.setMandatoryBiometricStatus(this.mContext, 0);
                this.mSwitchBar.getSwitch().setCheckedInternal(false);
                updatePreferenceStates();
                return;
            }
            return;
        }
        if (i != 1002) {
            if (i == 1003
                    && TheftProtectionUtils.getMandatoryBiometricStatus(this.mContext) == -1) {
                finish();
                return;
            }
            return;
        }
        if (i2 == -1) {
            NecessaryElementChecker necessaryElementChecker =
                    new NecessaryElementChecker(this.mContext);
            necessaryElementChecker.mDoSuccess =
                    new Runnable() { // from class:
                                     // com.samsung.android.settings.theftprotection.MandatoryBiometricFragment$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            Context context = MandatoryBiometricFragment.this.mContext;
                            if (context != null) {
                                SubSettingLauncher subSettingLauncher =
                                        new SubSettingLauncher(context);
                                String name = MandatoryBiometricLocationFragment.class.getName();
                                SubSettingLauncher.LaunchRequest launchRequest =
                                        subSettingLauncher.mLaunchRequest;
                                launchRequest.mDestinationName = name;
                                launchRequest.mSourceMetricsCategory = 54103;
                                subSettingLauncher.setTitleRes(
                                        R.string.mandatory_biometric_trusted_places, null);
                                subSettingLauncher.launch();
                            }
                        }
                    };
            necessaryElementChecker.mDoFail = null;
            necessaryElementChecker.processNecessaryElements(
                    NecessaryElementChecker.Sequence.START_CHECKING);
        }
    }

    @Override // androidx.appcompat.widget.SeslToggleSwitch.OnBeforeCheckedChangeListener
    public final boolean onBeforeCheckedChanged(boolean z) {
        Log.d("MandatoryBiometricFragment", "onBeforeCheckedChanged = " + z);
        if (!z) {
            TheftProtectionUtils.showProtectionPromptWithAccount(this);
            updatePreferenceStates();
            return true;
        }
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = MandatoryBiometricOnboardingFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = 54103;
        subSettingLauncher.setTitleRes(R.string.theft_protection_mandatory_biometric_title, null);
        subSettingLauncher.launch();
        return true;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getContext();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        if (TextUtils.equals(preference.getKey(), "trusted_places")) {
            startActivityForResult(
                    TheftProtectionUtils.getIntentForMandatoryBiometricsSetting(false), 1002);
        }
        return super.onPreferenceTreeClick(preference);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar
                    .getSwitch()
                    .setCheckedInternal(
                            TheftProtectionUtils.getMandatoryBiometricStatus(this.mContext) == 1);
        }
        updatePreferenceStates();
        if (TheftProtectionUtils.getMandatoryBiometricStatus(this.mContext) == -1) {
            SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
            String name = MandatoryBiometricOnboardingFragment.class.getName();
            SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
            launchRequest.mDestinationName = name;
            launchRequest.mSourceMetricsCategory = 54103;
            subSettingLauncher.setTitleRes(
                    R.string.theft_protection_mandatory_biometric_title, null);
            try {
                startActivityForResult(subSettingLauncher.toIntent(), 1003);
            } catch (ActivityNotFoundException e) {
                Log.d("MandatoryBiometricFragment", "Activity Not Found" + e.getMessage());
            }
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("location_mode"), false, this.mObserver);
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.Global.getUriFor("airplane_mode_on"), false, this.mObserver);
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.Global.getUriFor("mobile_data"), false, this.mObserver);
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("assisted_gps_enabled"), false, this.mObserver);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        this.mContext.getContentResolver().unregisterContentObserver(this.mObserver);
    }
}
