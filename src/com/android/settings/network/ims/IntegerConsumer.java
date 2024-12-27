package com.android.settings.network.ims;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
class IntegerConsumer extends Semaphore implements Consumer<Integer> {
    private volatile AtomicInteger mValue;

    public IntegerConsumer() {
        super(0);
        this.mValue = new AtomicInteger();
    }

    @Override // java.util.function.Consumer
    public final void accept(Integer num) {
        Integer num2 = num;
        if (num2 != null) {
            this.mValue.set(num2.intValue());
        }
        release();
    }

    public final int get() {
        tryAcquire(2000L, TimeUnit.MILLISECONDS);
        return this.mValue.get();
    }
}
