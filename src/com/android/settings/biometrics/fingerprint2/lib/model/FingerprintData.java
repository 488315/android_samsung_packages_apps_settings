package com.android.settings.biometrics.fingerprint2.lib.model;

import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class FingerprintData {
    public final long deviceId;
    public final int fingerId;
    public final String name;

    public FingerprintData(long j, int i, String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        this.name = name;
        this.fingerId = i;
        this.deviceId = j;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FingerprintData)) {
            return false;
        }
        FingerprintData fingerprintData = (FingerprintData) obj;
        return Intrinsics.areEqual(this.name, fingerprintData.name)
                && this.fingerId == fingerprintData.fingerId
                && this.deviceId == fingerprintData.deviceId;
    }

    public final int hashCode() {
        return Long.hashCode(this.deviceId)
                + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(
                        this.fingerId, this.name.hashCode() * 31, 31);
    }

    public final String toString() {
        return "FingerprintData(name="
                + this.name
                + ", fingerId="
                + this.fingerId
                + ", deviceId="
                + this.deviceId
                + ")";
    }
}
