package com.github.mikephil.charting.charts;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.highlight.BarHighlighter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.renderer.BarChartRenderer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class BarChart extends BarLineChartBase implements BarDataProvider {
    public boolean mDrawValueAboveBar;
    public boolean mHighlightFullBarEnabled;

    public BarChart(Context context) {
        super(context);
        this.mHighlightFullBarEnabled = false;
        this.mDrawValueAboveBar = true;
    }

    @Override // com.github.mikephil.charting.charts.BarLineChartBase
    public final void calcMinMax() {
        XAxis xAxis = this.mXAxis;
        BarData barData = (BarData) this.mData;
        xAxis.calculate(barData.mXMin, barData.mXMax);
        YAxis yAxis = this.mAxisLeft;
        BarData barData2 = (BarData) this.mData;
        YAxis.AxisDependency axisDependency = YAxis.AxisDependency.LEFT;
        yAxis.calculate(
                barData2.getYMin(axisDependency), ((BarData) this.mData).getYMax(axisDependency));
        YAxis yAxis2 = this.mAxisRight;
        BarData barData3 = (BarData) this.mData;
        YAxis.AxisDependency axisDependency2 = YAxis.AxisDependency.RIGHT;
        yAxis2.calculate(
                barData3.getYMin(axisDependency2), ((BarData) this.mData).getYMax(axisDependency2));
    }

    @Override // com.github.mikephil.charting.charts.BarLineChartBase
    public final Highlight getHighlightByTouchPoint(float f, float f2) {
        if (this.mData == null) {
            Log.e("MPAndroidChart", "Can't select by touch. No data set.");
            return null;
        }
        Highlight highlight = this.mHighlighter.getHighlight(f, f2);
        return (highlight == null || !this.mHighlightFullBarEnabled)
                ? highlight
                : new Highlight(
                        highlight.mX,
                        highlight.mY,
                        highlight.mXPx,
                        highlight.mYPx,
                        highlight.mDataSetIndex,
                        -1,
                        highlight.axis);
    }

    @Override // com.github.mikephil.charting.charts.BarLineChartBase
    public final void init() {
        super.init();
        this.mRenderer = new BarChartRenderer(this, this.mAnimator, this.mViewPortHandler);
        this.mHighlighter = new BarHighlighter(this);
        XAxis xAxis = this.mXAxis;
        xAxis.mSpaceMin = 0.5f;
        xAxis.mSpaceMax = 0.5f;
    }

    public BarChart(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mHighlightFullBarEnabled = false;
        this.mDrawValueAboveBar = true;
    }

    public BarChart(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mHighlightFullBarEnabled = false;
        this.mDrawValueAboveBar = true;
    }
}
