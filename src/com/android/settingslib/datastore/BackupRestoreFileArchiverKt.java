package com.android.settingslib.datastore;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;

import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BackupRestoreFileArchiverKt {
    public static final BackupRestoreEntity toBackupRestoreEntity(
            final BackupRestoreFileStorage backupRestoreFileStorage) {
        Intrinsics.checkNotNullParameter(backupRestoreFileStorage, "<this>");
        return new BackupRestoreEntity() { // from class:
                                           // com.android.settingslib.datastore.BackupRestoreFileArchiverKt$toBackupRestoreEntity$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.android.settingslib.datastore.BackupRestoreEntity
            public final EntityBackupResult backup(
                    BackupContext backupContext, OutputStream outputStream) {
                Intrinsics.checkNotNullParameter(outputStream, "outputStream");
                BackupRestoreFileStorage backupRestoreFileStorage2 = BackupRestoreFileStorage.this;
                backupRestoreFileStorage2.getClass();
                File backupFile = backupRestoreFileStorage2.getBackupFile();
                SharedPreferencesStorage sharedPreferencesStorage =
                        (SharedPreferencesStorage) backupRestoreFileStorage2;
                SharedPreferences sharedPreferences =
                        sharedPreferencesStorage.context.getSharedPreferences(
                                sharedPreferencesStorage
                                        .getIntermediateName$frameworks__base__packages__SettingsLib__DataStore__android_common__SeslSettingsLibDataStore(),
                                4);
                Intrinsics.checkNotNullExpressionValue(
                        sharedPreferences, "getSharedPreferences(...)");
                Map<String, ?> all = sharedPreferencesStorage.sharedPreferences.getAll();
                Intrinsics.checkNotNullExpressionValue(all, "getAll(...)");
                if (!sharedPreferencesStorage
                        .mergeSharedPreferences$frameworks__base__packages__SettingsLib__DataStore__android_common__SeslSettingsLibDataStore(
                                sharedPreferences, all, "Backup")
                        .commit()) {
                    Log.w(
                            "BackupRestoreStorage",
                            "[" + sharedPreferencesStorage.name + "] fail to commit");
                }
                if (!backupFile.exists()) {
                    Log.i(
                            "BackupRestoreStorage",
                            MotionLayout$$ExternalSyntheticOutline0.m(
                                    "[",
                                    sharedPreferencesStorage.name,
                                    "] ",
                                    backupRestoreFileStorage2.storageFilePath,
                                    " not exist"));
                    return EntityBackupResult.DELETE;
                }
                BackupZipCodec backupZipCodec = BackupZipCodec.BEST_COMPRESSION;
                OutputStream wrapBackupOutputStream =
                        backupRestoreFileStorage2.wrapBackupOutputStream(
                                backupZipCodec, outputStream);
                try {
                    FileInputStream fileInputStream = new FileInputStream(backupFile);
                    try {
                        long copyTo$default =
                                ByteStreamsKt.copyTo$default(
                                        fileInputStream, wrapBackupOutputStream);
                        CloseableKt.closeFinally(fileInputStream, null);
                        Log.i(
                                "BackupRestoreStorage",
                                "["
                                        + ((SharedPreferencesStorage) backupRestoreFileStorage2)
                                                .name
                                        + "] "
                                        + backupRestoreFileStorage2.storageFilePath
                                        + " backup "
                                        + copyTo$default
                                        + " bytes with "
                                        + backupZipCodec.name);
                        CloseableKt.closeFinally(wrapBackupOutputStream, null);
                        SharedPreferences sharedPreferences2 =
                                sharedPreferencesStorage.context.getSharedPreferences(
                                        sharedPreferencesStorage
                                                .getIntermediateName$frameworks__base__packages__SettingsLib__DataStore__android_common__SeslSettingsLibDataStore(),
                                        4);
                        Intrinsics.checkNotNullExpressionValue(
                                sharedPreferences2, "getSharedPreferences(...)");
                        sharedPreferencesStorage.delete(
                                sharedPreferences2,
                                sharedPreferencesStorage
                                        .getIntermediateName$frameworks__base__packages__SettingsLib__DataStore__android_common__SeslSettingsLibDataStore());
                        return EntityBackupResult.UPDATE;
                    } finally {
                    }
                } finally {
                }
            }

            @Override // com.android.settingslib.datastore.BackupRestoreEntity
            public final String getKey() {
                return BackupRestoreFileStorage.this.storageFilePath;
            }

            @Override // com.android.settingslib.datastore.BackupRestoreEntity
            public final void restore(RestoreContext restoreContext, InputStream inputStream) {
                Intrinsics.checkNotNullParameter(inputStream, "inputStream");
            }
        };
    }
}
