package com.github.mikephil.charting.data;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.github.mikephil.charting.formatter.DefaultFillFormatter;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.utils.Utils;
import com.samsung.android.knox.custom.IKnoxCustomManager;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class LineDataSet extends BarLineScatterCandleBubbleDataSet {
    public final List mCircleColors;
    public final int mCircleHoleColor;
    public final float mCircleHoleRadius;
    public final float mCircleRadius;
    public final float mCubicIntensity;
    public final boolean mDrawCircleHole;
    public boolean mDrawCircles;
    public boolean mDrawFilled;
    public boolean mDrawHorizontalHighlightIndicator;
    public boolean mDrawVerticalHighlightIndicator;
    public final int mFillAlpha;
    public final int mFillColor;
    public Drawable mFillDrawable;
    public IFillFormatter mFillFormatter;
    public float mHighlightLineWidth;
    public float mLineWidth;
    public final Mode mMode;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Mode {
        public static final /* synthetic */ Mode[] $VALUES;
        public static final Mode LINEAR;
        public static final Mode STEPPED;

        static {
            Mode mode = new Mode("LINEAR", 0);
            LINEAR = mode;
            Mode mode2 = new Mode("STEPPED", 1);
            STEPPED = mode2;
            $VALUES =
                    new Mode[] {
                        mode, mode2, new Mode("CUBIC_BEZIER", 2), new Mode("HORIZONTAL_BEZIER", 3)
                    };
        }

        public static Mode valueOf(String str) {
            return (Mode) Enum.valueOf(Mode.class, str);
        }

        public static Mode[] values() {
            return (Mode[]) $VALUES.clone();
        }
    }

    public LineDataSet(String str, List list) {
        super(str, list);
        this.mDrawVerticalHighlightIndicator = true;
        this.mDrawHorizontalHighlightIndicator = true;
        this.mHighlightLineWidth = 0.5f;
        this.mHighlightLineWidth = Utils.convertDpToPixel(0.5f);
        this.mFillColor =
                Color.rgb(140, IKnoxCustomManager.Stub.TRANSACTION_deleteHomeScreenPage, 255);
        this.mFillAlpha = 85;
        this.mLineWidth = 2.5f;
        this.mDrawFilled = false;
        this.mMode = Mode.LINEAR;
        this.mCircleColors = null;
        this.mCircleHoleColor = -1;
        this.mCircleRadius = 8.0f;
        this.mCircleHoleRadius = 4.0f;
        this.mCubicIntensity = 0.2f;
        this.mFillFormatter = new DefaultFillFormatter();
        this.mDrawCircles = true;
        this.mDrawCircleHole = true;
        ArrayList arrayList = new ArrayList();
        this.mCircleColors = arrayList;
        arrayList.clear();
        ((ArrayList) this.mCircleColors)
                .add(
                        Integer.valueOf(
                                Color.rgb(
                                        140,
                                        IKnoxCustomManager.Stub.TRANSACTION_deleteHomeScreenPage,
                                        255)));
    }

    public final void setLineWidth(float f) {
        if (f < 0.0f) {
            f = 0.0f;
        }
        if (f > 10.0f) {
            f = 10.0f;
        }
        this.mLineWidth = Utils.convertDpToPixel(f);
    }
}
