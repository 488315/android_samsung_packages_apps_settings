package com.android.settings.network;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.AdaptedFunctionReference;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public /* synthetic */ class NetworkProviderCallsSmsController$summaryFlow$1
        extends AdaptedFunctionReference implements Function4 {
    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        Object summaryFlow$getSummary;
        summaryFlow$getSummary =
                NetworkProviderCallsSmsController.summaryFlow$getSummary(
                        (NetworkProviderCallsSmsController) this.receiver,
                        (List) obj,
                        ((Number) obj2).intValue(),
                        ((Number) obj3).intValue(),
                        (Continuation) obj4);
        return summaryFlow$getSummary;
    }
}
