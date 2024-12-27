package com.android.settings.network.telephony;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleKt;

import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.StandaloneCoroutine;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class TelephonyStatusControlSession implements AutoCloseable {
    public final Set controllerSet;
    public final Collection controllers;
    public final StandaloneCoroutine job;

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
    /* renamed from: com.android.settings.network.telephony.TelephonyStatusControlSession$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

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
        /* renamed from: com.android.settings.network.telephony.TelephonyStatusControlSession$1$1, reason: invalid class name and collision with other inner class name */
        final class C00391 extends SuspendLambda implements Function2 {
            final /* synthetic */ AbstractPreferenceController $controller;
            int label;
            final /* synthetic */ TelephonyStatusControlSession this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00391(
                    TelephonyStatusControlSession telephonyStatusControlSession,
                    AbstractPreferenceController abstractPreferenceController,
                    Continuation continuation) {
                super(2, continuation);
                this.this$0 = telephonyStatusControlSession;
                this.$controller = abstractPreferenceController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00391(this.this$0, this.$controller, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C00391) create((CoroutineScope) obj, (Continuation) obj2))
                        .invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    TelephonyStatusControlSession telephonyStatusControlSession = this.this$0;
                    AbstractPreferenceController abstractPreferenceController = this.$controller;
                    this.label = 1;
                    if (TelephonyStatusControlSession.access$setupAvailabilityStatus(
                                    telephonyStatusControlSession,
                                    abstractPreferenceController,
                                    this)
                            == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException(
                                "call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                return Unit.INSTANCE;
            }
        }

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 =
                    TelephonyStatusControlSession.this.new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 =
                    (AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2);
            Unit unit = Unit.INSTANCE;
            anonymousClass1.invokeSuspend(unit);
            return unit;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            Iterator it = TelephonyStatusControlSession.this.controllers.iterator();
            while (it.hasNext()) {
                BuildersKt.launch$default(
                        coroutineScope,
                        null,
                        null,
                        new C00391(
                                TelephonyStatusControlSession.this,
                                (AbstractPreferenceController) it.next(),
                                null),
                        3);
            }
            return Unit.INSTANCE;
        }
    }

    public TelephonyStatusControlSession(Collection collection, Lifecycle lifecycle) {
        Intrinsics.checkNotNullParameter(lifecycle, "lifecycle");
        this.controllers = collection;
        this.controllerSet = Collections.newSetFromMap(new ConcurrentHashMap());
        this.job =
                BuildersKt.launch$default(
                        LifecycleKt.getCoroutineScope(lifecycle),
                        Dispatchers.Default,
                        null,
                        new AnonymousClass1(null),
                        2);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(11:0|1|(2:3|(8:5|6|7|(1:(3:10|11|12)(2:20|21))(5:22|23|(2:25|(2:27|28))|17|18)|13|(1:15)|17|18))|31|6|7|(0)(0)|13|(0)|17|18) */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0036, code lost:

       r4 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x006b, code lost:

       android.util.Log.e("TelephonyStatusControlSS", "Setup availability status failed!", r4);
       r3 = false;
    */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0065 A[Catch: Exception -> 0x0036, TRY_LEAVE, TryCatch #0 {Exception -> 0x0036, blocks: (B:11:0x0030, B:13:0x005d, B:15:0x0065, B:23:0x0043, B:25:0x0047), top: B:7:0x0022 }] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$setupAvailabilityStatus(
            com.android.settings.network.telephony.TelephonyStatusControlSession r4,
            com.android.settingslib.core.AbstractPreferenceController r5,
            kotlin.coroutines.Continuation r6) {
        /*
            r4.getClass()
            boolean r0 = r6 instanceof com.android.settings.network.telephony.TelephonyStatusControlSession$setupAvailabilityStatus$1
            if (r0 == 0) goto L16
            r0 = r6
            com.android.settings.network.telephony.TelephonyStatusControlSession$setupAvailabilityStatus$1 r0 = (com.android.settings.network.telephony.TelephonyStatusControlSession$setupAvailabilityStatus$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.settings.network.telephony.TelephonyStatusControlSession$setupAvailabilityStatus$1 r0 = new com.android.settings.network.telephony.TelephonyStatusControlSession$setupAvailabilityStatus$1
            r0.<init>(r4, r6)
        L1b:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L40
            if (r2 != r3) goto L38
            int r4 = r0.I$0
            java.lang.Object r5 = r0.L$1
            com.android.settingslib.core.AbstractPreferenceController r5 = (com.android.settingslib.core.AbstractPreferenceController) r5
            java.lang.Object r0 = r0.L$0
            com.android.settings.network.telephony.TelephonyStatusControlSession r0 = (com.android.settings.network.telephony.TelephonyStatusControlSession) r0
            kotlin.ResultKt.throwOnFailure(r6)     // Catch: java.lang.Exception -> L36
            r6 = r4
            r4 = r0
            goto L5d
        L36:
            r4 = move-exception
            goto L6b
        L38:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L40:
            kotlin.ResultKt.throwOnFailure(r6)
            boolean r6 = r5 instanceof com.android.settings.network.telephony.TelephonyAvailabilityHandler     // Catch: java.lang.Exception -> L36
            if (r6 == 0) goto L73
            r6 = r5
            com.android.settings.core.BasePreferenceController r6 = (com.android.settings.core.BasePreferenceController) r6     // Catch: java.lang.Exception -> L36
            int r6 = r6.getAvailabilityStatus()     // Catch: java.lang.Exception -> L36
            r0.L$0 = r4     // Catch: java.lang.Exception -> L36
            r0.L$1 = r5     // Catch: java.lang.Exception -> L36
            r0.I$0 = r6     // Catch: java.lang.Exception -> L36
            r0.label = r3     // Catch: java.lang.Exception -> L36
            java.lang.Object r0 = kotlinx.coroutines.YieldKt.yield(r0)     // Catch: java.lang.Exception -> L36
            if (r0 != r1) goto L5d
            goto L77
        L5d:
            java.util.Set r4 = r4.controllerSet     // Catch: java.lang.Exception -> L36
            boolean r4 = r4.add(r5)     // Catch: java.lang.Exception -> L36
            if (r4 == 0) goto L73
            com.android.settings.network.telephony.TelephonyAvailabilityHandler r5 = (com.android.settings.network.telephony.TelephonyAvailabilityHandler) r5     // Catch: java.lang.Exception -> L36
            r5.setAvailabilityStatus(r6)     // Catch: java.lang.Exception -> L36
            goto L73
        L6b:
            java.lang.String r5 = "TelephonyStatusControlSS"
            java.lang.String r6 = "Setup availability status failed!"
            android.util.Log.e(r5, r6, r4)
            r3 = 0
        L73:
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r3)
        L77:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.network.telephony.TelephonyStatusControlSession.access$setupAvailabilityStatus(com.android.settings.network.telephony.TelephonyStatusControlSession,"
                    + " com.android.settingslib.core.AbstractPreferenceController,"
                    + " kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // java.lang.AutoCloseable
    public final void close() {
        StandaloneCoroutine standaloneCoroutine = this.job;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        Iterator it = this.controllerSet.iterator();
        while (it.hasNext()) {
            ((TelephonyAvailabilityHandler) it.next()).unsetAvailabilityStatus();
        }
    }
}
