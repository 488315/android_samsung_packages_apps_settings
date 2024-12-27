package com.samsung.android.settings.biometrics;

import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.view.View;
import android.view.ViewGroup;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.password.SetupSkipDialog;

import com.samsung.android.settings.Rune;
import com.sec.android.secsetupwizardlib.SuwBaseActivity;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SuwBiometricsSetScreenLockActivity extends SuwBaseActivity {
    public static final /* synthetic */ int $r8$clinit = 0;

    @Override // com.sec.android.secsetupwizardlib.SuwBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        int keyguardStoredPasswordQuality =
                new LockPatternUtils(this).getKeyguardStoredPasswordQuality(UserHandle.myUserId());
        this.mRootLayout.setHeaderText(
                keyguardStoredPasswordQuality != 65536
                        ? (keyguardStoredPasswordQuality == 131072
                                        || keyguardStoredPasswordQuality == 196608)
                                ? getString(R.string.sec_pin_set_as_screen_lock_type)
                                : (keyguardStoredPasswordQuality == 262144
                                                || keyguardStoredPasswordQuality == 327680
                                                || keyguardStoredPasswordQuality == 393216)
                                        ? getString(R.string.sec_password_set_as_screen_lock_type)
                                        : null
                        : getString(R.string.sec_pattern_set_as_screen_lock_type));
        setDescriptionText(R.string.sec_biometrics_chooselock_description_common);
        setHeaderIcon(getDrawable(R.drawable.sec_ic_biometric_lock_suw));
        setContentView(R.layout.sec_biometrics_disclaimer_suw_lock_check_layout);
        final int i = 0;
        setPrimaryActionButton(
                R.string.sec_biometrics_disclaimer_button_next,
                new View.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.biometrics.SuwBiometricsSetScreenLockActivity$$ExternalSyntheticLambda0
                    public final /* synthetic */ SuwBiometricsSetScreenLockActivity f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i2 = i;
                        SuwBiometricsSetScreenLockActivity suwBiometricsSetScreenLockActivity =
                                this.f$0;
                        switch (i2) {
                            case 0:
                                int i3 = SuwBiometricsSetScreenLockActivity.$r8$clinit;
                                suwBiometricsSetScreenLockActivity.setResult(-1);
                                suwBiometricsSetScreenLockActivity.finish();
                                break;
                            default:
                                int i4 = SuwBiometricsSetScreenLockActivity.$r8$clinit;
                                Intent intent = suwBiometricsSetScreenLockActivity.getIntent();
                                SetupSkipDialog.newInstance(
                                                intent.getBooleanExtra(
                                                        ":settings:frp_supported", false),
                                                intent.getBooleanExtra("isSetupFlow", false))
                                        .show(
                                                suwBiometricsSetScreenLockActivity
                                                        .getSupportFragmentManager());
                                break;
                        }
                    }
                });
        if (!Rune.LOCKSCREEN_SECURITY_HIDE_SKIP_SUW_BUTTON) {
            final int i2 = 1;
            setSecondaryActionButton(
                    R.string.sec_biometrics_disclaimer_button_skip,
                    new View.OnClickListener(
                            this) { // from class:
                                    // com.samsung.android.settings.biometrics.SuwBiometricsSetScreenLockActivity$$ExternalSyntheticLambda0
                        public final /* synthetic */ SuwBiometricsSetScreenLockActivity f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            int i22 = i2;
                            SuwBiometricsSetScreenLockActivity suwBiometricsSetScreenLockActivity =
                                    this.f$0;
                            switch (i22) {
                                case 0:
                                    int i3 = SuwBiometricsSetScreenLockActivity.$r8$clinit;
                                    suwBiometricsSetScreenLockActivity.setResult(-1);
                                    suwBiometricsSetScreenLockActivity.finish();
                                    break;
                                default:
                                    int i4 = SuwBiometricsSetScreenLockActivity.$r8$clinit;
                                    Intent intent = suwBiometricsSetScreenLockActivity.getIntent();
                                    SetupSkipDialog.newInstance(
                                                    intent.getBooleanExtra(
                                                            ":settings:frp_supported", false),
                                                    intent.getBooleanExtra("isSetupFlow", false))
                                            .show(
                                                    suwBiometricsSetScreenLockActivity
                                                            .getSupportFragmentManager());
                                    break;
                            }
                        }
                    });
        }
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.sswl_scroll_view);
        if (viewGroup != null) {
            viewGroup.setFocusable(false);
        }
    }
}
