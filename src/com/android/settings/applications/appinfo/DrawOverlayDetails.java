package com.android.settings.applications.appinfo;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.applications.AppInfoBase;
import com.android.settings.applications.AppInfoWithHeader;
import com.android.settings.applications.AppStateOverlayBridge;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;

import com.samsung.android.settings.applications.AppCommonUtils;
import com.samsung.android.settings.logging.SALogging;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DrawOverlayDetails extends AppInfoWithHeader
        implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {
    public AppOpsManager mAppOpsManager;
    public AppStateOverlayBridge mOverlayBridge;
    public AppStateOverlayBridge.OverlayState mOverlayState;
    public TwoStatePreference mSwitchPref = null;

    @Override // com.android.settings.applications.AppInfoBase
    public final AlertDialog createDialog(int i) {
        return null;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getFragmentTitleResId(Context context) {
        return AppCommonUtils.getOverlayTitle();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 221;
    }

    public void logSpecialPermissionChange(boolean z, String str) {
        int i = z ? 770 : 771;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        SettingsMetricsFeatureProvider metricsFeatureProvider =
                featureFactoryImpl.getMetricsFeatureProvider();
        FragmentActivity activity = getActivity();
        metricsFeatureProvider.getClass();
        metricsFeatureProvider.action(
                MetricsFeatureProvider.getAttribution(activity), i, 221, 0, str);
    }

    @Override // com.android.settings.applications.AppInfoBase,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentActivity activity = getActivity();
        this.mOverlayBridge =
                new AppStateOverlayBridge(activity, ((AppInfoBase) this).mState, null);
        this.mAppOpsManager = (AppOpsManager) activity.getSystemService("appops");
        if (!Utils.isSystemAlertWindowEnabled(activity)) {
            this.mPackageInfo = null;
            return;
        }
        addPreferencesFromResource(R.xml.sec_draw_overlay_permissions_details);
        TwoStatePreference twoStatePreference =
                (TwoStatePreference) findPreference("app_ops_settings_switch");
        this.mSwitchPref = twoStatePreference;
        twoStatePreference.setOnPreferenceChangeListener(this);
        getActivity().setTitle(AppCommonUtils.getOverlayTitle());
        this.mSwitchPref.seslSetRoundedBg(15);
        SALogging.insertSALog(Integer.toString(39003));
    }

    @Override // com.android.settings.applications.AppInfoBase,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        this.mOverlayBridge.release();
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (preference != this.mSwitchPref) {
            return false;
        }
        Boolean bool = (Boolean) obj;
        AppInfoWithHeader.insertEventLogging(39003, 3933, this.mPackageName, bool.booleanValue());
        if (this.mOverlayState != null
                && bool.booleanValue() != this.mOverlayState.isPermissible()) {
            boolean z = !this.mOverlayState.isPermissible();
            logSpecialPermissionChange(z, this.mPackageName);
            this.mAppOpsManager.setMode(
                    24, this.mPackageInfo.applicationInfo.uid, this.mPackageName, z ? 0 : 2);
            refreshUi();
        }
        return true;
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        return false;
    }

    @Override // com.android.settings.applications.AppInfoBase
    public final boolean refreshUi() {
        PackageInfo packageInfo = this.mPackageInfo;
        boolean z = false;
        if (packageInfo == null) {
            return false;
        }
        AppStateOverlayBridge.OverlayState overlayState =
                new AppStateOverlayBridge.OverlayState(
                        this.mOverlayBridge.getPermissionInfo(
                                packageInfo.applicationInfo.uid, this.mPackageName));
        this.mOverlayState = overlayState;
        this.mSwitchPref.setChecked(overlayState.isPermissible());
        TwoStatePreference twoStatePreference = this.mSwitchPref;
        AppStateOverlayBridge.OverlayState overlayState2 = this.mOverlayState;
        if (overlayState2.permissionDeclared && overlayState2.controlEnabled) {
            z = true;
        }
        twoStatePreference.setEnabled(z);
        return true;
    }
}
