package com.android.settings.network;

import com.android.settingslib.RestrictedPreference;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0012\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010 \n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\u0010\u0000\u001a\u00020\u00012*\u0010\u0002\u001a&\u0012\f\u0012\n"
                + " \u0005*\u0004\u0018\u00010\u00040\u0004 \u0005*\u0012\u0012\f\u0012\n"
                + " \u0005*\u0004\u0018\u00010\u00040\u0004\u0018\u00010\u00030\u0003H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "subscriptionInfoList",
            ApnSettings.MVNO_NONE,
            "Landroid/telephony/SubscriptionInfo;",
            "kotlin.jvm.PlatformType"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class NetworkProviderCallsSmsController$onViewCreated$2 extends SuspendLambda
        implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ NetworkProviderCallsSmsController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NetworkProviderCallsSmsController$onViewCreated$2(
            NetworkProviderCallsSmsController networkProviderCallsSmsController,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = networkProviderCallsSmsController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        NetworkProviderCallsSmsController$onViewCreated$2
                networkProviderCallsSmsController$onViewCreated$2 =
                        new NetworkProviderCallsSmsController$onViewCreated$2(
                                this.this$0, continuation);
        networkProviderCallsSmsController$onViewCreated$2.L$0 = obj;
        return networkProviderCallsSmsController$onViewCreated$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        NetworkProviderCallsSmsController$onViewCreated$2
                networkProviderCallsSmsController$onViewCreated$2 =
                        (NetworkProviderCallsSmsController$onViewCreated$2)
                                create((List) obj, (Continuation) obj2);
        Unit unit = Unit.INSTANCE;
        networkProviderCallsSmsController$onViewCreated$2.invokeSuspend(unit);
        return unit;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        RestrictedPreference restrictedPreference;
        RestrictedPreference restrictedPreference2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List list = (List) this.L$0;
        restrictedPreference = this.this$0.preference;
        if (restrictedPreference == null) {
            Intrinsics.throwUninitializedPropertyAccessException("preference");
            throw null;
        }
        if (!restrictedPreference.mHelper.mDisabledByAdmin) {
            restrictedPreference2 = this.this$0.preference;
            if (restrictedPreference2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("preference");
                throw null;
            }
            Intrinsics.checkNotNull(list);
            restrictedPreference2.setEnabled(!list.isEmpty());
        }
        return Unit.INSTANCE;
    }
}
