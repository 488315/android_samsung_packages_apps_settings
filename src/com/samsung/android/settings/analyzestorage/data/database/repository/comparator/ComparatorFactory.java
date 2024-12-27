package com.samsung.android.settings.analyzestorage.data.database.repository.comparator;

import com.samsung.android.settings.analyzestorage.domain.log.Log;

import java.util.Comparator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class ComparatorFactory {
    public static final Comparator getAppSortComparator(int i, boolean z) {
        if (i == 0) {
            return new AppSizeComparator(z);
        }
        if (i == 1) {
            return new AppNameComparator(z);
        }
        if (i == 2) {
            return new LastLaunchTimeComparator(z);
        }
        Log.d(
                "ComparatorFactory",
                "getSortByComparator() ] sortByType(" + i + ") is not supported");
        return new DefaultComparator();
    }
}
