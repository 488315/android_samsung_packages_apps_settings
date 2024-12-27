package com.google.zxing.datamatrix.decoder;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class DecodedBitStreamParser {
    public static final char[] C40_SHIFT2_SET_CHARS;
    public static final char[] TEXT_SHIFT2_SET_CHARS;
    public static final char[] C40_BASIC_SET_CHARS = {
        '*', '*', '*', ' ', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
        'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
        'W', 'X', 'Y', 'Z'
    };
    public static final char[] TEXT_BASIC_SET_CHARS = {
        '*', '*', '*', ' ', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
        'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
        'w', 'x', 'y', 'z'
    };
    public static final char[] TEXT_SHIFT3_SET_CHARS = {
        '`', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
        'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '{', '|', '}', '~', 127
    };

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    final class Mode {
        public static final /* synthetic */ Mode[] $VALUES;
        public static final Mode ANSIX12_ENCODE;
        public static final Mode ASCII_ENCODE;
        public static final Mode BASE256_ENCODE;
        public static final Mode C40_ENCODE;
        public static final Mode ECI_ENCODE;
        public static final Mode EDIFACT_ENCODE;
        public static final Mode PAD_ENCODE;
        public static final Mode TEXT_ENCODE;

        static {
            Mode mode = new Mode("PAD_ENCODE", 0);
            PAD_ENCODE = mode;
            Mode mode2 = new Mode("ASCII_ENCODE", 1);
            ASCII_ENCODE = mode2;
            Mode mode3 = new Mode("C40_ENCODE", 2);
            C40_ENCODE = mode3;
            Mode mode4 = new Mode("TEXT_ENCODE", 3);
            TEXT_ENCODE = mode4;
            Mode mode5 = new Mode("ANSIX12_ENCODE", 4);
            ANSIX12_ENCODE = mode5;
            Mode mode6 = new Mode("EDIFACT_ENCODE", 5);
            EDIFACT_ENCODE = mode6;
            Mode mode7 = new Mode("BASE256_ENCODE", 6);
            BASE256_ENCODE = mode7;
            Mode mode8 = new Mode("ECI_ENCODE", 7);
            ECI_ENCODE = mode8;
            $VALUES = new Mode[] {mode, mode2, mode3, mode4, mode5, mode6, mode7, mode8};
        }

        public static Mode valueOf(String str) {
            return (Mode) Enum.valueOf(Mode.class, str);
        }

        public static Mode[] values() {
            return (Mode[]) $VALUES.clone();
        }
    }

    static {
        char[] cArr = {
            '!', '\"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', ':', ';',
            '<', '=', '>', '?', '@', '[', '\\', ']', '^', '_'
        };
        C40_SHIFT2_SET_CHARS = cArr;
        TEXT_SHIFT2_SET_CHARS = cArr;
    }

    public static void parseTwoBytes(int i, int i2, int[] iArr) {
        int i3 = ((i << 8) + i2) - 1;
        int i4 = i3 / 1600;
        iArr[0] = i4;
        int i5 = i3 - (i4 * 1600);
        int i6 = i5 / 40;
        iArr[1] = i6;
        iArr[2] = i5 - (i6 * 40);
    }

    public static int unrandomize255State(int i, int i2) {
        int i3 = i - (((i2 * 149) % 255) + 1);
        return i3 >= 0 ? i3 : i3 + 256;
    }
}
