package com.github.mikephil.charting.charts;

import android.graphics.Bitmap;
import android.graphics.Paint;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract /* synthetic */ class Chart$2 {
    public static final /* synthetic */ int[] $SwitchMap$android$graphics$Bitmap$CompressFormat;
    public static final /* synthetic */ int[] $SwitchMap$android$graphics$Paint$Align;

    static {
        int[] iArr = new int[Bitmap.CompressFormat.values().length];
        $SwitchMap$android$graphics$Bitmap$CompressFormat = iArr;
        try {
            iArr[Bitmap.CompressFormat.PNG.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            $SwitchMap$android$graphics$Bitmap$CompressFormat[
                            Bitmap.CompressFormat.WEBP.ordinal()] =
                    2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            $SwitchMap$android$graphics$Bitmap$CompressFormat[
                            Bitmap.CompressFormat.JPEG.ordinal()] =
                    3;
        } catch (NoSuchFieldError unused3) {
        }
        int[] iArr2 = new int[Paint.Align.values().length];
        $SwitchMap$android$graphics$Paint$Align = iArr2;
        try {
            iArr2[Paint.Align.LEFT.ordinal()] = 1;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            $SwitchMap$android$graphics$Paint$Align[Paint.Align.RIGHT.ordinal()] = 2;
        } catch (NoSuchFieldError unused5) {
        }
    }
}
