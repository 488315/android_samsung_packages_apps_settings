package com.android.settings.biometrics.combination;

import android.content.Context;
import android.hardware.face.FaceManager;
import android.hardware.fingerprint.FingerprintManager;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.biometrics.ParentalControlsUtils;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.utils.StringUtil;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class CombinedBiometricStatusUtils {
    public final Context mContext;
    public final FaceManager mFaceManager;
    public final FingerprintManager mFingerprintManager;
    public final int mUserId;

    public CombinedBiometricStatusUtils(Context context, int i) {
        this.mContext = context;
        this.mFingerprintManager = Utils.getFingerprintManagerOrNull(context);
        this.mFaceManager = Utils.getFaceManagerOrNull(context);
        this.mUserId = i;
    }

    public final RestrictedLockUtils.EnforcedAdmin getDisablingAdmin() {
        RestrictedLockUtils.EnforcedAdmin parentConsentRequired =
                ParentalControlsUtils.parentConsentRequired(this.mContext, 8);
        RestrictedLockUtils.EnforcedAdmin parentConsentRequired2 =
                ParentalControlsUtils.parentConsentRequired(this.mContext, 2);
        boolean z = parentConsentRequired != null;
        boolean z2 = parentConsentRequired2 != null;
        if (z && z2) {
            return parentConsentRequired;
        }
        return null;
    }

    public final String getSummary() {
        FingerprintManager fingerprintManager = this.mFingerprintManager;
        boolean z = false;
        int i = this.mUserId;
        int size =
                fingerprintManager != null
                        ? fingerprintManager.getEnrolledFingerprints(i).size()
                        : 0;
        FaceManager faceManager = this.mFaceManager;
        if (faceManager != null && faceManager.hasEnrolledTemplates(i)) {
            z = true;
        }
        return (!z || size <= 1)
                ? (z && size == 1)
                        ? this.mContext.getString(
                                R.string
                                        .security_settings_biometric_preference_summary_both_fp_single)
                        : z
                                ? this.mContext.getString(
                                        R.string.security_settings_face_preference_summary)
                                : size > 0
                                        ? StringUtil.getIcuPluralsString(
                                                this.mContext,
                                                size,
                                                R.string
                                                        .security_settings_fingerprint_preference_summary)
                                        : this.mContext.getString(
                                                R.string
                                                        .security_settings_biometric_preference_summary_none_enrolled)
                : this.mContext.getString(
                        R.string.security_settings_biometric_preference_summary_both_fp_multiple);
    }
}
