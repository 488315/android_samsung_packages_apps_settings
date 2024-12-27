package com.google.zxing.pdf417.decoder;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class BoundingBox {
    public final ResultPoint bottomLeft;
    public final ResultPoint bottomRight;
    public final BitMatrix image;
    public final int maxX;
    public final int maxY;
    public final int minX;
    public final int minY;
    public final ResultPoint topLeft;
    public final ResultPoint topRight;

    public BoundingBox(
            BitMatrix bitMatrix,
            ResultPoint resultPoint,
            ResultPoint resultPoint2,
            ResultPoint resultPoint3,
            ResultPoint resultPoint4) {
        boolean z = resultPoint == null || resultPoint2 == null;
        boolean z2 = resultPoint3 == null || resultPoint4 == null;
        if (z && z2) {
            throw NotFoundException.getNotFoundInstance();
        }
        if (z) {
            resultPoint = new ResultPoint(0.0f, resultPoint3.y);
            resultPoint2 = new ResultPoint(0.0f, resultPoint4.y);
        } else if (z2) {
            int i = bitMatrix.width;
            resultPoint3 = new ResultPoint(i - 1, resultPoint.y);
            resultPoint4 = new ResultPoint(i - 1, resultPoint2.y);
        }
        this.image = bitMatrix;
        this.topLeft = resultPoint;
        this.bottomLeft = resultPoint2;
        this.topRight = resultPoint3;
        this.bottomRight = resultPoint4;
        this.minX = (int) Math.min(resultPoint.x, resultPoint2.x);
        this.maxX = (int) Math.max(resultPoint3.x, resultPoint4.x);
        this.minY = (int) Math.min(resultPoint.y, resultPoint3.y);
        this.maxY = (int) Math.max(resultPoint2.y, resultPoint4.y);
    }

    public BoundingBox(BoundingBox boundingBox) {
        this.image = boundingBox.image;
        this.topLeft = boundingBox.topLeft;
        this.bottomLeft = boundingBox.bottomLeft;
        this.topRight = boundingBox.topRight;
        this.bottomRight = boundingBox.bottomRight;
        this.minX = boundingBox.minX;
        this.maxX = boundingBox.maxX;
        this.minY = boundingBox.minY;
        this.maxY = boundingBox.maxY;
    }
}
