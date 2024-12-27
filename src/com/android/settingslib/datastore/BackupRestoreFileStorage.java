package com.android.settingslib.datastore;

import android.content.Context;

import kotlin.collections.EmptyList;
import kotlin.jvm.internal.Intrinsics;

import java.io.File;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BackupRestoreFileStorage extends BackupRestoreStorage {
    public final Context context;
    public final String storageFilePath;

    public BackupRestoreFileStorage(Context context, String str) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.storageFilePath = str;
    }

    @Override // com.android.settingslib.datastore.BackupRestoreStorage
    public final List createBackupRestoreEntities() {
        return EmptyList.INSTANCE;
    }

    public abstract File getBackupFile();

    public abstract void onRestoreFinished(File file);
}
