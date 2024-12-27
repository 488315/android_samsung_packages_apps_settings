package com.android.settings.slices;

import android.content.Context;
import android.net.Uri;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SlicesFeatureProviderImpl {
    public SliceDataConverter mSliceDataConverter;
    public SlicesIndexer mSlicesIndexer;
    public long mUiSessionToken;

    public static CustomSliceable getSliceableFromUri(Context context, Uri uri) {
        Uri uri2 = CustomSliceRegistry.CONTEXTUAL_ADAPTIVE_SLEEP_URI;
        Uri build = uri != null ? uri.buildUpon().clearQuery().build() : null;
        Class<? extends CustomSliceable> cls =
                CustomSliceRegistry.sUriToSlice.get(
                        build != null ? build.buildUpon().clearQuery().build() : null);
        if (cls != null) {
            return CustomSliceable.createInstance(context, cls);
        }
        throw new IllegalArgumentException("No Slice found for uri: " + uri);
    }
}
