package com.github.mikephil.charting.formatter;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.Collection;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class IndexAxisValueFormatter implements IAxisValueFormatter {
    public final int mValueCount;
    public final String[] mValues;

    public IndexAxisValueFormatter(Collection collection) {
        this.mValues = new String[0];
        this.mValueCount = 0;
        ArrayList arrayList = (ArrayList) collection;
        String[] strArr = (String[]) arrayList.toArray(new String[arrayList.size()]);
        strArr = strArr == null ? new String[0] : strArr;
        this.mValues = strArr;
        this.mValueCount = strArr.length;
    }

    @Override // com.github.mikephil.charting.formatter.IAxisValueFormatter
    public final String getFormattedValue(float f) {
        int round = Math.round(f);
        return (round < 0 || round >= this.mValueCount || round != ((int) f))
                ? ApnSettings.MVNO_NONE
                : this.mValues[round];
    }
}
