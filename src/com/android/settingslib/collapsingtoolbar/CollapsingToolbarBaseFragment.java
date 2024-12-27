package com.android.settingslib.collapsingtoolbar;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toolbar;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.android.settings.R;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.internal.CollapsingTextHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class CollapsingToolbarBaseFragment extends Fragment {
    public CollapsingToolbarDelegate mToolbardelegate;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DelegateCallback {
        public DelegateCallback() {}
    }

    @Override // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mToolbardelegate = new CollapsingToolbarDelegate(new DelegateCallback());
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        CollapsingToolbarDelegate collapsingToolbarDelegate = this.mToolbardelegate;
        collapsingToolbarDelegate.getClass();
        View inflate =
                layoutInflater.inflate(R.layout.collapsing_toolbar_base_layout, viewGroup, false);
        if (inflate instanceof CoordinatorLayout) {}
        collapsingToolbarDelegate.mCollapsingToolbarLayout =
                (CollapsingToolbarLayout) inflate.findViewById(R.id.collapsing_toolbar);
        collapsingToolbarDelegate.mAppBarLayout = (AppBarLayout) inflate.findViewById(R.id.app_bar);
        CollapsingToolbarLayout collapsingToolbarLayout =
                collapsingToolbarDelegate.mCollapsingToolbarLayout;
        if (collapsingToolbarLayout != null) {
            CollapsingTextHelper collapsingTextHelper =
                    collapsingToolbarLayout.collapsingTextHelper;
            collapsingTextHelper.lineSpacingMultiplier = 1.1f;
            collapsingTextHelper.hyphenationFrequency = 3;
            CollapsingToolbarDelegate$$ExternalSyntheticLambda0
                    collapsingToolbarDelegate$$ExternalSyntheticLambda0 =
                            new CollapsingToolbarDelegate$$ExternalSyntheticLambda0();
            if (collapsingTextHelper.staticLayoutBuilderConfigurer
                    != collapsingToolbarDelegate$$ExternalSyntheticLambda0) {
                collapsingTextHelper.staticLayoutBuilderConfigurer =
                        collapsingToolbarDelegate$$ExternalSyntheticLambda0;
                collapsingTextHelper.recalculate(true);
            }
        }
        AppBarLayout appBarLayout = collapsingToolbarDelegate.mAppBarLayout;
        if (appBarLayout != null) {
            CoordinatorLayout.LayoutParams layoutParams =
                    (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
            AppBarLayout.Behavior behavior = new AppBarLayout.Behavior();
            behavior.onDragCallback = new CollapsingToolbarDelegate.AnonymousClass1();
            layoutParams.setBehavior(behavior);
        }
        collapsingToolbarDelegate.mContentFrameLayout =
                (FrameLayout) inflate.findViewById(R.id.content_frame);
        Log.d("CTBdelegate", "onCreateView: from NonAppCompatActivity.");
        CollapsingToolbarBaseFragment.this
                .requireActivity()
                .setActionBar((Toolbar) inflate.findViewById(R.id.action_bar));
        return inflate;
    }
}
