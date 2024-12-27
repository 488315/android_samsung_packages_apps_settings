package com.android.settings.biometrics.fingerprint;

import android.content.Intent;

import com.google.android.setupcompat.util.WizardManagerHelper;
import com.samsung.android.knox.custom.IKnoxCustomManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SetupFingerprintEnrollEnrolling extends FingerprintEnrollEnrolling {
    @Override // com.android.settings.biometrics.fingerprint.FingerprintEnrollEnrolling,
              // com.android.settings.biometrics.BiometricsEnrollEnrolling
    public final Intent getFinishIntent() {
        Intent intent = new Intent(this, (Class<?>) SetupFingerprintEnrollFinish.class);
        WizardManagerHelper.copyWizardManagerExtras(getIntent(), intent);
        return intent;
    }

    @Override // com.android.settings.biometrics.fingerprint.FingerprintEnrollEnrolling,
              // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return IKnoxCustomManager.Stub.TRANSACTION_getHomeScreenMode;
    }
}
