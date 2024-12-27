package com.samsung.android.settings.biometrics;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.settings.R;

import com.samsung.android.settings.security.SecurityUtils;
import com.sec.android.secsetupwizardlib.SuwBaseActivity;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SuwBiometricsCommonSecurityNoticeActivity extends SuwBaseActivity {
    public TextView mFaceDescView;
    public TextView mFingerprintDescView;
    public LinearLayout mFingerprintView;

    @Override // com.sec.android.secsetupwizardlib.SuwBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.sec_biometrics_security_notice_layout, true);
        setHeaderTitle(R.string.sec_biometrics_common_about_security_notice_title);
        this.mFingerprintView =
                (LinearLayout)
                        findViewById(R.id.biometrics_common_security_notice_body_fingerprint);
        this.mFingerprintDescView =
                (TextView)
                        findViewById(R.id.biometrics_common_security_notice_body_fingerprint_desc);
        this.mFaceDescView =
                (TextView) findViewById(R.id.biometrics_common_security_notice_body_face_desc);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        super.onResume();
        Log.d("SuwBiometricsCommonSecurityNoticeActivity", "setDescriptionString");
        if (this.mFingerprintView == null || SecurityUtils.hasFingerprintFeature(this)) {
            TextView textView = this.mFingerprintDescView;
            if (textView != null) {
                textView.setText(BiometricsGenericHelper.getSecurityLevelDescription(this, 1));
            }
        } else {
            this.mFingerprintView.setVisibility(8);
        }
        TextView textView2 = this.mFaceDescView;
        if (textView2 != null) {
            textView2.setText(BiometricsGenericHelper.getSecurityLevelDescription(this, 256));
        }
    }
}
