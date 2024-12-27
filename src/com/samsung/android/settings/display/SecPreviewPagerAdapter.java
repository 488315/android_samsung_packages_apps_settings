package com.samsung.android.settings.display;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;

import androidx.core.widget.NestedScrollView;
import androidx.reflect.view.SeslViewReflector;
import androidx.viewpager.widget.PagerAdapter;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecPreviewPagerAdapter extends PagerAdapter {
    public static final Interpolator FADE_IN_INTERPOLATOR = null;
    public static final Interpolator FADE_OUT_INTERPOLATOR = null;
    public final int mAnimationCounter;
    public Runnable mAnimationEndAction;
    public Context mContext;
    public FrameLayout[] mPreviewFrames;
    public boolean[][] mViewStubInflated;

    static {
        new DecelerateInterpolator();
        new AccelerateInterpolator();
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView((View) obj);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final int getCount() {
        return this.mPreviewFrames.length;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final Object instantiateItem(ViewGroup viewGroup, int i) {
        Resources resources = this.mContext.getResources();
        NestedScrollView nestedScrollView =
                (NestedScrollView)
                        this.mPreviewFrames[i].findViewById(
                                R.id.sec_screen_zoom_preview_1_nested_scroll_view);
        int dimensionPixelSize =
                resources.getDimensionPixelSize(R.dimen.sec_nested_view_scroll_end_padding)
                        + resources.getDimensionPixelSize(R.dimen.sec_nested_view_scroll_padding);
        nestedScrollView.mScrollbarTopPadding = dimensionPixelSize;
        nestedScrollView.mScrollbarBottomPadding = dimensionPixelSize;
        SeslViewReflector.semSetScrollBarTopPadding(nestedScrollView, dimensionPixelSize);
        SeslViewReflector.semSetScrollBarBottomPadding(
                nestedScrollView, nestedScrollView.mScrollbarBottomPadding);
        viewGroup.addView(this.mPreviewFrames[i]);
        return this.mPreviewFrames[i];
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    public final void setVisibility$1(View view, int i) {
        view.setAlpha(i == 0 ? 1.0f : 0.0f);
        view.setVisibility(i);
    }
}
