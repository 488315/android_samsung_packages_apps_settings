package com.android.settings.enterprise;

import android.content.Context;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.applications.ApplicationFeatureProvider;
import com.android.settings.applications.ApplicationFeatureProviderImpl;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.utils.StringUtil;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class EnterpriseInstalledPackagesPreferenceController
        extends AbstractPreferenceController implements PreferenceControllerMixin {
    public final boolean mAsync;
    public final ApplicationFeatureProviderImpl mFeatureProvider;

    public static /* synthetic */ void $r8$lambda$DztCfSlkwQzgsJLYEJ88cDUvTlA(
            EnterpriseInstalledPackagesPreferenceController
                    enterpriseInstalledPackagesPreferenceController,
            Preference preference,
            int i) {
        boolean z;
        if (i == 0) {
            enterpriseInstalledPackagesPreferenceController.getClass();
            z = false;
        } else {
            preference.setSummary(
                    StringUtil.getIcuPluralsString(
                            enterpriseInstalledPackagesPreferenceController.mContext,
                            i,
                            R.string.enterprise_privacy_number_packages_lower_bound));
            z = true;
        }
        preference.setVisible(z);
    }

    public EnterpriseInstalledPackagesPreferenceController(Context context, boolean z) {
        super(context);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mFeatureProvider = featureFactoryImpl.getApplicationFeatureProvider();
        this.mAsync = z;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "number_enterprise_installed_packages";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        if (this.mAsync) {
            return true;
        }
        final Boolean[] boolArr = {null};
        ApplicationFeatureProvider.NumberOfAppsCallback numberOfAppsCallback =
                new ApplicationFeatureProvider
                        .NumberOfAppsCallback() { // from class:
                                                  // com.android.settings.enterprise.EnterpriseInstalledPackagesPreferenceController$$ExternalSyntheticLambda1
                    @Override // com.android.settings.applications.ApplicationFeatureProvider.NumberOfAppsCallback
                    public final void onNumberOfAppsResult(int i) {
                        boolArr[0] = Boolean.valueOf(i > 0);
                    }
                };
        ApplicationFeatureProviderImpl applicationFeatureProviderImpl = this.mFeatureProvider;
        applicationFeatureProviderImpl.getClass();
        ApplicationFeatureProviderImpl.CurrentUserAndManagedProfilePolicyInstalledAppCounter
                currentUserAndManagedProfilePolicyInstalledAppCounter =
                        new ApplicationFeatureProviderImpl
                                .CurrentUserAndManagedProfilePolicyInstalledAppCounter(
                                applicationFeatureProviderImpl.mContext,
                                1,
                                applicationFeatureProviderImpl.mPm);
        currentUserAndManagedProfilePolicyInstalledAppCounter.mCallback = numberOfAppsCallback;
        Void[] voidArr = new Void[0];
        currentUserAndManagedProfilePolicyInstalledAppCounter.onCountComplete(
                currentUserAndManagedProfilePolicyInstalledAppCounter.doInBackground().intValue());
        return boolArr[0].booleanValue();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(final Preference preference) {
        ApplicationFeatureProvider.NumberOfAppsCallback numberOfAppsCallback =
                new ApplicationFeatureProvider
                        .NumberOfAppsCallback() { // from class:
                                                  // com.android.settings.enterprise.EnterpriseInstalledPackagesPreferenceController$$ExternalSyntheticLambda0
                    @Override // com.android.settings.applications.ApplicationFeatureProvider.NumberOfAppsCallback
                    public final void onNumberOfAppsResult(int i) {
                        EnterpriseInstalledPackagesPreferenceController
                                .$r8$lambda$DztCfSlkwQzgsJLYEJ88cDUvTlA(
                                        EnterpriseInstalledPackagesPreferenceController.this,
                                        preference,
                                        i);
                    }
                };
        ApplicationFeatureProviderImpl applicationFeatureProviderImpl = this.mFeatureProvider;
        applicationFeatureProviderImpl.getClass();
        ApplicationFeatureProviderImpl.CurrentUserAndManagedProfilePolicyInstalledAppCounter
                currentUserAndManagedProfilePolicyInstalledAppCounter =
                        new ApplicationFeatureProviderImpl
                                .CurrentUserAndManagedProfilePolicyInstalledAppCounter(
                                applicationFeatureProviderImpl.mContext,
                                1,
                                applicationFeatureProviderImpl.mPm);
        currentUserAndManagedProfilePolicyInstalledAppCounter.mCallback = numberOfAppsCallback;
        currentUserAndManagedProfilePolicyInstalledAppCounter.execute(new Void[0]);
    }
}
