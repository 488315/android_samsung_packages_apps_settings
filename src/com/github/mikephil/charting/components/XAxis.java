package com.github.mikephil.charting.components;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class XAxis extends AxisBase {
    public boolean mAvoidFirstLastClipping;
    public int mLabelRotatedHeight;
    public XAxisPosition mPosition;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class XAxisPosition {
        public static final /* synthetic */ XAxisPosition[] $VALUES;
        public static final XAxisPosition BOTH_SIDED;
        public static final XAxisPosition BOTTOM;
        public static final XAxisPosition BOTTOM_INSIDE;
        public static final XAxisPosition TOP;
        public static final XAxisPosition TOP_INSIDE;

        static {
            XAxisPosition xAxisPosition = new XAxisPosition("TOP", 0);
            TOP = xAxisPosition;
            XAxisPosition xAxisPosition2 = new XAxisPosition("BOTTOM", 1);
            BOTTOM = xAxisPosition2;
            XAxisPosition xAxisPosition3 = new XAxisPosition("BOTH_SIDED", 2);
            BOTH_SIDED = xAxisPosition3;
            XAxisPosition xAxisPosition4 = new XAxisPosition("TOP_INSIDE", 3);
            TOP_INSIDE = xAxisPosition4;
            XAxisPosition xAxisPosition5 = new XAxisPosition("BOTTOM_INSIDE", 4);
            BOTTOM_INSIDE = xAxisPosition5;
            $VALUES =
                    new XAxisPosition[] {
                        xAxisPosition,
                        xAxisPosition2,
                        xAxisPosition3,
                        xAxisPosition4,
                        xAxisPosition5
                    };
        }

        public static XAxisPosition valueOf(String str) {
            return (XAxisPosition) Enum.valueOf(XAxisPosition.class, str);
        }

        public static XAxisPosition[] values() {
            return (XAxisPosition[]) $VALUES.clone();
        }
    }
}
