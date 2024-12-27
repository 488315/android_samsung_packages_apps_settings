package com.github.mikephil.charting.components;

import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.Utils;
import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class AxisBase extends ComponentBase {
    public IAxisValueFormatter mAxisValueFormatter;
    public int mDecimals;
    public int mEntryCount;
    public final List mLimitLines;
    public int mGridColor = -7829368;
    public float mGridLineWidth = 1.0f;
    public final int mAxisLineColor = -7829368;
    public final float mAxisLineWidth = 1.0f;
    public float[] mEntries = new float[0];
    public int mLabelCount = 6;
    public float mGranularity = 1.0f;
    public boolean mGranularityEnabled = false;
    public boolean mForceLabels = false;
    public boolean mDrawGridLines = true;
    public boolean mDrawAxisLine = true;
    public boolean mDrawLabels = true;
    public final boolean mDrawGridLinesBehindData = true;
    public float mSpaceMin = 0.0f;
    public float mSpaceMax = 0.0f;
    public boolean mCustomAxisMin = false;
    public boolean mCustomAxisMax = false;
    public float mAxisMaximum = 0.0f;
    public float mAxisMinimum = 0.0f;
    public float mAxisRange = 0.0f;
    public final int mAxisMinLabels = 2;
    public final int mAxisMaxLabels = 25;

    public AxisBase() {
        this.mTextSize = Utils.convertDpToPixel(10.0f);
        this.mXOffset = Utils.convertDpToPixel(5.0f);
        this.mYOffset = Utils.convertDpToPixel(5.0f);
        this.mLimitLines = new ArrayList();
    }

    public void calculate(float f, float f2) {
        float f3 = this.mCustomAxisMin ? this.mAxisMinimum : f - this.mSpaceMin;
        float f4 = this.mCustomAxisMax ? this.mAxisMaximum : f2 + this.mSpaceMax;
        if (Math.abs(f4 - f3) == 0.0f) {
            f4 += 1.0f;
            f3 -= 1.0f;
        }
        this.mAxisMinimum = f3;
        this.mAxisMaximum = f4;
        this.mAxisRange = Math.abs(f4 - f3);
    }

    public final String getFormattedLabel(int i) {
        return (i < 0 || i >= this.mEntries.length)
                ? ApnSettings.MVNO_NONE
                : getValueFormatter().getFormattedValue(this.mEntries[i]);
    }

    public final String getLongestLabel() {
        String str = ApnSettings.MVNO_NONE;
        for (int i = 0; i < this.mEntries.length; i++) {
            String formattedLabel = getFormattedLabel(i);
            if (formattedLabel != null && str.length() < formattedLabel.length()) {
                str = formattedLabel;
            }
        }
        return str;
    }

    public final IAxisValueFormatter getValueFormatter() {
        IAxisValueFormatter iAxisValueFormatter = this.mAxisValueFormatter;
        if (iAxisValueFormatter == null
                || ((iAxisValueFormatter instanceof DefaultAxisValueFormatter)
                        && ((DefaultAxisValueFormatter) iAxisValueFormatter).digits
                                != this.mDecimals)) {
            this.mAxisValueFormatter = new DefaultAxisValueFormatter(this.mDecimals);
        }
        return this.mAxisValueFormatter;
    }

    public final void setAxisMaximum(float f) {
        this.mCustomAxisMax = true;
        this.mAxisMaximum = f;
        this.mAxisRange = Math.abs(f - this.mAxisMinimum);
    }

    public final void setAxisMinimum(float f) {
        this.mCustomAxisMin = true;
        this.mAxisMinimum = f;
        this.mAxisRange = Math.abs(this.mAxisMaximum - f);
    }

    public final void setGranularity() {
        this.mGranularity = 1.0f;
        this.mGranularityEnabled = true;
    }

    public final void setLabelCount(int i) {
        int i2 = this.mAxisMaxLabels;
        if (i > i2) {
            i = i2;
        }
        int i3 = this.mAxisMinLabels;
        if (i < i3) {
            i = i3;
        }
        this.mLabelCount = i;
        this.mForceLabels = true;
    }
}
