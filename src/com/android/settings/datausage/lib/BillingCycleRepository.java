package com.android.settings.datausage.lib;

import android.content.Context;
import android.os.INetworkManagementService;
import android.os.ServiceManager;
import android.os.UserManager;

import com.android.settings.network.telephony.TelephonyRepository;
import com.android.settingslib.spaprivileged.framework.common.ContextsKt;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BillingCycleRepository {
    public final INetworkManagementService networkService;
    public final TelephonyRepository telephonyRepository;
    public final UserManager userManager;

    public BillingCycleRepository(Context context) {
        INetworkManagementService asInterface =
                INetworkManagementService.Stub.asInterface(
                        ServiceManager.getService("network_management"));
        Intrinsics.checkNotNullExpressionValue(asInterface, "asInterface(...)");
        TelephonyRepository telephonyRepository = new TelephonyRepository(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.networkService = asInterface;
        this.telephonyRepository = telephonyRepository;
        this.userManager = ContextsKt.getUserManager(context);
    }
}
