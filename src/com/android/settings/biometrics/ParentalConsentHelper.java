package com.android.settings.biometrics;

import android.app.Activity;
import android.content.Intent;

import com.android.settings.biometrics.face.FaceEnrollParentalConsent;
import com.android.settings.biometrics.fingerprint.FingerprintEnrollParentalConsent;

import com.google.android.setupcompat.util.WizardManagerHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ParentalConsentHelper {
    public Boolean mConsentFace;
    public Boolean mConsentFingerprint;
    public long mGkPwHandle;
    public boolean mRequireFace;
    public boolean mRequireFingerprint;

    public final boolean launchNext(Activity activity) {
        Intent intent =
                (this.mRequireFingerprint && this.mConsentFingerprint == null)
                        ? new Intent(activity, (Class<?>) FingerprintEnrollParentalConsent.class)
                        : (this.mRequireFace && this.mConsentFace == null)
                                ? new Intent(activity, (Class<?>) FaceEnrollParentalConsent.class)
                                : null;
        if (intent == null) {
            return false;
        }
        WizardManagerHelper.copyWizardManagerExtras(activity.getIntent(), intent);
        long j = this.mGkPwHandle;
        if (j != 0) {
            intent.putExtra("gk_pw_handle", j);
        }
        activity.startActivityForResult(intent, 3);
        return true;
    }
}
