package com.android.settings.connecteddevice.audiosharing;

import com.android.settingslib.bluetooth.LocalBluetoothProfile;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class AudioSharingDevicePreferenceController$$ExternalSyntheticLambda0
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        boolean lambda$isMediaDevice$0;
        lambda$isMediaDevice$0 =
                AudioSharingDevicePreferenceController.lambda$isMediaDevice$0(
                        (LocalBluetoothProfile) obj);
        return lambda$isMediaDevice$0;
    }
}
