package com.android.settingslib.spaprivileged.template.app;

import android.content.pm.ApplicationInfo;

import com.android.settingslib.spaprivileged.model.app.AppRecord;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0010\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00020\u0004H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "T",
            "Lcom/android/settingslib/spaprivileged/model/app/AppRecord;",
            "Lkotlinx/coroutines/flow/FlowCollector;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class TogglePermissionAppInfoPageKt$rememberRecord$1$1 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ ApplicationInfo $app;
    final /* synthetic */ TogglePermissionAppListModel $this_rememberRecord;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TogglePermissionAppInfoPageKt$rememberRecord$1$1(
            TogglePermissionAppListModel togglePermissionAppListModel,
            ApplicationInfo applicationInfo,
            Continuation continuation) {
        super(2, continuation);
        this.$this_rememberRecord = togglePermissionAppListModel;
        this.$app = applicationInfo;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        TogglePermissionAppInfoPageKt$rememberRecord$1$1
                togglePermissionAppInfoPageKt$rememberRecord$1$1 =
                        new TogglePermissionAppInfoPageKt$rememberRecord$1$1(
                                this.$this_rememberRecord, this.$app, continuation);
        togglePermissionAppInfoPageKt$rememberRecord$1$1.L$0 = obj;
        return togglePermissionAppInfoPageKt$rememberRecord$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TogglePermissionAppInfoPageKt$rememberRecord$1$1)
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
            AppRecord transformItem = this.$this_rememberRecord.transformItem(this.$app);
            this.label = 1;
            if (flowCollector.emit(transformItem, this) == coroutineSingletons) {
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
