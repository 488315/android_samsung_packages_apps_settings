package com.samsung.android.gtscell.utils;

import android.graphics.Bitmap;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

import java.io.ByteArrayOutputStream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        bv = {1, 0, 3},
        d1 = {
            "\u0000\f\n"
                + "\u0000\n"
                + "\u0002\u0010\u0012\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"
                + "¢\u0006\u0002\b\u0003"
        },
        d2 = {"toByteArray", ApnSettings.MVNO_NONE, "Landroid/graphics/Bitmap;", "invoke"},
        k = 3,
        mv = {1, 1, 16})
/* loaded from: classes4.dex */
public final class GtsCellExKt$toBase64String$1 extends Lambda implements Function1 {
    public static final GtsCellExKt$toBase64String$1 INSTANCE = new GtsCellExKt$toBase64String$1();

    public GtsCellExKt$toBase64String$1() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final byte[] invoke(Bitmap toByteArray) {
        Intrinsics.checkParameterIsNotNull(toByteArray, "$this$toByteArray");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        toByteArray.compress(Bitmap.CompressFormat.WEBP, 90, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        Intrinsics.checkExpressionValueIsNotNull(byteArray, "stream.toByteArray()");
        return byteArray;
    }
}
