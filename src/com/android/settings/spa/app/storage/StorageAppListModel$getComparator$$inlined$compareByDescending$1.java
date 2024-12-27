package com.android.settings.spa.app.storage;

import com.android.settingslib.spaprivileged.model.app.AppEntry;

import kotlin.comparisons.ComparisonsKt__ComparisonsKt;

import java.util.Comparator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class StorageAppListModel$getComparator$$inlined$compareByDescending$1
        implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return ComparisonsKt__ComparisonsKt.compareValues(
                Long.valueOf(((AppRecordWithSize) ((AppEntry) obj2).record).size),
                Long.valueOf(((AppRecordWithSize) ((AppEntry) obj).record).size));
    }
}
