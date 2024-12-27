package com.android.settings.system;

import android.content.Context;
import android.os.SystemUpdateManager;
import android.util.Log;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\n\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\u008a@"
        },
        d2 = {"<anonymous>", "Landroid/os/Bundle;", "Lkotlinx/coroutines/CoroutineScope;"},
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class SystemUpdateManagerExtKt$getSystemUpdateInfo$2 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ Context $this_getSystemUpdateInfo;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SystemUpdateManagerExtKt$getSystemUpdateInfo$2(
            Context context, Continuation continuation) {
        super(2, continuation);
        this.$this_getSystemUpdateInfo = context;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SystemUpdateManagerExtKt$getSystemUpdateInfo$2(
                this.$this_getSystemUpdateInfo, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SystemUpdateManagerExtKt$getSystemUpdateInfo$2)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Object systemService =
                this.$this_getSystemUpdateInfo.getSystemService(
                        (Class<Object>) SystemUpdateManager.class);
        Intrinsics.checkNotNull(systemService);
        try {
            return ((SystemUpdateManager) systemService).retrieveSystemUpdateInfo();
        } catch (Exception unused) {
            Log.w("SystemUpdateManagerExt", "Error getting system update info.");
            return null;
        }
    }
}
