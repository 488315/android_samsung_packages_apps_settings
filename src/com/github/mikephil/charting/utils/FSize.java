package com.github.mikephil.charting.utils;


/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class FSize extends ObjectPool.Poolable {
    public static final ObjectPool pool;
    public float width = 0.0f;
    public float height = 0.0f;

    static {
        ObjectPool create = ObjectPool.create(256, new FSize());
        pool = create;
        create.replenishPercentage = 0.5f;
    }

    public final boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FSize)) {
            return false;
        }
        FSize fSize = (FSize) obj;
        return this.width == fSize.width && this.height == fSize.height;
    }

    public final int hashCode() {
        return Float.floatToIntBits(this.height) ^ Float.floatToIntBits(this.width);
    }

    @Override // com.github.mikephil.charting.utils.ObjectPool.Poolable
    public final ObjectPool.Poolable instantiate() {
        return new FSize();
    }

    public final String toString() {
        return this.width + "x" + this.height;
    }
}
