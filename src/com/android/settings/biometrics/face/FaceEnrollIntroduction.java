package com.android.settings.biometrics.face;

import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.face.FaceManager;
import android.os.Bundle;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.biometrics.BiometricEnrollIntroduction;
import com.android.settings.biometrics.BiometricEnrollIntroduction$$ExternalSyntheticLambda0;
import com.android.settings.biometrics.BiometricUtils;
import com.android.systemui.unfold.compat.ScreenSizeFoldProvider;
import com.android.systemui.unfold.updates.FoldProvider$FoldCallback;

import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupdesign.span.LinkSpan;
import com.samsung.android.settings.biometrics.face.FaceLockSettings;
import com.samsung.android.settings.biometrics.face.FaceSettingsHelper;
import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FaceEnrollIntroduction extends BiometricEnrollIntroduction {
    public static final /* synthetic */ int $r8$clinit = 0;
    public FaceManager mFaceManager;

    @Override // com.android.settings.biometrics.BiometricEnrollIntroduction
    public final int checkMaxEnrolled() {
        if (this.mFaceManager == null) {
            return R.string.face_intro_error_unknown;
        }
        if (maxFacesEnrolled()) {
            return R.string.face_intro_error_max;
        }
        return 0;
    }

    @Override // com.android.settings.biometrics.BiometricEnrollIntroduction
    public final void getChallenge(
            final BiometricEnrollIntroduction$$ExternalSyntheticLambda0
                    biometricEnrollIntroduction$$ExternalSyntheticLambda0) {
        FaceManager faceManagerOrNull = Utils.getFaceManagerOrNull(this);
        this.mFaceManager = faceManagerOrNull;
        if (faceManagerOrNull == null) {
            biometricEnrollIntroduction$$ExternalSyntheticLambda0.onChallengeGenerated(0, 0L);
        } else {
            faceManagerOrNull.generateChallenge(
                    this.mUserId,
                    new FaceManager
                            .GenerateChallengeCallback() { // from class:
                                                           // com.android.settings.biometrics.face.FaceEnrollIntroduction$$ExternalSyntheticLambda1
                        public final void onGenerateChallengeResult(int i, int i2, long j) {
                            BiometricEnrollIntroduction$$ExternalSyntheticLambda0.this
                                    .onChallengeGenerated(i, j);
                        }
                    });
        }
    }

    public int getDevicePostureState() {
        return this.mDevicePostureState;
    }

    public FaceManager getFaceManager() {
        return Utils.getFaceManagerOrNull(this);
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public int getMetricsCategory() {
        return VolteConstants.ErrorCode.SDP_PROCESSING_FAILED;
    }

    @Override // com.android.settings.biometrics.BiometricEnrollIntroduction
    public final FooterButton getNextButton() {
        FooterBarMixin footerBarMixin = this.mFooterBarMixin;
        if (footerBarMixin != null) {
            return footerBarMixin.primaryButton;
        }
        return null;
    }

    public FoldProvider$FoldCallback getPostureCallback() {
        return this.mFoldCallback;
    }

    public Intent getPostureGuidanceIntent() {
        return this.mPostureGuidanceIntent;
    }

    @Override // com.android.settings.biometrics.BiometricEnrollIntroduction
    public final Intent getSecBiometricsEnrollIntent() {
        Log.d("SecBiometricsEnrollment", "return Face enrollment intent");
        boolean z = FaceSettingsHelper.IS_SUPPORT_FACE_DAEMON_DETECT_OPEN_EYES;
        Log.d("FcstFaceSettingsHelper", "getFaceEnrollmentIntentForAosp");
        Intent intent = new Intent();
        intent.setClassName(getPackageName(), FaceLockSettings.class.getName());
        intent.putExtra("previousStage", "face_register_external");
        return intent;
    }

    public final boolean maxFacesEnrolled() {
        FaceManager faceManager = this.mFaceManager;
        return faceManager != null
                && faceManager.getEnrolledFaces(this.mUserId).size()
                        >= getApplicationContext()
                                .getResources()
                                .getInteger(R.integer.suw_max_faces_enrollable);
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x003a, code lost:

       if (r4 != r5) goto L31;
    */
    @Override // com.android.settings.biometrics.BiometricEnrollIntroduction,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onActivityResult(int r7, int r8, android.content.Intent r9) {
        /*
            r6 = this;
            r0 = 7
            r1 = 2
            r2 = 0
            if (r7 != r0) goto L12
            r6.mLaunchedPostureGuidance = r2
            if (r8 == 0) goto Lb
            if (r8 != r1) goto L11
        Lb:
            r6.getCurrentFocus()
            r6.onSkipButtonClick()
        L11:
            return
        L12:
            r0 = 1
            if (r7 == r1) goto L1b
            r3 = 6
            if (r7 != r3) goto L19
            goto L1b
        L19:
            r3 = r2
            goto L1c
        L1b:
            r3 = r0
        L1c:
            if (r8 == r1) goto L27
            r1 = 11
            if (r8 == r1) goto L27
            if (r8 != r0) goto L25
            goto L27
        L25:
            r1 = r2
            goto L28
        L27:
            r1 = r0
        L28:
            if (r9 == 0) goto L30
            java.lang.String r4 = "finished_enrolling_face"
            boolean r2 = r9.getBooleanExtra(r4, r2)
        L30:
            if (r8 != 0) goto L44
            if (r2 != 0) goto L3d
            int r4 = r6.mDevicePostureState
            int r5 = com.android.settings.biometrics.BiometricUtils.sAllowEnrollPosture
            if (r5 == 0) goto L44
            if (r4 != r5) goto L3d
            goto L44
        L3d:
            r6.setResult(r8, r9)
            r6.finish()
            return
        L44:
            if (r3 == 0) goto L48
            if (r1 != 0) goto L4a
        L48:
            if (r2 == 0) goto L56
        L4a:
            if (r9 != 0) goto L51
            android.content.Intent r9 = new android.content.Intent
            r9.<init>()
        L51:
            java.lang.String r1 = "skip_pending_enroll"
            r9.putExtra(r1, r0)
        L56:
            super.onActivityResult(r7, r8, r9)
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.biometrics.face.FaceEnrollIntroduction.onActivityResult(int,"
                    + " int, android.content.Intent):void");
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.mScreenSizeFoldProvider == null || getPostureCallback() == null) {
            return;
        }
        this.mScreenSizeFoldProvider.onConfigurationChange(configuration);
    }

    @Override // com.android.settings.biometrics.BiometricEnrollIntroduction,
              // com.android.settings.biometrics.BiometricEnrollBase,
              // com.android.settings.core.InstrumentedActivity,
              // com.android.settingslib.core.lifecycle.ObservableActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        this.mFaceManager = getFaceManager();
        super.onCreate(bundle);
        isFinishing();
    }

    @Override // com.android.settings.biometrics.BiometricEnrollIntroduction
    public void onEnrollmentSkipped(Intent intent) {
        if (BiometricUtils.tryStartingNextBiometricEnroll(this)) {
            return;
        }
        super.onEnrollmentSkipped(intent);
    }

    @Override // com.android.settings.biometrics.BiometricEnrollIntroduction
    public void onFinishedEnrolling(Intent intent) {
        if (BiometricUtils.tryStartingNextBiometricEnroll(this)) {
            return;
        }
        setResult(1, intent);
        finish();
    }

    public void onSkipButtonClick() {
        if (BiometricUtils.tryStartingNextBiometricEnroll(this)) {
            return;
        }
        onEnrollmentSkipped(null);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableActivity,
              // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onStart() {
        super.onStart();
        if (maxFacesEnrolled()) {
            Log.d(
                    "FaceEnrollIntroduction",
                    "Device has enrolled face, do not show posture guidance");
            return;
        }
        if (getPostureGuidanceIntent() == null) {
            Log.d("FaceEnrollIntroduction", "Device do not support posture guidance");
            return;
        }
        BiometricUtils.sAllowEnrollPosture =
                getResources().getInteger(R.integer.config_face_enroll_supported_posture);
        if (getPostureCallback() == null) {
            this.mFoldCallback =
                    new FoldProvider$FoldCallback() { // from class:
                                                      // com.android.settings.biometrics.face.FaceEnrollIntroduction$$ExternalSyntheticLambda0
                        @Override // com.android.systemui.unfold.updates.FoldProvider$FoldCallback
                        public final void onFoldUpdated(boolean z) {
                            int i = FaceEnrollIntroduction.$r8$clinit;
                            FaceEnrollIntroduction faceEnrollIntroduction =
                                    FaceEnrollIntroduction.this;
                            faceEnrollIntroduction.getClass();
                            int i2 = z ? 1 : 3;
                            faceEnrollIntroduction.mDevicePostureState = i2;
                            boolean z2 = faceEnrollIntroduction.mLaunchedPostureGuidance;
                            int i3 = BiometricUtils.sAllowEnrollPosture;
                            if (i3 == 0 || i2 == i3 || z2 || faceEnrollIntroduction.mNextLaunched) {
                                return;
                            }
                            faceEnrollIntroduction.launchPostureGuidance();
                        }
                    };
        }
        if (this.mScreenSizeFoldProvider == null) {
            ScreenSizeFoldProvider screenSizeFoldProvider =
                    new ScreenSizeFoldProvider(getApplicationContext());
            this.mScreenSizeFoldProvider = screenSizeFoldProvider;
            screenSizeFoldProvider.registerCallback(this.mFoldCallback, getMainExecutor());
        }
    }

    public byte[] requestGatekeeperHat(long j) {
        return BiometricUtils.requestGatekeeperHat(this, getIntent(), this.mUserId, j);
    }

    @Override // com.android.settings.biometrics.BiometricEnrollBase
    public final boolean shouldFinishWhenBackgrounded() {
        if (super.shouldFinishWhenBackgrounded()) {
            int i = this.mDevicePostureState;
            boolean z = this.mLaunchedPostureGuidance;
            int i2 = BiometricUtils.sAllowEnrollPosture;
            if (i2 == 0 || i == i2 || !z) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.settings.biometrics.BiometricEnrollIntroduction
    public void updateDescriptionText() {
        if (Utils.isPrivateProfile(getApplicationContext(), this.mUserId)) {
            setDescriptionText(getString(R.string.private_space_face_enroll_introduction_message));
        }
    }

    @Override // com.google.android.setupdesign.span.LinkSpan.OnClickListener
    public final void onClick(LinkSpan linkSpan) {}
}
