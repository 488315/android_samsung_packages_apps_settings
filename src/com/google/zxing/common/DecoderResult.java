package com.google.zxing.common;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class DecoderResult {
    public final List byteSegments;
    public final String ecLevel;
    public Integer erasures;
    public Integer errorsCorrected;
    public int numBits;
    public Object other;
    public final byte[] rawBytes;
    public final int structuredAppendParity;
    public final int structuredAppendSequenceNumber;
    public final int symbologyModifier;
    public final String text;

    public DecoderResult(String str, byte[] bArr, String str2) {
        this(bArr, str, null, str2, -1, -1, 0);
    }

    public DecoderResult(byte[] bArr, String str, List list, String str2, int i, int i2, int i3) {
        this.rawBytes = bArr;
        if (bArr != null) {
            int length = bArr.length;
        }
        this.text = str;
        this.byteSegments = list;
        this.ecLevel = str2;
        this.structuredAppendParity = i2;
        this.structuredAppendSequenceNumber = i;
        this.symbologyModifier = i3;
    }
}
