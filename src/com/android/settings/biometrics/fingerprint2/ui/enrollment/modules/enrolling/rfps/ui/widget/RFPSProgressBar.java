package com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.rfps.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.animation.AnimationUtils;

import com.android.settings.R;
import com.android.settings.widget.RingProgressBar;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0016\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0019\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0004\b\u0006\u0010\u0007¨\u0006\b"
        },
        d2 = {
            "Lcom/android/settings/biometrics/fingerprint2/ui/enrollment/modules/enrolling/rfps/ui/widget/RFPSProgressBar;",
            "Lcom/android/settings/widget/RingProgressBar;",
            "Landroid/content/Context;",
            "context",
            "Landroid/util/AttributeSet;",
            "attributeSet",
            "<init>",
            "(Landroid/content/Context;Landroid/util/AttributeSet;)V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class RFPSProgressBar extends RingProgressBar {
    public final AnimatedVectorDrawable iconAnimationDrawable;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RFPSProgressBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(attributeSet, "attributeSet");
        Drawable background = getBackground();
        Intrinsics.checkNotNull(
                background,
                "null cannot be cast to non-null type android.graphics.drawable.LayerDrawable");
        LayerDrawable layerDrawable = (LayerDrawable) background;
        Drawable findDrawableByLayerId =
                layerDrawable.findDrawableByLayerId(R.id.fingerprint_animation);
        Intrinsics.checkNotNull(
                findDrawableByLayerId,
                "null cannot be cast to non-null type"
                    + " android.graphics.drawable.AnimatedVectorDrawable");
        Drawable findDrawableByLayerId2 =
                layerDrawable.findDrawableByLayerId(R.id.fingerprint_background);
        Intrinsics.checkNotNull(
                findDrawableByLayerId2,
                "null cannot be cast to non-null type"
                    + " android.graphics.drawable.AnimatedVectorDrawable");
        Intrinsics.checkNotNullExpressionValue(
                AnimationUtils.loadInterpolator(context, android.R.interpolator.fast_out_slow_in),
                "loadInterpolator(...)");
        ((AnimatedVectorDrawable) findDrawableByLayerId)
                .registerAnimationCallback(
                        new Animatable2
                                .AnimationCallback() { // from class:
                                                       // com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.rfps.ui.widget.RFPSProgressBar.1
                            @Override // android.graphics.drawable.Animatable2.AnimationCallback
                            public final void onAnimationEnd(Drawable drawable) {
                                super.onAnimationEnd(drawable);
                                RFPSProgressBar.this.getClass();
                            }
                        });
        setProgressBackgroundTintMode(PorterDuff.Mode.SRC);
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(
                        R.style.RingProgressBarStyle, new int[] {android.R.attr.max});
        Intrinsics.checkNotNullExpressionValue(
                obtainStyledAttributes, "obtainStyledAttributes(...)");
        obtainStyledAttributes.getInt(0, -1);
        obtainStyledAttributes.recycle();
    }
}
