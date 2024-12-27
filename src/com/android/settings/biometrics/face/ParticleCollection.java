package com.android.settings.biometrics.face;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.biometrics.BiometricEnrollSidecar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ParticleCollection implements BiometricEnrollSidecar.Listener {
    public final Listener mListener;
    public final List mPrimariesInProgress;
    public int mState;
    public final AnonymousClass1 mParticleListener = new AnonymousClass1();
    public final List mParticleList = new ArrayList();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.biometrics.face.ParticleCollection$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public AnonymousClass1() {}
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Listener {
        void onEnrolled();
    }

    public ParticleCollection(
            Context context,
            FaceEnrollAnimationDrawable.AnonymousClass1 anonymousClass1,
            Rect rect) {
        this.mListener = anonymousClass1;
        ArrayList arrayList = new ArrayList();
        Resources.Theme theme = context.getTheme();
        Resources resources = context.getResources();
        arrayList.add(
                Integer.valueOf(resources.getColor(R.color.face_anim_particle_color_1, theme)));
        arrayList.add(
                Integer.valueOf(resources.getColor(R.color.face_anim_particle_color_2, theme)));
        arrayList.add(
                Integer.valueOf(resources.getColor(R.color.face_anim_particle_color_3, theme)));
        arrayList.add(
                Integer.valueOf(resources.getColor(R.color.face_anim_particle_color_4, theme)));
        this.mPrimariesInProgress = new ArrayList(Arrays.asList(0, 4, 8));
        int[] iArr = {3, 7, 11, 2, 6, 10, 1, 5, 9, 0, 4, 8};
        for (int i = 0; i < 12; i++) {
            AnimationParticle animationParticle =
                    new AnimationParticle(
                            context, this.mParticleListener, rect, iArr[i], arrayList);
            if (((ArrayList) this.mPrimariesInProgress).contains(Integer.valueOf(iArr[i]))) {
                animationParticle.mSweepRate = 480.0f;
            }
            ((ArrayList) this.mParticleList).add(animationParticle);
        }
        updateState$1(1);
    }

    @Override // com.android.settings.biometrics.BiometricEnrollSidecar.Listener
    public final void onEnrollmentProgressChange(int i, int i2) {
        if (i2 == 0) {
            updateState$1(4);
        }
    }

    public final void updateState$1(int i) {
        if (this.mState != i) {
            for (int i2 = 0; i2 < ((ArrayList) this.mParticleList).size(); i2++) {
                AnimationParticle animationParticle =
                        (AnimationParticle) ((ArrayList) this.mParticleList).get(i2);
                if (animationParticle.mAnimationState == i) {
                    RecordingInputConnection$$ExternalSyntheticOutline0.m(
                            i, "Already in state ", "AnimationParticle");
                } else {
                    if (i == 4) {
                        animationParticle.mPaint.setStyle(Paint.Style.STROKE);
                    }
                    animationParticle.mLastAnimationState = animationParticle.mAnimationState;
                    animationParticle.mAnimationState = i;
                }
            }
            this.mState = i;
        }
    }

    @Override // com.android.settings.biometrics.BiometricEnrollSidecar.Listener
    public final void onEnrollmentError(int i, CharSequence charSequence) {}

    @Override // com.android.settings.biometrics.BiometricEnrollSidecar.Listener
    public final void onEnrollmentHelp(int i, CharSequence charSequence) {}
}
