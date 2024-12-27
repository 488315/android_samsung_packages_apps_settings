package com.android.settingslib.spaprivileged.model.app;

import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppListData {
    public final List appEntries;
    public final int option;

    public AppListData(int i, List appEntries) {
        Intrinsics.checkNotNullParameter(appEntries, "appEntries");
        this.appEntries = appEntries;
        this.option = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppListData)) {
            return false;
        }
        AppListData appListData = (AppListData) obj;
        return Intrinsics.areEqual(this.appEntries, appListData.appEntries)
                && this.option == appListData.option;
    }

    public final int hashCode() {
        return Integer.hashCode(this.option) + (this.appEntries.hashCode() * 31);
    }

    public final String toString() {
        return "AppListData(appEntries=" + this.appEntries + ", option=" + this.option + ")";
    }
}
