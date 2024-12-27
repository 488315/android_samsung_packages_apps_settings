package com.google.zxing;

import java.util.EnumMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class Result {
    public final BarcodeFormat format;
    public final byte[] rawBytes;
    public Map resultMetadata;
    public ResultPoint[] resultPoints;
    public final String text;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public Result(
            String str, byte[] bArr, ResultPoint[] resultPointArr, BarcodeFormat barcodeFormat) {
        this(str, bArr, resultPointArr, barcodeFormat, 0);
        System.currentTimeMillis();
    }

    public final void putAllMetadata(Map map) {
        if (map != null) {
            Map map2 = this.resultMetadata;
            if (map2 == null) {
                this.resultMetadata = map;
            } else {
                map2.putAll(map);
            }
        }
    }

    public final void putMetadata(ResultMetadataType resultMetadataType, Object obj) {
        if (this.resultMetadata == null) {
            this.resultMetadata = new EnumMap(ResultMetadataType.class);
        }
        this.resultMetadata.put(resultMetadataType, obj);
    }

    public final String toString() {
        return this.text;
    }

    public Result(
            String str,
            byte[] bArr,
            ResultPoint[] resultPointArr,
            BarcodeFormat barcodeFormat,
            int i) {
        this.text = str;
        this.rawBytes = bArr;
        this.resultPoints = resultPointArr;
        this.format = barcodeFormat;
        this.resultMetadata = null;
    }
}
