package com.github.mikephil.charting.formatter;

import com.sec.ims.configuration.DATA;

import java.text.DecimalFormat;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class DefaultValueFormatter {
    public DecimalFormat mFormat;

    public DefaultValueFormatter(int i) {
        setup(i);
    }

    public final void setup(int i) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < i; i2++) {
            if (i2 == 0) {
                stringBuffer.append(".");
            }
            stringBuffer.append(DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
        }
        this.mFormat = new DecimalFormat("###,###,###,##0" + stringBuffer.toString());
    }
}
