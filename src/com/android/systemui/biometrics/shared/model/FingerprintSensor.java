package com.android.systemui.biometrics.shared.model;

import android.graphics.Rect;

import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class FingerprintSensor {
    public final int maxEnrollmentsPerUser;
    public final Rect sensorBounds;
    public final int sensorId;
    public final int sensorRadius;
    public final SensorStrength sensorStrength;
    public final FingerprintSensorType sensorType;

    public FingerprintSensor(
            int i,
            SensorStrength sensorStrength,
            int i2,
            FingerprintSensorType fingerprintSensorType,
            Rect rect,
            int i3) {
        this.sensorId = i;
        this.sensorStrength = sensorStrength;
        this.maxEnrollmentsPerUser = i2;
        this.sensorType = fingerprintSensorType;
        this.sensorBounds = rect;
        this.sensorRadius = i3;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FingerprintSensor)) {
            return false;
        }
        FingerprintSensor fingerprintSensor = (FingerprintSensor) obj;
        return this.sensorId == fingerprintSensor.sensorId
                && this.sensorStrength == fingerprintSensor.sensorStrength
                && this.maxEnrollmentsPerUser == fingerprintSensor.maxEnrollmentsPerUser
                && this.sensorType == fingerprintSensor.sensorType
                && Intrinsics.areEqual(this.sensorBounds, fingerprintSensor.sensorBounds)
                && this.sensorRadius == fingerprintSensor.sensorRadius;
    }

    public final int hashCode() {
        return Integer.hashCode(this.sensorRadius)
                + ((this.sensorBounds.hashCode()
                                + ((this.sensorType.hashCode()
                                                + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0
                                                        .m(
                                                                this.maxEnrollmentsPerUser,
                                                                (this.sensorStrength.hashCode()
                                                                                + (Integer.hashCode(
                                                                                                this
                                                                                                        .sensorId)
                                                                                        * 31))
                                                                        * 31,
                                                                31))
                                        * 31))
                        * 31);
    }

    public final String toString() {
        return "FingerprintSensor(sensorId="
                + this.sensorId
                + ", sensorStrength="
                + this.sensorStrength
                + ", maxEnrollmentsPerUser="
                + this.maxEnrollmentsPerUser
                + ", sensorType="
                + this.sensorType
                + ", sensorBounds="
                + this.sensorBounds
                + ", sensorRadius="
                + this.sensorRadius
                + ")";
    }
}
