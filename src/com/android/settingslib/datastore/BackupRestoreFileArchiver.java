package com.android.settingslib.datastore;

import android.app.backup.BackupDataInputStream;
import android.content.Context;
import android.util.Log;

import androidx.collection.MutableScatterMap;

import com.android.settings.accessibility.PreferredShortcuts$$ExternalSyntheticOutline0;

import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BackupRestoreFileArchiver extends BackupRestoreStorage {
    public final Context context;
    public final List fileStorages;
    public final String name;

    public BackupRestoreFileArchiver(Context context, List list, String str) {
        this.context = context;
        this.fileStorages = list;
        this.name = str;
    }

    @Override // com.android.settingslib.datastore.BackupRestoreStorage
    public final List createBackupRestoreEntities() {
        List list = this.fileStorages;
        ArrayList arrayList =
                new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(
                    BackupRestoreFileArchiverKt.toBackupRestoreEntity(
                            (BackupRestoreFileStorage) it.next()));
        }
        return arrayList;
    }

    public final List<BackupRestoreFileStorage>
            getFileStorages$frameworks__base__packages__SettingsLib__DataStore__android_common__SeslSettingsLibDataStore() {
        return this.fileStorages;
    }

    @Override // com.android.settingslib.datastore.BackupRestoreStorage
    public final String getName() {
        return this.name;
    }

    @Override // com.android.settingslib.datastore.BackupRestoreStorage
    public final void onRestoreFinished() {
        Iterator it = this.fileStorages.iterator();
        while (it.hasNext()) {
            ((BackupRestoreFileStorage) it.next()).getClass();
        }
    }

    @Override // com.android.settingslib.datastore.BackupRestoreStorage,
              // android.app.backup.BackupHelper
    public final void restoreEntity(BackupDataInputStream data) {
        Object obj;
        File file;
        BackupCodec backupCodec;
        Intrinsics.checkNotNullParameter(data, "data");
        String key = data.getKey();
        Iterator it = this.fileStorages.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            } else {
                obj = it.next();
                if (Intrinsics.areEqual(((BackupRestoreFileStorage) obj).storageFilePath, key)) {
                    break;
                }
            }
        }
        BackupRestoreFileStorage backupRestoreFileStorage = (BackupRestoreFileStorage) obj;
        if (backupRestoreFileStorage != null) {
            file = backupRestoreFileStorage.getBackupFile();
        } else {
            Log.i("BackupRestoreStorage", "Restore unknown file " + key);
            Context context = this.context;
            Intrinsics.checkNotNullParameter(context, "<this>");
            File dataDir = context.getDataDir();
            Intrinsics.checkNotNull(dataDir);
            file = new File(dataDir, key);
        }
        StringBuilder m =
                PreferredShortcuts$$ExternalSyntheticOutline0.m(
                        data.size(), "[", this.name, "] Restore ", " bytes for ");
        m.append(key);
        m.append(" to ");
        m.append(file);
        Log.i("BackupRestoreStorage", m.toString());
        LimitedNoCloseInputStream limitedNoCloseInputStream = new LimitedNoCloseInputStream(data);
        CRC32 crc32 = new CRC32();
        CheckedInputStream checkedInputStream =
                new CheckedInputStream(limitedNoCloseInputStream, crc32);
        try {
            byte read = (byte) checkedInputStream.read();
            BackupZipCodec backupZipCodec = BackupZipCodec.BEST_COMPRESSION;
            if (read == 0) {
                backupCodec = new BackupNoOpCodec();
            } else {
                if (read != 1) {
                    throw new IllegalArgumentException("Unknown codec id " + ((int) read));
                }
                backupCodec = backupZipCodec;
            }
            if (backupRestoreFileStorage != null) {
                backupZipCodec.getClass();
                if (1 != backupCodec.getId()) {
                    String str = this.name;
                    byte id = backupCodec.getId();
                    backupZipCodec.getClass();
                    Log.i(
                            "BackupRestoreStorage",
                            "[" + str + "] " + key + " different codec: " + ((int) id) + ", 1");
                }
            }
            File parentFile = file.getParentFile();
            if (parentFile != null) {
                parentFile.mkdirs();
            }
            InputStream decode = backupCodec.decode(checkedInputStream);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            try {
                long copyTo$default = ByteStreamsKt.copyTo$default(decode, fileOutputStream);
                CloseableKt.closeFinally(fileOutputStream, null);
                Log.i(
                        "BackupRestoreStorage",
                        "["
                                + this.name
                                + "] "
                                + key
                                + " restore "
                                + copyTo$default
                                + " bytes with "
                                + backupCodec.getName());
                if (backupRestoreFileStorage != null) {
                    backupRestoreFileStorage.onRestoreFinished(file);
                }
                MutableScatterMap mutableScatterMap = this.entityStates;
                Intrinsics.checkNotNull(key);
                mutableScatterMap.set(key, Long.valueOf(crc32.getValue()));
            } finally {
            }
        } catch (Exception e) {
            Log.e("BackupRestoreStorage", "[" + this.name + "] Fail to restore " + key, e);
        }
    }

    @Override // com.android.settingslib.datastore.BackupRestoreStorage
    public final OutputStream wrapBackupOutputStream(BackupCodec codec, OutputStream outputStream) {
        Intrinsics.checkNotNullParameter(codec, "codec");
        return outputStream;
    }

    @Override // com.android.settingslib.datastore.BackupRestoreStorage
    public final InputStream wrapRestoreInputStream(BackupCodec codec, InputStream inputStream) {
        Intrinsics.checkNotNullParameter(codec, "codec");
        return inputStream;
    }
}
