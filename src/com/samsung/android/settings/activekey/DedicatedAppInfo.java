package com.samsung.android.settings.activekey;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManagerGlobal;

import com.android.settings.Utils;
import com.android.settings.applications.AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0;

import com.samsung.android.knox.custom.CustomDeviceManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;
import com.samsung.android.view.SemWindowManager;
import com.sec.ims.volte2.data.VolteConstants;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class DedicatedAppInfo {
    public static String getDedicatedApp(Context context, int i) {
        return Settings.System.getString(context.getContentResolver(), getDedicatedAppDBKey(i));
    }

    public static String getDedicatedAppDBKey(int i) {
        return (i == 0 || i == 1)
                ? "dedicated_app_xcover"
                : i != 2
                        ? i != 3 ? ApnSettings.MVNO_NONE : "dedicated_app_side"
                        : "dedicated_app_top";
    }

    public static boolean getDedicatedAppState(Context context, int i) {
        String string =
                Settings.System.getString(context.getContentResolver(), getDedicatedAppDBKey(i));
        return (string == null || ApnSettings.MVNO_NONE.equals(string)) ? false : true;
    }

    public static boolean getDedicatedAppSwitch(Context context, int i) {
        return Settings.System.getInt(
                        context.getContentResolver(),
                        (i == 0 || i == 1)
                                ? "dedicated_app_xcover_switch"
                                : i != 2
                                        ? i != 3
                                                ? ApnSettings.MVNO_NONE
                                                : "dedicated_app_side_switch"
                                        : "dedicated_app_top_switch",
                        0)
                != 0;
    }

    public static synchronized ArrayList loadAppList(final Context context, int i) {
        ArrayList arrayList;
        synchronized (DedicatedAppInfo.class) {
            try {
                arrayList = new ArrayList();
                arrayList.clear();
                try {
                    List<ApplicationInfo> installedApplications =
                            context.getPackageManager().getInstalledApplications(128);
                    Log.secD(
                            "DedicatedAppInfo",
                            " installedApps.size : " + installedApplications.size());
                    if (i == 3) {
                        for (ApplicationInfo applicationInfo : installedApplications) {
                            Bundle bundle = applicationInfo.metaData;
                            if (bundle != null
                                    && bundle.getBoolean(
                                            "com.samsung.android.knox.intent.action.addon.SIDE_KEY_DELTA",
                                            false)
                                    && !arrayList.contains(applicationInfo.packageName)) {
                                arrayList.add(applicationInfo.packageName);
                            }
                        }
                    } else {
                        for (ApplicationInfo applicationInfo2 : installedApplications) {
                            Bundle bundle2 = applicationInfo2.metaData;
                            if (bundle2 != null
                                    && bundle2.getBoolean(
                                            "com.samsung.android.knox.intent.action.HARD_KEY_PRESS",
                                            false)
                                    && !arrayList.contains(applicationInfo2.packageName)
                                    && !"com.att.eptt".equals(applicationInfo2.packageName)) {
                                arrayList.add(applicationInfo2.packageName);
                            }
                        }
                    }
                    arrayList.sort(
                            new Comparator() { // from class:
                                               // com.samsung.android.settings.activekey.DedicatedAppInfo.1
                                public final Collator sCollator = Collator.getInstance();

                                @Override // java.util.Comparator
                                public final int compare(Object obj, Object obj2) {
                                    return this.sCollator.compare(
                                            Utils.getApplicationLabel(context, (String) obj),
                                            Utils.getApplicationLabel(context, (String) obj2));
                                }
                            });
                    if (i != 3) {
                        if (UsefulfeatureUtils.hasVzwPttEnable(context)) {
                            arrayList.add(0, "com.verizon.pushtotalkplus");
                        } else if (UsefulfeatureUtils.hasEPttEnable(context)) {
                            arrayList.add(0, "com.att.eptt");
                        } else if (UsefulfeatureUtils.hasBMCPttEnable(context)) {
                            arrayList.add(0, "com.bell.ptt");
                        } else if (UsefulfeatureUtils.hasTMOPttEnable(context)) {
                            arrayList.add(0, "com.sprint.sdcplus");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public static void saveDedicatedApp(Context context, int i, String str) {
        Settings.System.putString(context.getContentResolver(), getDedicatedAppDBKey(i), str);
        Log.d("DedicatedAppInfo", "[save] Press Type : " + i + "/ app : " + str);
        setB2BDeltaKeyCustomizationInfo(context, i, str, TextUtils.isEmpty(str) ^ true);
    }

    public static void saveDedicatedAppLabel(Context context, int i, String str) {
        Settings.System.putString(
                context.getContentResolver(),
                (i == 0 || i == 1)
                        ? "dedicated_app_label_xcover"
                        : i != 2
                                ? i != 3 ? ApnSettings.MVNO_NONE : "dedicated_app_label_side"
                                : "dedicated_app_label_top",
                str);
        StringBuilder sb = new StringBuilder("[save] Press Type : ");
        sb.append(i);
        AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0.m(
                sb, "/ app label : ", str, "DedicatedAppInfo");
    }

    public static void setB2BDeltaKeyCustomizationInfo(
            Context context, int i, String str, boolean z) {
        int i2;
        int i3;
        int i4;
        if (i == 3) {
            if (z) {
                try {
                    Intent intent = new Intent(CustomDeviceManager.ACTION_HARD_KEY_REPORT);
                    intent.addFlags(16777216);
                    intent.putExtra("com.samsung.android.knox.intent.extra.KEY_CODE", 26);
                    intent.setPackage(str);
                    Log.i(
                            "DedicatedAppInfo",
                            "setB2BDeltaKeyCustomizationInfo side key sendbroadcast intent="
                                    + intent);
                    WindowManagerGlobal.getWindowManagerService()
                            .putKeyCustomizationInfo(
                                    new SemWindowManager.KeyCustomizationInfo(
                                            3, 951, 26, 2, intent));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    WindowManagerGlobal.getWindowManagerService()
                            .removeKeyCustomizationInfo(951, 3, 26);
                    Log.i(
                            "DedicatedAppInfo",
                            "setB2BDeltaKeyCustomizationInfo side key removeKeyCustomizationInfo");
                } catch (RemoteException e2) {
                    Log.e("DedicatedAppInfo", "failed to removeKeyCustomizationInfo " + e2);
                }
            }
            UsefulfeatureUtils.setSideKeyCustomizationInfo(context, 1, !z);
            return;
        }
        if (i != 2) {
            i3 = 0;
            i2 = 1015;
            i4 = 1;
        } else {
            i2 = 1079;
            i3 = 3;
            i4 = 4;
        }
        if (!z) {
            if (ActiveKeyInfo.getPressActionState(context, i3)) {
                ActiveKeyInfo.setExtraKeyCustomizationInfo(
                        i3,
                        Settings.System.getString(
                                context.getContentResolver(), ActiveKeyInfo.getActiveKeyDBKey(i3)),
                        true);
            }
            try {
                WindowManagerGlobal.getWindowManagerService()
                        .removeKeyCustomizationInfo(951, 3, i2);
                Log.i(
                        "DedicatedAppInfo",
                        "setB2BDeltaKeyCustomizationInfo removeKeyCustomizationInfo");
            } catch (RemoteException e3) {
                Log.e("DedicatedAppInfo", "failed to removeKeyCustomizationInfo " + e3);
            }
            int i5 = i4;
            if (ActiveKeyInfo.getPressActionState(context, i5)) {
                ActiveKeyInfo.setExtraKeyCustomizationInfo(
                        i5,
                        Settings.System.getString(
                                context.getContentResolver(), ActiveKeyInfo.getActiveKeyDBKey(i5)),
                        true);
                return;
            }
            return;
        }
        if (str == null || str.isEmpty()) {
            return;
        }
        if (str.equalsIgnoreCase("com.att.firstnet.grey")) {
            try {
                Intent intent2 =
                        i2 == 1015
                                ? new Intent(
                                        "com.mcx.intent.action.CRITICAL_COMMUNICATION_CONTROL_KEY")
                                : i2 == 1079
                                        ? new Intent(
                                                "com.mcx.intent.action.CRITICAL_COMMUNICATION_SOS_KEY")
                                        : null;
                if (intent2 != null) {
                    intent2.setComponent(
                            new ComponentName(
                                    "com.att.firstnet.grey",
                                    "com.samsung.android.sptt.keyevent.KeyEventService"));
                    Log.i(
                            "DedicatedAppInfo",
                            "setB2BDeltaKeyCustomizationInfo MCPTT startServiceAsUser intent="
                                    + intent2);
                    WindowManagerGlobal.getWindowManagerService()
                            .putKeyCustomizationInfo(
                                    new SemWindowManager.KeyCustomizationInfo(
                                            3, 951, i2, 3, intent2));
                }
            } catch (RemoteException e4) {
                e4.printStackTrace();
            }
        } else if (str.equalsIgnoreCase("com.att.eptt")
                || str.equalsIgnoreCase("com.sprint.sdcplus")
                || str.equalsIgnoreCase("com.verizon.pushtotalkplus")
                || str.equalsIgnoreCase("com.bell.ptt")) {
            try {
                Intent intent3 =
                        i2 == 1015
                                ? new Intent(
                                        "com.mcx.intent.action.CRITICAL_COMMUNICATION_CONTROL_KEY")
                                : i2 == 1079
                                        ? new Intent(
                                                "com.mcx.intent.action.CRITICAL_COMMUNICATION_SOS_KEY")
                                        : null;
                if (intent3 != null) {
                    intent3.addFlags(32);
                    intent3.setPackage(str);
                    Log.i(
                            "DedicatedAppInfo",
                            "setB2BDeltaKeyCustomizationInfo sendbroadcast intent = "
                                    + intent3
                                    + " / package = "
                                    + str);
                    WindowManagerGlobal.getWindowManagerService()
                            .putKeyCustomizationInfo(
                                    new SemWindowManager.KeyCustomizationInfo(
                                            3, 951, i2, 2, intent3));
                }
            } catch (RemoteException e5) {
                e5.printStackTrace();
            }
        } else {
            try {
                Intent intent4 = new Intent(CustomDeviceManager.ACTION_HARD_KEY_REPORT);
                intent4.addFlags(16777216);
                intent4.putExtra("com.samsung.android.knox.intent.extra.KEY_CODE", i2);
                intent4.setPackage(str);
                Log.i(
                        "DedicatedAppInfo",
                        "setB2BDeltaKeyCustomizationInfo sendbroadcast intent=" + intent4);
                WindowManagerGlobal.getWindowManagerService()
                        .putKeyCustomizationInfo(
                                new SemWindowManager.KeyCustomizationInfo(3, 951, i2, 2, intent4));
            } catch (RemoteException e6) {
                e6.printStackTrace();
            }
        }
        try {
            WindowManagerGlobal.getWindowManagerService()
                    .removeKeyCustomizationInfo(
                            VolteConstants.ErrorCode.CALL_SESSION_TIMEOUT, 3, i2);
            WindowManagerGlobal.getWindowManagerService()
                    .removeKeyCustomizationInfo(
                            VolteConstants.ErrorCode.CALL_SESSION_TIMEOUT, 4, i2);
            Log.i("DedicatedAppInfo", "setExtraKeyCustomizationInfo removeKeyCustomizationInfo");
        } catch (RemoteException e7) {
            Log.e("DedicatedAppInfo", "failed to removeKeyCustomizationInfo " + e7);
        }
    }

    public static void setDedicatedAppSwitch(Context context, int i, boolean z) {
        Settings.System.putInt(
                context.getContentResolver(),
                (i == 0 || i == 1)
                        ? "dedicated_app_xcover_switch"
                        : i != 2
                                ? i != 3 ? ApnSettings.MVNO_NONE : "dedicated_app_side_switch"
                                : "dedicated_app_top_switch",
                z ? 1 : 0);
        Log.d("DedicatedAppInfo", "[save] Press Type : " + i + "/ switch : " + z);
    }
}
