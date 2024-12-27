package com.samsung.android.settings.analyzestorage.presenter.mediafile;

import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.presenter.utils.fileutils.FileWrapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SccFileType {
    public static String getSccMimeType(FileWrapper fileWrapper) {
        ArrayIndexOutOfBoundsException e;
        String str;
        IOException e2;
        try {
            FileInputStream fileInputStream = new FileInputStream(fileWrapper);
            try {
                byte[] bArr = new byte[4];
                byte[] bArr2 = new byte[128];
                if (fileInputStream.skip(22L) < 0) {
                    Log.e("SccFileUtil", "getSccMimeType- need checking for skip(22)");
                }
                if (fileInputStream.read(bArr, 0, 4) < 0) {
                    Log.e("SccFileUtil", "getSccMimeType - need checking for read(pkLenBuf, 0, 4)");
                }
                ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
                ByteBuffer allocate = ByteBuffer.allocate(4);
                allocate.order(byteOrder);
                allocate.put(bArr);
                allocate.flip();
                int i = allocate.getInt();
                if (fileInputStream.skip(12L) < 0) {
                    Log.e("SccFileUtil", "getSccMimeType - need checking for skip(12)");
                }
                if (fileInputStream.read(bArr2, 0, i) < 0) {
                    Log.e(
                            "SccFileUtil",
                            "getSccMimeType - need checking for read(mimeTypeBuf, 0, pkLen)");
                }
                str = new String(bArr2, Charset.defaultCharset());
                try {
                    fileInputStream.close();
                } catch (IOException e3) {
                    e2 = e3;
                    Log.e("SccFileUtil", "getSccMimeType() ] IOException : " + e2);
                    return str;
                } catch (ArrayIndexOutOfBoundsException e4) {
                    e = e4;
                    Log.e(
                            "SccFileUtil",
                            "getSccMimeType() ] ArrayIndexOutOfBoundsException : " + e);
                    return str;
                }
            } finally {
            }
        } catch (IOException e5) {
            e2 = e5;
            str = null;
            Log.e("SccFileUtil", "getSccMimeType() ] IOException : " + e2);
            return str;
        } catch (ArrayIndexOutOfBoundsException e6) {
            e = e6;
            str = null;
            Log.e("SccFileUtil", "getSccMimeType() ] ArrayIndexOutOfBoundsException : " + e);
            return str;
        }
        return str;
    }
}
