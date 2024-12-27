package com.android.settings.wifi.dpp;

import android.app.KeyguardManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContracts$StartActivityForResult;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.core.InstrumentedActivity;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class WifiDppConfiguratorAuthActivity extends InstrumentedActivity {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1595;
    }

    @VisibleForTesting
    public void onAuthResult(ActivityResult activityResult) {
        if (activityResult.mResultCode == -1) {
            startQrCodeActivity();
        }
        finish();
    }

    @Override // com.android.settings.core.InstrumentedActivity,
              // com.android.settingslib.core.lifecycle.ObservableActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().clearFlags(2);
        Intent createConfirmDeviceCredentialIntent =
                ((KeyguardManager) getSystemService(KeyguardManager.class))
                        .createConfirmDeviceCredentialIntent(
                                getText(R.string.wifi_dpp_lockscreen_title), null, getUserId());
        if (createConfirmDeviceCredentialIntent != null) {
            Duration duration = WifiDppUtils.VIBRATE_DURATION_QR_CODE_RECOGNITION;
            try {
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
                cipher.init(1, WifiDppUtils.generateSecretKey());
                cipher.doFinal();
            } catch (InvalidKeyException
                    | NoSuchAlgorithmException
                    | BadPaddingException
                    | IllegalBlockSizeException
                    | NoSuchPaddingException unused) {
                registerForActivityResult(
                                new ActivityResultContracts$StartActivityForResult(0),
                                new ActivityResultCallback() { // from class:
                                                               // com.android.settings.wifi.dpp.WifiDppConfiguratorAuthActivity$$ExternalSyntheticLambda0
                                    @Override // androidx.activity.result.ActivityResultCallback
                                    public final void onActivityResult(Object obj) {
                                        WifiDppConfiguratorAuthActivity.this.onAuthResult(
                                                (ActivityResult) obj);
                                    }
                                })
                        .launch(createConfirmDeviceCredentialIntent);
                return;
            }
        }
        startQrCodeActivity();
        finish();
    }

    public final void startQrCodeActivity() {
        sendBroadcast(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS").setFlags(268435456));
        Intent intent = new Intent();
        intent.setAction("android.settings.WIFI_DPP_CONFIGURATOR_QR_CODE_GENERATOR");
        intent.setPackage(getPackageName());
        intent.putExtras(getIntent());
        startActivity(intent);
    }
}
