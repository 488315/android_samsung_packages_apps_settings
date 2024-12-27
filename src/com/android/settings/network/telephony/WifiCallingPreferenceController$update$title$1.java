package com.android.settings.network.telephony;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;

import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;

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
            "\u0000\n\n"
                + "\u0000\n"
                + "\u0002\u0010\r\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\u008a@"
        },
        d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "Lkotlinx/coroutines/CoroutineScope;"},
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class WifiCallingPreferenceController$update$title$1 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ Intent $intent;
    int label;
    final /* synthetic */ WifiCallingPreferenceController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WifiCallingPreferenceController$update$title$1(
            WifiCallingPreferenceController wifiCallingPreferenceController,
            Intent intent,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = wifiCallingPreferenceController;
        this.$intent = intent;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new WifiCallingPreferenceController$update$title$1(
                this.this$0, this.$intent, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((WifiCallingPreferenceController$update$title$1)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Context context;
        Context context2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        context = ((AbstractPreferenceController) this.this$0).mContext;
        ResolveInfo resolveActivity = context.getPackageManager().resolveActivity(this.$intent, 0);
        if (resolveActivity == null) {
            return null;
        }
        context2 = ((AbstractPreferenceController) this.this$0).mContext;
        return resolveActivity.loadLabel(context2.getPackageManager());
    }
}
