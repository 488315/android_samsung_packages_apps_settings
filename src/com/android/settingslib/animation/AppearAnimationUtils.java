package com.android.settingslib.animation;

import android.content.Context;
import android.view.animation.Interpolator;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AppearAnimationUtils {
    public boolean mAppearing;
    public final float mDelayScale;
    public final long mDuration;
    public final Interpolator mInterpolator;
    public final AppearAnimationProperties mProperties;
    public RowTranslationScaler mRowTranslationScaler;
    public final float mStartTranslation;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AppearAnimationProperties {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface RowTranslationScaler {}

    public AppearAnimationUtils(
            Context context, long j, float f, float f2, Interpolator interpolator) {
        context.getResources().getDimensionPixelOffset(R.dimen.appear_y_translation_start);
    }
}
