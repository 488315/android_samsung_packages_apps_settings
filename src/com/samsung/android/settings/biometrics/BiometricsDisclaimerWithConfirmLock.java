package com.samsung.android.settings.biometrics;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.biometrics.GatekeeperPasswordProvider;
import com.android.settings.password.ChooseLockSettingsHelper;

import com.samsung.android.knox.SemPersonaManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BiometricsDisclaimerWithConfirmLock extends BiometricsDisclaimerActivity {
    public boolean mIsRequestChallenge;
    public View mLayoutView;
    public int mUserId;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(
                        "onActivityResult : requestCode : ",
                        " resultCode : ",
                        i,
                        i2,
                        " data is NULL : "),
                intent == null,
                "BiometricsDisclaimerWithConfirmLock");
        if (i == 1000) {
            if (i2 != -1) {
                setResult(0);
                finish();
                return;
            }
            this.mIsWaitConfirmLock = false;
            this.mIsRequestChallenge = true;
            View view = this.mLayoutView;
            if (view != null) {
                view.setVisibility(0);
            }
            if (GatekeeperPasswordProvider.containsGatekeeperPasswordHandle(intent)) {
                this.mGkPwHandle = GatekeeperPasswordProvider.getGatekeeperPasswordHandle(intent);
                Intent intent2 = new Intent();
                intent2.putExtra("gk_pw_handle", this.mGkPwHandle);
                setResult(0, intent2);
            }
        }
    }

    @Override // com.samsung.android.settings.biometrics.BiometricsDisclaimerActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity,
              // android.app.Activity, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        View findViewById = findViewById(R.id.biometrics_disclaimer);
        this.mLayoutView = findViewById;
        if (this.mIsRequestChallenge || findViewById == null || this.mGkPwHandle != 0) {
            return;
        }
        findViewById.setVisibility(4);
    }

    @Override // com.samsung.android.settings.biometrics.BiometricsDisclaimerActivity,
              // com.android.settings.core.SettingsBaseActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mUserId =
                getIntent().getIntExtra("android.intent.extra.USER_ID", UserHandle.myUserId());
    }

    @Override // com.samsung.android.settings.biometrics.BiometricsDisclaimerActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        View findViewById = findViewById(R.id.biometrics_disclaimer);
        this.mLayoutView = findViewById;
        if (!this.mIsRequestChallenge && findViewById != null && this.mGkPwHandle == 0) {
            findViewById.setVisibility(4);
        }
        if (this.mGkPwHandle == 0 && !this.mIsWaitConfirmLock && !this.mIsRequestChallenge) {
            Log.d("BiometricsDisclaimerWithConfirmLock", "launchConfirmLock");
            ChooseLockSettingsHelper.Builder builder = new ChooseLockSettingsHelper.Builder(this);
            builder.mRequestCode = 1000;
            builder.mHeader = BiometricsGenericHelper.getConfirmLockHeader(this, this.mUserId);
            builder.mRequestGatekeeperPasswordHandle = true;
            builder.mForegroundOnly = true;
            builder.mReturnCredentials = true;
            int i = this.mUserId;
            if (i != -10000) {
                builder.mUserId = i;
            }
            if (SemPersonaManager.isKnoxId(i)
                    && !SemPersonaManager.isSecureFolderId(this.mUserId)) {
                builder.mKnoxWorkProfileSecurity = true;
            }
            this.mIsWaitConfirmLock = true;
            boolean show = builder.show();
            overridePendingTransition(0, 0);
            if (!show) {
                Log.e("BiometricsDisclaimerWithConfirmLock", "Fail launchConfirmationActivity!");
                this.mIsWaitConfirmLock = false;
                setResult(0);
                finish();
            }
        }
        super.onResume();
    }
}
