package com.android.settings.widget;

import android.R;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class LoadingViewController {
    public final View mContentView;
    public final View mEmptyView;
    public final View mLoadingView;
    public final AnonymousClass1 mShowLoadingContainerRunnable =
            new Runnable() { // from class: com.android.settings.widget.LoadingViewController.1
                @Override // java.lang.Runnable
                public final void run() {
                    LoadingViewController.this.handleLoadingContainer(false, false, false);
                }
            };
    public final Handler mFgHandler = new Handler(Looper.getMainLooper());

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.settings.widget.LoadingViewController$1] */
    public LoadingViewController(View view, View view2, View view3) {
        this.mLoadingView = view;
        this.mContentView = view2;
        this.mEmptyView = view3;
    }

    public static void setViewShown(final View view, boolean z, boolean z2) {
        if (!z2) {
            view.clearAnimation();
            view.setVisibility(z ? 0 : 4);
            return;
        }
        Animation loadAnimation =
                AnimationUtils.loadAnimation(
                        view.getContext(), z ? R.anim.fade_in : R.anim.fade_out);
        if (z) {
            view.setVisibility(0);
        } else {
            loadAnimation.setAnimationListener(
                    new Animation
                            .AnimationListener() { // from class:
                                                   // com.android.settings.widget.LoadingViewController.2
                        @Override // android.view.animation.Animation.AnimationListener
                        public final void onAnimationEnd(Animation animation) {
                            view.setVisibility(4);
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public final void onAnimationRepeat(Animation animation) {}

                        @Override // android.view.animation.Animation.AnimationListener
                        public final void onAnimationStart(Animation animation) {}
                    });
        }
        view.startAnimation(loadAnimation);
    }

    public final void handleLoadingContainer(boolean z, boolean z2, boolean z3) {
        View view = this.mLoadingView;
        View view2 = this.mContentView;
        View view3 = this.mEmptyView;
        if (view3 != null) {
            setViewShown(view3, z2, z3);
        }
        setViewShown(view2, z, z3);
        setViewShown(view, (z || z2) ? false : true, z3);
    }

    public final void showContent(boolean z) {
        this.mFgHandler.removeCallbacks(this.mShowLoadingContainerRunnable);
        handleLoadingContainer(true, false, z);
    }

    public final void showEmpty() {
        if (this.mEmptyView == null) {
            return;
        }
        this.mFgHandler.removeCallbacks(this.mShowLoadingContainerRunnable);
        handleLoadingContainer(false, true, false);
    }
}
