package com.android.settings.enterprise;

import java.util.Date;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class NetworkLogsPreferenceController extends AdminActionPreferenceControllerBase {
    @Override // com.android.settings.enterprise.AdminActionPreferenceControllerBase
    public final Date getAdminActionTimestamp() {
        long lastNetworkLogRetrievalTime =
                this.mFeatureProvider.mDpm.getLastNetworkLogRetrievalTime();
        if (lastNetworkLogRetrievalTime < 0) {
            return null;
        }
        return new Date(lastNetworkLogRetrievalTime);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "network_logs";
    }

    @Override // com.android.settings.enterprise.AdminActionPreferenceControllerBase,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        EnterprisePrivacyFeatureProviderImpl enterprisePrivacyFeatureProviderImpl =
                this.mFeatureProvider;
        if (!enterprisePrivacyFeatureProviderImpl.mDpm.isNetworkLoggingEnabled(null)) {
            long lastNetworkLogRetrievalTime =
                    enterprisePrivacyFeatureProviderImpl.mDpm.getLastNetworkLogRetrievalTime();
            if ((lastNetworkLogRetrievalTime >= 0 ? new Date(lastNetworkLogRetrievalTime) : null)
                    == null) {
                return false;
            }
        }
        return true;
    }
}
