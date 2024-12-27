package com.github.mikephil.charting.jobs;

import android.view.View;

import com.github.mikephil.charting.utils.ObjectPool;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class ViewPortJob extends ObjectPool.Poolable implements Runnable {
    public Transformer mTrans;
    public ViewPortHandler mViewPortHandler;
    public float[] pts;
    public View view;
    public float xValue;
    public float yValue;
}
