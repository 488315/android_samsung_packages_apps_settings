package com.android.settings.spa.app.appinfo;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppPermissionSummaryState {
    public final boolean enabled;
    public final String summary;

    public AppPermissionSummaryState(String summary, boolean z) {
        Intrinsics.checkNotNullParameter(summary, "summary");
        this.summary = summary;
        this.enabled = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppPermissionSummaryState)) {
            return false;
        }
        AppPermissionSummaryState appPermissionSummaryState = (AppPermissionSummaryState) obj;
        return Intrinsics.areEqual(this.summary, appPermissionSummaryState.summary)
                && this.enabled == appPermissionSummaryState.enabled;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.enabled) + (this.summary.hashCode() * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("AppPermissionSummaryState(summary=");
        sb.append(this.summary);
        sb.append(", enabled=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.enabled, ")");
    }
}
