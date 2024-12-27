package com.android.settingslib.animation;

import android.content.Context;
import android.view.animation.Interpolator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DisappearAnimationUtils extends AppearAnimationUtils {
    public static final AnonymousClass1 ROW_TRANSLATION_SCALER = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settingslib.animation.DisappearAnimationUtils$1, reason: invalid class name */
    public final class AnonymousClass1 implements AppearAnimationUtils.RowTranslationScaler {}

    public DisappearAnimationUtils(Context context, Interpolator interpolator) {
        this(context, 110L, 1.0f, 0.5f, interpolator, ROW_TRANSLATION_SCALER);
    }

    public DisappearAnimationUtils(
            Context context,
            long j,
            float f,
            float f2,
            Interpolator interpolator,
            AppearAnimationUtils.RowTranslationScaler rowTranslationScaler) {
        super(context, j, f, f2, interpolator);
    }
}
