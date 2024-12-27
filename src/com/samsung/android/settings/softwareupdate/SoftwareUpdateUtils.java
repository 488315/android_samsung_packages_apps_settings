package com.samsung.android.settings.softwareupdate;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.SemSystemProperties;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.secutil.Log;

import com.android.settings.Utils;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.settings.Rune;
import com.samsung.android.util.SemLog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SoftwareUpdateUtils {
    public static void checkNewSoftwareUpdate(Context context) {
        if (context == null) {
            Log.d("PrintSettingsFragment", "context is null");
            return;
        }
        Intent intent = new Intent("com.wssyncmldm.UserInitEntryActivity");
        intent.setPackage("com.wssyncmldm");
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            context.sendBroadcast(
                    new Intent("sec.fota.action.SOFTWARE_UPDATE")
                            .setFlags(268435488)
                            .setPackage("com.wssyncmldm"));
        }
    }

    public static int getDataSimSlotId(Context context) {
        float f;
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        try {
            f =
                    Float.parseFloat(
                            SemFloatingFeature.getInstance()
                                    .getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_OMC_VERSION"));
        } catch (NullPointerException | NumberFormatException unused) {
            f = 0.0f;
        }
        if (f >= 5.0d) {
            String str2 = SemSystemProperties.get("ro.multisim.simslotcount");
            try {
                if (!TextUtils.isEmpty(str2)) {
                    if (2 == Integer.parseInt(str2)) {
                        int defaultDataSubscriptionId =
                                SubscriptionManager.getDefaultDataSubscriptionId();
                        SubscriptionInfo activeSubscriptionInfo =
                                SubscriptionManager.from(context)
                                        .getActiveSubscriptionInfo(defaultDataSubscriptionId);
                        if (activeSubscriptionInfo == null) {
                            Log.d(
                                    "PrintSettingsFragment",
                                    "subscriptionInfo is null, so return sim slot 0");
                            return 0;
                        }
                        int simSlotIndex = activeSubscriptionInfo.getSimSlotIndex();
                        Log.d(
                                "PrintSettingsFragment",
                                "subscriptionID : "
                                        + defaultDataSubscriptionId
                                        + ", slotId : "
                                        + simSlotIndex);
                        return simSlotIndex;
                    }
                }
            } catch (NumberFormatException e) {
                SemLog.e("Rune", "NumberFormatException " + e);
            }
        }
        Log.d(
                "PrintSettingsFragment",
                "omc version is under 5 or device is not dual sim, so return 0");
        return 0;
    }

    public static int getFotaBadgeCount(Context context) {
        int intForUser =
                Settings.System.getIntForUser(
                        context.getContentResolver(), "badge_for_fota", 0, -2);
        Log.i("PrintSettingsFragment", "getFotaBadgeCount badgeCount : " + intForUser);
        return intForUser;
    }

    public static boolean isOTAUpgradeAllowed(Context context) {
        int enterprisePolicyEnabled =
                Utils.getEnterprisePolicyEnabled(
                        context,
                        "content://com.sec.knox.provider/RestrictionPolicy3",
                        "isOTAUpgradeAllowed",
                        null);
        Log.i("PrintSettingsFragment", "isOTAUpgradeAllowed : " + enterprisePolicyEnabled);
        return enterprisePolicyEnabled != 0;
    }

    public static boolean isVZWFotaNewEnabled(Context context) {
        PackageManager packageManager;
        String salesCode = SemSystemProperties.getSalesCode();
        if ((!"VZW".equals(salesCode) && !"VPP".equals(salesCode))
                || (packageManager = context.getPackageManager()) == null) {
            return false;
        }
        Intent intent = new Intent();
        intent.setClassName("com.samsung.sdm", "com.samsung.sdm.VZWSystemUpdatesLauncher");
        return packageManager.resolveActivity(intent, 131072) != null;
    }
}
