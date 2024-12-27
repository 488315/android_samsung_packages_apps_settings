package com.samsung.android.settings.languagepack.apkverifier;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;

import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class ApkSigningBlockUtils {
    public static ByteBuffer getByteBuffer(int i, ByteBuffer byteBuffer) {
        if (i < 0) {
            throw new IllegalArgumentException(
                    SeslRoundedCorner$$ExternalSyntheticOutline0.m(i, "size: "));
        }
        int limit = byteBuffer.limit();
        int position = byteBuffer.position();
        int i2 = i + position;
        if (i2 < position || i2 > limit) {
            throw new BufferUnderflowException();
        }
        byteBuffer.limit(i2);
        try {
            ByteBuffer slice = byteBuffer.slice();
            slice.order(byteBuffer.order());
            byteBuffer.position(i2);
            return slice;
        } finally {
            byteBuffer.limit(limit);
        }
    }

    public static ByteBuffer getLengthPrefixedSlice(ByteBuffer byteBuffer) {
        if (byteBuffer.remaining() < 4) {
            throw new IOException(
                    "Remaining buffer too short to contain length of length-prefixed field."
                        + " Remaining: "
                            + byteBuffer.remaining());
        }
        int i = byteBuffer.getInt();
        if (i < 0) {
            throw new IllegalArgumentException("Negative length");
        }
        if (i <= byteBuffer.remaining()) {
            return getByteBuffer(i, byteBuffer);
        }
        StringBuilder m =
                ListPopupWindow$$ExternalSyntheticOutline0.m(
                        i,
                        "Length-prefixed field longer than remaining buffer. Field length: ",
                        ", remaining: ");
        m.append(byteBuffer.remaining());
        throw new IOException(m.toString());
    }

    public static byte[] readLengthPrefixedByteArray(ByteBuffer byteBuffer) {
        int i = byteBuffer.getInt();
        if (i < 0) {
            throw new IOException("Negative length");
        }
        if (i <= byteBuffer.remaining()) {
            byte[] bArr = new byte[i];
            byteBuffer.get(bArr);
            return bArr;
        }
        StringBuilder m =
                ListPopupWindow$$ExternalSyntheticOutline0.m(
                        i,
                        "Underflow while reading length-prefixed value. Length: ",
                        ", available: ");
        m.append(byteBuffer.remaining());
        throw new IOException(m.toString());
    }
}
