package com.android.wifitrackerlib;

import android.net.wifi.WifiConfiguration;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class SavedNetworkTracker$$ExternalSyntheticLambda11
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return !((WifiConfiguration) obj).carrierMerged;
    }
}
