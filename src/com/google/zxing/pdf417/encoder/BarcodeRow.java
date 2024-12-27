package com.google.zxing.pdf417.encoder;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class BarcodeRow {
    public int currentLocation = 0;
    public final byte[] row;

    public BarcodeRow(int i) {
        this.row = new byte[i];
    }
}
