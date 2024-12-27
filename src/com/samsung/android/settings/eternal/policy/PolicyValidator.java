package com.samsung.android.settings.eternal.policy;

import android.os.SemSystemProperties;

import com.samsung.android.feature.SemFloatingFeature;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class PolicyValidator {
    public final int mHingeDirection;
    public final boolean mIsTablet;
    public final int mOneUIVersion;
    public final HashMap mProviderVersion;

    public PolicyValidator(HashMap hashMap) {
        String str = SemSystemProperties.get("ro.build.characteristics");
        this.mIsTablet = str != null && str.contains("tablet");
        this.mHingeDirection =
                SemFloatingFeature.getInstance()
                                .getInt("SEC_FLOATING_FEATURE_SIP_CONFIG_FOLD_UX_VERSION")
                        & 15;
        this.mOneUIVersion = SemSystemProperties.getInt("ro.build.version.oneui", 0);
        HashMap hashMap2 = new HashMap();
        this.mProviderVersion = hashMap2;
        hashMap2.putAll(hashMap);
    }

    public static Double getVersionAsDouble(String str) {
        str.getClass();
        return !str.equals("max")
                ? !str.equals("min")
                        ? Double.valueOf(Double.parseDouble(str))
                        : Double.valueOf(Double.MIN_VALUE)
                : Double.valueOf(Double.MAX_VALUE);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0094, code lost:

       if (r8 == false) goto L37;
    */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0097, code lost:

       if (r9 == 1) goto L37;
    */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x009a, code lost:

       if (r9 == 2) goto L37;
    */
    /* JADX WARN: Removed duplicated region for block: B:45:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:6:0x009f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isValidPolicy(
            com.samsung.android.settings.eternal.policy.provider.PolicyItem r13) {
        /*
            Method dump skipped, instructions count: 470
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.eternal.policy.PolicyValidator.isValidPolicy(com.samsung.android.settings.eternal.policy.provider.PolicyItem):boolean");
    }
}
