package com.android.settings.display;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;

import androidx.viewpager.widget.PagerAdapter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PreviewPagerAdapter extends PagerAdapter {
    public static final Interpolator FADE_IN_INTERPOLATOR = null;
    public static final Interpolator FADE_OUT_INTERPOLATOR = null;
    public boolean mIsLayoutRtl;
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
        viewGroup.addView(this.mPreviewFrames[i]);
        return this.mPreviewFrames[i];
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    public final void setPreviewLayer(int i, int i2, int i3) {
        for (FrameLayout frameLayout : this.mPreviewFrames) {
            boolean[][] zArr = this.mViewStubInflated;
            if (i2 >= 0) {
                View childAt = frameLayout.getChildAt(i2);
                if (zArr[i3][i2]) {
                    if (frameLayout == this.mPreviewFrames[i3]) {
                        setVisibility(childAt, 4);
                    } else {
                        setVisibility(childAt, 4);
                    }
                }
            }
            View childAt2 = frameLayout.getChildAt(i);
            if (frameLayout == this.mPreviewFrames[i3]) {
                if (!zArr[i3][i]) {
                    childAt2 = ((ViewStub) childAt2).inflate();
                    childAt2.setAlpha(0.0f);
                }
                setVisibility(childAt2, 0);
            } else {
                setVisibility(childAt2, 0);
            }
        }
    }

    public final void setVisibility(View view, int i) {
        view.setAlpha(i == 0 ? 1.0f : 0.0f);
        view.setVisibility(i);
    }
}
