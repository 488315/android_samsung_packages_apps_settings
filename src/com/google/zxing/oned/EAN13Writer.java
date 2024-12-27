package com.google.zxing.oned;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.FormatException;

import java.util.Collection;
import java.util.Collections;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class EAN13Writer extends UPCEANWriter {
    @Override // com.google.zxing.oned.OneDimensionalCodeWriter
    public final boolean[] encode(String str) {
        int length = str.length();
        if (length == 12) {
            try {
                str = str + UPCEANReader.getStandardUPCEANChecksum(str);
            } catch (FormatException e) {
                throw new IllegalArgumentException(e);
            }
        } else {
            if (length != 13) {
                throw new IllegalArgumentException(
                        SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                length,
                                "Requested contents should be 12 or 13 digits long, but got "));
            }
            try {
                if (!UPCEANReader.checkStandardUPCEANChecksum(str)) {
                    throw new IllegalArgumentException("Contents do not pass checksum");
                }
            } catch (FormatException unused) {
                throw new IllegalArgumentException("Illegal contents");
            }
        }
        OneDimensionalCodeWriter.checkNumeric(str);
        int i = EAN13Reader.FIRST_DIGIT_ENCODINGS[Character.digit(str.charAt(0), 10)];
        boolean[] zArr = new boolean[95];
        int appendPattern =
                OneDimensionalCodeWriter.appendPattern(
                        zArr, 0, UPCEANReader.START_END_PATTERN, true);
        for (int i2 = 1; i2 <= 6; i2++) {
            int digit = Character.digit(str.charAt(i2), 10);
            if (((i >> (6 - i2)) & 1) == 1) {
                digit += 10;
            }
            appendPattern +=
                    OneDimensionalCodeWriter.appendPattern(
                            zArr, appendPattern, UPCEANReader.L_AND_G_PATTERNS[digit], false);
        }
        int appendPattern2 =
                OneDimensionalCodeWriter.appendPattern(
                                zArr, appendPattern, UPCEANReader.MIDDLE_PATTERN, false)
                        + appendPattern;
        for (int i3 = 7; i3 <= 12; i3++) {
            appendPattern2 +=
                    OneDimensionalCodeWriter.appendPattern(
                            zArr,
                            appendPattern2,
                            UPCEANReader.L_PATTERNS[Character.digit(str.charAt(i3), 10)],
                            true);
        }
        OneDimensionalCodeWriter.appendPattern(
                zArr, appendPattern2, UPCEANReader.START_END_PATTERN, true);
        return zArr;
    }

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter
    public final Collection getSupportedWriteFormats() {
        return Collections.singleton(BarcodeFormat.EAN_13);
    }
}
