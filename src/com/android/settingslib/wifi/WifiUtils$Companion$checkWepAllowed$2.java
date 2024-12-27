package com.android.settingslib.wifi;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;

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
/* loaded from: classes2.dex */
final class WifiUtils$Companion$checkWepAllowed$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Context $context;
    final /* synthetic */ int $dialogWindowType;
    final /* synthetic */ Function0 $onAllowed;
    final /* synthetic */ Function1 $onStartActivity;
    final /* synthetic */ String $ssid;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WifiUtils$Companion$checkWepAllowed$2(
            Context context,
            Function0 function0,
            Function1 function1,
            int i,
            String str,
            Continuation continuation) {
        super(2, continuation);
        this.$context = context;
        this.$onAllowed = function0;
        this.$onStartActivity = function1;
        this.$dialogWindowType = i;
        this.$ssid = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new WifiUtils$Companion$checkWepAllowed$2(
                this.$context,
                this.$onAllowed,
                this.$onStartActivity,
                this.$dialogWindowType,
                this.$ssid,
                continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((WifiUtils$Companion$checkWepAllowed$2)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        Unit unit = Unit.INSTANCE;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            WifiManager wifiManager =
                    (WifiManager) this.$context.getSystemService(WifiManager.class);
            if (wifiManager == null) {
                return unit;
            }
            this.label = 1;
            obj =
                    BuildersKt.withContext(
                            Dispatchers.Default,
                            new WifiUtils$Companion$queryWepAllowed$2(wifiManager, null),
                            this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        if (((Boolean) obj).booleanValue()) {
            this.$onAllowed.mo1068invoke();
        } else {
            Intent intent = new Intent("android.intent.action.MAIN");
            int i2 = this.$dialogWindowType;
            String str = this.$ssid;
            intent.setComponent(
                    new ComponentName(
                            KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                            "com.android.settings.network.WepNetworkDialogActivity"));
            intent.putExtra("dialog_window_type", i2);
            intent.putExtra("ssid", str);
            Intent addFlags = intent.addFlags(268435456);
            Intrinsics.checkNotNullExpressionValue(addFlags, "addFlags(...)");
            this.$onStartActivity.invoke(addFlags);
        }
        return unit;
    }
}
