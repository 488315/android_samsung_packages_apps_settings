package com.android.settings.network.telephony;

import android.content.Context;

import com.android.settings.network.SatelliteRepository;

import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SubscriptionActivationRepository {
    public final CallStateRepository callStateRepository;
    public final Context context;
    public final SatelliteRepository satelliteRepository;

    public SubscriptionActivationRepository(Context context) {
        CallStateRepository callStateRepository = new CallStateRepository(context);
        SatelliteRepository satelliteRepository = new SatelliteRepository(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.callStateRepository = callStateRepository;
        this.satelliteRepository = satelliteRepository;
    }

    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isActivationChangeableFlow() {
        return FlowKt.combine(
                this.callStateRepository.isInCallFlow(),
                SatelliteRepository.getIsSessionStartedFlow$default(this.satelliteRepository),
                new SubscriptionActivationRepository$isActivationChangeableFlow$1(3, null));
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object setActive(int r6, boolean r7, kotlin.coroutines.Continuation r8) {
        /*
            r5 = this;
            boolean r0 = r8 instanceof com.android.settings.network.telephony.SubscriptionActivationRepository$setActive$1
            if (r0 == 0) goto L13
            r0 = r8
            com.android.settings.network.telephony.SubscriptionActivationRepository$setActive$1 r0 = (com.android.settings.network.telephony.SubscriptionActivationRepository$setActive$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.settings.network.telephony.SubscriptionActivationRepository$setActive$1 r0 = new com.android.settings.network.telephony.SubscriptionActivationRepository$setActive$1
            r0.<init>(r5, r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            kotlin.Unit r3 = kotlin.Unit.INSTANCE
            r4 = 1
            if (r2 == 0) goto L39
            if (r2 != r4) goto L31
            boolean r7 = r0.Z$0
            int r6 = r0.I$0
            java.lang.Object r5 = r0.L$0
            com.android.settings.network.telephony.SubscriptionActivationRepository r5 = (com.android.settings.network.telephony.SubscriptionActivationRepository) r5
            kotlin.ResultKt.throwOnFailure(r8)
            goto L63
        L31:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L39:
            kotlin.ResultKt.throwOnFailure(r8)
            boolean r8 = android.telephony.SubscriptionManager.isUsableSubscriptionId(r6)
            if (r8 != 0) goto L4a
            java.lang.String r5 = "SubscriptionActivationR"
            java.lang.String r6 = "Unable to toggle subscription due to unusable subscription ID."
            android.util.Log.i(r5, r6)
            return r3
        L4a:
            if (r7 != 0) goto L7d
            r0.L$0 = r5
            r0.I$0 = r6
            r0.Z$0 = r7
            r0.label = r4
            kotlinx.coroutines.scheduling.DefaultScheduler r8 = kotlinx.coroutines.Dispatchers.Default
            com.android.settings.network.telephony.SubscriptionActivationRepository$isEmergencyCallbackMode$2 r2 = new com.android.settings.network.telephony.SubscriptionActivationRepository$isEmergencyCallbackMode$2
            r4 = 0
            r2.<init>(r5, r6, r4)
            java.lang.Object r8 = kotlinx.coroutines.BuildersKt.withContext(r8, r2, r0)
            if (r8 != r1) goto L63
            return r1
        L63:
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto L7d
            android.content.Intent r6 = new android.content.Intent
            java.lang.String r7 = "android.telephony.action.SHOW_NOTICE_ECM_BLOCK_OTHERS"
            r6.<init>(r7)
            java.lang.String r7 = "com.android.phone"
            r6.setPackage(r7)
            android.content.Context r5 = r5.context
            r5.startActivity(r6)
            return r3
        L7d:
            if (r7 == 0) goto L9d
            com.android.settings.network.SimOnboardingService r7 = com.android.settings.network.SimOnboardingActivity.onboardingService
            android.content.Context r5 = r5.context
            java.lang.String r7 = "context"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r5, r7)
            android.content.Intent r7 = new android.content.Intent
            java.lang.Class<com.android.settings.network.SimOnboardingActivity> r8 = com.android.settings.network.SimOnboardingActivity.class
            r7.<init>(r5, r8)
            java.lang.String r8 = "sub_id"
            r7.putExtra(r8, r6)
            r6 = 268435456(0x10000000, float:2.5243549E-29)
            r7.setFlags(r6)
            r5.startActivity(r7)
            return r3
        L9d:
            android.content.Context r5 = r5.context
            android.content.Intent r6 = com.android.settings.network.telephony.ToggleSubscriptionDialogActivity.getIntent(r5, r6, r7)
            r5.startActivity(r6)
            return r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.network.telephony.SubscriptionActivationRepository.setActive(int,"
                    + " boolean, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
