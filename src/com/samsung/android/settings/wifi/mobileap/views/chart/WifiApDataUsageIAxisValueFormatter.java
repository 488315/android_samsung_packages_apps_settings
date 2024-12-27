package com.samsung.android.settings.wifi.mobileap.views.chart;

import android.content.Context;

import com.android.settings.R;

import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiApDataUsageIAxisValueFormatter implements IAxisValueFormatter {
    public final Context mContext;
    public String mDataUsageUnit = "MB";

    public WifiApDataUsageIAxisValueFormatter(Context context) {
        this.mContext = context;
    }

    @Override // com.github.mikephil.charting.formatter.IAxisValueFormatter
    public final String getFormattedValue(float f) {
        String format = String.format("%d", Integer.valueOf(Math.round(f)));
        return "GB".equals(this.mDataUsageUnit)
                ? String.format(this.mContext.getString(R.string.wifi_ap_value_in_gb), format)
                : String.format(this.mContext.getString(R.string.wifi_ap_value_in_mb), format);
    }
}
