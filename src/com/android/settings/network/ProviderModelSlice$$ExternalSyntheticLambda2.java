package com.android.settings.network;

import com.android.settings.wifi.slice.WifiSliceItem;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class ProviderModelSlice$$ExternalSyntheticLambda2
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((WifiSliceItem) obj).mConnectedState != 2;
    }
}
