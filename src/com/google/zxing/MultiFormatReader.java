package com.google.zxing;

import com.google.zxing.aztec.AztecReader;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.datamatrix.DataMatrixReader;
import com.google.zxing.maxicode.MaxiCodeReader;
import com.google.zxing.oned.MultiFormatOneDReader;
import com.google.zxing.pdf417.PDF417Reader;
import com.google.zxing.qrcode.QRCodeReader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class MultiFormatReader implements Reader {
    public static final Reader[] EMPTY_READER_ARRAY = new Reader[0];
    public Map hints;
    public Reader[] readers;

    @Override // com.google.zxing.Reader
    public final Result decode(BinaryBitmap binaryBitmap, Map map) {
        setHints(map);
        return decodeInternal(binaryBitmap);
    }

    public final Result decodeInternal(BinaryBitmap binaryBitmap) {
        Reader[] readerArr = this.readers;
        if (readerArr != null) {
            for (Reader reader : readerArr) {
                if (Thread.currentThread().isInterrupted()) {
                    throw NotFoundException.getNotFoundInstance();
                }
                try {
                    return reader.decode(binaryBitmap, this.hints);
                } catch (ReaderException unused) {
                }
            }
            Map map = this.hints;
            if (map != null && map.containsKey(DecodeHintType.ALSO_INVERTED)) {
                BitMatrix blackMatrix = binaryBitmap.getBlackMatrix();
                int length = blackMatrix.bits.length;
                for (int i = 0; i < length; i++) {
                    int[] iArr = blackMatrix.bits;
                    iArr[i] = ~iArr[i];
                }
                for (Reader reader2 : this.readers) {
                    if (Thread.currentThread().isInterrupted()) {
                        throw NotFoundException.getNotFoundInstance();
                    }
                    try {
                        return reader2.decode(binaryBitmap, this.hints);
                    } catch (ReaderException unused2) {
                    }
                }
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    @Override // com.google.zxing.Reader
    public final void reset() {
        Reader[] readerArr = this.readers;
        if (readerArr != null) {
            for (Reader reader : readerArr) {
                reader.reset();
            }
        }
    }

    public final void setHints(Map map) {
        this.hints = map;
        boolean z = map != null && map.containsKey(DecodeHintType.TRY_HARDER);
        Collection collection =
                map == null ? null : (Collection) map.get(DecodeHintType.POSSIBLE_FORMATS);
        ArrayList arrayList = new ArrayList();
        if (collection != null) {
            boolean z2 =
                    collection.contains(BarcodeFormat.UPC_A)
                            || collection.contains(BarcodeFormat.UPC_E)
                            || collection.contains(BarcodeFormat.EAN_13)
                            || collection.contains(BarcodeFormat.EAN_8)
                            || collection.contains(BarcodeFormat.CODABAR)
                            || collection.contains(BarcodeFormat.CODE_39)
                            || collection.contains(BarcodeFormat.CODE_93)
                            || collection.contains(BarcodeFormat.CODE_128)
                            || collection.contains(BarcodeFormat.ITF)
                            || collection.contains(BarcodeFormat.RSS_14)
                            || collection.contains(BarcodeFormat.RSS_EXPANDED);
            if (z2 && !z) {
                arrayList.add(new MultiFormatOneDReader(map));
            }
            if (collection.contains(BarcodeFormat.QR_CODE)) {
                arrayList.add(new QRCodeReader());
            }
            if (collection.contains(BarcodeFormat.DATA_MATRIX)) {
                arrayList.add(new DataMatrixReader());
            }
            if (collection.contains(BarcodeFormat.AZTEC)) {
                arrayList.add(new AztecReader());
            }
            if (collection.contains(BarcodeFormat.PDF_417)) {
                arrayList.add(new PDF417Reader());
            }
            if (collection.contains(BarcodeFormat.MAXICODE)) {
                arrayList.add(new MaxiCodeReader());
            }
            if (z2 && z) {
                arrayList.add(new MultiFormatOneDReader(map));
            }
        }
        if (arrayList.isEmpty()) {
            if (!z) {
                arrayList.add(new MultiFormatOneDReader(map));
            }
            arrayList.add(new QRCodeReader());
            arrayList.add(new DataMatrixReader());
            arrayList.add(new AztecReader());
            arrayList.add(new PDF417Reader());
            arrayList.add(new MaxiCodeReader());
            if (z) {
                arrayList.add(new MultiFormatOneDReader(map));
            }
        }
        this.readers = (Reader[]) arrayList.toArray(EMPTY_READER_ARRAY);
    }
}
