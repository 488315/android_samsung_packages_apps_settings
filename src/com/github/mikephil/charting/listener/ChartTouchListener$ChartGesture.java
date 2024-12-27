package com.github.mikephil.charting.listener;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ChartTouchListener$ChartGesture {
    public static final /* synthetic */ ChartTouchListener$ChartGesture[] $VALUES;
    public static final ChartTouchListener$ChartGesture DOUBLE_TAP;
    public static final ChartTouchListener$ChartGesture DRAG;
    public static final ChartTouchListener$ChartGesture FLING;
    public static final ChartTouchListener$ChartGesture LONG_PRESS;
    public static final ChartTouchListener$ChartGesture NONE;
    public static final ChartTouchListener$ChartGesture PINCH_ZOOM;
    public static final ChartTouchListener$ChartGesture SINGLE_TAP;
    public static final ChartTouchListener$ChartGesture X_ZOOM;
    public static final ChartTouchListener$ChartGesture Y_ZOOM;

    static {
        ChartTouchListener$ChartGesture chartTouchListener$ChartGesture =
                new ChartTouchListener$ChartGesture("NONE", 0);
        NONE = chartTouchListener$ChartGesture;
        ChartTouchListener$ChartGesture chartTouchListener$ChartGesture2 =
                new ChartTouchListener$ChartGesture("DRAG", 1);
        DRAG = chartTouchListener$ChartGesture2;
        ChartTouchListener$ChartGesture chartTouchListener$ChartGesture3 =
                new ChartTouchListener$ChartGesture("X_ZOOM", 2);
        X_ZOOM = chartTouchListener$ChartGesture3;
        ChartTouchListener$ChartGesture chartTouchListener$ChartGesture4 =
                new ChartTouchListener$ChartGesture("Y_ZOOM", 3);
        Y_ZOOM = chartTouchListener$ChartGesture4;
        ChartTouchListener$ChartGesture chartTouchListener$ChartGesture5 =
                new ChartTouchListener$ChartGesture("PINCH_ZOOM", 4);
        PINCH_ZOOM = chartTouchListener$ChartGesture5;
        ChartTouchListener$ChartGesture chartTouchListener$ChartGesture6 =
                new ChartTouchListener$ChartGesture("ROTATE", 5);
        ChartTouchListener$ChartGesture chartTouchListener$ChartGesture7 =
                new ChartTouchListener$ChartGesture("SINGLE_TAP", 6);
        SINGLE_TAP = chartTouchListener$ChartGesture7;
        ChartTouchListener$ChartGesture chartTouchListener$ChartGesture8 =
                new ChartTouchListener$ChartGesture("DOUBLE_TAP", 7);
        DOUBLE_TAP = chartTouchListener$ChartGesture8;
        ChartTouchListener$ChartGesture chartTouchListener$ChartGesture9 =
                new ChartTouchListener$ChartGesture("LONG_PRESS", 8);
        LONG_PRESS = chartTouchListener$ChartGesture9;
        ChartTouchListener$ChartGesture chartTouchListener$ChartGesture10 =
                new ChartTouchListener$ChartGesture("FLING", 9);
        FLING = chartTouchListener$ChartGesture10;
        $VALUES =
                new ChartTouchListener$ChartGesture[] {
                    chartTouchListener$ChartGesture,
                    chartTouchListener$ChartGesture2,
                    chartTouchListener$ChartGesture3,
                    chartTouchListener$ChartGesture4,
                    chartTouchListener$ChartGesture5,
                    chartTouchListener$ChartGesture6,
                    chartTouchListener$ChartGesture7,
                    chartTouchListener$ChartGesture8,
                    chartTouchListener$ChartGesture9,
                    chartTouchListener$ChartGesture10
                };
    }

    public static ChartTouchListener$ChartGesture valueOf(String str) {
        return (ChartTouchListener$ChartGesture)
                Enum.valueOf(ChartTouchListener$ChartGesture.class, str);
    }

    public static ChartTouchListener$ChartGesture[] values() {
        return (ChartTouchListener$ChartGesture[]) $VALUES.clone();
    }
}
