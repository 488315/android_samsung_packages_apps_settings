package com.android.settings.network.telephony;

import android.telephony.SubscriptionManager;

import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\n\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"
        },
        d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "Lkotlinx/coroutines/CoroutineScope;"},
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class VoNrRepository$setVoNrEnabled$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $enabled;
    int label;
    final /* synthetic */ VoNrRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VoNrRepository$setVoNrEnabled$2(
            VoNrRepository voNrRepository, boolean z, Continuation continuation) {
        super(2, continuation);
        this.this$0 = voNrRepository;
        this.$enabled = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new VoNrRepository$setVoNrEnabled$2(this.this$0, this.$enabled, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((VoNrRepository$setVoNrEnabled$2) create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean isValidSubscriptionId =
                SubscriptionManager.isValidSubscriptionId(this.this$0.subId);
        Unit unit = Unit.INSTANCE;
        if (!isValidSubscriptionId) {
            return unit;
        }
        int voNrEnabled = this.this$0.telephonyManager.setVoNrEnabled(this.$enabled);
        int i = this.this$0.subId;
        boolean z = this.$enabled;
        StringBuilder sb = new StringBuilder("[");
        sb.append(i);
        sb.append("] setVoNrEnabled: ");
        sb.append(z);
        sb.append(", result: ");
        Preference$$ExternalSyntheticOutline0.m(sb, voNrEnabled, "VoNrRepository");
        return unit;
    }
}
