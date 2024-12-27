package com.google.zxing.datamatrix.decoder;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class Version {
    public static final Version[] VERSIONS = {
        new Version(1, 10, 10, 8, 8, new ECBlocks(5, new ECB(1, 3))),
        new Version(2, 12, 12, 10, 10, new ECBlocks(7, new ECB(1, 5))),
        new Version(3, 14, 14, 12, 12, new ECBlocks(10, new ECB(1, 8))),
        new Version(4, 16, 16, 14, 14, new ECBlocks(12, new ECB(1, 12))),
        new Version(5, 18, 18, 16, 16, new ECBlocks(14, new ECB(1, 18))),
        new Version(6, 20, 20, 18, 18, new ECBlocks(18, new ECB(1, 22))),
        new Version(7, 22, 22, 20, 20, new ECBlocks(20, new ECB(1, 30))),
        new Version(8, 24, 24, 22, 22, new ECBlocks(24, new ECB(1, 36))),
        new Version(9, 26, 26, 24, 24, new ECBlocks(28, new ECB(1, 44))),
        new Version(10, 32, 32, 14, 14, new ECBlocks(36, new ECB(1, 62))),
        new Version(11, 36, 36, 16, 16, new ECBlocks(42, new ECB(1, 86))),
        new Version(12, 40, 40, 18, 18, new ECBlocks(48, new ECB(1, 114))),
        new Version(13, 44, 44, 20, 20, new ECBlocks(56, new ECB(1, 144))),
        new Version(14, 48, 48, 22, 22, new ECBlocks(68, new ECB(1, 174))),
        new Version(15, 52, 52, 24, 24, new ECBlocks(42, new ECB(2, 102))),
        new Version(16, 64, 64, 14, 14, new ECBlocks(56, new ECB(2, 140))),
        new Version(17, 72, 72, 16, 16, new ECBlocks(36, new ECB(4, 92))),
        new Version(18, 80, 80, 18, 18, new ECBlocks(48, new ECB(4, 114))),
        new Version(19, 88, 88, 20, 20, new ECBlocks(56, new ECB(4, 144))),
        new Version(20, 96, 96, 22, 22, new ECBlocks(68, new ECB(4, 174))),
        new Version(21, 104, 104, 24, 24, new ECBlocks(56, new ECB(6, 136))),
        new Version(22, 120, 120, 18, 18, new ECBlocks(68, new ECB(6, 175))),
        new Version(23, 132, 132, 20, 20, new ECBlocks(62, new ECB(8, 163))),
        new Version(24, 144, 144, 22, 22, new ECBlocks(new ECB(8, 156), new ECB(2, 155))),
        new Version(25, 8, 18, 6, 16, new ECBlocks(7, new ECB(1, 5))),
        new Version(26, 8, 32, 6, 14, new ECBlocks(11, new ECB(1, 10))),
        new Version(27, 12, 26, 10, 24, new ECBlocks(14, new ECB(1, 16))),
        new Version(28, 12, 36, 10, 16, new ECBlocks(18, new ECB(1, 22))),
        new Version(29, 16, 36, 14, 16, new ECBlocks(24, new ECB(1, 32))),
        new Version(30, 16, 48, 14, 22, new ECBlocks(28, new ECB(1, 49))),
        new Version(31, 8, 48, 6, 22, new ECBlocks(15, new ECB(1, 18))),
        new Version(32, 8, 64, 6, 14, new ECBlocks(18, new ECB(1, 24))),
        new Version(33, 8, 80, 6, 18, new ECBlocks(22, new ECB(1, 32))),
        new Version(34, 8, 96, 6, 22, new ECBlocks(28, new ECB(1, 38))),
        new Version(35, 8, 120, 6, 18, new ECBlocks(32, new ECB(1, 49))),
        new Version(36, 8, 144, 6, 22, new ECBlocks(36, new ECB(1, 63))),
        new Version(37, 12, 64, 10, 14, new ECBlocks(27, new ECB(1, 43))),
        new Version(38, 12, 88, 10, 20, new ECBlocks(36, new ECB(1, 64))),
        new Version(39, 16, 64, 14, 14, new ECBlocks(36, new ECB(1, 62))),
        new Version(40, 20, 36, 18, 16, new ECBlocks(28, new ECB(1, 44))),
        new Version(41, 20, 44, 18, 20, new ECBlocks(34, new ECB(1, 56))),
        new Version(42, 20, 64, 18, 14, new ECBlocks(42, new ECB(1, 84))),
        new Version(43, 22, 48, 20, 22, new ECBlocks(38, new ECB(1, 72))),
        new Version(44, 24, 48, 22, 22, new ECBlocks(41, new ECB(1, 80))),
        new Version(45, 24, 64, 22, 14, new ECBlocks(46, new ECB(1, 108))),
        new Version(46, 26, 40, 24, 18, new ECBlocks(38, new ECB(1, 70))),
        new Version(47, 26, 48, 24, 22, new ECBlocks(42, new ECB(1, 90))),
        new Version(48, 26, 64, 24, 14, new ECBlocks(50, new ECB(1, 118)))
    };
    public final int dataRegionSizeColumns;
    public final int dataRegionSizeRows;
    public final ECBlocks ecBlocks;
    public final int symbolSizeColumns;
    public final int symbolSizeRows;
    public final int totalCodewords;
    public final int versionNumber;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ECB {
        public final int count;
        public final int dataCodewords;

        public ECB(int i, int i2) {
            this.count = i;
            this.dataCodewords = i2;
        }
    }

    public Version(int i, int i2, int i3, int i4, int i5, ECBlocks eCBlocks) {
        this.versionNumber = i;
        this.symbolSizeRows = i2;
        this.symbolSizeColumns = i3;
        this.dataRegionSizeRows = i4;
        this.dataRegionSizeColumns = i5;
        this.ecBlocks = eCBlocks;
        int i6 = 0;
        for (ECB ecb : eCBlocks.ecBlocks) {
            i6 += (ecb.dataCodewords + eCBlocks.ecCodewords) * ecb.count;
        }
        this.totalCodewords = i6;
    }

    public final String toString() {
        return String.valueOf(this.versionNumber);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ECBlocks {
        public final ECB[] ecBlocks;
        public final int ecCodewords;

        public ECBlocks(int i, ECB ecb) {
            this.ecCodewords = i;
            this.ecBlocks = new ECB[] {ecb};
        }

        public ECBlocks(ECB ecb, ECB ecb2) {
            this.ecCodewords = 62;
            this.ecBlocks = new ECB[] {ecb, ecb2};
        }
    }
}
