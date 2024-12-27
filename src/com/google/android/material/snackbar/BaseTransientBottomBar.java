package com.google.android.material.snackbar;

import android.animation.TimeInterpolator;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.behavior.SwipeDismissBehavior;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class BaseTransientBottomBar {
    public static final /* synthetic */ int $r8$clinit = 0;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.google.android.material.snackbar.BaseTransientBottomBar$1, reason: invalid class name */
    public final class AnonymousClass1 implements Handler.Callback {
        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            int i = message.what;
            if (i == 0) {
                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(message.obj);
                throw null;
            }
            if (i != 1) {
                return false;
            }
            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(message.obj);
            throw null;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class Behavior extends SwipeDismissBehavior<View> {
        public final BehaviorDelegate delegate;

        public Behavior() {
            BehaviorDelegate behaviorDelegate = new BehaviorDelegate();
            this.alphaStartSwipeDistance = Math.min(Math.max(0.0f, 0.1f), 1.0f);
            this.alphaEndSwipeDistance = Math.min(Math.max(0.0f, 0.6f), 1.0f);
            this.swipeDirection = 0;
            this.delegate = behaviorDelegate;
        }

        @Override // com.google.android.material.behavior.SwipeDismissBehavior
        public final boolean canSwipeDismissView(View view) {
            this.delegate.getClass();
            return view instanceof Snackbar$SnackbarLayout;
        }

        @Override // com.google.android.material.behavior.SwipeDismissBehavior,
                  // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public final boolean onInterceptTouchEvent(
                CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
            this.delegate.getClass();
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked != 0) {
                if (actionMasked == 1 || actionMasked == 3) {
                    if (SnackbarManager.snackbarManager == null) {
                        SnackbarManager.snackbarManager = new SnackbarManager();
                    }
                    synchronized (SnackbarManager.snackbarManager.lock) {
                    }
                }
            } else if (coordinatorLayout.isPointInChildBounds(
                    view, (int) motionEvent.getX(), (int) motionEvent.getY())) {
                if (SnackbarManager.snackbarManager == null) {
                    SnackbarManager.snackbarManager = new SnackbarManager();
                }
                synchronized (SnackbarManager.snackbarManager.lock) {
                }
            }
            return super.onInterceptTouchEvent(coordinatorLayout, view, motionEvent);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class BehaviorDelegate {}

    static {
        TimeInterpolator timeInterpolator = AnimationUtils.LINEAR_INTERPOLATOR;
        new Handler(Looper.getMainLooper(), new AnonymousClass1());
    }
}
