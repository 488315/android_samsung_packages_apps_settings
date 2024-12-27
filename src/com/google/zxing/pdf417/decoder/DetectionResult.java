package com.google.zxing.pdf417.decoder;

import com.google.zxing.ResultPoint;

import java.util.Formatter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class DetectionResult {
    public final int barcodeColumnCount;
    public final BarcodeMetadata barcodeMetadata;
    public BoundingBox boundingBox;
    public final DetectionResultColumn[] detectionResultColumns;

    public DetectionResult(BarcodeMetadata barcodeMetadata, BoundingBox boundingBox) {
        this.barcodeMetadata = barcodeMetadata;
        int i = barcodeMetadata.columnCount;
        this.barcodeColumnCount = i;
        this.boundingBox = boundingBox;
        this.detectionResultColumns = new DetectionResultColumn[i + 2];
    }

    public final void adjustIndicatorColumnRowNumbers(DetectionResultColumn detectionResultColumn) {
        if (detectionResultColumn != null) {
            DetectionResultRowIndicatorColumn detectionResultRowIndicatorColumn =
                    (DetectionResultRowIndicatorColumn) detectionResultColumn;
            Codeword[] codewordArr = detectionResultRowIndicatorColumn.codewords;
            for (Codeword codeword : codewordArr) {
                if (codeword != null) {
                    codeword.setRowNumberAsRowIndicatorColumn();
                }
            }
            BarcodeMetadata barcodeMetadata = this.barcodeMetadata;
            detectionResultRowIndicatorColumn.removeIncorrectCodewords(
                    codewordArr, barcodeMetadata);
            BoundingBox boundingBox = detectionResultRowIndicatorColumn.boundingBox;
            boolean z = detectionResultRowIndicatorColumn.isLeft;
            ResultPoint resultPoint = z ? boundingBox.topLeft : boundingBox.topRight;
            ResultPoint resultPoint2 = z ? boundingBox.bottomLeft : boundingBox.bottomRight;
            int imageRowToCodewordIndex =
                    detectionResultRowIndicatorColumn.imageRowToCodewordIndex((int) resultPoint.y);
            int imageRowToCodewordIndex2 =
                    detectionResultRowIndicatorColumn.imageRowToCodewordIndex((int) resultPoint2.y);
            int i = -1;
            int i2 = 1;
            int i3 = 0;
            while (imageRowToCodewordIndex < imageRowToCodewordIndex2) {
                Codeword codeword2 = codewordArr[imageRowToCodewordIndex];
                if (codeword2 != null) {
                    int i4 = codeword2.rowNumber;
                    int i5 = i4 - i;
                    if (i5 == 0) {
                        i3++;
                    } else {
                        if (i5 == 1) {
                            i2 = Math.max(i2, i3);
                            i = codeword2.rowNumber;
                        } else if (i5 < 0
                                || i4 >= barcodeMetadata.rowCount
                                || i5 > imageRowToCodewordIndex) {
                            codewordArr[imageRowToCodewordIndex] = null;
                        } else {
                            if (i2 > 2) {
                                i5 *= i2 - 2;
                            }
                            boolean z2 = i5 >= imageRowToCodewordIndex;
                            for (int i6 = 1; i6 <= i5 && !z2; i6++) {
                                z2 = codewordArr[imageRowToCodewordIndex - i6] != null;
                            }
                            if (z2) {
                                codewordArr[imageRowToCodewordIndex] = null;
                            } else {
                                i = codeword2.rowNumber;
                            }
                        }
                        i3 = 1;
                    }
                }
                imageRowToCodewordIndex++;
            }
        }
    }

    public final String toString() {
        DetectionResultColumn[] detectionResultColumnArr = this.detectionResultColumns;
        DetectionResultColumn detectionResultColumn = detectionResultColumnArr[0];
        int i = this.barcodeColumnCount;
        if (detectionResultColumn == null) {
            detectionResultColumn = detectionResultColumnArr[i + 1];
        }
        Formatter formatter = new Formatter();
        for (int i2 = 0; i2 < detectionResultColumn.codewords.length; i2++) {
            try {
                formatter.format("CW %3d:", Integer.valueOf(i2));
                for (int i3 = 0; i3 < i + 2; i3++) {
                    DetectionResultColumn detectionResultColumn2 = detectionResultColumnArr[i3];
                    if (detectionResultColumn2 == null) {
                        formatter.format("    |   ", new Object[0]);
                    } else {
                        Codeword codeword = detectionResultColumn2.codewords[i2];
                        if (codeword == null) {
                            formatter.format("    |   ", new Object[0]);
                        } else {
                            formatter.format(
                                    " %3d|%3d",
                                    Integer.valueOf(codeword.rowNumber),
                                    Integer.valueOf(codeword.value));
                        }
                    }
                }
                formatter.format("%n", new Object[0]);
            } catch (Throwable th) {
                try {
                    formatter.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        String formatter2 = formatter.toString();
        formatter.close();
        return formatter2;
    }
}
