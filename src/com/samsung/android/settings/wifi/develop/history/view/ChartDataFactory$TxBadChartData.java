package com.samsung.android.settings.wifi.develop.history.view;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ChartDataFactory$TxBadChartData extends ChartDataFactory$BaseChartData {
    public final /* synthetic */ int $r8$classId;
    public long mMaxTxBad;
    public long mMinTxBad;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ ChartDataFactory$TxBadChartData(int i, List list) {
        super(list);
        this.$r8$classId = i;
    }

    @Override // com.samsung.android.settings.wifi.develop.history.view.ChartDataFactory$BaseChartData
    public final CharSequence[] getValueAxisLabels() {
        switch (this.$r8$classId) {
            case 0:
                long j = this.mMaxTxBad;
                long j2 = this.mMinTxBad;
                return new CharSequence[] {
                    String.valueOf(j), String.valueOf((j + j2) / 2), String.valueOf(j2)
                };
            case 1:
                long j3 = this.mMaxTxBad;
                long j4 = this.mMinTxBad;
                return new CharSequence[] {
                    String.valueOf(j3), String.valueOf((j3 + j4) / 2), String.valueOf(j4)
                };
            case 2:
                long j5 = this.mMaxTxBad;
                long j6 = this.mMinTxBad;
                return new CharSequence[] {
                    String.valueOf(j5), String.valueOf((j5 + j6) / 2), String.valueOf(j6)
                };
            default:
                long j7 = this.mMaxTxBad;
                long j8 = this.mMinTxBad;
                return new CharSequence[] {
                    String.valueOf(j7), String.valueOf((j7 + j8) / 2), String.valueOf(j8)
                };
        }
    }

    @Override // com.samsung.android.settings.wifi.develop.history.view.ChartDataFactory$BaseChartData
    public final long getValueAxisOffset() {
        switch (this.$r8$classId) {
        }
        return 0L;
    }
}
