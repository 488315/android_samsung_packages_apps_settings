package com.android.settings.biometrics.fingerprint;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.SystemClock;
import android.util.Log;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.biometrics.BiometricEnrollSidecar;

import com.google.android.setupcompat.util.WizardManagerHelper;
import com.samsung.android.knox.custom.IKnoxCustomManager;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FingerprintEnrollSidecar extends BiometricEnrollSidecar {
    public final int mEnrollReason;

    @VisibleForTesting
    FingerprintManager.EnrollmentCallback mEnrollmentCallback =
            new FingerprintManager
                    .EnrollmentCallback() { // from class:
                                            // com.android.settings.biometrics.fingerprint.FingerprintEnrollSidecar.1
                public final void onAcquired(boolean z) {
                    FingerprintEnrollSidecar fingerprintEnrollSidecar =
                            FingerprintEnrollSidecar.this;
                    BiometricEnrollSidecar.Listener listener = fingerprintEnrollSidecar.mListener;
                    if (listener != null) {
                        listener.onAcquired(z);
                    } else {
                        fingerprintEnrollSidecar.mQueuedEvents.add(
                                new BiometricEnrollSidecar.QueuedAcquired(z));
                    }
                }

                public final void onEnrollmentError(int i, CharSequence charSequence) {
                    FingerprintEnrollSidecar.this.onEnrollmentError(i, charSequence);
                }

                public final void onEnrollmentHelp(int i, CharSequence charSequence) {
                    FingerprintEnrollSidecar fingerprintEnrollSidecar =
                            FingerprintEnrollSidecar.this;
                    BiometricEnrollSidecar.Listener listener = fingerprintEnrollSidecar.mListener;
                    if (listener != null) {
                        listener.onEnrollmentHelp(i, charSequence);
                        return;
                    }
                    ArrayList arrayList = fingerprintEnrollSidecar.mQueuedEvents;
                    BiometricEnrollSidecar.QueuedEnrollmentHelp queuedEnrollmentHelp =
                            new BiometricEnrollSidecar.QueuedEnrollmentHelp(0);
                    queuedEnrollmentHelp.helpMsgId = i;
                    queuedEnrollmentHelp.helpString = charSequence;
                    arrayList.add(queuedEnrollmentHelp);
                }

                public final void onEnrollmentProgress(int i) {
                    FingerprintEnrollSidecar.this.onEnrollmentProgress(i);
                }

                public final void onUdfpsOverlayShown() {
                    FingerprintEnrollSidecar fingerprintEnrollSidecar =
                            FingerprintEnrollSidecar.this;
                    BiometricEnrollSidecar.Listener listener = fingerprintEnrollSidecar.mListener;
                    if (listener != null) {
                        listener.onUdfpsOverlayShown();
                    } else {
                        fingerprintEnrollSidecar.mQueuedEvents.add(
                                new BiometricEnrollSidecar.QueuedUdfpsOverlayShown());
                    }
                }

                public final void onUdfpsPointerDown(int i) {
                    FingerprintEnrollSidecar fingerprintEnrollSidecar =
                            FingerprintEnrollSidecar.this;
                    BiometricEnrollSidecar.Listener listener = fingerprintEnrollSidecar.mListener;
                    if (listener != null) {
                        listener.onUdfpsPointerDown();
                    } else {
                        fingerprintEnrollSidecar.mQueuedEvents.add(
                                new BiometricEnrollSidecar.QueuedUdfpsPointerUp(i, 1));
                    }
                }

                public final void onUdfpsPointerUp(int i) {
                    FingerprintEnrollSidecar fingerprintEnrollSidecar =
                            FingerprintEnrollSidecar.this;
                    BiometricEnrollSidecar.Listener listener = fingerprintEnrollSidecar.mListener;
                    if (listener != null) {
                        listener.onUdfpsPointerUp();
                    } else {
                        fingerprintEnrollSidecar.mQueuedEvents.add(
                                new BiometricEnrollSidecar.QueuedUdfpsPointerUp(i, 0));
                    }
                }
            };

    public FingerprintUpdater mFingerprintUpdater;
    public final Intent mIntent;
    public final MessageDisplayController mMessageDisplayController;
    public final boolean mMessageDisplayControllerFlag;

    public FingerprintEnrollSidecar(int i, Context context, Intent intent) {
        this.mEnrollReason = i;
        this.mIntent = intent;
        int integer =
                context.getResources().getInteger(R.integer.enrollment_help_minimum_time_display);
        int integer2 =
                context.getResources()
                        .getInteger(R.integer.enrollment_progress_minimum_time_display);
        boolean z =
                context.getResources().getBoolean(R.bool.enrollment_progress_priority_over_help);
        boolean z2 =
                context.getResources().getBoolean(R.bool.enrollment_prioritize_acquire_messages);
        int integer3 = context.getResources().getInteger(R.integer.enrollment_collect_time);
        this.mMessageDisplayControllerFlag =
                context.getResources()
                        .getBoolean(R.bool.enrollment_message_display_controller_flag);
        this.mMessageDisplayController =
                new MessageDisplayController(
                        context.getMainThreadHandler(),
                        this.mEnrollmentCallback,
                        SystemClock.elapsedRealtimeClock(),
                        integer,
                        integer2,
                        z,
                        z2,
                        integer3);
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return IKnoxCustomManager.Stub.TRANSACTION_setHomeScreenMode;
    }

    @Override // com.android.settings.biometrics.BiometricEnrollSidecar,
              // androidx.fragment.app.Fragment
    public final void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mFingerprintUpdater = new FingerprintUpdater(activity);
    }

    @Override // com.android.settings.biometrics.BiometricEnrollSidecar
    public final void startEnrollment() {
        super.startEnrollment();
        if (this.mToken == null) {
            Log.e("FingerprintEnrollSidecar", "Null hardware auth token for enroll");
            onEnrollmentError(1, getString(R.string.fingerprint_intro_error_unknown));
            return;
        }
        if (this.mIntent.getIntExtra("enroll_reason", -1) == -1) {
            this.mIntent.putExtra(
                    "enroll_reason", WizardManagerHelper.isAnySetupWizard(this.mIntent) ? 3 : 2);
        }
        int i = this.mEnrollReason;
        if (i == 2 && this.mMessageDisplayControllerFlag) {
            this.mFingerprintUpdater.enroll(
                    this.mToken,
                    this.mEnrollmentCancel,
                    this.mUserId,
                    this.mMessageDisplayController,
                    i,
                    this.mIntent);
        } else {
            this.mFingerprintUpdater.enroll(
                    this.mToken,
                    this.mEnrollmentCancel,
                    this.mUserId,
                    this.mEnrollmentCallback,
                    i,
                    this.mIntent);
        }
    }
}
