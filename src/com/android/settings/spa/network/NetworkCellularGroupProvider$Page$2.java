package com.android.settings.spa.network;

import androidx.compose.runtime.MutableIntState;
import androidx.compose.runtime.SnapshotMutableIntStateImpl;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\b\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u008a@"
        },
        d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "it"},
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class NetworkCellularGroupProvider$Page$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Ref$ObjectRef<MutableIntState> $callsSelectedId;
    final /* synthetic */ Ref$ObjectRef<MutableIntState> $mobileDataSelectedId;
    final /* synthetic */ Ref$ObjectRef<MutableIntState> $nonDdsRemember;
    final /* synthetic */ Ref$ObjectRef<MutableIntState> $textsSelectedId;
    int label;
    final /* synthetic */ NetworkCellularGroupProvider this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NetworkCellularGroupProvider$Page$2(
            Ref$ObjectRef ref$ObjectRef,
            NetworkCellularGroupProvider networkCellularGroupProvider,
            Ref$ObjectRef ref$ObjectRef2,
            Ref$ObjectRef ref$ObjectRef3,
            Ref$ObjectRef ref$ObjectRef4,
            Continuation continuation) {
        super(2, continuation);
        this.$callsSelectedId = ref$ObjectRef;
        this.this$0 = networkCellularGroupProvider;
        this.$textsSelectedId = ref$ObjectRef2;
        this.$mobileDataSelectedId = ref$ObjectRef3;
        this.$nonDdsRemember = ref$ObjectRef4;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new NetworkCellularGroupProvider$Page$2(
                this.$callsSelectedId,
                this.this$0,
                this.$textsSelectedId,
                this.$mobileDataSelectedId,
                this.$nonDdsRemember,
                continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        NetworkCellularGroupProvider$Page$2 networkCellularGroupProvider$Page$2 =
                (NetworkCellularGroupProvider$Page$2) create((Unit) obj, (Continuation) obj2);
        Unit unit = Unit.INSTANCE;
        networkCellularGroupProvider$Page$2.invokeSuspend(unit);
        return unit;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ((SnapshotMutableIntStateImpl) this.$callsSelectedId.element)
                .setIntValue(this.this$0.defaultVoiceSubId);
        ((SnapshotMutableIntStateImpl) this.$textsSelectedId.element)
                .setIntValue(this.this$0.defaultSmsSubId);
        ((SnapshotMutableIntStateImpl) this.$mobileDataSelectedId.element)
                .setIntValue(this.this$0.defaultDataSubId);
        ((SnapshotMutableIntStateImpl) this.$nonDdsRemember.element)
                .setIntValue(this.this$0.nonDds);
        return Unit.INSTANCE;
    }
}
