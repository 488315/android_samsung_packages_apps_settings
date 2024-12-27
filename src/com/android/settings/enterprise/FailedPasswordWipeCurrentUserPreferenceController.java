package com.android.settings.enterprise;

import android.content.ComponentName;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class FailedPasswordWipeCurrentUserPreferenceController
        extends FailedPasswordWipePreferenceControllerBase {
    @Override // com.android.settings.enterprise.FailedPasswordWipePreferenceControllerBase
    public final int getMaximumFailedPasswordsBeforeWipe() {
        EnterprisePrivacyFeatureProviderImpl enterprisePrivacyFeatureProviderImpl =
                this.mFeatureProvider;
        ComponentName deviceOwnerComponentOnCallingUser =
                enterprisePrivacyFeatureProviderImpl.mDpm.getDeviceOwnerComponentOnCallingUser();
        int i = EnterprisePrivacyFeatureProviderImpl.MY_USER_ID;
        if (deviceOwnerComponentOnCallingUser == null) {
            deviceOwnerComponentOnCallingUser =
                    enterprisePrivacyFeatureProviderImpl.mDpm.getProfileOwnerAsUser(i);
        }
        if (deviceOwnerComponentOnCallingUser == null) {
            return 0;
        }
        return enterprisePrivacyFeatureProviderImpl.mDpm.getMaximumFailedPasswordsForWipe(
                deviceOwnerComponentOnCallingUser, i);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "failed_password_wipe_current_user";
    }
}
