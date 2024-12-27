package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.FormatException;
import com.google.zxing.Result;
import com.google.zxing.common.BitArray;

import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class UPCAReader extends UPCEANReader {
    public final EAN13Reader ean13Reader = new EAN13Reader();

    public static Result maybeReturnResult(Result result) {
        String str = result.text;
        if (str.charAt(0) != '0') {
            throw FormatException.getFormatInstance();
        }
        Result result2 =
                new Result(str.substring(1), null, result.resultPoints, BarcodeFormat.UPC_A);
        Map map = result.resultMetadata;
        if (map != null) {
            result2.putAllMetadata(map);
        }
        return result2;
    }

    @Override // com.google.zxing.oned.OneDReader, com.google.zxing.Reader
    public final Result decode(BinaryBitmap binaryBitmap, Map map) {
        return maybeReturnResult(this.ean13Reader.decode(binaryBitmap, map));
    }

    @Override // com.google.zxing.oned.UPCEANReader
    public final int decodeMiddle(BitArray bitArray, int[] iArr, StringBuilder sb) {
        return this.ean13Reader.decodeMiddle(bitArray, iArr, sb);
    }

    @Override // com.google.zxing.oned.UPCEANReader
    public final Result decodeRow(int i, BitArray bitArray, int[] iArr, Map map) {
        return maybeReturnResult(this.ean13Reader.decodeRow(i, bitArray, iArr, map));
    }

    @Override // com.google.zxing.oned.UPCEANReader
    public final BarcodeFormat getBarcodeFormat() {
        return BarcodeFormat.UPC_A;
    }

    @Override // com.google.zxing.oned.UPCEANReader, com.google.zxing.oned.OneDReader
    public final Result decodeRow(int i, BitArray bitArray, Map map) {
        return maybeReturnResult(this.ean13Reader.decodeRow(i, bitArray, map));
    }
}
