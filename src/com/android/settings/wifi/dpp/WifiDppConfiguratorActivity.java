package com.android.settings.wifi.dpp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserManager;
import android.util.EventLog;
import android.util.Log;

import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.FragmentManagerImpl;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class WifiDppConfiguratorActivity extends WifiDppBaseActivity
        implements WifiNetworkConfig.Retriever,
                WifiDppQrCodeScannerFragment.OnScanWifiDppSuccessListener,
                WifiDppAddDeviceFragment.OnClickChooseDifferentNetworkListener,
                WifiNetworkListFragment.OnChooseNetworkListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public WifiQrCode mWifiDppQrCode;
    public WifiNetworkConfig mWifiNetworkConfig;

    public static boolean isAddWifiConfigAllowed(Context context) {
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        if (userManager == null || !userManager.hasUserRestriction("no_add_wifi_config")) {
            return true;
        }
        Log.e("WifiDppConfiguratorActivity", "The user is not allowed to add Wi-Fi configuration.");
        return false;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1595;
    }

    @Override // com.android.settings.wifi.dpp.WifiDppBaseActivity
    public final void handleIntent(final Intent intent) {
        UserManager userManager;
        if (!isAddWifiConfigAllowed(getApplicationContext())) {
            finish();
            return;
        }
        Context applicationContext = getApplicationContext();
        if ((applicationContext == null
                        || (userManager =
                                        (UserManager)
                                                applicationContext.getSystemService(
                                                        UserManager.class))
                                == null)
                ? false
                : userManager.isGuestUser()) {
            Log.e("WifiDppConfiguratorActivity", "Guest user is not allowed to configure Wi-Fi!");
            EventLog.writeEvent(1397638484, "224772890", -1, "User is a guest");
            finish();
            return;
        }
        String action = intent != null ? intent.getAction() : null;
        if (action == null) {
            finish();
            return;
        }
        switch (action) {
            case "android.settings.PROCESS_WIFI_EASY_CONNECT_URI":
                WifiDppUtils.showLockScreen(
                        this,
                        new Runnable() { // from class:
                            // com.android.settings.wifi.dpp.WifiDppConfiguratorActivity$$ExternalSyntheticLambda0
                            /* JADX WARN: Code restructure failed: missing block: B:7:0x002b, code lost:

                               if ("DPP".equals(java.lang.Integer.valueOf(r3.mUriParserResults.getUriScheme())) != false) goto L11;
                            */
                            @Override // java.lang.Runnable
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public final void run() {
                                /*
                                    r11 = this;
                                    com.android.settings.wifi.dpp.WifiDppConfiguratorActivity r0 = com.android.settings.wifi.dpp.WifiDppConfiguratorActivity.this
                                    android.content.Intent r11 = r2
                                    int r1 = com.android.settings.wifi.dpp.WifiDppConfiguratorActivity.$r8$clinit
                                    r0.getClass()
                                    android.net.Uri r1 = r11.getData()
                                    r2 = 0
                                    if (r1 != 0) goto L12
                                    r1 = r2
                                    goto L16
                                L12:
                                    java.lang.String r1 = r1.toString()
                                L16:
                                    com.android.settings.wifi.dpp.WifiQrCode r3 = new com.android.settings.wifi.dpp.WifiQrCode     // Catch: java.lang.IllegalArgumentException -> L2e
                                    r3.<init>(r1)     // Catch: java.lang.IllegalArgumentException -> L2e
                                    android.net.wifi.UriParserResults r1 = r3.mUriParserResults
                                    int r1 = r1.getUriScheme()
                                    java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
                                    java.lang.String r4 = "DPP"
                                    boolean r1 = r4.equals(r1)
                                    if (r1 == 0) goto L2e
                                    goto L2f
                                L2e:
                                    r3 = r2
                                L2f:
                                    r0.mWifiDppQrCode = r3
                                    java.lang.String r1 = "android.provider.extra.EASY_CONNECT_BAND_LIST"
                                    r11.getIntArrayExtra(r1)
                                    java.time.Duration r11 = com.android.settings.wifi.dpp.WifiDppUtils.VIBRATE_DURATION_QR_CODE_RECOGNITION
                                    java.lang.Class<android.net.wifi.WifiManager> r11 = android.net.wifi.WifiManager.class
                                    java.lang.Object r11 = r0.getSystemService(r11)
                                    android.net.wifi.WifiManager r11 = (android.net.wifi.WifiManager) r11
                                    boolean r11 = r11.isEasyConnectSupported()
                                    java.lang.String r1 = "WifiDppConfiguratorActivity"
                                    if (r11 != 0) goto L4d
                                    java.lang.String r3 = "ACTION_PROCESS_WIFI_EASY_CONNECT_URI for a device that doesn't support Wifi DPP - use WifiManager#isEasyConnectSupported"
                                    android.util.Log.e(r1, r3)
                                L4d:
                                    com.android.settings.wifi.dpp.WifiQrCode r3 = r0.mWifiDppQrCode
                                    if (r3 != 0) goto L56
                                    java.lang.String r3 = "ACTION_PROCESS_WIFI_EASY_CONNECT_URI with null URI!"
                                    android.util.Log.e(r1, r3)
                                L56:
                                    com.android.settings.wifi.dpp.WifiQrCode r1 = r0.mWifiDppQrCode
                                    if (r1 == 0) goto Lea
                                    if (r11 != 0) goto L5e
                                    goto Lea
                                L5e:
                                    java.lang.Class<android.net.wifi.WifiManager> r11 = android.net.wifi.WifiManager.class
                                    java.lang.Object r11 = r0.getSystemService(r11)
                                    android.net.wifi.WifiManager r11 = (android.net.wifi.WifiManager) r11
                                    boolean r1 = r11.isWifiEnabled()
                                    if (r1 != 0) goto L6d
                                    goto La3
                                L6d:
                                    android.net.wifi.WifiInfo r1 = r11.getConnectionInfo()
                                    if (r1 != 0) goto L74
                                    goto La3
                                L74:
                                    int r1 = r1.getNetworkId()
                                    java.util.List r11 = r11.getConfiguredNetworks()
                                    java.util.Iterator r11 = r11.iterator()
                                L80:
                                    boolean r3 = r11.hasNext()
                                    if (r3 == 0) goto La3
                                    java.lang.Object r3 = r11.next()
                                    android.net.wifi.WifiConfiguration r3 = (android.net.wifi.WifiConfiguration) r3
                                    int r4 = r3.networkId
                                    if (r4 != r1) goto L80
                                    java.lang.String r5 = com.android.settings.wifi.dpp.WifiDppUtils.getSecurityString(r3)
                                    java.lang.String r6 = r3.getPrintableSsid()
                                    java.lang.String r7 = r3.preSharedKey
                                    boolean r8 = r3.hiddenSSID
                                    int r9 = r3.networkId
                                    r10 = 0
                                    com.android.settings.wifi.dpp.WifiNetworkConfig r2 = com.android.settings.wifi.dpp.WifiNetworkConfig.getValidConfigOrNull(r5, r6, r7, r8, r9, r10)
                                La3:
                                    r11 = 0
                                    if (r2 == 0) goto Le6
                                    java.lang.Class<android.net.wifi.WifiManager> r1 = android.net.wifi.WifiManager.class
                                    java.lang.Object r1 = r0.getSystemService(r1)
                                    android.net.wifi.WifiManager r1 = (android.net.wifi.WifiManager) r1
                                    boolean r1 = r1.isEasyConnectSupported()
                                    if (r1 != 0) goto Lb5
                                    goto Le6
                                Lb5:
                                    java.lang.String r1 = r2.mSecurity
                                    boolean r3 = android.text.TextUtils.isEmpty(r1)
                                    if (r3 == 0) goto Lbe
                                    goto Le6
                                Lbe:
                                    java.lang.Class<android.net.wifi.WifiManager> r3 = android.net.wifi.WifiManager.class
                                    java.lang.Object r3 = r0.getSystemService(r3)
                                    android.net.wifi.WifiManager r3 = (android.net.wifi.WifiManager) r3
                                    r1.getClass()
                                    java.lang.String r4 = "SAE"
                                    boolean r4 = r1.equals(r4)
                                    if (r4 != 0) goto Lda
                                    java.lang.String r3 = "WPA"
                                    boolean r1 = r1.equals(r3)
                                    if (r1 != 0) goto Le0
                                    goto Le6
                                Lda:
                                    boolean r1 = r3.isWpa3SaeSupported()
                                    if (r1 == 0) goto Le6
                                Le0:
                                    r0.mWifiNetworkConfig = r2
                                    r0.showAddDeviceFragment(r11)
                                    goto Led
                                Le6:
                                    r0.showChooseSavedWifiNetworkFragment(r11)
                                    goto Led
                                Lea:
                                    r0.finish()
                                Led:
                                    return
                                */
                                throw new UnsupportedOperationException(
                                        "Method not decompiled:"
                                            + " com.android.settings.wifi.dpp.WifiDppConfiguratorActivity$$ExternalSyntheticLambda0.run():void");
                            }
                        });
                return;
            case "android.settings.WIFI_DPP_CONFIGURATOR_QR_CODE_GENERATOR":
                WifiNetworkConfig validConfigOrNull =
                        WifiNetworkConfig.getValidConfigOrNull(intent);
                if (validConfigOrNull != null) {
                    this.mWifiNetworkConfig = validConfigOrNull;
                    WifiDppQrCodeGeneratorFragment wifiDppQrCodeGeneratorFragment =
                            (WifiDppQrCodeGeneratorFragment)
                                    this.mFragmentManager.findFragmentByTag(
                                            "qr_code_generator_fragment");
                    if (wifiDppQrCodeGeneratorFragment != null) {
                        if (wifiDppQrCodeGeneratorFragment.isVisible()) {
                            return;
                        }
                        this.mFragmentManager.popBackStackImmediate();
                        return;
                    } else {
                        WifiDppQrCodeGeneratorFragment wifiDppQrCodeGeneratorFragment2 =
                                new WifiDppQrCodeGeneratorFragment();
                        FragmentManagerImpl fragmentManagerImpl = this.mFragmentManager;
                        fragmentManagerImpl.getClass();
                        BackStackRecord backStackRecord = new BackStackRecord(fragmentManagerImpl);
                        backStackRecord.replace(
                                R.id.fragment_container,
                                wifiDppQrCodeGeneratorFragment2,
                                "qr_code_generator_fragment");
                        backStackRecord.commitInternal(false);
                        return;
                    }
                }
                break;
            case "android.settings.WIFI_DPP_CONFIGURATOR_QR_CODE_SCANNER":
                WifiNetworkConfig validConfigOrNull2 =
                        WifiNetworkConfig.getValidConfigOrNull(intent);
                if (validConfigOrNull2 != null) {
                    this.mWifiNetworkConfig = validConfigOrNull2;
                    showQrCodeScannerFragment();
                    return;
                }
                break;
            default:
                Log.e("WifiDppConfiguratorActivity", "Launch with an invalid action");
                break;
        }
        finish();
    }

    @Override // com.android.settings.wifi.dpp.WifiDppBaseActivity,
              // com.android.settings.core.InstrumentedActivity,
              // com.android.settingslib.core.lifecycle.ObservableActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!isAddWifiConfigAllowed(getApplicationContext())) {
            finish();
            return;
        }
        if (bundle != null) {
            WifiQrCode wifiQrCode = null;
            try {
                WifiQrCode wifiQrCode2 = new WifiQrCode(bundle.getString("key_qr_code"));
                if ("DPP".equals(Integer.valueOf(wifiQrCode2.mUriParserResults.getUriScheme()))) {
                    wifiQrCode = wifiQrCode2;
                }
            } catch (IllegalArgumentException unused) {
            }
            this.mWifiDppQrCode = wifiQrCode;
            this.mWifiNetworkConfig =
                    WifiNetworkConfig.getValidConfigOrNull(
                            bundle.getString("key_wifi_security"),
                            bundle.getString("key_wifi_ssid"),
                            bundle.getString("key_wifi_preshared_key"),
                            bundle.getBoolean("key_wifi_hidden_ssid"),
                            bundle.getInt("key_wifi_network_id"),
                            bundle.getBoolean("key_is_hotspot"));
        }
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity,
              // android.app.Activity
    public final void onSaveInstanceState(Bundle bundle) {
        WifiQrCode wifiQrCode = this.mWifiDppQrCode;
        if (wifiQrCode != null) {
            bundle.putString("key_qr_code", wifiQrCode.mQrCode);
        }
        WifiNetworkConfig wifiNetworkConfig = this.mWifiNetworkConfig;
        if (wifiNetworkConfig != null) {
            bundle.putString("key_wifi_security", wifiNetworkConfig.mSecurity);
            bundle.putString("key_wifi_ssid", this.mWifiNetworkConfig.mSsid);
            bundle.putString("key_wifi_preshared_key", this.mWifiNetworkConfig.mPreSharedKey);
            bundle.putBoolean("key_wifi_hidden_ssid", this.mWifiNetworkConfig.mHiddenSsid);
            bundle.putInt("key_wifi_network_id", this.mWifiNetworkConfig.mNetworkId);
            bundle.putBoolean("key_is_hotspot", this.mWifiNetworkConfig.mIsHotspot);
        }
        super.onSaveInstanceState(bundle);
    }

    @Override // com.android.settings.wifi.dpp.WifiDppQrCodeScannerFragment.OnScanWifiDppSuccessListener
    public final void onScanWifiDppSuccess(WifiQrCode wifiQrCode) {
        this.mWifiDppQrCode = wifiQrCode;
        showAddDeviceFragment(true);
    }

    public boolean setWifiDppQrCode(WifiQrCode wifiQrCode) {
        if (wifiQrCode == null
                || !"DPP".equals(Integer.valueOf(wifiQrCode.mUriParserResults.getUriScheme()))) {
            return false;
        }
        this.mWifiDppQrCode = new WifiQrCode(wifiQrCode.mQrCode);
        return true;
    }

    public boolean setWifiNetworkConfig(WifiNetworkConfig wifiNetworkConfig) {
        if (!WifiNetworkConfig.isValidConfig(wifiNetworkConfig)) {
            return false;
        }
        this.mWifiNetworkConfig = new WifiNetworkConfig(wifiNetworkConfig);
        return true;
    }

    public final void showAddDeviceFragment(boolean z) {
        WifiDppAddDeviceFragment wifiDppAddDeviceFragment =
                (WifiDppAddDeviceFragment)
                        this.mFragmentManager.findFragmentByTag("add_device_fragment");
        if (wifiDppAddDeviceFragment != null) {
            if (wifiDppAddDeviceFragment.isVisible()) {
                return;
            }
            this.mFragmentManager.popBackStackImmediate();
            return;
        }
        WifiDppAddDeviceFragment wifiDppAddDeviceFragment2 = new WifiDppAddDeviceFragment();
        FragmentManagerImpl fragmentManagerImpl = this.mFragmentManager;
        fragmentManagerImpl.getClass();
        BackStackRecord backStackRecord = new BackStackRecord(fragmentManagerImpl);
        backStackRecord.replace(
                R.id.fragment_container, wifiDppAddDeviceFragment2, "add_device_fragment");
        if (z) {
            backStackRecord.addToBackStack(null);
        }
        backStackRecord.commitInternal(false);
    }

    public final void showChooseSavedWifiNetworkFragment(boolean z) {
        WifiDppChooseSavedWifiNetworkFragment wifiDppChooseSavedWifiNetworkFragment =
                (WifiDppChooseSavedWifiNetworkFragment)
                        this.mFragmentManager.findFragmentByTag(
                                "choose_saved_wifi_network_fragment");
        if (wifiDppChooseSavedWifiNetworkFragment != null) {
            if (wifiDppChooseSavedWifiNetworkFragment.isVisible()) {
                return;
            }
            this.mFragmentManager.popBackStackImmediate();
            return;
        }
        WifiDppChooseSavedWifiNetworkFragment wifiDppChooseSavedWifiNetworkFragment2 =
                new WifiDppChooseSavedWifiNetworkFragment();
        FragmentManagerImpl fragmentManagerImpl = this.mFragmentManager;
        fragmentManagerImpl.getClass();
        BackStackRecord backStackRecord = new BackStackRecord(fragmentManagerImpl);
        backStackRecord.replace(
                R.id.fragment_container,
                wifiDppChooseSavedWifiNetworkFragment2,
                "choose_saved_wifi_network_fragment");
        if (z) {
            backStackRecord.addToBackStack(null);
        }
        backStackRecord.commitInternal(false);
    }

    public void showQrCodeScannerFragment() {
        WifiDppQrCodeScannerFragment wifiDppQrCodeScannerFragment =
                (WifiDppQrCodeScannerFragment)
                        this.mFragmentManager.findFragmentByTag("qr_code_scanner_fragment");
        if (wifiDppQrCodeScannerFragment != null) {
            if (wifiDppQrCodeScannerFragment.isVisible()) {
                return;
            }
            this.mFragmentManager.popBackStackImmediate();
        } else {
            WifiDppQrCodeScannerFragment wifiDppQrCodeScannerFragment2 =
                    new WifiDppQrCodeScannerFragment();
            FragmentManagerImpl fragmentManagerImpl = this.mFragmentManager;
            fragmentManagerImpl.getClass();
            BackStackRecord backStackRecord = new BackStackRecord(fragmentManagerImpl);
            backStackRecord.replace(
                    R.id.fragment_container,
                    wifiDppQrCodeScannerFragment2,
                    "qr_code_scanner_fragment");
            backStackRecord.commitInternal(false);
        }
    }
}
