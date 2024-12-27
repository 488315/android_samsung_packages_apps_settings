package com.android.settings.datausage.lib;

import android.util.Range;

import androidx.compose.animation.Scale$$ExternalSyntheticOutline0;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class NetworkUsageDetailsData {
    public static final NetworkUsageDetailsData AllZero =
            new NetworkUsageDetailsData(new Range(0L, 0L), 0, 0, 0);
    public final long backgroundUsage;
    public final long foregroundUsage;
    public final Range range;
    public final long totalUsage;

    public NetworkUsageDetailsData(Range range, long j, long j2, long j3) {
        Intrinsics.checkNotNullParameter(range, "range");
        this.range = range;
        this.totalUsage = j;
        this.foregroundUsage = j2;
        this.backgroundUsage = j3;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NetworkUsageDetailsData)) {
            return false;
        }
        NetworkUsageDetailsData networkUsageDetailsData = (NetworkUsageDetailsData) obj;
        return Intrinsics.areEqual(this.range, networkUsageDetailsData.range)
                && this.totalUsage == networkUsageDetailsData.totalUsage
                && this.foregroundUsage == networkUsageDetailsData.foregroundUsage
                && this.backgroundUsage == networkUsageDetailsData.backgroundUsage;
    }

    public final int hashCode() {
        return Long.hashCode(this.backgroundUsage)
                + Scale$$ExternalSyntheticOutline0.m(
                        Scale$$ExternalSyntheticOutline0.m(
                                this.range.hashCode() * 31, 31, this.totalUsage),
                        31,
                        this.foregroundUsage);
    }

    public final String toString() {
        return "NetworkUsageDetailsData(range="
                + this.range
                + ", totalUsage="
                + this.totalUsage
                + ", foregroundUsage="
                + this.foregroundUsage
                + ", backgroundUsage="
                + this.backgroundUsage
                + ")";
    }
}
