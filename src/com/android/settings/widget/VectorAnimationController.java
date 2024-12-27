package com.android.settings.widget;

import android.graphics.drawable.Drawable;
import android.view.TextureView;
import android.view.View;

import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class VectorAnimationController implements VideoPreference.AnimationController {
    public AnimatedVectorDrawableCompat mAnimatedVectorDrawableCompat;
    public AnonymousClass1 mAnimationCallback;
    public Drawable mPreviewDrawable;

    @Override // com.android.settings.widget.VideoPreference.AnimationController
    public final void attachView(TextureView textureView, final View view, final View view2) {
        this.mPreviewDrawable = view.getForeground();
        textureView.setVisibility(8);
        updateViewStates$1(view, view2);
        view.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.widget.VectorAnimationController$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view3) {
                        VectorAnimationController.this.updateViewStates$1(view, view2);
                    }
                });
    }

    @Override // com.android.settings.widget.VideoPreference.AnimationController
    public final int getDuration() {
        return 5000;
    }

    @Override // com.android.settings.widget.VideoPreference.AnimationController
    public final int getVideoHeight() {
        return this.mAnimatedVectorDrawableCompat.getIntrinsicHeight();
    }

    @Override // com.android.settings.widget.VideoPreference.AnimationController
    public final int getVideoWidth() {
        return this.mAnimatedVectorDrawableCompat.getIntrinsicWidth();
    }

    @Override // com.android.settings.widget.VideoPreference.AnimationController
    public final void release() {
        AnimatedVectorDrawableCompat animatedVectorDrawableCompat =
                this.mAnimatedVectorDrawableCompat;
        animatedVectorDrawableCompat.stop();
        animatedVectorDrawableCompat.clearAnimationCallbacks();
    }

    public final void updateViewStates$1(View view, View view2) {
        AnimatedVectorDrawableCompat animatedVectorDrawableCompat =
                this.mAnimatedVectorDrawableCompat;
        if (animatedVectorDrawableCompat.isRunning()) {
            animatedVectorDrawableCompat.stop();
            animatedVectorDrawableCompat.clearAnimationCallbacks();
            view2.setVisibility(0);
            view.setForeground(this.mPreviewDrawable);
            return;
        }
        view2.setVisibility(8);
        view.setForeground(animatedVectorDrawableCompat);
        animatedVectorDrawableCompat.start();
        animatedVectorDrawableCompat.registerAnimationCallback(this.mAnimationCallback);
    }
}
