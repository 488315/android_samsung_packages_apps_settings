package com.android.settings.datausage;

import androidx.compose.animation.Scale$$ExternalSyntheticOutline0;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DataPlanInfo {
    public final Long cycleEnd;
    public final Long cycleStart;
    public final long dataBarSize;
    public final int dataPlanCount;
    public final long dataPlanSize;
    public final long dataPlanUse;
    public final long snapshotTime;

    public DataPlanInfo(int i, long j, long j2, long j3, Long l, long j4, Long l2) {
        this.dataPlanCount = i;
        this.dataPlanSize = j;
        this.dataBarSize = j2;
        this.dataPlanUse = j3;
        this.cycleEnd = l;
        this.snapshotTime = j4;
        this.cycleStart = l2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DataPlanInfo)) {
            return false;
        }
        DataPlanInfo dataPlanInfo = (DataPlanInfo) obj;
        return this.dataPlanCount == dataPlanInfo.dataPlanCount
                && this.dataPlanSize == dataPlanInfo.dataPlanSize
                && this.dataBarSize == dataPlanInfo.dataBarSize
                && this.dataPlanUse == dataPlanInfo.dataPlanUse
                && Intrinsics.areEqual(this.cycleEnd, dataPlanInfo.cycleEnd)
                && this.snapshotTime == dataPlanInfo.snapshotTime
                && Intrinsics.areEqual(this.cycleStart, dataPlanInfo.cycleStart);
    }

    public final int hashCode() {
        int m =
                Scale$$ExternalSyntheticOutline0.m(
                        Scale$$ExternalSyntheticOutline0.m(
                                Scale$$ExternalSyntheticOutline0.m(
                                        Integer.hashCode(this.dataPlanCount) * 31,
                                        31,
                                        this.dataPlanSize),
                                31,
                                this.dataBarSize),
                        31,
                        this.dataPlanUse);
        Long l = this.cycleEnd;
        int m2 =
                Scale$$ExternalSyntheticOutline0.m(
                        (m + (l == null ? 0 : l.hashCode())) * 31, 31, this.snapshotTime);
        Long l2 = this.cycleStart;
        return m2 + (l2 != null ? l2.hashCode() : 0);
    }

    public final String toString() {
        return "DataPlanInfo(dataPlanCount="
                + this.dataPlanCount
                + ", dataPlanSize="
                + this.dataPlanSize
                + ", dataBarSize="
                + this.dataBarSize
                + ", dataPlanUse="
                + this.dataPlanUse
                + ", cycleEnd="
                + this.cycleEnd
                + ", snapshotTime="
                + this.snapshotTime
                + ", cycleStart="
                + this.cycleStart
                + ")";
    }
}
