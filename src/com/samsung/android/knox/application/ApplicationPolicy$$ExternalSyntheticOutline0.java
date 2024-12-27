package com.samsung.android.knox.application;

import com.samsung.android.knox.AccessController;
import com.samsung.android.knox.ContextInfo;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract /* synthetic */ class ApplicationPolicy$$ExternalSyntheticOutline0 {
    public static IApplicationPolicy m(
            ContextInfo contextInfo, String str, ApplicationPolicy applicationPolicy, String str2) {
        AccessController.throwIfParentInstance(contextInfo, str);
        applicationPolicy.logUsage(str2);
        return applicationPolicy.getService();
    }
}
