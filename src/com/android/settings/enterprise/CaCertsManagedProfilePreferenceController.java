package com.android.settings.enterprise;

import android.os.UserHandle;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class CaCertsManagedProfilePreferenceController
        extends CaCertsPreferenceControllerBase {
    static final String CA_CERTS_MANAGED_PROFILE = "ca_certs_managed_profile";

    @Override // com.android.settings.enterprise.CaCertsPreferenceControllerBase
    public final int getNumberOfCaCerts() {
        List ownerInstalledCaCerts;
        EnterprisePrivacyFeatureProviderImpl enterprisePrivacyFeatureProviderImpl =
                this.mFeatureProvider;
        int managedProfileUserId = enterprisePrivacyFeatureProviderImpl.getManagedProfileUserId();
        if (managedProfileUserId == -10000
                || (ownerInstalledCaCerts =
                                enterprisePrivacyFeatureProviderImpl.mDpm.getOwnerInstalledCaCerts(
                                        new UserHandle(managedProfileUserId)))
                        == null) {
            return 0;
        }
        return ownerInstalledCaCerts.size();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return CA_CERTS_MANAGED_PROFILE;
    }
}
