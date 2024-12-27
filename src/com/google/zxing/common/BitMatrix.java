package com.google.zxing.common;

import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class BitMatrix implements Cloneable {
    public int[] bits;
    public int height;
    public int rowSize;
    public int width;

    public BitMatrix(int i, int i2) {
        if (i < 1 || i2 < 1) {
            throw new IllegalArgumentException("Both dimensions must be greater than 0");
        }
        this.width = i;
        this.height = i2;
        int i3 = (i + 31) / 32;
        this.rowSize = i3;
        this.bits = new int[i3 * i2];
    }

    public final Object clone() {
        int i = this.width;
        int i2 = this.height;
        int i3 = this.rowSize;
        int[] iArr = (int[]) this.bits.clone();
        BitMatrix bitMatrix = new BitMatrix();
        bitMatrix.width = i;
        bitMatrix.height = i2;
        bitMatrix.rowSize = i3;
        bitMatrix.bits = iArr;
        return bitMatrix;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof BitMatrix)) {
            return false;
        }
        BitMatrix bitMatrix = (BitMatrix) obj;
        return this.width == bitMatrix.width
                && this.height == bitMatrix.height
                && this.rowSize == bitMatrix.rowSize
                && Arrays.equals(this.bits, bitMatrix.bits);
    }

    public final void flip(int i, int i2) {
        int i3 = (i / 32) + (i2 * this.rowSize);
        int[] iArr = this.bits;
        iArr[i3] = (1 << (i & 31)) ^ iArr[i3];
    }

    public final boolean get(int i, int i2) {
        return ((this.bits[(i / 32) + (i2 * this.rowSize)] >>> (i & 31)) & 1) != 0;
    }

    public final int[] getBottomRightOnBit() {
        int length = this.bits.length - 1;
        while (length >= 0 && this.bits[length] == 0) {
            length--;
        }
        if (length < 0) {
            return null;
        }
        int i = this.rowSize;
        int i2 = length / i;
        int i3 = (length % i) * 32;
        int i4 = this.bits[length];
        int i5 = 31;
        while ((i4 >>> i5) == 0) {
            i5--;
        }
        return new int[] {i3 + i5, i2};
    }

    public final BitArray getRow(int i, BitArray bitArray) {
        int i2 = bitArray.size;
        int i3 = this.width;
        if (i2 < i3) {
            bitArray = new BitArray(i3);
        } else {
            int length = bitArray.bits.length;
            for (int i4 = 0; i4 < length; i4++) {
                bitArray.bits[i4] = 0;
            }
        }
        int i5 = i * this.rowSize;
        for (int i6 = 0; i6 < this.rowSize; i6++) {
            bitArray.bits[(i6 * 32) / 32] = this.bits[i5 + i6];
        }
        return bitArray;
    }

    public final int[] getTopLeftOnBit() {
        int[] iArr;
        int i = 0;
        int i2 = 0;
        while (true) {
            iArr = this.bits;
            if (i2 >= iArr.length || iArr[i2] != 0) {
                break;
            }
            i2++;
        }
        if (i2 == iArr.length) {
            return null;
        }
        int i3 = this.rowSize;
        int i4 = i2 / i3;
        int i5 = (i2 % i3) * 32;
        while ((iArr[i2] << (31 - i)) == 0) {
            i++;
        }
        return new int[] {i5 + i, i4};
    }

    public final int hashCode() {
        int i = this.width;
        return Arrays.hashCode(this.bits)
                + (((((((i * 31) + i) * 31) + this.height) * 31) + this.rowSize) * 31);
    }

    public final void rotate180() {
        BitArray bitArray = new BitArray(this.width);
        BitArray bitArray2 = new BitArray(this.width);
        int i = (this.height + 1) / 2;
        for (int i2 = 0; i2 < i; i2++) {
            bitArray = getRow(i2, bitArray);
            int i3 = (this.height - 1) - i2;
            bitArray2 = getRow(i3, bitArray2);
            bitArray.reverse();
            bitArray2.reverse();
            int[] iArr = bitArray2.bits;
            int[] iArr2 = this.bits;
            int i4 = this.rowSize;
            System.arraycopy(iArr, 0, iArr2, i2 * i4, i4);
            int[] iArr3 = bitArray.bits;
            int[] iArr4 = this.bits;
            int i5 = this.rowSize;
            System.arraycopy(iArr3, 0, iArr4, i3 * i5, i5);
        }
    }

    public final void rotate90() {
        int i = this.height;
        int i2 = this.width;
        int i3 = (i + 31) / 32;
        int[] iArr = new int[i3 * i2];
        for (int i4 = 0; i4 < this.height; i4++) {
            for (int i5 = 0; i5 < this.width; i5++) {
                if (((this.bits[(i5 / 32) + (this.rowSize * i4)] >>> (i5 & 31)) & 1) != 0) {
                    int i6 = (i4 / 32) + (((i2 - 1) - i5) * i3);
                    iArr[i6] = iArr[i6] | (1 << (i4 & 31));
                }
            }
        }
        this.width = i;
        this.height = i2;
        this.rowSize = i3;
        this.bits = iArr;
    }

    public final void set(int i, int i2) {
        int i3 = (i / 32) + (i2 * this.rowSize);
        int[] iArr = this.bits;
        iArr[i3] = (1 << (i & 31)) | iArr[i3];
    }

    public final void setRegion(int i, int i2, int i3, int i4) {
        if (i2 < 0 || i < 0) {
            throw new IllegalArgumentException("Left and top must be nonnegative");
        }
        if (i4 < 1 || i3 < 1) {
            throw new IllegalArgumentException("Height and width must be at least 1");
        }
        int i5 = i3 + i;
        int i6 = i4 + i2;
        if (i6 > this.height || i5 > this.width) {
            throw new IllegalArgumentException("The region must fit inside the matrix");
        }
        while (i2 < i6) {
            int i7 = this.rowSize * i2;
            for (int i8 = i; i8 < i5; i8++) {
                int[] iArr = this.bits;
                int i9 = (i8 / 32) + i7;
                iArr[i9] = iArr[i9] | (1 << (i8 & 31));
            }
            i2++;
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder((this.width + 1) * this.height);
        for (int i = 0; i < this.height; i++) {
            for (int i2 = 0; i2 < this.width; i2++) {
                sb.append(get(i2, i) ? "X " : "  ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
