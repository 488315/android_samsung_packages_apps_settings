package com.google.zxing.pdf417.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.common.ECIStringBuilder;
import com.samsung.android.knox.net.vpn.VpnErrorValues;

import java.math.BigInteger;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class DecodedBitStreamParser {
    public static final BigInteger[] EXP900;
    public static final char[] PUNCT_CHARS = ";<>@[\\]_`~!\r\t,:\n-.$/\"|*()?{}'".toCharArray();
    public static final char[] MIXED_CHARS = "0123456789&\r\t,:#-.$/+%*=^".toCharArray();

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    final class Mode {
        public static final /* synthetic */ Mode[] $VALUES;
        public static final Mode ALPHA;
        public static final Mode ALPHA_SHIFT;
        public static final Mode LOWER;
        public static final Mode MIXED;
        public static final Mode PUNCT;
        public static final Mode PUNCT_SHIFT;

        static {
            Mode mode = new Mode("ALPHA", 0);
            ALPHA = mode;
            Mode mode2 = new Mode("LOWER", 1);
            LOWER = mode2;
            Mode mode3 = new Mode("MIXED", 2);
            MIXED = mode3;
            Mode mode4 = new Mode("PUNCT", 3);
            PUNCT = mode4;
            Mode mode5 = new Mode("ALPHA_SHIFT", 4);
            ALPHA_SHIFT = mode5;
            Mode mode6 = new Mode("PUNCT_SHIFT", 5);
            PUNCT_SHIFT = mode6;
            $VALUES = new Mode[] {mode, mode2, mode3, mode4, mode5, mode6};
        }

        public static Mode valueOf(String str) {
            return (Mode) Enum.valueOf(Mode.class, str);
        }

        public static Mode[] values() {
            return (Mode[]) $VALUES.clone();
        }
    }

    static {
        BigInteger[] bigIntegerArr = new BigInteger[16];
        EXP900 = bigIntegerArr;
        bigIntegerArr[0] = BigInteger.ONE;
        BigInteger valueOf = BigInteger.valueOf(900L);
        bigIntegerArr[1] = valueOf;
        int i = 2;
        while (true) {
            BigInteger[] bigIntegerArr2 = EXP900;
            if (i >= bigIntegerArr2.length) {
                return;
            }
            bigIntegerArr2[i] = bigIntegerArr2[i - 1].multiply(valueOf);
            i++;
        }
    }

    public static String decodeBase900toBase10(int i, int[] iArr) {
        BigInteger bigInteger = BigInteger.ZERO;
        for (int i2 = 0; i2 < i; i2++) {
            bigInteger =
                    bigInteger.add(EXP900[(i - i2) - 1].multiply(BigInteger.valueOf(iArr[i2])));
        }
        String bigInteger2 = bigInteger.toString();
        if (bigInteger2.charAt(0) == '1') {
            return bigInteger2.substring(1);
        }
        throw FormatException.getFormatInstance();
    }

    /* JADX WARN: Removed duplicated region for block: B:55:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x008e A[FALL_THROUGH, PHI: r2 r3 r4
     0x008e: PHI (r2v6 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode) =
     (r2v3 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode)
     (r2v1 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode)
     (r2v1 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode)
     (r2v1 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode)
     (r2v10 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode)
     (r2v1 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode)
     (r2v1 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode)
     (r2v1 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode)
     (r2v13 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode)
     (r2v1 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode)
     (r2v1 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode)
     (r2v1 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode)
    binds: [B:79:0x00d6, B:81:0x00da, B:76:0x00ce, B:77:0x00d2, B:68:0x00b4, B:70:0x00bc, B:65:0x00ac, B:66:0x00b0, B:54:0x0091, B:58:0x0099, B:51:0x0088, B:52:0x008c] A[DONT_GENERATE, DONT_INLINE]
     0x008e: PHI (r3v4 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode) =
     (r3v2 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode)
     (r3v1 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode)
     (r3v1 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode)
     (r3v3 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode)
     (r3v7 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode)
     (r3v1 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode)
     (r3v1 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode)
     (r3v9 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode)
     (r3v10 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode)
     (r3v1 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode)
     (r3v1 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode)
     (r3v11 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode)
    binds: [B:79:0x00d6, B:81:0x00da, B:76:0x00ce, B:77:0x00d2, B:68:0x00b4, B:70:0x00bc, B:65:0x00ac, B:66:0x00b0, B:54:0x0091, B:58:0x0099, B:51:0x0088, B:52:0x008c] A[DONT_GENERATE, DONT_INLINE]
     0x008e: PHI (r4v3 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode) =
     (r4v1 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode)
     (r4v1 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode)
     (r4v1 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode)
     (r4v2 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode)
     (r4v1 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode)
     (r4v1 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode)
     (r4v1 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode)
     (r4v7 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode)
     (r4v1 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode)
     (r4v1 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode)
     (r4v1 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode)
     (r4v8 com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode)
    binds: [B:79:0x00d6, B:81:0x00da, B:76:0x00ce, B:77:0x00d2, B:68:0x00b4, B:70:0x00bc, B:65:0x00ac, B:66:0x00b0, B:54:0x0091, B:58:0x0099, B:51:0x0088, B:52:0x008c] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode decodeTextCompaction(
            int[] r16,
            int[] r17,
            int r18,
            com.google.zxing.common.ECIStringBuilder r19,
            com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode r20) {
        /*
            Method dump skipped, instructions count: 276
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.zxing.pdf417.decoder.DecodedBitStreamParser.decodeTextCompaction(int[],"
                    + " int[], int, com.google.zxing.common.ECIStringBuilder,"
                    + " com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode):com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode");
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x003e, code lost:

       r10.currentBytes.append(decodeBase900toBase10(r3, r0));
       r3 = 0;
    */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x003c A[ADDED_TO_REGION, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int numericCompaction(
            int[] r8, int r9, com.google.zxing.common.ECIStringBuilder r10) {
        /*
            r0 = 15
            int[] r0 = new int[r0]
            r1 = 0
            r2 = r1
            r3 = r2
        L7:
            r4 = r8[r1]
            if (r9 >= r4) goto L49
            if (r2 != 0) goto L49
            int r5 = r9 + 1
            r6 = r8[r9]
            r7 = 1
            if (r5 != r4) goto L15
            r2 = r7
        L15:
            r4 = 900(0x384, float:1.261E-42)
            if (r6 >= r4) goto L1f
            r0[r3] = r6
            int r3 = r3 + 1
        L1d:
            r9 = r5
            goto L32
        L1f:
            if (r6 == r4) goto L31
            r4 = 901(0x385, float:1.263E-42)
            if (r6 == r4) goto L31
            r4 = 927(0x39f, float:1.299E-42)
            if (r6 == r4) goto L31
            r4 = 928(0x3a0, float:1.3E-42)
            if (r6 == r4) goto L31
            switch(r6) {
                case 922: goto L31;
                case 923: goto L31;
                case 924: goto L31;
                default: goto L30;
            }
        L30:
            goto L1d
        L31:
            r2 = r7
        L32:
            int r4 = r3 % 15
            if (r4 == 0) goto L3c
            r4 = 902(0x386, float:1.264E-42)
            if (r6 == r4) goto L3c
            if (r2 == 0) goto L7
        L3c:
            if (r3 <= 0) goto L7
            java.lang.String r3 = decodeBase900toBase10(r3, r0)
            java.lang.StringBuilder r4 = r10.currentBytes
            r4.append(r3)
            r3 = r1
            goto L7
        L49:
            return r9
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.zxing.pdf417.decoder.DecodedBitStreamParser.numericCompaction(int[],"
                    + " int, com.google.zxing.common.ECIStringBuilder):int");
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:28:0x0036. Please report as an issue. */
    /* JADX WARN: Failed to find 'out' block for switch in B:29:0x0039. Please report as an issue. */
    public static int textCompaction(int[] iArr, int i, ECIStringBuilder eCIStringBuilder) {
        int i2 = (iArr[0] - i) * 2;
        int[] iArr2 = new int[i2];
        int[] iArr3 = new int[i2];
        Mode mode = Mode.ALPHA;
        boolean z = false;
        int i3 = 0;
        while (i < iArr[0] && !z) {
            int i4 = i + 1;
            int i5 = iArr[i];
            if (i5 < 900) {
                iArr2[i3] = i5 / 30;
                iArr2[i3 + 1] = i5 % 30;
                i3 += 2;
            } else if (i5 == 913) {
                iArr2[i3] = 913;
                i += 2;
                iArr3[i3] = iArr[i4];
                i3++;
            } else if (i5 != 927) {
                if (i5 != 928) {
                    switch (i5) {
                        case 900:
                            iArr2[i3] = 900;
                            i3++;
                            break;
                        case 901:
                        case VpnErrorValues.ERROR_USB_TETHERING_FAILED /* 902 */:
                            break;
                        default:
                            switch (i5) {
                            }
                    }
                }
                z = true;
            } else {
                Mode decodeTextCompaction =
                        decodeTextCompaction(iArr2, iArr3, i3, eCIStringBuilder, mode);
                i += 2;
                eCIStringBuilder.appendECI(iArr[i4]);
                int i6 = iArr[0];
                if (i > i6) {
                    throw FormatException.getFormatInstance();
                }
                int i7 = (i6 - i) * 2;
                i3 = 0;
                mode = decodeTextCompaction;
                iArr3 = new int[i7];
                iArr2 = new int[i7];
            }
            i = i4;
        }
        decodeTextCompaction(iArr2, iArr3, i3, eCIStringBuilder, mode);
        return i;
    }
}
