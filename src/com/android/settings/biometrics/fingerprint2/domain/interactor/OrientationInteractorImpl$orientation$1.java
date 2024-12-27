package com.android.settings.biometrics.fingerprint2.domain.interactor;

import android.content.Context;
import android.view.OrientationEventListener;
import com.samsung.android.knox.net.apn.ApnSettings;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerCoroutine;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u008a@"}, d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "Lkotlinx/coroutines/channels/ProducerScope;", ApnSettings.MVNO_NONE}, k = 3, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes2.dex */
final class OrientationInteractorImpl$orientation$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ OrientationInteractorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public OrientationInteractorImpl$orientation$1(OrientationInteractorImpl orientationInteractorImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = orientationInteractorImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        OrientationInteractorImpl$orientation$1 orientationInteractorImpl$orientation$1 = new OrientationInteractorImpl$orientation$1(this.this$0, continuation);
        orientationInteractorImpl$orientation$1.L$0 = obj;
        return orientationInteractorImpl$orientation$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((OrientationInteractorImpl$orientation$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [android.view.OrientationEventListener, com.android.settings.biometrics.fingerprint2.domain.interactor.OrientationInteractorImpl$orientation$1$orientationEventListener$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final Context context = this.this$0.context;
            final ?? r3 = new OrientationEventListener(context) { // from class: com.android.settings.biometrics.fingerprint2.domain.interactor.OrientationInteractorImpl$orientation$1$orientationEventListener$1
                @Override // android.view.OrientationEventListener
                public final void onOrientationChanged(int i2) {
                    ((ProducerCoroutine) ProducerScope.this).mo1469trySendJP2dKIU(Integer.valueOf(i2));
                }
            };
            r3.enable();
            Function0 function0 = new Function0() { // from class: com.android.settings.biometrics.fingerprint2.domain.interactor.OrientationInteractorImpl$orientation$1.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* renamed from: invoke */
                public final Object mo1068invoke() {
                    disable();
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
