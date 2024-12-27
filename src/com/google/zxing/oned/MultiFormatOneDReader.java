package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.rss.RSS14Reader;
import com.google.zxing.oned.rss.expanded.RSSExpandedReader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class MultiFormatOneDReader extends OneDReader {
    public static final OneDReader[] EMPTY_ONED_ARRAY = new OneDReader[0];
    public final OneDReader[] readers;

    public MultiFormatOneDReader(Map map) {
        Collection collection =
                map == null ? null : (Collection) map.get(DecodeHintType.POSSIBLE_FORMATS);
        boolean z =
                (map == null || map.get(DecodeHintType.ASSUME_CODE_39_CHECK_DIGIT) == null)
                        ? false
                        : true;
        ArrayList arrayList = new ArrayList();
        if (collection != null) {
            if (collection.contains(BarcodeFormat.EAN_13)
                    || collection.contains(BarcodeFormat.UPC_A)
                    || collection.contains(BarcodeFormat.EAN_8)
                    || collection.contains(BarcodeFormat.UPC_E)) {
                arrayList.add(new MultiFormatUPCEANReader(map));
            }
            if (collection.contains(BarcodeFormat.CODE_39)) {
                arrayList.add(new Code39Reader(z));
            }
            if (collection.contains(BarcodeFormat.CODE_93)) {
                arrayList.add(new Code93Reader());
            }
            if (collection.contains(BarcodeFormat.CODE_128)) {
                arrayList.add(new Code128Reader());
            }
            if (collection.contains(BarcodeFormat.ITF)) {
                arrayList.add(new ITFReader());
            }
            if (collection.contains(BarcodeFormat.CODABAR)) {
                arrayList.add(new CodaBarReader());
            }
            if (collection.contains(BarcodeFormat.RSS_14)) {
                arrayList.add(new RSS14Reader());
            }
            if (collection.contains(BarcodeFormat.RSS_EXPANDED)) {
                arrayList.add(new RSSExpandedReader());
            }
        }
        if (arrayList.isEmpty()) {
            arrayList.add(new MultiFormatUPCEANReader(map));
            arrayList.add(new Code39Reader(false));
            arrayList.add(new CodaBarReader());
            arrayList.add(new Code93Reader());
            arrayList.add(new Code128Reader());
            arrayList.add(new ITFReader());
            arrayList.add(new RSS14Reader());
            arrayList.add(new RSSExpandedReader());
        }
        this.readers = (OneDReader[]) arrayList.toArray(EMPTY_ONED_ARRAY);
    }

    @Override // com.google.zxing.oned.OneDReader
    public final Result decodeRow(int i, BitArray bitArray, Map map) {
        OneDReader[] oneDReaderArr = this.readers;
        for (int i2 = 0; i2 < oneDReaderArr.length; i2++) {
            try {
                return oneDReaderArr[i2].decodeRow(i, bitArray, map);
            } catch (ReaderException unused) {
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    @Override // com.google.zxing.oned.OneDReader, com.google.zxing.Reader
    public final void reset() {
        for (OneDReader oneDReader : this.readers) {
            oneDReader.reset();
        }
    }
}
