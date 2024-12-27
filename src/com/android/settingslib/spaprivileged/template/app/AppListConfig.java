package com.android.settingslib.spaprivileged.template.app;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;

import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppListConfig {
    public final boolean matchAnyUserForAdmin;
    public final boolean showInstantApps;
    public final List userIds;

    public AppListConfig(List list, boolean z, boolean z2) {
        this.userIds = list;
        this.showInstantApps = z;
        this.matchAnyUserForAdmin = z2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppListConfig)) {
            return false;
        }
        AppListConfig appListConfig = (AppListConfig) obj;
        return Intrinsics.areEqual(this.userIds, appListConfig.userIds)
                && this.showInstantApps == appListConfig.showInstantApps
                && this.matchAnyUserForAdmin == appListConfig.matchAnyUserForAdmin;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.matchAnyUserForAdmin)
                + TransitionData$$ExternalSyntheticOutline0.m(
                        this.userIds.hashCode() * 31, 31, this.showInstantApps);
    }

    public final String toString() {
        List list = this.userIds;
        StringBuilder sb = new StringBuilder("AppListConfig(userIds=");
        sb.append(list);
        sb.append(", showInstantApps=");
        sb.append(this.showInstantApps);
        sb.append(", matchAnyUserForAdmin=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(
                sb, this.matchAnyUserForAdmin, ")");
    }
}
