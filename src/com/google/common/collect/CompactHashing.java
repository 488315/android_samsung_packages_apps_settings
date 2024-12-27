package com.google.common.collect;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class CompactHashing {
    public static Object createTable(int i) {
        if (i < 2 || i > 1073741824 || Integer.highestOneBit(i) != i) {
            throw new IllegalArgumentException(
                    SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                            i, "must be power of 2 between 2^1 and 2^30: "));
        }
        return i <= 256 ? new byte[i] : i <= 65536 ? new short[i] : new int[i];
    }

    public static int maskCombine(int i, int i2, int i3) {
        return (i & (~i3)) | (i2 & i3);
    }

    public static int newCapacity(int i) {
        return (i + 1) * (i < 32 ? 4 : 2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x002b, code lost:

       r9 = r6 & r11;
    */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x002d, code lost:

       if (r5 != (-1)) goto L16;
    */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x002f, code lost:

       tableSet(r1, r9, r12);
    */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x003b, code lost:

       return r2;
    */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0033, code lost:

       r13[r5] = maskCombine(r13[r5], r9, r11);
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int remove(
            java.lang.Object r9,
            java.lang.Object r10,
            int r11,
            java.lang.Object r12,
            int[] r13,
            java.lang.Object[] r14,
            java.lang.Object[] r15) {
        /*
            int r0 = com.google.common.collect.Hashing.smearedHash(r9)
            r1 = r0 & r11
            int r2 = tableGet(r1, r12)
            r3 = -1
            if (r2 != 0) goto Le
            return r3
        Le:
            int r4 = ~r11
            r0 = r0 & r4
            r5 = r3
        L11:
            int r2 = r2 + (-1)
            r6 = r13[r2]
            r7 = r6 & r4
            if (r7 != r0) goto L3c
            r7 = r14[r2]
            boolean r7 = com.google.common.base.Objects.equal(r9, r7)
            if (r7 == 0) goto L3c
            if (r15 == 0) goto L2b
            r7 = r15[r2]
            boolean r7 = com.google.common.base.Objects.equal(r10, r7)
            if (r7 == 0) goto L3c
        L2b:
            r9 = r6 & r11
            if (r5 != r3) goto L33
            tableSet(r1, r9, r12)
            goto L3b
        L33:
            r10 = r13[r5]
            int r9 = maskCombine(r10, r9, r11)
            r13[r5] = r9
        L3b:
            return r2
        L3c:
            r5 = r6 & r11
            if (r5 != 0) goto L41
            return r3
        L41:
            r8 = r5
            r5 = r2
            r2 = r8
            goto L11
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.common.collect.CompactHashing.remove(java.lang.Object,"
                    + " java.lang.Object, int, java.lang.Object, int[], java.lang.Object[],"
                    + " java.lang.Object[]):int");
    }

    public static int tableGet(int i, Object obj) {
        return obj instanceof byte[]
                ? ((byte[]) obj)[i] & 255
                : obj instanceof short[] ? ((short[]) obj)[i] & 65535 : ((int[]) obj)[i];
    }

    public static void tableSet(int i, int i2, Object obj) {
        if (obj instanceof byte[]) {
            ((byte[]) obj)[i] = (byte) i2;
        } else if (obj instanceof short[]) {
            ((short[]) obj)[i] = (short) i2;
        } else {
            ((int[]) obj)[i] = i2;
        }
    }
}
