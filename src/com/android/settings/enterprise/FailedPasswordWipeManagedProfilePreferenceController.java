package com.android.settings.enterprise;

import android.content.ComponentName;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class FailedPasswordWipeManagedProfilePreferenceController
        extends FailedPasswordWipePreferenceControllerBase {
    @Override // com.android.settings.enterprise.FailedPasswordWipePreferenceControllerBase
    public final int getMaximumFailedPasswordsBeforeWipe() {
        ComponentName profileOwnerAsUser;
        EnterprisePrivacyFeatureProviderImpl enterprisePrivacyFeatureProviderImpl =
                this.mFeatureProvider;
        int managedProfileUserId = enterprisePrivacyFeatureProviderImpl.getManagedProfileUserId();
        if (managedProfileUserId == -10000
                || (profileOwnerAsUser =
                                enterprisePrivacyFeatureProviderImpl.mDpm.getProfileOwnerAsUser(
                                        managedProfileUserId))
                        == null) {
            return 0;
        }
        return enterprisePrivacyFeatureProviderImpl.mDpm.getMaximumFailedPasswordsForWipe(
                profileOwnerAsUser, managedProfileUserId);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "failed_password_wipe_managed_profile";
    }
}
