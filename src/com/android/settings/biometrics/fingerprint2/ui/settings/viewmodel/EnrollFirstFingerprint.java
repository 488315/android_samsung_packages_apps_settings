package com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel;

import kotlin.jvm.internal.Intrinsics;

import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class EnrollFirstFingerprint extends NextStepViewModel {
    public final Long challenge;
    public final byte[] challengeToken;
    public final Long gateKeeperPasswordHandle;
    public final int userId;

    public EnrollFirstFingerprint(int i, Long l, Long l2, byte[] bArr) {
        this.userId = i;
        this.gateKeeperPasswordHandle = l;
        this.challenge = l2;
        this.challengeToken = bArr;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EnrollFirstFingerprint)) {
            return false;
        }
        EnrollFirstFingerprint enrollFirstFingerprint = (EnrollFirstFingerprint) obj;
        return this.userId == enrollFirstFingerprint.userId
                && Intrinsics.areEqual(
                        this.gateKeeperPasswordHandle,
                        enrollFirstFingerprint.gateKeeperPasswordHandle)
                && Intrinsics.areEqual(this.challenge, enrollFirstFingerprint.challenge)
                && Intrinsics.areEqual(this.challengeToken, enrollFirstFingerprint.challengeToken);
    }

    public final int hashCode() {
        int hashCode = Integer.hashCode(this.userId) * 31;
        Long l = this.gateKeeperPasswordHandle;
        int hashCode2 = (hashCode + (l == null ? 0 : l.hashCode())) * 31;
        Long l2 = this.challenge;
        int hashCode3 = (hashCode2 + (l2 == null ? 0 : l2.hashCode())) * 31;
        byte[] bArr = this.challengeToken;
        return hashCode3 + (bArr != null ? Arrays.hashCode(bArr) : 0);
    }

    public final String toString() {
        return "EnrollFirstFingerprint(userId="
                + this.userId
                + ", gateKeeperPasswordHandle="
                + this.gateKeeperPasswordHandle
                + ", challenge="
                + this.challenge
                + ", challengeToken="
                + Arrays.toString(this.challengeToken)
                + ")";
    }
}
