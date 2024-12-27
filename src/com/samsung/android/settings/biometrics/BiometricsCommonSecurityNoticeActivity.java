package com.samsung.android.settings.biometrics;

import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.android.settings.R;
import com.android.settings.core.SettingsBaseActivity;

import com.samsung.android.settings.security.SecurityUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BiometricsCommonSecurityNoticeActivity extends SettingsBaseActivity {
    public ActionBar mActionBar;
    public BiometricsCommonSecurityNoticeActivity mContext;
    public TextView mFaceDescView;
    public TextView mFingerprintDescView;
    public LinearLayout mFingerprintView;
    public boolean mFinishSetting = true;
    public boolean mFromSetupWizard;
    public int mUserId;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // android.app.Activity
    public final void onBackPressed() {
        this.mFinishSetting = false;
        super.onBackPressed();
    }

    @Override // com.android.settings.core.SettingsBaseActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d("BiometricsCommonSecurityNoticeActivity", "onCreate");
        this.mContext = this;
        this.mUserId =
                getIntent().getIntExtra("android.intent.extra.USER_ID", UserHandle.myUserId());
        Log.d("BiometricsCommonSecurityNoticeActivity", "mUserId : " + this.mUserId);
        this.mFromSetupWizard = getIntent().getBooleanExtra("fromSetupWizard", false);
        setContentView(R.layout.sec_biometrics_security_notice_layout);
        this.mActionBar = getSupportActionBar();
        this.mFingerprintView =
                (LinearLayout)
                        findViewById(R.id.biometrics_common_security_notice_body_fingerprint);
        this.mFingerprintDescView =
                (TextView)
                        findViewById(R.id.biometrics_common_security_notice_body_fingerprint_desc);
        this.mFaceDescView =
                (TextView) findViewById(R.id.biometrics_common_security_notice_body_face_desc);
        Log.d("BiometricsCommonSecurityNoticeActivity", "setDescriptionString");
        if (this.mFingerprintView == null || SecurityUtils.hasFingerprintFeature(this.mContext)) {
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

    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onPause() {
        super.onPause();
        if (isChangingConfigurations()) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("biometrics_settings_destroy", this.mFinishSetting);
        intent.putExtra("screen_lock_force_destroy", this.mFinishSetting);
        setResult(0, intent);
        finish();
    }

    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        super.onResume();
        ActionBar actionBar = this.mActionBar;
        if (actionBar != null && !this.mFromSetupWizard) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            this.mActionBar.setHomeButtonEnabled();
        }
        BiometricsCommonSecurityNoticeActivity biometricsCommonSecurityNoticeActivity =
                this.mContext;
        if (biometricsCommonSecurityNoticeActivity != null) {
            setTitle(
                    biometricsCommonSecurityNoticeActivity.getString(
                            R.string.sec_biometrics_common_about_security_notice_title));
        } else {
            Log.e("BiometricsCommonSecurityNoticeActivity", "mContext is null");
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.action_bar);
        if (toolbar != null) {
            toolbar.setContentInsetsAbsolute(
                    toolbar.getContentInsetStart(), toolbar.getContentInsetStart());
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity,
              // android.app.Activity
    public final void onStop() {
        this.mContext = null;
        super.onStop();
    }
}
