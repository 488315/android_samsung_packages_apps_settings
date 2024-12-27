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
public abstract class AdminGrantedPermissionsPreferenceControllerBase
        extends AbstractPreferenceController implements PreferenceControllerMixin {
    public final boolean mAsync;
    public final ApplicationFeatureProviderImpl mFeatureProvider;
    public boolean mHasApps;
    public final String[] mPermissions;

    /* renamed from: $r8$lambda$eup4Bmb_joEfgUk8nuk2bfAW-QI, reason: not valid java name */
    public static /* synthetic */ void m860$r8$lambda$eup4Bmb_joEfgUk8nuk2bfAWQI(
            AdminGrantedPermissionsPreferenceControllerBase
                    adminGrantedPermissionsPreferenceControllerBase,
            Preference preference,
            int i) {
        if (i == 0) {
            adminGrantedPermissionsPreferenceControllerBase.mHasApps = false;
        } else {
            preference.setSummary(
                    StringUtil.getIcuPluralsString(
                            adminGrantedPermissionsPreferenceControllerBase.mContext,
                            i,
                            R.string.enterprise_privacy_number_packages_lower_bound));
            adminGrantedPermissionsPreferenceControllerBase.mHasApps = true;
        }
        preference.setVisible(adminGrantedPermissionsPreferenceControllerBase.mHasApps);
    }

    public AdminGrantedPermissionsPreferenceControllerBase(
            Context context, boolean z, String[] strArr) {
        super(context);
        this.mPermissions = strArr;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mFeatureProvider = featureFactoryImpl.getApplicationFeatureProvider();
        this.mAsync = z;
        this.mHasApps = false;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean handlePreferenceTreeClick(Preference preference) {
        getPreferenceKey().equals(preference.getKey());
        return false;
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
                                                  // com.android.settings.enterprise.AdminGrantedPermissionsPreferenceControllerBase$$ExternalSyntheticLambda0
                    @Override // com.android.settings.applications.ApplicationFeatureProvider.NumberOfAppsCallback
                    public final void onNumberOfAppsResult(int i) {
                        boolArr[0] = Boolean.valueOf(i > 0);
                    }
                };
        ApplicationFeatureProviderImpl applicationFeatureProviderImpl = this.mFeatureProvider;
        applicationFeatureProviderImpl.getClass();
        ApplicationFeatureProviderImpl
                        .CurrentUserAndManagedProfileAppWithAdminGrantedPermissionsCounter
                currentUserAndManagedProfileAppWithAdminGrantedPermissionsCounter =
                        new ApplicationFeatureProviderImpl
                                .CurrentUserAndManagedProfileAppWithAdminGrantedPermissionsCounter(
                                applicationFeatureProviderImpl.mContext,
                                this.mPermissions,
                                applicationFeatureProviderImpl.mPm,
                                applicationFeatureProviderImpl.mPms,
                                applicationFeatureProviderImpl.mDpm,
                                numberOfAppsCallback);
        Void[] voidArr = new Void[0];
        currentUserAndManagedProfileAppWithAdminGrantedPermissionsCounter.onCountComplete(
                currentUserAndManagedProfileAppWithAdminGrantedPermissionsCounter
                        .doInBackground()
                        .intValue());
        boolean booleanValue = boolArr[0].booleanValue();
        this.mHasApps = booleanValue;
        return booleanValue;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(final Preference preference) {
        ApplicationFeatureProvider.NumberOfAppsCallback numberOfAppsCallback =
                new ApplicationFeatureProvider
                        .NumberOfAppsCallback() { // from class:
                                                  // com.android.settings.enterprise.AdminGrantedPermissionsPreferenceControllerBase$$ExternalSyntheticLambda1
                    @Override // com.android.settings.applications.ApplicationFeatureProvider.NumberOfAppsCallback
                    public final void onNumberOfAppsResult(int i) {
                        AdminGrantedPermissionsPreferenceControllerBase
                                .m860$r8$lambda$eup4Bmb_joEfgUk8nuk2bfAWQI(
                                        AdminGrantedPermissionsPreferenceControllerBase.this,
                                        preference,
                                        i);
                    }
                };
        ApplicationFeatureProviderImpl applicationFeatureProviderImpl = this.mFeatureProvider;
        applicationFeatureProviderImpl.getClass();
        new ApplicationFeatureProviderImpl
                        .CurrentUserAndManagedProfileAppWithAdminGrantedPermissionsCounter(
                        applicationFeatureProviderImpl.mContext,
                        this.mPermissions,
                        applicationFeatureProviderImpl.mPm,
                        applicationFeatureProviderImpl.mPms,
                        applicationFeatureProviderImpl.mDpm,
                        numberOfAppsCallback)
                .execute(new Void[0]);
    }
}
