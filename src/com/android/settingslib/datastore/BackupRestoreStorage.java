package com.android.settingslib.datastore;

import android.app.backup.BackupDataInputStream;
import android.app.backup.BackupDataOutput;
import android.app.backup.BackupHelper;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.collection.MutableScatterMap;
import androidx.collection.ScatterMapKt;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;

import com.android.settings.fuelgauge.datasaver.DynamicDenylistManager$$ExternalSyntheticOutline0;

import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.CheckedOutputStream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BackupRestoreStorage implements BackupHelper {
    public List entities;
    public final MutableScatterMap entityStates = new MutableScatterMap();

    public abstract List createBackupRestoreEntities();

    public BackupCodec defaultCodec() {
        return BackupZipCodec.BEST_COMPRESSION;
    }

    public boolean enableBackup(BackupContext backupContext) {
        return true;
    }

    public boolean enableRestore() {
        return true;
    }

    public abstract String getName();

    @Override // android.app.backup.BackupHelper
    public final void performBackup(
            ParcelFileDescriptor parcelFileDescriptor,
            BackupDataOutput data,
            ParcelFileDescriptor newState) {
        BackupContext backupContext;
        OutputStream wrapBackupOutputStream;
        Intrinsics.checkNotNullParameter(data, "data");
        Intrinsics.checkNotNullParameter(newState, "newState");
        readEntityStates$frameworks__base__packages__SettingsLib__DataStore__android_common__SeslSettingsLibDataStore(
                parcelFileDescriptor, this.entityStates);
        BackupContext backupContext2 = new BackupContext();
        if (!enableBackup(backupContext2)) {
            Log.i("BackupRestoreStorage", "[" + getName() + "] Backup disabled");
            return;
        }
        Log.i("BackupRestoreStorage", "[" + getName() + "] Backup start");
        CRC32 crc32 = new CRC32();
        for (BackupRestoreEntity backupRestoreEntity : createBackupRestoreEntities()) {
            String key = backupRestoreEntity.getKey();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            crc32.reset();
            try {
                wrapBackupOutputStream =
                        wrapBackupOutputStream(
                                defaultCodec(),
                                new CheckedOutputStream(byteArrayOutputStream, crc32));
            } catch (Exception e) {
                e = e;
                backupContext = backupContext2;
            }
            try {
                EntityBackupResult backup =
                        backupRestoreEntity.backup(backupContext2, wrapBackupOutputStream);
                CloseableKt.closeFinally(wrapBackupOutputStream, null);
                int ordinal = backup.ordinal();
                if (ordinal == 0) {
                    long value = crc32.getValue();
                    MutableScatterMap mutableScatterMap = this.entityStates;
                    Long valueOf = Long.valueOf(value);
                    int findInsertIndex = mutableScatterMap.findInsertIndex(key);
                    if (findInsertIndex < 0) {
                        findInsertIndex = ~findInsertIndex;
                    }
                    backupContext = backupContext2;
                    Object[] objArr = mutableScatterMap.values;
                    Object obj = objArr[findInsertIndex];
                    mutableScatterMap.keys[findInsertIndex] = key;
                    objArr[findInsertIndex] = valueOf;
                    Long l = (Long) obj;
                    if (l != null && l.longValue() == value) {
                        String name = getName();
                        int size = byteArrayOutputStream.size();
                        StringBuilder m =
                                SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                        "[", name, "] Backup entity ", key, " unchanged: ");
                        m.append(size);
                        m.append(" bytes");
                        Log.i("BackupRestoreStorage", m.toString());
                    } else {
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        int length = byteArray.length;
                        data.writeEntityHeader(key, length);
                        data.writeEntityData(byteArray, length);
                        StringBuilder m2 =
                                SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                        "[", getName(), "] Backup entity ", key, ": ");
                        m2.append(length);
                        m2.append(" bytes");
                        Log.i("BackupRestoreStorage", m2.toString());
                    }
                    backupContext2 = backupContext;
                } else if (ordinal == 1) {
                    Log.i(
                            "BackupRestoreStorage",
                            MotionLayout$$ExternalSyntheticOutline0.m(
                                    "[", getName(), "] Backup entity ", key, " intact"));
                } else if (ordinal == 2) {
                    this.entityStates.remove(key);
                    data.writeEntityHeader(key, -1);
                    Log.i(
                            "BackupRestoreStorage",
                            MotionLayout$$ExternalSyntheticOutline0.m(
                                    "[", getName(), "] Backup entity ", key, " deleted"));
                }
            } catch (Throwable th) {
                backupContext = backupContext2;
                try {
                    throw th;
                } catch (Throwable th2) {
                    try {
                        CloseableKt.closeFinally(wrapBackupOutputStream, th);
                        throw th2;
                    } catch (Exception e2) {
                        e = e2;
                        Log.e(
                                "BackupRestoreStorage",
                                "[" + getName() + "] Fail to backup entity " + key,
                                e);
                        backupContext2 = backupContext;
                    }
                }
            }
        }
        writeAndClearEntityStates(newState);
        Log.i("BackupRestoreStorage", "[" + getName() + "] Backup end");
    }

    public final void
            readEntityStates$frameworks__base__packages__SettingsLib__DataStore__android_common__SeslSettingsLibDataStore(
                    ParcelFileDescriptor parcelFileDescriptor, MutableScatterMap state) {
        Intrinsics.checkNotNullParameter(state, "state");
        state.clear();
        FileDescriptor fileDescriptor =
                parcelFileDescriptor != null ? parcelFileDescriptor.getFileDescriptor() : null;
        if (fileDescriptor == null) {
            return;
        }
        DataInputStream dataInputStream = new DataInputStream(new FileInputStream(fileDescriptor));
        try {
            byte readByte = dataInputStream.readByte();
            if (readByte != 0) {
                Log.w(
                        "BackupRestoreStorage",
                        "["
                                + getName()
                                + "] Unexpected state version, read:"
                                + ((int) readByte)
                                + ", expected:0");
                return;
            }
            int readInt = dataInputStream.readInt();
            while (true) {
                int i = readInt - 1;
                if (readInt <= 0) {
                    return;
                }
                String readUTF = dataInputStream.readUTF();
                long readLong = dataInputStream.readLong();
                Intrinsics.checkNotNull(readUTF);
                state.set(readUTF, Long.valueOf(readLong));
                readInt = i;
            }
        } catch (Exception e) {
            if (e instanceof EOFException) {
                Log.d("BackupRestoreStorage", "[" + getName() + "] Hit EOF when read state file");
            } else {
                Log.e("BackupRestoreStorage", "[" + getName() + "] Fail to read state file", e);
            }
            state.clear();
        }
    }

    @Override // android.app.backup.BackupHelper
    public void restoreEntity(BackupDataInputStream data) {
        Object obj;
        Intrinsics.checkNotNullParameter(data, "data");
        String key = data.getKey();
        if (!enableRestore()) {
            Log.i(
                    "BackupRestoreStorage",
                    "[" + getName() + "] Restore disabled, ignore entity " + key);
            return;
        }
        List list = this.entities;
        if (list == null) {
            list = createBackupRestoreEntities();
            this.entities = list;
        }
        Iterator it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            } else {
                obj = it.next();
                if (Intrinsics.areEqual(((BackupRestoreEntity) obj).getKey(), key)) {
                    break;
                }
            }
        }
        BackupRestoreEntity backupRestoreEntity = (BackupRestoreEntity) obj;
        if (backupRestoreEntity == null) {
            Log.w(
                    "BackupRestoreStorage",
                    "[" + getName() + "] Cannot find handler for entity " + key);
            return;
        }
        String name = getName();
        int size = data.size();
        StringBuilder m =
                SeslRoundedCorner$$ExternalSyntheticOutline0.m("[", name, "] Restore ", key, ": ");
        m.append(size);
        m.append(" bytes");
        Log.i("BackupRestoreStorage", m.toString());
        Intrinsics.checkNotNull(key);
        RestoreContext restoreContext = new RestoreContext();
        BackupCodec defaultCodec = defaultCodec();
        LimitedNoCloseInputStream limitedNoCloseInputStream = new LimitedNoCloseInputStream(data);
        CRC32 crc32 = new CRC32();
        try {
            backupRestoreEntity.restore(
                    restoreContext,
                    wrapRestoreInputStream(
                            defaultCodec,
                            new CheckedInputStream(limitedNoCloseInputStream, crc32)));
            this.entityStates.set(key, Long.valueOf(crc32.getValue()));
        } catch (Exception e) {
            Log.e("BackupRestoreStorage", "[" + getName() + "] Fail to restore entity " + key, e);
        }
    }

    public OutputStream wrapBackupOutputStream(BackupCodec codec, OutputStream outputStream) {
        Intrinsics.checkNotNullParameter(codec, "codec");
        Intrinsics.checkNotNullParameter(outputStream, "outputStream");
        outputStream.write(codec.getId());
        return codec.encode(outputStream);
    }

    public InputStream wrapRestoreInputStream(BackupCodec codec, InputStream inputStream) {
        BackupCodec backupCodec;
        Intrinsics.checkNotNullParameter(codec, "codec");
        int read = inputStream.read();
        byte id = codec.getId();
        if (read == id) {
            return codec.decode(inputStream);
        }
        DynamicDenylistManager$$ExternalSyntheticOutline0.m(
                "Expect codec id ", " but got ", id, read, "BackupRestoreStorage");
        byte b = (byte) read;
        if (b == 0) {
            backupCodec = new BackupNoOpCodec();
        } else {
            if (b != 1) {
                throw new IllegalArgumentException(
                        SeslRoundedCorner$$ExternalSyntheticOutline0.m(b, "Unknown codec id "));
            }
            backupCodec = BackupZipCodec.BEST_COMPRESSION;
        }
        return backupCodec.decode(inputStream);
    }

    public final void writeAndClearEntityStates(ParcelFileDescriptor parcelFileDescriptor) {
        DataOutputStream dataOutputStream =
                new DataOutputStream(
                        new FileOutputStream(parcelFileDescriptor.getFileDescriptor()));
        int i = 0;
        try {
            dataOutputStream.writeByte(0);
            dataOutputStream.writeInt(this.entityStates._size);
            MutableScatterMap mutableScatterMap = this.entityStates;
            Object[] objArr = mutableScatterMap.keys;
            Object[] objArr2 = mutableScatterMap.values;
            long[] jArr = mutableScatterMap.metadata;
            int length = jArr.length - 2;
            if (length >= 0) {
                int i2 = 0;
                while (true) {
                    long j = jArr[i2];
                    if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                        int i3 = 8 - ((~(i2 - length)) >>> 31);
                        for (int i4 = i; i4 < i3; i4++) {
                            if ((255 & j) < 128) {
                                int i5 = (i2 << 3) + i4;
                                Object obj = objArr[i5];
                                long longValue = ((Number) objArr2[i5]).longValue();
                                dataOutputStream.writeUTF((String) obj);
                                dataOutputStream.writeLong(longValue);
                            }
                            j >>= 8;
                        }
                        if (i3 != 8) {
                            break;
                        }
                    }
                    if (i2 == length) {
                        break;
                    }
                    i2++;
                    i = 0;
                }
            }
            dataOutputStream.flush();
        } catch (Exception e) {
            Log.e("BackupRestoreStorage", "[" + getName() + "] Fail to write state file", e);
        }
        this.entityStates.clear();
        MutableScatterMap mutableScatterMap2 = this.entityStates;
        int i6 = mutableScatterMap2._capacity;
        int normalizeCapacity =
                ScatterMapKt.normalizeCapacity(
                        ScatterMapKt.unloadedCapacity(mutableScatterMap2._size));
        if (normalizeCapacity < i6) {
            mutableScatterMap2.resizeStorage(normalizeCapacity);
        }
    }

    @Override // android.app.backup.BackupHelper
    public final void writeNewStateDescription(ParcelFileDescriptor newState) {
        Intrinsics.checkNotNullParameter(newState, "newState");
        if (enableRestore()) {
            this.entities = null;
            writeAndClearEntityStates(newState);
            onRestoreFinished();
        }
    }

    public static /* synthetic */ void
            getEntities$frameworks__base__packages__SettingsLib__DataStore__android_common__SeslSettingsLibDataStore$annotations() {}

    public static /* synthetic */ void getEntityStates$annotations() {}

    public void onRestoreFinished() {}
}
