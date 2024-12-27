package com.google.zxing.qrcode.detector;

import com.google.zxing.common.BitMatrix;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class FinderPatternFinder {
    public static final EstimatedModuleComparator moduleComparator =
            new EstimatedModuleComparator(0);
    public boolean hasSkipped;
    public final BitMatrix image;
    public final List possibleCenters = new ArrayList();
    public final int[] crossCheckStateCount = new int[5];

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class EstimatedModuleComparator
            implements Comparator<FinderPattern>, Serializable {
        private EstimatedModuleComparator() {}

        @Override // java.util.Comparator
        public final int compare(FinderPattern finderPattern, FinderPattern finderPattern2) {
            return Float.compare(
                    finderPattern.estimatedModuleSize, finderPattern2.estimatedModuleSize);
        }

        public /* synthetic */ EstimatedModuleComparator(int i) {
            this();
        }
    }

    public FinderPatternFinder(BitMatrix bitMatrix) {
        this.image = bitMatrix;
    }

    public static float centerFromEnd(int i, int[] iArr) {
        return ((i - iArr[4]) - iArr[3]) - (iArr[2] / 2.0f);
    }

    public static boolean foundPatternCross(int[] iArr) {
        int i = 0;
        for (int i2 = 0; i2 < 5; i2++) {
            int i3 = iArr[i2];
            if (i3 == 0) {
                return false;
            }
            i += i3;
        }
        if (i < 7) {
            return false;
        }
        float f = i / 7.0f;
        float f2 = f / 2.0f;
        return Math.abs(f - ((float) iArr[0])) < f2
                && Math.abs(f - ((float) iArr[1])) < f2
                && Math.abs((f * 3.0f) - ((float) iArr[2])) < 3.0f * f2
                && Math.abs(f - ((float) iArr[3])) < f2
                && Math.abs(f - ((float) iArr[4])) < f2;
    }

    public static double squaredDistance(
            FinderPattern finderPattern, FinderPattern finderPattern2) {
        double d = finderPattern.x - finderPattern2.x;
        double d2 = finderPattern.y - finderPattern2.y;
        return (d2 * d2) + (d * d);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x00df  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x019f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean handlePossibleCenter(int r18, int r19, int[] r20) {
        /*
            Method dump skipped, instructions count: 804
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.zxing.qrcode.detector.FinderPatternFinder.handlePossibleCenter(int,"
                    + " int, int[]):boolean");
    }

    public final boolean haveMultiplyConfirmedCenters() {
        int size = ((ArrayList) this.possibleCenters).size();
        Iterator it = ((ArrayList) this.possibleCenters).iterator();
        float f = 0.0f;
        int i = 0;
        float f2 = 0.0f;
        while (it.hasNext()) {
            FinderPattern finderPattern = (FinderPattern) it.next();
            if (finderPattern.count >= 2) {
                i++;
                f2 += finderPattern.estimatedModuleSize;
            }
        }
        if (i < 3) {
            return false;
        }
        float f3 = f2 / size;
        Iterator it2 = ((ArrayList) this.possibleCenters).iterator();
        while (it2.hasNext()) {
            f += Math.abs(((FinderPattern) it2.next()).estimatedModuleSize - f3);
        }
        return f <= f2 * 0.05f;
    }
}
