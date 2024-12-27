package com.google.zxing.datamatrix.encoder;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;

import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SymbolInfo {
    public static final SymbolInfo[] symbols = {
        new SymbolInfo(false, 3, 5, 8, 8, 1, 3, 5),
        new SymbolInfo(false, 5, 7, 10, 10, 1, 5, 7),
        new SymbolInfo(true, 5, 7, 16, 6, 1, 5, 7),
        new SymbolInfo(false, 8, 10, 12, 12, 1, 8, 10),
        new SymbolInfo(true, 10, 11, 14, 6, 2, 10, 11),
        new SymbolInfo(false, 12, 12, 14, 14, 1, 12, 12),
        new SymbolInfo(true, 16, 14, 24, 10, 1, 16, 14),
        new SymbolInfo(false, 18, 14, 16, 16, 1, 18, 14),
        new SymbolInfo(false, 22, 18, 18, 18, 1, 22, 18),
        new SymbolInfo(true, 22, 18, 16, 10, 2, 22, 18),
        new SymbolInfo(false, 30, 20, 20, 20, 1, 30, 20),
        new SymbolInfo(true, 32, 24, 16, 14, 2, 32, 24),
        new SymbolInfo(false, 36, 24, 22, 22, 1, 36, 24),
        new SymbolInfo(false, 44, 28, 24, 24, 1, 44, 28),
        new SymbolInfo(true, 49, 28, 22, 14, 2, 49, 28),
        new SymbolInfo(false, 62, 36, 14, 14, 4, 62, 36),
        new SymbolInfo(false, 86, 42, 16, 16, 4, 86, 42),
        new SymbolInfo(false, 114, 48, 18, 18, 4, 114, 48),
        new SymbolInfo(false, 144, 56, 20, 20, 4, 144, 56),
        new SymbolInfo(false, 174, 68, 22, 22, 4, 174, 68),
        new SymbolInfo(false, 204, 84, 24, 24, 4, 102, 42),
        new SymbolInfo(
                false,
                IKnoxCustomManager.Stub.TRANSACTION_getForceSingleView,
                112,
                14,
                14,
                16,
                140,
                56),
        new SymbolInfo(false, 368, 144, 16, 16, 16, 92, 36),
        new SymbolInfo(false, 456, 192, 18, 18, 16, 114, 48),
        new SymbolInfo(false, 576, 224, 20, 20, 16, 144, 56),
        new SymbolInfo(false, 696, 272, 22, 22, 16, 174, 68),
        new SymbolInfo(false, 816, FileType.SDOCX, 24, 24, 16, 136, 56),
        new SymbolInfo(false, 1050, VolteConstants.ErrorCode.REQUEST_TIMEOUT, 18, 18, 36, 175, 68),
        new SymbolInfo(false, 1304, 496, 20, 20, 36, 163, 62),
        new DataMatrixSymbolInfo144(false, 1558, 620, 22, 22, 36, -1, 62)
    };
    public final int dataCapacity;
    public final int dataRegions;
    public final int errorCodewords;
    public final int matrixHeight;
    public final int matrixWidth;
    public final boolean rectangular;
    public final int rsBlockData;
    public final int rsBlockError;

    public SymbolInfo(boolean z, int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        this.rectangular = z;
        this.dataCapacity = i;
        this.errorCodewords = i2;
        this.matrixWidth = i3;
        this.matrixHeight = i4;
        this.dataRegions = i5;
        this.rsBlockData = i6;
        this.rsBlockError = i7;
    }

    public static SymbolInfo lookup(int i, SymbolShapeHint symbolShapeHint) {
        SymbolInfo[] symbolInfoArr = symbols;
        for (int i2 = 0; i2 < 30; i2++) {
            SymbolInfo symbolInfo = symbolInfoArr[i2];
            if (!(symbolShapeHint == SymbolShapeHint.FORCE_SQUARE && symbolInfo.rectangular)
                    && ((symbolShapeHint != SymbolShapeHint.FORCE_RECTANGLE
                                    || symbolInfo.rectangular)
                            && i <= symbolInfo.dataCapacity)) {
                return symbolInfo;
            }
        }
        throw new IllegalArgumentException(
                SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                        i,
                        "Can't find a symbol arrangement that matches the message. Data"
                            + " codewords: "));
    }

    public int getDataLengthForInterleavedBlock(int i) {
        return this.rsBlockData;
    }

    public final int getHorizontalDataRegions() {
        int i = 1;
        int i2 = this.dataRegions;
        if (i2 != 1) {
            i = 2;
            if (i2 != 2 && i2 != 4) {
                if (i2 == 16) {
                    return 4;
                }
                if (i2 == 36) {
                    return 6;
                }
                throw new IllegalStateException("Cannot handle this number of data regions");
            }
        }
        return i;
    }

    public int getInterleavedBlockCount() {
        return this.dataCapacity / this.rsBlockData;
    }

    public final int getVerticalDataRegions() {
        int i = this.dataRegions;
        if (i == 1 || i == 2) {
            return 1;
        }
        if (i == 4) {
            return 2;
        }
        if (i == 16) {
            return 4;
        }
        if (i == 36) {
            return 6;
        }
        throw new IllegalStateException("Cannot handle this number of data regions");
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.rectangular ? "Rectangular Symbol:" : "Square Symbol:");
        sb.append(" data region ");
        int i = this.matrixWidth;
        sb.append(i);
        sb.append('x');
        int i2 = this.matrixHeight;
        sb.append(i2);
        sb.append(", symbol size ");
        sb.append((getHorizontalDataRegions() * 2) + (getHorizontalDataRegions() * i));
        sb.append('x');
        sb.append((getVerticalDataRegions() * 2) + (getVerticalDataRegions() * i2));
        sb.append(", symbol data size ");
        sb.append(getHorizontalDataRegions() * i);
        sb.append('x');
        sb.append(getVerticalDataRegions() * i2);
        sb.append(", codewords ");
        sb.append(this.dataCapacity);
        sb.append('+');
        sb.append(this.errorCodewords);
        return sb.toString();
    }
}
