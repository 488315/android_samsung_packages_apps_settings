package com.android.settings.network.telephony.gsm;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public /* synthetic */ class AutoSelectPreferenceController$Content$isAuto$2
        extends AdaptedFunctionReference implements Function2 {
    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Object Content$updateListenerValue;
        Content$updateListenerValue =
                AutoSelectPreferenceController.Content$updateListenerValue(
                        (AutoSelectPreferenceController) this.receiver,
                        ((Boolean) obj).booleanValue(),
                        (Continuation) obj2);
        return Content$updateListenerValue;
    }
}
