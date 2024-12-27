package com.android.settings.applications.specialaccess.pictureinpicture;

import android.app.AppOpsManager;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.settings.R;
import com.android.settings.applications.AppInfoWithHeader;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;

import com.samsung.android.settings.logging.SALogging;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PictureInPictureDetails extends AppInfoWithHeader
        implements Preference.OnPreferenceChangeListener {
    public TwoStatePreference mSwitchPref;

    public static boolean getEnterPipStateForPackage(Context context, int i, String str) {
        return ((AppOpsManager) context.getSystemService(AppOpsManager.class))
                        .checkOpNoThrow(67, i, str)
                == 0;
    }

    @Override // com.android.settings.applications.AppInfoBase
    public final AlertDialog createDialog(int i) {
        return null;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1990;
    }

    public void logSpecialPermissionChange(boolean z, String str) {
        int i = z ? 813 : 814;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        SettingsMetricsFeatureProvider metricsFeatureProvider =
                featureFactoryImpl.getMetricsFeatureProvider();
        FragmentActivity activity = getActivity();
        metricsFeatureProvider.getClass();
        metricsFeatureProvider.action(
                MetricsFeatureProvider.getAttribution(activity), i, 1990, 0, str);
    }

    @Override // com.android.settings.applications.AppInfoBase,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.picture_in_picture_permissions_details);
        TwoStatePreference twoStatePreference =
                (TwoStatePreference) findPreference("app_ops_settings_switch");
        this.mSwitchPref = twoStatePreference;
        twoStatePreference.setTitle(R.string.sec_special_access_detail_switch);
        this.mSwitchPref.seslSetRoundedBg(15);
        SALogging.insertSALog(Integer.toString(39008));
        this.mSwitchPref.setOnPreferenceChangeListener(this);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (preference != this.mSwitchPref) {
            return false;
        }
        Boolean bool = (Boolean) obj;
        logSpecialPermissionChange(bool.booleanValue(), this.mPackageName);
        FragmentActivity activity = getActivity();
        ((AppOpsManager) activity.getSystemService(AppOpsManager.class))
                .setMode(
                        67,
                        this.mPackageInfo.applicationInfo.uid,
                        this.mPackageName,
                        bool.booleanValue() ? 0 : 2);
        AppInfoWithHeader.insertEventLogging(39008, 3938, this.mPackageName, bool.booleanValue());
        return true;
    }

    @Override // com.android.settings.applications.AppInfoBase
    public final boolean refreshUi() {
        this.mSwitchPref.setChecked(
                getEnterPipStateForPackage(
                        getActivity(), this.mPackageInfo.applicationInfo.uid, this.mPackageName));
        return true;
    }
}
