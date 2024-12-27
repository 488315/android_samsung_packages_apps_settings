package com.google.zxing;

import com.google.zxing.aztec.AztecWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.datamatrix.DataMatrixWriter;
import com.google.zxing.oned.CodaBarWriter;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.oned.Code39Writer;
import com.google.zxing.oned.Code93Writer;
import com.google.zxing.oned.EAN13Writer;
import com.google.zxing.oned.EAN8Writer;
import com.google.zxing.oned.ITFWriter;
import com.google.zxing.oned.UPCAWriter;
import com.google.zxing.oned.UPCEWriter;
import com.google.zxing.pdf417.PDF417Writer;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class MultiFormatWriter implements Writer {
    @Override // com.google.zxing.Writer
    public final BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map map) {
        Writer aztecWriter;
        switch (barcodeFormat.ordinal()) {
            case 0:
                aztecWriter = new AztecWriter();
                break;
            case 1:
                aztecWriter = new CodaBarWriter();
                break;
            case 2:
                aztecWriter = new Code39Writer();
                break;
            case 3:
                aztecWriter = new Code93Writer();
                break;
            case 4:
                aztecWriter = new Code128Writer();
                break;
            case 5:
                aztecWriter = new DataMatrixWriter();
                break;
            case 6:
                aztecWriter = new EAN8Writer();
                break;
            case 7:
                aztecWriter = new EAN13Writer();
                break;
            case 8:
                aztecWriter = new ITFWriter();
                break;
            case 9:
            case 12:
            case 13:
            default:
                throw new IllegalArgumentException(
                        "No encoder available for format " + barcodeFormat);
            case 10:
                aztecWriter = new PDF417Writer();
                break;
            case 11:
                aztecWriter = new QRCodeWriter();
                break;
            case 14:
                aztecWriter = new UPCAWriter();
                break;
            case 15:
                aztecWriter = new UPCEWriter();
                break;
        }
        return aztecWriter.encode(str, barcodeFormat, i, i2, map);
    }
}
