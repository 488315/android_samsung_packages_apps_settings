package com.android.settings.biometrics.face;

import android.app.Activity;
import android.content.Intent;
import android.hardware.face.FaceEnrollOptions;
import android.hardware.face.FaceManager;
import android.os.CancellationSignal;
import android.view.Surface;

import com.android.settings.biometrics.BiometricEnrollSidecar;

import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FaceEnrollSidecar extends BiometricEnrollSidecar {
    public final int[] mDisabledFeatures;
    public final AnonymousClass1 mEnrollmentCallback =
            new FaceManager
                    .EnrollmentCallback() { // from class:
                                            // com.android.settings.biometrics.face.FaceEnrollSidecar.1
                public final void onEnrollmentError(int i, CharSequence charSequence) {
                    FaceEnrollSidecar.this.onEnrollmentError(i, charSequence);
                }

                public final void onEnrollmentHelp(int i, CharSequence charSequence) {
                    FaceEnrollSidecar faceEnrollSidecar = FaceEnrollSidecar.this;
                    BiometricEnrollSidecar.Listener listener = faceEnrollSidecar.mListener;
                    if (listener != null) {
                        listener.onEnrollmentHelp(i, charSequence);
                        return;
                    }
                    ArrayList arrayList = faceEnrollSidecar.mQueuedEvents;
                    BiometricEnrollSidecar.QueuedEnrollmentHelp queuedEnrollmentHelp =
                            new BiometricEnrollSidecar.QueuedEnrollmentHelp(0);
                    queuedEnrollmentHelp.helpMsgId = i;
                    queuedEnrollmentHelp.helpString = charSequence;
                    arrayList.add(queuedEnrollmentHelp);
                }

                public final void onEnrollmentProgress(int i) {
                    FaceEnrollSidecar.this.onEnrollmentProgress(i);
                }
            };
    public FaceUpdater mFaceUpdater;
    public final Intent mIntent;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.settings.biometrics.face.FaceEnrollSidecar$1] */
    public FaceEnrollSidecar(int[] iArr, Intent intent) {
        this.mDisabledFeatures = Arrays.copyOf(iArr, iArr.length);
        this.mIntent = intent;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1509;
    }

    @Override // com.android.settings.biometrics.BiometricEnrollSidecar,
              // androidx.fragment.app.Fragment
    public final void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mFaceUpdater = new FaceUpdater(activity);
    }

    @Override // com.android.settings.biometrics.BiometricEnrollSidecar
    public final void startEnrollment() {
        super.startEnrollment();
        FaceUpdater faceUpdater = this.mFaceUpdater;
        int i = this.mUserId;
        byte[] bArr = this.mToken;
        CancellationSignal cancellationSignal = this.mEnrollmentCancel;
        AnonymousClass1 anonymousClass1 = this.mEnrollmentCallback;
        int[] iArr = this.mDisabledFeatures;
        Intent intent = this.mIntent;
        faceUpdater.getClass();
        FaceUpdater.NotifyingEnrollmentCallback notifyingEnrollmentCallback =
                new FaceUpdater.NotifyingEnrollmentCallback(faceUpdater.mContext, anonymousClass1);
        FaceManager faceManager = faceUpdater.mFaceManager;
        FaceUpdater.NotifyingEnrollmentCallback notifyingEnrollmentCallback2 =
                new FaceUpdater.NotifyingEnrollmentCallback(
                        faceUpdater.mContext, notifyingEnrollmentCallback);
        int intExtra = intent.getIntExtra("enroll_reason", -1);
        FaceEnrollOptions.Builder builder = new FaceEnrollOptions.Builder();
        builder.setEnrollReason(0);
        if (intExtra != -1) {
            builder.setEnrollReason(intExtra);
        }
        faceManager.enroll(
                i,
                bArr,
                cancellationSignal,
                notifyingEnrollmentCallback2,
                iArr,
                (Surface) null,
                false,
                builder.build());
    }
}
