package com.google.common.base;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
final class AbstractIterator$State {
    public static final /* synthetic */ AbstractIterator$State[] $VALUES;
    public static final AbstractIterator$State DONE;
    public static final AbstractIterator$State FAILED;
    public static final AbstractIterator$State NOT_READY;
    public static final AbstractIterator$State READY;

    static {
        AbstractIterator$State abstractIterator$State = new AbstractIterator$State("READY", 0);
        READY = abstractIterator$State;
        AbstractIterator$State abstractIterator$State2 = new AbstractIterator$State("NOT_READY", 1);
        NOT_READY = abstractIterator$State2;
        AbstractIterator$State abstractIterator$State3 = new AbstractIterator$State("DONE", 2);
        DONE = abstractIterator$State3;
        AbstractIterator$State abstractIterator$State4 = new AbstractIterator$State("FAILED", 3);
        FAILED = abstractIterator$State4;
        $VALUES =
                new AbstractIterator$State[] {
                    abstractIterator$State,
                    abstractIterator$State2,
                    abstractIterator$State3,
                    abstractIterator$State4
                };
    }

    public static AbstractIterator$State valueOf(String str) {
        return (AbstractIterator$State) Enum.valueOf(AbstractIterator$State.class, str);
    }

    public static AbstractIterator$State[] values() {
        return (AbstractIterator$State[]) $VALUES.clone();
    }
}
