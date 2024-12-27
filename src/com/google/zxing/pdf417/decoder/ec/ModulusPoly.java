package com.google.zxing.pdf417.decoder.ec;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ModulusPoly {
    public final int[] coefficients;
    public final ModulusGF field;

    public ModulusPoly(ModulusGF modulusGF, int[] iArr) {
        if (iArr.length == 0) {
            throw new IllegalArgumentException();
        }
        this.field = modulusGF;
        int length = iArr.length;
        int i = 1;
        if (length <= 1 || iArr[0] != 0) {
            this.coefficients = iArr;
            return;
        }
        while (i < length && iArr[i] == 0) {
            i++;
        }
        if (i == length) {
            this.coefficients = new int[] {0};
            return;
        }
        int i2 = length - i;
        int[] iArr2 = new int[i2];
        this.coefficients = iArr2;
        System.arraycopy(iArr, i, iArr2, 0, i2);
    }

    public final ModulusPoly add(ModulusPoly modulusPoly) {
        ModulusGF modulusGF = modulusPoly.field;
        ModulusGF modulusGF2 = this.field;
        if (!modulusGF2.equals(modulusGF)) {
            throw new IllegalArgumentException("ModulusPolys do not have same ModulusGF field");
        }
        if (isZero()) {
            return modulusPoly;
        }
        if (modulusPoly.isZero()) {
            return this;
        }
        int[] iArr = this.coefficients;
        int length = iArr.length;
        int[] iArr2 = modulusPoly.coefficients;
        if (length <= iArr2.length) {
            iArr2 = iArr;
            iArr = iArr2;
        }
        int[] iArr3 = new int[iArr.length];
        int length2 = iArr.length - iArr2.length;
        System.arraycopy(iArr, 0, iArr3, 0, length2);
        for (int i = length2; i < iArr.length; i++) {
            iArr3[i] = modulusGF2.add(iArr2[i - length2], iArr[i]);
        }
        return new ModulusPoly(modulusGF2, iArr3);
    }

    public final int evaluateAt(int i) {
        if (i == 0) {
            return getCoefficient(0);
        }
        ModulusGF modulusGF = this.field;
        int[] iArr = this.coefficients;
        if (i == 1) {
            int i2 = 0;
            for (int i3 : iArr) {
                i2 = modulusGF.add(i2, i3);
            }
            return i2;
        }
        int i4 = iArr[0];
        int length = iArr.length;
        for (int i5 = 1; i5 < length; i5++) {
            i4 = modulusGF.add(modulusGF.multiply(i, i4), iArr[i5]);
        }
        return i4;
    }

    public final int getCoefficient(int i) {
        return this.coefficients[(r1.length - 1) - i];
    }

    public final int getDegree() {
        return this.coefficients.length - 1;
    }

    public final boolean isZero() {
        return this.coefficients[0] == 0;
    }

    public final ModulusPoly multiply(ModulusPoly modulusPoly) {
        ModulusGF modulusGF = modulusPoly.field;
        ModulusGF modulusGF2 = this.field;
        if (!modulusGF2.equals(modulusGF)) {
            throw new IllegalArgumentException("ModulusPolys do not have same ModulusGF field");
        }
        if (isZero() || modulusPoly.isZero()) {
            return modulusGF2.zero;
        }
        int[] iArr = this.coefficients;
        int length = iArr.length;
        int[] iArr2 = modulusPoly.coefficients;
        int length2 = iArr2.length;
        int[] iArr3 = new int[(length + length2) - 1];
        for (int i = 0; i < length; i++) {
            int i2 = iArr[i];
            for (int i3 = 0; i3 < length2; i3++) {
                int i4 = i + i3;
                iArr3[i4] = modulusGF2.add(iArr3[i4], modulusGF2.multiply(i2, iArr2[i3]));
            }
        }
        return new ModulusPoly(modulusGF2, iArr3);
    }

    public final ModulusPoly subtract(ModulusPoly modulusPoly) {
        if (!this.field.equals(modulusPoly.field)) {
            throw new IllegalArgumentException("ModulusPolys do not have same ModulusGF field");
        }
        if (modulusPoly.isZero()) {
            return this;
        }
        int[] iArr = modulusPoly.coefficients;
        int length = iArr.length;
        int[] iArr2 = new int[length];
        int i = 0;
        while (true) {
            ModulusGF modulusGF = modulusPoly.field;
            if (i >= length) {
                return add(new ModulusPoly(modulusGF, iArr2));
            }
            int i2 = iArr[i];
            modulusGF.getClass();
            iArr2[i] = (929 - i2) % 929;
            i++;
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(getDegree() * 8);
        for (int degree = getDegree(); degree >= 0; degree--) {
            int coefficient = getCoefficient(degree);
            if (coefficient != 0) {
                if (coefficient < 0) {
                    sb.append(" - ");
                    coefficient = -coefficient;
                } else if (sb.length() > 0) {
                    sb.append(" + ");
                }
                if (degree == 0 || coefficient != 1) {
                    sb.append(coefficient);
                }
                if (degree != 0) {
                    if (degree == 1) {
                        sb.append('x');
                    } else {
                        sb.append("x^");
                        sb.append(degree);
                    }
                }
            }
        }
        return sb.toString();
    }

    public final ModulusPoly multiply(int i) {
        ModulusGF modulusGF = this.field;
        if (i == 0) {
            return modulusGF.zero;
        }
        if (i == 1) {
            return this;
        }
        int[] iArr = this.coefficients;
        int length = iArr.length;
        int[] iArr2 = new int[length];
        for (int i2 = 0; i2 < length; i2++) {
            iArr2[i2] = modulusGF.multiply(iArr[i2], i);
        }
        return new ModulusPoly(modulusGF, iArr2);
    }
}
