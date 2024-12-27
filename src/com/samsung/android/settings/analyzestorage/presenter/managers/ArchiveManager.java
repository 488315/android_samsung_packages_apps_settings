package com.samsung.android.settings.analyzestorage.presenter.managers;

import android.content.pm.PackageManager;

import com.samsung.android.util.SemLog;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class ArchiveManager {
    public static final boolean isArchivalApp(PackageManager packageManager, String packageName) {
        Intrinsics.checkNotNullParameter(packageManager, "packageManager");
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        try {
            return packageManager.isAppArchivable(packageName);
        } catch (PackageManager.NameNotFoundException e) {
            SemLog.e(
                    "ArchiveManager",
                    "isArchivalApp() ] NameNotFoundException : " + e.getMessage());
            return false;
        }
    }
}
