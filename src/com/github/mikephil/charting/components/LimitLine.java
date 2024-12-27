package com.github.mikephil.charting.components;

import android.graphics.DashPathEffect;
import android.graphics.Paint;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class LimitLine extends ComponentBase {
    public DashPathEffect mDashPathEffect;
    public String mLabel;
    public LimitLabelPosition mLabelPosition;
    public float mLimit;
    public int mLineColor;
    public float mLineWidth;
    public Paint.Style mTextStyle;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class LimitLabelPosition {
        public static final /* synthetic */ LimitLabelPosition[] $VALUES;
        public static final LimitLabelPosition LEFT_TOP;
        public static final LimitLabelPosition RIGHT_BOTTOM;
        public static final LimitLabelPosition RIGHT_TOP;

        static {
            LimitLabelPosition limitLabelPosition = new LimitLabelPosition("LEFT_TOP", 0);
            LEFT_TOP = limitLabelPosition;
            LimitLabelPosition limitLabelPosition2 = new LimitLabelPosition("LEFT_BOTTOM", 1);
            LimitLabelPosition limitLabelPosition3 = new LimitLabelPosition("RIGHT_TOP", 2);
            RIGHT_TOP = limitLabelPosition3;
            LimitLabelPosition limitLabelPosition4 = new LimitLabelPosition("RIGHT_BOTTOM", 3);
            RIGHT_BOTTOM = limitLabelPosition4;
            $VALUES =
                    new LimitLabelPosition[] {
                        limitLabelPosition,
                        limitLabelPosition2,
                        limitLabelPosition3,
                        limitLabelPosition4
                    };
        }

        public static LimitLabelPosition valueOf(String str) {
            return (LimitLabelPosition) Enum.valueOf(LimitLabelPosition.class, str);
        }

        public static LimitLabelPosition[] values() {
            return (LimitLabelPosition[]) $VALUES.clone();
        }
    }
}
