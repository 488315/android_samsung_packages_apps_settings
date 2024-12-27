package com.samsung.android.settings.languagepack.gdiff;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.HeightInLinesModifierKt$$ExternalSyntheticOutline0;

import com.samsung.android.knox.custom.IKnoxCustomManager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class GDiffPatcher {
    public ByteBuffer buf;
    public byte[] buf2;

    public final void append(
            int i,
            BufferedInputStream bufferedInputStream,
            BufferedOutputStream bufferedOutputStream) {
        while (i > 0) {
            byte[] bArr = this.buf2;
            int min = Math.min(bArr.length, i);
            int read = bufferedInputStream.read(bArr, 0, min);
            if (read == -1) {
                throw new EOFException(
                        HeightInLinesModifierKt$$ExternalSyntheticOutline0.m(
                                i, min, "cannot read ", " len : "));
            }
            bufferedOutputStream.write(bArr, 0, read);
            i -= read;
        }
    }

    public final void copy(
            long j,
            int i,
            RandomAccessFileSeekableSource randomAccessFileSeekableSource,
            BufferedOutputStream bufferedOutputStream) {
        randomAccessFileSeekableSource.raf.seek(j);
        while (i > 0) {
            this.buf.clear().limit(Math.min(this.buf.capacity(), i));
            ByteBuffer byteBuffer = this.buf;
            int read =
                    randomAccessFileSeekableSource.raf.read(
                            byteBuffer.array(), byteBuffer.position(), byteBuffer.remaining());
            if (read == -1) {
                read = -1;
            } else {
                byteBuffer.position(byteBuffer.position() + read);
            }
            if (read == -1) {
                throw new EOFException("in copy " + j + " " + i);
            }
            bufferedOutputStream.write(this.buf.array(), 0, read);
            i -= read;
        }
    }

    public final void patch(File file, File file2, File file3) {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        RandomAccessFileSeekableSource randomAccessFileSeekableSource =
                new RandomAccessFileSeekableSource();
        randomAccessFileSeekableSource.raf = randomAccessFile;
        FileInputStream fileInputStream = new FileInputStream(file2);
        FileOutputStream fileOutputStream = new FileOutputStream(file3);
        try {
            try {
                patch(randomAccessFileSeekableSource, fileInputStream, fileOutputStream);
                try {
                    randomAccessFile.close();
                    randomAccessFileSeekableSource.close();
                    fileInputStream.close();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e2) {
                throw e2;
            }
        } catch (Throwable th) {
            try {
                randomAccessFile.close();
                randomAccessFileSeekableSource.close();
                fileInputStream.close();
                fileOutputStream.close();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
            throw th;
        }
    }

    public final void patch(
            RandomAccessFileSeekableSource randomAccessFileSeekableSource,
            InputStream inputStream,
            OutputStream outputStream) {
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        DataOutputStream dataOutputStream = new DataOutputStream(bufferedOutputStream);
        DataInputStream dataInputStream = new DataInputStream(bufferedInputStream);
        if (dataInputStream.readUnsignedByte() != 209
                || dataInputStream.readUnsignedByte() != 255
                || dataInputStream.readUnsignedByte() != 209
                || dataInputStream.readUnsignedByte() != 255
                || dataInputStream.readUnsignedByte() != 4) {
            throw new PatchException("magic string not found, aborting!");
        }
        while (true) {
            int readUnsignedByte = dataInputStream.readUnsignedByte();
            if (readUnsignedByte == 0) {
                dataOutputStream.flush();
                return;
            }
            if (readUnsignedByte <= 246) {
                append(readUnsignedByte, bufferedInputStream, bufferedOutputStream);
            } else {
                switch (readUnsignedByte) {
                    case IKnoxCustomManager.Stub.TRANSACTION_addDexShortcut /* 247 */:
                        append(
                                dataInputStream.readUnsignedShort(),
                                bufferedInputStream,
                                bufferedOutputStream);
                        break;
                    case IKnoxCustomManager.Stub.TRANSACTION_removeDexShortcut /* 248 */:
                        append(
                                dataInputStream.readInt(),
                                bufferedInputStream,
                                bufferedOutputStream);
                        break;
                    case IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcut /* 249 */:
                        copy(
                                dataInputStream.readUnsignedShort(),
                                dataInputStream.readUnsignedByte(),
                                randomAccessFileSeekableSource,
                                bufferedOutputStream);
                        break;
                    case IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend /* 250 */:
                        copy(
                                dataInputStream.readUnsignedShort(),
                                dataInputStream.readUnsignedShort(),
                                randomAccessFileSeekableSource,
                                bufferedOutputStream);
                        break;
                    case IKnoxCustomManager.Stub.TRANSACTION_removeDexURLShortcut /* 251 */:
                        copy(
                                dataInputStream.readUnsignedShort(),
                                dataInputStream.readInt(),
                                randomAccessFileSeekableSource,
                                bufferedOutputStream);
                        break;
                    case IKnoxCustomManager.Stub
                            .TRANSACTION_setDexForegroundModePackageList /* 252 */:
                        copy(
                                dataInputStream.readInt(),
                                dataInputStream.readUnsignedByte(),
                                randomAccessFileSeekableSource,
                                bufferedOutputStream);
                        break;
                    case IKnoxCustomManager.Stub
                            .TRANSACTION_getDexForegroundModePackageList /* 253 */:
                        copy(
                                dataInputStream.readInt(),
                                dataInputStream.readUnsignedShort(),
                                randomAccessFileSeekableSource,
                                bufferedOutputStream);
                        break;
                    case 254:
                        copy(
                                dataInputStream.readInt(),
                                dataInputStream.readInt(),
                                randomAccessFileSeekableSource,
                                bufferedOutputStream);
                        break;
                    case 255:
                        copy(
                                dataInputStream.readLong(),
                                dataInputStream.readInt(),
                                randomAccessFileSeekableSource,
                                bufferedOutputStream);
                        break;
                    default:
                        throw new IllegalStateException(
                                SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                        readUnsignedByte, "command "));
                }
            }
        }
    }
}
