package com.google.common.primitives;

import com.google.common.base.Strings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class Ints {
    public static int constrainToRange(int i, int i2) {
        if (i2 <= 1073741823) {
            return Math.min(Math.max(i, i2), 1073741823);
        }
        throw new IllegalArgumentException(
                Strings.lenientFormat(
                        "min (%s) must be less than or equal to max (%s)",
                        Integer.valueOf(i2), 1073741823));
    }

    public static int indexOf(int i, int[] iArr) {
        int length = iArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            if (iArr[i2] == i) {
                return i2;
            }
        }
        return -1;
    }

    public static Integer tryParse(String str) {
        byte b;
        Long valueOf;
        byte b2;
        str.getClass();
        if (!str.isEmpty()) {
            int i = str.charAt(0) == '-' ? 1 : 0;
            if (i != str.length()) {
                int i2 = i + 1;
                char charAt = str.charAt(i);
                if (charAt < 128) {
                    b = Longs$AsciiDigits.asciiDigits[charAt];
                } else {
                    byte[] bArr = Longs$AsciiDigits.asciiDigits;
                    b = -1;
                }
                if (b >= 0 && b < 10) {
                    long j = -b;
                    long j2 = 10;
                    long j3 = Long.MIN_VALUE / j2;
                    while (true) {
                        if (i2 < str.length()) {
                            int i3 = i2 + 1;
                            char charAt2 = str.charAt(i2);
                            if (charAt2 < 128) {
                                b2 = Longs$AsciiDigits.asciiDigits[charAt2];
                            } else {
                                byte[] bArr2 = Longs$AsciiDigits.asciiDigits;
                                b2 = -1;
                            }
                            if (b2 < 0 || b2 >= 10 || j < j3) {
                                break;
                            }
                            long j4 = j * j2;
                            long j5 = b2;
                            if (j4 < j5 - Long.MIN_VALUE) {
                                break;
                            }
                            j = j4 - j5;
                            i2 = i3;
                        } else if (i != 0) {
                            valueOf = Long.valueOf(j);
                        } else if (j != Long.MIN_VALUE) {
                            valueOf = Long.valueOf(-j);
                        }
                    }
                }
            }
        }
        valueOf = null;
        if (valueOf == null || valueOf.longValue() != valueOf.intValue()) {
            return null;
        }
        return Integer.valueOf(valueOf.intValue());
    }
}
