package com.android.settings.biometrics;

import android.os.Bundle;
import android.view.View;

import com.android.settings.R;

import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BiometricHandoffActivity extends BiometricEnrollBase {
    public FooterButton mPrimaryFooterButton;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1894;
    }

    @Override // com.android.settings.biometrics.BiometricEnrollBase,
              // com.android.settings.core.InstrumentedActivity,
              // com.android.settingslib.core.lifecycle.ObservableActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.biometric_handoff);
        setHeaderText(R.string.biometric_settings_hand_back_to_guardian);
        FooterBarMixin footerBarMixin = (FooterBarMixin) getLayout().getMixin(FooterBarMixin.class);
        this.mFooterBarMixin = footerBarMixin;
        if (this.mPrimaryFooterButton == null) {
            this.mPrimaryFooterButton =
                    new FooterButton(
                            getString(R.string.biometric_settings_hand_back_to_guardian_ok),
                            new View
                                    .OnClickListener() { // from class:
                                                         // com.android.settings.biometrics.BiometricHandoffActivity$$ExternalSyntheticLambda0
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) {
                                    BiometricHandoffActivity biometricHandoffActivity =
                                            BiometricHandoffActivity.this;
                                    biometricHandoffActivity.setResult(-1);
                                    biometricHandoffActivity.finish();
                                }
                            },
                            5,
                            2132083805);
        }
        footerBarMixin.setPrimaryButton(this.mPrimaryFooterButton);
    }
}
