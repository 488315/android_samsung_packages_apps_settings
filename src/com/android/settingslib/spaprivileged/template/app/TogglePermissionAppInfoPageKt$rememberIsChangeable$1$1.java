package com.android.settingslib.spaprivileged.template.app;

import com.android.settingslib.spaprivileged.model.app.AppRecord;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0014\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u000b\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\b\u0012\u0004\u0012\u00020\u00050\u0004H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "T",
            "Lcom/android/settingslib/spaprivileged/model/app/AppRecord;",
            "Lkotlinx/coroutines/flow/FlowCollector;",
            ApnSettings.MVNO_NONE
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class TogglePermissionAppInfoPageKt$rememberIsChangeable$1$1 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ AppRecord $record;
    final /* synthetic */ TogglePermissionAppListModel $this_rememberIsChangeable;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TogglePermissionAppInfoPageKt$rememberIsChangeable$1$1(
            TogglePermissionAppListModel togglePermissionAppListModel,
            AppRecord appRecord,
            Continuation continuation) {
        super(2, continuation);
        this.$this_rememberIsChangeable = togglePermissionAppListModel;
        this.$record = appRecord;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        TogglePermissionAppInfoPageKt$rememberIsChangeable$1$1
                togglePermissionAppInfoPageKt$rememberIsChangeable$1$1 =
                        new TogglePermissionAppInfoPageKt$rememberIsChangeable$1$1(
                                this.$this_rememberIsChangeable, this.$record, continuation);
        togglePermissionAppInfoPageKt$rememberIsChangeable$1$1.L$0 = obj;
        return togglePermissionAppInfoPageKt$rememberIsChangeable$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TogglePermissionAppInfoPageKt$rememberIsChangeable$1$1)
                        create((FlowCollector) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            TogglePermissionAppListModel togglePermissionAppListModel =
                    this.$this_rememberIsChangeable;
            AppRecord record = this.$record;
            Intrinsics.checkNotNullParameter(togglePermissionAppListModel, "<this>");
            Intrinsics.checkNotNullParameter(record, "record");
            Boolean valueOf =
                    Boolean.valueOf(
                            !TogglePermissionAppListKt.isSystemOrRootUid(record)
                                    && togglePermissionAppListModel.isChangeable(record));
            this.label = 1;
            if (flowCollector.emit(valueOf, this) == coroutineSingletons) {
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
