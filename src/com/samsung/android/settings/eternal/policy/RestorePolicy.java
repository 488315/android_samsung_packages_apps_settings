package com.samsung.android.settings.eternal.policy;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class RestorePolicy {
    public static final List RESTORE_ALLOW_LIST_FOR_IOS = List.of("/Settings/Advanced/LiftToWake");
    public List mDeferredRestorationItems;
    public String mPolicyId;
    public PolicyValidator mPolicyValidator;
    public List mRestoreRestrictionItems;
}
