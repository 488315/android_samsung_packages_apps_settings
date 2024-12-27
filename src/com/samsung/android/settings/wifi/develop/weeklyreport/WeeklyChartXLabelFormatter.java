package com.samsung.android.settings.wifi.develop.weeklyreport;

import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WeeklyChartXLabelFormatter implements IAxisValueFormatter {
    public ArrayList mLabels;

    @Override // com.github.mikephil.charting.formatter.IAxisValueFormatter
    public final String getFormattedValue(float f) {
        return (String) this.mLabels.get((int) f);
    }
}
