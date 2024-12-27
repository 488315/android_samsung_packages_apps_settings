package com.samsung.android.settings.analyzestorage.domain.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class CollectionUtils {
    public static List getEmptyListIfNull(List list) {
        return list == null ? Collections.emptyList() : list;
    }

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }
}
