package com.android.settings.biometrics.face;

import android.os.Bundle;
import android.view.View;

import com.android.settings.R;
import com.android.settings.biometrics.BiometricEnrollBase;

import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FaceEnrollFinish extends BiometricEnrollBase {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return VolteConstants.ErrorCode.DSDA_FALLBACK_DSDS;
    }

    @Override // com.android.settings.biometrics.BiometricEnrollBase,
              // com.android.settings.core.InstrumentedActivity,
              // com.android.settingslib.core.lifecycle.ObservableActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.face_enroll_finish);
        setHeaderText(R.string.security_settings_face_enroll_finish_title);
        FooterBarMixin footerBarMixin = (FooterBarMixin) getLayout().getMixin(FooterBarMixin.class);
        this.mFooterBarMixin = footerBarMixin;
        footerBarMixin.setPrimaryButton(
                new FooterButton(
                        getString(R.string.security_settings_face_enroll_done),
                        new View
                                .OnClickListener() { // from class:
                                                     // com.android.settings.biometrics.face.FaceEnrollFinish$$ExternalSyntheticLambda0
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                FaceEnrollFinish.this.onNextButtonClick(view);
                            }
                        },
                        5,
                        2132083805));
    }

    public void onNextButtonClick(View view) {
        setResult(1);
        finish();
    }
}
