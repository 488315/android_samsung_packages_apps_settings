package com.samsung.android.settings.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.Log;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.appcompat.view.SeslContentViewInsetsCallback$$ExternalSyntheticOutline0;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;

import com.android.settings.R;
import com.android.settings.Utils;

import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;

import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class AccountUtils {
    public static final boolean SupportTwoPhone =
            SemCscFeature.getInstance().getBoolean("CscFeature_Common_SupportTwoPhoneService");

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.account.AccountUtils$1, reason: invalid class name */
    public final class AnonymousClass1 implements DialogInterface.OnClickListener {
        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.cancel();
        }
    }

    public static void addSamsungAccount(
            Context context, Fragment fragment, int i, int i2, int i3) {
        Account[] accountsByTypeAsUser =
                AccountManager.get(context)
                        .getAccountsByTypeAsUser("com.osp.app.signin", UserHandle.of(i3));
        if (accountsByTypeAsUser == null || accountsByTypeAsUser.length != 0) {
            return;
        }
        Intent intent = new Intent();
        if (i2 == 1) {
            intent.setAction("com.msc.action.samsungaccount.SIGNIN_POPUP");
        } else {
            intent.setAction("com.osp.app.signin.action.ADD_SAMSUNG_ACCOUNT");
        }
        intent.putExtra("client_id", "s5d189ajvs");
        intent.putExtra("mypackage", "com.samsung.android.scloud");
        intent.putExtra("OSP_VER", "OSP_02");
        intent.putExtra("MODE", "ADD_ACCOUNT");
        try {
            fragment.getActivity().startActivityForResultAsUser(intent, i, UserHandle.of(i3));
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkCarrierByodCondition() {
        String str;
        boolean z;
        String str2;
        String str3 = "NONE";
        boolean z2 = false;
        try {
            str2 = SystemProperties.get("ro.csc.sales_code", "NONE");
            try {
                str3 = SystemProperties.get("ro.boot.carrierid", "NONE");
                z = SystemProperties.getBoolean("persist.sys.omc_byod", false);
            } catch (IllegalArgumentException e) {
                e = e;
                str = str3;
                str3 = str2;
                e.printStackTrace();
                z = false;
                String str4 = str;
                str2 = str3;
                str3 = str4;
                if (!"VZW".equals(str2)) {}
                z2 = true;
                SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(
                        SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                "checkCarrierByodCondition: byod condition - carrierid:",
                                str3,
                                ", sales_code:",
                                str2,
                                ","),
                        z,
                        "AccountUtils");
                return z2;
            }
        } catch (IllegalArgumentException e2) {
            e = e2;
            str = "NONE";
        }
        if ((!"VZW".equals(str2) || "VPP".equals(str2)) && z) {
            z2 = true;
        }
        SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(
                SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                        "checkCarrierByodCondition: byod condition - carrierid:",
                        str3,
                        ", sales_code:",
                        str2,
                        ","),
                z,
                "AccountUtils");
        return z2;
    }

    public static boolean checkIsDeviceOwner(Context context) {
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) context.getSystemService("device_policy");
        return (devicePolicyManager == null || devicePolicyManager.getDeviceOwner() == null)
                ? false
                : true;
    }

    public static boolean checkSamsungBackup(Context context) {
        if (UserHandle.myUserId() != 0) {
            return false;
        }
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) context.getSystemService("device_policy");
        if (devicePolicyManager != null && devicePolicyManager.getDeviceOwner() != null) {
            return false;
        }
        try {
            PackageInfo packageInfo =
                    context.getPackageManager().getPackageInfo("com.samsung.android.scloud", 5);
            boolean z =
                    SemFloatingFeature.getInstance()
                            .getBoolean(
                                    "SEC_FLOATING_FEATURE_SAMSUNGCLOUD_ENABLE_SETTING_MENU", true);
            if (packageInfo.applicationInfo.enabled) {
                if (!checkCarrierByodCondition()) {
                    if (!supportSamsungCloud() || !z) {}
                }
                return true;
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return false;
    }

    public static boolean checkSamsungBackupAvailble(Context context) {
        boolean z;
        boolean z2 = false;
        try {
            if (context.getPackageManager()
                    .getPackageInfo("com.samsung.android.scloud", 5)
                    .applicationInfo
                    .enabled) {
                if (checkCarrierByodCondition()) {
                    Log.i("Utils", "carrier byod activated");
                    z = false;
                } else {
                    z = !supportSamsungCloud();
                }
                if (!z) {
                    z2 = true;
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (Utils.hasPackage(context, "com.samsung.android.app.samsungcloud.enabler")) {
            return true;
        }
        return z2;
    }

    public static boolean isChildAccount(Context context) {
        try {
            Bundle call =
                    context.getContentResolver()
                            .call(
                                    Uri.parse(
                                            "content://com.samsung.android.samsungaccount.accountmanagerprovider"),
                                    "isChildAccount",
                                    "s5d189ajvs",
                                    (Bundle) null);
            if (call == null) {
                Log.d("AccountUtils", "Result bundle is null");
                return false;
            }
            int i = call.getInt("result_code", 1);
            String string = call.getString("result_message", ApnSettings.MVNO_NONE);
            if (i != 0) {
                DialogFragment$$ExternalSyntheticOutline0.m(
                        "Failure message : resultMessage = ", string, "AccountUtils");
                return false;
            }
            if ("true".equals(string)) {
                Log.d("AccountUtils", "This account is a child account.");
                return true;
            }
            Log.d("AccountUtils", "This account is not a child account.");
            return false;
        } catch (Exception e) {
            Log.d("AccountUtils", "Exception Error : " + e.getMessage());
            return false;
        }
    }

    public static boolean isKnoxActivated(Context context) {
        if (context != null) {
            Iterator it =
                    ((SemPersonaManager) context.getSystemService("persona"))
                            .getKnoxIds(false)
                            .iterator();
            while (it.hasNext()) {
                int intValue = ((Integer) it.next()).intValue();
                if (SemPersonaManager.isKnoxId(intValue)
                        && !SemPersonaManager.isSecureFolderId(intValue)
                        && !SemPersonaManager.isAppSeparationUserId(intValue)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isSamsungAccountExists(Context context) {
        Account[] accountsByType =
                AccountManager.get(context).getAccountsByType("com.osp.app.signin");
        return accountsByType == null || accountsByType.length != 0;
    }

    public static boolean isSamsungAccountExistsAsUser(Context context, int i) {
        Account[] accountsByTypeAsUser =
                AccountManager.get(context)
                        .getAccountsByTypeAsUser("com.osp.app.signin", UserHandle.of(i));
        return accountsByTypeAsUser == null || accountsByTypeAsUser.length != 0;
    }

    public static boolean isSecureFolderActivated(Context context) {
        if (context != null) {
            Iterator it =
                    ((SemPersonaManager) context.getSystemService("persona"))
                            .getKnoxIds(false)
                            .iterator();
            while (it.hasNext()) {
                if (SemPersonaManager.isSecureFolderId(((Integer) it.next()).intValue())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void removeDualAppManagedProfiles(List list) {
        int i = 0;
        while (i < list.size()) {
            if (SemDualAppManager.isDualAppId(((UserHandle) list.get(i)).getIdentifier())) {
                list.remove(i);
                i--;
            }
            i++;
        }
    }

    public static void showDownloadSamsungCloudDialog(final Context context) {
        String string = context.getResources().getString(R.string.cloud_title);
        String string2 = context.getResources().getString(R.string.download_scloud_title, string);
        new AlertDialog.Builder(context)
                .setTitle(string2)
                .setMessage(
                        context.getResources().getString(R.string.download_scloud_message, string))
                .setCancelable(true)
                .setPositiveButton(
                        R.string.monotype_dialog_button,
                        new DialogInterface.OnClickListener() { // from class:
                            // com.samsung.android.settings.account.AccountUtils.2
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent;
                                try {
                                    intent =
                                            new Intent(
                                                    "android.intent.action.VIEW",
                                                    Uri.parse(
                                                            "samsungapps://ProductDetail/com.samsung.android.scloud"));
                                    try {
                                        intent.addFlags(335577120);
                                        if (intent.resolveActivity(context.getPackageManager())
                                                != null) {
                                            Log.i(
                                                    "AccountUtils",
                                                    "startMarket resolveActivity is not null, start"
                                                        + " market service, uri : "
                                                            + intent.toString());
                                            context.startActivity(intent);
                                            dialogInterface.dismiss();
                                        } else {
                                            Log.i(
                                                    "AccountUtils",
                                                    "null resolveActivity.try again via google"
                                                        + " play");
                                            Intent intent2 =
                                                    new Intent(
                                                            "android.intent.action.VIEW",
                                                            Uri.parse(
                                                                    "market://details?id=com.samsung.android.scloud"));
                                            try {
                                                intent2.addFlags(335577120);
                                                context.startActivity(intent2);
                                                dialogInterface.dismiss();
                                            } catch (ActivityNotFoundException e) {
                                                e = e;
                                                intent = intent2;
                                                StringBuilder sb =
                                                        new StringBuilder(
                                                                "linkToMarket got an error, uri :"
                                                                    + " ");
                                                sb.append(
                                                        intent != null ? intent.toString() : null);
                                                Log.i("AccountUtils", sb.toString());
                                                Log.e(
                                                        "AccountUtils",
                                                        "Can not link to market, Exception e: "
                                                                + e.getMessage());
                                                dialogInterface.dismiss();
                                            }
                                        }
                                    } catch (ActivityNotFoundException e2) {
                                        e = e2;
                                    }
                                } catch (ActivityNotFoundException e3) {
                                    e = e3;
                                    intent = null;
                                }
                            }
                        })
                .setNegativeButton(R.string.cancel, new AnonymousClass1())
                .show();
    }

    public static boolean supportSamsungCloud() {
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        String salesCode = Utils.getSalesCode();
        return ("VZW".equals(salesCode) || "VPP".equals(salesCode)) ? false : true;
    }
}
