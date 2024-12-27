package com.android.settings.backup;

import android.app.backup.IBackupManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Log;

import androidx.compose.ui.text.SaversKt$LocaleSaver$2$$ExternalSyntheticOutline0;

import java.util.List;
import java.util.TreeSet;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class PrivacySettingsUtils {
    public static boolean isInvisibleKey(Context context, String str) {
        boolean z;
        try {
            z =
                    IBackupManager.Stub.asInterface(ServiceManager.getService("backup"))
                            .isBackupServiceActive(UserHandle.myUserId());
        } catch (RemoteException unused) {
            Log.w(
                    "PrivacySettingsUtils",
                    "Failed querying backup manager service activity status. Assuming it is"
                        + " inactive.");
            z = false;
        }
        boolean z2 =
                context.getPackageManager().resolveContentProvider("com.google.settings", 0)
                        == null;
        TreeSet treeSet = new TreeSet();
        if (z2 || z) {
            treeSet.add("backup_inactive");
        }
        if (z2 || !z) {
            treeSet.add("backup_data");
            treeSet.add("auto_restore");
            treeSet.add("configure_account");
        }
        if (Log.isLoggable("PrivacySettingsUtils", 3)) {
            Log.d(
                    "PrivacySettingsUtils",
                    "keysToRemove size=" + treeSet.size() + " keysToRemove=" + treeSet);
        }
        return treeSet.contains(str);
    }

    public static Intent validatedActivityIntent(Context context, Intent intent, String str) {
        if (intent == null) {
            return intent;
        }
        List<ResolveInfo> queryIntentActivities =
                context.getPackageManager().queryIntentActivities(intent, 0);
        if (queryIntentActivities != null && !queryIntentActivities.isEmpty()) {
            return intent;
        }
        SaversKt$LocaleSaver$2$$ExternalSyntheticOutline0.m(
                "Backup ", str, " intent null fails to resolve; ignoring", "PrivacySettingsUtils");
        return null;
    }
}
