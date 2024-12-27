package com.android.settings.bluetooth;

import android.companion.AssociationInfo;

import java.util.function.ToLongFunction;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class BluetoothDetailsDataSyncController$$ExternalSyntheticLambda2
        implements ToLongFunction {
    @Override // java.util.function.ToLongFunction
    public final long applyAsLong(Object obj) {
        return ((AssociationInfo) obj).getTimeApprovedMs();
    }
}
