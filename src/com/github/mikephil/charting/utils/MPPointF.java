package com.github.mikephil.charting.utils;


/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class MPPointF extends ObjectPool.Poolable {
    public static final ObjectPool pool;
    public float x;
    public float y;

    static {
        ObjectPool create = ObjectPool.create(32, new MPPointF(0.0f, 0.0f));
        pool = create;
        create.replenishPercentage = 0.5f;
    }

    public MPPointF(float f, float f2) {
        this.x = f;
        this.y = f2;
    }

    public static MPPointF getInstance(float f, float f2) {
        MPPointF mPPointF = (MPPointF) pool.get();
        mPPointF.x = f;
        mPPointF.y = f2;
        return mPPointF;
    }

    @Override // com.github.mikephil.charting.utils.ObjectPool.Poolable
    public final ObjectPool.Poolable instantiate() {
        return new MPPointF(0.0f, 0.0f);
    }
}
