package com.android.settingslib.widget;

import android.app.ActionBar;
import android.app.Activity;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.recyclerview.widget.RecyclerView;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ActionBarShadowController implements LifecycleObserver {
    static final float ELEVATION_HIGH = 8.0f;
    static final float ELEVATION_LOW = 0.0f;
    public boolean mIsScrollWatcherAttached;
    ScrollChangeWatcher mScrollChangeWatcher;
    public View mScrollView;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ScrollChangeWatcher implements View.OnScrollChangeListener {
        public final Activity mActivity;

        public ScrollChangeWatcher(FragmentActivity fragmentActivity) {
            this.mActivity = fragmentActivity;
        }

        @Override // android.view.View.OnScrollChangeListener
        public final void onScrollChange(View view, int i, int i2, int i3, int i4) {
            ActionBar actionBar;
            boolean canScrollVertically = view.canScrollVertically(-1);
            Activity activity = this.mActivity;
            if (activity == null || (actionBar = activity.getActionBar()) == null) {
                return;
            }
            actionBar.setElevation(
                    canScrollVertically ? ActionBarShadowController.ELEVATION_HIGH : 0.0f);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private void attachScrollWatcher() {
        ActionBar actionBar;
        if (this.mIsScrollWatcherAttached) {
            return;
        }
        this.mIsScrollWatcherAttached = true;
        this.mScrollView.setOnScrollChangeListener(this.mScrollChangeWatcher);
        ScrollChangeWatcher scrollChangeWatcher = this.mScrollChangeWatcher;
        View view = this.mScrollView;
        scrollChangeWatcher.getClass();
        boolean canScrollVertically = view.canScrollVertically(-1);
        Activity activity = scrollChangeWatcher.mActivity;
        if (activity == null || (actionBar = activity.getActionBar()) == null) {
            return;
        }
        actionBar.setElevation(canScrollVertically ? ELEVATION_HIGH : 0.0f);
    }

    public static void attachToView(
            FragmentActivity fragmentActivity,
            com.android.settingslib.core.lifecycle.Lifecycle lifecycle,
            RecyclerView recyclerView) {
        ActionBarShadowController actionBarShadowController = new ActionBarShadowController();
        actionBarShadowController.mScrollChangeWatcher = new ScrollChangeWatcher(fragmentActivity);
        actionBarShadowController.mScrollView = recyclerView;
        actionBarShadowController.attachScrollWatcher();
        lifecycle.addObserver(actionBarShadowController);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private void detachScrollWatcher() {
        this.mScrollView.setOnScrollChangeListener(null);
        this.mIsScrollWatcherAttached = false;
    }
}
