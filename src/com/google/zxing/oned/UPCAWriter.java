package com.google.zxing.oned;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.common.BitMatrix;
import com.sec.ims.configuration.DATA;

import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class UPCAWriter implements Writer {
    public final EAN13Writer subWriter = new EAN13Writer();

    @Override // com.google.zxing.Writer
    public final BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map map) {
        if (barcodeFormat == BarcodeFormat.UPC_A) {
            return this.subWriter.encode(
                    AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                            DATA.DM_FIELD_INDEX.PCSCF_DOMAIN, str),
                    BarcodeFormat.EAN_13,
                    i,
                    i2,
                    map);
        }
        throw new IllegalArgumentException("Can only encode UPC-A, but got " + barcodeFormat);
    }
}
