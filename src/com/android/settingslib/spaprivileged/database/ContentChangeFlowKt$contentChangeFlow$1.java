package com.android.settingslib.spaprivileged.database;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
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
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00010\u0002H\u008a@"}, d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "Lkotlinx/coroutines/channels/ProducerScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes2.dex */
final class ContentChangeFlowKt$contentChangeFlow$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $sendInitial;
    final /* synthetic */ Context $this_contentChangeFlow;
    final /* synthetic */ Uri $uri;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ContentChangeFlowKt$contentChangeFlow$1(Context context, Uri uri, boolean z, Continuation continuation) {
        super(2, continuation);
        this.$this_contentChangeFlow = context;
        this.$uri = uri;
        this.$sendInitial = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ContentChangeFlowKt$contentChangeFlow$1 contentChangeFlowKt$contentChangeFlow$1 = new ContentChangeFlowKt$contentChangeFlow$1(this.$this_contentChangeFlow, this.$uri, this.$sendInitial, continuation);
        contentChangeFlowKt$contentChangeFlow$1.L$0 = obj;
        return contentChangeFlowKt$contentChangeFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ContentChangeFlowKt$contentChangeFlow$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.database.ContentObserver, com.android.settingslib.spaprivileged.database.ContentChangeFlowKt$contentChangeFlow$1$contentObserver$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        Unit unit = Unit.INSTANCE;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new ContentObserver() { // from class: com.android.settingslib.spaprivileged.database.ContentChangeFlowKt$contentChangeFlow$1$contentObserver$1
                {
                    super(null);
                }

                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    ((ProducerCoroutine) ProducerScope.this).mo1469trySendJP2dKIU(Unit.INSTANCE);
                }
            };
            this.$this_contentChangeFlow.getContentResolver().registerContentObserver(this.$uri, false, r1);
            if (this.$sendInitial) {
                ((ProducerCoroutine) producerScope).mo1469trySendJP2dKIU(unit);
            }
            final Context context = this.$this_contentChangeFlow;
            Function0 function0 = new Function0() { // from class: com.android.settingslib.spaprivileged.database.ContentChangeFlowKt$contentChangeFlow$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* renamed from: invoke */
                public final Object mo1068invoke() {
                    context.getContentResolver().unregisterContentObserver(r1);
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
        return unit;
    }
}
