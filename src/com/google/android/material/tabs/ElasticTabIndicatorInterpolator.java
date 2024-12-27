package com.google.android.material.tabs;

import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.google.android.material.animation.AnimationUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ElasticTabIndicatorInterpolator extends TabIndicatorInterpolator {
    @Override // com.google.android.material.tabs.TabIndicatorInterpolator
    public final void updateIndicatorForOffset(
            TabLayout tabLayout, View view, View view2, float f, Drawable drawable) {
        float sin;
        float cos;
        RectF calculateIndicatorWidthForTab =
                TabIndicatorInterpolator.calculateIndicatorWidthForTab(tabLayout, view);
        RectF calculateIndicatorWidthForTab2 =
                TabIndicatorInterpolator.calculateIndicatorWidthForTab(tabLayout, view2);
        if (calculateIndicatorWidthForTab.left < calculateIndicatorWidthForTab2.left) {
            double d = (f * 3.141592653589793d) / 2.0d;
            sin = (float) (1.0d - Math.cos(d));
            cos = (float) Math.sin(d);
        } else {
            double d2 = (f * 3.141592653589793d) / 2.0d;
            sin = (float) Math.sin(d2);
            cos = (float) (1.0d - Math.cos(d2));
        }
        drawable.setBounds(
                AnimationUtils.lerp(
                        (int) calculateIndicatorWidthForTab.left,
                        (int) calculateIndicatorWidthForTab2.left,
                        sin),
                drawable.getBounds().top,
                AnimationUtils.lerp(
                        (int) calculateIndicatorWidthForTab.right,
                        (int) calculateIndicatorWidthForTab2.right,
                        cos),
                drawable.getBounds().bottom);
    }
}
