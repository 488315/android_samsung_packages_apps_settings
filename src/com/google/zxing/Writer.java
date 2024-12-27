package com.google.zxing;

import com.google.zxing.common.BitMatrix;

import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public interface Writer {
    BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map map);
}
