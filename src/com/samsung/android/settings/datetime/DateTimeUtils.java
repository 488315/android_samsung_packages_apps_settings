package com.samsung.android.settings.datetime;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.telephony.TelephonyManager;

import com.android.settings.Utils;

import com.samsung.android.settings.Rune;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class DateTimeUtils {
    public static boolean applyEDMDateTimeChangePolicy(Context context) {
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) context.getSystemService("device_policy");
        int enterprisePolicyEnabled =
                Utils.getEnterprisePolicyEnabled(
                        context,
                        "content://com.sec.knox.provider/DateTimePolicy",
                        "isDateTimeChangeEnalbed");
        if (enterprisePolicyEnabled != -1 && enterprisePolicyEnabled != 1) {
            return false;
        }
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        if ("CTC".equals(Utils.getSalesCode())) {
            return TelephonyManager.getDefault().getNetworkType() < 4;
        }
        devicePolicyManager.getAutoTimeRequired();
        return true;
    }
}
