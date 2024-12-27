package com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel;

import kotlin.jvm.internal.Intrinsics;

import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class EnrollAdditionalFingerprint extends NextStepViewModel {
    public final byte[] challengeToken;
    public final int userId;

    public EnrollAdditionalFingerprint(int i, byte[] bArr) {
        this.userId = i;
        this.challengeToken = bArr;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EnrollAdditionalFingerprint)) {
            return false;
        }
        EnrollAdditionalFingerprint enrollAdditionalFingerprint = (EnrollAdditionalFingerprint) obj;
        return this.userId == enrollAdditionalFingerprint.userId
                && Intrinsics.areEqual(
                        this.challengeToken, enrollAdditionalFingerprint.challengeToken);
    }

    public final int hashCode() {
        int hashCode = Integer.hashCode(this.userId) * 31;
        byte[] bArr = this.challengeToken;
        return hashCode + (bArr == null ? 0 : Arrays.hashCode(bArr));
    }

    public final String toString() {
        return "EnrollAdditionalFingerprint(userId="
                + this.userId
                + ", challengeToken="
                + Arrays.toString(this.challengeToken)
                + ")";
    }
}
