package com.android.settings.deviceinfo.simstatus;

import android.content.Context;

import com.android.settings.network.telephony.ServiceStateFlowKt;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SignalStrengthRepository {
    public final Context context;
    public final Function1 serviceStateFlowFactory;

    public SignalStrengthRepository(final Context context) {
        Function1 function1 =
                new Function1() { // from class:
                                  // com.android.settings.deviceinfo.simstatus.SignalStrengthRepository.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return ServiceStateFlowKt.serviceStateFlow(
                                context, ((Number) obj).intValue());
                    }
                };
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.serviceStateFlowFactory = function1;
    }
}
