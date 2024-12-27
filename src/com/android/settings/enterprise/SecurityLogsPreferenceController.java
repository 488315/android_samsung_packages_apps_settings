package com.android.settings.enterprise;

import java.util.Date;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SecurityLogsPreferenceController extends AdminActionPreferenceControllerBase {
    @Override // com.android.settings.enterprise.AdminActionPreferenceControllerBase
    public final Date getAdminActionTimestamp() {
        long lastSecurityLogRetrievalTime =
                this.mFeatureProvider.mDpm.getLastSecurityLogRetrievalTime();
        if (lastSecurityLogRetrievalTime < 0) {
            return null;
        }
        return new Date(lastSecurityLogRetrievalTime);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "security_logs";
    }

    @Override // com.android.settings.enterprise.AdminActionPreferenceControllerBase,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        EnterprisePrivacyFeatureProviderImpl enterprisePrivacyFeatureProviderImpl =
                this.mFeatureProvider;
        if (!enterprisePrivacyFeatureProviderImpl.mDpm.isSecurityLoggingEnabled(null)) {
            long lastSecurityLogRetrievalTime =
                    enterprisePrivacyFeatureProviderImpl.mDpm.getLastSecurityLogRetrievalTime();
            if ((lastSecurityLogRetrievalTime >= 0 ? new Date(lastSecurityLogRetrievalTime) : null)
                    == null) {
                return false;
            }
        }
        return true;
    }
}
