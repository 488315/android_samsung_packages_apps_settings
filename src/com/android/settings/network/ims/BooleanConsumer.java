package com.android.settings.network.ims;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
class BooleanConsumer extends Semaphore implements Consumer<Boolean> {
    private volatile AtomicBoolean mValue;

    public BooleanConsumer() {
        super(0);
        this.mValue = new AtomicBoolean();
    }

    @Override // java.util.function.Consumer
    public final void accept(Boolean bool) {
        Boolean bool2 = bool;
        if (bool2 != null) {
            this.mValue.set(bool2.booleanValue());
        }
        release();
    }

    public final boolean get() {
        tryAcquire(2000L, TimeUnit.MILLISECONDS);
        return this.mValue.get();
    }
}
