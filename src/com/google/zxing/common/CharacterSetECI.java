package com.google.zxing.common;

import com.google.zxing.FormatException;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public enum CharacterSetECI {
    /* JADX INFO: Fake field, exist only in values array */
    Cp437(new int[] {0, 2}, new String[0]),
    /* JADX INFO: Fake field, exist only in values array */
    ISO8859_1(new int[] {1, 3}, "ISO-8859-1"),
    /* JADX INFO: Fake field, exist only in values array */
    ISO8859_2(2, 4),
    /* JADX INFO: Fake field, exist only in values array */
    ISO8859_3(3, 5),
    /* JADX INFO: Fake field, exist only in values array */
    ISO8859_4(4, 6),
    /* JADX INFO: Fake field, exist only in values array */
    ISO8859_5(5, 7),
    /* JADX INFO: Fake field, exist only in values array */
    ISO8859_6(6, 8),
    /* JADX INFO: Fake field, exist only in values array */
    ISO8859_7(7, 9),
    /* JADX INFO: Fake field, exist only in values array */
    ISO8859_8(8, 10),
    /* JADX INFO: Fake field, exist only in values array */
    ISO8859_9(9, 11),
    /* JADX INFO: Fake field, exist only in values array */
    ISO8859_10(10, 12),
    /* JADX INFO: Fake field, exist only in values array */
    ISO8859_11(11, 13),
    /* JADX INFO: Fake field, exist only in values array */
    ISO8859_13(12, 15),
    /* JADX INFO: Fake field, exist only in values array */
    ISO8859_14(13, 16),
    /* JADX INFO: Fake field, exist only in values array */
    ISO8859_15(14, 17),
    /* JADX INFO: Fake field, exist only in values array */
    ISO8859_16(15, 18),
    /* JADX INFO: Fake field, exist only in values array */
    SJIS(16, 20),
    /* JADX INFO: Fake field, exist only in values array */
    Cp1250(17, 21),
    /* JADX INFO: Fake field, exist only in values array */
    Cp1251(18, 22),
    /* JADX INFO: Fake field, exist only in values array */
    Cp1252(19, 23),
    /* JADX INFO: Fake field, exist only in values array */
    Cp1256(20, 24),
    /* JADX INFO: Fake field, exist only in values array */
    UnicodeBigUnmarked(21, 25),
    /* JADX INFO: Fake field, exist only in values array */
    UTF8(22, 26),
    /* JADX INFO: Fake field, exist only in values array */
    ASCII(new int[] {27, 170}, "US-ASCII"),
    /* JADX INFO: Fake field, exist only in values array */
    Big5(new int[] {28}, new String[0]),
    /* JADX INFO: Fake field, exist only in values array */
    GB18030(25, 29),
    /* JADX INFO: Fake field, exist only in values array */
    EUC_KR(26, 30);

    private final String[] otherEncodingNames;
    private final int[] values;
    public static final Map VALUE_TO_ECI = new HashMap();
    public static final Map NAME_TO_ECI = new HashMap();

    static {
        for (CharacterSetECI characterSetECI : values()) {
            if (Charset.isSupported(characterSetECI.name())) {
                for (int i : characterSetECI.values) {
                    ((HashMap) VALUE_TO_ECI).put(Integer.valueOf(i), characterSetECI);
                }
                ((HashMap) NAME_TO_ECI).put(characterSetECI.name(), characterSetECI);
                for (String str : characterSetECI.otherEncodingNames) {
                    ((HashMap) NAME_TO_ECI).put(str, characterSetECI);
                }
            }
        }
    }

    CharacterSetECI(int i, int i2) {
        this.values = new int[] {i2};
        this.otherEncodingNames = r2;
    }

    public static CharacterSetECI getCharacterSetECI(Charset charset) {
        return (CharacterSetECI) ((HashMap) NAME_TO_ECI).get(charset.name());
    }

    public static CharacterSetECI getCharacterSetECIByValue(int i) {
        if (i < 0 || i >= 900) {
            throw FormatException.getFormatInstance();
        }
        return (CharacterSetECI) ((HashMap) VALUE_TO_ECI).get(Integer.valueOf(i));
    }

    public final int getValue() {
        return this.values[0];
    }

    CharacterSetECI(int[] iArr, String... strArr) {
        this.values = iArr;
        this.otherEncodingNames = strArr;
    }
}
