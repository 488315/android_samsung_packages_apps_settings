package com.android.settingslib.spaprivileged.template.app;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppListState {
    public final Function0 searchQuery;
    public final Function0 showSystem;

    public AppListState(Function0 showSystem, Function0 searchQuery) {
        Intrinsics.checkNotNullParameter(showSystem, "showSystem");
        Intrinsics.checkNotNullParameter(searchQuery, "searchQuery");
        this.showSystem = showSystem;
        this.searchQuery = searchQuery;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppListState)) {
            return false;
        }
        AppListState appListState = (AppListState) obj;
        return Intrinsics.areEqual(this.showSystem, appListState.showSystem)
                && Intrinsics.areEqual(this.searchQuery, appListState.searchQuery);
    }

    public final int hashCode() {
        return this.searchQuery.hashCode() + (this.showSystem.hashCode() * 31);
    }

    public final String toString() {
        return "AppListState(showSystem="
                + this.showSystem
                + ", searchQuery="
                + this.searchQuery
                + ")";
    }
}
