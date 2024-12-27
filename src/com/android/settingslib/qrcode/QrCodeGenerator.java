package com.android.settingslib.qrcode;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;

import kotlin.jvm.internal.Intrinsics;

import java.nio.charset.StandardCharsets;
import java.util.EnumMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class QrCodeGenerator {
    public static Bitmap encodeQrCode$default(int i, String contents) {
        Intrinsics.checkNotNullParameter(contents, "contents");
        EnumMap enumMap = new EnumMap(EncodeHintType.class);
        if (!StandardCharsets.ISO_8859_1.newEncoder().canEncode(contents)) {
            enumMap.put(
                    (EnumMap) EncodeHintType.CHARACTER_SET,
                    (EncodeHintType) StandardCharsets.UTF_8.name());
        }
        BitMatrix encode =
                new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, i, i, enumMap);
        int[] iArr = new int[i * i];
        for (int i2 = 0; i2 < i; i2++) {
            for (int i3 = 0; i3 < i; i3++) {
                iArr[(i2 * i) + i3] = encode.get(i2, i3) ? EmergencyPhoneWidget.BG_COLOR : -1;
            }
        }
        Bitmap createBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.RGB_565);
        Intrinsics.checkNotNullExpressionValue(createBitmap, "createBitmap(...)");
        createBitmap.setPixels(iArr, 0, i, 0, 0, i, i);
        return createBitmap;
    }
}
