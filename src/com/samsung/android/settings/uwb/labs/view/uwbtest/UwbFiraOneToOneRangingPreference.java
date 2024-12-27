package com.samsung.android.settings.uwb.labs.view.uwbtest;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class UwbFiraOneToOneRangingPreference extends Preference {
    public int distance;
    public final Context mContext;
    public LineChart mDistChart;
    public LineData mDistLineData;
    public LineDataSet mDistLineDataSet;
    public ArrayList mDistVal;
    public int mErrorCnt;
    public int mRangingCnt;
    public int mSuccessCnt;
    public int mTime;
    public PreferenceViewHolder mViewHolder;
    public int nlos;
    public int rssi;
    public int status;

    public UwbFiraOneToOneRangingPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        Log.i("UwbFiraOneToOneRangingPreference", "CREATE: UwbFiraOneToOneRangingPreference");
        setLayoutResource(R.layout.sec_uwb_labs_fira_ranging_preference2);
    }

    public final Drawable getPointDrawable() {
        GradientDrawable gradientDrawable =
                (GradientDrawable) this.mContext.getDrawable(R.drawable.sec_uwb_labs_open_point);
        gradientDrawable.setColor(
                this.mContext.getColor(R.color.sec_uwb_labs_instruction_text_color));
        return gradientDrawable;
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        this.mViewHolder = preferenceViewHolder;
        ((ImageView) preferenceViewHolder.findViewById(R.id.distance_imageview))
                .setImageDrawable(getPointDrawable());
        ((ImageView) this.mViewHolder.findViewById(R.id.rssi_imageview))
                .setImageDrawable(getPointDrawable());
        ((ImageView) this.mViewHolder.findViewById(R.id.rangingCount_imageview))
                .setImageDrawable(getPointDrawable());
        ((ImageView) this.mViewHolder.findViewById(R.id.successCount_imageview))
                .setImageDrawable(getPointDrawable());
        ((ImageView) this.mViewHolder.findViewById(R.id.rangingStatus_imageview))
                .setImageDrawable(getPointDrawable());
        this.mTime = 0;
        LineChart lineChart = (LineChart) this.mViewHolder.findViewById(R.id.distanceChart);
        this.mDistChart = lineChart;
        lineChart.mAutoScaleMinMaxEnabled = true;
        ArrayList arrayList = new ArrayList();
        this.mDistVal = arrayList;
        LineDataSet lineDataSet = new LineDataSet("Distance", arrayList);
        this.mDistLineDataSet = lineDataSet;
        lineDataSet.mDrawValues = false;
        lineDataSet.mDrawCircles = false;
        lineDataSet.mAxisDependency = YAxis.AxisDependency.LEFT;
        lineDataSet.setColor(this.mContext.getColor(R.color.red));
        this.mDistLineData = new LineData(this.mDistLineDataSet);
        XAxis xAxis = this.mDistChart.mXAxis;
        xAxis.mPosition = XAxis.XAxisPosition.BOTTOM;
        xAxis.mTextColor = this.mContext.getColor(R.color.black);
        YAxis yAxis = this.mDistChart.mAxisRight;
        yAxis.mDrawLabels = false;
        yAxis.mDrawAxisLine = false;
        yAxis.mDrawGridLines = false;
        this.mTime = 0;
        this.mDistVal.clear();
        this.mDistChart.invalidate();
        LineChart lineChart2 = this.mDistChart;
        lineChart2.mData = null;
        lineChart2.mOffsetsCalculated = false;
        lineChart2.mIndicesToHighlight = null;
        lineChart2.mChartTouchListener.mLastHighlighted = null;
        lineChart2.invalidate();
        this.mRangingCnt = 0;
        this.mErrorCnt = 0;
        this.mSuccessCnt = 0;
    }
}
