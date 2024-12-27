package com.samsung.android.settings.wifi.mobileap.clients.report.chart;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.samsung.android.settings.wifi.mobileap.datamodels.chart.WifiApDailyStackedProgressBarEntryConfig;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApDateUtils;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class DataChartMarker extends AbsChartMarker {
    public List mDailyStackedProgressBarEntryConfigList;
    public TextView mMarkerXLabelText;
    public TextView mMarkerYValueText;

    @Override // com.github.mikephil.charting.components.IMarker
    public final void refreshContent(Entry entry, Highlight highlight) {
        if (highlight.mStackIndex == 0) {
            this.mDrawMarker = false;
            return;
        }
        this.mDrawMarker = entry.getY() > 0.0f;
        WifiApDailyStackedProgressBarEntryConfig wifiApDailyStackedProgressBarEntryConfig =
                (WifiApDailyStackedProgressBarEntryConfig)
                        this.mDailyStackedProgressBarEntryConfigList.get((int) entry.x);
        Context context = this.mContext;
        long j = wifiApDailyStackedProgressBarEntryConfig.mDateInMillis;
        Integer num = WifiApDateUtils.NUMBER_OF_DAYS_IN_WEEK;
        String formatDateTime = DateUtils.formatDateTime(context, j, 98322);
        String usageValueInLocaleString =
                wifiApDailyStackedProgressBarEntryConfig.mTotalUsed.getUsageValueInLocaleString(
                        this.mContext);
        this.mMarkerXLabelText.setText(formatDateTime);
        this.mMarkerYValueText.setText(usageValueInLocaleString);
        measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
    }
}
