package com.android.settings.biometrics.fingerprint;

import com.android.settings.R;

import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FingerprintSuggestionActivity extends SetupFingerprintEnrollIntroduction {
    public static final /* synthetic */ int $r8$clinit = 0;

    @Override // android.app.Activity
    public final void finish() {
        setResult(0);
        super.finish();
    }

    @Override // com.android.settings.biometrics.fingerprint.FingerprintEnrollIntroduction,
              // com.android.settings.biometrics.BiometricEnrollIntroduction,
              // com.android.settings.biometrics.BiometricEnrollBase
    public final void initViews$1() {
        super.initViews$1();
        FooterBarMixin footerBarMixin = this.mFooterBarMixin;
        FooterButton footerButton = footerBarMixin != null ? footerBarMixin.secondaryButton : null;
        footerButton.getClass();
        footerButton.setText(
                getText(R.string.security_settings_fingerprint_enroll_introduction_cancel));
    }
}
