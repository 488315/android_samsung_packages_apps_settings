package com.android.systemui.biometrics.shared.model;

import android.graphics.Rect;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class UdfpsOverlayParams {
    public final int logicalDisplayHeight;
    public final int logicalDisplayWidth;
    public final int naturalDisplayHeight;
    public final int naturalDisplayWidth;
    public final Rect overlayBounds;
    public final int rotation;
    public final float scaleFactor;
    public final Rect sensorBounds;
    public final int sensorType;

    public UdfpsOverlayParams(Rect rect, Rect rect2, int i, int i2, float f, int i3, int i4) {
        this.sensorBounds = rect;
        this.overlayBounds = rect2;
        this.naturalDisplayWidth = i;
        this.naturalDisplayHeight = i2;
        this.scaleFactor = f;
        this.rotation = i3;
        this.sensorType = i4;
        Rect rect3 = new Rect(rect);
        float f2 = 1.0f / f;
        rect3.scale(f2);
        new Rect(rect2).scale(f2);
        this.logicalDisplayWidth = (i3 == 1 || i3 == 3) ? i2 : i;
        if (i3 != 1 && i3 != 3) {
            i = i2;
        }
        this.logicalDisplayHeight = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UdfpsOverlayParams)) {
            return false;
        }
        UdfpsOverlayParams udfpsOverlayParams = (UdfpsOverlayParams) obj;
        return Intrinsics.areEqual(this.sensorBounds, udfpsOverlayParams.sensorBounds)
                && Intrinsics.areEqual(this.overlayBounds, udfpsOverlayParams.overlayBounds)
                && this.naturalDisplayWidth == udfpsOverlayParams.naturalDisplayWidth
                && this.naturalDisplayHeight == udfpsOverlayParams.naturalDisplayHeight
                && Float.compare(this.scaleFactor, udfpsOverlayParams.scaleFactor) == 0
                && this.rotation == udfpsOverlayParams.rotation
                && this.sensorType == udfpsOverlayParams.sensorType;
    }

    public final int hashCode() {
        return Integer.hashCode(this.sensorType)
                + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(
                        this.rotation,
                        FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(
                                KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(
                                        this.naturalDisplayHeight,
                                        KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(
                                                this.naturalDisplayWidth,
                                                (this.overlayBounds.hashCode()
                                                                + (this.sensorBounds.hashCode()
                                                                        * 31))
                                                        * 31,
                                                31),
                                        31),
                                31,
                                this.scaleFactor),
                        31);
    }

    public final String toString() {
        Rect rect = this.sensorBounds;
        Rect rect2 = this.overlayBounds;
        StringBuilder sb = new StringBuilder("UdfpsOverlayParams(sensorBounds=");
        sb.append(rect);
        sb.append(", overlayBounds=");
        sb.append(rect2);
        sb.append(", naturalDisplayWidth=");
        sb.append(this.naturalDisplayWidth);
        sb.append(", naturalDisplayHeight=");
        sb.append(this.naturalDisplayHeight);
        sb.append(", scaleFactor=");
        sb.append(this.scaleFactor);
        sb.append(", rotation=");
        sb.append(this.rotation);
        sb.append(", sensorType=");
        return Anchor$$ExternalSyntheticOutline0.m(sb, this.sensorType, ")");
    }
}
