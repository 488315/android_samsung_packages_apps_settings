package com.android.settingslib.search;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SearchIndexableData {
    public final Indexable$SearchIndexProvider mSearchIndexProvider;
    public final Class mTargetClass;

    public SearchIndexableData(
            Class cls, Indexable$SearchIndexProvider indexable$SearchIndexProvider) {
        this.mTargetClass = cls;
        this.mSearchIndexProvider = indexable$SearchIndexProvider;
    }
}
