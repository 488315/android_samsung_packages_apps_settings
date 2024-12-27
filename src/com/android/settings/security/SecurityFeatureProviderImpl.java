package com.android.settings.security;

import android.content.Context;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.security.trustagent.TrustAgentManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SecurityFeatureProviderImpl {
    public LockPatternUtils mLockPatternUtils;
    public TrustAgentManager mTrustAgentManager;

    public final LockPatternUtils getLockPatternUtils(Context context) {
        if (this.mLockPatternUtils == null) {
            this.mLockPatternUtils = new LockPatternUtils(context.getApplicationContext());
        }
        return this.mLockPatternUtils;
    }

    public final TrustAgentManager getTrustAgentManager() {
        if (this.mTrustAgentManager == null) {
            this.mTrustAgentManager = new TrustAgentManager();
        }
        return this.mTrustAgentManager;
    }
}
