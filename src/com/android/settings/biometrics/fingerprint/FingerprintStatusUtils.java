package com.android.settings.biometrics.fingerprint;

import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class FingerprintStatusUtils {
    public final Context mContext;
    public final FingerprintManager mFingerprintManager;
    public final int mUserId;

    public FingerprintStatusUtils(Context context, FingerprintManager fingerprintManager, int i) {
        this.mContext = context;
        this.mFingerprintManager = fingerprintManager;
        this.mUserId = i;
    }
}
