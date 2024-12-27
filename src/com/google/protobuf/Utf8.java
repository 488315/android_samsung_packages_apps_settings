package com.google.protobuf;

import androidx.compose.foundation.text.HeightInLinesModifierKt$$ExternalSyntheticOutline0;

import com.att.iqi.lib.metrics.hw.HwConstants;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class Utf8 {
    public static final SafeProcessor processor;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class DecodeUtil {
        public static boolean isNotTrailingByte(byte b) {
            return b > -65;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SafeProcessor {
        public final boolean isValidUtf8(byte[] bArr, int i, int i2) {
            while (i < i2 && bArr[i] >= 0) {
                i++;
            }
            int i3 = 0;
            if (i < i2) {
                while (true) {
                    if (i >= i2) {
                        break;
                    }
                    int i4 = i + 1;
                    byte b = bArr[i];
                    if (b >= 0) {
                        i = i4;
                    } else if (b >= -32) {
                        if (b >= -16) {
                            if (i4 < i2 - 2) {
                                int i5 = i + 2;
                                byte b2 = bArr[i4];
                                if (b2 > -65) {
                                    break;
                                }
                                if ((((b2 + 112) + (b << 28)) >> 30) != 0) {
                                    break;
                                }
                                int i6 = i + 3;
                                if (bArr[i5] > -65) {
                                    break;
                                }
                                i += 4;
                                if (bArr[i6] > -65) {
                                    break;
                                }
                            } else {
                                i3 = Utf8.access$1100(bArr, i4, i2);
                                break;
                            }
                        } else if (i4 < i2 - 1) {
                            int i7 = i + 2;
                            byte b3 = bArr[i4];
                            if (b3 > -65 || ((b == -32 && b3 < -96) || (b == -19 && b3 >= -96))) {
                                break;
                            }
                            i += 3;
                            if (bArr[i7] > -65) {
                                break;
                            }
                        } else {
                            i3 = Utf8.access$1100(bArr, i4, i2);
                            break;
                        }
                    } else if (i4 < i2) {
                        if (b < -62) {
                            break;
                        }
                        i += 2;
                        if (bArr[i4] > -65) {
                            break;
                        }
                    } else {
                        i3 = b;
                        break;
                    }
                }
                i3 = -1;
            }
            return i3 == 0;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    class UnpairedSurrogateException extends IllegalArgumentException {
        public UnpairedSurrogateException(int i, int i2) {
            super(
                    HeightInLinesModifierKt$$ExternalSyntheticOutline0.m(
                            i, i2, "Unpaired surrogate at index ", " of "));
        }
    }

    static {
        if (UnsafeUtil.HAS_UNSAFE_ARRAY_OPERATIONS && UnsafeUtil.HAS_UNSAFE_BYTEBUFFER_OPERATIONS) {
            Class cls = Android.MEMORY_CLASS;
        }
        processor = new SafeProcessor();
    }

    public static int access$1100(byte[] bArr, int i, int i2) {
        byte b = bArr[i - 1];
        int i3 = i2 - i;
        if (i3 == 0) {
            if (b > -12) {
                b = -1;
            }
            return b;
        }
        if (i3 == 1) {
            byte b2 = bArr[i];
            if (b <= -12 && b2 <= -65) {
                return (b2 << 8) ^ b;
            }
        } else {
            if (i3 != 2) {
                throw new AssertionError();
            }
            byte b3 = bArr[i];
            byte b4 = bArr[i + 1];
            if (b <= -12 && b3 <= -65 && b4 <= -65) {
                return (b4 << HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED) ^ ((b3 << 8) ^ b);
            }
        }
        return -1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0100, code lost:

       return r9 + r0;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int encode(java.lang.CharSequence r7, byte[] r8, int r9, int r10) {
        /*
            Method dump skipped, instructions count: 257
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: com.google.protobuf.Utf8.encode(java.lang.CharSequence,"
                    + " byte[], int, int):int");
    }

    public static int encodedLength(CharSequence charSequence) {
        int length = charSequence.length();
        int i = 0;
        int i2 = 0;
        while (i2 < length && charSequence.charAt(i2) < 128) {
            i2++;
        }
        int i3 = length;
        while (true) {
            if (i2 >= length) {
                break;
            }
            char charAt = charSequence.charAt(i2);
            if (charAt < 2048) {
                i3 += (127 - charAt) >>> 31;
                i2++;
            } else {
                int length2 = charSequence.length();
                while (i2 < length2) {
                    char charAt2 = charSequence.charAt(i2);
                    if (charAt2 < 2048) {
                        i += (127 - charAt2) >>> 31;
                    } else {
                        i += 2;
                        if (55296 <= charAt2 && charAt2 <= 57343) {
                            if (Character.codePointAt(charSequence, i2) < 65536) {
                                throw new UnpairedSurrogateException(i2, length2);
                            }
                            i2++;
                        }
                    }
                    i2++;
                }
                i3 += i;
            }
        }
        if (i3 >= length) {
            return i3;
        }
        throw new IllegalArgumentException(
                "UTF-8 length does not fit in int: "
                        + (i3 + GoodSettingsContract.PreferenceFlag.FLAG_NEED_TYPE));
    }
}
