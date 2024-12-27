package com.google.zxing.oned.rss.expanded;

import androidx.compose.runtime.PrioritySet$$ExternalSyntheticOutline0;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.oned.OneDReader;
import com.google.zxing.oned.rss.AbstractRSSReader;
import com.google.zxing.oned.rss.DataCharacter;
import com.google.zxing.oned.rss.FinderPattern;
import com.google.zxing.oned.rss.RSSUtils;
import com.google.zxing.oned.rss.expanded.decoders.AI013103decoder;
import com.google.zxing.oned.rss.expanded.decoders.AI01320xDecoder;
import com.google.zxing.oned.rss.expanded.decoders.AI01392xDecoder;
import com.google.zxing.oned.rss.expanded.decoders.AI01393xDecoder;
import com.google.zxing.oned.rss.expanded.decoders.AI013x0x1xDecoder;
import com.google.zxing.oned.rss.expanded.decoders.AI01AndOtherAIs;
import com.google.zxing.oned.rss.expanded.decoders.AbstractExpandedDecoder;
import com.google.zxing.oned.rss.expanded.decoders.AnyAIDecoder;
import com.google.zxing.oned.rss.expanded.decoders.GeneralAppIdDecoder;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.sec.ims.configuration.DATA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class RSSExpandedReader extends AbstractRSSReader {
    public final List pairs = new ArrayList(11);
    public final List rows = new ArrayList();
    public final int[] startEnd = new int[2];
    public boolean startFromEven;
    public static final int[] SYMBOL_WIDEST = {7, 5, 4, 3, 1};
    public static final int[] EVEN_TOTAL_SUBSET = {4, 20, 52, 104, 204};
    public static final int[] GSUM = {0, FileType.ACSM, 1388, 2948, 3988};
    public static final int[][] FINDER_PATTERNS = {
        new int[] {1, 8, 4, 1},
        new int[] {3, 6, 4, 1},
        new int[] {3, 4, 6, 1},
        new int[] {3, 2, 8, 1},
        new int[] {2, 6, 5, 1},
        new int[] {2, 2, 9, 1}
    };
    public static final int[][] WEIGHTS = {
        new int[] {1, 3, 9, 27, 81, 32, 96, 77},
        new int[] {20, 60, 180, 118, 143, 7, 21, 63},
        new int[] {189, 145, 13, 39, 117, 140, 209, 205},
        new int[] {193, 157, 49, 147, 19, 57, 171, 91},
        new int[] {62, 186, 136, 197, 169, 85, 44, 132},
        new int[] {185, 133, 188, 142, 4, 12, 36, 108},
        new int[] {113, 128, 173, 97, 80, 29, 87, 50},
        new int[] {150, 28, 84, 41, 123, 158, 52, 156},
        new int[] {46, 138, 203, 187, 139, 206, 196, 166},
        new int[] {76, 17, 51, 153, 37, 111, 122, 155},
        new int[] {43, 129, 176, 106, 107, 110, 119, 146},
        new int[] {16, 48, 144, 10, 30, 90, 59, 177},
        new int[] {109, 116, 137, 200, 178, 112, 125, 164},
        new int[] {70, 210, 208, 202, 184, 130, 179, 115},
        new int[] {134, 191, 151, 31, 93, 68, 204, 190},
        new int[] {148, 22, 66, 198, 172, 94, 71, 2},
        new int[] {6, 18, 54, 162, 64, 192, 154, 40},
        new int[] {120, 149, 25, 75, 14, 42, 126, 167},
        new int[] {79, 26, 78, 23, 69, 207, 199, 175},
        new int[] {103, 98, 83, 38, 114, 131, 182, 124},
        new int[] {161, 61, 183, 127, 170, 88, 53, 159},
        new int[] {55, 165, 73, 8, 24, 72, 5, 15},
        new int[] {45, 135, 194, 160, 58, 174, 100, 89}
    };
    public static final int[][] FINDER_PATTERN_SEQUENCES = {
        new int[] {0, 0},
        new int[] {0, 1, 1},
        new int[] {0, 2, 1, 3},
        new int[] {0, 4, 1, 3, 2},
        new int[] {0, 4, 1, 3, 3, 5},
        new int[] {0, 4, 1, 3, 4, 5, 5},
        new int[] {0, 0, 1, 1, 2, 2, 3, 3},
        new int[] {0, 0, 1, 1, 2, 2, 3, 4, 4},
        new int[] {0, 0, 1, 1, 2, 2, 3, 4, 5, 5},
        new int[] {0, 0, 1, 1, 2, 3, 3, 4, 4, 5, 5}
    };

    public static Result constructResult(List list) {
        AbstractExpandedDecoder aI013103decoder;
        int size = list.size() * 2;
        int i = size - 1;
        if (((ExpandedPair) PrioritySet$$ExternalSyntheticOutline0.m(1, list)).rightChar == null) {
            i = size - 2;
        }
        BitArray bitArray = new BitArray(i * 12);
        int i2 = ((ExpandedPair) list.get(0)).rightChar.value;
        int i3 = 0;
        for (int i4 = 11; i4 >= 0; i4--) {
            if (((1 << i4) & i2) != 0) {
                bitArray.set(i3);
            }
            i3++;
        }
        for (int i5 = 1; i5 < list.size(); i5++) {
            ExpandedPair expandedPair = (ExpandedPair) list.get(i5);
            int i6 = expandedPair.leftChar.value;
            for (int i7 = 11; i7 >= 0; i7--) {
                if (((1 << i7) & i6) != 0) {
                    bitArray.set(i3);
                }
                i3++;
            }
            DataCharacter dataCharacter = expandedPair.rightChar;
            if (dataCharacter != null) {
                for (int i8 = 11; i8 >= 0; i8--) {
                    if (((1 << i8) & dataCharacter.value) != 0) {
                        bitArray.set(i3);
                    }
                    i3++;
                }
            }
        }
        if (bitArray.get(1)) {
            aI013103decoder = new AI01AndOtherAIs(bitArray);
        } else if (bitArray.get(2)) {
            int extractNumericValueFromBitArray =
                    GeneralAppIdDecoder.extractNumericValueFromBitArray(1, 4, bitArray);
            if (extractNumericValueFromBitArray == 4) {
                aI013103decoder = new AI013103decoder(bitArray);
            } else if (extractNumericValueFromBitArray != 5) {
                int extractNumericValueFromBitArray2 =
                        GeneralAppIdDecoder.extractNumericValueFromBitArray(1, 5, bitArray);
                if (extractNumericValueFromBitArray2 == 12) {
                    aI013103decoder = new AI01392xDecoder(bitArray);
                } else if (extractNumericValueFromBitArray2 != 13) {
                    switch (GeneralAppIdDecoder.extractNumericValueFromBitArray(1, 7, bitArray)) {
                        case 56:
                            aI013103decoder =
                                    new AI013x0x1xDecoder(
                                            bitArray, "310", DATA.DM_FIELD_INDEX.SMS_WRITE_UICC);
                            break;
                        case 57:
                            aI013103decoder =
                                    new AI013x0x1xDecoder(
                                            bitArray, "320", DATA.DM_FIELD_INDEX.SMS_WRITE_UICC);
                            break;
                        case 58:
                            aI013103decoder =
                                    new AI013x0x1xDecoder(
                                            bitArray, "310", DATA.DM_FIELD_INDEX.SIP_T2_TIMER);
                            break;
                        case 59:
                            aI013103decoder =
                                    new AI013x0x1xDecoder(
                                            bitArray, "320", DATA.DM_FIELD_INDEX.SIP_T2_TIMER);
                            break;
                        case 60:
                            aI013103decoder =
                                    new AI013x0x1xDecoder(
                                            bitArray, "310", DATA.DM_FIELD_INDEX.SIP_TA_TIMER);
                            break;
                        case 61:
                            aI013103decoder =
                                    new AI013x0x1xDecoder(
                                            bitArray, "320", DATA.DM_FIELD_INDEX.SIP_TA_TIMER);
                            break;
                        case 62:
                            aI013103decoder =
                                    new AI013x0x1xDecoder(
                                            bitArray, "310", DATA.DM_FIELD_INDEX.SIP_TC_TIMER);
                            break;
                        case 63:
                            aI013103decoder =
                                    new AI013x0x1xDecoder(
                                            bitArray, "320", DATA.DM_FIELD_INDEX.SIP_TC_TIMER);
                            break;
                        default:
                            throw new IllegalStateException("unknown decoder: " + bitArray);
                    }
                } else {
                    aI013103decoder = new AI01393xDecoder(bitArray);
                }
            } else {
                aI013103decoder = new AI01320xDecoder(bitArray);
            }
        } else {
            aI013103decoder = new AnyAIDecoder(bitArray);
        }
        String parseInformation = aI013103decoder.parseInformation();
        ResultPoint[] resultPointArr = ((ExpandedPair) list.get(0)).finderPattern.resultPoints;
        ResultPoint[] resultPointArr2 =
                ((ExpandedPair) PrioritySet$$ExternalSyntheticOutline0.m(1, list))
                        .finderPattern
                        .resultPoints;
        Result result =
                new Result(
                        parseInformation,
                        null,
                        new ResultPoint[] {
                            resultPointArr[0],
                            resultPointArr[1],
                            resultPointArr2[0],
                            resultPointArr2[1]
                        },
                        BarcodeFormat.RSS_EXPANDED);
        result.putMetadata(ResultMetadataType.SYMBOLOGY_IDENTIFIER, "]e0");
        return result;
    }

    public final boolean checkChecksum() {
        ExpandedPair expandedPair = (ExpandedPair) ((ArrayList) this.pairs).get(0);
        DataCharacter dataCharacter = expandedPair.leftChar;
        DataCharacter dataCharacter2 = expandedPair.rightChar;
        if (dataCharacter2 == null) {
            return false;
        }
        int i = 2;
        int i2 = dataCharacter2.checksumPortion;
        for (int i3 = 1; i3 < ((ArrayList) this.pairs).size(); i3++) {
            ExpandedPair expandedPair2 = (ExpandedPair) ((ArrayList) this.pairs).get(i3);
            i2 += expandedPair2.leftChar.checksumPortion;
            int i4 = i + 1;
            DataCharacter dataCharacter3 = expandedPair2.rightChar;
            if (dataCharacter3 != null) {
                i2 += dataCharacter3.checksumPortion;
                i += 2;
            } else {
                i = i4;
            }
        }
        return ((i + (-4)) * 211) + (i2 % 211) == dataCharacter.value;
    }

    public final List checkRows(boolean z) {
        List list = null;
        if (((ArrayList) this.rows).size() > 25) {
            ((ArrayList) this.rows).clear();
            return null;
        }
        ((ArrayList) this.pairs).clear();
        if (z) {
            Collections.reverse(this.rows);
        }
        try {
            list = checkRows(0, new ArrayList());
        } catch (NotFoundException unused) {
        }
        if (z) {
            Collections.reverse(this.rows);
        }
        return list;
    }

    public final DataCharacter decodeDataCharacter(
            BitArray bitArray, FinderPattern finderPattern, boolean z, boolean z2) {
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        int[][] iArr;
        int[] iArr2 = this.dataCharacterCounters;
        Arrays.fill(iArr2, 0);
        if (z2) {
            OneDReader.recordPatternInReverse(finderPattern.startEnd[0], bitArray, iArr2);
        } else {
            OneDReader.recordPattern(finderPattern.startEnd[1], bitArray, iArr2);
            int i = 0;
            for (int length = iArr2.length - 1; i < length; length--) {
                int i2 = iArr2[i];
                iArr2[i] = iArr2[length];
                iArr2[length] = i2;
                i++;
            }
        }
        float sum = MathUtils.sum(iArr2) / 17;
        int[] iArr3 = finderPattern.startEnd;
        float f = (iArr3[1] - iArr3[0]) / 15.0f;
        if (Math.abs(sum - f) / f > 0.3f) {
            throw NotFoundException.getNotFoundInstance();
        }
        int i3 = 0;
        while (true) {
            int length2 = iArr2.length;
            float[] fArr = this.evenRoundingErrors;
            float[] fArr2 = this.oddRoundingErrors;
            int[] iArr4 = this.evenCounts;
            int[] iArr5 = this.oddCounts;
            if (i3 >= length2) {
                int sum2 = MathUtils.sum(iArr5);
                int sum3 = MathUtils.sum(iArr4);
                if (sum2 > 13) {
                    z3 = false;
                    z4 = true;
                } else if (sum2 < 4) {
                    z4 = false;
                    z3 = true;
                } else {
                    z3 = false;
                    z4 = false;
                }
                if (sum3 > 13) {
                    z5 = false;
                    z6 = true;
                } else if (sum3 < 4) {
                    z6 = false;
                    z5 = true;
                } else {
                    z5 = false;
                    z6 = false;
                }
                int i4 = (sum2 + sum3) - 17;
                boolean z7 = (sum2 & 1) == 1;
                boolean z8 = (sum3 & 1) == 0;
                boolean z9 = z3;
                boolean z10 = z4;
                boolean z11 = z5;
                boolean z12 = z6;
                if (i4 != -1) {
                    if (i4 != 0) {
                        if (i4 != 1) {
                            throw NotFoundException.getNotFoundInstance();
                        }
                        if (z7) {
                            if (z8) {
                                throw NotFoundException.getNotFoundInstance();
                            }
                            z10 = true;
                            z9 = z3;
                            z11 = z5;
                            z12 = z6;
                        } else {
                            if (!z8) {
                                throw NotFoundException.getNotFoundInstance();
                            }
                            z12 = true;
                            z9 = z3;
                            z10 = z4;
                            z11 = z5;
                        }
                    } else if (z7) {
                        if (!z8) {
                            throw NotFoundException.getNotFoundInstance();
                        }
                        if (sum2 < sum3) {
                            z9 = true;
                            z12 = true;
                            z10 = z4;
                            z11 = z5;
                        } else {
                            z10 = true;
                            z11 = true;
                            z9 = z3;
                            z12 = z6;
                        }
                    } else if (z8) {
                        throw NotFoundException.getNotFoundInstance();
                    }
                } else if (z7) {
                    if (z8) {
                        throw NotFoundException.getNotFoundInstance();
                    }
                    z9 = true;
                    z10 = z4;
                    z11 = z5;
                    z12 = z6;
                } else {
                    if (!z8) {
                        throw NotFoundException.getNotFoundInstance();
                    }
                    z11 = true;
                    z9 = z3;
                    z10 = z4;
                    z12 = z6;
                }
                if (z9) {
                    if (z10) {
                        throw NotFoundException.getNotFoundInstance();
                    }
                    AbstractRSSReader.increment(fArr2, iArr5);
                }
                if (z10) {
                    AbstractRSSReader.decrement(fArr2, iArr5);
                }
                if (z11) {
                    if (z12) {
                        throw NotFoundException.getNotFoundInstance();
                    }
                    AbstractRSSReader.increment(fArr2, iArr4);
                }
                if (z12) {
                    AbstractRSSReader.decrement(fArr, iArr4);
                }
                int i5 = finderPattern.value;
                int i6 = (((i5 * 4) + (z ? 0 : 2)) + (!z2 ? 1 : 0)) - 1;
                int length3 = iArr5.length - 1;
                int i7 = 0;
                int i8 = 0;
                while (true) {
                    iArr = WEIGHTS;
                    if (length3 < 0) {
                        break;
                    }
                    if (i5 != 0 || !z || !z2) {
                        i7 += iArr5[length3] * iArr[i6][length3 * 2];
                    }
                    i8 += iArr5[length3];
                    length3--;
                }
                int i9 = 0;
                for (int length4 = iArr4.length - 1; length4 >= 0; length4--) {
                    if (i5 != 0 || !z || !z2) {
                        i9 += iArr4[length4] * iArr[i6][(length4 * 2) + 1];
                    }
                }
                int i10 = i7 + i9;
                if ((i8 & 1) != 0 || i8 > 13 || i8 < 4) {
                    throw NotFoundException.getNotFoundInstance();
                }
                int i11 = (13 - i8) / 2;
                int i12 = SYMBOL_WIDEST[i11];
                return new DataCharacter(
                        (RSSUtils.getRSSvalue(iArr5, i12, true) * EVEN_TOTAL_SUBSET[i11])
                                + RSSUtils.getRSSvalue(iArr4, 9 - i12, false)
                                + GSUM[i11],
                        i10);
            }
            float f2 = (iArr2[i3] * 1.0f) / sum;
            int i13 = (int) (0.5f + f2);
            if (i13 < 1) {
                if (f2 < 0.3f) {
                    throw NotFoundException.getNotFoundInstance();
                }
                i13 = 1;
            } else if (i13 > 8) {
                if (f2 > 8.7f) {
                    throw NotFoundException.getNotFoundInstance();
                }
                i13 = 8;
            }
            int i14 = i3 / 2;
            if ((i3 & 1) == 0) {
                iArr5[i14] = i13;
                fArr2[i14] = f2 - i13;
            } else {
                iArr4[i14] = i13;
                fArr[i14] = f2 - i13;
            }
            i3++;
        }
    }

    @Override // com.google.zxing.oned.OneDReader
    public final Result decodeRow(int i, BitArray bitArray, Map map) {
        ((ArrayList) this.pairs).clear();
        this.startFromEven = false;
        try {
            return constructResult(decodeRow2pairs(i, bitArray));
        } catch (NotFoundException unused) {
            ((ArrayList) this.pairs).clear();
            this.startFromEven = true;
            return constructResult(this.decodeRow2pairs(i, bitArray));
        }
    }

    public final List decodeRow2pairs(int i, BitArray bitArray) {
        boolean z;
        boolean z2;
        boolean z3 = false;
        while (!z3) {
            try {
                List list = this.pairs;
                ((ArrayList) list).add(retrieveNextPair(bitArray, list, i));
            } catch (NotFoundException e) {
                if (((ArrayList) this.pairs).isEmpty()) {
                    throw e;
                }
                z3 = true;
            }
        }
        if (checkChecksum()) {
            return this.pairs;
        }
        boolean z4 = !((ArrayList) this.rows).isEmpty();
        int i2 = 0;
        boolean z5 = false;
        while (true) {
            if (i2 >= ((ArrayList) this.rows).size()) {
                z = false;
                break;
            }
            ExpandedRow expandedRow = (ExpandedRow) ((ArrayList) this.rows).get(i2);
            if (expandedRow.rowNumber > i) {
                z = ((ArrayList) expandedRow.pairs).equals(this.pairs);
                break;
            }
            z5 = ((ArrayList) expandedRow.pairs).equals(this.pairs);
            i2++;
        }
        if (!z && !z5) {
            List list2 = this.pairs;
            Iterator it = ((ArrayList) this.rows).iterator();
            loop2:
            while (true) {
                if (!it.hasNext()) {
                    z2 = false;
                    break;
                }
                ExpandedRow expandedRow2 = (ExpandedRow) it.next();
                Iterator it2 = ((ArrayList) list2).iterator();
                while (it2.hasNext()) {
                    ExpandedPair expandedPair = (ExpandedPair) it2.next();
                    Iterator it3 = ((ArrayList) expandedRow2.pairs).iterator();
                    while (it3.hasNext()) {
                        if (expandedPair.equals((ExpandedPair) it3.next())) {
                            break;
                        }
                    }
                }
                z2 = true;
                break loop2;
            }
            if (!z2) {
                ((ArrayList) this.rows).add(i2, new ExpandedRow(i, this.pairs));
                List list3 = this.pairs;
                Iterator it4 = ((ArrayList) this.rows).iterator();
                while (it4.hasNext()) {
                    ExpandedRow expandedRow3 = (ExpandedRow) it4.next();
                    ArrayList arrayList = (ArrayList) list3;
                    if (((ArrayList) expandedRow3.pairs).size() != arrayList.size()) {
                        Iterator it5 = ((ArrayList) expandedRow3.pairs).iterator();
                        while (true) {
                            if (!it5.hasNext()) {
                                it4.remove();
                                break;
                            }
                            if (!arrayList.contains((ExpandedPair) it5.next())) {
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (z4) {
            List checkRows = checkRows(false);
            if (checkRows != null) {
                return checkRows;
            }
            List checkRows2 = checkRows(true);
            if (checkRows2 != null) {
                return checkRows2;
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    @Override // com.google.zxing.oned.OneDReader, com.google.zxing.Reader
    public final void reset() {
        ((ArrayList) this.pairs).clear();
        ((ArrayList) this.rows).clear();
    }

    public final ExpandedPair retrieveNextPair(BitArray bitArray, List list, int i) {
        int i2;
        int i3;
        int nextUnset;
        int i4;
        FinderPattern finderPattern;
        int[][] iArr;
        int i5;
        int i6 = 2;
        int i7 = 0;
        int i8 = 1;
        boolean z = list.size() % 2 == 0;
        if (this.startFromEven) {
            z = !z;
        }
        int i9 = -1;
        boolean z2 = true;
        while (true) {
            int[] iArr2 = this.decodeFinderCounters;
            iArr2[i7] = i7;
            iArr2[i8] = i7;
            iArr2[i6] = i7;
            int i10 = 3;
            iArr2[3] = i7;
            int i11 = bitArray.size;
            int i12 =
                    i9 >= 0
                            ? i9
                            : list.isEmpty()
                                    ? i7
                                    : ((ExpandedPair)
                                                    PrioritySet$$ExternalSyntheticOutline0.m(
                                                            i8, list))
                                            .finderPattern
                                            .startEnd[i8];
            int i13 = list.size() % i6 != 0 ? i8 : i7;
            if (this.startFromEven) {
                i13 ^= 1;
            }
            int i14 = i7;
            while (i12 < i11) {
                i14 = bitArray.get(i12) ^ i8;
                if (i14 == 0) {
                    break;
                }
                i12++;
            }
            int i15 = i7;
            boolean z3 = i14;
            int i16 = i12;
            while (i12 < i11) {
                if (bitArray.get(i12) != z3) {
                    iArr2[i15] = iArr2[i15] + i8;
                    i2 = i8;
                } else {
                    if (i15 == i10) {
                        if (i13 != 0) {
                            int length = iArr2.length;
                            for (int i17 = 0; i17 < length / 2; i17++) {
                                int i18 = iArr2[i17];
                                int i19 = (length - i17) - 1;
                                iArr2[i17] = iArr2[i19];
                                iArr2[i19] = i18;
                            }
                        }
                        if (AbstractRSSReader.isFinderPattern(iArr2)) {
                            int[] iArr3 = this.startEnd;
                            iArr3[0] = i16;
                            iArr3[1] = i12;
                            if (z) {
                                int i20 = i16 - 1;
                                while (i20 >= 0 && !bitArray.get(i20)) {
                                    i20--;
                                }
                                i16 = i20 + 1;
                                i4 = iArr3[0] - i16;
                                i3 = 1;
                                nextUnset = iArr3[1];
                            } else {
                                i3 = 1;
                                nextUnset = bitArray.getNextUnset(i12 + 1);
                                i4 = nextUnset - iArr3[1];
                            }
                            int i21 = nextUnset;
                            int i22 = i16;
                            System.arraycopy(iArr2, 0, iArr2, i3, iArr2.length - i3);
                            iArr2[0] = i4;
                            DataCharacter dataCharacter = null;
                            try {
                                iArr = FINDER_PATTERNS;
                            } catch (NotFoundException unused) {
                                finderPattern = null;
                            }
                            for (i5 = 0; i5 < 6; i5++) {
                                if (OneDReader.patternMatchVariance(iArr2, iArr[i5], 0.45f)
                                        < 0.2f) {
                                    finderPattern =
                                            new FinderPattern(
                                                    i5, i22, i21, i, new int[] {i22, i21});
                                    if (finderPattern == null) {
                                        int i23 = iArr3[0];
                                        i9 =
                                                bitArray.get(i23)
                                                        ? bitArray.getNextSet(
                                                                bitArray.getNextUnset(i23))
                                                        : bitArray.getNextUnset(
                                                                bitArray.getNextSet(i23));
                                    } else {
                                        z2 = false;
                                    }
                                    if (!z2) {
                                        DataCharacter decodeDataCharacter =
                                                decodeDataCharacter(
                                                        bitArray, finderPattern, z, true);
                                        if (!list.isEmpty()
                                                && ((ExpandedPair)
                                                                        PrioritySet$$ExternalSyntheticOutline0
                                                                                .m(1, list))
                                                                .rightChar
                                                        == null) {
                                            throw NotFoundException.getNotFoundInstance();
                                        }
                                        try {
                                            dataCharacter =
                                                    decodeDataCharacter(
                                                            bitArray, finderPattern, z, false);
                                        } catch (NotFoundException unused2) {
                                        }
                                        return new ExpandedPair(
                                                decodeDataCharacter, dataCharacter, finderPattern);
                                    }
                                    i6 = 2;
                                    i7 = 0;
                                    i8 = 1;
                                }
                            }
                            throw NotFoundException.getNotFoundInstance();
                        } else {
                            if (i13 != 0) {
                                int length2 = iArr2.length;
                                for (int i24 = 0; i24 < length2 / 2; i24++) {
                                    int i25 = iArr2[i24];
                                    int i26 = (length2 - i24) - 1;
                                    iArr2[i24] = iArr2[i26];
                                    iArr2[i26] = i25;
                                }
                            }
                            i2 = 1;
                            i16 += iArr2[0] + iArr2[1];
                            iArr2[0] = iArr2[2];
                            i10 = 3;
                            iArr2[1] = iArr2[3];
                            iArr2[2] = 0;
                            iArr2[3] = 0;
                            i15--;
                        }
                    } else {
                        i2 = i8;
                        i15++;
                    }
                    iArr2[i15] = i2;
                    z3 = !z3;
                }
                i12++;
                i8 = i2;
            }
            throw NotFoundException.getNotFoundInstance();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0074, code lost:

       if (r10.checkChecksum() == false) goto L24;
    */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0079, code lost:

       r1 = new java.util.ArrayList(r12);
       r1.add(r0);
    */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0087, code lost:

       return r10.checkRows(r11 + 1, r1);
    */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x008b, code lost:

       continue;
    */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x008b, code lost:

       r11 = r11 + 1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0078, code lost:

       return r10.pairs;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List checkRows(int r11, java.util.List r12) {
        /*
            r10 = this;
        L0:
            java.util.List r0 = r10.rows
            java.util.ArrayList r0 = (java.util.ArrayList) r0
            int r0 = r0.size()
            if (r11 >= r0) goto L8f
            java.util.List r0 = r10.rows
            java.util.ArrayList r0 = (java.util.ArrayList) r0
            java.lang.Object r0 = r0.get(r11)
            com.google.zxing.oned.rss.expanded.ExpandedRow r0 = (com.google.zxing.oned.rss.expanded.ExpandedRow) r0
            java.util.List r1 = r10.pairs
            java.util.ArrayList r1 = (java.util.ArrayList) r1
            r1.clear()
            r1 = r12
            java.util.ArrayList r1 = (java.util.ArrayList) r1
            java.util.Iterator r1 = r1.iterator()
        L22:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L38
            java.lang.Object r2 = r1.next()
            com.google.zxing.oned.rss.expanded.ExpandedRow r2 = (com.google.zxing.oned.rss.expanded.ExpandedRow) r2
            java.util.List r3 = r10.pairs
            java.util.List r2 = r2.pairs
            java.util.ArrayList r3 = (java.util.ArrayList) r3
            r3.addAll(r2)
            goto L22
        L38:
            java.util.List r1 = r10.pairs
            java.util.List r2 = r0.pairs
            java.util.ArrayList r1 = (java.util.ArrayList) r1
            r1.addAll(r2)
            java.util.List r1 = r10.pairs
            int[][] r2 = com.google.zxing.oned.rss.expanded.RSSExpandedReader.FINDER_PATTERN_SEQUENCES
            r3 = 0
            r4 = r3
        L47:
            r5 = 10
            if (r4 >= r5) goto L8b
            r5 = r2[r4]
            r6 = r1
            java.util.ArrayList r6 = (java.util.ArrayList) r6
            int r7 = r6.size()
            int r8 = r5.length
            if (r7 > r8) goto L88
            r7 = r3
        L58:
            int r8 = r6.size()
            if (r7 >= r8) goto L70
            java.lang.Object r8 = r6.get(r7)
            com.google.zxing.oned.rss.expanded.ExpandedPair r8 = (com.google.zxing.oned.rss.expanded.ExpandedPair) r8
            com.google.zxing.oned.rss.FinderPattern r8 = r8.finderPattern
            int r8 = r8.value
            r9 = r5[r7]
            if (r8 == r9) goto L6d
            goto L88
        L6d:
            int r7 = r7 + 1
            goto L58
        L70:
            boolean r1 = r10.checkChecksum()
            if (r1 == 0) goto L79
            java.util.List r10 = r10.pairs
            return r10
        L79:
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>(r12)
            r1.add(r0)
            int r0 = r11 + 1
            java.util.List r10 = r10.checkRows(r0, r1)     // Catch: com.google.zxing.NotFoundException -> L8b
            return r10
        L88:
            int r4 = r4 + 1
            goto L47
        L8b:
            int r11 = r11 + 1
            goto L0
        L8f:
            com.google.zxing.NotFoundException r10 = com.google.zxing.NotFoundException.getNotFoundInstance()
            throw r10
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.zxing.oned.rss.expanded.RSSExpandedReader.checkRows(int,"
                    + " java.util.List):java.util.List");
    }
}
