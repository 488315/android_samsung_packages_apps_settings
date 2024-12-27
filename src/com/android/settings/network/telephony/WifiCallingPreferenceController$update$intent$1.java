package com.android.settings.network.telephony;

import android.content.Context;
import android.content.Intent;
import android.telecom.PhoneAccountHandle;

import com.android.settingslib.core.AbstractPreferenceController;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\n"
                + " \u0002*\u0004\u0018\u00010\u00010\u0001*\u00020\u0003H\u008a@"
        },
        d2 = {
            "<anonymous>",
            "Landroid/content/Intent;",
            "kotlin.jvm.PlatformType",
            "Lkotlinx/coroutines/CoroutineScope;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class WifiCallingPreferenceController$update$intent$1 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ PhoneAccountHandle $simCallManager;
    int label;
    final /* synthetic */ WifiCallingPreferenceController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WifiCallingPreferenceController$update$intent$1(
            WifiCallingPreferenceController wifiCallingPreferenceController,
            PhoneAccountHandle phoneAccountHandle,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = wifiCallingPreferenceController;
        this.$simCallManager = phoneAccountHandle;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new WifiCallingPreferenceController$update$intent$1(
                this.this$0, this.$simCallManager, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((WifiCallingPreferenceController$update$intent$1)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Context context;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        context = ((AbstractPreferenceController) this.this$0).mContext;
        PhoneAccountHandle phoneAccountHandle = this.$simCallManager;
        Intent buildConfigureIntent =
                MobileNetworkUtils.buildConfigureIntent(
                        context,
                        phoneAccountHandle,
                        "android.telecom.action.CONFIGURE_PHONE_ACCOUNT");
        return buildConfigureIntent == null
                ? MobileNetworkUtils.buildConfigureIntent(
                        context,
                        phoneAccountHandle,
                        "android.telecom.action.CONNECTION_SERVICE_CONFIGURE")
                : buildConfigureIntent;
    }
}
