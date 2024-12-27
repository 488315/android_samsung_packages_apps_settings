package com.android.settings.network.telephony;

import android.content.Context;
import android.telephony.CarrierConfigManager;
import android.telephony.TelephonyManager;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class VoNrRepository {
    public final CarrierConfigManager carrierConfigManager;
    public final Context context;
    public final int subId;
    public final TelephonyManager telephonyManager;

    public VoNrRepository(Context context, int i) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.subId = i;
        this.telephonyManager = TelephonyRepositoryKt.telephonyManager(context, i);
        Object systemService = context.getSystemService((Class<Object>) CarrierConfigManager.class);
        Intrinsics.checkNotNull(systemService);
        this.carrierConfigManager = (CarrierConfigManager) systemService;
    }
}
