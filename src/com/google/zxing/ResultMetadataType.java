package com.google.zxing;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ResultMetadataType {
    public static final /* synthetic */ ResultMetadataType[] $VALUES;
    public static final ResultMetadataType BYTE_SEGMENTS;
    public static final ResultMetadataType ERASURES_CORRECTED;
    public static final ResultMetadataType ERRORS_CORRECTED;
    public static final ResultMetadataType ERROR_CORRECTION_LEVEL;
    public static final ResultMetadataType ISSUE_NUMBER;
    public static final ResultMetadataType ORIENTATION;
    public static final ResultMetadataType PDF417_EXTRA_METADATA;
    public static final ResultMetadataType POSSIBLE_COUNTRY;
    public static final ResultMetadataType STRUCTURED_APPEND_PARITY;
    public static final ResultMetadataType STRUCTURED_APPEND_SEQUENCE;
    public static final ResultMetadataType SUGGESTED_PRICE;
    public static final ResultMetadataType SYMBOLOGY_IDENTIFIER;
    public static final ResultMetadataType UPC_EAN_EXTENSION;

    /* JADX INFO: Fake field, exist only in values array */
    ResultMetadataType EF0;

    static {
        ResultMetadataType resultMetadataType = new ResultMetadataType("OTHER", 0);
        ResultMetadataType resultMetadataType2 = new ResultMetadataType("ORIENTATION", 1);
        ORIENTATION = resultMetadataType2;
        ResultMetadataType resultMetadataType3 = new ResultMetadataType("BYTE_SEGMENTS", 2);
        BYTE_SEGMENTS = resultMetadataType3;
        ResultMetadataType resultMetadataType4 =
                new ResultMetadataType("ERROR_CORRECTION_LEVEL", 3);
        ERROR_CORRECTION_LEVEL = resultMetadataType4;
        ResultMetadataType resultMetadataType5 = new ResultMetadataType("ERRORS_CORRECTED", 4);
        ERRORS_CORRECTED = resultMetadataType5;
        ResultMetadataType resultMetadataType6 = new ResultMetadataType("ERASURES_CORRECTED", 5);
        ERASURES_CORRECTED = resultMetadataType6;
        ResultMetadataType resultMetadataType7 = new ResultMetadataType("ISSUE_NUMBER", 6);
        ISSUE_NUMBER = resultMetadataType7;
        ResultMetadataType resultMetadataType8 = new ResultMetadataType("SUGGESTED_PRICE", 7);
        SUGGESTED_PRICE = resultMetadataType8;
        ResultMetadataType resultMetadataType9 = new ResultMetadataType("POSSIBLE_COUNTRY", 8);
        POSSIBLE_COUNTRY = resultMetadataType9;
        ResultMetadataType resultMetadataType10 = new ResultMetadataType("UPC_EAN_EXTENSION", 9);
        UPC_EAN_EXTENSION = resultMetadataType10;
        ResultMetadataType resultMetadataType11 =
                new ResultMetadataType("PDF417_EXTRA_METADATA", 10);
        PDF417_EXTRA_METADATA = resultMetadataType11;
        ResultMetadataType resultMetadataType12 =
                new ResultMetadataType("STRUCTURED_APPEND_SEQUENCE", 11);
        STRUCTURED_APPEND_SEQUENCE = resultMetadataType12;
        ResultMetadataType resultMetadataType13 =
                new ResultMetadataType("STRUCTURED_APPEND_PARITY", 12);
        STRUCTURED_APPEND_PARITY = resultMetadataType13;
        ResultMetadataType resultMetadataType14 =
                new ResultMetadataType("SYMBOLOGY_IDENTIFIER", 13);
        SYMBOLOGY_IDENTIFIER = resultMetadataType14;
        $VALUES =
                new ResultMetadataType[] {
                    resultMetadataType,
                    resultMetadataType2,
                    resultMetadataType3,
                    resultMetadataType4,
                    resultMetadataType5,
                    resultMetadataType6,
                    resultMetadataType7,
                    resultMetadataType8,
                    resultMetadataType9,
                    resultMetadataType10,
                    resultMetadataType11,
                    resultMetadataType12,
                    resultMetadataType13,
                    resultMetadataType14
                };
    }

    public static ResultMetadataType valueOf(String str) {
        return (ResultMetadataType) Enum.valueOf(ResultMetadataType.class, str);
    }

    public static ResultMetadataType[] values() {
        return (ResultMetadataType[]) $VALUES.clone();
    }
}
