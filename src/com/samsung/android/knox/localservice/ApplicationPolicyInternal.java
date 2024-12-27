package com.samsung.android.knox.localservice;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class ApplicationPolicyInternal {
    public abstract String getApplicationNameFromDb(String str, int i);

    public abstract boolean getApplicationStateEnabledAsUser(String str, boolean z, int i);

    public abstract boolean isApplicationStartDisabledAsUser(String str, int i);

    public abstract boolean isApplicationStopDisabledAsUser(
            String str, int i, String str2, String str3, String str4, boolean z);
}
