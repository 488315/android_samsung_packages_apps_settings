package com.github.mikephil.charting.utils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ObjectPool {
    public static int ids;
    public int desiredCapacity;
    public Poolable modelObject;
    public Object[] objects;
    public int objectsPointer;
    public int poolId;
    public float replenishPercentage;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class Poolable {
        public int currentOwnerId = -1;

        public abstract Poolable instantiate();
    }

    public static synchronized ObjectPool create(int i, Poolable poolable) {
        ObjectPool objectPool;
        synchronized (ObjectPool.class) {
            objectPool = new ObjectPool();
            if (i <= 0) {
                throw new IllegalArgumentException(
                        "Object Pool must be instantiated with a capacity greater than 0!");
            }
            objectPool.desiredCapacity = i;
            objectPool.objects = new Object[i];
            objectPool.objectsPointer = 0;
            objectPool.modelObject = poolable;
            objectPool.replenishPercentage = 1.0f;
            objectPool.refillPool();
            int i2 = ids;
            objectPool.poolId = i2;
            ids = i2 + 1;
        }
        return objectPool;
    }

    public final synchronized Poolable get() {
        Poolable poolable;
        try {
            if (this.objectsPointer == -1 && this.replenishPercentage > 0.0f) {
                refillPool();
            }
            Object[] objArr = this.objects;
            int i = this.objectsPointer;
            poolable = (Poolable) objArr[i];
            poolable.currentOwnerId = -1;
            this.objectsPointer = i - 1;
        } catch (Throwable th) {
            throw th;
        }
        return poolable;
    }

    public final synchronized void recycle(Poolable poolable) {
        try {
            int i = poolable.currentOwnerId;
            if (i != -1) {
                if (i == this.poolId) {
                    throw new IllegalArgumentException(
                            "The object passed is already stored in this pool!");
                }
                throw new IllegalArgumentException(
                        "The object to recycle already belongs to poolId "
                                + poolable.currentOwnerId
                                + ".  Object cannot belong to two different pool instances"
                                + " simultaneously!");
            }
            int i2 = this.objectsPointer + 1;
            this.objectsPointer = i2;
            if (i2 >= this.objects.length) {
                int i3 = this.desiredCapacity;
                int i4 = i3 * 2;
                this.desiredCapacity = i4;
                Object[] objArr = new Object[i4];
                for (int i5 = 0; i5 < i3; i5++) {
                    objArr[i5] = this.objects[i5];
                }
                this.objects = objArr;
            }
            poolable.currentOwnerId = this.poolId;
            this.objects[this.objectsPointer] = poolable;
        } catch (Throwable th) {
            throw th;
        }
    }

    public final void refillPool() {
        float f = this.replenishPercentage;
        int i = this.desiredCapacity;
        int i2 = (int) (i * f);
        if (i2 < 1) {
            i = 1;
        } else if (i2 <= i) {
            i = i2;
        }
        for (int i3 = 0; i3 < i; i3++) {
            this.objects[i3] = this.modelObject.instantiate();
        }
        this.objectsPointer = i - 1;
    }
}
