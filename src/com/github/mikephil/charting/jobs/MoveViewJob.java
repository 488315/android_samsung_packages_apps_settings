package com.github.mikephil.charting.jobs;

import android.graphics.Matrix;
import android.graphics.RectF;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.utils.ObjectPool;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class MoveViewJob extends ViewPortJob {
    public static final ObjectPool pool;

    static {
        ObjectPool create = ObjectPool.create(2, new MoveViewJob(null, 0.0f, null, null));
        pool = create;
        create.replenishPercentage = 0.5f;
    }

    public MoveViewJob(
            ViewPortHandler viewPortHandler,
            float f,
            Transformer transformer,
            LineChart lineChart) {
        this.pts = new float[2];
        this.mViewPortHandler = viewPortHandler;
        this.xValue = f;
        this.yValue = 0.0f;
        this.mTrans = transformer;
        this.view = lineChart;
    }

    @Override // com.github.mikephil.charting.utils.ObjectPool.Poolable
    public final ObjectPool.Poolable instantiate() {
        return new MoveViewJob(
                this.mViewPortHandler, this.xValue, this.mTrans, (LineChart) this.view);
    }

    @Override // java.lang.Runnable
    public final void run() {
        float[] fArr = this.pts;
        fArr[0] = this.xValue;
        fArr[1] = this.yValue;
        this.mTrans.pointValuesToPixel(fArr);
        ViewPortHandler viewPortHandler = this.mViewPortHandler;
        float[] fArr2 = this.pts;
        LineChart lineChart = (LineChart) this.view;
        Matrix matrix = viewPortHandler.mCenterViewPortMatrixBuffer;
        matrix.reset();
        matrix.set(viewPortHandler.mMatrixTouch);
        float f = fArr2[0];
        RectF rectF = viewPortHandler.mContentRect;
        matrix.postTranslate(-(f - rectF.left), -(fArr2[1] - rectF.top));
        viewPortHandler.refresh(matrix, lineChart, true);
        pool.recycle(this);
    }
}
