package com.android.settings.wifi;

import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.hardware.biometrics.BiometricManager;
import android.hardware.biometrics.BiometricPrompt;
import android.net.InetAddresses;
import android.net.TetheringManager;
import android.net.wifi.SoftApConfiguration;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.compose.runtime.ComposerKt$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.password.ChooseLockSettingsHelper;
import com.android.settings.wifi.dpp.WifiDppUtils;
import com.android.settings.wifi.dpp.WifiDppUtils$$ExternalSyntheticLambda1;
import com.android.wifitrackerlib.WifiEntry;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.wifi.WifiDevicePolicyManager;
import com.samsung.android.settings.wifi.details.WifiDppAuthenticationErrorListener;
import com.samsung.android.wifitrackerlib.SemWifiUtils;
import com.sec.ims.ft.FtIntent;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.Inet4Address;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class WifiUtils extends com.android.settingslib.wifi.WifiUtils {
    public static Boolean sCanShowWifiHotspotCached;

    public static void authenticateUser(
            int i,
            final Runnable runnable,
            final WifiDppAuthenticationErrorListener wifiDppAuthenticationErrorListener,
            Fragment fragment) {
        Context context = fragment.getContext();
        if (!isSupportVerifyingForPassword(context)) {
            runnable.run();
            return;
        }
        if (((BiometricManager) context.getSystemService(BiometricManager.class))
                        .canAuthenticate(255)
                != 0) {
            Resources resources = fragment.getActivity().getResources();
            ChooseLockSettingsHelper.Builder builder =
                    new ChooseLockSettingsHelper.Builder(fragment.getActivity(), fragment);
            builder.mRequestCode = i;
            builder.mTitle = resources.getText(R.string.main_clear_short_title);
            builder.show();
            return;
        }
        Duration duration = WifiDppUtils.VIBRATE_DURATION_QR_CODE_RECOGNITION;
        if (!((KeyguardManager) context.getSystemService("keyguard")).isKeyguardSecure()) {
            runnable.run();
            return;
        }
        BiometricPrompt.AuthenticationCallback authenticationCallback =
                new BiometricPrompt
                        .AuthenticationCallback() { // from class:
                                                    // com.android.settings.wifi.dpp.WifiDppUtils.2
                    @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
                    public final void onAuthenticationError(int i2, CharSequence charSequence) {
                        WifiDppAuthenticationErrorListener wifiDppAuthenticationErrorListener2 =
                                wifiDppAuthenticationErrorListener;
                        if (wifiDppAuthenticationErrorListener2 != null) {
                            wifiDppAuthenticationErrorListener2.onAuthenticationError();
                        }
                    }

                    @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
                    public final void onAuthenticationSucceeded(
                            BiometricPrompt.AuthenticationResult authenticationResult) {
                        runnable.run();
                    }
                };
        BiometricPrompt.Builder title =
                new BiometricPrompt.Builder(context)
                        .setTitle(context.getText(R.string.sec_biometircs_prompt_title));
        title.setNegativeButton(
                context.getString(R.string.cancel),
                context.getMainExecutor(),
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.android.settings.wifi.dpp.WifiDppUtils$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i2) {
                        WifiDppAuthenticationErrorListener wifiDppAuthenticationErrorListener2 =
                                WifiDppAuthenticationErrorListener.this;
                        if (wifiDppAuthenticationErrorListener2 != null) {
                            wifiDppAuthenticationErrorListener2.onAuthenticationError();
                        }
                    }
                });
        title.setConfirmationRequired(false);
        title.build()
                .authenticate(
                        new CancellationSignal(),
                        new WifiDppUtils$$ExternalSyntheticLambda1(
                                new Handler(Looper.getMainLooper()), 0),
                        authenticationCallback);
    }

    public static boolean canShowWifiHotspot(Context context) {
        if (sCanShowWifiHotspotCached == null) {
            boolean z = false;
            if (context != null) {
                if (!context.getResources().getBoolean(R.bool.config_show_wifi_hotspot_settings)) {
                    Log.w("WifiUtils", "config_show_wifi_hotspot_settings:false");
                } else if (((WifiManager) context.getSystemService(WifiManager.class)) == null) {
                    Log.e("WifiUtils", "WifiManager is null");
                } else {
                    TetheringManager tetheringManager =
                            (TetheringManager) context.getSystemService(TetheringManager.class);
                    if (tetheringManager == null) {
                        Log.e("WifiUtils", "TetheringManager is null");
                    } else {
                        String[] tetherableWifiRegexs = tetheringManager.getTetherableWifiRegexs();
                        if (tetherableWifiRegexs == null || tetherableWifiRegexs.length == 0) {
                            Log.w("WifiUtils", "TetherableWifiRegexs is empty");
                        } else {
                            z = true;
                        }
                    }
                }
            }
            sCanShowWifiHotspotCached = Boolean.valueOf(z);
        }
        return sCanShowWifiHotspotCached.booleanValue();
    }

    public static Inet4Address getIPv4Address(String str) {
        try {
            return (Inet4Address) InetAddresses.parseNumericAddress(str);
        } catch (ClassCastException | IllegalArgumentException unused) {
            return null;
        }
    }

    public static int getPhase2Method(int i, String str) {
        Log.d("WifiUtils", "getPhase2Method eapMethod: " + str + ", phase2: " + i);
        int i2 = 2;
        if ("PEAP".equals(str)) {
            if (i != 0) {
                if (i == 1) {
                    return 4;
                }
                if (i == 2) {
                    return 5;
                }
                if (i == 3) {
                    return 6;
                }
                if (i == 4) {
                    return 7;
                }
                Log.e("WifiUtils", "Unknown phase2: " + i + ", eap: PEAP");
                return 0;
            }
            return 3;
        }
        if (!"TTLS".equals(str)) {
            if (i != 0) {
                if (i == 1) {
                    return 4;
                }
                return 0;
            }
            return 3;
        }
        if (i == 0) {
            i2 = 1;
        } else if (i != 1) {
            if (i == 2) {
                i2 = 3;
            } else if (i != 3) {
                Log.e("WifiUtils", "Unknown phase2: " + i + ", eap: TTLS");
                i2 = 0;
            } else {
                i2 = 4;
            }
        }
        return i2;
    }

    public static Intent getSecScannerIntent() {
        Intent intent = new Intent();
        intent.setAction("com.sec.android.app.camera.action.SCAN_QR_CODE");
        intent.setPackage("com.sec.android.app.camera");
        intent.putExtra("request_qr_scan_type", "WIFI");
        intent.putExtra(FtIntent.Extras.EXTRA_REQUEST_RESULT, true);
        return intent;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x00c5, code lost:

       return r0;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.net.wifi.WifiConfiguration getWifiConfig(
            com.android.wifitrackerlib.WifiEntry r10, android.net.wifi.ScanResult r11) {
        /*
            if (r10 != 0) goto Ld
            if (r11 == 0) goto L5
            goto Ld
        L5:
            java.lang.IllegalArgumentException r10 = new java.lang.IllegalArgumentException
            java.lang.String r11 = "At least one of WifiEntry and ScanResult input is required."
            r10.<init>(r11)
            throw r10
        Ld:
            android.net.wifi.WifiConfiguration r0 = new android.net.wifi.WifiConfiguration
            r0.<init>()
            r1 = 0
            r2 = 4
            r3 = 3
            r4 = 6
            r5 = 2
            r6 = 5
            r7 = 1
            java.lang.String r8 = "\""
            if (r10 != 0) goto L76
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>(r8)
            java.lang.String r9 = r11.SSID
            java.lang.String r10 = androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0.m(r10, r9, r8)
            r0.SSID = r10
            java.lang.String r10 = r11.capabilities
            java.lang.String r8 = "WEP"
            boolean r10 = r10.contains(r8)
            if (r10 == 0) goto L37
            r10 = r7
            goto La6
        L37:
            java.lang.String r10 = r11.capabilities
            java.lang.String r8 = "SAE"
            boolean r10 = r10.contains(r8)
            if (r10 == 0) goto L44
            r10 = r6
            goto La6
        L44:
            java.lang.String r10 = r11.capabilities
            java.lang.String r8 = "PSK"
            boolean r10 = r10.contains(r8)
            if (r10 == 0) goto L50
            r10 = r5
            goto La6
        L50:
            java.lang.String r10 = r11.capabilities
            java.lang.String r8 = "EAP_SUITE_B_192"
            boolean r10 = r10.contains(r8)
            if (r10 == 0) goto L5c
            r10 = r4
            goto La6
        L5c:
            java.lang.String r10 = r11.capabilities
            java.lang.String r8 = "EAP"
            boolean r10 = r10.contains(r8)
            if (r10 == 0) goto L68
            r10 = r3
            goto La6
        L68:
            java.lang.String r10 = r11.capabilities
            java.lang.String r11 = "OWE"
            boolean r10 = r10.contains(r11)
            if (r10 == 0) goto L74
            r10 = r2
            goto La6
        L74:
            r10 = r1
            goto La6
        L76:
            android.net.wifi.WifiConfiguration r11 = r10.getWifiConfiguration()
            if (r11 != 0) goto L92
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>(r8)
            java.lang.String r9 = r10.getSsid()
            r11.append(r9)
            r11.append(r8)
            java.lang.String r11 = r11.toString()
            r0.SSID = r11
            goto La2
        L92:
            android.net.wifi.WifiConfiguration r11 = r10.getWifiConfiguration()
            int r11 = r11.networkId
            r0.networkId = r11
            android.net.wifi.WifiConfiguration r11 = r10.getWifiConfiguration()
            boolean r11 = r11.hiddenSSID
            r0.hiddenSSID = r11
        La2:
            int r10 = r10.getSecurity$1()
        La6:
            switch(r10) {
                case 0: goto Lc2;
                case 1: goto Lbe;
                case 2: goto Lba;
                case 3: goto Lb6;
                case 4: goto Lb2;
                case 5: goto Lae;
                case 6: goto Laa;
                default: goto La9;
            }
        La9:
            goto Lc5
        Laa:
            r0.setSecurityParams(r6)
            goto Lc5
        Lae:
            r0.setSecurityParams(r2)
            goto Lc5
        Lb2:
            r0.setSecurityParams(r4)
            goto Lc5
        Lb6:
            r0.setSecurityParams(r3)
            goto Lc5
        Lba:
            r0.setSecurityParams(r5)
            goto Lc5
        Lbe:
            r0.setSecurityParams(r7)
            goto Lc5
        Lc2:
            r0.setSecurityParams(r1)
        Lc5:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.wifi.WifiUtils.getWifiConfig(com.android.wifitrackerlib.WifiEntry,"
                    + " android.net.wifi.ScanResult):android.net.wifi.WifiConfiguration");
    }

    public static boolean isBlockedByEnterprise(Context context, String str) {
        SubscriptionManager subscriptionManager = SemWifiUtils.mSubscriptionManager;
        if (TextUtils.isEmpty(str)) {
            str = ApnSettings.MVNO_NONE;
        } else if (str.length() < 2 || !str.startsWith("\"") || !str.endsWith("\"")) {
            str = ComposerKt$$ExternalSyntheticOutline0.m("\"", str, "\"");
        }
        if (WifiDevicePolicyManager.getCursorValuePositive(
                context,
                "content://com.sec.knox.provider2/WifiPolicy",
                "isEnterpriseNetwork",
                new String[] {str},
                "true")) {
            Log.i("WifiDevicePolicyManager", "isEnterpriseKnoxSsid true");
            if (WifiDevicePolicyManager.getCursorValuePositive(
                    context,
                    "content://com.sec.knox.provider2/WifiPolicy",
                    "getAllowUserPolicyChanges",
                    null,
                    "false")) {
                Log.i("WifiDevicePolicyManager", "isAllowedToChangeUserPolicy false");
                return true;
            }
        }
        return false;
    }

    public static boolean isHotspotPasswordValid(int i, String str) {
        SoftApConfiguration.Builder builder = new SoftApConfiguration.Builder();
        if (i == 1 || i == 2) {
            try {
                if (str.length() >= 8) {
                    if (str.length() > 63) {}
                }
            } catch (Exception unused) {
            }
            return false;
        }
        builder.setPassphrase(str, i);
        return true;
    }

    public static boolean isNetworkLockedDown(
            Context context, WifiConfiguration wifiConfiguration) {
        int managedProfileId;
        ComponentName profileOwnerAsUser;
        if (context == null || wifiConfiguration == null) {
            return false;
        }
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) context.getSystemService("device_policy");
        PackageManager packageManager = context.getPackageManager();
        UserManager userManager = (UserManager) context.getSystemService("user");
        if (packageManager.hasSystemFeature("android.software.device_admin")
                && devicePolicyManager == null) {
            return true;
        }
        if (devicePolicyManager == null) {
            return false;
        }
        ComponentName deviceOwnerComponentOnAnyUser =
                devicePolicyManager.getDeviceOwnerComponentOnAnyUser();
        try {
            if (deviceOwnerComponentOnAnyUser != null) {
                if (packageManager.getPackageUidAsUser(
                                deviceOwnerComponentOnAnyUser.getPackageName(),
                                devicePolicyManager.getDeviceOwnerUserId())
                        != wifiConfiguration.creatorUid) {
                    return false;
                }
            } else if (!devicePolicyManager.isOrganizationOwnedDeviceWithManagedProfile()
                    || (profileOwnerAsUser =
                                    devicePolicyManager.getProfileOwnerAsUser(
                                            (managedProfileId =
                                                    Utils.getManagedProfileId(
                                                            userManager, UserHandle.myUserId()))))
                            == null
                    || packageManager.getPackageUidAsUser(
                                    profileOwnerAsUser.getPackageName(), managedProfileId)
                            != wifiConfiguration.creatorUid) {
                return false;
            }
            return Settings.Global.getInt(
                            context.getContentResolver(), "wifi_device_owner_configs_lockdown", 0)
                    != 0;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public static boolean isQuickShareEnabled(Context context) {
        try {
            PackageInfo packageInfo =
                    context.getPackageManager()
                            .getPackageInfo("com.samsung.android.app.sharelive", 1);
            Log.d("WifiUtils", "ShareLive enabled? " + packageInfo.applicationInfo.enabled);
            return packageInfo.applicationInfo.enabled;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.i("WifiUtils", "PackageManager could not find QuickShare to get activity");
            return false;
        }
    }

    public static boolean isSSIDTooLong(String str) {
        return !TextUtils.isEmpty(str) && str.getBytes(StandardCharsets.UTF_8).length > 32;
    }

    public static boolean isSecurityTypeSupportQrCode(Context context, WifiEntry wifiEntry) {
        if (wifiEntry.semIsEphemeral()) {
            return false;
        }
        return (wifiEntry.getSecurity$1() == 1
                        || wifiEntry.getSecurity$1() == 2
                        || wifiEntry.getSecurity$1() == 5
                        || wifiEntry.getSecurity$1() == 4
                        || wifiEntry.getSecurity$1() == 0)
                && !KnoxUtils.isApplicationRestricted(context, "wifi_qrcode", "hide");
    }

    public static boolean isSupportQuickShare(Context context) {
        try {
            JSONObject jSONObject =
                    new JSONObject(
                            context.getPackageManager()
                                    .getApplicationInfo("com.samsung.android.app.sharelive", 128)
                                    .metaData
                                    .getString("com.samsung.android.app.sharelive.extend"));
            if (jSONObject.has("wifiQR")) {
                return jSONObject.getBoolean("wifiQR");
            }
        } catch (PackageManager.NameNotFoundException | JSONException e) {
            Log.d("WifiUtils", e.toString());
        }
        Log.d("WifiUtils", "Not support QUICK_SHARE_WIFI_QR");
        return false;
    }

    public static boolean isSupportVerifyingForPassword(Context context) {
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl != null) {
            return featureFactoryImpl
                            .getSecurityFeatureProvider()
                            .getLockPatternUtils(context)
                            .isSecure(UserHandle.myUserId())
                    && Build.VERSION.SEM_PLATFORM_INT > 140000;
        }
        throw new UnsupportedOperationException("No feature factory configured");
    }

    public static boolean isValidPsk(String str, boolean z) {
        if (z && str.length() == 0) {
            return true;
        }
        if (str.length() == 64 && str.matches("[0-9A-Fa-f]{64}")) {
            return true;
        }
        boolean z2 = false;
        if (str.length() < 8 || str.length() >= 64) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) < ' ' || str.charAt(i) == 127) {
                Log.e("WifiUtils", "Passphrase has invalid character - " + ((int) str.charAt(i)));
                z2 = true;
                break;
            }
        }
        return !z2;
    }

    public static boolean isValidSae(String str, boolean z) {
        if (z && str.length() == 0) {
            return true;
        }
        if (str.length() == 64 && str.matches("[0-9A-Fa-f]{64}")) {
            return true;
        }
        return str.length() >= 1 && str.length() < 64;
    }

    public static boolean isValidWep(String str, boolean z) {
        if (z && str.length() == 0) {
            return true;
        }
        return (str.length() == 10 && str.matches("[0-9A-Fa-f]{10}"))
                || (str.length() == 26 && str.matches("[0-9A-Fa-f]{26}"))
                || str.length() == 5
                || str.length() == 13;
    }

    public static void setCanShowWifiHotspotCached(Boolean bool) {
        sCanShowWifiHotspotCached = bool;
    }
}
