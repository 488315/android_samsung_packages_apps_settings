package com.google.android.material.bottomsheet;

import android.view.View;

import androidx.core.view.WindowInsetsAnimationCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.animation.AnimationUtils;

import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class InsetsAnimationCallback extends WindowInsetsAnimationCompat.Callback {
    public int startTranslationY;
    public int startY;
    public final int[] tmpLocation;
    public final View view;

    public InsetsAnimationCallback(View view) {
        super(0);
        this.tmpLocation = new int[2];
        this.view = view;
    }

    @Override // androidx.core.view.WindowInsetsAnimationCompat.Callback
    public final void onEnd(WindowInsetsAnimationCompat windowInsetsAnimationCompat) {
        this.view.setTranslationY(0.0f);
    }

    @Override // androidx.core.view.WindowInsetsAnimationCompat.Callback
    public final void onPrepare() {
        View view = this.view;
        int[] iArr = this.tmpLocation;
        view.getLocationOnScreen(iArr);
        this.startY = iArr[1];
    }

    @Override // androidx.core.view.WindowInsetsAnimationCompat.Callback
    public final WindowInsetsCompat onProgress(WindowInsetsCompat windowInsetsCompat, List list) {
        Iterator it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            if ((((WindowInsetsAnimationCompat) it.next()).mImpl.mWrapped.getTypeMask() & 8) != 0) {
                this.view.setTranslationY(
                        AnimationUtils.lerp(
                                this.startTranslationY,
                                0,
                                r0.mImpl.mWrapped.getInterpolatedFraction()));
                break;
            }
        }
        return windowInsetsCompat;
    }

    @Override // androidx.core.view.WindowInsetsAnimationCompat.Callback
    public final WindowInsetsAnimationCompat.BoundsCompat onStart(
            WindowInsetsAnimationCompat.BoundsCompat boundsCompat) {
        View view = this.view;
        int[] iArr = this.tmpLocation;
        view.getLocationOnScreen(iArr);
        int i = this.startY - iArr[1];
        this.startTranslationY = i;
        this.view.setTranslationY(i);
        return boundsCompat;
    }
}
