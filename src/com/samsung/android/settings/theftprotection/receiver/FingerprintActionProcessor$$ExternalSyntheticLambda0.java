package com.samsung.android.settings.theftprotection.receiver;

import com.samsung.android.settings.theftprotection.location.LocationData;
import com.samsung.android.settings.theftprotection.location.LocationStorage;

import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class FingerprintActionProcessor$$ExternalSyntheticLambda0
        implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        LocationData locationData = (LocationData) obj;
        locationData.mEnterLocation = 0;
        LocationStorage.InstanceHolder.INSTANCE.updateLocationData(locationData);
    }
}
