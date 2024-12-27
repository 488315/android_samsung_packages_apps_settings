package com.android.settings.biometrics.fingerprint2.domain.interactor;

import android.content.Context;
import com.android.systemui.unfold.compat.ScreenSizeFoldProvider;
import com.android.systemui.unfold.updates.FoldProvider$FoldCallback;
import com.samsung.android.knox.net.apn.ApnSettings;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerCoroutine;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u008a@"}, d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "Lkotlinx/coroutines/channels/ProducerScope;", ApnSettings.MVNO_NONE}, k = 3, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes2.dex */
final class FoldStateInteractorImpl$isFolded$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Context $context;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ FoldStateInteractorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FoldStateInteractorImpl$isFolded$1(FoldStateInteractorImpl foldStateInteractorImpl, Context context, Continuation continuation) {
        super(2, continuation);
        this.this$0 = foldStateInteractorImpl;
        this.$context = context;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        FoldStateInteractorImpl$isFolded$1 foldStateInteractorImpl$isFolded$1 = new FoldStateInteractorImpl$isFolded$1(this.this$0, this.$context, continuation);
        foldStateInteractorImpl$isFolded$1.L$0 = obj;
        return foldStateInteractorImpl$isFolded$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FoldStateInteractorImpl$isFolded$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.settings.biometrics.fingerprint2.domain.interactor.FoldStateInteractorImpl$isFolded$1$foldStateListener$1, com.android.systemui.unfold.updates.FoldProvider$FoldCallback] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new FoldProvider$FoldCallback() { // from class: com.android.settings.biometrics.fingerprint2.domain.interactor.FoldStateInteractorImpl$isFolded$1$foldStateListener$1
                @Override // com.android.systemui.unfold.updates.FoldProvider$FoldCallback
                public final void onFoldUpdated(boolean z) {
                    ((ProducerCoroutine) ProducerScope.this).mo1469trySendJP2dKIU(Boolean.valueOf(z));
                }
            };
            ScreenSizeFoldProvider screenSizeFoldProvider = this.this$0.screenSizeFoldProvider;
            Executor mainExecutor = this.$context.getMainExecutor();
            Intrinsics.checkNotNullExpressionValue(mainExecutor, "getMainExecutor(...)");
            screenSizeFoldProvider.registerCallback(r1, mainExecutor);
            final FoldStateInteractorImpl foldStateInteractorImpl = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.settings.biometrics.fingerprint2.domain.interactor.FoldStateInteractorImpl$isFolded$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* renamed from: invoke */
                public final Object mo1068invoke() {
                    ScreenSizeFoldProvider screenSizeFoldProvider2 = FoldStateInteractorImpl.this.screenSizeFoldProvider;
                    FoldProvider$FoldCallback callback = r1;
                    screenSizeFoldProvider2.getClass();
                    Intrinsics.checkNotNullParameter(callback, "callback");
                    ((ArrayList) screenSizeFoldProvider2.callbacks).remove(callback);
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
