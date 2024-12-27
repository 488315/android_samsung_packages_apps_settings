package com.github.mikephil.charting.components;

import android.graphics.DashPathEffect;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class LegendEntry {
    public Legend.LegendForm form;
    public int formColor;
    public DashPathEffect formLineDashEffect = null;
    public float formLineWidth;
    public float formSize;
    public String label;

    public LegendEntry(String str, Legend.LegendForm legendForm, float f, float f2, int i) {
        this.label = str;
        this.form = legendForm;
        this.formSize = f;
        this.formLineWidth = f2;
        this.formColor = i;
    }
}
