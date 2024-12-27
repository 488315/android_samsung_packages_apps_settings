package com.google.zxing.common;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public interface ECIInput {
    char charAt(int i);

    int getECIValue(int i);

    boolean isECI(int i);

    int length();

    CharSequence subSequence(int i, int i2);
}
