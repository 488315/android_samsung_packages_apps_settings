package com.android.settings.gestures;

import android.animation.TimeAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BackGestureIndicatorDrawable extends Drawable {
    public final Context mContext;
    public float mCurrentWidth;
    public float mFinalWidth;
    public final AnonymousClass1 mHandler;
    public final Paint mPaint = new Paint();
    public final boolean mReversed;
    public final TimeAnimator mTimeAnimator;
    public float mWidthChangePerMs;

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.settings.gestures.BackGestureIndicatorDrawable$1] */
    public BackGestureIndicatorDrawable(Context context, boolean z) {
        TimeAnimator timeAnimator = new TimeAnimator();
        this.mTimeAnimator = timeAnimator;
        this.mHandler =
                new Handler(
                        Looper
                                .getMainLooper()) { // from class:
                                                    // com.android.settings.gestures.BackGestureIndicatorDrawable.1
                    @Override // android.os.Handler
                    public final void handleMessage(Message message) {
                        int i = message.what;
                        BackGestureIndicatorDrawable backGestureIndicatorDrawable =
                                BackGestureIndicatorDrawable.this;
                        if (i == 1) {
                            backGestureIndicatorDrawable.mTimeAnimator.end();
                            float f = message.arg1;
                            backGestureIndicatorDrawable.mFinalWidth = f;
                            backGestureIndicatorDrawable.mWidthChangePerMs =
                                    Math.abs(backGestureIndicatorDrawable.mCurrentWidth - f)
                                            / 200.0f;
                            backGestureIndicatorDrawable.mTimeAnimator.start();
                            return;
                        }
                        if (i != 3) {
                            return;
                        }
                        backGestureIndicatorDrawable.mCurrentWidth =
                                backGestureIndicatorDrawable.mFinalWidth;
                        removeMessages(1);
                        sendMessageDelayed(obtainMessage(1, 0, 0), 700L);
                        backGestureIndicatorDrawable.invalidateSelf();
                    }
                };
        this.mContext = context;
        this.mReversed = z;
        timeAnimator.setTimeListener(
                new TimeAnimator
                        .TimeListener() { // from class:
                                          // com.android.settings.gestures.BackGestureIndicatorDrawable$$ExternalSyntheticLambda0
                    @Override // android.animation.TimeAnimator.TimeListener
                    public final void onTimeUpdate(TimeAnimator timeAnimator2, long j, long j2) {
                        BackGestureIndicatorDrawable backGestureIndicatorDrawable =
                                BackGestureIndicatorDrawable.this;
                        synchronized (backGestureIndicatorDrawable.mTimeAnimator) {
                            try {
                                float f = j2 * backGestureIndicatorDrawable.mWidthChangePerMs;
                                if (j < 200
                                        && f
                                                < Math.abs(
                                                        backGestureIndicatorDrawable.mFinalWidth
                                                                - backGestureIndicatorDrawable
                                                                        .mCurrentWidth)) {
                                    float f2 = backGestureIndicatorDrawable.mCurrentWidth;
                                    backGestureIndicatorDrawable.mCurrentWidth =
                                            ((f2 < backGestureIndicatorDrawable.mFinalWidth
                                                                    ? 1.0f
                                                                    : -1.0f)
                                                            * f)
                                                    + f2;
                                }
                                backGestureIndicatorDrawable.mCurrentWidth =
                                        backGestureIndicatorDrawable.mFinalWidth;
                                backGestureIndicatorDrawable.mTimeAnimator.end();
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        backGestureIndicatorDrawable.invalidateSelf();
                    }
                });
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        this.mPaint.setAntiAlias(true);
        this.mPaint.setColor(this.mContext.getResources().getColor(R.color.back_gesture_indicator));
        this.mPaint.setAlpha(64);
        int height = canvas.getHeight();
        int i = (int) this.mCurrentWidth;
        Rect rect = new Rect(0, 0, i, height);
        if (this.mReversed) {
            rect.offset(canvas.getWidth() - i, 0);
        }
        canvas.drawRect(rect, this.mPaint);
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return 0;
    }

    @VisibleForTesting
    public int getWidth() {
        return (int) this.mFinalWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {}

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {}
}
