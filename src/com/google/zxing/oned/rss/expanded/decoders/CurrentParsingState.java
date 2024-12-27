package com.google.zxing.oned.rss.expanded.decoders;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class CurrentParsingState {
    public State encoding;
    public int position;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    final class State {
        public static final /* synthetic */ State[] $VALUES;
        public static final State ALPHA;
        public static final State ISO_IEC_646;
        public static final State NUMERIC;

        static {
            State state = new State("NUMERIC", 0);
            NUMERIC = state;
            State state2 = new State("ALPHA", 1);
            ALPHA = state2;
            State state3 = new State("ISO_IEC_646", 2);
            ISO_IEC_646 = state3;
            $VALUES = new State[] {state, state2, state3};
        }

        public static State valueOf(String str) {
            return (State) Enum.valueOf(State.class, str);
        }

        public static State[] values() {
            return (State[]) $VALUES.clone();
        }
    }
}
