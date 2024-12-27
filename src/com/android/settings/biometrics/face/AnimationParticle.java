package com.android.settings.biometrics.face;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;

import com.android.settings.R;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AnimationParticle {
    public int mAnimationState;
    public final int mAssignedColor;
    public final Rect mBounds;
    public float mCurrentAngle;
    public final int mErrorColor;
    public final int mIndex;
    public int mLastAnimationState;
    public final ParticleCollection.AnonymousClass1 mListener;
    public final float mOffsetTimeSec;
    public final Paint mPaint;
    public float mRingAdjustRate;
    public float mRingCompletionTime;
    public float mCurrentSize = 10.0f;
    public float mRotationSpeed = 0.8f;
    public float mSweepAngle = 0.0f;
    public float mSweepRate = 240.0f;
    public final int mBorderWidth = 20;
    public final ArgbEvaluator mEvaluator = new ArgbEvaluator();

    public AnimationParticle(
            Context context,
            ParticleCollection.AnonymousClass1 anonymousClass1,
            Rect rect,
            int i,
            List list) {
        this.mBounds = rect;
        this.mErrorColor =
                context.getResources()
                        .getColor(R.color.face_anim_particle_error, context.getTheme());
        this.mIndex = i;
        this.mListener = anonymousClass1;
        float f = i / 12;
        this.mCurrentAngle = f * 2.0f * 3.1415927f;
        this.mOffsetTimeSec = f * 1.25f * 2.0f * 3.1415927f;
        Paint paint = new Paint();
        this.mPaint = paint;
        ArrayList arrayList = (ArrayList) list;
        int intValue = ((Integer) arrayList.get(i % arrayList.size())).intValue();
        this.mAssignedColor = intValue;
        paint.setColor(intValue);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(this.mCurrentSize);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }
}
