package com.google.zxing.oned;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;

import com.google.zxing.BarcodeFormat;

import java.util.Collection;
import java.util.Collections;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ITFWriter extends OneDimensionalCodeWriter {
    public static final int[] START_PATTERN = {1, 1, 1, 1};
    public static final int[] END_PATTERN = {3, 1, 1};
    public static final int[][] PATTERNS = {
        new int[] {1, 1, 3, 3, 1},
        new int[] {3, 1, 1, 1, 3},
        new int[] {1, 3, 1, 1, 3},
        new int[] {3, 3, 1, 1, 1},
        new int[] {1, 1, 3, 1, 3},
        new int[] {3, 1, 3, 1, 1},
        new int[] {1, 3, 3, 1, 1},
        new int[] {1, 1, 1, 3, 3},
        new int[] {3, 1, 1, 3, 1},
        new int[] {1, 3, 1, 3, 1}
    };

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter
    public final boolean[] encode(String str) {
        int length = str.length();
        if (length % 2 != 0) {
            throw new IllegalArgumentException("The length of the input should be even");
        }
        if (length > 80) {
            throw new IllegalArgumentException(
                    SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                            length,
                            "Requested contents should be less than 80 digits long, but got "));
        }
        OneDimensionalCodeWriter.checkNumeric(str);
        boolean[] zArr = new boolean[(length * 9) + 9];
        int appendPattern = OneDimensionalCodeWriter.appendPattern(zArr, 0, START_PATTERN, true);
        for (int i = 0; i < length; i += 2) {
            int digit = Character.digit(str.charAt(i), 10);
            int digit2 = Character.digit(str.charAt(i + 1), 10);
            int[] iArr = new int[10];
            for (int i2 = 0; i2 < 5; i2++) {
                int i3 = i2 * 2;
                int[][] iArr2 = PATTERNS;
                iArr[i3] = iArr2[digit][i2];
                iArr[i3 + 1] = iArr2[digit2][i2];
            }
            appendPattern +=
                    OneDimensionalCodeWriter.appendPattern(zArr, appendPattern, iArr, true);
        }
        OneDimensionalCodeWriter.appendPattern(zArr, appendPattern, END_PATTERN, true);
        return zArr;
    }

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter
    public final Collection getSupportedWriteFormats() {
        return Collections.singleton(BarcodeFormat.ITF);
    }
}
