package com.android.settings.biometrics2.ui.view;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;

import com.android.settings.R;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.SimpleLottieValueCallback;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class FingerprintEnrollEnrollingSfpsFragmentKt {
    public static final void applyLottieDynamicColor(
            LottieAnimationView lottieAnimationView, final Context context, final boolean z) {
        lottieAnimationView.lottieDrawable.addValueCallback(
                new KeyPath(".blue100", "**"),
                LottieProperty.COLOR_FILTER,
                new LottieAnimationView.AnonymousClass1(
                        new SimpleLottieValueCallback() { // from class:
                                                          // com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingSfpsFragmentKt$applyLottieDynamicColor$1
                            @Override // com.airbnb.lottie.value.SimpleLottieValueCallback
                            public final Object getValue() {
                                return new PorterDuffColorFilter(
                                        context.getColor(
                                                z
                                                        ? R.color.sfps_enrollment_fp_error_color
                                                        : R.color
                                                                .sfps_enrollment_fp_captured_color),
                                        PorterDuff.Mode.SRC_ATOP);
                            }
                        }));
        lottieAnimationView.invalidate();
    }
}
