package com.github.mikephil.charting.components;

import android.graphics.Paint;
import android.util.DisplayMetrics;

import com.github.mikephil.charting.utils.Utils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class YAxis extends AxisBase {
    public final AxisDependency mAxisDependency;
    public final boolean mDrawBottomYLabelEntry = true;
    public final boolean mDrawTopYLabelEntry = true;
    public float mSpacePercentTop = 10.0f;
    public final float mSpacePercentBottom = 10.0f;
    public final YAxisLabelPosition mPosition = YAxisLabelPosition.OUTSIDE_CHART;
    public float mMinWidth = 0.0f;
    public float mMaxWidth = Float.POSITIVE_INFINITY;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AxisDependency {
        public static final /* synthetic */ AxisDependency[] $VALUES;
        public static final AxisDependency LEFT;
        public static final AxisDependency RIGHT;

        static {
            AxisDependency axisDependency = new AxisDependency("LEFT", 0);
            LEFT = axisDependency;
            AxisDependency axisDependency2 = new AxisDependency("RIGHT", 1);
            RIGHT = axisDependency2;
            $VALUES = new AxisDependency[] {axisDependency, axisDependency2};
        }

        public static AxisDependency valueOf(String str) {
            return (AxisDependency) Enum.valueOf(AxisDependency.class, str);
        }

        public static AxisDependency[] values() {
            return (AxisDependency[]) $VALUES.clone();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class YAxisLabelPosition {
        public static final /* synthetic */ YAxisLabelPosition[] $VALUES;
        public static final YAxisLabelPosition OUTSIDE_CHART;

        static {
            YAxisLabelPosition yAxisLabelPosition = new YAxisLabelPosition("OUTSIDE_CHART", 0);
            OUTSIDE_CHART = yAxisLabelPosition;
            $VALUES =
                    new YAxisLabelPosition[] {
                        yAxisLabelPosition, new YAxisLabelPosition("INSIDE_CHART", 1)
                    };
        }

        public static YAxisLabelPosition valueOf(String str) {
            return (YAxisLabelPosition) Enum.valueOf(YAxisLabelPosition.class, str);
        }

        public static YAxisLabelPosition[] values() {
            return (YAxisLabelPosition[]) $VALUES.clone();
        }
    }

    public YAxis(AxisDependency axisDependency) {
        this.mAxisDependency = axisDependency;
        this.mYOffset = 0.0f;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0039  */
    @Override // com.github.mikephil.charting.components.AxisBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void calculate(float r6, float r7) {
        /*
            r5 = this;
            int r0 = (r6 > r7 ? 1 : (r6 == r7 ? 0 : -1))
            r1 = 0
            if (r0 <= 0) goto L1d
            boolean r0 = r5.mCustomAxisMax
            if (r0 == 0) goto Le
            boolean r2 = r5.mCustomAxisMin
            if (r2 == 0) goto Le
            goto L2f
        Le:
            r2 = 1056964608(0x3f000000, float:0.5)
            r3 = 1069547520(0x3fc00000, float:1.5)
            if (r0 == 0) goto L21
            int r6 = (r7 > r1 ? 1 : (r7 == r1 ? 0 : -1))
            if (r6 >= 0) goto L1b
            float r3 = r3 * r7
            r6 = r3
            goto L1d
        L1b:
            float r2 = r2 * r7
            r6 = r2
        L1d:
            r4 = r7
            r7 = r6
            r6 = r4
            goto L2f
        L21:
            boolean r0 = r5.mCustomAxisMin
            if (r0 == 0) goto L1d
            int r7 = (r6 > r1 ? 1 : (r6 == r1 ? 0 : -1))
            if (r7 >= 0) goto L2b
            float r2 = r2 * r6
            goto L2d
        L2b:
            float r2 = r6 * r3
        L2d:
            r7 = r6
            r6 = r2
        L2f:
            float r0 = r6 - r7
            float r0 = java.lang.Math.abs(r0)
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 != 0) goto L3d
            r0 = 1065353216(0x3f800000, float:1.0)
            float r6 = r6 + r0
            float r7 = r7 - r0
        L3d:
            float r0 = r6 - r7
            float r0 = java.lang.Math.abs(r0)
            boolean r1 = r5.mCustomAxisMin
            r2 = 1120403456(0x42c80000, float:100.0)
            if (r1 == 0) goto L4c
            float r7 = r5.mAxisMinimum
            goto L52
        L4c:
            float r1 = r0 / r2
            float r3 = r5.mSpacePercentBottom
            float r1 = r1 * r3
            float r7 = r7 - r1
        L52:
            r5.mAxisMinimum = r7
            boolean r1 = r5.mCustomAxisMax
            if (r1 == 0) goto L5b
            float r6 = r5.mAxisMaximum
            goto L60
        L5b:
            float r0 = r0 / r2
            float r1 = r5.mSpacePercentTop
            float r0 = r0 * r1
            float r6 = r6 + r0
        L60:
            r5.mAxisMaximum = r6
            float r7 = r7 - r6
            float r6 = java.lang.Math.abs(r7)
            r5.mAxisRange = r6
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.github.mikephil.charting.components.YAxis.calculate(float,"
                    + " float):void");
    }

    public final float getRequiredWidthSpace(Paint paint) {
        paint.setTextSize(this.mTextSize);
        String longestLabel = getLongestLabel();
        DisplayMetrics displayMetrics = Utils.mMetrics;
        float measureText = (this.mXOffset * 2.0f) + ((int) paint.measureText(longestLabel));
        float f = this.mMinWidth;
        float f2 = this.mMaxWidth;
        if (f > 0.0f) {
            f = Utils.convertDpToPixel(f);
        }
        if (f2 > 0.0f && f2 != Float.POSITIVE_INFINITY) {
            f2 = Utils.convertDpToPixel(f2);
        }
        if (f2 <= 0.0d) {
            f2 = measureText;
        }
        return Math.max(f, Math.min(measureText, f2));
    }
}
