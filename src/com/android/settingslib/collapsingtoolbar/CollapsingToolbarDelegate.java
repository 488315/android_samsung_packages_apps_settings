package com.android.settingslib.collapsingtoolbar;

import android.widget.FrameLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class CollapsingToolbarDelegate {
    public AppBarLayout mAppBarLayout;
    public CollapsingToolbarLayout mCollapsingToolbarLayout;
    public FrameLayout mContentFrameLayout;
    public final CollapsingToolbarBaseFragment.DelegateCallback mHostCallback;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settingslib.collapsingtoolbar.CollapsingToolbarDelegate$1, reason: invalid class name */
    public final class AnonymousClass1 extends AppBarLayout.Behavior.DragCallback {
        @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior.BaseDragCallback
        public final boolean canDrag(AppBarLayout appBarLayout) {
            return appBarLayout.getResources().getConfiguration().orientation == 2;
        }
    }

    public CollapsingToolbarDelegate(
            CollapsingToolbarBaseFragment.DelegateCallback delegateCallback) {
        this.mHostCallback = delegateCallback;
    }
}
