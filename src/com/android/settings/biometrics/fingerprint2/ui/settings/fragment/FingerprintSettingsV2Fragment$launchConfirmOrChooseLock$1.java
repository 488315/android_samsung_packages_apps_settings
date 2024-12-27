package com.android.settings.biometrics.fingerprint2.ui.settings.fragment;

import android.content.Intent;

import com.android.settings.R;
import com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsNavigationViewModel;
import com.android.settings.password.ChooseLockGeneric;
import com.android.settings.password.ChooseLockSettingsHelper;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

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
                + "\u0002\u0010\u0002\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"
        },
        d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "Lkotlinx/coroutines/CoroutineScope;"},
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class FingerprintSettingsV2Fragment$launchConfirmOrChooseLock$1 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ int $userId;
    int label;
    final /* synthetic */ FingerprintSettingsV2Fragment this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FingerprintSettingsV2Fragment$launchConfirmOrChooseLock$1(
            FingerprintSettingsV2Fragment fingerprintSettingsV2Fragment,
            int i,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = fingerprintSettingsV2Fragment;
        this.$userId = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FingerprintSettingsV2Fragment$launchConfirmOrChooseLock$1(
                this.this$0, this.$userId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        FingerprintSettingsV2Fragment$launchConfirmOrChooseLock$1
                fingerprintSettingsV2Fragment$launchConfirmOrChooseLock$1 =
                        (FingerprintSettingsV2Fragment$launchConfirmOrChooseLock$1)
                                create((CoroutineScope) obj, (Continuation) obj2);
        Unit unit = Unit.INSTANCE;
        fingerprintSettingsV2Fragment$launchConfirmOrChooseLock$1.invokeSuspend(unit);
        return unit;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        FingerprintSettingsNavigationViewModel fingerprintSettingsNavigationViewModel =
                this.this$0.navigationViewModel;
        if (fingerprintSettingsNavigationViewModel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("navigationViewModel");
            throw null;
        }
        fingerprintSettingsNavigationViewModel.setStepToLaunched();
        Intent intent = new Intent();
        ChooseLockSettingsHelper.Builder builder =
                new ChooseLockSettingsHelper.Builder(this.this$0.requireActivity(), this.this$0);
        builder.mRequestCode = 4;
        builder.mTitle =
                this.this$0.getString(R.string.security_settings_fingerprint_preference_title);
        builder.mRequestGatekeeperPasswordHandle = true;
        builder.mUserId = this.$userId;
        builder.mForegroundOnly = true;
        builder.mReturnCredentials = true;
        if (!builder.show()) {
            intent.setClassName(
                    KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, ChooseLockGeneric.class.getName());
            intent.putExtra("hide_insecure_options", true);
            intent.putExtra("request_gk_pw_handle", true);
            intent.putExtra("android.intent.extra.USER_ID", this.$userId);
            this.this$0.confirmDeviceResultListener.launch(intent);
        }
        return Unit.INSTANCE;
    }
}
