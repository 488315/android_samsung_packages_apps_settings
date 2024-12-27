package com.android.settings.widget;

import android.animation.ValueAnimator;
import android.view.View;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class HighlightablePreferenceGroupAdapter$$ExternalSyntheticLambda4
        implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ View f$0;

    public /* synthetic */ HighlightablePreferenceGroupAdapter$$ExternalSyntheticLambda4(
            View view, int i) {
        this.$r8$classId = i;
        this.f$0 = view;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        int i = this.$r8$classId;
        View view = this.f$0;
        switch (i) {
            case 0:
                view.setBackgroundColor(((Integer) valueAnimator.getAnimatedValue()).intValue());
                break;
            default:
                view.setBackgroundColor(((Integer) valueAnimator.getAnimatedValue()).intValue());
                break;
        }
    }
}
