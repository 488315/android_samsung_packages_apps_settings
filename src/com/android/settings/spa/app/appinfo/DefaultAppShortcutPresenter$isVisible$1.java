package com.android.settings.spa.app.appinfo;

import androidx.lifecycle.LiveDataScope;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u000e\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u000b\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "Landroidx/lifecycle/LiveDataScope;",
            ApnSettings.MVNO_NONE
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class DefaultAppShortcutPresenter$isVisible$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DefaultAppShortcutPresenter this$0;

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
    /* renamed from: com.android.settings.spa.app.appinfo.DefaultAppShortcutPresenter$isVisible$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ LiveDataScope $$this$liveData;
        private /* synthetic */ Object L$0;
        Object L$1;
        int label;
        final /* synthetic */ DefaultAppShortcutPresenter this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(
                LiveDataScope liveDataScope,
                DefaultAppShortcutPresenter defaultAppShortcutPresenter,
                Continuation continuation) {
            super(2, continuation);
            this.$$this$liveData = liveDataScope;
            this.this$0 = defaultAppShortcutPresenter;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 =
                    new AnonymousClass1(this.$$this$liveData, this.this$0, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2))
                    .invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:14:0x0080, code lost:

           if (((java.lang.Boolean) r10).booleanValue() == false) goto L24;
        */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r10) {
            /*
                r9 = this;
                kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r1 = r9.label
                r2 = 3
                r3 = 2
                r4 = 1
                r5 = 0
                if (r1 == 0) goto L31
                if (r1 == r4) goto L25
                if (r1 == r3) goto L1d
                if (r1 != r2) goto L15
                kotlin.ResultKt.throwOnFailure(r10)
                goto L97
            L15:
                java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
                r9.<init>(r10)
                throw r9
            L1d:
                java.lang.Object r1 = r9.L$0
                androidx.lifecycle.LiveDataScope r1 = (androidx.lifecycle.LiveDataScope) r1
                kotlin.ResultKt.throwOnFailure(r10)
                goto L75
            L25:
                java.lang.Object r1 = r9.L$1
                androidx.lifecycle.LiveDataScope r1 = (androidx.lifecycle.LiveDataScope) r1
                java.lang.Object r6 = r9.L$0
                kotlinx.coroutines.Deferred r6 = (kotlinx.coroutines.Deferred) r6
                kotlin.ResultKt.throwOnFailure(r10)
                goto L60
            L31:
                kotlin.ResultKt.throwOnFailure(r10)
                java.lang.Object r10 = r9.L$0
                kotlinx.coroutines.CoroutineScope r10 = (kotlinx.coroutines.CoroutineScope) r10
                com.android.settings.spa.app.appinfo.DefaultAppShortcutPresenter$isVisible$1$1$roleVisible$1 r1 = new com.android.settings.spa.app.appinfo.DefaultAppShortcutPresenter$isVisible$1$1$roleVisible$1
                com.android.settings.spa.app.appinfo.DefaultAppShortcutPresenter r6 = r9.this$0
                r1.<init>(r6, r5)
                kotlinx.coroutines.DeferredCoroutine r1 = kotlinx.coroutines.BuildersKt.async$default(r10, r1)
                com.android.settings.spa.app.appinfo.DefaultAppShortcutPresenter$isVisible$1$1$applicationVisibleForRole$1 r6 = new com.android.settings.spa.app.appinfo.DefaultAppShortcutPresenter$isVisible$1$1$applicationVisibleForRole$1
                com.android.settings.spa.app.appinfo.DefaultAppShortcutPresenter r7 = r9.this$0
                r6.<init>(r7, r5)
                kotlinx.coroutines.DeferredCoroutine r6 = kotlinx.coroutines.BuildersKt.async$default(r10, r6)
                androidx.lifecycle.LiveDataScope r10 = r9.$$this$liveData
                r9.L$0 = r6
                r9.L$1 = r10
                r9.label = r4
                java.lang.Object r1 = r1.awaitInternal(r9)
                if (r1 != r0) goto L5d
                return r0
            L5d:
                r8 = r1
                r1 = r10
                r10 = r8
            L60:
                java.lang.Boolean r10 = (java.lang.Boolean) r10
                boolean r10 = r10.booleanValue()
                if (r10 == 0) goto L83
                r9.L$0 = r1
                r9.L$1 = r5
                r9.label = r3
                java.lang.Object r10 = r6.await(r9)
                if (r10 != r0) goto L75
                return r0
            L75:
                java.lang.String r3 = "await(...)"
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r10, r3)
                java.lang.Boolean r10 = (java.lang.Boolean) r10
                boolean r10 = r10.booleanValue()
                if (r10 == 0) goto L83
                goto L84
            L83:
                r4 = 0
            L84:
                java.lang.Boolean r10 = java.lang.Boolean.valueOf(r4)
                r9.L$0 = r5
                r9.L$1 = r5
                r9.label = r2
                androidx.lifecycle.LiveDataScopeImpl r1 = (androidx.lifecycle.LiveDataScopeImpl) r1
                java.lang.Object r9 = r1.emit(r10, r9)
                if (r9 != r0) goto L97
                return r0
            L97:
                kotlin.Unit r9 = kotlin.Unit.INSTANCE
                return r9
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.settings.spa.app.appinfo.DefaultAppShortcutPresenter$isVisible$1.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DefaultAppShortcutPresenter$isVisible$1(
            DefaultAppShortcutPresenter defaultAppShortcutPresenter, Continuation continuation) {
        super(2, continuation);
        this.this$0 = defaultAppShortcutPresenter;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DefaultAppShortcutPresenter$isVisible$1 defaultAppShortcutPresenter$isVisible$1 =
                new DefaultAppShortcutPresenter$isVisible$1(this.this$0, continuation);
        defaultAppShortcutPresenter$isVisible$1.L$0 = obj;
        return defaultAppShortcutPresenter$isVisible$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DefaultAppShortcutPresenter$isVisible$1)
                        create((LiveDataScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            AnonymousClass1 anonymousClass1 =
                    new AnonymousClass1((LiveDataScope) this.L$0, this.this$0, null);
            this.label = 1;
            if (CoroutineScopeKt.coroutineScope(anonymousClass1, this) == coroutineSingletons) {
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
