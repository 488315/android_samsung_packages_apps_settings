package com.samsung.android.settings.wifi.mobileap.views.progressbar;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class AbsProgressbar extends View {
    public Paint mBarBgPaint;
    public Paint mBarPaint;
    public float mGraphHeight;
    public Path mGraphOutlinePath;
    public Paint mGraphOutlineStrokePaint;
    public float mGraphRadius;
    public float mMaxGraphWidth;
    public float mProgress;
    public ValueAnimator mProgressAnimator;

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        StackProgressbar stackProgressbar = (StackProgressbar) this;
        this.mMaxGraphWidth = (int) (stackProgressbar.getWidth() * stackProgressbar.mProgress);
    }
}
