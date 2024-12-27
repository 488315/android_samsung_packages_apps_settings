package com.samsung.android.gtscell.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.util.Base64;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.io.ExposingBufferByteArrayOutputStream;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        bv = {1, 0, 3},
        d1 = {
            "\u0000\u0018\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\b\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\f\u0010\u0005\u001a\u0004\u0018\u00010\u0002*\u00020\u0006\u001a\f\u0010\u0005\u001a\u0004\u0018\u00010\u0002*\u00020\u0001¨\u0006\u0007"
        },
        d2 = {
            "toBase64String",
            ApnSettings.MVNO_NONE,
            "Landroid/graphics/Bitmap;",
            "size",
            ApnSettings.MVNO_NONE,
            "toBitmap",
            "Ljava/io/File;",
            "gtscell_release"
        },
        k = 2,
        mv = {1, 1, 16})
/* loaded from: classes4.dex */
public final class GtsCellExKt {
    public static final String toBase64String(Bitmap toBase64String, int i) {
        byte[] invoke;
        Intrinsics.checkParameterIsNotNull(toBase64String, "$this$toBase64String");
        GtsCellExKt$toBase64String$1 gtsCellExKt$toBase64String$1 =
                GtsCellExKt$toBase64String$1.INSTANCE;
        if (toBase64String.getWidth() > i) {
            Bitmap thumbnail =
                    ThumbnailUtils.extractThumbnail(
                            toBase64String,
                            i,
                            MathKt.roundToInt(
                                    i / (toBase64String.getWidth() / toBase64String.getHeight())));
            Intrinsics.checkExpressionValueIsNotNull(thumbnail, "thumbnail");
            invoke = gtsCellExKt$toBase64String$1.invoke(thumbnail);
            thumbnail.recycle();
        } else {
            invoke = gtsCellExKt$toBase64String$1.invoke(toBase64String);
        }
        String encodeToString = Base64.encodeToString(invoke, 0);
        Intrinsics.checkExpressionValueIsNotNull(
                encodeToString, "Base64.encodeToString(bytes, Base64.DEFAULT)");
        return encodeToString;
    }

    public static final Bitmap toBitmap(File toBitmap) {
        Intrinsics.checkParameterIsNotNull(toBitmap, "$this$toBitmap");
        FileInputStream fileInputStream = new FileInputStream(toBitmap);
        try {
            long length = toBitmap.length();
            if (length > 2147483647L) {
                throw new OutOfMemoryError(
                        "File "
                                + toBitmap
                                + " is too big ("
                                + length
                                + " bytes) to fit in memory.");
            }
            int i = (int) length;
            byte[] bArr = new byte[i];
            int i2 = i;
            int i3 = 0;
            while (i2 > 0) {
                int read = fileInputStream.read(bArr, i3, i2);
                if (read < 0) {
                    break;
                }
                i2 -= read;
                i3 += read;
            }
            if (i2 > 0) {
                bArr = Arrays.copyOf(bArr, i3);
                Intrinsics.checkNotNullExpressionValue(bArr, "copyOf(...)");
            } else {
                int read2 = fileInputStream.read();
                if (read2 != -1) {
                    ExposingBufferByteArrayOutputStream exposingBufferByteArrayOutputStream =
                            new ExposingBufferByteArrayOutputStream(8193);
                    exposingBufferByteArrayOutputStream.write(read2);
                    ByteStreamsKt.copyTo$default(
                            fileInputStream, exposingBufferByteArrayOutputStream);
                    int size = exposingBufferByteArrayOutputStream.size() + i;
                    if (size < 0) {
                        throw new OutOfMemoryError(
                                "File " + toBitmap + " is too big to fit in memory.");
                    }
                    byte[] buffer = exposingBufferByteArrayOutputStream.getBuffer();
                    bArr = Arrays.copyOf(bArr, size);
                    Intrinsics.checkNotNullExpressionValue(bArr, "copyOf(...)");
                    ArraysKt___ArraysKt.copyInto(
                            buffer, i, 0, bArr, exposingBufferByteArrayOutputStream.size());
                }
            }
            CloseableKt.closeFinally(fileInputStream, null);
            try {
                return BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
            } catch (Exception unused) {
                return null;
            }
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(fileInputStream, th);
                throw th2;
            }
        }
    }

    public static final Bitmap toBitmap(String toBitmap) {
        Intrinsics.checkParameterIsNotNull(toBitmap, "$this$toBitmap");
        byte[] decode = Base64.decode(toBitmap, 0);
        try {
            return BitmapFactory.decodeByteArray(decode, 0, decode.length);
        } catch (Exception unused) {
            return null;
        }
    }
}
