package com.samsung.android.settings.biometrics;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.SettingsBaseActivity;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.lockscreen.LockUtils;
import com.samsung.android.settings.security.SecurityUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BiometricsSetScreenLockActivity extends SettingsBaseActivity {
    public void onClickContinue(View view) {
        Log.d("BiometricsSetScreenLockActivity", "onClickContinue");
        setResult(-1);
        finish();
    }

    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity,
              // android.app.Activity, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        setLayout$1();
    }

    @Override // com.android.settings.core.SettingsBaseActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!SecurityUtils.isNotAvailableBiometricsWithDexAndMultiWindow(
                this,
                BiometricsGenericHelper.getAppId(
                        getIntent().getIntExtra("BIOMETRICS_LOCK_TYPE", 0)),
                "BiometricsSetScreenLockActivity")) {
            setLayout$1();
        } else {
            setResult(0);
            finish();
        }
    }

    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.activity.ComponentActivity, android.app.Activity
    public final void onMultiWindowModeChanged(boolean z) {
        super.onMultiWindowModeChanged(z);
        StringBuilder m =
                RowView$$ExternalSyntheticOutline0.m("onMultiWindowModeChanged: ", ", ", z);
        m.append(isResumed());
        Log.d("BiometricsSetScreenLockActivity", m.toString());
        if (LockUtils.isInMultiWindow(this)) {
            Toast.makeText(
                            this,
                            getString(R.string.sec_biometrics_common_not_use_multi_window_view),
                            0)
                    .show();
            setResult(0);
            finish();
        }
    }

    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onPause() {
        super.onPause();
        if (isFinishing() || isChangingConfigurations()) {
            return;
        }
        finish();
    }

    public final void setLayout$1() {
        setContentView(R.layout.sec_biometrics_disclaimer_lock_check_layout);
        TextView textView = (TextView) findViewById(R.id.biometrics_screen_lock_title_text);
        int keyguardStoredPasswordQuality =
                new LockPatternUtils(this).getKeyguardStoredPasswordQuality(UserHandle.myUserId());
        textView.setText(
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
        hideAppBar();
        BiometricsGenericHelper.removeSideMargin(this);
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        Button button = (Button) findViewById(R.id.continue_button);
        if (button != null) {
            Utils.setMaxFontScale$1(this, button);
        }
    }
}
