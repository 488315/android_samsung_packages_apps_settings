package com.google.android.material.tabs;

import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.google.android.material.animation.AnimationUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class FadeTabIndicatorInterpolator extends TabIndicatorInterpolator {
    @Override // com.google.android.material.tabs.TabIndicatorInterpolator
    public final void updateIndicatorForOffset(
            TabLayout tabLayout, View view, View view2, float f, Drawable drawable) {
        if (f >= 0.5f) {
            view = view2;
        }
        RectF calculateIndicatorWidthForTab =
                TabIndicatorInterpolator.calculateIndicatorWidthForTab(tabLayout, view);
        float lerp =
                f < 0.5f
                        ? AnimationUtils.lerp(1.0f, 0.0f, 0.0f, 0.5f, f)
                        : AnimationUtils.lerp(0.0f, 1.0f, 0.5f, 1.0f, f);
        drawable.setBounds(
                (int) calculateIndicatorWidthForTab.left,
                drawable.getBounds().top,
                (int) calculateIndicatorWidthForTab.right,
                drawable.getBounds().bottom);
        drawable.setAlpha((int) (lerp * 255.0f));
    }
}
