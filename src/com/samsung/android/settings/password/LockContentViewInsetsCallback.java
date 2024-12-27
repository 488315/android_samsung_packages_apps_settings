package com.samsung.android.settings.password;

import android.graphics.Insets;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;

import com.samsung.android.util.SemLog;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class LockContentViewInsetsCallback extends WindowInsetsAnimation.Callback
        implements View.OnApplyWindowInsetsListener {
    public final int mDeferInsetTypes;
    public boolean mImeShown;
    public boolean mIsDeferInsets;
    public WindowInsets mLastWindowInsets;
    public final int mPersistentInsetTypes;
    public final View mView;

    public LockContentViewInsetsCallback(View view, int i, int i2) {
        super(1);
        this.mIsDeferInsets = false;
        this.mImeShown = false;
        this.mView = view;
        this.mPersistentInsetTypes = i;
        this.mDeferInsetTypes = i2;
    }

    @Override // android.view.View.OnApplyWindowInsetsListener
    public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
        this.mLastWindowInsets = windowInsets;
        Insets insets =
                windowInsets.getInsets(
                        this.mIsDeferInsets
                                ? this.mPersistentInsetTypes
                                : this.mPersistentInsetTypes | this.mDeferInsetTypes);
        SemLog.i(
                "LockContentViewInsetsCallback",
                "onApplyWindowInsets, typeInsets = "
                        + insets
                        + ", mIsDeferInsets = "
                        + this.mIsDeferInsets
                        + ", mImeShown="
                        + this.mImeShown);
        if (!this.mImeShown) {
            this.mView.setPadding(insets.left, insets.top, insets.right, insets.bottom);
        }
        this.mImeShown = windowInsets.isVisible(this.mDeferInsetTypes);
        return WindowInsets.CONSUMED;
    }

    @Override // android.view.WindowInsetsAnimation.Callback
    public final void onEnd(WindowInsetsAnimation windowInsetsAnimation) {
        WindowInsets windowInsets;
        if ((windowInsetsAnimation.getTypeMask() & this.mDeferInsetTypes) != 0) {
            SemLog.i("LockContentViewInsetsCallback", "onEnd");
            this.mIsDeferInsets = false;
            View view = this.mView;
            if (view == null || (windowInsets = this.mLastWindowInsets) == null) {
                return;
            }
            view.dispatchApplyWindowInsets(windowInsets);
        }
    }

    @Override // android.view.WindowInsetsAnimation.Callback
    public final void onPrepare(WindowInsetsAnimation windowInsetsAnimation) {
        if ((windowInsetsAnimation.getTypeMask() & this.mDeferInsetTypes) != 0) {
            SemLog.i("LockContentViewInsetsCallback", "onPrepare");
            this.mIsDeferInsets = true;
        }
    }

    @Override // android.view.WindowInsetsAnimation.Callback
    public final WindowInsets onProgress(WindowInsets windowInsets, List list) {
        int i =
                this.mIsDeferInsets
                        ? this.mPersistentInsetTypes
                        : this.mPersistentInsetTypes | this.mDeferInsetTypes;
        Insets max =
                Insets.max(
                        Insets.subtract(
                                windowInsets.getInsets(this.mDeferInsetTypes),
                                windowInsets.getInsets(this.mPersistentInsetTypes)),
                        Insets.NONE);
        int i2 = max.top - max.bottom;
        Insets insets = windowInsets.getInsets(i);
        this.mView.setPadding(insets.left, insets.top, insets.right, insets.bottom - i2);
        return windowInsets;
    }

    @Override // android.view.WindowInsetsAnimation.Callback
    public final WindowInsetsAnimation.Bounds onStart(
            WindowInsetsAnimation windowInsetsAnimation, WindowInsetsAnimation.Bounds bounds) {
        if ((this.mDeferInsetTypes & windowInsetsAnimation.getTypeMask()) != 0) {
            SemLog.i("LockContentViewInsetsCallback", "onStart");
        }
        return bounds;
    }
}
