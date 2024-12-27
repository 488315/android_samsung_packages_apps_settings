package com.samsung.android.settings.galaxysystemupdate.apps.data;

import android.util.Log;

import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.io.File;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class GalaxySystemAppsInfoManager {
    /* JADX WARN: Code restructure failed: missing block: B:74:0x017b, code lost:

       if (r7.getVersion() == r5.getLongVersionCode()) goto L18;
    */
    /* JADX WARN: Removed duplicated region for block: B:15:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x01a2  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x01e7 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x01eb A[ADDED_TO_REGION, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x01d0 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x013a  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0147  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.ArrayList getActiveApexList(android.content.Context r19, boolean r20) {
        /*
            Method dump skipped, instructions count: 584
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.galaxysystemupdate.apps.data.GalaxySystemAppsInfoManager.getActiveApexList(android.content.Context,"
                    + " boolean):java.util.ArrayList");
    }

    public static String getActiveFilePath(String str) {
        File[] listFiles = new File("/data/apex/active").listFiles();
        if (listFiles == null) {
            Log.d("System Files", "files: null");
            return ApnSettings.MVNO_NONE;
        }
        Preference$$ExternalSyntheticOutline0.m(
                new StringBuilder("Size: "), listFiles.length, "GalaxySystemAppsInfoManager");
        for (File file : listFiles) {
            String name = file.getName();
            Log.d("GalaxySystemAppsInfoManager", "Name : " + name);
            if (name.contains(str)) {
                return "/data/apex/active/".concat(name);
            }
        }
        return ApnSettings.MVNO_NONE;
    }

    public static String getPreloadFilePath(String str) {
        File[] listFiles = new File("/vendor/apex").listFiles();
        if (listFiles != null) {
            Preference$$ExternalSyntheticOutline0.m(
                    new StringBuilder("Size: "), listFiles.length, "System Files");
            for (File file : listFiles) {
                String name = file.getName();
                Log.d("GalaxySystemAppsInfoManager", "Name : " + name);
                if (name.contains(str)) {
                    return "/vendor/apex/".concat(name);
                }
            }
        } else {
            Log.d("System Files", "files: null");
        }
        File[] listFiles2 = new File("/system/apex").listFiles();
        if (listFiles2 == null) {
            return ApnSettings.MVNO_NONE;
        }
        for (File file2 : listFiles2) {
            String name2 = file2.getName();
            Log.d("GalaxySystemAppsInfoManager", "Name : " + name2);
            if (name2.contains(str)) {
                return "/system/apex/".concat(name2);
            }
        }
        return ApnSettings.MVNO_NONE;
    }
}
