package com.android.settings.datausage;

import com.android.settings.datausage.lib.NetworkUsageData;

import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SelectedBuckets {
    public final List buckets;
    public final NetworkUsageData selectedCycle;

    public SelectedBuckets(NetworkUsageData selectedCycle, List list) {
        Intrinsics.checkNotNullParameter(selectedCycle, "selectedCycle");
        this.selectedCycle = selectedCycle;
        this.buckets = list;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SelectedBuckets)) {
            return false;
        }
        SelectedBuckets selectedBuckets = (SelectedBuckets) obj;
        return Intrinsics.areEqual(this.selectedCycle, selectedBuckets.selectedCycle)
                && Intrinsics.areEqual(this.buckets, selectedBuckets.buckets);
    }

    public final int hashCode() {
        return this.buckets.hashCode() + (this.selectedCycle.hashCode() * 31);
    }

    public final String toString() {
        return "SelectedBuckets(selectedCycle="
                + this.selectedCycle
                + ", buckets="
                + this.buckets
                + ")";
    }
}
