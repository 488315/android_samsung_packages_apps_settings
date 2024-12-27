package com.android.settings.biometrics.face;

import android.content.Context;
import android.hardware.face.Face;
import android.hardware.face.FaceEnrollCell;
import android.hardware.face.FaceManager;

import com.android.settings.Utils;
import com.android.settings.safetycenter.BiometricsSafetySource;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class FaceUpdater {
    public final Context mContext;
    public final FaceManager mFaceManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class NotifyingEnrollmentCallback extends FaceManager.EnrollmentCallback {
        public final FaceManager.EnrollmentCallback mCallback;
        public final Context mContext;

        public NotifyingEnrollmentCallback(
                Context context, FaceManager.EnrollmentCallback enrollmentCallback) {
            this.mContext = context;
            this.mCallback = enrollmentCallback;
        }

        public final void onEnrollmentError(int i, CharSequence charSequence) {
            this.mCallback.onEnrollmentError(i, charSequence);
        }

        public final void onEnrollmentFrame(
                int i,
                CharSequence charSequence,
                FaceEnrollCell faceEnrollCell,
                int i2,
                float f,
                float f2,
                float f3) {
            this.mCallback.onEnrollmentFrame(i, charSequence, faceEnrollCell, i2, f, f2, f3);
        }

        public final void onEnrollmentHelp(int i, CharSequence charSequence) {
            this.mCallback.onEnrollmentHelp(i, charSequence);
        }

        public final void onEnrollmentProgress(int i) {
            this.mCallback.onEnrollmentProgress(i);
            if (i == 0) {
                BiometricsSafetySource.onBiometricsChanged(this.mContext);
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class NotifyingRemovalCallback extends FaceManager.RemovalCallback {
        public final FaceManager.RemovalCallback mCallback;
        public final Context mContext;

        public NotifyingRemovalCallback(
                Context context, FaceManager.RemovalCallback removalCallback) {
            this.mContext = context;
            this.mCallback = removalCallback;
        }

        public final void onRemovalError(Face face, int i, CharSequence charSequence) {
            this.mCallback.onRemovalError(face, i, charSequence);
        }

        public final void onRemovalSucceeded(Face face, int i) {
            this.mCallback.onRemovalSucceeded(face, i);
            BiometricsSafetySource.onBiometricsChanged(this.mContext);
        }
    }

    public FaceUpdater(Context context) {
        this.mContext = context;
        this.mFaceManager = Utils.getFaceManagerOrNull(context);
    }

    public FaceUpdater(Context context, FaceManager faceManager) {
        this.mContext = context;
        this.mFaceManager = faceManager;
    }
}
