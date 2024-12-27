package com.samsung.android.settings.general;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.view.textservice.TextServicesManager;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;

import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.settings.Rune;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class GeneralUtils {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.general.GeneralUtils$1, reason: invalid class name */
    public final class AnonymousClass1 implements DialogInterface.OnClickListener {
        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.cancel();
        }
    }

    public static Intent getContactUsIntent(Context context) {
        String packageName = context.getPackageName();
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("voc://view/contactUs"));
        intent.putExtra("packageName", packageName);
        intent.putExtra("appId", "be4f156x1l");
        intent.putExtra("appName", "Settings");
        return intent;
    }

    public static Bundle getTemporaryBackupBundle(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        try {
            Bundle bundle = new Bundle();
            bundle.putBoolean("isSkipCheckSupport", false);
            return contentResolver.call(
                    Uri.parse("content://com.samsung.android.scloud.statusprovider"),
                    "ctb_support",
                    "settings_reset",
                    bundle);
        } catch (Exception e) {
            Log.i("GeneralUtils", "isSupportTemporaryBackup exception " + e);
            return null;
        }
    }

    public static Intent getTemporaryBackupIntent(Context context) {
        Bundle temporaryBackupBundle = getTemporaryBackupBundle(context);
        if (temporaryBackupBundle == null) {
            return null;
        }
        if (temporaryBackupBundle.getBoolean("support", false)) {
            return (Intent) temporaryBackupBundle.getParcelable("targetIntent", Intent.class);
        }
        Log.i("GeneralUtils", "isSupportTemporaryBackup = false");
        return null;
    }

    public static boolean isEsimEmbedded(Context context) {
        if (context == null) {
            return false;
        }
        List availableSubscriptionInfoList =
                ((SubscriptionManager) context.getSystemService(SubscriptionManager.class))
                        .getAvailableSubscriptionInfoList();
        int size = availableSubscriptionInfoList != null ? availableSubscriptionInfoList.size() : 0;
        for (int i = 0; i < size; i++) {
            SubscriptionInfo subscriptionInfo =
                    (SubscriptionInfo) availableSubscriptionInfoList.get(i);
            boolean z = subscriptionInfo.getProfileClass() == 2;
            if (subscriptionInfo.isEmbedded() && z) {
                Log.i("GeneralUtils", "eSim embedded");
                return true;
            }
        }
        Log.i("GeneralUtils", "eSim not embedded");
        return false;
    }

    public static boolean isInstalledAnySpellCheckerPackage(Context context) {
        List queryIntentServicesAsUser =
                context.getPackageManager()
                        .queryIntentServicesAsUser(
                                new Intent("android.service.textservice.SpellCheckerService"),
                                128,
                                UserHandle.myUserId());
        int size = queryIntentServicesAsUser.size();
        for (int i = 0; i < size; i++) {
            ServiceInfo serviceInfo = ((ResolveInfo) queryIntentServicesAsUser.get(i)).serviceInfo;
            ComponentName componentName =
                    new ComponentName(serviceInfo.packageName, serviceInfo.name);
            if (!"android.permission.BIND_TEXT_SERVICE".equals(serviceInfo.permission)) {
                Log.w(
                        "InputMethodAndLanguageSettings",
                        "Skipping text service "
                                + componentName
                                + ": it does not require the permission"
                                + " android.permission.BIND_TEXT_SERVICE");
            } else if (!"com.sec.android.inputmethod".equals(serviceInfo.packageName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNAEsimResetConcept() {
        return "dsds".equals(SystemProperties.get("persist.radio.multisim.config"))
                && (Rune.isUSA() || "CA".equals(Utils.readCountryCode()));
    }

    public static boolean isResetEnabled(Context context) {
        if (Rune.isShopDemo(context) || Utils.isDesktopModeEnabled(context)) {
            return false;
        }
        SemDesktopModeManager semDesktopModeManager =
                (SemDesktopModeManager) context.getSystemService("desktopmode");
        return !(semDesktopModeManager != null ? semDesktopModeManager.isDeviceConnected() : false);
    }

    public static boolean isSupportSpellCheckerService(Context context) {
        if (context == null) {
            return false;
        }
        TextServicesManager textServicesManager =
                (TextServicesManager) context.getSystemService("textservices");
        boolean isInstalledAnySpellCheckerPackage = isInstalledAnySpellCheckerPackage(context);
        boolean z =
                SemCscFeature.getInstance()
                        .getBoolean("CscFeature_Sip_SupportSpellCheckerSettingMenu", false);
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "isInstalledAnySpellCheckerPackage = ",
                "GeneralUtils",
                isInstalledAnySpellCheckerPackage);
        if (!isInstalledAnySpellCheckerPackage || textServicesManager == null) {
            Log.d("GeneralUtils", "cannot find spellcheckerinfo, tsm = " + textServicesManager);
            return false;
        }
        if (z && Utils.hasPackage(context, "com.samsung.android.spellchecker")) {
            return context.getResources().getBoolean(R.bool.config_show_spellcheckers_settings);
        }
        InputMethodManager inputMethodManager =
                (InputMethodManager) context.getSystemService("input_method");
        boolean isCurrentInputMethodAsSamsungKeyboard =
                inputMethodManager != null
                        ? inputMethodManager.isCurrentInputMethodAsSamsungKeyboard()
                        : false;
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "isSamsungKeyboard = ", "GeneralUtils", isCurrentInputMethodAsSamsungKeyboard);
        if (isCurrentInputMethodAsSamsungKeyboard) {
            return false;
        }
        return context.getResources().getBoolean(R.bool.config_show_spellcheckers_settings);
    }
}
