package com.google.zxing.common.reedsolomon;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ReedSolomonDecoder {
    public final GenericGF field;

    public ReedSolomonDecoder(GenericGF genericGF) {
        this.field = genericGF;
    }

    public final int decodeWithECCount(int i, int[] iArr) {
        int[] iArr2;
        GenericGF genericGF;
        int[] iArr3;
        int i2;
        if (iArr.length == 0) {
            throw new IllegalArgumentException();
        }
        int length = iArr.length;
        if (length <= 1 || iArr[0] != 0) {
            iArr2 = iArr;
        } else {
            int i3 = 1;
            while (i3 < length && iArr[i3] == 0) {
                i3++;
            }
            if (i3 == length) {
                iArr2 = new int[] {0};
            } else {
                int i4 = length - i3;
                int[] iArr4 = new int[i4];
                System.arraycopy(iArr, i3, iArr4, 0, i4);
                iArr2 = iArr4;
            }
        }
        int[] iArr5 = new int[i];
        boolean z = true;
        int i5 = 0;
        while (true) {
            genericGF = this.field;
            if (i5 >= i) {
                break;
            }
            int i6 = genericGF.expTable[genericGF.generatorBase + i5];
            if (i6 == 0) {
                i2 = iArr2[iArr2.length - 1];
            } else if (i6 == 1) {
                int i7 = 0;
                for (int i8 : iArr2) {
                    GenericGF genericGF2 = GenericGF.AZTEC_DATA_12;
                    i7 ^= i8;
                }
                i2 = i7;
            } else {
                int i9 = iArr2[0];
                int length2 = iArr2.length;
                for (int i10 = 1; i10 < length2; i10++) {
                    i9 = genericGF.multiply(i6, i9) ^ iArr2[i10];
                }
                i2 = i9;
            }
            iArr5[(i - 1) - i5] = i2;
            if (i2 != 0) {
                z = false;
            }
            i5++;
        }
        if (z) {
            return 0;
        }
        GenericGFPoly genericGFPoly = new GenericGFPoly(genericGF, iArr5);
        GenericGFPoly buildMonomial = genericGF.buildMonomial(i, 1);
        if (buildMonomial.getDegree() >= genericGFPoly.getDegree()) {
            buildMonomial = genericGFPoly;
            genericGFPoly = buildMonomial;
        }
        GenericGFPoly genericGFPoly2 = genericGF.zero;
        GenericGFPoly genericGFPoly3 = genericGF.one;
        GenericGFPoly genericGFPoly4 = genericGFPoly2;
        GenericGFPoly genericGFPoly5 = buildMonomial;
        GenericGFPoly genericGFPoly6 = genericGFPoly;
        GenericGFPoly genericGFPoly7 = genericGFPoly5;
        while (genericGFPoly7.getDegree() * 2 >= i) {
            if (genericGFPoly7.isZero()) {
                throw new ReedSolomonException("r_{i-1} was zero");
            }
            int inverse =
                    genericGF.inverse(genericGFPoly7.getCoefficient(genericGFPoly7.getDegree()));
            GenericGFPoly genericGFPoly8 = genericGFPoly2;
            while (genericGFPoly6.getDegree() >= genericGFPoly7.getDegree()
                    && !genericGFPoly6.isZero()) {
                int degree = genericGFPoly6.getDegree() - genericGFPoly7.getDegree();
                int multiply =
                        genericGF.multiply(
                                genericGFPoly6.getCoefficient(genericGFPoly6.getDegree()), inverse);
                genericGFPoly8 =
                        genericGFPoly8.addOrSubtract(genericGF.buildMonomial(degree, multiply));
                genericGFPoly6 =
                        genericGFPoly6.addOrSubtract(
                                genericGFPoly7.multiplyByMonomial(degree, multiply));
            }
            GenericGFPoly addOrSubtract =
                    genericGFPoly8.multiply(genericGFPoly3).addOrSubtract(genericGFPoly4);
            if (genericGFPoly6.getDegree() >= genericGFPoly7.getDegree()) {
                throw new IllegalStateException(
                        "Division algorithm failed to reduce polynomial? r: "
                                + genericGFPoly6
                                + ", rLast: "
                                + genericGFPoly7);
            }
            GenericGFPoly genericGFPoly9 = genericGFPoly6;
            genericGFPoly6 = genericGFPoly7;
            genericGFPoly7 = genericGFPoly9;
            genericGFPoly4 = genericGFPoly3;
            genericGFPoly3 = addOrSubtract;
        }
        int coefficient = genericGFPoly3.getCoefficient(0);
        if (coefficient == 0) {
            throw new ReedSolomonException("sigmaTilde(0) was zero");
        }
        int inverse2 = genericGF.inverse(coefficient);
        GenericGFPoly[] genericGFPolyArr = {
            genericGFPoly3.multiply(inverse2), genericGFPoly7.multiply(inverse2)
        };
        GenericGFPoly genericGFPoly10 = genericGFPolyArr[0];
        GenericGFPoly genericGFPoly11 = genericGFPolyArr[1];
        int degree2 = genericGFPoly10.getDegree();
        if (degree2 == 1) {
            iArr3 = new int[] {genericGFPoly10.getCoefficient(1)};
        } else {
            int[] iArr6 = new int[degree2];
            int i11 = 0;
            for (int i12 = 1; i12 < genericGF.size && i11 < degree2; i12++) {
                if (genericGFPoly10.evaluateAt(i12) == 0) {
                    iArr6[i11] = genericGF.inverse(i12);
                    i11++;
                }
            }
            if (i11 != degree2) {
                throw new ReedSolomonException(
                        "Error locator degree does not match number of roots");
            }
            iArr3 = iArr6;
        }
        int length3 = iArr3.length;
        int[] iArr7 = new int[length3];
        for (int i13 = 0; i13 < length3; i13++) {
            int inverse3 = genericGF.inverse(iArr3[i13]);
            int i14 = 1;
            for (int i15 = 0; i15 < length3; i15++) {
                if (i13 != i15) {
                    int multiply2 = genericGF.multiply(iArr3[i15], inverse3);
                    i14 =
                            genericGF.multiply(
                                    i14, (multiply2 & 1) == 0 ? multiply2 | 1 : multiply2 & (-2));
                }
            }
            int multiply3 =
                    genericGF.multiply(
                            genericGFPoly11.evaluateAt(inverse3), genericGF.inverse(i14));
            iArr7[i13] = multiply3;
            if (genericGF.generatorBase != 0) {
                iArr7[i13] = genericGF.multiply(multiply3, inverse3);
            }
        }
        for (int i16 = 0; i16 < iArr3.length; i16++) {
            int length4 = iArr.length - 1;
            int i17 = iArr3[i16];
            if (i17 == 0) {
                throw new IllegalArgumentException();
            }
            int i18 = length4 - genericGF.logTable[i17];
            if (i18 < 0) {
                throw new ReedSolomonException("Bad error location");
            }
            iArr[i18] = iArr[i18] ^ iArr7[i16];
        }
        return iArr3.length;
    }
}
