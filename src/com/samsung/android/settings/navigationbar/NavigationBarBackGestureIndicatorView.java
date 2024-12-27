package com.samsung.android.settings.navigationbar;

import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.LinearLayout;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class NavigationBarBackGestureIndicatorView extends LinearLayout {
    public NavigationBarBackGestureIndicatorDrawable mBottomDrawable;
    public int mCutoutL;
    public int mCutoutR;
    public ViewGroup mLayout;
    public NavigationBarBackGestureIndicatorDrawable mLeftDrawable;
    public boolean mNavBarCanMove;
    public NavigationBarBackGestureIndicatorDrawable mRightDrawable;
    public int mRotation;

    @Override // android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        if (windowInsets.getDisplayCutout() != null) {
            this.mCutoutL = windowInsets.getDisplayCutout().getSafeInsetLeft();
            this.mCutoutR = windowInsets.getDisplayCutout().getSafeInsetRight();
        }
        return super.onApplyWindowInsets(windowInsets);
    }

    @Override // android.view.View
    public final WindowManager.LayoutParams getLayoutParams() {
        WindowManager.LayoutParams layoutParams =
                new WindowManager.LayoutParams(2038, -2130640616, -3);
        layoutParams.setFitInsetsTypes(0);
        layoutParams.alpha = 0.2f;
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.setTitle("BackGestureIndicatorView");
        layoutParams.token = getContext().getActivityToken();
        return layoutParams;
    }
}
