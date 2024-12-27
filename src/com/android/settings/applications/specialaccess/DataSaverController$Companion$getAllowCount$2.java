package com.android.settings.applications.specialaccess;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.net.NetworkPolicyManager;

import com.android.settingslib.spaprivileged.model.app.AppListRepository;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DeferredCoroutine;

import java.util.Collection;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\n\n"
                + "\u0000\n"
                + "\u0002\u0010\b\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"
        },
        d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "Lkotlinx/coroutines/CoroutineScope;"},
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class DataSaverController$Companion$getAllowCount$2 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ AppListRepository $appListRepository;
    final /* synthetic */ Context $context;
    int label;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            d1 = {
                "\u0000\n\n"
                    + "\u0000\n"
                    + "\u0002\u0010\b\n"
                    + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"
            },
            d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "Lkotlinx/coroutines/CoroutineScope;"},
            k = 3,
            mv = {1, 9, 0},
            xi = 48)
    /* renamed from: com.android.settings.applications.specialaccess.DataSaverController$Companion$getAllowCount$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ AppListRepository $appListRepository;
        final /* synthetic */ Context $context;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(
                Context context, AppListRepository appListRepository, Continuation continuation) {
            super(2, continuation);
            this.$context = context;
            this.$appListRepository = appListRepository;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 =
                    new AnonymousClass1(this.$context, this.$appListRepository, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2))
                    .invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            int[] iArr;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                DeferredCoroutine async$default =
                        BuildersKt.async$default(
                                (CoroutineScope) this.L$0,
                                new DataSaverController$Companion$getAllowCount$2$1$appsDeferred$1(
                                        this.$appListRepository, null));
                int[] uidsWithPolicy =
                        NetworkPolicyManager.from(this.$context).getUidsWithPolicy(4);
                this.L$0 = uidsWithPolicy;
                this.label = 1;
                obj = async$default.awaitInternal(this);
                if (obj == coroutineSingletons) {
                    return coroutineSingletons;
                }
                iArr = uidsWithPolicy;
            } else {
                if (i != 1) {
                    throw new IllegalStateException(
                            "call to 'resume' before 'invoke' with coroutine");
                }
                iArr = (int[]) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            Iterable<ApplicationInfo> iterable = (Iterable) obj;
            int i2 = 0;
            if (!(iterable instanceof Collection) || !((Collection) iterable).isEmpty()) {
                for (ApplicationInfo applicationInfo : iterable) {
                    Intrinsics.checkNotNull(iArr);
                    if (ArraysKt___ArraysKt.contains(applicationInfo.uid, iArr)
                            && (i2 = i2 + 1) < 0) {
                        throw new ArithmeticException("Count overflow has happened.");
                    }
                }
            }
            return new Integer(i2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DataSaverController$Companion$getAllowCount$2(
            Context context, AppListRepository appListRepository, Continuation continuation) {
        super(2, continuation);
        this.$context = context;
        this.$appListRepository = appListRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DataSaverController$Companion$getAllowCount$2(
                this.$context, this.$appListRepository, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DataSaverController$Companion$getAllowCount$2)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            AnonymousClass1 anonymousClass1 =
                    new AnonymousClass1(this.$context, this.$appListRepository, null);
            this.label = 1;
            obj = CoroutineScopeKt.coroutineScope(anonymousClass1, this);
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
