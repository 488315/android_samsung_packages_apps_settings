package com.android.settingslib.datastore;

import java.io.InputStream;
import java.io.OutputStream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public interface BackupRestoreEntity {
    EntityBackupResult backup(BackupContext backupContext, OutputStream outputStream);

    String getKey();

    void restore(RestoreContext restoreContext, InputStream inputStream);
}
