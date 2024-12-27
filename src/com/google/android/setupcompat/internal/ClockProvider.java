package com.google.android.setupcompat.internal;


/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class ClockProvider {
    public static final ClockProvider$$ExternalSyntheticLambda0 SYSTEM_TICKER;
    public static Ticker ticker;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Supplier<T> {
        Object get();
    }

    static {
        ClockProvider$$ExternalSyntheticLambda0 clockProvider$$ExternalSyntheticLambda0 =
                new ClockProvider$$ExternalSyntheticLambda0();
        SYSTEM_TICKER = clockProvider$$ExternalSyntheticLambda0;
        ticker = clockProvider$$ExternalSyntheticLambda0;
    }

    public static void resetInstance() {
        ticker = SYSTEM_TICKER;
    }

    public static void setInstance(final Supplier<Long> supplier) {
        ticker =
                new Ticker() { // from class:
                               // com.google.android.setupcompat.internal.ClockProvider$$ExternalSyntheticLambda1
                    @Override // com.google.android.setupcompat.internal.Ticker
                    public final long read() {
                        return ((Long) ClockProvider.Supplier.this.get()).longValue();
                    }
                };
    }
}
