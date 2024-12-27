package com.android.settings.biometrics.face;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FaceEnrollIntroductionInternal extends FragmentActivity {
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (isFinishing()) {
            return;
        }
        Intent intent = new Intent(getIntent());
        intent.setFlags(0);
        intent.setClassName(
                KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                FaceEnrollIntroduction.class.getName());
        startActivity(intent);
        finish();
    }
}
