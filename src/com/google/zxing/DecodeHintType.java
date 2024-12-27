package com.google.zxing;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public enum DecodeHintType {
    /* JADX INFO: Fake field, exist only in values array */
    EF0("OTHER"),
    PURE_BARCODE("PURE_BARCODE"),
    POSSIBLE_FORMATS("POSSIBLE_FORMATS"),
    TRY_HARDER("TRY_HARDER"),
    CHARACTER_SET("CHARACTER_SET"),
    ALLOWED_LENGTHS("ALLOWED_LENGTHS"),
    ASSUME_CODE_39_CHECK_DIGIT("ASSUME_CODE_39_CHECK_DIGIT"),
    ASSUME_GS1("ASSUME_GS1"),
    RETURN_CODABAR_START_END("RETURN_CODABAR_START_END"),
    NEED_RESULT_POINT_CALLBACK("NEED_RESULT_POINT_CALLBACK"),
    ALLOWED_EAN_EXTENSIONS("ALLOWED_EAN_EXTENSIONS"),
    ALSO_INVERTED("ALSO_INVERTED");

    private final Class<?> valueType;

    DecodeHintType(String str) {
        this.valueType = r2;
    }
}
