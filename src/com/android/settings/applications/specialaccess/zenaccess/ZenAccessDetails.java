package com.android.settings.applications.specialaccess.zenaccess;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.settings.R;
import com.android.settings.applications.AppInfoWithHeader;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ZenAccessDetails extends AppInfoWithHeader
        implements ZenAccessSettingObserverMixin.Listener {
    @Override // com.android.settings.applications.AppInfoBase
    public final AlertDialog createDialog(int i) {
        return null;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1692;
    }

    @Override // com.android.settings.applications.AppInfoBase,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.zen_access_permission_details);
        getSettingsLifecycle().addObserver(new ZenAccessSettingObserverMixin(getContext(), this));
    }

    @Override // com.android.settings.applications.AppInfoBase
    public final boolean refreshUi() {
        Context context = getContext();
        if (!ZenAccessController.getPackagesRequestingNotificationPolicyAccess()
                .contains(this.mPackageName)) {
            finish();
            return true;
        }
        TwoStatePreference twoStatePreference =
                (TwoStatePreference) findPreference("zen_access_switch");
        final CharSequence loadLabel = this.mPackageInfo.applicationInfo.loadLabel(this.mPm);
        if (ZenAccessController.getAutoApprovedPackages(context).contains(this.mPackageName)) {
            twoStatePreference.setEnabled(false);
            twoStatePreference.setSummary(getString(R.string.zen_access_disabled_package_warning));
            return true;
        }
        twoStatePreference.setChecked(ZenAccessController.hasAccess(context, this.mPackageName));
        twoStatePreference.setOnPreferenceChangeListener(
                new Preference
                        .OnPreferenceChangeListener() { // from class:
                                                        // com.android.settings.applications.specialaccess.zenaccess.ZenAccessDetails$$ExternalSyntheticLambda0
                    @Override // androidx.preference.Preference.OnPreferenceChangeListener
                    public final boolean onPreferenceChange(Preference preference, Object obj) {
                        CharSequence charSequence = loadLabel;
                        ZenAccessDetails zenAccessDetails = ZenAccessDetails.this;
                        zenAccessDetails.getClass();
                        if (((Boolean) obj).booleanValue()) {
                            ScaryWarningDialogFragment scaryWarningDialogFragment =
                                    new ScaryWarningDialogFragment();
                            scaryWarningDialogFragment.setPkgInfo$1(
                                    zenAccessDetails.mPackageName, charSequence, zenAccessDetails);
                            scaryWarningDialogFragment.show(
                                    zenAccessDetails.getFragmentManager(), "dialog");
                            return false;
                        }
                        FriendlyWarningDialogFragment friendlyWarningDialogFragment =
                                new FriendlyWarningDialogFragment();
                        friendlyWarningDialogFragment.setPkgInfo(
                                zenAccessDetails.mPackageName, charSequence, zenAccessDetails);
                        friendlyWarningDialogFragment.show(
                                zenAccessDetails.getFragmentManager(), "dialog");
                        return false;
                    }
                });
        return true;
    }
}
