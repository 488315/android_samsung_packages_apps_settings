package com.android.settings.spa.app.appinfo;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.SafeContinuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\n\n"
                + "\u0000\n"
                + "\u0002\u0010\u000b\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"
        },
        d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "Lkotlinx/coroutines/CoroutineScope;"},
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class DefaultAppShortcutPresenter$isVisible$1$1$roleVisible$1 extends SuspendLambda
        implements Function2 {
    int label;
    final /* synthetic */ DefaultAppShortcutPresenter this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DefaultAppShortcutPresenter$isVisible$1$1$roleVisible$1(
            DefaultAppShortcutPresenter defaultAppShortcutPresenter, Continuation continuation) {
        super(2, continuation);
        this.this$0 = defaultAppShortcutPresenter;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DefaultAppShortcutPresenter$isVisible$1$1$roleVisible$1(
                this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DefaultAppShortcutPresenter$isVisible$1$1$roleVisible$1)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            DefaultAppShortcutPresenter defaultAppShortcutPresenter = this.this$0;
            this.label = 1;
            defaultAppShortcutPresenter.getClass();
            SafeContinuation safeContinuation =
                    new SafeContinuation(IntrinsicsKt__IntrinsicsJvmKt.intercepted(this));
            defaultAppShortcutPresenter.roleManager.isRoleVisible(
                    defaultAppShortcutPresenter.roleName,
                    defaultAppShortcutPresenter.executor,
                    new DefaultAppShortcutPresenter$isRoleVisible$2$1(safeContinuation, 0));
            obj = safeContinuation.getOrThrow();
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return obj;
    }
}
