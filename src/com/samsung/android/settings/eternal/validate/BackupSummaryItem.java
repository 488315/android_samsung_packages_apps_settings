package com.samsung.android.settings.eternal.validate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BackupSummaryItem {
    public int mBackupItemSize;
    public int mDTDSize;
    public String mUID;

    public final String toString() {
        return "["
                + this.mUID
                + "]\n * DTD size = "
                + this.mDTDSize
                + " / backupSceneList size = "
                + this.mBackupItemSize;
    }
}
