package com.android.settingslib.spaprivileged.framework.compose;

import android.content.Intent;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final /* synthetic */
class DisposableBroadcastReceiverAsUserKt$DisposableBroadcastReceiverAsUser$1$1
        extends FunctionReferenceImpl implements Function2 {
    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        ((Function1) this.receiver).invoke((Intent) obj);
        return Unit.INSTANCE;
    }
}
