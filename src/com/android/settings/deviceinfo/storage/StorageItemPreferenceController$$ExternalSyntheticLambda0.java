package com.android.settings.deviceinfo.storage;

import com.android.settings.deviceinfo.StorageItemPreference;

import java.util.function.ToLongFunction;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class StorageItemPreferenceController$$ExternalSyntheticLambda0
        implements ToLongFunction {
    @Override // java.util.function.ToLongFunction
    public final long applyAsLong(Object obj) {
        return ((StorageItemPreference) obj).mStorageSize;
    }
}
