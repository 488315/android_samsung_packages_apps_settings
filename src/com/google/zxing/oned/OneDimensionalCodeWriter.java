package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.common.BitMatrix;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class OneDimensionalCodeWriter implements Writer {
    public static final Pattern NUMERIC = Pattern.compile("[0-9]+");

    public static int appendPattern(boolean[] zArr, int i, int[] iArr, boolean z) {
        int i2 = 0;
        for (int i3 : iArr) {
            int i4 = 0;
            while (i4 < i3) {
                zArr[i] = z;
                i4++;
                i++;
            }
            i2 += i3;
            z = !z;
        }
        return i2;
    }

    public static void checkNumeric(String str) {
        if (!NUMERIC.matcher(str).matches()) {
            throw new IllegalArgumentException("Input should only contain digits 0-9");
        }
    }

    public abstract boolean[] encode(String str);

    public boolean[] encode(String str, Map map) {
        return encode(str);
    }

    public int getDefaultMargin() {
        return 10;
    }

    public abstract Collection getSupportedWriteFormats();

    @Override // com.google.zxing.Writer
    public final BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map map) {
        if (str.isEmpty()) {
            throw new IllegalArgumentException("Found empty contents");
        }
        if (i < 0 || i2 < 0) {
            throw new IllegalArgumentException(
                    "Negative size is not allowed. Input: " + i + 'x' + i2);
        }
        Collection supportedWriteFormats = getSupportedWriteFormats();
        if (supportedWriteFormats != null && !supportedWriteFormats.contains(barcodeFormat)) {
            throw new IllegalArgumentException(
                    "Can only encode " + supportedWriteFormats + ", but got " + barcodeFormat);
        }
        int defaultMargin = getDefaultMargin();
        if (map != null) {
            EncodeHintType encodeHintType = EncodeHintType.MARGIN;
            EnumMap enumMap = (EnumMap) map;
            if (enumMap.containsKey(encodeHintType)) {
                defaultMargin = Integer.parseInt(enumMap.get(encodeHintType).toString());
            }
        }
        boolean[] encode = encode(str, map);
        int length = encode.length;
        int i3 = defaultMargin + length;
        int max = Math.max(i, i3);
        int max2 = Math.max(1, i2);
        int i4 = max / i3;
        int i5 = (max - (length * i4)) / 2;
        BitMatrix bitMatrix = new BitMatrix(max, max2);
        int i6 = 0;
        while (i6 < length) {
            if (encode[i6]) {
                bitMatrix.setRegion(i5, 0, i4, max2);
            }
            i6++;
            i5 += i4;
        }
        return bitMatrix;
    }
}
