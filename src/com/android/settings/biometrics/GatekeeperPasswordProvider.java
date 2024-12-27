package com.android.settings.biometrics;

import android.content.Intent;
import android.util.Log;

import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.VerifyCredentialResponse;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class GatekeeperPasswordProvider {
    public final LockPatternUtils mLockPatternUtils;

    public GatekeeperPasswordProvider(LockPatternUtils lockPatternUtils) {
        this.mLockPatternUtils = lockPatternUtils;
    }

    public static boolean containsGatekeeperPasswordHandle(Intent intent) {
        return intent != null && intent.hasExtra("gk_pw_handle");
    }

    public static long getGatekeeperPasswordHandle(Intent intent) {
        return intent.getLongExtra("gk_pw_handle", 0L);
    }

    public final void removeGatekeeperPasswordHandle(long j) {
        this.mLockPatternUtils.removeGatekeeperPasswordHandle(j);
        Log.d("GatekeeperPasswordProvider", "Removed handle");
    }

    public final byte[] requestGatekeeperHat(long j, long j2, int i) {
        VerifyCredentialResponse verifyGatekeeperPasswordHandle =
                this.mLockPatternUtils.verifyGatekeeperPasswordHandle(j, j2, i);
        if (verifyGatekeeperPasswordHandle.isMatched()) {
            return verifyGatekeeperPasswordHandle.getGatekeeperHAT();
        }
        Log.e("GatekeeperPasswordProvider", "Unable to request Gatekeeper HAT");
        return null;
    }
}
