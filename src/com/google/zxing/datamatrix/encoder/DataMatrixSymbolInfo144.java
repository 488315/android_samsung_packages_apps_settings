package com.google.zxing.datamatrix.encoder;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class DataMatrixSymbolInfo144 extends SymbolInfo {
    @Override // com.google.zxing.datamatrix.encoder.SymbolInfo
    public final int getDataLengthForInterleavedBlock(int i) {
        return i <= 8 ? 156 : 155;
    }

    @Override // com.google.zxing.datamatrix.encoder.SymbolInfo
    public final int getInterleavedBlockCount() {
        return 10;
    }
}
