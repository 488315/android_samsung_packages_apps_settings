package com.android.settings.wifi.dpp;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.biometrics.BiometricPrompt;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.security.keystore.KeyGenParameterSpec;
import android.text.TextUtils;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.Utils;

import com.samsung.android.knox.net.wifi.WifiPolicy;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.Iterator;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class WifiDppUtils {
    public static final Duration VIBRATE_DURATION_QR_CODE_RECOGNITION = Duration.ofMillis(3);

    public static SecretKey generateSecretKey() {
        KeyGenParameterSpec build =
                new KeyGenParameterSpec.Builder("wifi_sharing_auth_key", 1)
                        .setBlockModes("CBC")
                        .setEncryptionPaddings("PKCS7Padding")
                        .setUserAuthenticationRequired(true)
                        .setUserAuthenticationParameters(60, 3)
                        .build();
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(build);
            return keyGenerator.generateKey();
        } catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException unused) {
            return null;
        }
    }

    public static Intent getEnrolleeQrCodeScannerIntent(Context context, String str) {
        Intent intent = new Intent(context, (Class<?>) WifiDppEnrolleeActivity.class);
        intent.setAction("android.settings.WIFI_DPP_ENROLLEE_QR_CODE_SCANNER");
        if (!TextUtils.isEmpty(str)) {
            intent.putExtra("ssid", str);
        }
        return intent;
    }

    public static String getSecurityString(WifiConfiguration wifiConfiguration) {
        return wifiConfiguration.allowedKeyManagement.get(8)
                ? WifiPolicy.SECURITY_TYPE_SAE
                : wifiConfiguration.allowedKeyManagement.get(9)
                        ? "nopass"
                        : (wifiConfiguration.allowedKeyManagement.get(1)
                                        || wifiConfiguration.allowedKeyManagement.get(4))
                                ? "WPA"
                                : wifiConfiguration.wepKeys[0] == null ? "nopass" : "WEP";
    }

    public static boolean isSupportEnrolleeQrCodeScanner(Context context, int i) {
        if (((WifiManager) context.getSystemService(WifiManager.class)).isEasyConnectSupported()) {
            WifiManager wifiManager = (WifiManager) context.getSystemService(WifiManager.class);
            if (i == 2) {
                return true;
            }
            if (i == 5 && wifiManager.isWpa3SaeSupported()) {
                return true;
            }
        }
        WifiManager wifiManager2 = (WifiManager) context.getSystemService(WifiManager.class);
        if (i == 0 || i == 1 || i == 2) {
            return true;
        }
        if (i != 4) {
            if (i == 5 && wifiManager2.isWpa3SaeSupported()) {
                return true;
            }
        } else if (wifiManager2.isEnhancedOpenSupported()) {
            return true;
        }
        return false;
    }

    public static String removeFirstAndLastDoubleQuotes(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        int length = str.length();
        int i = length - 1;
        int i2 = str.charAt(0) == '\"' ? 1 : 0;
        if (str.charAt(i) == '\"') {
            i = length - 2;
        }
        return str.substring(i2, i + 1);
    }

    public static void setConfiguratorIntentExtra(
            Intent intent, WifiManager wifiManager, WifiConfiguration wifiConfiguration) {
        String str;
        String removeFirstAndLastDoubleQuotes =
                removeFirstAndLastDoubleQuotes(wifiConfiguration.SSID);
        String securityString = getSecurityString(wifiConfiguration);
        Iterator it = wifiManager.getPrivilegedConfiguredNetworks().iterator();
        while (true) {
            if (!it.hasNext()) {
                str = wifiConfiguration.preSharedKey;
                break;
            } else {
                WifiConfiguration wifiConfiguration2 = (WifiConfiguration) it.next();
                if (wifiConfiguration2.networkId == wifiConfiguration.networkId) {
                    str =
                            (wifiConfiguration.allowedKeyManagement.get(0)
                                            && wifiConfiguration.allowedAuthAlgorithms.get(1))
                                    ? wifiConfiguration2.wepKeys[wifiConfiguration2.wepTxKeyIndex]
                                    : wifiConfiguration2.preSharedKey;
                }
            }
        }
        String removeFirstAndLastDoubleQuotes2 = removeFirstAndLastDoubleQuotes(str);
        if (!TextUtils.isEmpty(removeFirstAndLastDoubleQuotes)) {
            intent.putExtra("ssid", removeFirstAndLastDoubleQuotes);
        }
        if (!TextUtils.isEmpty(securityString)) {
            intent.putExtra("security", securityString);
        }
        if (!TextUtils.isEmpty(removeFirstAndLastDoubleQuotes2)) {
            intent.putExtra("preSharedKey", removeFirstAndLastDoubleQuotes2);
        }
        intent.putExtra("hiddenSsid", wifiConfiguration.hiddenSSID);
    }

    public static void showLockScreen(Context context, final Runnable runnable) {
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService("keyguard");
        if (!keyguardManager.isKeyguardSecure()) {
            runnable.run();
            return;
        }
        BiometricPrompt.AuthenticationCallback authenticationCallback =
                new BiometricPrompt
                        .AuthenticationCallback() { // from class:
                                                    // com.android.settings.wifi.dpp.WifiDppUtils.1
                    @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
                    public final void onAuthenticationSucceeded(
                            BiometricPrompt.AuthenticationResult authenticationResult) {
                        runnable.run();
                    }

                    @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
                    public final void onAuthenticationError(int i, CharSequence charSequence) {}
                };
        int myUserId = UserHandle.myUserId();
        BiometricPrompt.Builder title =
                new BiometricPrompt.Builder(context)
                        .setTitle(context.getText(R.string.wifi_dpp_lockscreen_title));
        if (keyguardManager.isDeviceSecure()) {
            title.setDeviceCredentialAllowed(true);
            StringBuilder sb = Utils.sBuilder;
            title.setTextForDeviceCredential(
                    null,
                    Utils.getConfirmCredentialStringForUser(
                            context,
                            myUserId,
                            new LockPatternUtils(context).getCredentialTypeForUser(myUserId)),
                    null);
        }
        title.build()
                .authenticate(
                        new CancellationSignal(),
                        new WifiDppUtils$$ExternalSyntheticLambda1(
                                new Handler(Looper.getMainLooper()), 1),
                        authenticationCallback);
    }
}
