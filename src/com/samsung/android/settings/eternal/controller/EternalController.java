package com.samsung.android.settings.eternal.controller;

import android.content.Context;

import com.samsung.android.settings.eternal.database.DatabaseManager;
import com.samsung.android.settings.eternal.manager.EternalManager;
import com.samsung.android.settings.eternal.manager.XmlManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class EternalController {
    public final Context mContext;
    public final DatabaseManager mDatabaseManager;
    public final EternalManager mEternalManager;
    public final XmlManager mXmlManager;

    public EternalController(Context context) {
        this.mContext = context;
        this.mEternalManager = new EternalManager(context);
        this.mXmlManager = new XmlManager(context);
        this.mDatabaseManager = new DatabaseManager(context);
    }

    /* JADX WARN: Removed duplicated region for block: B:85:0x01e8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.io.File fetchBackup(
            com.samsung.android.settings.eternal.data.BackupInfo r10) {
        /*
            Method dump skipped, instructions count: 516
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.eternal.controller.EternalController.fetchBackup(com.samsung.android.settings.eternal.data.BackupInfo):java.io.File");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:46:0x030e A[Catch: Exception -> 0x0384, LOOP:1: B:44:0x0308->B:46:0x030e, LOOP_END, TRY_LEAVE, TryCatch #4 {Exception -> 0x0384, blocks: (B:43:0x02fc, B:44:0x0308, B:46:0x030e), top: B:42:0x02fc }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x03a8  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x05d3  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x03b3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.samsung.android.settings.eternal.data.EpisodeHolder fetchRestore(
            com.samsung.android.settings.eternal.data.RestoreInfo r30) {
        /*
            Method dump skipped, instructions count: 1648
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.eternal.controller.EternalController.fetchRestore(com.samsung.android.settings.eternal.data.RestoreInfo):com.samsung.android.settings.eternal.data.EpisodeHolder");
    }
}
