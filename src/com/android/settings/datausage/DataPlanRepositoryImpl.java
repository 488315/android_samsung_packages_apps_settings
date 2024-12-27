package com.android.settings.datausage;

import com.android.settings.datausage.lib.INetworkCycleDataRepository;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DataPlanRepositoryImpl {
    public final INetworkCycleDataRepository networkCycleDataRepository;

    public DataPlanRepositoryImpl(INetworkCycleDataRepository networkCycleDataRepository) {
        Intrinsics.checkNotNullParameter(networkCycleDataRepository, "networkCycleDataRepository");
        this.networkCycleDataRepository = networkCycleDataRepository;
    }
}
