package com.android.settings.enterprise;

import java.util.Date;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BugReportsPreferenceController extends AdminActionPreferenceControllerBase {
    @Override // com.android.settings.enterprise.AdminActionPreferenceControllerBase
    public final Date getAdminActionTimestamp() {
        long lastBugReportRequestTime = this.mFeatureProvider.mDpm.getLastBugReportRequestTime();
        if (lastBugReportRequestTime < 0) {
            return null;
        }
        return new Date(lastBugReportRequestTime);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bug_reports";
    }
}
