package com.samsung.android.settings.activekey;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class ActiveKeyInfo {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ActiveKeyAppInfo {
        public String mDB;
        public boolean mIsEnabled;
        public CharSequence mLabel;
    }

    public static ActiveKeyAppInfo getActiveKeyAppInfo(FragmentActivity fragmentActivity, int i) {
        boolean z = true;
        String string =
                i != 1
                        ? i != 2
                                ? i != 3
                                        ? i != 4
                                                ? Settings.System.getString(
                                                        fragmentActivity.getContentResolver(),
                                                        "short_press_app")
                                                : Settings.System.getString(
                                                        fragmentActivity.getContentResolver(),
                                                        "xcover_top_long_press_app")
                                        : Settings.System.getString(
                                                fragmentActivity.getContentResolver(),
                                                "xcover_top_short_press_app")
                                : Settings.System.getString(
                                        fragmentActivity.getContentResolver(), "double_press_app")
                        : Settings.System.getString(
                                fragmentActivity.getContentResolver(), "long_press_app");
        Log.d("ActiveKeyInfo", "Press Type : " + i + "/ app : " + string);
        if (string == null || ApnSettings.MVNO_NONE.equals(string)) {
            return null;
        }
        int indexOf = string.indexOf(47, 0);
        String substring = string.substring(0, indexOf);
        String substring2 = string.substring(indexOf + 1, string.length());
        ActiveKeyAppInfo activeKeyAppInfo = new ActiveKeyAppInfo();
        activeKeyAppInfo.mLabel = null;
        activeKeyAppInfo.mIsEnabled = true;
        activeKeyAppInfo.mDB = string;
        UsefulfeatureUtils.hasActiveKey();
        UsefulfeatureUtils.hasActiveKey();
        try {
            activeKeyAppInfo.mLabel =
                    fragmentActivity
                            .getPackageManager()
                            .getActivityInfo(new ComponentName(substring, substring2), 512)
                            .loadLabel(fragmentActivity.getPackageManager());
            activeKeyAppInfo.mIsEnabled =
                    UsefulfeatureUtils.isEnabledPackage(fragmentActivity, substring)
                            && UsefulfeatureUtils.isEnabledComponent(
                                    fragmentActivity, new ComponentName(substring, substring2));
            Log.d(
                    "ActiveKeyInfo",
                    "Press Type : "
                            + i
                            + "/ app : "
                            + ((Object) activeKeyAppInfo.mLabel)
                            + " / "
                            + activeKeyAppInfo.mIsEnabled);
            try {
                activeKeyAppInfo.mLabel =
                        fragmentActivity
                                .getPackageManager()
                                .getActivityInfo(new ComponentName(substring, substring2), 128)
                                .loadLabel(fragmentActivity.getPackageManager());
                if (!UsefulfeatureUtils.isEnabledPackage(fragmentActivity, substring)
                        || !UsefulfeatureUtils.isEnabledComponent(
                                fragmentActivity, new ComponentName(substring, substring2))) {
                    z = false;
                }
                activeKeyAppInfo.mIsEnabled = z;
                Log.d(
                        "ActiveKeyInfo",
                        "Press Type : "
                                + i
                                + "/ app : "
                                + ((Object) activeKeyAppInfo.mLabel)
                                + " / "
                                + activeKeyAppInfo.mIsEnabled);
            } catch (PackageManager.NameNotFoundException unused) {
                Log.d("ActiveKeyInfo", "cannot find app name !");
            }
        } catch (PackageManager.NameNotFoundException unused2) {
            Log.d("ActiveKeyInfo", "cannot find app name !");
        }
        return activeKeyAppInfo;
    }

    public static String getActiveKeyDBKey(int i) {
        return i != 1
                ? i != 2
                        ? i != 3
                                ? i != 4 ? "short_press_app" : "xcover_top_long_press_app"
                                : "xcover_top_short_press_app"
                        : "double_press_app"
                : "long_press_app";
    }

    public static boolean getPressActionState(Context context, int i) {
        String string =
                Settings.System.getString(context.getContentResolver(), getActiveKeyDBKey(i));
        return (string == null || ApnSettings.MVNO_NONE.equals(string)) ? false : true;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0023  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0061 A[Catch: RemoteException -> 0x0050, TryCatch #1 {RemoteException -> 0x0050, blocks: (B:14:0x0029, B:16:0x0032, B:18:0x003a, B:21:0x0043, B:24:0x0054, B:26:0x0061, B:27:0x0064), top: B:13:0x0029 }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0091 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void setExtraKeyCustomizationInfo(int r11, java.lang.String r12, boolean r13) {
        /*
            java.lang.String r0 = "setExtraKeyCustomizationInfo startActivity intent="
            r1 = 1
            r2 = 1015(0x3f7, float:1.422E-42)
            r3 = 4
            r4 = 3
            if (r11 == r1) goto L1d
            r5 = 2
            if (r11 == r5) goto L1b
            r5 = 1079(0x437, float:1.512E-42)
            if (r11 == r4) goto L19
            if (r11 == r3) goto L16
            r8 = r2
            r6 = r4
            goto L1f
        L16:
            r6 = r3
        L17:
            r8 = r5
            goto L1f
        L19:
            r6 = r4
            goto L17
        L1b:
            r3 = 8
        L1d:
            r8 = r2
            r6 = r3
        L1f:
            java.lang.String r11 = "ActiveKeyInfo"
            if (r13 == 0) goto L91
            boolean r13 = android.text.TextUtils.isEmpty(r12)
            if (r13 != 0) goto Lb3
            java.lang.String r13 = "torch/torch"
            boolean r13 = r13.equals(r12)     // Catch: android.os.RemoteException -> L50
            if (r13 != 0) goto L52
            java.lang.String r13 = "home/home"
            boolean r13 = r13.equals(r12)     // Catch: android.os.RemoteException -> L50
            if (r13 != 0) goto L52
            java.lang.String r13 = "back/back"
            boolean r13 = r13.equals(r12)     // Catch: android.os.RemoteException -> L50
            if (r13 == 0) goto L43
            goto L52
        L43:
            java.lang.String r13 = "quickMessageSender/quickMessageSender"
            boolean r13 = r13.equals(r12)     // Catch: android.os.RemoteException -> L50
            if (r13 == 0) goto L4e
            r9 = r4
            goto L54
        L4e:
            r9 = r1
            goto L54
        L50:
            r11 = move-exception
            goto L8d
        L52:
            r1 = 0
            goto L4e
        L54:
            android.content.ComponentName r12 = android.content.ComponentName.unflattenFromString(r12)     // Catch: android.os.RemoteException -> L50
            android.content.Intent r10 = new android.content.Intent     // Catch: android.os.RemoteException -> L50
            java.lang.String r13 = "android.intent.action.MAIN"
            r10.<init>(r13)     // Catch: android.os.RemoteException -> L50
            if (r12 == 0) goto L64
            r10.setComponent(r12)     // Catch: android.os.RemoteException -> L50
        L64:
            java.lang.String r12 = "android.intent.category.LAUNCHER"
            r10.addCategory(r12)     // Catch: android.os.RemoteException -> L50
            r12 = 270532608(0x10200000, float:3.1554436E-29)
            r10.addFlags(r12)     // Catch: android.os.RemoteException -> L50
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch: android.os.RemoteException -> L50
            r12.<init>(r0)     // Catch: android.os.RemoteException -> L50
            r12.append(r10)     // Catch: android.os.RemoteException -> L50
            java.lang.String r12 = r12.toString()     // Catch: android.os.RemoteException -> L50
            android.util.Log.i(r11, r12)     // Catch: android.os.RemoteException -> L50
            com.samsung.android.view.SemWindowManager$KeyCustomizationInfo r11 = new com.samsung.android.view.SemWindowManager$KeyCustomizationInfo     // Catch: android.os.RemoteException -> L50
            r7 = 1103(0x44f, float:1.546E-42)
            r5 = r11
            r5.<init>(r6, r7, r8, r9, r10)     // Catch: android.os.RemoteException -> L50
            android.view.IWindowManager r12 = android.view.WindowManagerGlobal.getWindowManagerService()     // Catch: android.os.RemoteException -> L50
            r12.putKeyCustomizationInfo(r11)     // Catch: android.os.RemoteException -> L50
            goto Lb3
        L8d:
            r11.printStackTrace()
            goto Lb3
        L91:
            android.view.IWindowManager r12 = android.view.WindowManagerGlobal.getWindowManagerService()     // Catch: android.os.RemoteException -> La1
            r13 = 1103(0x44f, float:1.546E-42)
            r12.removeKeyCustomizationInfo(r13, r6, r8)     // Catch: android.os.RemoteException -> La1
            java.lang.String r12 = "setExtraKeyCustomizationInfo removeKeyCustomizationInfo"
            android.util.Log.i(r11, r12)     // Catch: android.os.RemoteException -> La1
            goto Lb3
        La1:
            r12 = move-exception
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            java.lang.String r0 = "failed to removeKeyCustomizationInfo "
            r13.<init>(r0)
            r13.append(r12)
            java.lang.String r12 = r13.toString()
            android.util.Log.e(r11, r12)
        Lb3:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.activekey.ActiveKeyInfo.setExtraKeyCustomizationInfo(int,"
                    + " java.lang.String, boolean):void");
    }
}
