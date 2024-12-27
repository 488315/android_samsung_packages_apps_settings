package com.github.mikephil.charting.utils;


/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class MPPointD extends ObjectPool.Poolable {
    public static final ObjectPool pool;
    public double x = 0.0d;
    public double y = 0.0d;

    static {
        ObjectPool create = ObjectPool.create(64, new MPPointD());
        pool = create;
        create.replenishPercentage = 0.5f;
    }

    public static MPPointD getInstance(double d, double d2) {
        MPPointD mPPointD = (MPPointD) pool.get();
        mPPointD.x = d;
        mPPointD.y = d2;
        return mPPointD;
    }

    public static void recycleInstance(MPPointD mPPointD) {
        pool.recycle(mPPointD);
    }

    @Override // com.github.mikephil.charting.utils.ObjectPool.Poolable
    public final ObjectPool.Poolable instantiate() {
        return new MPPointD();
    }

    public final String toString() {
        return "MPPointD, x: " + this.x + ", y: " + this.y;
    }
}
