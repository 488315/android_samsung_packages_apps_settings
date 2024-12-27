package com.android.settings.slices;

import android.content.IntentFilter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public interface Sliceable {
    default Class getBackgroundWorkerClass() {
        return null;
    }

    default IntentFilter getIntentFilter() {
        return null;
    }

    int getSliceHighlightMenuRes();

    default boolean hasAsyncUpdate() {
        return false;
    }

    default boolean isPublicSlice() {
        return false;
    }

    default boolean isSliceable() {
        return false;
    }

    default boolean useDynamicSliceSummary() {
        return false;
    }
}
