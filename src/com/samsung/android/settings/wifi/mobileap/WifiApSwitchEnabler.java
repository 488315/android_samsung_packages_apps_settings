package com.samsung.android.settings.wifi.mobileap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.hardware.display.DisplayManager;
import android.hardware.display.SemWifiDisplayStatus;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.net.wifi.SoftApConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemProperties;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.Preference;
import androidx.preference.SecSwitchPreferenceScreen;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.datausage.DataSaverBackend;
import com.android.settings.fuelgauge.datasaver.DynamicDenylistManager$$ExternalSyntheticOutline0;
import com.android.settings.widget.SettingsMainSwitchBar;

import com.samsung.android.settings.WifiApUtils;
import com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastSourceInfoController$$ExternalSyntheticOutline0;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.wifi.mobileap.clients.WifiApMobileDataSharedTodayPreferenceController;
import com.samsung.android.settings.wifi.mobileap.datamodels.WifiApConfiguration;
import com.samsung.android.settings.wifi.mobileap.datamodels.WifiApDataUsageConfig;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApConnectedDeviceUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFrameworkUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSoftApUtils;
import com.samsung.android.wifi.SemWifiApCust;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifi.p2p.SemWifiP2pManager;
import com.sec.ims.extensions.WiFiManagerExt;
import com.sec.ims.settings.ImsProfile;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiApSwitchEnabler
        implements CompoundButton.OnCheckedChangeListener,
                LifecycleObserver,
                Preference.OnPreferenceChangeListener {
    public static final boolean DBG = Utils.MHSDBG;
    public static final String TAG = "WifiApSwitchEnabler";
    public static final IntentFilter WIFIAP_SWITCH_INTENT_FILTER;
    public boolean isBroadcastReceiverRegistered;
    public final WifiManager mAOSPWifiManager;
    public final Activity mActivity;
    public AlertDialog.Builder mAlertDialogBuilder;
    public final Context mContext;
    public Button mDataLimitChangeLimitButton;
    public AlertDialog mDataLimitDialog;
    public TextView mDataLimitEditErrorTextView;
    public EditText mDataLimitEditText;
    public final DataSaverBackend mDataSaverBackend;
    public String[] mProvisionApp;
    public final SemWifiManager mSemWifiManager;
    public final SettingsMainSwitchBar mSwitchBar;
    public final SecSwitchPreferenceScreen mSwitchPref;
    public final WifiApConfiguration mWifiApConfiguration;
    public WifiApDataSaver mWifiApDataSaver;
    public boolean waitingForConfigurationSet;
    public boolean waitingForWifiDisable = false;
    public final AnonymousClass1 mReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.samsung.android.settings.wifi.mobileap.WifiApSwitchEnabler.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if (!WifiApMobileDataSharedTodayPreferenceController
                            .ACTION_WIFI_AP_STATE_CHANGED
                            .equals(action)) {
                        if (action.equals("android.intent.action.AIRPLANE_MODE")) {
                            WifiApSwitchEnabler.this.updateMHSSwitch();
                            return;
                        }
                        if (action.equals("android.net.wifi.WIFI_STATE_CHANGED")) {
                            int intExtra = intent.getIntExtra("wifi_state", 4);
                            Log.i(
                                    WifiApSwitchEnabler.TAG,
                                    "waitingForWifiDisable : "
                                            + WifiApSwitchEnabler.this.waitingForWifiDisable
                                            + " wifistate : "
                                            + intExtra);
                            WifiApSwitchEnabler wifiApSwitchEnabler = WifiApSwitchEnabler.this;
                            if (wifiApSwitchEnabler.waitingForWifiDisable && intExtra == 1) {
                                wifiApSwitchEnabler.startProvisioningIfNecessary();
                                WifiApSwitchEnabler.this.waitingForWifiDisable = false;
                            }
                            return;
                        }
                        return;
                    }
                    int intExtra2 = intent.getIntExtra("wifi_state", 14);
                    WifiApSwitchEnabler wifiApSwitchEnabler2 = WifiApSwitchEnabler.this;
                    wifiApSwitchEnabler2.getClass();
                    ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                            intExtra2, "WifiAp state:", WifiApSwitchEnabler.TAG);
                    switch (intExtra2) {
                        case 10:
                            wifiApSwitchEnabler2.updateMHSSwitch();
                            wifiApSwitchEnabler2.showProgress(true);
                            break;
                        case 11:
                            wifiApSwitchEnabler2.showProgress(false);
                            wifiApSwitchEnabler2.updateMHSSwitch();
                            break;
                        case 12:
                            wifiApSwitchEnabler2.showProgress(true);
                            wifiApSwitchEnabler2.updateMHSSwitch();
                            break;
                        case 13:
                            wifiApSwitchEnabler2.updateMHSSwitch();
                            wifiApSwitchEnabler2.showProgress(false);
                            break;
                        default:
                            wifiApSwitchEnabler2.showProgress(false);
                            wifiApSwitchEnabler2.updateMHSSwitch();
                            break;
                    }
                }
            };
    public final AnonymousClass43 mDataLimitEditTextWatcher =
            new TextWatcher() { // from class:
                                // com.samsung.android.settings.wifi.mobileap.WifiApSwitchEnabler.43
                @Override // android.text.TextWatcher
                public final void afterTextChanged(Editable editable) {
                    WifiApSwitchEnabler.this.buttonValidate();
                }

                @Override // android.text.TextWatcher
                public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    String charSequence2 = charSequence.toString();
                    WifiApSwitchEnabler wifiApSwitchEnabler = WifiApSwitchEnabler.this;
                    if (wifiApSwitchEnabler.mDataLimitEditErrorTextView == null
                            || wifiApSwitchEnabler.mDataLimitEditText == null) {
                        return;
                    }
                    if (charSequence2.length() >= 10) {
                        WifiApSwitchEnabler.this.mDataLimitEditErrorTextView.setVisibility(0);
                        WifiApSwitchEnabler wifiApSwitchEnabler2 = WifiApSwitchEnabler.this;
                        SecBluetoothLeBroadcastSourceInfoController$$ExternalSyntheticOutline0.m(
                                wifiApSwitchEnabler2.mContext,
                                R.color.sec_wifi_dialog_error_color,
                                wifiApSwitchEnabler2.mDataLimitEditText);
                        return;
                    }
                    WifiApSwitchEnabler.this.mDataLimitEditErrorTextView.setVisibility(8);
                    WifiApSwitchEnabler wifiApSwitchEnabler3 = WifiApSwitchEnabler.this;
                    SecBluetoothLeBroadcastSourceInfoController$$ExternalSyntheticOutline0.m(
                            wifiApSwitchEnabler3.mContext,
                            R.color.sec_wifi_ap_edit_text_background_color,
                            wifiApSwitchEnabler3.mDataLimitEditText);
                }

                @Override // android.text.TextWatcher
                public final void beforeTextChanged(
                        CharSequence charSequence, int i, int i2, int i3) {}
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.WifiApSwitchEnabler$2, reason: invalid class name */
    public final class AnonymousClass2 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object this$0;

        public /* synthetic */ AnonymousClass2(int i, Object obj) {
            this.$r8$classId = i;
            this.this$0 = obj;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    ((WifiApSwitchEnabler) this.this$0).startProvisioningIfNecessary();
                    break;
                case 1:
                    WifiApSwitchEnabler wifiApSwitchEnabler =
                            ((AnonymousClass3) this.this$0).this$0;
                    boolean z = WifiApSwitchEnabler.DBG;
                    wifiApSwitchEnabler.startProvisioningIfNecessary();
                    break;
                case 2:
                    WifiApSwitchEnabler wifiApSwitchEnabler2 =
                            ((AnonymousClass3) this.this$0).this$0;
                    boolean z2 = WifiApSwitchEnabler.DBG;
                    wifiApSwitchEnabler2.startProvisioningIfNecessary();
                    break;
                default:
                    WifiApSwitchEnabler wifiApSwitchEnabler3 =
                            ((AnonymousClass3) this.this$0).this$0;
                    boolean z3 = WifiApSwitchEnabler.DBG;
                    wifiApSwitchEnabler3.startProvisioningIfNecessary();
                    break;
            }
        }

        public AnonymousClass2(AnonymousClass3 anonymousClass3) {
            this.$r8$classId = 2;
            this.this$0 = anonymousClass3;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.WifiApSwitchEnabler$3, reason: invalid class name */
    public final class AnonymousClass3 implements DialogInterface.OnClickListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ WifiApSwitchEnabler this$0;

        public /* synthetic */ AnonymousClass3(WifiApSwitchEnabler wifiApSwitchEnabler, int i) {
            this.$r8$classId = i;
            this.this$0 = wifiApSwitchEnabler;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            switch (this.$r8$classId) {
                case 0:
                    SALogging.insertSALog("TETH_010", "8035");
                    this.this$0.updateMHSSwitch();
                    break;
                case 1:
                    this.this$0.checkFirstTimeWiFiSharingDialogAndProceedFurthur();
                    break;
                case 2:
                    this.this$0.updateMHSSwitch();
                    break;
                case 3:
                    this.this$0.checkFirstTimeConfigureAndProceedFurthur();
                    break;
                case 4:
                    SALogging.insertSALog("TETH_010", "8055");
                    this.this$0.updateMHSSwitch();
                    break;
                case 5:
                    SALogging.insertSALog("TETH_010", "8054");
                    this.this$0.getClass();
                    Log.i(WifiApSwitchEnabler.TAG, "isJdmModel is false");
                    this.this$0.startProvisioningIfNecessary();
                    break;
                case 6:
                    SALogging.insertSALog("TETH_010", "8049");
                    this.this$0.updateMHSSwitch();
                    break;
                case 7:
                    SALogging.insertSALog("TETH_010", "8048");
                    ((DisplayManager) this.this$0.mContext.getSystemService("display"))
                            .semDisconnectWifiDisplay();
                    new Handler().postDelayed(new AnonymousClass2(1, this), 600L);
                    break;
                case 8:
                    SALogging.insertSALog("TETH_010", "8059");
                    this.this$0.updateMHSSwitch();
                    break;
                case 9:
                    SALogging.insertSALog("TETH_010", "8058");
                    this.this$0.mAOSPWifiManager.setWifiEnabled(false);
                    Settings.Secure.putInt(
                            this.this$0.mContext.getContentResolver(), "wifi_saved_state", 1);
                    new Handler().postDelayed(new AnonymousClass2(this), 600L);
                    break;
                case 10:
                    SALogging.insertSALog("TETH_010", "8047");
                    this.this$0.updateMHSSwitch();
                    break;
                case 11:
                    SALogging.insertSALog("TETH_010", "8046");
                    this.this$0.startProvisioningIfNecessary();
                    break;
                case 12:
                    SALogging.insertSALog("TETH_010", "8056");
                    int wifiState = this.this$0.mAOSPWifiManager.getWifiState();
                    if (wifiState != 2
                            && wifiState != 3
                            && !this.this$0.mAOSPWifiManager.isScanAlwaysAvailable()) {
                        this.this$0.startProvisioningIfNecessary();
                        break;
                    } else {
                        if (wifiState == 2 || wifiState == 3) {
                            Settings.Secure.putInt(
                                    this.this$0.mContext.getContentResolver(),
                                    "wifi_saved_state",
                                    1);
                        }
                        this.this$0.mAOSPWifiManager.setWifiEnabled(false);
                        this.this$0.waitingForWifiDisable = true;
                        break;
                    }
                case 13:
                    SALogging.insertSALog("TETH_010", "8057");
                    this.this$0.updateMHSSwitch();
                    break;
                case 14:
                    SALogging.insertSALog("TETH_010", "8050");
                    this.this$0.updateMHSSwitch();
                    break;
                case 15:
                    SALogging.insertSALog("TETH_010", "8051");
                    this.this$0.startProvisioningIfNecessary();
                    break;
                case 16:
                    SALogging.insertSALog("TETH_010", "8066");
                    this.this$0.mAOSPWifiManager.disconnect();
                    new Handler().postDelayed(new AnonymousClass2(3, this), 600L);
                    break;
                case 17:
                    SALogging.insertSALog("TETH_010", "8065");
                    this.this$0.updateMHSSwitch();
                    break;
                case 18:
                    SALogging.insertSALog("TETH_010", "8064");
                    this.this$0.startProvisioningIfNecessary();
                    break;
                case 19:
                    WifiApDataUsageConfig inputDataInDataUsageConfig =
                            this.this$0.getInputDataInDataUsageConfig();
                    if (inputDataInDataUsageConfig.getUsageValueInMB()
                            < WifiApConnectedDeviceUtils.getWifiApTodayTotalDataUsage(
                                            this.this$0.mContext)
                                    .getUsageValueInMB()) {
                        Log.i(
                                WifiApSwitchEnabler.TAG,
                                "Error Settings Global data limit : "
                                        + inputDataInDataUsageConfig.getUsageValueInMB());
                        Toast.makeText(
                                        this.this$0.mContext,
                                        "You cannot set less than the amount of data you used."
                                            + " Please enter again.",
                                        0)
                                .show();
                        this.this$0.updateMHSSwitch();
                    } else {
                        Log.i(
                                WifiApSwitchEnabler.TAG,
                                "Settings Global data limit : "
                                        + (inputDataInDataUsageConfig.mUsageValueInBytes
                                                / 1000.0d));
                        WifiApConnectedDeviceUtils.setWifiApActiveSessionDataLimit(
                                this.this$0.mContext,
                                (long) ((double) inputDataInDataUsageConfig.mUsageValueInBytes));
                        try {
                            Thread.sleep(100L);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            e.printStackTrace();
                        }
                        this.this$0.startProvisioningIfNecessary();
                    }
                    WifiApSwitchEnabler.putBooleanSharedPreference(this.this$0.mContext, false);
                    break;
                case 20:
                    WifiApConnectedDeviceUtils.setWifiApActiveSessionDataLimit(
                            this.this$0.mContext, 0L);
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException e2) {
                        Thread.currentThread().interrupt();
                        e2.printStackTrace();
                    }
                    this.this$0.startProvisioningIfNecessary();
                    WifiApSwitchEnabler.putBooleanSharedPreference(this.this$0.mContext, false);
                    break;
                case 21:
                    this.this$0.updateMHSSwitch();
                    WifiApSwitchEnabler.putBooleanSharedPreference(this.this$0.mContext, false);
                    break;
                case 22:
                    WifiApSwitchEnabler wifiApSwitchEnabler = this.this$0;
                    wifiApSwitchEnabler.getClass();
                    Log.i(WifiApSwitchEnabler.TAG, "skip checkLowBatteryAndProceedFurther");
                    wifiApSwitchEnabler.checkFirstTimeConfigureAndProceedFurthur();
                    break;
                case 23:
                    this.this$0.checkFirstTimeWiFiSharingDialogAndProceedFurthur();
                    break;
                default:
                    this.this$0.updateMHSSwitch();
                    break;
            }
        }
    }

    static {
        IntentFilter intentFilter =
                new IntentFilter(
                        WifiApMobileDataSharedTodayPreferenceController
                                .ACTION_WIFI_AP_STATE_CHANGED);
        WIFIAP_SWITCH_INTENT_FILTER = intentFilter;
        intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.samsung.android.settings.wifi.mobileap.WifiApSwitchEnabler$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.samsung.android.settings.wifi.mobileap.WifiApSwitchEnabler$43] */
    public WifiApSwitchEnabler(
            FragmentActivity fragmentActivity, SettingsMainSwitchBar settingsMainSwitchBar) {
        Log.i(TAG, "WifiApSwitchEnabler WifiApSettings");
        this.mActivity = fragmentActivity;
        this.mContext = fragmentActivity;
        this.mSwitchBar = settingsMainSwitchBar;
        Utils.initMHSFeature(fragmentActivity);
        int i = fragmentActivity.getResources().getConfiguration().uiMode;
        this.mDataSaverBackend = new DataSaverBackend(fragmentActivity);
        Utils.isTablet();
        this.mSemWifiManager =
                (SemWifiManager) fragmentActivity.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        this.mAOSPWifiManager =
                (WifiManager) fragmentActivity.getSystemService(ImsProfile.PDN_WIFI);
        this.mWifiApConfiguration = new WifiApConfiguration(fragmentActivity);
        updateMHSSwitch();
    }

    public static void putBooleanSharedPreference(Context context, boolean z) {
        String str = TAG;
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "putBooleanSharedPreference() - strKey: showhotspotdialoglimit, bValue: ", str, z);
        SharedPreferences.Editor edit =
                context.getSharedPreferences("MHSNoProvisionPreferences", 0).edit();
        edit.putBoolean("showhotspotdialoglimit", z);
        edit.commit();
        Log.i(str, "putSharedPreference - showhotspotdialoglimit : " + String.valueOf(z));
    }

    public final void CheckWiFiConcurrencyAndProceedFurther() {
        boolean isP2pConnected =
                WifiApFrameworkUtils.getSemWifiManager(this.mContext).isP2pConnected();
        boolean isP2pSoftApConcurrencySupported =
                ((SemWifiP2pManager) this.mContext.getSystemService("sem_wifi_p2p"))
                        .isP2pSoftApConcurrencySupported();
        boolean isThisDualBand2GhzAnd5Ghz =
                WifiApSoftApUtils.getWifiApBandConfig(this.mContext).isThisDualBand2GhzAnd5Ghz();
        boolean z =
                isP2pConnected && (isThisDualBand2GhzAnd5Ghz || !isP2pSoftApConcurrencySupported);
        StringBuilder m =
                Utils$$ExternalSyntheticOutline0.m(
                        " isP2pCon: ",
                        isP2pConnected,
                        ", isDualBandCurSel: ",
                        isThisDualBand2GhzAnd5Ghz,
                        ", isP2pSoftApConcurrencySupported: ");
        m.append(isP2pSoftApConcurrencySupported);
        m.append(", isP2pDialogCanBeShown: ");
        m.append(z);
        String sb = m.toString();
        String str = TAG;
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "CheckWiFiConcurrencyAndProceedFurther - ", sb, str);
        this.mSemWifiManager.reportHotspotDumpLogs(str + " - " + sb);
        boolean z2 = this.mWifiApConfiguration.mSoftApBandArray.length > 1;
        AbsAdapter$$ExternalSyntheticOutline0.m("Is Dual Band: ", "WifiApConfiguration", z2);
        if (z2
                && !Utils.supportBridgedApStaConcurrency()
                && (this.mAOSPWifiManager.getWifiState() == 2
                        || this.mAOSPWifiManager.getWifiState() == 3)) {
            showDialog(24);
            return;
        }
        SemWifiDisplayStatus semGetWifiDisplayStatus =
                ((DisplayManager) this.mContext.getSystemService("display"))
                        .semGetWifiDisplayStatus();
        if (semGetWifiDisplayStatus != null
                && semGetWifiDisplayStatus.getActiveDisplayState() == 2
                && semGetWifiDisplayStatus.getConnectedState() == 2) {
            Log.d(str, "isWirelessDexEnabled:true");
            showDialog(23);
            return;
        }
        Log.d(str, "isWirelessDexEnabled:false");
        SemWifiDisplayStatus semGetWifiDisplayStatus2 =
                ((DisplayManager) this.mContext.getSystemService("display"))
                        .semGetWifiDisplayStatus();
        if (semGetWifiDisplayStatus2 != null
                && semGetWifiDisplayStatus2.getActiveDisplayState() == 2
                && semGetWifiDisplayStatus2.getConnectedState() == 0) {
            Log.d(str, "isSmartViewEnabled:true");
            if (z) {
                Log.i(str, "Smartview is enabled. Create dailog");
                showDialog(17);
                return;
            }
        } else {
            Log.d(str, "isSmartViewEnabled:false");
        }
        WifiApSettingsUtils.isNanEnabledDialogRequired(this.mContext);
        if (this.mAOSPWifiManager.getWifiState() != 2
                && this.mAOSPWifiManager.getWifiState() != 3) {
            if (z) {
                Log.i(str, "WiFi P2p is connected. Create dailog");
                showDialog(10);
                return;
            } else {
                Log.i(str, " Wi-Fi is not enabled");
                startProvisioningIfNecessary();
                return;
            }
        }
        if (!Utils.SUPPORT_MOBILEAP_WIFISHARING || !isWifiSharingEnabled()) {
            if (!"TMO".equals(Utils.CONFIGOPBRANDINGFORMOBILEAP)
                    && !"NEWCO".equals(Utils.CONFIGOPBRANDINGFORMOBILEAP)) {
                Log.i(str, "WiFi Sharing not supported/not enabled. Create dailog");
                showDialog(3);
                return;
            } else if (getWifiConnectedFrequency() != -1) {
                showDialog(3);
                return;
            } else {
                this.mAOSPWifiManager.setWifiEnabled(false);
                new Handler().postDelayed(new AnonymousClass2(0, this), 600L);
                return;
            }
        }
        if (!Utils.SUPPORT_MOBILEAP_WIFISHARINGLITE || !isWifiSharingEnabled()) {
            if (!Utils.SUPPORT_MOBILEAP_WIFISHARING || !isWifiSharingEnabled()) {
                Log.e(str, "not handling in any case, it is an error");
                return;
            }
            Log.i(str, "WiFi Sharing  model.");
            if (!z) {
                startProvisioningIfNecessary();
                return;
            } else {
                Log.i(str, "WiFi P2p is connected. Create dailog");
                showDialog(10);
                return;
            }
        }
        if (z) {
            Log.i(str, "WiFi P2p is connected. Create dailog");
            showDialog(10);
            return;
        }
        int wifiConnectedFrequency = getWifiConnectedFrequency();
        if (wifiConnectedFrequency == -1) {
            Log.i(str, "WiFi Sharing lite model. Wifi not connected");
            startProvisioningIfNecessary();
            return;
        }
        int indoorStatus = this.mSemWifiManager.getIndoorStatus();
        MainClearConfirm$$ExternalSyntheticOutline0.m(wifiConnectedFrequency, "Frequency : ", str);
        int i =
                wifiConnectedFrequency != -1
                        ? (wifiConnectedFrequency < 2412 || wifiConnectedFrequency > 2484) ? 5 : 2
                        : 0;
        Log.i(str, "Frequency band of connected AP:" + i);
        Log.i(str, "WiFi Sharing lite model. Wifi connected , indoor status:" + indoorStatus);
        if (indoorStatus == 1) {
            showDialog(20);
        } else if (i == 5) {
            showDialog(21);
        } else {
            startProvisioningIfNecessary();
        }
    }

    public final void buttonValidate() {
        WifiApDataUsageConfig wifiApActiveSessionDataLimit =
                WifiApConnectedDeviceUtils.getWifiApActiveSessionDataLimit(this.mContext);
        if (this.mDataLimitChangeLimitButton != null) {
            if (this.mDataLimitEditText.getText().length() <= 0
                    || this.mDataLimitEditText.getText().toString().trim().length() <= 0) {
                this.mDataLimitChangeLimitButton.setEnabled(false);
                return;
            }
            if (wifiApActiveSessionDataLimit.getUsageValueInMB()
                    < getInputDataInDataUsageConfig().getUsageValueInMB()) {
                this.mDataLimitChangeLimitButton.setEnabled(true);
            } else {
                this.mDataLimitChangeLimitButton.setEnabled(false);
            }
        }
    }

    public final void checkFirstTimeConfigureAndProceedFurthur() {
        String str = TAG;
        Log.i(str, "checkFirstTimeConfigureAndProceedFurthur");
        if ((!WifiApSettingsUtils.isCarrierTMO() && !WifiApSettingsUtils.isCarrierNEWCO())
                || !this.mWifiApConfiguration.isDefaultPassphraseSet()) {
            CheckWiFiConcurrencyAndProceedFurther();
            return;
        }
        this.waitingForConfigurationSet = true;
        Log.i(str, "Dialog create during first time Mobile HotSpot at TMO");
        new Bundle().putInt("needresult", 1);
        Log.i(
                str,
                "Launching WifiApEditSettings activity with title: "
                        + this.mContext.getString(R.string.wifi_ap_first_time_configuration));
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mSourceMetricsCategory = 3400;
        launchRequest.mDestinationName = WifiApEditSettings.class.getCanonicalName();
        subSettingLauncher.setTitleRes(R.string.wifi_ap_first_time_configuration, null);
        subSettingLauncher.launch();
    }

    public final void checkFirstTimeWiFiSharingDialogAndProceedFurthur() {
        Log.i(TAG, "checkFirstTimeWiFiSharingDialogAndProceedFurthur");
        if (Utils.SUPPORT_MOBILEAP_WIFISHARINGLITE
                && Settings.Secure.getInt(
                                this.mContext.getContentResolver(), "wifi_ap_wifi_sharing", 10)
                        == 10) {
            Settings.Secure.putInt(this.mContext.getContentResolver(), "wifi_ap_wifi_sharing", 0);
        }
        int wifiState = this.mAOSPWifiManager.getWifiState();
        if (Utils.SUPPORT_MOBILEAP_WIFISHARING
                && !Utils.SUPPORT_MOBILEAP_WIFISHARINGLITE
                && Settings.Secure.getInt(
                                this.mContext.getContentResolver(), "wifi_ap_wifi_sharing", 10)
                        == 1
                && Settings.Secure.getInt(
                                this.mContext.getContentResolver(),
                                "wifi_ap_first_time_wifi_sharing_dialog",
                                0)
                        == 0
                && (wifiState == 2 || wifiState == 3)) {
            Settings.Secure.putInt(
                    this.mContext.getContentResolver(),
                    "wifi_ap_first_time_wifi_sharing_dialog",
                    1);
            showDialog(14);
        } else {
            Log.i(TAG, "skip checkLowBatteryAndProceedFurther");
            checkFirstTimeConfigureAndProceedFurthur();
        }
    }

    public final void checkVZWRoamingAndProceedFurthur() {
        String str = TAG;
        Log.i(str, "checkVZWRoamingAndProceedFurthur");
        if ("VZW".equals(Utils.CONFIGOPBRANDINGFORMOBILEAP)) {
            TelephonyManager telephonyManager =
                    (TelephonyManager) this.mContext.getSystemService("phone");
            boolean isNetworkRoaming = telephonyManager.isNetworkRoaming();
            String networkCountryIso = telephonyManager.getNetworkCountryIso();
            if (isNetworkRoaming && !"us".equals(networkCountryIso)) {
                Log.i(str, "In VZW roaming");
                showDialog(5);
                return;
            }
        }
        checkFirstTimeWiFiSharingDialogAndProceedFurthur();
    }

    public final void enableTethering(boolean z) {
        AbsAdapter$$ExternalSyntheticOutline0.m("enableTethering:", TAG, z);
        if (!z) {
            this.mSemWifiManager.setWifiApEnabled((SoftApConfiguration) null, false);
            return;
        }
        if ("LGT".equals(Utils.CONFIGOPBRANDINGFORMOBILEAP)) {
            showToastMsg(R.string.wifi_ap_warn_toast_lgt);
        }
        this.mSemWifiManager.setWifiApEnabled((SoftApConfiguration) null, true);
    }

    public final WifiApDataUsageConfig getInputDataInDataUsageConfig() {
        return new WifiApDataUsageConfig(
                Long.parseLong(
                                (this.mDataLimitEditText.getText().length() <= 0
                                                || this.mDataLimitEditText
                                                                .getText()
                                                                .toString()
                                                                .trim()
                                                                .length()
                                                        <= 0)
                                        ? null
                                        : this.mDataLimitEditText.getText().toString())
                        * 1000000);
    }

    public final int getWifiConnectedFrequency() {
        WifiInfo connectionInfo = this.mAOSPWifiManager.getConnectionInfo();
        if (connectionInfo == null || connectionInfo.getNetworkId() == -1) {
            return -1;
        }
        Log.i(TAG, "Wifi Frequency is " + connectionInfo.getFrequency());
        return connectionInfo.getFrequency();
    }

    public final boolean isInternetSharingAllowed() {
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) this.mContext.getSystemService("device_policy");
        if (devicePolicyManager == null || devicePolicyManager.semGetAllowInternetSharing(null)) {
            return true;
        }
        Log.d(TAG, "WIFI_AP Internet Sharing is restricted by DPM.");
        showToastMsg(R.string.wifi_ap_security_policy);
        return false;
    }

    public final boolean isOperatorRequiresProvisioning() {
        if (DBG && SystemProperties.get("vendor.wifiap.provisioning.disable").equals("1")) {
            Log.d(TAG, "Skip isProvisioningCheck");
            return false;
        }
        String[] stringArray = this.mContext.getResources().getStringArray(17236257);
        if (stringArray == null || stringArray.length == 0) {
            Utils$$ExternalSyntheticOutline0.m(
                    new StringBuilder(" provisioning app is not set in CSCfeature, operator:"),
                    Utils.CONFIGOPBRANDINGFORMOBILEAP,
                    TAG);
            return false;
        }
        if (!SemWifiApCust.isProvisioningNeeded()) {
            Utils$$ExternalSyntheticOutline0.m(
                    new StringBuilder(" provisioning is not required for this operator, operator:"),
                    Utils.CONFIGOPBRANDINGFORMOBILEAP,
                    TAG);
            return false;
        }
        String[] stringArray2 = this.mContext.getResources().getStringArray(17236257);
        this.mProvisionApp = stringArray2;
        return stringArray2.length == 2;
    }

    public final boolean isWifiApBlocked() {
        Log.i(TAG, "isWifiApBlocked()");
        Cursor query =
                this.mContext
                        .getContentResolver()
                        .query(
                                Uri.parse("content://com.sec.knox.provider/RestrictionPolicy4"),
                                null,
                                "isWifiTetheringEnabled",
                                null,
                                null);
        if (query == null) {
            return false;
        }
        try {
            query.moveToFirst();
            return query.getString(query.getColumnIndex("isWifiTetheringEnabled")).equals("false");
        } finally {
            query.close();
        }
    }

    public final boolean isWifiConnected() {
        NetworkCapabilities networkCapabilities;
        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.mContext.getSystemService("connectivity");
        Network activeNetwork = connectivityManager.getActiveNetwork();
        if (activeNetwork == null
                || (networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork))
                        == null) {
            return false;
        }
        return networkCapabilities.hasTransport(1);
    }

    public final boolean isWifiSharingEnabled() {
        return Settings.Secure.getInt(this.mContext.getContentResolver(), "wifi_ap_wifi_sharing", 0)
                == 1;
    }

    public final void onActivityResult(int i, int i2, Intent intent) {
        String str = TAG;
        DynamicDenylistManager$$ExternalSyntheticOutline0.m(
                "onActivityResult() hit - requestCode: ", ", resultCode: ", i, i2, str);
        if (1 == i) {
            enableTethering(true);
            return;
        }
        if (i == 0) {
            if (i2 == -1) {
                Log.i(str, " provisioning success");
                this.mSemWifiManager.setProvisionSuccess(true);
                enableTethering(true);
                return;
            }
            int i3 =
                    Settings.Secure.getInt(
                            this.mContext.getContentResolver(), "wifi_saved_state", 0);
            if (this.mSemWifiManager.getWifiApState() != 12
                    && this.mSemWifiManager.getWifiApState() != 13) {
                if (isWifiConnected() && isWifiSharingEnabled()) {
                    Log.e(str, " provisioning failed, Wifi is connected, so enabling MHS ");
                    enableTethering(true);
                    return;
                } else {
                    if (i3 == 1) {
                        Log.e(str, " provisioning failed, enabling Wifi ");
                        this.mAOSPWifiManager.setWifiEnabled(true);
                        Settings.Secure.putInt(
                                this.mContext.getContentResolver(), "wifi_saved_state", 0);
                        return;
                    }
                    return;
                }
            }
            if (Utils.SUPPORT_MOBILEAP_WIFISHARING && isWifiConnected() && isWifiSharingEnabled()) {
                Log.e(str, " provisioning failed, MHS is ON and Wifi is connected");
                return;
            }
            Log.e(str, " provisioning failed, MHS is ON and Wifi is not connected");
            enableTethering(false);
            if (i3 == 1) {
                try {
                    Thread.sleep(600L);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
                Log.e(TAG, " provisioning failed, enabling Wifi ");
                this.mAOSPWifiManager.setWifiEnabled(true);
                Settings.Secure.putInt(this.mContext.getContentResolver(), "wifi_saved_state", 0);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x0080, code lost:

       if (r1 != false) goto L31;
    */
    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onCheckedChanged(android.widget.CompoundButton r6, boolean r7) {
        /*
            r5 = this;
            com.samsung.android.wifi.SemWifiManager r6 = r5.mSemWifiManager
            int r6 = r6.getWifiApState()
            java.lang.String r0 = com.samsung.android.settings.wifi.mobileap.WifiApSwitchEnabler.TAG
            java.lang.String r1 = "onSwitchChanged  getWifiApState:"
            androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0.m1m(r6, r1, r0)
            com.samsung.android.settings.wifi.mobileap.datamodels.WifiApConfiguration r1 = r5.mWifiApConfiguration
            android.content.Context r2 = r5.mContext
            com.samsung.android.wifi.SemWifiManager r3 = r5.mSemWifiManager
            android.net.wifi.SoftApConfiguration r3 = r3.getSoftApConfiguration()
            com.samsung.android.wifi.SemWifiManager r4 = r5.mSemWifiManager
            int[] r4 = r4.getSoftApBands()
            r1.update(r2, r3, r4)
            java.lang.String r1 = "onSwitchChanged: wifiApConfiguration updated"
            android.util.Log.i(r0, r1)
            r1 = 0
            if (r7 != 0) goto L36
            r2 = 13
            if (r6 != r2) goto L36
            java.lang.String r6 = "onSwitchChanged, disabling"
            android.util.Log.i(r0, r6)
            r5.enableTethering(r1)
            goto Lad
        L36:
            if (r7 == 0) goto Lad
            r7 = 11
            if (r6 == r7) goto L40
            r7 = 14
            if (r6 != r7) goto Lad
        L40:
            com.android.settings.widget.SettingsMainSwitchBar r6 = r5.mSwitchBar
            r6.setChecked(r1)
            java.lang.String r6 = "onSwitchChanged, enabling/enabled"
            android.util.Log.i(r0, r6)
            boolean r6 = r5.isWifiApBlocked()
            if (r6 == 0) goto L56
            java.lang.String r5 = "WifiApBlocked so not enabling"
            android.util.Log.i(r0, r5)
            return
        L56:
            boolean r6 = r5.isInternetSharingAllowed()
            if (r6 != 0) goto L62
            java.lang.String r5 = "InternetSharing not Allowed so not enabling"
            android.util.Log.i(r0, r5)
            return
        L62:
            com.samsung.android.settings.wifi.mobileap.datamodels.WifiApConfiguration r6 = r5.mWifiApConfiguration
            int r7 = r6.mSecurityType
            r2 = 1
            if (r7 != 0) goto L6b
            r7 = r2
            goto L6c
        L6b:
            r7 = r1
        L6c:
            java.lang.String r3 = "Is Open Security Type: "
            java.lang.String r4 = "WifiApConfiguration"
            androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0.m(r3, r4, r7)
            if (r7 != 0) goto L82
            int r6 = r6.mSecurityType
            r7 = 5
            if (r6 != r7) goto L7b
            r1 = r2
        L7b:
            java.lang.String r6 = "Is Open Enhanced Security Type: "
            androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0.m(r6, r4, r1)
            if (r1 == 0) goto Laa
        L82:
            android.content.Context r6 = r5.mContext
            android.content.Context r6 = r6.getApplicationContext()
            com.samsung.android.knox.EnterpriseDeviceManager r6 = com.samsung.android.knox.EnterpriseDeviceManager.getInstance(r6)
            if (r6 == 0) goto L9c
            com.samsung.android.knox.net.wifi.WifiPolicy r7 = r6.getWifiPolicy()
            if (r7 == 0) goto L9c
            com.samsung.android.knox.net.wifi.WifiPolicy r6 = r6.getWifiPolicy()
            boolean r2 = r6.isOpenWifiApAllowed()
        L9c:
            if (r2 != 0) goto Laa
            java.lang.String r6 = "openSecurity is not allowed"
            android.util.Log.i(r0, r6)
            r6 = 17040579(0x10404c3, float:2.4247987E-38)
            r5.showToastMsg(r6)
            return
        Laa:
            r5.checkVZWRoamingAndProceedFurthur()
        Lad:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.mobileap.WifiApSwitchEnabler.onCheckedChanged(android.widget.CompoundButton,"
                    + " boolean):void");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        putBooleanSharedPreference(this.mContext, false);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        Log.d(TAG, "onPause");
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x0085, code lost:

       if (r6 != false) goto L31;
    */
    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onPreferenceChange(
            androidx.preference.Preference r6, java.lang.Object r7) {
        /*
            r5 = this;
            java.lang.Boolean r7 = (java.lang.Boolean) r7
            boolean r6 = r7.booleanValue()
            java.lang.String r7 = com.samsung.android.settings.wifi.mobileap.WifiApSwitchEnabler.TAG
            java.lang.String r0 = "onPreferenceChange:"
            androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0.m(r0, r7, r6)
            if (r6 == 0) goto L12
            r0 = 1
            goto L14
        L12:
            r0 = 0
        L14:
            r2 = 0
            java.lang.String r3 = "TETH_010"
            java.lang.String r4 = "8001"
            com.samsung.android.settings.logging.SALogging.insertSALog(r0, r3, r4, r2)
            com.samsung.android.settings.wifi.mobileap.datamodels.WifiApConfiguration r0 = r5.mWifiApConfiguration
            android.content.Context r1 = r5.mContext
            com.samsung.android.wifi.SemWifiManager r2 = r5.mSemWifiManager
            android.net.wifi.SoftApConfiguration r2 = r2.getSoftApConfiguration()
            com.samsung.android.wifi.SemWifiManager r3 = r5.mSemWifiManager
            int[] r3 = r3.getSoftApBands()
            r0.update(r1, r2, r3)
            java.lang.String r0 = "onPreferenceChange: wifiApConfiguration updated"
            android.util.Log.i(r7, r0)
            r5.updateMHSSwitch()
            r0 = 0
            if (r6 != 0) goto L3f
            r5.enableTethering(r0)
            goto Lb2
        L3f:
            com.samsung.android.wifi.SemWifiManager r6 = r5.mSemWifiManager
            boolean r6 = r6.isWifiApEnabled()
            if (r6 != 0) goto Lb2
            boolean r6 = r5.isWifiApBlocked()
            if (r6 == 0) goto L53
            java.lang.String r5 = "WifiApBlocked so not enabling"
            android.util.Log.i(r7, r5)
            return r0
        L53:
            boolean r6 = r5.isInternetSharingAllowed()
            if (r6 != 0) goto L65
            java.lang.String r6 = "InternetSharing not Allowed so not enabling"
            android.util.Log.i(r7, r6)
            r6 = 2132030363(0x7f14339b, float:1.969937E38)
            r5.showToastMsg(r6)
            return r0
        L65:
            com.samsung.android.settings.wifi.mobileap.datamodels.WifiApConfiguration r6 = r5.mWifiApConfiguration
            int r1 = r6.mSecurityType
            r2 = 1
            if (r1 != 0) goto L6e
            r1 = r2
            goto L6f
        L6e:
            r1 = r0
        L6f:
            java.lang.String r3 = "Is Open Security Type: "
            java.lang.String r4 = "WifiApConfiguration"
            androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0.m(r3, r4, r1)
            if (r1 != 0) goto L87
            int r6 = r6.mSecurityType
            r1 = 5
            if (r6 != r1) goto L7f
            r6 = r2
            goto L80
        L7f:
            r6 = r0
        L80:
            java.lang.String r1 = "Is Open Enhanced Security Type: "
            androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0.m(r1, r4, r6)
            if (r6 == 0) goto Laf
        L87:
            android.content.Context r6 = r5.mContext
            android.content.Context r6 = r6.getApplicationContext()
            com.samsung.android.knox.EnterpriseDeviceManager r6 = com.samsung.android.knox.EnterpriseDeviceManager.getInstance(r6)
            if (r6 == 0) goto La1
            com.samsung.android.knox.net.wifi.WifiPolicy r1 = r6.getWifiPolicy()
            if (r1 == 0) goto La1
            com.samsung.android.knox.net.wifi.WifiPolicy r6 = r6.getWifiPolicy()
            boolean r2 = r6.isOpenWifiApAllowed()
        La1:
            if (r2 != 0) goto Laf
            java.lang.String r6 = "openSecurity is not allowed"
            android.util.Log.i(r7, r6)
            r6 = 17040579(0x10404c3, float:2.4247987E-38)
            r5.showToastMsg(r6)
            return r0
        Laf:
            r5.checkVZWRoamingAndProceedFurthur()
        Lb2:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.mobileap.WifiApSwitchEnabler.onPreferenceChange(androidx.preference.Preference,"
                    + " java.lang.Object):boolean");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        String str = TAG;
        Log.d(str, "onResume");
        if (this.waitingForConfigurationSet) {
            Log.i(str, "waitingForConfigurationSet()");
            this.waitingForConfigurationSet = false;
            if (this.mWifiApConfiguration.isDefaultPassphraseSet()) {
                return;
            }
            CheckWiFiConcurrencyAndProceedFurther();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        String str = TAG;
        Log.d(str, "on Start");
        boolean z = true;
        this.isBroadcastReceiverRegistered = true;
        this.mContext.registerReceiver(this.mReceiver, WIFIAP_SWITCH_INTENT_FILTER, 2);
        WifiApDataSaver wifiApDataSaver = new WifiApDataSaver();
        this.mWifiApDataSaver = wifiApDataSaver;
        this.mDataSaverBackend.addListener(wifiApDataSaver);
        int wifiApState = this.mSemWifiManager.getWifiApState();
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                wifiApState, "updateProgress wifiapstate : ", str);
        if (wifiApState != 12 && wifiApState != 10) {
            z = false;
        }
        showProgress(z);
        updateMHSSwitch();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        Log.d(TAG, "onStop");
        AlertDialog alertDialog = this.mDataLimitDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.mDataLimitDialog.dismiss();
            putBooleanSharedPreference(this.mContext, false);
        }
        try {
            if (this.isBroadcastReceiverRegistered) {
                this.isBroadcastReceiverRegistered = false;
                this.mContext.unregisterReceiver(this.mReceiver);
            }
        } catch (IllegalArgumentException unused) {
        }
        this.mDataSaverBackend.remListener(this.mWifiApDataSaver);
    }

    public final void showDialog(int i) {
        final int i2 = 4;
        final int i3 = 8;
        final int i4 = 9;
        int i5 = 17;
        int i6 = 14;
        final int i7 = 10;
        final int i8 = 7;
        final int i9 = 5;
        final int i10 = 3;
        final int i11 = 0;
        final int i12 = 1;
        String str = TAG;
        MainClearConfirm$$ExternalSyntheticOutline0.m(i, "showDialog() - id: ", str);
        if (i == 1) {
            Context context = this.mContext;
            boolean z = WifiApSettingsUtils.DBG;
            AlertDialog.Builder builder = new AlertDialog.Builder(context, 0);
            if ("CTC".equals(Utils.getSalesCode())) {
                builder.setMessage(R.string.mobile_hotspot_dialog_nouim_or_nosim_warning);
            } else {
                builder.setMessage(
                        WifiApUtils.getString(
                                this.mContext, R.string.wifi_tether_dialog_nosim_warning));
            }
            builder.setPositiveButton(R.string.dlg_ok, new AnonymousClass3(this, i11));
            builder.setOnCancelListener(
                    new DialogInterface.OnCancelListener(
                            this) { // from class:
                                    // com.samsung.android.settings.wifi.mobileap.WifiApSwitchEnabler.4
                        public final /* synthetic */ WifiApSwitchEnabler this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnCancelListener
                        public final void onCancel(DialogInterface dialogInterface) {
                            switch (i11) {
                                case 0:
                                    this.this$0.updateMHSSwitch();
                                    break;
                                case 1:
                                    this.this$0.updateMHSSwitch();
                                    break;
                                case 2:
                                    this.this$0.updateMHSSwitch();
                                    break;
                                case 3:
                                    this.this$0.updateMHSSwitch();
                                    break;
                                case 4:
                                    this.this$0.updateMHSSwitch();
                                    break;
                                case 5:
                                    this.this$0.updateMHSSwitch();
                                    break;
                                case 6:
                                    this.this$0.updateMHSSwitch();
                                    break;
                                case 7:
                                    this.this$0.updateMHSSwitch();
                                    break;
                                case 8:
                                    this.this$0.updateMHSSwitch();
                                    break;
                                case 9:
                                    this.this$0.updateMHSSwitch();
                                    break;
                                case 10:
                                    this.this$0.updateMHSSwitch();
                                    WifiApSwitchEnabler.putBooleanSharedPreference(
                                            this.this$0.mContext, false);
                                    break;
                                default:
                                    WifiApSwitchEnabler wifiApSwitchEnabler = this.this$0;
                                    wifiApSwitchEnabler.getClass();
                                    Log.i(
                                            WifiApSwitchEnabler.TAG,
                                            "skip checkLowBatteryAndProceedFurther");
                                    wifiApSwitchEnabler.checkFirstTimeConfigureAndProceedFurthur();
                                    break;
                            }
                        }
                    });
            builder.setTitle(R.string.wifi_tether_dialog_nosim_warning_title);
            builder.create().show();
        }
        if (i == 3) {
            Context context2 = this.mContext;
            boolean z2 = WifiApSettingsUtils.DBG;
            AlertDialog.Builder builder2 = new AlertDialog.Builder(context2, 0);
            builder2.setMessage(
                    WifiApUtils.getString(this.mContext, R.string.wifi_ap_wifi_off_warn));
            builder2.setPositiveButton(
                    WifiApUtils.getString(this.mContext, R.string.positive_button_off),
                    new AnonymousClass3(this, 12));
            builder2.setNegativeButton(R.string.dlg_cancel, new AnonymousClass3(this, 13));
            final int i13 = 6;
            builder2.setOnCancelListener(
                    new DialogInterface.OnCancelListener(
                            this) { // from class:
                                    // com.samsung.android.settings.wifi.mobileap.WifiApSwitchEnabler.4
                        public final /* synthetic */ WifiApSwitchEnabler this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnCancelListener
                        public final void onCancel(DialogInterface dialogInterface) {
                            switch (i13) {
                                case 0:
                                    this.this$0.updateMHSSwitch();
                                    break;
                                case 1:
                                    this.this$0.updateMHSSwitch();
                                    break;
                                case 2:
                                    this.this$0.updateMHSSwitch();
                                    break;
                                case 3:
                                    this.this$0.updateMHSSwitch();
                                    break;
                                case 4:
                                    this.this$0.updateMHSSwitch();
                                    break;
                                case 5:
                                    this.this$0.updateMHSSwitch();
                                    break;
                                case 6:
                                    this.this$0.updateMHSSwitch();
                                    break;
                                case 7:
                                    this.this$0.updateMHSSwitch();
                                    break;
                                case 8:
                                    this.this$0.updateMHSSwitch();
                                    break;
                                case 9:
                                    this.this$0.updateMHSSwitch();
                                    break;
                                case 10:
                                    this.this$0.updateMHSSwitch();
                                    WifiApSwitchEnabler.putBooleanSharedPreference(
                                            this.this$0.mContext, false);
                                    break;
                                default:
                                    WifiApSwitchEnabler wifiApSwitchEnabler = this.this$0;
                                    wifiApSwitchEnabler.getClass();
                                    Log.i(
                                            WifiApSwitchEnabler.TAG,
                                            "skip checkLowBatteryAndProceedFurther");
                                    wifiApSwitchEnabler.checkFirstTimeConfigureAndProceedFurthur();
                                    break;
                            }
                        }
                    });
            builder2.setTitle(
                    WifiApUtils.getString(this.mContext, R.string.wifi_ap_wifi_off_warn_title));
            builder2.create().show();
            return;
        }
        if (i == 5) {
            Context context3 = this.mContext;
            boolean z3 = WifiApSettingsUtils.DBG;
            this.mAlertDialogBuilder = new AlertDialog.Builder(context3, 0);
            if ("VZW".equals(Utils.CONFIGOPBRANDINGFORMOBILEAP)) {
                this.mAlertDialogBuilder.setMessage(
                        WifiApUtils.getString(
                                this.mContext, R.string.wifi_ap_warn_roaming_msg_beyond_vzw));
                this.mAlertDialogBuilder.setPositiveButton(
                        R.string.lockpattern_continue_button_text, new AnonymousClass3(this, 23));
                this.mAlertDialogBuilder.setNegativeButton(
                        R.string.dlg_cancel, new AnonymousClass3(this, 24));
                this.mAlertDialogBuilder.setTitle(R.string.wifi_ap_warn_roaming_title_beyond_vzw);
                this.mAlertDialogBuilder
                        .create()
                        .setOnDismissListener(
                                new DialogInterface.OnDismissListener(
                                        this) { // from class:
                                                // com.samsung.android.settings.wifi.mobileap.WifiApSwitchEnabler.9
                                    public final /* synthetic */ WifiApSwitchEnabler this$0;

                                    {
                                        this.this$0 = this;
                                    }

                                    @Override // android.content.DialogInterface.OnDismissListener
                                    public final void onDismiss(DialogInterface dialogInterface) {
                                        switch (i11) {
                                            case 0:
                                                this.this$0.updateMHSSwitch();
                                                break;
                                            default:
                                                this.this$0.updateMHSSwitch();
                                                break;
                                        }
                                    }
                                });
            } else {
                this.mAlertDialogBuilder.setMessage(
                        this.mContext.getString(R.string.wifi_ap_warn_roaming_msg, "$20.48"));
                this.mAlertDialogBuilder.setPositiveButton(
                        R.string.lockpattern_continue_button_text, new AnonymousClass3(this, i12));
                this.mAlertDialogBuilder.setTitle(R.string.wifi_ap_warn_roaming_title);
                this.mAlertDialogBuilder
                        .create()
                        .setOnDismissListener(
                                new DialogInterface.OnDismissListener(
                                        this) { // from class:
                                                // com.samsung.android.settings.wifi.mobileap.WifiApSwitchEnabler.9
                                    public final /* synthetic */ WifiApSwitchEnabler this$0;

                                    {
                                        this.this$0 = this;
                                    }

                                    @Override // android.content.DialogInterface.OnDismissListener
                                    public final void onDismiss(DialogInterface dialogInterface) {
                                        switch (i12) {
                                            case 0:
                                                this.this$0.updateMHSSwitch();
                                                break;
                                            default:
                                                this.this$0.updateMHSSwitch();
                                                break;
                                        }
                                    }
                                });
            }
            this.mAlertDialogBuilder.create().show();
            return;
        }
        if (i == 7) {
            Context context4 = this.mContext;
            boolean z4 = WifiApSettingsUtils.DBG;
            AlertDialog.Builder builder3 = new AlertDialog.Builder(context4, 0);
            this.mAlertDialogBuilder = builder3;
            builder3.setMessage(
                            WifiApUtils.getString(
                                    this.mContext, R.string.dialog_battery_data_warning))
                    .setPositiveButton(R.string.dlg_ok, new AnonymousClass3(this, i10))
                    .setNegativeButton(R.string.dlg_cancel, new AnonymousClass3(this, 2))
                    .setOnCancelListener(
                            new DialogInterface.OnCancelListener(
                                    this) { // from class:
                                            // com.samsung.android.settings.wifi.mobileap.WifiApSwitchEnabler.4
                                public final /* synthetic */ WifiApSwitchEnabler this$0;

                                {
                                    this.this$0 = this;
                                }

                                @Override // android.content.DialogInterface.OnCancelListener
                                public final void onCancel(DialogInterface dialogInterface) {
                                    switch (i12) {
                                        case 0:
                                            this.this$0.updateMHSSwitch();
                                            break;
                                        case 1:
                                            this.this$0.updateMHSSwitch();
                                            break;
                                        case 2:
                                            this.this$0.updateMHSSwitch();
                                            break;
                                        case 3:
                                            this.this$0.updateMHSSwitch();
                                            break;
                                        case 4:
                                            this.this$0.updateMHSSwitch();
                                            break;
                                        case 5:
                                            this.this$0.updateMHSSwitch();
                                            break;
                                        case 6:
                                            this.this$0.updateMHSSwitch();
                                            break;
                                        case 7:
                                            this.this$0.updateMHSSwitch();
                                            break;
                                        case 8:
                                            this.this$0.updateMHSSwitch();
                                            break;
                                        case 9:
                                            this.this$0.updateMHSSwitch();
                                            break;
                                        case 10:
                                            this.this$0.updateMHSSwitch();
                                            WifiApSwitchEnabler.putBooleanSharedPreference(
                                                    this.this$0.mContext, false);
                                            break;
                                        default:
                                            WifiApSwitchEnabler wifiApSwitchEnabler = this.this$0;
                                            wifiApSwitchEnabler.getClass();
                                            Log.i(
                                                    WifiApSwitchEnabler.TAG,
                                                    "skip checkLowBatteryAndProceedFurther");
                                            wifiApSwitchEnabler
                                                    .checkFirstTimeConfigureAndProceedFurthur();
                                            break;
                                    }
                                }
                            })
                    .setTitle(WifiApUtils.getString(this.mContext, R.string.mobileap));
            this.mAlertDialogBuilder.create().show();
            return;
        }
        if (i == 10) {
            Context context5 = this.mContext;
            boolean z5 = WifiApSettingsUtils.DBG;
            AlertDialog.Builder builder4 = new AlertDialog.Builder(context5, 0);
            builder4.setMessage(
                            WifiApUtils.getString(
                                    this.mContext, R.string.wifi_ap_wifi_p2p_off_warn))
                    .setPositiveButton(R.string.turn_on_button, new AnonymousClass3(this, 15))
                    .setNegativeButton(R.string.dlg_cancel, new AnonymousClass3(this, i6))
                    .setOnCancelListener(
                            new DialogInterface.OnCancelListener(
                                    this) { // from class:
                                            // com.samsung.android.settings.wifi.mobileap.WifiApSwitchEnabler.4
                                public final /* synthetic */ WifiApSwitchEnabler this$0;

                                {
                                    this.this$0 = this;
                                }

                                @Override // android.content.DialogInterface.OnCancelListener
                                public final void onCancel(DialogInterface dialogInterface) {
                                    switch (i8) {
                                        case 0:
                                            this.this$0.updateMHSSwitch();
                                            break;
                                        case 1:
                                            this.this$0.updateMHSSwitch();
                                            break;
                                        case 2:
                                            this.this$0.updateMHSSwitch();
                                            break;
                                        case 3:
                                            this.this$0.updateMHSSwitch();
                                            break;
                                        case 4:
                                            this.this$0.updateMHSSwitch();
                                            break;
                                        case 5:
                                            this.this$0.updateMHSSwitch();
                                            break;
                                        case 6:
                                            this.this$0.updateMHSSwitch();
                                            break;
                                        case 7:
                                            this.this$0.updateMHSSwitch();
                                            break;
                                        case 8:
                                            this.this$0.updateMHSSwitch();
                                            break;
                                        case 9:
                                            this.this$0.updateMHSSwitch();
                                            break;
                                        case 10:
                                            this.this$0.updateMHSSwitch();
                                            WifiApSwitchEnabler.putBooleanSharedPreference(
                                                    this.this$0.mContext, false);
                                            break;
                                        default:
                                            WifiApSwitchEnabler wifiApSwitchEnabler = this.this$0;
                                            wifiApSwitchEnabler.getClass();
                                            Log.i(
                                                    WifiApSwitchEnabler.TAG,
                                                    "skip checkLowBatteryAndProceedFurther");
                                            wifiApSwitchEnabler
                                                    .checkFirstTimeConfigureAndProceedFurthur();
                                            break;
                                    }
                                }
                            })
                    .setTitle(WifiApUtils.getStringID(R.string.wifi_ap_wifi_p2p_off_warn_title));
            builder4.create().show();
            return;
        }
        if (i == 14) {
            Log.i(str, "DIALOG_WIFI_SHARING_FIRST_TIME");
            this.mContext.getContentResolver();
            Context context6 = this.mContext;
            boolean z6 = WifiApSettingsUtils.DBG;
            AlertDialog.Builder builder5 = new AlertDialog.Builder(context6, 0);
            final int i14 = 11;
            builder5.setTitle(
                            WifiApUtils.getString(
                                    this.mContext, R.string.wifi_sharing_first_time_dialog_title))
                    .setMessage(
                            WifiApUtils.getString(
                                    this.mContext, R.string.wifi_sharing_first_time_dialog_text))
                    .setPositiveButton(R.string.dlg_ok, new AnonymousClass3(this, 22))
                    .setOnCancelListener(
                            new DialogInterface.OnCancelListener(
                                    this) { // from class:
                                            // com.samsung.android.settings.wifi.mobileap.WifiApSwitchEnabler.4
                                public final /* synthetic */ WifiApSwitchEnabler this$0;

                                {
                                    this.this$0 = this;
                                }

                                @Override // android.content.DialogInterface.OnCancelListener
                                public final void onCancel(DialogInterface dialogInterface) {
                                    switch (i14) {
                                        case 0:
                                            this.this$0.updateMHSSwitch();
                                            break;
                                        case 1:
                                            this.this$0.updateMHSSwitch();
                                            break;
                                        case 2:
                                            this.this$0.updateMHSSwitch();
                                            break;
                                        case 3:
                                            this.this$0.updateMHSSwitch();
                                            break;
                                        case 4:
                                            this.this$0.updateMHSSwitch();
                                            break;
                                        case 5:
                                            this.this$0.updateMHSSwitch();
                                            break;
                                        case 6:
                                            this.this$0.updateMHSSwitch();
                                            break;
                                        case 7:
                                            this.this$0.updateMHSSwitch();
                                            break;
                                        case 8:
                                            this.this$0.updateMHSSwitch();
                                            break;
                                        case 9:
                                            this.this$0.updateMHSSwitch();
                                            break;
                                        case 10:
                                            this.this$0.updateMHSSwitch();
                                            WifiApSwitchEnabler.putBooleanSharedPreference(
                                                    this.this$0.mContext, false);
                                            break;
                                        default:
                                            WifiApSwitchEnabler wifiApSwitchEnabler = this.this$0;
                                            wifiApSwitchEnabler.getClass();
                                            Log.i(
                                                    WifiApSwitchEnabler.TAG,
                                                    "skip checkLowBatteryAndProceedFurther");
                                            wifiApSwitchEnabler
                                                    .checkFirstTimeConfigureAndProceedFurthur();
                                            break;
                                    }
                                }
                            });
            builder5.create().show();
            return;
        }
        if (i == 17) {
            Log.d(str, "CASE DIALOG_WARN_SMARTVIEW_DISABLE");
            Context context7 = this.mContext;
            boolean z7 = WifiApSettingsUtils.DBG;
            AlertDialog.Builder builder6 = new AlertDialog.Builder(context7, 0);
            this.mAlertDialogBuilder = builder6;
            final int i15 = 2;
            builder6.setMessage(WifiApUtils.getStringID(R.string.wifi_ap_smartview_off_warn))
                    .setPositiveButton(R.string.turn_off_button, new AnonymousClass3(this, i9))
                    .setNegativeButton(R.string.dlg_cancel, new AnonymousClass3(this, i2))
                    .setOnCancelListener(
                            new DialogInterface.OnCancelListener(
                                    this) { // from class:
                                            // com.samsung.android.settings.wifi.mobileap.WifiApSwitchEnabler.4
                                public final /* synthetic */ WifiApSwitchEnabler this$0;

                                {
                                    this.this$0 = this;
                                }

                                @Override // android.content.DialogInterface.OnCancelListener
                                public final void onCancel(DialogInterface dialogInterface) {
                                    switch (i15) {
                                        case 0:
                                            this.this$0.updateMHSSwitch();
                                            break;
                                        case 1:
                                            this.this$0.updateMHSSwitch();
                                            break;
                                        case 2:
                                            this.this$0.updateMHSSwitch();
                                            break;
                                        case 3:
                                            this.this$0.updateMHSSwitch();
                                            break;
                                        case 4:
                                            this.this$0.updateMHSSwitch();
                                            break;
                                        case 5:
                                            this.this$0.updateMHSSwitch();
                                            break;
                                        case 6:
                                            this.this$0.updateMHSSwitch();
                                            break;
                                        case 7:
                                            this.this$0.updateMHSSwitch();
                                            break;
                                        case 8:
                                            this.this$0.updateMHSSwitch();
                                            break;
                                        case 9:
                                            this.this$0.updateMHSSwitch();
                                            break;
                                        case 10:
                                            this.this$0.updateMHSSwitch();
                                            WifiApSwitchEnabler.putBooleanSharedPreference(
                                                    this.this$0.mContext, false);
                                            break;
                                        default:
                                            WifiApSwitchEnabler wifiApSwitchEnabler = this.this$0;
                                            wifiApSwitchEnabler.getClass();
                                            Log.i(
                                                    WifiApSwitchEnabler.TAG,
                                                    "skip checkLowBatteryAndProceedFurther");
                                            wifiApSwitchEnabler
                                                    .checkFirstTimeConfigureAndProceedFurthur();
                                            break;
                                    }
                                }
                            })
                    .setTitle(R.string.wifi_ap_smartview_off_warn_title);
            this.mAlertDialogBuilder.create().show();
            return;
        }
        switch (i) {
            case 20:
                Context context8 = this.mContext;
                boolean z8 = WifiApSettingsUtils.DBG;
                AlertDialog.Builder builder7 = new AlertDialog.Builder(context8, 0);
                builder7.setMessage(
                        WifiApUtils.getString(
                                this.mContext, R.string.wifi_ap_wifi_sharinglite_indoor_text));
                builder7.setPositiveButton(R.string.dlg_ok, new AnonymousClass3(this, 16));
                builder7.setNegativeButton(R.string.dlg_cancel, new AnonymousClass3(this, i5));
                builder7.setOnCancelListener(
                        new DialogInterface.OnCancelListener(
                                this) { // from class:
                                        // com.samsung.android.settings.wifi.mobileap.WifiApSwitchEnabler.4
                            public final /* synthetic */ WifiApSwitchEnabler this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.content.DialogInterface.OnCancelListener
                            public final void onCancel(DialogInterface dialogInterface) {
                                switch (i3) {
                                    case 0:
                                        this.this$0.updateMHSSwitch();
                                        break;
                                    case 1:
                                        this.this$0.updateMHSSwitch();
                                        break;
                                    case 2:
                                        this.this$0.updateMHSSwitch();
                                        break;
                                    case 3:
                                        this.this$0.updateMHSSwitch();
                                        break;
                                    case 4:
                                        this.this$0.updateMHSSwitch();
                                        break;
                                    case 5:
                                        this.this$0.updateMHSSwitch();
                                        break;
                                    case 6:
                                        this.this$0.updateMHSSwitch();
                                        break;
                                    case 7:
                                        this.this$0.updateMHSSwitch();
                                        break;
                                    case 8:
                                        this.this$0.updateMHSSwitch();
                                        break;
                                    case 9:
                                        this.this$0.updateMHSSwitch();
                                        break;
                                    case 10:
                                        this.this$0.updateMHSSwitch();
                                        WifiApSwitchEnabler.putBooleanSharedPreference(
                                                this.this$0.mContext, false);
                                        break;
                                    default:
                                        WifiApSwitchEnabler wifiApSwitchEnabler = this.this$0;
                                        wifiApSwitchEnabler.getClass();
                                        Log.i(
                                                WifiApSwitchEnabler.TAG,
                                                "skip checkLowBatteryAndProceedFurther");
                                        wifiApSwitchEnabler
                                                .checkFirstTimeConfigureAndProceedFurthur();
                                        break;
                                }
                            }
                        });
                builder7.setTitle(
                        WifiApUtils.getString(
                                this.mContext,
                                R.string.wifi_ap_wifi_sharinglite_indoor_disconnect_text));
                builder7.create().show();
                break;
            case 21:
                Context context9 = this.mContext;
                boolean z9 = WifiApSettingsUtils.DBG;
                AlertDialog.Builder builder8 = new AlertDialog.Builder(context9, 0);
                builder8.setMessage(
                        WifiApUtils.getString(
                                this.mContext,
                                R.string.wifi_ap_wifi_sharinglite_24ghz_client_mhs_on_text));
                builder8.setPositiveButton(R.string.dlg_ok, new AnonymousClass3(this, 18));
                builder8.setOnCancelListener(
                        new DialogInterface.OnCancelListener(
                                this) { // from class:
                                        // com.samsung.android.settings.wifi.mobileap.WifiApSwitchEnabler.4
                            public final /* synthetic */ WifiApSwitchEnabler this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.content.DialogInterface.OnCancelListener
                            public final void onCancel(DialogInterface dialogInterface) {
                                switch (i4) {
                                    case 0:
                                        this.this$0.updateMHSSwitch();
                                        break;
                                    case 1:
                                        this.this$0.updateMHSSwitch();
                                        break;
                                    case 2:
                                        this.this$0.updateMHSSwitch();
                                        break;
                                    case 3:
                                        this.this$0.updateMHSSwitch();
                                        break;
                                    case 4:
                                        this.this$0.updateMHSSwitch();
                                        break;
                                    case 5:
                                        this.this$0.updateMHSSwitch();
                                        break;
                                    case 6:
                                        this.this$0.updateMHSSwitch();
                                        break;
                                    case 7:
                                        this.this$0.updateMHSSwitch();
                                        break;
                                    case 8:
                                        this.this$0.updateMHSSwitch();
                                        break;
                                    case 9:
                                        this.this$0.updateMHSSwitch();
                                        break;
                                    case 10:
                                        this.this$0.updateMHSSwitch();
                                        WifiApSwitchEnabler.putBooleanSharedPreference(
                                                this.this$0.mContext, false);
                                        break;
                                    default:
                                        WifiApSwitchEnabler wifiApSwitchEnabler = this.this$0;
                                        wifiApSwitchEnabler.getClass();
                                        Log.i(
                                                WifiApSwitchEnabler.TAG,
                                                "skip checkLowBatteryAndProceedFurther");
                                        wifiApSwitchEnabler
                                                .checkFirstTimeConfigureAndProceedFurthur();
                                        break;
                                }
                            }
                        });
                builder8.setTitle(
                        WifiApUtils.getString(
                                this.mContext,
                                R.string.wifi_ap_wifi_sharinglite_mobile_hotspot_not_available));
                builder8.create().show();
                break;
            case 22:
                Context context10 = this.mContext;
                boolean z10 = WifiApSettingsUtils.DBG;
                AlertDialog.Builder builder9 = new AlertDialog.Builder(context10, 0);
                builder9.setMessage(R.string.wifi_ap_wifi_nan_off_warn)
                        .setPositiveButton(
                                R.string.wifi_ap_button_stop, new AnonymousClass3(this, 11))
                        .setNegativeButton(R.string.dlg_cancel, new AnonymousClass3(this, i7))
                        .setOnCancelListener(
                                new DialogInterface.OnCancelListener(
                                        this) { // from class:
                                                // com.samsung.android.settings.wifi.mobileap.WifiApSwitchEnabler.4
                                    public final /* synthetic */ WifiApSwitchEnabler this$0;

                                    {
                                        this.this$0 = this;
                                    }

                                    @Override // android.content.DialogInterface.OnCancelListener
                                    public final void onCancel(DialogInterface dialogInterface) {
                                        switch (i9) {
                                            case 0:
                                                this.this$0.updateMHSSwitch();
                                                break;
                                            case 1:
                                                this.this$0.updateMHSSwitch();
                                                break;
                                            case 2:
                                                this.this$0.updateMHSSwitch();
                                                break;
                                            case 3:
                                                this.this$0.updateMHSSwitch();
                                                break;
                                            case 4:
                                                this.this$0.updateMHSSwitch();
                                                break;
                                            case 5:
                                                this.this$0.updateMHSSwitch();
                                                break;
                                            case 6:
                                                this.this$0.updateMHSSwitch();
                                                break;
                                            case 7:
                                                this.this$0.updateMHSSwitch();
                                                break;
                                            case 8:
                                                this.this$0.updateMHSSwitch();
                                                break;
                                            case 9:
                                                this.this$0.updateMHSSwitch();
                                                break;
                                            case 10:
                                                this.this$0.updateMHSSwitch();
                                                WifiApSwitchEnabler.putBooleanSharedPreference(
                                                        this.this$0.mContext, false);
                                                break;
                                            default:
                                                WifiApSwitchEnabler wifiApSwitchEnabler =
                                                        this.this$0;
                                                wifiApSwitchEnabler.getClass();
                                                Log.i(
                                                        WifiApSwitchEnabler.TAG,
                                                        "skip checkLowBatteryAndProceedFurther");
                                                wifiApSwitchEnabler
                                                        .checkFirstTimeConfigureAndProceedFurthur();
                                                break;
                                        }
                                    }
                                })
                        .setTitle(R.string.wifi_ap_wifi_nan_off_warn_title);
                builder9.create().show();
                break;
            case 23:
                Log.i(str, "CASE DIALOG_WARN_WIRELESS_DEX_DISABLE");
                Context context11 = this.mContext;
                boolean z11 = WifiApSettingsUtils.DBG;
                new AlertDialog.Builder(context11, 0)
                        .setMessage(R.string.wifi_ap_wirelessdex_off_warn)
                        .setPositiveButton(
                                R.string.wifi_ap_disconnect_text, new AnonymousClass3(this, i8))
                        .setNegativeButton(R.string.dlg_cancel, new AnonymousClass3(this, 6))
                        .setOnCancelListener(
                                new DialogInterface.OnCancelListener(
                                        this) { // from class:
                                                // com.samsung.android.settings.wifi.mobileap.WifiApSwitchEnabler.4
                                    public final /* synthetic */ WifiApSwitchEnabler this$0;

                                    {
                                        this.this$0 = this;
                                    }

                                    @Override // android.content.DialogInterface.OnCancelListener
                                    public final void onCancel(DialogInterface dialogInterface) {
                                        switch (i10) {
                                            case 0:
                                                this.this$0.updateMHSSwitch();
                                                break;
                                            case 1:
                                                this.this$0.updateMHSSwitch();
                                                break;
                                            case 2:
                                                this.this$0.updateMHSSwitch();
                                                break;
                                            case 3:
                                                this.this$0.updateMHSSwitch();
                                                break;
                                            case 4:
                                                this.this$0.updateMHSSwitch();
                                                break;
                                            case 5:
                                                this.this$0.updateMHSSwitch();
                                                break;
                                            case 6:
                                                this.this$0.updateMHSSwitch();
                                                break;
                                            case 7:
                                                this.this$0.updateMHSSwitch();
                                                break;
                                            case 8:
                                                this.this$0.updateMHSSwitch();
                                                break;
                                            case 9:
                                                this.this$0.updateMHSSwitch();
                                                break;
                                            case 10:
                                                this.this$0.updateMHSSwitch();
                                                WifiApSwitchEnabler.putBooleanSharedPreference(
                                                        this.this$0.mContext, false);
                                                break;
                                            default:
                                                WifiApSwitchEnabler wifiApSwitchEnabler =
                                                        this.this$0;
                                                wifiApSwitchEnabler.getClass();
                                                Log.i(
                                                        WifiApSwitchEnabler.TAG,
                                                        "skip checkLowBatteryAndProceedFurther");
                                                wifiApSwitchEnabler
                                                        .checkFirstTimeConfigureAndProceedFurthur();
                                                break;
                                        }
                                    }
                                })
                        .setTitle(R.string.wifi_ap_wirelessdex_off_warn_title)
                        .create()
                        .show();
                break;
            case 24:
                Log.i(str, "CASE DIALOG_WARN_DUALAP_WIFI_DISABLE");
                Context context12 = this.mContext;
                boolean z12 = WifiApSettingsUtils.DBG;
                new AlertDialog.Builder(context12, 0)
                        .setMessage(R.string.wifi_ap_dualap_wifi_off_warn)
                        .setPositiveButton(
                                WifiApUtils.getString(this.mContext, R.string.positive_button_off),
                                new AnonymousClass3(this, i4))
                        .setNegativeButton(R.string.dlg_cancel, new AnonymousClass3(this, i3))
                        .setOnCancelListener(
                                new DialogInterface.OnCancelListener(
                                        this) { // from class:
                                                // com.samsung.android.settings.wifi.mobileap.WifiApSwitchEnabler.4
                                    public final /* synthetic */ WifiApSwitchEnabler this$0;

                                    {
                                        this.this$0 = this;
                                    }

                                    @Override // android.content.DialogInterface.OnCancelListener
                                    public final void onCancel(DialogInterface dialogInterface) {
                                        switch (i2) {
                                            case 0:
                                                this.this$0.updateMHSSwitch();
                                                break;
                                            case 1:
                                                this.this$0.updateMHSSwitch();
                                                break;
                                            case 2:
                                                this.this$0.updateMHSSwitch();
                                                break;
                                            case 3:
                                                this.this$0.updateMHSSwitch();
                                                break;
                                            case 4:
                                                this.this$0.updateMHSSwitch();
                                                break;
                                            case 5:
                                                this.this$0.updateMHSSwitch();
                                                break;
                                            case 6:
                                                this.this$0.updateMHSSwitch();
                                                break;
                                            case 7:
                                                this.this$0.updateMHSSwitch();
                                                break;
                                            case 8:
                                                this.this$0.updateMHSSwitch();
                                                break;
                                            case 9:
                                                this.this$0.updateMHSSwitch();
                                                break;
                                            case 10:
                                                this.this$0.updateMHSSwitch();
                                                WifiApSwitchEnabler.putBooleanSharedPreference(
                                                        this.this$0.mContext, false);
                                                break;
                                            default:
                                                WifiApSwitchEnabler wifiApSwitchEnabler =
                                                        this.this$0;
                                                wifiApSwitchEnabler.getClass();
                                                Log.i(
                                                        WifiApSwitchEnabler.TAG,
                                                        "skip checkLowBatteryAndProceedFurther");
                                                wifiApSwitchEnabler
                                                        .checkFirstTimeConfigureAndProceedFurthur();
                                                break;
                                        }
                                    }
                                })
                        .setTitle(
                                WifiApUtils.getString(
                                        this.mContext, R.string.wifi_ap_wifi_off_warn_title))
                        .create()
                        .show();
                break;
            case 25:
                Context context13 = this.mContext;
                WifiApDataUsageConfig wifiApActiveSessionDataLimit =
                        WifiApConnectedDeviceUtils.getWifiApActiveSessionDataLimit(context13);
                boolean z13 = WifiApSettingsUtils.DBG;
                AlertDialog.Builder builder10 = new AlertDialog.Builder(context13, 0);
                View inflate =
                        View.inflate(
                                this.mContext, R.layout.sec_wifi_ap_set_data_limit_dialog1, null);
                builder10.setView(inflate);
                ((TextView) inflate.findViewById(R.id.data_limit_message_textview))
                        .setText(R.string.wifi_ap_you_have_already_used_hotspot_data_today);
                EditText editText = (EditText) inflate.findViewById(R.id.data_limit_edittext);
                this.mDataLimitEditText = editText;
                editText.addTextChangedListener(this.mDataLimitEditTextWatcher);
                this.mDataLimitEditText.setText(
                        String.valueOf((int) wifiApActiveSessionDataLimit.getUsageValueInMB()));
                this.mDataLimitEditText.selectAll();
                this.mDataLimitEditErrorTextView =
                        (TextView) inflate.findViewById(R.id.data_limit_edit_error_textview);
                builder10.setTitle(R.string.wifi_ap_data_limit_reached);
                builder10.setPositiveButton(
                        R.string.wifi_ap_change_limit, new AnonymousClass3(this, 19));
                builder10.setNegativeButton(
                        R.string.data_limit_cancel, new AnonymousClass3(this, 20));
                builder10.setNeutralButton(R.string.dlg_cancel, new AnonymousClass3(this, 21));
                builder10.setOnCancelListener(
                        new DialogInterface.OnCancelListener(
                                this) { // from class:
                                        // com.samsung.android.settings.wifi.mobileap.WifiApSwitchEnabler.4
                            public final /* synthetic */ WifiApSwitchEnabler this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.content.DialogInterface.OnCancelListener
                            public final void onCancel(DialogInterface dialogInterface) {
                                switch (i7) {
                                    case 0:
                                        this.this$0.updateMHSSwitch();
                                        break;
                                    case 1:
                                        this.this$0.updateMHSSwitch();
                                        break;
                                    case 2:
                                        this.this$0.updateMHSSwitch();
                                        break;
                                    case 3:
                                        this.this$0.updateMHSSwitch();
                                        break;
                                    case 4:
                                        this.this$0.updateMHSSwitch();
                                        break;
                                    case 5:
                                        this.this$0.updateMHSSwitch();
                                        break;
                                    case 6:
                                        this.this$0.updateMHSSwitch();
                                        break;
                                    case 7:
                                        this.this$0.updateMHSSwitch();
                                        break;
                                    case 8:
                                        this.this$0.updateMHSSwitch();
                                        break;
                                    case 9:
                                        this.this$0.updateMHSSwitch();
                                        break;
                                    case 10:
                                        this.this$0.updateMHSSwitch();
                                        WifiApSwitchEnabler.putBooleanSharedPreference(
                                                this.this$0.mContext, false);
                                        break;
                                    default:
                                        WifiApSwitchEnabler wifiApSwitchEnabler = this.this$0;
                                        wifiApSwitchEnabler.getClass();
                                        Log.i(
                                                WifiApSwitchEnabler.TAG,
                                                "skip checkLowBatteryAndProceedFurther");
                                        wifiApSwitchEnabler
                                                .checkFirstTimeConfigureAndProceedFurthur();
                                        break;
                                }
                            }
                        });
                AlertDialog create = builder10.create();
                this.mDataLimitDialog = create;
                create.show();
                this.mDataLimitChangeLimitButton = this.mDataLimitDialog.getButton(-1);
                putBooleanSharedPreference(this.mContext, true);
                buttonValidate();
                break;
        }
    }

    public final void showProgress(boolean z) {
        String str = TAG;
        AbsAdapter$$ExternalSyntheticOutline0.m("showProgress() - ", str, z);
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.setProgressBarVisible(z);
        } else {
            if (this.mSwitchPref != null) {
                return;
            }
            Log.e(str, "both switchbar and Switchpref are null");
        }
    }

    public final void showToastMsg(final int i) {
        new Handler(Looper.getMainLooper())
                .post(
                        new Runnable() { // from class:
                                         // com.samsung.android.settings.wifi.mobileap.WifiApSwitchEnabler.38
                            @Override // java.lang.Runnable
                            public final void run() {
                                Toast.makeText(
                                                WifiApSwitchEnabler.this.mActivity
                                                        .getApplicationContext(),
                                                i,
                                                0)
                                        .show();
                            }
                        });
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x012d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void startProvisioningIfNecessary() {
        /*
            Method dump skipped, instructions count: 313
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.mobileap.WifiApSwitchEnabler.startProvisioningIfNecessary():void");
    }

    /* JADX WARN: Removed duplicated region for block: B:107:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x004a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0078 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00bc A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x01ae A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x01be  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x01c7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateMHSSwitch() {
        /*
            Method dump skipped, instructions count: 521
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.mobileap.WifiApSwitchEnabler.updateMHSSwitch():void");
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.samsung.android.settings.wifi.mobileap.WifiApSwitchEnabler$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.samsung.android.settings.wifi.mobileap.WifiApSwitchEnabler$43] */
    public WifiApSwitchEnabler(
            FragmentActivity fragmentActivity,
            SecSwitchPreferenceScreen secSwitchPreferenceScreen) {
        Log.i(TAG, "WifiApSwitchEnabler SecTetherSettings");
        this.mActivity = fragmentActivity;
        this.mContext = fragmentActivity;
        Utils.initMHSFeature(fragmentActivity);
        this.mSwitchPref = secSwitchPreferenceScreen;
        int i = fragmentActivity.getResources().getConfiguration().uiMode;
        this.mDataSaverBackend = new DataSaverBackend(fragmentActivity);
        Utils.isTablet();
        SemWifiManager semWifiManager =
                (SemWifiManager) fragmentActivity.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        this.mSemWifiManager = semWifiManager;
        semWifiManager.getWifiApState();
        this.mAOSPWifiManager =
                (WifiManager) fragmentActivity.getSystemService(ImsProfile.PDN_WIFI);
        this.mWifiApConfiguration = new WifiApConfiguration(fragmentActivity);
        updateMHSSwitch();
        secSwitchPreferenceScreen.setOnPreferenceChangeListener(this);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class WifiApDataSaver implements DataSaverBackend.Listener {
        public WifiApDataSaver() {}

        @Override // com.android.settings.datausage.DataSaverBackend.Listener
        public final void onDataSaverChanged(boolean z) {
            if (z) {
                Log.e(WifiApSwitchEnabler.TAG, "Data saver is enabled");
                WifiApSwitchEnabler.this.updateMHSSwitch();
            }
        }

        @Override // com.android.settings.datausage.DataSaverBackend.Listener
        public final void onAllowlistStatusChanged(int i, boolean z) {}

        @Override // com.android.settings.datausage.DataSaverBackend.Listener
        public final void onDenylistStatusChanged(int i, boolean z) {}
    }
}
