package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.sec.ims.settings.ImsProfile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class Code128Writer extends OneDimensionalCodeWriter {

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    final class CType {
        public static final /* synthetic */ CType[] $VALUES;
        public static final CType FNC_1;
        public static final CType ONE_DIGIT;
        public static final CType TWO_DIGITS;
        public static final CType UNCODABLE;

        static {
            CType cType = new CType("UNCODABLE", 0);
            UNCODABLE = cType;
            CType cType2 = new CType("ONE_DIGIT", 1);
            ONE_DIGIT = cType2;
            CType cType3 = new CType("TWO_DIGITS", 2);
            TWO_DIGITS = cType3;
            CType cType4 = new CType("FNC_1", 3);
            FNC_1 = cType4;
            $VALUES = new CType[] {cType, cType2, cType3, cType4};
        }

        public static CType valueOf(String str) {
            return (CType) Enum.valueOf(CType.class, str);
        }

        public static CType[] values() {
            return (CType[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class MinimalEncoder {
        public int[][] memoizedCost;
        public Latch[][] minPath;

        /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
        /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        final class Charset {
            public static final /* synthetic */ Charset[] $VALUES;
            public static final Charset A;
            public static final Charset B;
            public static final Charset C;
            public static final Charset NONE;

            static {
                Charset charset = new Charset(ImsProfile.TIMER_NAME_A, 0);
                A = charset;
                Charset charset2 = new Charset(ImsProfile.TIMER_NAME_B, 1);
                B = charset2;
                Charset charset3 = new Charset(ImsProfile.TIMER_NAME_C, 2);
                C = charset3;
                Charset charset4 = new Charset("NONE", 3);
                NONE = charset4;
                $VALUES = new Charset[] {charset, charset2, charset3, charset4};
            }

            public static Charset valueOf(String str) {
                return (Charset) Enum.valueOf(Charset.class, str);
            }

            public static Charset[] values() {
                return (Charset[]) $VALUES.clone();
            }
        }

        /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
        /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        final class Latch {
            public static final /* synthetic */ Latch[] $VALUES;
            public static final Latch C;
            public static final Latch NONE;
            public static final Latch SHIFT;

            /* JADX INFO: Fake field, exist only in values array */
            Latch EF0;

            static {
                Latch latch = new Latch(ImsProfile.TIMER_NAME_A, 0);
                Latch latch2 = new Latch(ImsProfile.TIMER_NAME_B, 1);
                Latch latch3 = new Latch(ImsProfile.TIMER_NAME_C, 2);
                C = latch3;
                Latch latch4 = new Latch("SHIFT", 3);
                SHIFT = latch4;
                Latch latch5 = new Latch("NONE", 4);
                NONE = latch5;
                $VALUES = new Latch[] {latch, latch2, latch3, latch4, latch5};
            }

            public static Latch valueOf(String str) {
                return (Latch) Enum.valueOf(Latch.class, str);
            }

            public static Latch[] values() {
                return (Latch[]) $VALUES.clone();
            }
        }

        public static void addPattern(
                Collection collection, int i, int[] iArr, int[] iArr2, int i2) {
            ((ArrayList) collection).add(Code128Reader.CODE_PATTERNS[i]);
            if (i2 != 0) {
                iArr2[0] = iArr2[0] + 1;
            }
            iArr[0] = (i * iArr2[0]) + iArr[0];
        }

        public static boolean canEncode(CharSequence charSequence, Charset charset, int i) {
            int i2;
            char charAt;
            char charAt2 = charSequence.charAt(i);
            int ordinal = charset.ordinal();
            if (ordinal == 0) {
                return charAt2 == 241
                        || charAt2 == 242
                        || charAt2 == 243
                        || charAt2 == 244
                        || " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_\u0000\u0001\u0002\u0003\u0004\u0005\u0006\u0007\b\t\n\u000b\f\r\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u001fÿ"
                                        .indexOf(charAt2)
                                >= 0;
            }
            if (ordinal == 1) {
                return charAt2 == 241
                        || charAt2 == 242
                        || charAt2 == 243
                        || charAt2 == 244
                        || " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u007fÿ"
                                        .indexOf(charAt2)
                                >= 0;
            }
            if (ordinal != 2) {
                return false;
            }
            return charAt2 == 241
                    || ((i2 = i + 1) < charSequence.length()
                            && charAt2 >= '0'
                            && charAt2 <= '9'
                            && (charAt = charSequence.charAt(i2)) >= '0'
                            && charAt <= '9');
        }

        public final int encode(CharSequence charSequence, Charset charset, int i) {
            int i2;
            Latch latch;
            int i3;
            int i4 = this.memoizedCost[charset.ordinal()][i];
            if (i4 > 0) {
                return i4;
            }
            Latch latch2 = Latch.NONE;
            int i5 = i + 1;
            int i6 = 1;
            int i7 = 0;
            boolean z = i5 >= charSequence.length();
            Charset[] charsetArr = {Charset.A, Charset.B};
            Latch latch3 = latch2;
            int i8 = Integer.MAX_VALUE;
            while (true) {
                if (i7 > i6) {
                    break;
                }
                if (canEncode(charSequence, charsetArr[i7], i)) {
                    Charset charset2 = charsetArr[i7];
                    if (charset != charset2) {
                        latch = Latch.valueOf(charset2.toString());
                        i3 = 2;
                    } else {
                        latch = latch2;
                        i3 = i6;
                    }
                    if (!z) {
                        i3 += encode(charSequence, charsetArr[i7], i5);
                    }
                    if (i3 < i8) {
                        latch3 = latch;
                        i8 = i3;
                    }
                    if (charset == charsetArr[(i7 + 1) % 2]) {
                        Latch latch4 = Latch.SHIFT;
                        int encode = z ? 2 : encode(charSequence, charset, i5) + 2;
                        if (encode < i8) {
                            latch3 = latch4;
                            i8 = encode;
                        }
                    }
                }
                i7++;
                i6 = 1;
            }
            Charset charset3 = Charset.C;
            if (canEncode(charSequence, charset3, i)) {
                if (charset != charset3) {
                    latch2 = Latch.C;
                    i2 = 2;
                } else {
                    i2 = 1;
                }
                int i9 = (charSequence.charAt(i) == 241 ? 1 : 2) + i;
                if (i9 < charSequence.length()) {
                    i2 += encode(charSequence, charset3, i9);
                }
                if (i2 < i8) {
                    latch3 = latch2;
                    i8 = i2;
                }
            }
            if (i8 != Integer.MAX_VALUE) {
                this.memoizedCost[charset.ordinal()][i] = i8;
                this.minPath[charset.ordinal()][i] = latch3;
                return i8;
            }
            throw new IllegalArgumentException(
                    "Bad character in input: ASCII value=" + ((int) charSequence.charAt(i)));
        }
    }

    public static CType findCType(int i, CharSequence charSequence) {
        int length = charSequence.length();
        CType cType = CType.UNCODABLE;
        if (i >= length) {
            return cType;
        }
        char charAt = charSequence.charAt(i);
        if (charAt == 241) {
            return CType.FNC_1;
        }
        if (charAt >= '0' && charAt <= '9') {
            int i2 = i + 1;
            cType = CType.ONE_DIGIT;
            if (i2 >= length) {
                return cType;
            }
            char charAt2 = charSequence.charAt(i2);
            if (charAt2 >= '0' && charAt2 <= '9') {
                return CType.TWO_DIGITS;
            }
        }
        return cType;
    }

    public static boolean[] produceResult(int i, Collection collection) {
        int[][] iArr = Code128Reader.CODE_PATTERNS;
        ArrayList arrayList = (ArrayList) collection;
        arrayList.add(iArr[i % 103]);
        arrayList.add(iArr[106]);
        Iterator it = arrayList.iterator();
        int i2 = 0;
        int i3 = 0;
        while (it.hasNext()) {
            for (int i4 : (int[]) it.next()) {
                i3 += i4;
            }
        }
        boolean[] zArr = new boolean[i3];
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            i2 += OneDimensionalCodeWriter.appendPattern(zArr, i2, (int[]) it2.next(), true);
        }
        return zArr;
    }

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter
    public final boolean[] encode(String str) {
        return encode(str, null);
    }

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter
    public final Collection getSupportedWriteFormats() {
        return Collections.singleton(BarcodeFormat.CODE_128);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:184:0x022d, code lost:

       if (r9 > r12) goto L146;
    */
    /* JADX WARN: Code restructure failed: missing block: B:214:0x0282, code lost:

       if (r11 == r15) goto L147;
    */
    /* JADX WARN: Code restructure failed: missing block: B:218:0x028f, code lost:

       if (r13 == r12) goto L167;
    */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00d1 A[SYNTHETIC] */
    @Override // com.google.zxing.oned.OneDimensionalCodeWriter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean[] encode(java.lang.String r22, java.util.Map r23) {
        /*
            Method dump skipped, instructions count: 916
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.zxing.oned.Code128Writer.encode(java.lang.String,"
                    + " java.util.Map):boolean[]");
    }
}
