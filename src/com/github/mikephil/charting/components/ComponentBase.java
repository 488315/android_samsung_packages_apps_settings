package com.github.mikephil.charting.components;

import android.graphics.Typeface;

import com.github.mikephil.charting.utils.Utils;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class ComponentBase {
    public boolean mEnabled = true;
    public float mXOffset = 5.0f;
    public float mYOffset = 5.0f;
    public Typeface mTypeface = null;
    public float mTextSize = Utils.convertDpToPixel(10.0f);
    public int mTextColor = EmergencyPhoneWidget.BG_COLOR;

    public final void setTextSize(float f) {
        if (f > 24.0f) {
            f = 24.0f;
        }
        if (f < 6.0f) {
            f = 6.0f;
        }
        this.mTextSize = Utils.convertDpToPixel(f);
    }
}
