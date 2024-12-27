package com.github.mikephil.charting.charts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;

import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.renderer.BarLineScatterCandleBubbleRenderer;
import com.github.mikephil.charting.renderer.LineChartRenderer;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class LineChart extends BarLineChartBase implements LineDataProvider {
    public LineChart(Context context) {
        super(context);
    }

    @Override // com.github.mikephil.charting.charts.BarLineChartBase
    public final void init() {
        super.init();
        LineChartRenderer lineChartRenderer =
                new LineChartRenderer(this.mAnimator, this.mViewPortHandler);
        lineChartRenderer.mHighlightLinePath = new Path();
        lineChartRenderer.mBitmapConfig = Bitmap.Config.ARGB_8888;
        lineChartRenderer.cubicPath = new Path();
        lineChartRenderer.cubicFillPath = new Path();
        lineChartRenderer.mLineBuffer = new float[4];
        lineChartRenderer.mGenerateFilledPathBuffer = new Path();
        lineChartRenderer.mImageCaches = new HashMap();
        lineChartRenderer.mCirclesBuffer = new float[2];
        lineChartRenderer.mChart = this;
        Paint paint = new Paint(1);
        lineChartRenderer.mCirclePaintInner = paint;
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(-1);
        this.mRenderer = lineChartRenderer;
    }

    @Override // com.github.mikephil.charting.charts.BarLineChartBase, android.view.ViewGroup,
              // android.view.View
    public final void onDetachedFromWindow() {
        BarLineScatterCandleBubbleRenderer barLineScatterCandleBubbleRenderer = this.mRenderer;
        if (barLineScatterCandleBubbleRenderer != null
                && (barLineScatterCandleBubbleRenderer instanceof LineChartRenderer)) {
            LineChartRenderer lineChartRenderer =
                    (LineChartRenderer) barLineScatterCandleBubbleRenderer;
            Canvas canvas = lineChartRenderer.mBitmapCanvas;
            if (canvas != null) {
                canvas.setBitmap(null);
                lineChartRenderer.mBitmapCanvas = null;
            }
            WeakReference weakReference = lineChartRenderer.mDrawBitmap;
            if (weakReference != null) {
                Bitmap bitmap = (Bitmap) weakReference.get();
                if (bitmap != null) {
                    bitmap.recycle();
                }
                lineChartRenderer.mDrawBitmap.clear();
                lineChartRenderer.mDrawBitmap = null;
            }
        }
        super.onDetachedFromWindow();
    }

    public LineChart(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public LineChart(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
