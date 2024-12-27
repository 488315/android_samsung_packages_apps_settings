package com.samsung.android.settings.nfc;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Debug;
import android.os.SystemProperties;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.settings.Utils;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.Rune;
import com.samsung.android.util.SemLog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class NfcReceiver extends BroadcastReceiver {
    public static final /* synthetic */ int $r8$clinit = 0;

    static {
        Debug.semIsProductDev();
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(final Context context, Intent intent) {
        String action = intent.getAction();
        if (action == null) {
            return;
        }
        SemLog.d("NfcReceiver", "Action: ".concat(action));
        if (action.equals("android.intent.action.SIM_STATE_CHANGED")) {
            if (Rune.isSupportEUiccSwp(context)) {
                new Thread(
                                new Runnable() { // from class:
                                                 // com.samsung.android.settings.nfc.NfcReceiver$$ExternalSyntheticLambda1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        NfcReceiver nfcReceiver = NfcReceiver.this;
                                        Context context2 = context;
                                        int i = NfcReceiver.$r8$clinit;
                                        nfcReceiver.getClass();
                                        if (context2 == null) {
                                            return;
                                        }
                                        SemLog.i("NfcReceiver", "check for euicc");
                                        SubscriptionManager subscriptionManager =
                                                (SubscriptionManager)
                                                        context2.getSystemService(
                                                                "telephony_subscription_service");
                                        for (int i2 = 1;
                                                i2
                                                        <= subscriptionManager
                                                                .getAllSubscriptionInfoList()
                                                                .size();
                                                i2++) {
                                            SubscriptionInfo activeSubscriptionInfo =
                                                    subscriptionManager.getActiveSubscriptionInfo(
                                                            i2);
                                            if (activeSubscriptionInfo != null) {
                                                if (((TelephonyManager)
                                                                        context2.getSystemService(
                                                                                "phone"))
                                                                .getSimState(
                                                                        activeSubscriptionInfo
                                                                                .getSimSlotIndex())
                                                        != 5) {
                                                    SemLog.d(
                                                            "NfcReceiver",
                                                            "sim not ready for slot == "
                                                                    + activeSubscriptionInfo
                                                                            .getSimSlotIndex());
                                                } else if (activeSubscriptionInfo.isEmbedded()) {
                                                    SemLog.d(
                                                            "NfcReceiver",
                                                            "active esim found on slot == "
                                                                    + activeSubscriptionInfo
                                                                            .getSimSlotIndex());
                                                    context2.getSharedPreferences(
                                                                    "nfcSettingsPrefs", 0)
                                                            .edit()
                                                            .putBoolean("showNfcEuiccMenu", true)
                                                            .apply();
                                                    return;
                                                }
                                            }
                                        }
                                    }
                                })
                        .start();
            }
        } else if (action.equals("android.intent.action.BOOT_COMPLETED")) {
            new Thread(
                            new Runnable() { // from class:
                                // com.samsung.android.settings.nfc.NfcReceiver$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    Context context2 = context;
                                    int i = NfcReceiver.$r8$clinit;
                                    SemLog.i("NfcReceiver", "NfcReceiver - START");
                                    if (!context2.getPackageManager()
                                            .hasSystemFeature("android.hardware.nfc")) {
                                        StringBuilder sb = Utils.sBuilder;
                                        if (!"factory"
                                                .equalsIgnoreCase(
                                                        SystemProperties.get(
                                                                "ro.factory.factory_binary",
                                                                "Unknown"))) {
                                            Utils.disableComponent(
                                                    context2,
                                                    new ComponentName(
                                                            KnoxVpnPolicyConstants
                                                                    .ANDROID_SETTINGS_PKG,
                                                            "com.samsung.android.settings.nfc.NfcReceiver"));
                                            Log.d(
                                                    "Settings",
                                                    "Removing nfc components from Manifest file");
                                            Utils.disableComponent(
                                                    context2,
                                                    new ComponentName(
                                                            KnoxVpnPolicyConstants
                                                                    .ANDROID_SETTINGS_PKG,
                                                            "com.android.settings.Settings$NfcSettingsActivity"));
                                            Utils.disableComponent(
                                                    context2,
                                                    new ComponentName(
                                                            KnoxVpnPolicyConstants
                                                                    .ANDROID_SETTINGS_PKG,
                                                            "com.android.settings.Settings$PaymentSettingsActivity"));
                                            Utils.disableComponent(
                                                    context2,
                                                    new ComponentName(
                                                            KnoxVpnPolicyConstants
                                                                    .ANDROID_SETTINGS_PKG,
                                                            "com.android.settings.Settings$OtherSettingsActivity"));
                                            Utils.disableComponent(
                                                    context2,
                                                    new ComponentName(
                                                            KnoxVpnPolicyConstants
                                                                    .ANDROID_SETTINGS_PKG,
                                                            "com.android.settings.Settings$AndroidBeamSettingsActivity"));
                                            Utils.disableComponent(
                                                    context2,
                                                    new ComponentName(
                                                            KnoxVpnPolicyConstants
                                                                    .ANDROID_SETTINGS_PKG,
                                                            "com.android.settings.Settings$NfcAdvancedRoutingSettingActivity"));
                                            Utils.disableComponent(
                                                    context2,
                                                    new ComponentName(
                                                            KnoxVpnPolicyConstants
                                                                    .ANDROID_SETTINGS_PKG,
                                                            "com.samsung.android.settings.nfc.PaymentDefaultDialog"));
                                            Utils.disableComponent(
                                                    context2,
                                                    new ComponentName(
                                                            KnoxVpnPolicyConstants
                                                                    .ANDROID_SETTINGS_PKG,
                                                            "com.samsung.android.settings.nfc.NfcForegroundDialog"));
                                        }
                                    }
                                    if (Rune.isJapanModel()) {
                                        Utils.disableComponent(
                                                context2,
                                                new ComponentName(
                                                        KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                                                        "com.android.settings.Settings$NfcOsaifukeitaiSettingsActivity"));
                                        Utils.disableComponent(
                                                context2,
                                                new ComponentName(
                                                        KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                                                        "com.android.settings.Settings$PaymentSettingsActivity"));
                                        Utils.disableComponent(
                                                context2,
                                                new ComponentName(
                                                        KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                                                        "com.android.settings.Settings$OtherSettingsActivity"));
                                    } else {
                                        Utils.disableComponent(
                                                context2,
                                                new ComponentName(
                                                        KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                                                        "com.android.settings.Settings$NfcOsaifukeitaiSettingsActivity"));
                                        Utils.disableComponent(
                                                context2,
                                                new ComponentName(
                                                        KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                                                        "com.android.settings.Settings$PaymentDCMSettingsActivity"));
                                    }
                                    SemLog.i("NfcReceiver", "NfcReceiver - END");
                                }
                            })
                    .start();
        }
    }
}
