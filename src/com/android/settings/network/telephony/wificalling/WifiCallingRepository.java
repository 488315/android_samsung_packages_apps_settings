package com.android.settings.network.telephony.wificalling;

import android.content.Context;
import android.telephony.CarrierConfigManager;
import android.telephony.TelephonyManager;

import com.android.settings.network.telephony.ims.ImsMmTelRepositoryImpl;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiCallingRepository {
    public final CarrierConfigManager carrierConfigManager;
    public final Context context;
    public final ImsMmTelRepositoryImpl imsMmTelRepository;
    public final int subId;
    public final TelephonyManager telephonyManager;

    public WifiCallingRepository(Context context, int i) {
        ImsMmTelRepositoryImpl imsMmTelRepositoryImpl = new ImsMmTelRepositoryImpl(context, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.subId = i;
        this.imsMmTelRepository = imsMmTelRepositoryImpl;
        Object systemService = context.getSystemService((Class<Object>) TelephonyManager.class);
        Intrinsics.checkNotNull(systemService);
        this.telephonyManager = ((TelephonyManager) systemService).createForSubscriptionId(i);
        Object systemService2 =
                context.getSystemService((Class<Object>) CarrierConfigManager.class);
        Intrinsics.checkNotNull(systemService2);
        this.carrierConfigManager = (CarrierConfigManager) systemService2;
    }
}
