package com.google.zxing.pdf417.decoder;

import java.util.Formatter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class DetectionResultColumn {
    public final BoundingBox boundingBox;
    public final Codeword[] codewords;

    public DetectionResultColumn(BoundingBox boundingBox) {
        this.boundingBox = new BoundingBox(boundingBox);
        this.codewords = new Codeword[(boundingBox.maxY - boundingBox.minY) + 1];
    }

    public final Codeword getCodewordNearby(int i) {
        Codeword codeword;
        Codeword codeword2;
        int imageRowToCodewordIndex = imageRowToCodewordIndex(i);
        Codeword[] codewordArr = this.codewords;
        Codeword codeword3 = codewordArr[imageRowToCodewordIndex];
        if (codeword3 != null) {
            return codeword3;
        }
        for (int i2 = 1; i2 < 5; i2++) {
            int imageRowToCodewordIndex2 = imageRowToCodewordIndex(i) - i2;
            if (imageRowToCodewordIndex2 >= 0
                    && (codeword2 = codewordArr[imageRowToCodewordIndex2]) != null) {
                return codeword2;
            }
            int imageRowToCodewordIndex3 = imageRowToCodewordIndex(i) + i2;
            if (imageRowToCodewordIndex3 < codewordArr.length
                    && (codeword = codewordArr[imageRowToCodewordIndex3]) != null) {
                return codeword;
            }
        }
        return null;
    }

    public final int imageRowToCodewordIndex(int i) {
        return i - this.boundingBox.minY;
    }

    public String toString() {
        Formatter formatter = new Formatter();
        try {
            int i = 0;
            for (Codeword codeword : this.codewords) {
                if (codeword == null) {
                    formatter.format("%3d:    |   %n", Integer.valueOf(i));
                    i++;
                } else {
                    formatter.format(
                            "%3d: %3d|%3d%n",
                            Integer.valueOf(i),
                            Integer.valueOf(codeword.rowNumber),
                            Integer.valueOf(codeword.value));
                    i++;
                }
            }
            String formatter2 = formatter.toString();
            formatter.close();
            return formatter2;
        } catch (Throwable th) {
            try {
                formatter.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }
}
