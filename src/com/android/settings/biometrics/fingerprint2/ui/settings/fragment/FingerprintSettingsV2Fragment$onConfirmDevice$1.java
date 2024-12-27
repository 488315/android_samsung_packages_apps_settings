package com.android.settings.biometrics.fingerprint2.ui.settings.fragment;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

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
final class FingerprintSettingsV2Fragment$onConfirmDevice$1 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ Long $gateKeeperPasswordHandle;
    final /* synthetic */ boolean $wasSuccessful;
    int label;
    final /* synthetic */ FingerprintSettingsV2Fragment this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FingerprintSettingsV2Fragment$onConfirmDevice$1(
            FingerprintSettingsV2Fragment fingerprintSettingsV2Fragment,
            boolean z,
            Long l,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = fingerprintSettingsV2Fragment;
        this.$wasSuccessful = z;
        this.$gateKeeperPasswordHandle = l;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FingerprintSettingsV2Fragment$onConfirmDevice$1(
                this.this$0, this.$wasSuccessful, this.$gateKeeperPasswordHandle, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FingerprintSettingsV2Fragment$onConfirmDevice$1)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0048, code lost:

       if (r5 == r0) goto L21;
    */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r6) {
        /*
            r5 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r5.label
            kotlin.Unit r2 = kotlin.Unit.INSTANCE
            r3 = 1
            if (r1 == 0) goto L17
            if (r1 != r3) goto Lf
            kotlin.ResultKt.throwOnFailure(r6)
            goto L4d
        Lf:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L17:
            kotlin.ResultKt.throwOnFailure(r6)
            com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsV2Fragment r6 = r5.this$0
            com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsNavigationViewModel r6 = r6.navigationViewModel
            if (r6 == 0) goto L4e
            boolean r1 = r5.$wasSuccessful
            java.lang.Long r4 = r5.$gateKeeperPasswordHandle
            r5.label = r3
            if (r1 != 0) goto L2f
            java.lang.String r5 = "ConfirmDeviceCredential was unsuccessful"
            r6.launchFinishSettings(r5)
        L2d:
            r5 = r2
            goto L4a
        L2f:
            if (r4 != 0) goto L37
            java.lang.String r5 = "ConfirmDeviceCredential gatekeeper password was null"
            r6.launchFinishSettings(r5)
            goto L2d
        L37:
            com.android.settings.biometrics.fingerprint2.domain.interactor.FingerprintManagerInteractorImpl r1 = r6.fingerprintManagerInteractor
            kotlinx.coroutines.flow.SafeFlow r1 = r1.enrolledFingerprints
            com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsNavigationViewModel$launchEnrollNextStep$2 r3 = new com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsNavigationViewModel$launchEnrollNextStep$2
            r3.<init>()
            java.lang.Object r5 = r1.collect(r3, r5)
            if (r5 != r0) goto L47
            goto L48
        L47:
            r5 = r2
        L48:
            if (r5 != r0) goto L2d
        L4a:
            if (r5 != r0) goto L4d
            return r0
        L4d:
            return r2
        L4e:
            java.lang.String r5 = "navigationViewModel"
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r5)
            r5 = 0
            throw r5
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsV2Fragment$onConfirmDevice$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
