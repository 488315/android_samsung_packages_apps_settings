package com.samsung.android.scloud.oem.lib.backup;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BackupMetaManager {
    public static BackupMetaManager mMetaManager;
    public SharedPreferences backupMeta;
    public Map cancelMap;

    public static synchronized BackupMetaManager getInstance(Context context) {
        BackupMetaManager backupMetaManager;
        synchronized (BackupMetaManager.class) {
            try {
                if (mMetaManager == null) {
                    BackupMetaManager backupMetaManager2 = new BackupMetaManager();
                    backupMetaManager2.backupMeta = null;
                    backupMetaManager2.cancelMap = new ConcurrentHashMap();
                    backupMetaManager2.backupMeta = context.getSharedPreferences("BackupMeta", 0);
                    mMetaManager = backupMetaManager2;
                }
                backupMetaManager = mMetaManager;
            } catch (Throwable th) {
                throw th;
            }
        }
        return backupMetaManager;
    }

    public final void setCanceled(String str, boolean z) {
        ((ConcurrentHashMap) this.cancelMap).put(str, Boolean.valueOf(z));
    }
}
