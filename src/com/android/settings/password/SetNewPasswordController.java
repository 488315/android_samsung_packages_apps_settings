package com.android.settings.password;

import android.app.admin.DevicePolicyManager;
import android.content.pm.PackageManager;
import android.hardware.face.FaceManager;
import android.hardware.fingerprint.FingerprintManager;

import com.android.internal.util.Preconditions;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SetNewPasswordController {
    public final DevicePolicyManager mDevicePolicyManager;
    public final FaceManager mFaceManager;
    public final FingerprintManager mFingerprintManager;
    public final PackageManager mPackageManager;
    public final int mTargetUserId;
    public final Ui mUi;
    public boolean mShowCredentialTypeOnly = false;
    public boolean mFromSecNonBiometrics = false;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Ui {}

    public SetNewPasswordController(
            int i,
            PackageManager packageManager,
            FingerprintManager fingerprintManager,
            FaceManager faceManager,
            DevicePolicyManager devicePolicyManager,
            Ui ui) {
        this.mTargetUserId = i;
        this.mPackageManager = (PackageManager) Preconditions.checkNotNull(packageManager);
        this.mFingerprintManager = fingerprintManager;
        this.mFaceManager = faceManager;
        this.mDevicePolicyManager =
                (DevicePolicyManager) Preconditions.checkNotNull(devicePolicyManager);
        this.mUi = (Ui) Preconditions.checkNotNull(ui);
    }
}
