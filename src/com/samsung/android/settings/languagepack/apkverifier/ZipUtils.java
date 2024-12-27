package com.samsung.android.settings.languagepack.apkverifier;

import android.util.Pair;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;

import com.samsung.android.knox.custom.CustomDeviceManager;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class ZipUtils {
    public static Pair findZipEndOfCentralDirectoryRecord(
            RandomAccessFile randomAccessFile, int i) {
        int i2;
        if (i < 0 || i > 65535) {
            throw new IllegalArgumentException(
                    SeslRoundedCorner$$ExternalSyntheticOutline0.m(i, "maxCommentSize: "));
        }
        long length = randomAccessFile.length();
        if (length < 22) {
            return null;
        }
        ByteBuffer allocate = ByteBuffer.allocate(((int) Math.min(i, length - 22)) + 22);
        ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
        allocate.order(byteOrder);
        long capacity = length - allocate.capacity();
        randomAccessFile.seek(capacity);
        randomAccessFile.readFully(allocate.array(), allocate.arrayOffset(), allocate.capacity());
        if (allocate.order() != byteOrder) {
            throw new IllegalArgumentException("ByteBuffer byte order must be little endian");
        }
        int capacity2 = allocate.capacity();
        if (capacity2 >= 22) {
            int i3 = capacity2 - 22;
            int min = Math.min(i3, CustomDeviceManager.QUICK_PANEL_ALL);
            for (int i4 = 0; i4 <= min; i4++) {
                i2 = i3 - i4;
                if (allocate.getInt(i2) == 101010256
                        && (allocate.getShort(i2 + 20) & 65535) == i4) {
                    break;
                }
            }
        }
        i2 = -1;
        if (i2 == -1) {
            return null;
        }
        allocate.position(i2);
        ByteBuffer slice = allocate.slice();
        slice.order(ByteOrder.LITTLE_ENDIAN);
        return Pair.create(slice, Long.valueOf(capacity + i2));
    }
}
