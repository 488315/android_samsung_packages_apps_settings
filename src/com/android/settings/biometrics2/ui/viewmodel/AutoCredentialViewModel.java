package com.android.settings.biometrics2.ui.viewmodel;

import android.app.Application;
import android.hardware.fingerprint.FingerprintManager;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleCoroutineScopeImpl;

import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.VerifyCredentialResponse;
import com.android.settings.biometrics.BiometricUtils;
import com.android.settings.biometrics2.data.repository.FingerprintRepository;
import com.android.settings.biometrics2.ui.model.CredentialModel;

import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AutoCredentialViewModel extends AndroidViewModel {
    public final SharedFlowImpl _generateChallengeFailedFlow;
    public final FingerprintChallengeGenerator challengeGenerator;
    public final CredentialModel credentialModel;
    public boolean isGeneratingChallengeDuringCheckingCredential;
    public final LockPatternUtils lockPatternUtils;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class FingerprintChallengeGenerator {
        public AutoCredentialViewModel$generateChallenge$1 callback;
        public final FingerprintRepository fingerprintRepository;

        public FingerprintChallengeGenerator(FingerprintRepository fingerprintRepository) {
            this.fingerprintRepository = fingerprintRepository;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AutoCredentialViewModel(
            Application application,
            LockPatternUtils lockPatternUtils,
            FingerprintChallengeGenerator fingerprintChallengeGenerator,
            CredentialModel credentialModel) {
        super(application);
        Intrinsics.checkNotNullParameter(lockPatternUtils, "lockPatternUtils");
        this.lockPatternUtils = lockPatternUtils;
        this.challengeGenerator = fingerprintChallengeGenerator;
        this.credentialModel = credentialModel;
        this._generateChallengeFailedFlow = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
    }

    public static final byte[] access$requestGatekeeperHat(
            AutoCredentialViewModel autoCredentialViewModel, long j, long j2, int i) {
        VerifyCredentialResponse verifyGatekeeperPasswordHandle =
                autoCredentialViewModel.lockPatternUtils.verifyGatekeeperPasswordHandle(j, j2, i);
        Intrinsics.checkNotNullExpressionValue(
                verifyGatekeeperPasswordHandle, "verifyGatekeeperPasswordHandle(...)");
        if (verifyGatekeeperPasswordHandle.isMatched()) {
            return verifyGatekeeperPasswordHandle.getGatekeeperHAT();
        }
        throw new BiometricUtils.GatekeeperCredentialNotMatchException(
                "Unable to request Gatekeeper HAT");
    }

    public final void generateChallenge(
            long j, boolean z, LifecycleCoroutineScopeImpl lifecycleCoroutineScopeImpl) {
        final AutoCredentialViewModel$generateChallenge$1
                autoCredentialViewModel$generateChallenge$1 =
                        new AutoCredentialViewModel$generateChallenge$1(
                                this, j, z, lifecycleCoroutineScopeImpl);
        FingerprintChallengeGenerator fingerprintChallengeGenerator = this.challengeGenerator;
        fingerprintChallengeGenerator.callback = autoCredentialViewModel$generateChallenge$1;
        fingerprintChallengeGenerator.fingerprintRepository.mFingerprintManager.generateChallenge(
                this.credentialModel.userId,
                new FingerprintManager.GenerateChallengeCallback() { // from class:
                    // com.android.settings.biometrics2.ui.viewmodel.AutoCredentialViewModel$FingerprintChallengeGenerator$generateChallenge$1$1
                    /* JADX WARN: Removed duplicated region for block: B:24:0x00af  */
                    /* JADX WARN: Removed duplicated region for block: B:27:0x00d1  */
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final void onChallengeGenerated(int r17, int r18, long r19) {
                        /*
                            r16 = this;
                            r0 = r16
                            com.android.settings.biometrics2.ui.viewmodel.AutoCredentialViewModel$generateChallenge$1 r0 = com.android.settings.biometrics2.ui.viewmodel.AutoCredentialViewModel$generateChallenge$1.this
                            java.lang.String r1 = "generateChallenge, invalid Credential or IllegalStateException"
                            java.lang.String r2 = ", revokeGkPwHandle:"
                            java.lang.String r3 = "generateChallenge(), model:"
                            long r10 = r0.$gkPwHandle
                            kotlinx.coroutines.CoroutineScope r12 = r0.$scope
                            boolean r13 = r0.$revokeGkPwHandle
                            java.lang.String r14 = "AutoCredentialViewModel"
                            com.android.settings.biometrics2.ui.viewmodel.AutoCredentialViewModel r15 = r0.this$0
                            r9 = 3
                            r7 = 0
                            r4 = r15
                            r5 = r10
                            r16 = r12
                            r12 = r7
                            r7 = r19
                            r9 = r18
                            byte[] r0 = com.android.settings.biometrics2.ui.viewmodel.AutoCredentialViewModel.access$requestGatekeeperHat(r4, r5, r7, r9)     // Catch: java.lang.Throwable -> L71 java.lang.IllegalStateException -> L76
                            com.android.settings.biometrics2.ui.model.CredentialModel r4 = r15.credentialModel     // Catch: java.lang.Throwable -> L71 java.lang.IllegalStateException -> L76
                            java.time.Clock r5 = r4.clock     // Catch: java.lang.Throwable -> L71 java.lang.IllegalStateException -> L76
                            long r5 = r5.millis()     // Catch: java.lang.Throwable -> L71 java.lang.IllegalStateException -> L76
                            java.lang.Long r5 = java.lang.Long.valueOf(r5)     // Catch: java.lang.Throwable -> L71 java.lang.IllegalStateException -> L76
                            r4.updateChallengeMillis = r5     // Catch: java.lang.Throwable -> L71 java.lang.IllegalStateException -> L76
                            r5 = r19
                            r4.challenge = r5     // Catch: java.lang.Throwable -> L71 java.lang.IllegalStateException -> L76
                            java.time.Clock r5 = r4.clock     // Catch: java.lang.Throwable -> L71 java.lang.IllegalStateException -> L76
                            long r5 = r5.millis()     // Catch: java.lang.Throwable -> L71 java.lang.IllegalStateException -> L76
                            java.lang.Long r5 = java.lang.Long.valueOf(r5)     // Catch: java.lang.Throwable -> L71 java.lang.IllegalStateException -> L76
                            r4.updateTokenMillis = r5     // Catch: java.lang.Throwable -> L71 java.lang.IllegalStateException -> L76
                            r4.token = r0     // Catch: java.lang.Throwable -> L71 java.lang.IllegalStateException -> L76
                            if (r13 == 0) goto L4a
                            com.android.internal.widget.LockPatternUtils r0 = r15.lockPatternUtils
                            r0.removeGatekeeperPasswordHandle(r10)
                        L4a:
                            java.lang.StringBuilder r0 = new java.lang.StringBuilder
                            r0.<init>(r3)
                            r0.append(r4)
                            r0.append(r2)
                            r0.append(r13)
                            java.lang.String r0 = r0.toString()
                            android.util.Log.d(r14, r0)
                            boolean r0 = r15.isValidCredential()
                            if (r0 != 0) goto Lab
                            android.util.Log.w(r14, r1)
                            com.android.settings.biometrics2.ui.viewmodel.AutoCredentialViewModel$generateChallenge$1$onChallengeGenerated$1 r0 = new com.android.settings.biometrics2.ui.viewmodel.AutoCredentialViewModel$generateChallenge$1$onChallengeGenerated$1
                            r0.<init>(r15, r12)
                            r5 = 3
                            r4 = r16
                            goto La8
                        L71:
                            r0 = move-exception
                            r5 = 3
                            r4 = r16
                            goto Lad
                        L76:
                            r0 = move-exception
                            r5 = 3
                            r4 = r16
                            java.lang.String r6 = "generateChallenge, IllegalStateException"
                            android.util.Log.e(r14, r6, r0)     // Catch: java.lang.Throwable -> Lac
                            if (r13 == 0) goto L86
                            com.android.internal.widget.LockPatternUtils r0 = r15.lockPatternUtils
                            r0.removeGatekeeperPasswordHandle(r10)
                        L86:
                            com.android.settings.biometrics2.ui.model.CredentialModel r0 = r15.credentialModel
                            java.lang.StringBuilder r6 = new java.lang.StringBuilder
                            r6.<init>(r3)
                            r6.append(r0)
                            r6.append(r2)
                            r6.append(r13)
                            java.lang.String r0 = r6.toString()
                            android.util.Log.d(r14, r0)
                            r15.isValidCredential()
                            android.util.Log.w(r14, r1)
                            com.android.settings.biometrics2.ui.viewmodel.AutoCredentialViewModel$generateChallenge$1$onChallengeGenerated$1 r0 = new com.android.settings.biometrics2.ui.viewmodel.AutoCredentialViewModel$generateChallenge$1$onChallengeGenerated$1
                            r0.<init>(r15, r12)
                        La8:
                            kotlinx.coroutines.BuildersKt.launch$default(r4, r12, r12, r0, r5)
                        Lab:
                            return
                        Lac:
                            r0 = move-exception
                        Lad:
                            if (r13 == 0) goto Lb4
                            com.android.internal.widget.LockPatternUtils r6 = r15.lockPatternUtils
                            r6.removeGatekeeperPasswordHandle(r10)
                        Lb4:
                            com.android.settings.biometrics2.ui.model.CredentialModel r6 = r15.credentialModel
                            java.lang.StringBuilder r7 = new java.lang.StringBuilder
                            r7.<init>(r3)
                            r7.append(r6)
                            r7.append(r2)
                            r7.append(r13)
                            java.lang.String r2 = r7.toString()
                            android.util.Log.d(r14, r2)
                            boolean r2 = r15.isValidCredential()
                            if (r2 != 0) goto Ldc
                            android.util.Log.w(r14, r1)
                            com.android.settings.biometrics2.ui.viewmodel.AutoCredentialViewModel$generateChallenge$1$onChallengeGenerated$1 r1 = new com.android.settings.biometrics2.ui.viewmodel.AutoCredentialViewModel$generateChallenge$1$onChallengeGenerated$1
                            r1.<init>(r15, r12)
                            kotlinx.coroutines.BuildersKt.launch$default(r4, r12, r12, r1, r5)
                        Ldc:
                            throw r0
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.android.settings.biometrics2.ui.viewmodel.AutoCredentialViewModel$FingerprintChallengeGenerator$generateChallenge$1$1.onChallengeGenerated(int,"
                                    + " int, long):void");
                    }
                });
    }

    public final boolean isValidCredential() {
        LockPatternUtils lockPatternUtils = this.lockPatternUtils;
        CredentialModel credentialModel = this.credentialModel;
        return (lockPatternUtils.getActivePasswordQuality(credentialModel.userId) == 0
                        || credentialModel.token == null)
                ? false
                : true;
    }
}
