package com.samsung.android.settings.wifi.mobileap.clients.report.chart;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.settings.R;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.utils.MPPointF;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class AbsChartMarker extends RelativeLayout implements IMarker {
    public final CategoryViewConstants$CategoryType mCategoryType;
    public final BarChart mChart;
    public final Context mContext;
    public boolean mDrawMarker;
    public final boolean mIsRTL;
    public final Paint mLinePaint;
    public final float mMarkerEndBound;
    public float mMarkerHeight;
    public final float mMarkerStartBound;
    public final int mMissingIndex;
    public MPPointF mOffset;

    public AbsChartMarker(
            Context context,
            CategoryViewConstants$CategoryType categoryViewConstants$CategoryType,
            BarChart barChart) {
        super(context);
        View inflate =
                LayoutInflater.from(getContext()).inflate(R.layout.sec_wifi_ap_common_marker, this);
        inflate.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
        inflate.measure(
                View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        inflate.layout(0, 0, inflate.getMeasuredWidth(), inflate.getMeasuredHeight());
        this.mIsRTL = ViewUtils.isRTL();
        this.mContext = context;
        this.mCategoryType = categoryViewConstants$CategoryType;
        this.mChart = barChart;
        Paint paint = new Paint();
        this.mLinePaint = paint;
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(
                context.getResources()
                        .getColor(R.color.dw_marker_bg_line_color, context.getTheme()));
        paint.setStrokeWidth(
                context.getResources().getDimension(R.dimen.app_detail_chart_marker_line_width));
        if (ViewUtils.isRTL()) {
            this.mMarkerStartBound =
                    context.getResources().getDimension(R.dimen.app_detail_chart_marker_offset_end);
            this.mMarkerEndBound =
                    context.getResources()
                            .getDimension(R.dimen.app_detail_chart_marker_offset_start);
        } else {
            this.mMarkerStartBound =
                    context.getResources()
                            .getDimension(R.dimen.app_detail_chart_marker_offset_start);
            this.mMarkerEndBound =
                    context.getResources().getDimension(R.dimen.app_detail_chart_marker_offset_end);
        }
        this.mMissingIndex = -1;
    }
}
