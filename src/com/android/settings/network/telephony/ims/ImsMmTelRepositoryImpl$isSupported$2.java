package com.android.settings.network.telephony.ims;

import android.util.Log;

import androidx.compose.foundation.text.CoreTextFieldKt$CoreTextField$semanticsModifier$1$1$3$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.ExecutorsKt;

import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u000b\n"
                + "\u0002\b\u0003\u0010\u0003\u001a\n"
                + " \u0002*\u0004\u0018\u00010\u00010\u0001*\u00020\u0000H\u008a@¢\u0006\u0004\b\u0003\u0010\u0004"
        },
        d2 = {
            "Lkotlinx/coroutines/CoroutineScope;",
            ApnSettings.MVNO_NONE,
            "kotlin.jvm.PlatformType",
            "<anonymous>",
            "(Lkotlinx/coroutines/CoroutineScope;)Z"
        },
        k = 3,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
final class ImsMmTelRepositoryImpl$isSupported$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $capability;
    final /* synthetic */ int $transportType;
    int I$0;
    int I$1;
    Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ ImsMmTelRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ImsMmTelRepositoryImpl$isSupported$2(
            int i,
            int i2,
            ImsMmTelRepositoryImpl imsMmTelRepositoryImpl,
            Continuation continuation) {
        super(2, continuation);
        this.$capability = i;
        this.$transportType = i2;
        this.this$0 = imsMmTelRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ImsMmTelRepositoryImpl$isSupported$2(
                this.$capability, this.$transportType, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ImsMmTelRepositoryImpl$isSupported$2)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        String str;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            String m =
                    CoreTextFieldKt$CoreTextField$semanticsModifier$1$1$3$$ExternalSyntheticOutline0
                            .m(
                                    "isSupported(capability=",
                                    ",transportType=",
                                    this.$capability,
                                    this.$transportType,
                                    ")");
            ImsMmTelRepositoryImpl imsMmTelRepositoryImpl = this.this$0;
            int i2 = this.$capability;
            int i3 = this.$transportType;
            this.L$0 = m;
            this.L$1 = imsMmTelRepositoryImpl;
            this.I$0 = i2;
            this.I$1 = i3;
            this.label = 1;
            final CancellableContinuationImpl cancellableContinuationImpl =
                    new CancellableContinuationImpl(
                            1, IntrinsicsKt__IntrinsicsJvmKt.intercepted(this));
            cancellableContinuationImpl.initCancellability();
            try {
                imsMmTelRepositoryImpl.imsMmTelManager.isSupported(
                        i2,
                        i3,
                        ExecutorsKt.asExecutor(Dispatchers.Default),
                        new Consumer() { // from class:
                                         // com.android.settings.network.telephony.ims.ImsMmTelRepositoryImpl$isSupported$2$1$1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj2) {
                                cancellableContinuationImpl.resumeWith((Boolean) obj2);
                            }
                        });
            } catch (Exception e) {
                cancellableContinuationImpl.resumeWith(Boolean.FALSE);
                Log.w(
                        "ImsMmTelRepository",
                        "[" + imsMmTelRepositoryImpl.subId + "] " + m + " failed",
                        e);
            }
            Object result = cancellableContinuationImpl.getResult();
            CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (result == coroutineSingletons) {
                return coroutineSingletons;
            }
            str = m;
            obj = result;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            str = (String) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        Log.d("ImsMmTelRepository", "[" + this.this$0.subId + "] " + str + " = " + ((Boolean) obj));
        return obj;
    }
}
