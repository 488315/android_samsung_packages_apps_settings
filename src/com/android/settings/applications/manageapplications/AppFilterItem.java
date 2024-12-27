package com.android.settings.applications.manageapplications;

import com.android.settingslib.applications.ApplicationsState;

import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppFilterItem implements Comparable {
    public final ApplicationsState.AppFilter mFilter;
    public final int mFilterType;
    public final int mTitle;

    public AppFilterItem(ApplicationsState.AppFilter appFilter, int i, int i2) {
        this.mTitle = i2;
        this.mFilterType = i;
        this.mFilter = appFilter;
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        AppFilterItem appFilterItem = (AppFilterItem) obj;
        if (appFilterItem == null) {
            return 1;
        }
        if (this == appFilterItem) {
            return 0;
        }
        return this.mFilterType - appFilterItem.mFilterType;
    }

    public final boolean equals(Object obj) {
        if (obj == null || !(obj instanceof AppFilterItem)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        AppFilterItem appFilterItem = (AppFilterItem) obj;
        return this.mTitle == appFilterItem.mTitle
                && this.mFilterType == appFilterItem.mFilterType
                && this.mFilter == appFilterItem.mFilter;
    }

    public final int hashCode() {
        return Objects.hash(
                this.mFilter, Integer.valueOf(this.mTitle), Integer.valueOf(this.mFilterType));
    }
}
