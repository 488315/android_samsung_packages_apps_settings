package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.common.BitArray;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class GeneralAppIdDecoder {
    public final StringBuilder buffer;
    public final CurrentParsingState current;
    public final BitArray information;

    public GeneralAppIdDecoder(BitArray bitArray) {
        CurrentParsingState currentParsingState = new CurrentParsingState();
        currentParsingState.position = 0;
        currentParsingState.encoding = CurrentParsingState.State.NUMERIC;
        this.current = currentParsingState;
        this.buffer = new StringBuilder();
        this.information = bitArray;
    }

    public static int extractNumericValueFromBitArray(int i, int i2, BitArray bitArray) {
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            if (bitArray.get(i + i4)) {
                i3 |= 1 << ((i2 - i4) - 1);
            }
        }
        return i3;
    }

    public final String decodeAllCodes(int i, StringBuilder sb) {
        String str = null;
        while (true) {
            DecodedInformation decodeGeneralPurposeField = decodeGeneralPurposeField(i, str);
            String parseFieldsInGeneralPurpose =
                    FieldParser.parseFieldsInGeneralPurpose(decodeGeneralPurposeField.newString);
            if (parseFieldsInGeneralPurpose != null) {
                sb.append(parseFieldsInGeneralPurpose);
            }
            String valueOf =
                    decodeGeneralPurposeField.remaining
                            ? String.valueOf(decodeGeneralPurposeField.remainingValue)
                            : null;
            int i2 = decodeGeneralPurposeField.newPosition;
            if (i == i2) {
                return sb.toString();
            }
            i = i2;
            str = valueOf;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:150:0x0216, code lost:

       if (r5 <= r9.size) goto L134;
    */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x0219, code lost:

       if (r3 >= r5) goto L232;
    */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x021f, code lost:

       if (r9.get(r3) == false) goto L144;
    */
    /* JADX WARN: Code restructure failed: missing block: B:154:0x023b, code lost:

       r3 = r3 + 1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x023e, code lost:

       r1.position += 3;
       r1.encoding = r8;
    */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x0246, code lost:

       r3 = new com.google.zxing.oned.rss.expanded.decoders.BlockParsedResult();
    */
    /* JADX WARN: Code restructure failed: missing block: B:160:0x0227, code lost:

       if (isAlphaTo646ToAlphaLatch(r1.position) == false) goto L146;
    */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x0229, code lost:

       r3 = r1.position;
       r5 = r3 + 5;
       r7 = r9.size;
    */
    /* JADX WARN: Code restructure failed: missing block: B:162:0x022f, code lost:

       if (r5 >= r7) goto L142;
    */
    /* JADX WARN: Code restructure failed: missing block: B:163:0x0231, code lost:

       r1.position = r3 + 5;
    */
    /* JADX WARN: Code restructure failed: missing block: B:164:0x0238, code lost:

       r1.encoding = r6;
    */
    /* JADX WARN: Code restructure failed: missing block: B:165:0x0236, code lost:

       r1.position = r7;
    */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00d6, code lost:

       if (r5 <= r9.size) goto L50;
    */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00d9, code lost:

       if (r3 >= r5) goto L224;
    */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00df, code lost:

       if (r9.get(r3) == false) goto L60;
    */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x00fb, code lost:

       r3 = r3 + 1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x00fe, code lost:

       r1.position += 3;
       r1.encoding = r8;
    */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0106, code lost:

       r3 = new com.google.zxing.oned.rss.expanded.decoders.BlockParsedResult();
    */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x00e7, code lost:

       if (isAlphaTo646ToAlphaLatch(r1.position) == false) goto L62;
    */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x00e9, code lost:

       r3 = r1.position;
       r5 = r3 + 5;
       r6 = r9.size;
    */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x00ef, code lost:

       if (r5 >= r6) goto L58;
    */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x00f1, code lost:

       r1.position = r3 + 5;
    */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x00f8, code lost:

       r1.encoding = r7;
    */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x00f6, code lost:

       r1.position = r6;
    */
    /* JADX WARN: Removed duplicated region for block: B:177:0x0275  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x00cb A[LOOP:1: B:7:0x0030->B:18:0x00cb, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00bb A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:202:0x02f0 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.google.zxing.oned.rss.expanded.decoders.DecodedInformation
            decodeGeneralPurposeField(int r19, java.lang.String r20) {
        /*
            Method dump skipped, instructions count: 898
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.zxing.oned.rss.expanded.decoders.GeneralAppIdDecoder.decodeGeneralPurposeField(int,"
                    + " java.lang.String):com.google.zxing.oned.rss.expanded.decoders.DecodedInformation");
    }

    public final boolean isAlphaTo646ToAlphaLatch(int i) {
        int i2;
        int i3 = i + 1;
        BitArray bitArray = this.information;
        if (i3 > bitArray.size) {
            return false;
        }
        for (int i4 = 0; i4 < 5 && (i2 = i4 + i) < bitArray.size; i4++) {
            if (i4 == 2) {
                if (!bitArray.get(i + 2)) {
                    return false;
                }
            } else if (bitArray.get(i2)) {
                return false;
            }
        }
        return true;
    }
}
