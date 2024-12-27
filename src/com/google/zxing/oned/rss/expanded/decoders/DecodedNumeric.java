package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.FormatException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class DecodedNumeric extends DecodedObject {
    public final int firstDigit;
    public final int secondDigit;

    public DecodedNumeric(int i, int i2, int i3) {
        super(i);
        if (i2 < 0 || i2 > 10 || i3 < 0 || i3 > 10) {
            throw FormatException.getFormatInstance();
        }
        this.firstDigit = i2;
        this.secondDigit = i3;
    }
}
