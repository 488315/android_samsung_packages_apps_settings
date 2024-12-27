package com.google.zxing.common.reedsolomon;

import androidx.appcompat.app.AlertController$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ReedSolomonEncoder {
    public final List cachedGenerators;
    public final GenericGF field;

    public ReedSolomonEncoder(GenericGF genericGF) {
        this.field = genericGF;
        ArrayList arrayList = new ArrayList();
        this.cachedGenerators = arrayList;
        arrayList.add(new GenericGFPoly(genericGF, new int[] {1}));
    }

    public final void encode(int i, int[] iArr) {
        if (i == 0) {
            throw new IllegalArgumentException("No error correction bytes");
        }
        int length = iArr.length - i;
        if (length <= 0) {
            throw new IllegalArgumentException("No data bytes provided");
        }
        int size = ((ArrayList) this.cachedGenerators).size();
        GenericGF genericGF = this.field;
        if (i >= size) {
            GenericGFPoly genericGFPoly =
                    (GenericGFPoly)
                            AlertController$$ExternalSyntheticOutline0.m(
                                    1, (ArrayList) this.cachedGenerators);
            for (int size2 = ((ArrayList) this.cachedGenerators).size(); size2 <= i; size2++) {
                genericGFPoly =
                        genericGFPoly.multiply(
                                new GenericGFPoly(
                                        genericGF,
                                        new int[] {
                                            1,
                                            genericGF
                                                    .expTable[(size2 - 1) + genericGF.generatorBase]
                                        }));
                ((ArrayList) this.cachedGenerators).add(genericGFPoly);
            }
        }
        GenericGFPoly genericGFPoly2 = (GenericGFPoly) ((ArrayList) this.cachedGenerators).get(i);
        int[] iArr2 = new int[length];
        System.arraycopy(iArr, 0, iArr2, 0, length);
        if (length == 0) {
            throw new IllegalArgumentException();
        }
        if (length > 1 && iArr2[0] == 0) {
            int i2 = 1;
            while (i2 < length && iArr2[i2] == 0) {
                i2++;
            }
            if (i2 == length) {
                iArr2 = new int[] {0};
            } else {
                int i3 = length - i2;
                int[] iArr3 = new int[i3];
                System.arraycopy(iArr2, i2, iArr3, 0, i3);
                iArr2 = iArr3;
            }
        }
        if (i < 0) {
            throw new IllegalArgumentException();
        }
        int length2 = iArr2.length;
        int[] iArr4 = new int[length2 + i];
        for (int i4 = 0; i4 < length2; i4++) {
            iArr4[i4] = genericGF.multiply(iArr2[i4], 1);
        }
        GenericGFPoly genericGFPoly3 = new GenericGFPoly(genericGF, iArr4);
        if (!genericGF.equals(genericGFPoly2.field)) {
            throw new IllegalArgumentException("GenericGFPolys do not have same GenericGF field");
        }
        if (genericGFPoly2.isZero()) {
            throw new IllegalArgumentException("Divide by 0");
        }
        int inverse = genericGF.inverse(genericGFPoly2.getCoefficient(genericGFPoly2.getDegree()));
        GenericGFPoly genericGFPoly4 = genericGF.zero;
        while (genericGFPoly3.getDegree() >= genericGFPoly2.getDegree()
                && !genericGFPoly3.isZero()) {
            int degree = genericGFPoly3.getDegree() - genericGFPoly2.getDegree();
            int multiply =
                    genericGF.multiply(
                            genericGFPoly3.getCoefficient(genericGFPoly3.getDegree()), inverse);
            GenericGFPoly multiplyByMonomial = genericGFPoly2.multiplyByMonomial(degree, multiply);
            genericGFPoly4 =
                    genericGFPoly4.addOrSubtract(genericGF.buildMonomial(degree, multiply));
            genericGFPoly3 = genericGFPoly3.addOrSubtract(multiplyByMonomial);
        }
        int[] iArr5 = new GenericGFPoly[] {genericGFPoly4, genericGFPoly3}[1].coefficients;
        int length3 = i - iArr5.length;
        for (int i5 = 0; i5 < length3; i5++) {
            iArr[length + i5] = 0;
        }
        System.arraycopy(iArr5, 0, iArr, length + length3, iArr5.length);
    }
}
