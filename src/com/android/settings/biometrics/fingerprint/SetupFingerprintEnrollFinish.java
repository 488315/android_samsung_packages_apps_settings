package com.android.settings.biometrics.fingerprint;

import android.content.Intent;

import com.android.settings.R;

import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupcompat.util.WizardManagerHelper;
import com.samsung.android.knox.custom.IKnoxCustomManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SetupFingerprintEnrollFinish extends FingerprintEnrollFinish {
    @Override // com.android.settings.biometrics.BiometricEnrollBase
    public final Intent getFingerprintEnrollingIntent() {
        Intent intent = new Intent(this, (Class<?>) SetupFingerprintEnrollEnrolling.class);
        intent.putExtra("hw_auth_token", this.mToken);
        int i = this.mUserId;
        if (i != -10000) {
            intent.putExtra("android.intent.extra.USER_ID", i);
        }
        WizardManagerHelper.copyWizardManagerExtras(getIntent(), intent);
        return intent;
    }

    @Override // com.android.settings.biometrics.fingerprint.FingerprintEnrollFinish,
              // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return IKnoxCustomManager.Stub.TRANSACTION_removeDexShortcut;
    }

    @Override // com.android.settings.biometrics.BiometricEnrollBase
    public final void initViews$1() {
        super.initViews$1();
        FooterBarMixin footerBarMixin = this.mFooterBarMixin;
        FooterButton footerButton = footerBarMixin != null ? footerBarMixin.primaryButton : null;
        footerButton.getClass();
        footerButton.setText(getText(R.string.next_label));
    }
}
