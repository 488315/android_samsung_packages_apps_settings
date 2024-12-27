package com.samsung.android.settings.wifi.develop.history.view;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ChartDataFactory$RssiChartData extends ChartDataFactory$BaseChartData {
    public int mMaxRssi;
    public int mMinRssi;

    @Override // com.samsung.android.settings.wifi.develop.history.view.ChartDataFactory$BaseChartData
    public final CharSequence[] getValueAxisLabels() {
        int i = this.mMaxRssi;
        int i2 = this.mMinRssi;
        return new CharSequence[] {
            String.valueOf(i), String.valueOf((i + i2) / 2), String.valueOf(i2)
        };
    }

    @Override // com.samsung.android.settings.wifi.develop.history.view.ChartDataFactory$BaseChartData
    public final long getValueAxisOffset() {
        return -Math.abs(this.mMinRssi);
    }

    @Override // com.samsung.android.settings.wifi.develop.history.view.ChartDataFactory$BaseChartData
    public final boolean showValueLabel() {
        return true;
    }
}
