package com.google.zxing.qrcode.decoder;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public enum Mode {
    TERMINATOR(new int[] {0, 0, 0}, 0),
    NUMERIC(new int[] {10, 12, 14}, 1),
    ALPHANUMERIC(new int[] {9, 11, 13}, 2),
    STRUCTURED_APPEND(new int[] {0, 0, 0}, 3),
    BYTE(new int[] {8, 16, 16}, 4),
    ECI(new int[] {0, 0, 0}, 7),
    KANJI(new int[] {8, 10, 12}, 8),
    FNC1_FIRST_POSITION(new int[] {0, 0, 0}, 5),
    FNC1_SECOND_POSITION(new int[] {0, 0, 0}, 9),
    HANZI(new int[] {8, 10, 12}, 13);

    private final int bits;
    private final int[] characterCountBitsForVersions;

    Mode(int[] iArr, int i) {
        this.characterCountBitsForVersions = iArr;
        this.bits = i;
    }

    public final int getBits() {
        return this.bits;
    }

    public final int getCharacterCountBits(Version version) {
        int i = version.versionNumber;
        return this.characterCountBitsForVersions[
                i <= 9 ? (char) 0 : i <= 26 ? (char) 1 : (char) 2];
    }
}
