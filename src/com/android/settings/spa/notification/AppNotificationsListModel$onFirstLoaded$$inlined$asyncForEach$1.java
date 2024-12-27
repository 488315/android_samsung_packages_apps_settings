package com.android.settings.spa.notification;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u000e\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\b\u0002\u0010\u0003\u001a\u00020\u0002\"\u0004\b\u0000\u0010\u0000*\u00020\u0001H\u008a@¢\u0006\u0004\b\u0003\u0010\u0004"
        },
        d2 = {
            "T",
            "Lkotlinx/coroutines/CoroutineScope;",
            ApnSettings.MVNO_NONE,
            "<anonymous>",
            "(Lkotlinx/coroutines/CoroutineScope;)V"
        },
        k = 3,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class AppNotificationsListModel$onFirstLoaded$$inlined$asyncForEach$1
        extends SuspendLambda implements Function2 {
    final /* synthetic */ Iterable $this_asyncForEach;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            d1 = {
                "\u0000\u000e\n"
                    + "\u0000\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0010\u0002\n"
                    + "\u0002\b\u0002\u0010\u0003\u001a\u00020\u0002\"\u0004\b\u0000\u0010\u0000*\u00020\u0001H\u008a@¢\u0006\u0004\b\u0003\u0010\u0004"
            },
            d2 = {
                "T",
                "Lkotlinx/coroutines/CoroutineScope;",
                ApnSettings.MVNO_NONE,
                "<anonymous>",
                "(Lkotlinx/coroutines/CoroutineScope;)V"
            },
            k = 3,
            mv = {1, 9, 0})
    /* renamed from: com.android.settings.spa.notification.AppNotificationsListModel$onFirstLoaded$$inlined$asyncForEach$1$1, reason: invalid class name */
    public final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Object $it;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Object obj, Continuation continuation) {
            super(2, continuation);
            this.$it = obj;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$it, continuation);
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
            ((AppNotificationsRecord) this.$it).controller.getEnabled();
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppNotificationsListModel$onFirstLoaded$$inlined$asyncForEach$1(
            Iterable iterable, Continuation continuation) {
        super(2, continuation);
        this.$this_asyncForEach = iterable;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AppNotificationsListModel$onFirstLoaded$$inlined$asyncForEach$1
                appNotificationsListModel$onFirstLoaded$$inlined$asyncForEach$1 =
                        new AppNotificationsListModel$onFirstLoaded$$inlined$asyncForEach$1(
                                this.$this_asyncForEach, continuation);
        appNotificationsListModel$onFirstLoaded$$inlined$asyncForEach$1.L$0 = obj;
        return appNotificationsListModel$onFirstLoaded$$inlined$asyncForEach$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        AppNotificationsListModel$onFirstLoaded$$inlined$asyncForEach$1
                appNotificationsListModel$onFirstLoaded$$inlined$asyncForEach$1 =
                        (AppNotificationsListModel$onFirstLoaded$$inlined$asyncForEach$1)
                                create((CoroutineScope) obj, (Continuation) obj2);
        Unit unit = Unit.INSTANCE;
        appNotificationsListModel$onFirstLoaded$$inlined$asyncForEach$1.invokeSuspend(unit);
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
        Iterator it = this.$this_asyncForEach.iterator();
        while (it.hasNext()) {
            BuildersKt.launch$default(
                    coroutineScope, null, null, new AnonymousClass1(it.next(), null), 3);
        }
        return Unit.INSTANCE;
    }
}
