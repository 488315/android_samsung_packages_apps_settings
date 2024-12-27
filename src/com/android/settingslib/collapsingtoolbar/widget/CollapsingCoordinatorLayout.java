package com.android.settingslib.collapsingtoolbar.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.android.settings.R;
import com.android.settingslib.collapsingtoolbar.R$styleable;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.internal.CollapsingTextHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class CollapsingCoordinatorLayout extends CoordinatorLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AppBarLayout mAppBarLayout;
    public final CollapsingToolbarLayout mCollapsingToolbarLayout;
    public final boolean mIsMatchParentHeight;
    public final CharSequence mToolbarTitle;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settingslib.collapsingtoolbar.widget.CollapsingCoordinatorLayout$1, reason: invalid class name */
    public final class AnonymousClass1 extends AppBarLayout.Behavior.DragCallback {
        @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior.BaseDragCallback
        public final boolean canDrag(AppBarLayout appBarLayout) {
            return appBarLayout.getResources().getConfiguration().orientation == 2;
        }
    }

    public CollapsingCoordinatorLayout(Context context) {
        this(context, null);
    }

    @Override // android.view.ViewGroup
    public final void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        int id;
        if (view.getId() == R.id.content_frame && this.mIsMatchParentHeight) {
            layoutParams.height = -1;
        }
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.content_frame);
        if (viewGroup == null || (id = view.getId()) == R.id.app_bar || id == R.id.content_frame) {
            super.addView(view, i, layoutParams);
        } else {
            viewGroup.addView(view, i, layoutParams);
        }
    }

    public AppBarLayout getAppBarLayout() {
        return this.mAppBarLayout;
    }

    public CollapsingToolbarLayout getCollapsingToolbarLayout() {
        return this.mCollapsingToolbarLayout;
    }

    public View getContentFrameLayout() {
        return findViewById(R.id.content_frame);
    }

    public Toolbar getSupportToolbar() {
        return (Toolbar) this.mCollapsingToolbarLayout.findViewById(R.id.support_action_bar);
    }

    public CollapsingCoordinatorLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CollapsingCoordinatorLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIsMatchParentHeight = false;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes =
                    context.obtainStyledAttributes(
                            attributeSet, R$styleable.CollapsingCoordinatorLayout);
            this.mToolbarTitle = obtainStyledAttributes.getText(0);
            this.mIsMatchParentHeight = obtainStyledAttributes.getBoolean(1, false);
            obtainStyledAttributes.recycle();
        }
        ViewGroup.inflate(getContext(), R.layout.collapsing_toolbar_content_layout, this);
        this.mCollapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        this.mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        CollapsingToolbarLayout collapsingToolbarLayout = this.mCollapsingToolbarLayout;
        if (collapsingToolbarLayout != null) {
            CollapsingTextHelper collapsingTextHelper =
                    collapsingToolbarLayout.collapsingTextHelper;
            collapsingTextHelper.lineSpacingMultiplier = 1.1f;
            collapsingTextHelper.hyphenationFrequency = 3;
            CollapsingCoordinatorLayout$$ExternalSyntheticLambda0
                    collapsingCoordinatorLayout$$ExternalSyntheticLambda0 =
                            new CollapsingCoordinatorLayout$$ExternalSyntheticLambda0();
            if (collapsingTextHelper.staticLayoutBuilderConfigurer
                    != collapsingCoordinatorLayout$$ExternalSyntheticLambda0) {
                collapsingTextHelper.staticLayoutBuilderConfigurer =
                        collapsingCoordinatorLayout$$ExternalSyntheticLambda0;
                collapsingTextHelper.recalculate(true);
            }
            if (!TextUtils.isEmpty(this.mToolbarTitle)) {
                this.mCollapsingToolbarLayout.setTitle(this.mToolbarTitle);
            }
        }
        AppBarLayout appBarLayout = this.mAppBarLayout;
        if (appBarLayout == null) {
            return;
        }
        CoordinatorLayout.LayoutParams layoutParams =
                (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = new AppBarLayout.Behavior();
        behavior.onDragCallback = new AnonymousClass1();
        layoutParams.setBehavior(behavior);
    }
}
