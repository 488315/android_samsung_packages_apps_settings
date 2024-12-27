package com.google.zxing;

import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class BarcodeFormat {
    public static final /* synthetic */ BarcodeFormat[] $VALUES;
    public static final BarcodeFormat AZTEC;
    public static final BarcodeFormat CODABAR;
    public static final BarcodeFormat CODE_128;
    public static final BarcodeFormat CODE_39;
    public static final BarcodeFormat CODE_93;
    public static final BarcodeFormat DATA_MATRIX;
    public static final BarcodeFormat EAN_13;
    public static final BarcodeFormat EAN_8;
    public static final BarcodeFormat ITF;
    public static final BarcodeFormat MAXICODE;
    public static final BarcodeFormat PDF_417;
    public static final BarcodeFormat QR_CODE;
    public static final BarcodeFormat RSS_14;
    public static final BarcodeFormat RSS_EXPANDED;
    public static final BarcodeFormat UPC_A;
    public static final BarcodeFormat UPC_E;
    public static final BarcodeFormat UPC_EAN_EXTENSION;

    static {
        BarcodeFormat barcodeFormat = new BarcodeFormat("AZTEC", 0);
        AZTEC = barcodeFormat;
        BarcodeFormat barcodeFormat2 =
                new BarcodeFormat(PeripheralBarcodeConstants.Symbology.CODABAR, 1);
        CODABAR = barcodeFormat2;
        BarcodeFormat barcodeFormat3 = new BarcodeFormat("CODE_39", 2);
        CODE_39 = barcodeFormat3;
        BarcodeFormat barcodeFormat4 = new BarcodeFormat("CODE_93", 3);
        CODE_93 = barcodeFormat4;
        BarcodeFormat barcodeFormat5 = new BarcodeFormat("CODE_128", 4);
        CODE_128 = barcodeFormat5;
        BarcodeFormat barcodeFormat6 =
                new BarcodeFormat(PeripheralBarcodeConstants.Symbology.DATA_MATRIX, 5);
        DATA_MATRIX = barcodeFormat6;
        BarcodeFormat barcodeFormat7 = new BarcodeFormat("EAN_8", 6);
        EAN_8 = barcodeFormat7;
        BarcodeFormat barcodeFormat8 = new BarcodeFormat("EAN_13", 7);
        EAN_13 = barcodeFormat8;
        BarcodeFormat barcodeFormat9 = new BarcodeFormat("ITF", 8);
        ITF = barcodeFormat9;
        BarcodeFormat barcodeFormat10 =
                new BarcodeFormat(PeripheralBarcodeConstants.Symbology.MAXICODE, 9);
        MAXICODE = barcodeFormat10;
        BarcodeFormat barcodeFormat11 = new BarcodeFormat("PDF_417", 10);
        PDF_417 = barcodeFormat11;
        BarcodeFormat barcodeFormat12 =
                new BarcodeFormat(PeripheralBarcodeConstants.Symbology.QR_CODE, 11);
        QR_CODE = barcodeFormat12;
        BarcodeFormat barcodeFormat13 = new BarcodeFormat("RSS_14", 12);
        RSS_14 = barcodeFormat13;
        BarcodeFormat barcodeFormat14 = new BarcodeFormat("RSS_EXPANDED", 13);
        RSS_EXPANDED = barcodeFormat14;
        BarcodeFormat barcodeFormat15 = new BarcodeFormat("UPC_A", 14);
        UPC_A = barcodeFormat15;
        BarcodeFormat barcodeFormat16 = new BarcodeFormat("UPC_E", 15);
        UPC_E = barcodeFormat16;
        BarcodeFormat barcodeFormat17 = new BarcodeFormat("UPC_EAN_EXTENSION", 16);
        UPC_EAN_EXTENSION = barcodeFormat17;
        $VALUES =
                new BarcodeFormat[] {
                    barcodeFormat,
                    barcodeFormat2,
                    barcodeFormat3,
                    barcodeFormat4,
                    barcodeFormat5,
                    barcodeFormat6,
                    barcodeFormat7,
                    barcodeFormat8,
                    barcodeFormat9,
                    barcodeFormat10,
                    barcodeFormat11,
                    barcodeFormat12,
                    barcodeFormat13,
                    barcodeFormat14,
                    barcodeFormat15,
                    barcodeFormat16,
                    barcodeFormat17
                };
    }

    public static BarcodeFormat valueOf(String str) {
        return (BarcodeFormat) Enum.valueOf(BarcodeFormat.class, str);
    }

    public static BarcodeFormat[] values() {
        return (BarcodeFormat[]) $VALUES.clone();
    }
}
