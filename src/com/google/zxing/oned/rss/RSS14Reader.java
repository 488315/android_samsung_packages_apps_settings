package com.google.zxing.oned.rss;

import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.OneDReader;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class RSS14Reader extends AbstractRSSReader {
    public final List possibleLeftPairs = new ArrayList();
    public final List possibleRightPairs = new ArrayList();
    public static final int[] OUTSIDE_EVEN_TOTAL_SUBSET = {1, 10, 34, 70, 126};
    public static final int[] INSIDE_ODD_TOTAL_SUBSET = {4, 20, 48, 81};
    public static final int[] OUTSIDE_GSUM = {0, 161, 961, 2015, 2715};
    public static final int[] INSIDE_GSUM = {0, FileType.SDOCX, 1036, 1516};
    public static final int[] OUTSIDE_ODD_WIDEST = {8, 6, 4, 3, 1};
    public static final int[] INSIDE_ODD_WIDEST = {2, 4, 6, 8};
    public static final int[][] FINDER_PATTERNS = {
        new int[] {3, 8, 2, 1},
        new int[] {3, 5, 5, 1},
        new int[] {3, 3, 7, 1},
        new int[] {3, 1, 9, 1},
        new int[] {2, 7, 4, 1},
        new int[] {2, 5, 6, 1},
        new int[] {2, 3, 8, 1},
        new int[] {1, 5, 7, 1},
        new int[] {1, 3, 9, 1}
    };

    public static void addOrTally(Collection collection, Pair pair) {
        if (pair == null) {
            return;
        }
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            Pair pair2 = (Pair) it.next();
            if (pair2.value == pair.value) {
                pair2.count++;
                return;
            }
        }
        collection.add(pair);
    }

    /* JADX WARN: Code restructure failed: missing block: B:115:0x0092, code lost:

       if (r3 < 4) goto L35;
    */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x0094, code lost:

       r16 = false;
       r15 = true;
    */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x0098, code lost:

       r15 = false;
       r16 = false;
    */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x00ae, code lost:

       if (r3 < 4) goto L35;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.google.zxing.oned.rss.DataCharacter decodeDataCharacter(
            com.google.zxing.common.BitArray r19,
            com.google.zxing.oned.rss.FinderPattern r20,
            boolean r21) {
        /*
            Method dump skipped, instructions count: 445
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.zxing.oned.rss.RSS14Reader.decodeDataCharacter(com.google.zxing.common.BitArray,"
                    + " com.google.zxing.oned.rss.FinderPattern,"
                    + " boolean):com.google.zxing.oned.rss.DataCharacter");
    }

    public final Pair decodePair(BitArray bitArray, boolean z, int i, Map map) {
        try {
            FinderPattern parseFoundFinderPattern =
                    parseFoundFinderPattern(bitArray, i, z, findFinderPattern(bitArray, z));
            if (map != null) {
                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                        map.get(DecodeHintType.NEED_RESULT_POINT_CALLBACK));
            }
            DataCharacter decodeDataCharacter =
                    decodeDataCharacter(bitArray, parseFoundFinderPattern, true);
            DataCharacter decodeDataCharacter2 =
                    decodeDataCharacter(bitArray, parseFoundFinderPattern, false);
            return new Pair(
                    (decodeDataCharacter.value * 1597) + decodeDataCharacter2.value,
                    (decodeDataCharacter2.checksumPortion * 4)
                            + decodeDataCharacter.checksumPortion,
                    parseFoundFinderPattern);
        } catch (NotFoundException unused) {
            return null;
        }
    }

    @Override // com.google.zxing.oned.OneDReader
    public final Result decodeRow(int i, BitArray bitArray, Map map) {
        addOrTally(this.possibleLeftPairs, decodePair(bitArray, false, i, map));
        bitArray.reverse();
        addOrTally(this.possibleRightPairs, decodePair(bitArray, true, i, map));
        bitArray.reverse();
        Iterator it = ((ArrayList) this.possibleLeftPairs).iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            if (pair.count > 1) {
                Iterator it2 = ((ArrayList) this.possibleRightPairs).iterator();
                while (it2.hasNext()) {
                    Pair pair2 = (Pair) it2.next();
                    if (pair2.count > 1) {
                        int i2 = ((pair2.checksumPortion * 16) + pair.checksumPortion) % 79;
                        FinderPattern finderPattern = pair.finderPattern;
                        int i3 = finderPattern.value * 9;
                        FinderPattern finderPattern2 = pair2.finderPattern;
                        int i4 = i3 + finderPattern2.value;
                        if (i4 > 72) {
                            i4--;
                        }
                        if (i4 > 8) {
                            i4--;
                        }
                        if (i2 == i4) {
                            String valueOf = String.valueOf((pair.value * 4537077) + pair2.value);
                            StringBuilder sb = new StringBuilder(14);
                            for (int length = 13 - valueOf.length(); length > 0; length--) {
                                sb.append('0');
                            }
                            sb.append(valueOf);
                            int i5 = 0;
                            for (int i6 = 0; i6 < 13; i6++) {
                                int charAt = sb.charAt(i6) - '0';
                                if ((i6 & 1) == 0) {
                                    charAt *= 3;
                                }
                                i5 += charAt;
                            }
                            int i7 = 10 - (i5 % 10);
                            if (i7 == 10) {
                                i7 = 0;
                            }
                            sb.append(i7);
                            String sb2 = sb.toString();
                            ResultPoint[] resultPointArr = finderPattern.resultPoints;
                            ResultPoint resultPoint = resultPointArr[0];
                            ResultPoint resultPoint2 = resultPointArr[1];
                            ResultPoint[] resultPointArr2 = finderPattern2.resultPoints;
                            Result result =
                                    new Result(
                                            sb2,
                                            null,
                                            new ResultPoint[] {
                                                resultPoint,
                                                resultPoint2,
                                                resultPointArr2[0],
                                                resultPointArr2[1]
                                            },
                                            BarcodeFormat.RSS_14);
                            result.putMetadata(ResultMetadataType.SYMBOLOGY_IDENTIFIER, "]e0");
                            return result;
                        }
                    }
                }
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    public final int[] findFinderPattern(BitArray bitArray, boolean z) {
        int[] iArr = this.decodeFinderCounters;
        iArr[0] = 0;
        iArr[1] = 0;
        iArr[2] = 0;
        iArr[3] = 0;
        int i = bitArray.size;
        int i2 = 0;
        boolean z2 = false;
        while (i2 < i) {
            z2 = !bitArray.get(i2);
            if (z == z2) {
                break;
            }
            i2++;
        }
        int i3 = 0;
        int i4 = i2;
        while (i2 < i) {
            if (bitArray.get(i2) != z2) {
                iArr[i3] = iArr[i3] + 1;
            } else {
                if (i3 != 3) {
                    i3++;
                } else {
                    if (AbstractRSSReader.isFinderPattern(iArr)) {
                        return new int[] {i4, i2};
                    }
                    i4 += iArr[0] + iArr[1];
                    iArr[0] = iArr[2];
                    iArr[1] = iArr[3];
                    iArr[2] = 0;
                    iArr[3] = 0;
                    i3--;
                }
                iArr[i3] = 1;
                z2 = !z2;
            }
            i2++;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    public final FinderPattern parseFoundFinderPattern(
            BitArray bitArray, int i, boolean z, int[] iArr) {
        int i2;
        int i3;
        boolean z2 = bitArray.get(iArr[0]);
        int i4 = iArr[0] - 1;
        while (i4 >= 0 && z2 != bitArray.get(i4)) {
            i4--;
        }
        int i5 = i4 + 1;
        int i6 = iArr[0] - i5;
        int[] iArr2 = this.decodeFinderCounters;
        System.arraycopy(iArr2, 0, iArr2, 1, iArr2.length - 1);
        iArr2[0] = i6;
        int[][] iArr3 = FINDER_PATTERNS;
        for (int i7 = 0; i7 < 9; i7++) {
            if (OneDReader.patternMatchVariance(iArr2, iArr3[i7], 0.45f) < 0.2f) {
                int i8 = iArr[1];
                if (z) {
                    int i9 = bitArray.size - 1;
                    i2 = i9 - i8;
                    i3 = i9 - i5;
                } else {
                    i2 = i8;
                    i3 = i5;
                }
                return new FinderPattern(i7, i3, i2, i, new int[] {i5, i8});
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    @Override // com.google.zxing.oned.OneDReader, com.google.zxing.Reader
    public final void reset() {
        ((ArrayList) this.possibleLeftPairs).clear();
        ((ArrayList) this.possibleRightPairs).clear();
    }
}
