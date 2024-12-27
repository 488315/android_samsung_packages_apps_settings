package com.google.zxing;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class EncodeHintType {
    public static final /* synthetic */ EncodeHintType[] $VALUES;
    public static final EncodeHintType AZTEC_LAYERS;
    public static final EncodeHintType CHARACTER_SET;
    public static final EncodeHintType CODE128_COMPACT;
    public static final EncodeHintType DATA_MATRIX_COMPACT;
    public static final EncodeHintType DATA_MATRIX_SHAPE;
    public static final EncodeHintType ERROR_CORRECTION;
    public static final EncodeHintType FORCE_C40;
    public static final EncodeHintType FORCE_CODE_SET;
    public static final EncodeHintType GS1_FORMAT;
    public static final EncodeHintType MARGIN;
    public static final EncodeHintType MAX_SIZE;
    public static final EncodeHintType MIN_SIZE;
    public static final EncodeHintType PDF417_AUTO_ECI;
    public static final EncodeHintType PDF417_COMPACT;
    public static final EncodeHintType PDF417_COMPACTION;
    public static final EncodeHintType PDF417_DIMENSIONS;
    public static final EncodeHintType QR_COMPACT;
    public static final EncodeHintType QR_MASK_PATTERN;
    public static final EncodeHintType QR_VERSION;

    static {
        EncodeHintType encodeHintType = new EncodeHintType("ERROR_CORRECTION", 0);
        ERROR_CORRECTION = encodeHintType;
        EncodeHintType encodeHintType2 = new EncodeHintType("CHARACTER_SET", 1);
        CHARACTER_SET = encodeHintType2;
        EncodeHintType encodeHintType3 = new EncodeHintType("DATA_MATRIX_SHAPE", 2);
        DATA_MATRIX_SHAPE = encodeHintType3;
        EncodeHintType encodeHintType4 = new EncodeHintType("DATA_MATRIX_COMPACT", 3);
        DATA_MATRIX_COMPACT = encodeHintType4;
        EncodeHintType encodeHintType5 = new EncodeHintType("MIN_SIZE", 4);
        MIN_SIZE = encodeHintType5;
        EncodeHintType encodeHintType6 = new EncodeHintType("MAX_SIZE", 5);
        MAX_SIZE = encodeHintType6;
        EncodeHintType encodeHintType7 = new EncodeHintType("MARGIN", 6);
        MARGIN = encodeHintType7;
        EncodeHintType encodeHintType8 = new EncodeHintType("PDF417_COMPACT", 7);
        PDF417_COMPACT = encodeHintType8;
        EncodeHintType encodeHintType9 = new EncodeHintType("PDF417_COMPACTION", 8);
        PDF417_COMPACTION = encodeHintType9;
        EncodeHintType encodeHintType10 = new EncodeHintType("PDF417_DIMENSIONS", 9);
        PDF417_DIMENSIONS = encodeHintType10;
        EncodeHintType encodeHintType11 = new EncodeHintType("PDF417_AUTO_ECI", 10);
        PDF417_AUTO_ECI = encodeHintType11;
        EncodeHintType encodeHintType12 = new EncodeHintType("AZTEC_LAYERS", 11);
        AZTEC_LAYERS = encodeHintType12;
        EncodeHintType encodeHintType13 = new EncodeHintType("QR_VERSION", 12);
        QR_VERSION = encodeHintType13;
        EncodeHintType encodeHintType14 = new EncodeHintType("QR_MASK_PATTERN", 13);
        QR_MASK_PATTERN = encodeHintType14;
        EncodeHintType encodeHintType15 = new EncodeHintType("QR_COMPACT", 14);
        QR_COMPACT = encodeHintType15;
        EncodeHintType encodeHintType16 = new EncodeHintType("GS1_FORMAT", 15);
        GS1_FORMAT = encodeHintType16;
        EncodeHintType encodeHintType17 = new EncodeHintType("FORCE_CODE_SET", 16);
        FORCE_CODE_SET = encodeHintType17;
        EncodeHintType encodeHintType18 = new EncodeHintType("FORCE_C40", 17);
        FORCE_C40 = encodeHintType18;
        EncodeHintType encodeHintType19 = new EncodeHintType("CODE128_COMPACT", 18);
        CODE128_COMPACT = encodeHintType19;
        $VALUES =
                new EncodeHintType[] {
                    encodeHintType,
                    encodeHintType2,
                    encodeHintType3,
                    encodeHintType4,
                    encodeHintType5,
                    encodeHintType6,
                    encodeHintType7,
                    encodeHintType8,
                    encodeHintType9,
                    encodeHintType10,
                    encodeHintType11,
                    encodeHintType12,
                    encodeHintType13,
                    encodeHintType14,
                    encodeHintType15,
                    encodeHintType16,
                    encodeHintType17,
                    encodeHintType18,
                    encodeHintType19
                };
    }

    public static EncodeHintType valueOf(String str) {
        return (EncodeHintType) Enum.valueOf(EncodeHintType.class, str);
    }

    public static EncodeHintType[] values() {
        return (EncodeHintType[]) $VALUES.clone();
    }
}
