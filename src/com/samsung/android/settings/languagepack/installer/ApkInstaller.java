package com.samsung.android.settings.languagepack.installer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.samsung.android.knox.zt.config.securelog.SignalSeverity;
import com.samsung.android.settings.languagepack.logger.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ApkInstaller {
    public final Context mContext;
    public final InstallerCallbackListener mListener;
    public final Map mPackages = new HashMap();
    public final AnonymousClass1 mCallbackReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.samsung.android.settings.languagepack.installer.ApkInstaller.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if (TextUtils.equals(action, "INSTALLER_CALLBACK")) {
                        int intExtra = intent.getIntExtra("android.content.pm.extra.STATUS", 1);
                        String stringExtra =
                                intent.getStringExtra("android.content.pm.extra.STATUS_MESSAGE");
                        String stringExtra2 =
                                intent.getStringExtra("android.content.pm.extra.PACKAGE_NAME");
                        Log.d(
                                "ApkInstaller",
                                "PackageInstallerListener: result ["
                                        + intExtra
                                        + "], message ["
                                        + stringExtra
                                        + "], packageName ["
                                        + stringExtra2
                                        + "]");
                        if (intExtra == 0) {
                            ApkInstaller.this.updateInstallResult(stringExtra2, "success");
                            return;
                        } else {
                            ApkInstaller.this.updateInstallResult(stringExtra2, "failure");
                            return;
                        }
                    }
                    if (TextUtils.equals(action, "UNINSTALLER_CALLBACK")) {
                        int intExtra2 = intent.getIntExtra("android.content.pm.extra.STATUS", 1);
                        String stringExtra3 =
                                intent.getStringExtra("android.content.pm.extra.PACKAGE_NAME");
                        Log.d(
                                "ApkInstaller",
                                "PackageInstallerListener: result ["
                                        + intExtra2
                                        + "], packageName ["
                                        + stringExtra3
                                        + "]");
                        if (intExtra2 == 0) {
                            ApkInstaller apkInstaller = ApkInstaller.this;
                            ((HashMap) apkInstaller.mPackages).put(stringExtra3, "success");
                            apkInstaller.notifyToListenerIfNeeded();
                        } else {
                            ApkInstaller apkInstaller2 = ApkInstaller.this;
                            ((HashMap) apkInstaller2.mPackages).put(stringExtra3, "failure");
                            apkInstaller2.notifyToListenerIfNeeded();
                        }
                    }
                }
            };
    public String mMode = SignalSeverity.NONE;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface InstallerCallbackListener {
        void installFinish();

        void uninstallFinish();
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.samsung.android.settings.languagepack.installer.ApkInstaller$1] */
    public ApkInstaller(Context context, InstallerCallbackListener installerCallbackListener) {
        this.mContext = context.getApplicationContext();
        this.mListener = installerCallbackListener;
    }

    public static String getPackageName(String str) {
        String substring = str.substring(0, str.lastIndexOf("."));
        Log.d("ApkInstaller", "getPackageName() : " + substring);
        return substring;
    }

    public final void notifyToListenerIfNeeded() {
        Iterator it = new ArrayList(((HashMap) this.mPackages).values()).iterator();
        while (it.hasNext()) {
            if (TextUtils.isEmpty((String) it.next())) {
                return;
            }
        }
        Log.d("ApkInstaller", "notifyToListenerIfNeeded() finish : " + this.mMode);
        boolean equals = TextUtils.equals(this.mMode, "install");
        InstallerCallbackListener installerCallbackListener = this.mListener;
        if (equals) {
            installerCallbackListener.installFinish();
        } else {
            installerCallbackListener.uninstallFinish();
        }
        this.mMode = SignalSeverity.NONE;
        this.mContext.unregisterReceiver(this.mCallbackReceiver);
    }

    public final void updateInstallResult(String str, String str2) {
        ((HashMap) this.mPackages).put(str, str2);
        notifyToListenerIfNeeded();
        if (str2 != "exception") {
            Context context = this.mContext;
            Set<String> stringSet =
                    context.getSharedPreferences("language_pack_delta_update", 0)
                            .getStringSet("language_pack_delta_update_fail_list", null);
            if (stringSet != null) {
                if (str != null && str.contains("_")) {
                    int lastIndexOf = str.lastIndexOf(95);
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < lastIndexOf; i++) {
                        sb.append(str.charAt(i));
                    }
                    str = sb.toString();
                }
                if (stringSet.isEmpty() || !stringSet.contains(str)) {
                    return;
                }
                stringSet.remove(str);
                SharedPreferences.Editor edit =
                        context.getSharedPreferences("language_pack_delta_update", 0).edit();
                edit.putStringSet("language_pack_delta_update_fail_list", stringSet);
                edit.apply();
            }
        }
    }
}
