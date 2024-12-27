package com.samsung.android.settings.widget;

import android.content.Context;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import com.android.settings.R;

import com.airbnb.lottie.LottieAnimationView;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class PlayPauseLottieAnimationView extends LottieAnimationView {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AccessibilityDelegate extends AccessibilityDelegateCompat {
        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final void onInitializeAccessibilityNodeInfo(
                View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(
                    view, accessibilityNodeInfoCompat.mInfo);
            if (view instanceof LottieAnimationView) {
                accessibilityNodeInfoCompat.mInfo.setStateDescription(
                        view.getContext()
                                .getText(
                                        ((LottieAnimationView) view).lottieDrawable.isAnimating()
                                                ? R.string.animation_play
                                                : R.string.animation_stop));
            }
        }
    }

    public PlayPauseLottieAnimationView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setClickable(true);
        ViewCompat.setAccessibilityDelegate(this, new AccessibilityDelegate());
    }

    @Override // android.view.View
    public final boolean performClick() {
        if (this.lottieDrawable.isAnimating()) {
            pauseAnimation();
            announceForAccessibility(getContext().getText(R.string.animation_stopped));
        } else {
            resumeAnimation();
            announceForAccessibility(getContext().getText(R.string.animation_playing));
        }
        return super.performClick();
    }

    @Override // com.airbnb.lottie.LottieAnimationView
    public final void playAnimation$1() {
        if (Settings.Global.getInt(getContext().getContentResolver(), "remove_animations", 0)
                == 1) {
            cancelAnimation();
        } else {
            super.playAnimation$1();
        }
    }

    public PlayPauseLottieAnimationView(Context context) {
        this(context, null);
    }
}
