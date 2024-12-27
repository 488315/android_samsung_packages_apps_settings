package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothCodecConfig;

import java.util.Comparator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class A2dpProfile$$ExternalSyntheticLambda0 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return ((BluetoothCodecConfig) obj2).getCodecPriority()
                - ((BluetoothCodecConfig) obj).getCodecPriority();
    }
}
