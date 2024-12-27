package com.samsung.android.settings.navigationbar;

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

import androidx.fragment.app.FragmentActivity;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class NavigationBarBackGestureIndicatorDrawable extends Drawable {
    public final Context mContext;
    public float mCurrentHeight;
    public float mCurrentWidth;
    public final int mDrawLocation;
    public float mFinalHeight;
    public float mFinalWidth;
    public final AnonymousClass1 mHandler;
    public float mHeightChangePerMs;
    public final Paint mPaint = new Paint();
    public final TimeAnimator mTimeAnimator;
    public float mWidthChangePerMs;

    /* JADX WARN: Type inference failed for: r1v0, types: [com.samsung.android.settings.navigationbar.NavigationBarBackGestureIndicatorDrawable$1] */
    public NavigationBarBackGestureIndicatorDrawable(FragmentActivity fragmentActivity, int i) {
        TimeAnimator timeAnimator = new TimeAnimator();
        this.mTimeAnimator = timeAnimator;
        this.mHandler =
                new Handler(
                        Looper
                                .getMainLooper()) { // from class:
                                                    // com.samsung.android.settings.navigationbar.NavigationBarBackGestureIndicatorDrawable.1
                    @Override // android.os.Handler
                    public final void handleMessage(Message message) {
                        int i2 = message.what;
                        if (i2 != 1) {
                            if (i2 != 3) {
                                return;
                            }
                            removeMessages(1);
                            sendMessageDelayed(obtainMessage(1, 0, 0), 750L);
                            return;
                        }
                        NavigationBarBackGestureIndicatorDrawable
                                navigationBarBackGestureIndicatorDrawable =
                                        NavigationBarBackGestureIndicatorDrawable.this;
                        navigationBarBackGestureIndicatorDrawable.mTimeAnimator.end();
                        float f = message.arg1;
                        navigationBarBackGestureIndicatorDrawable.mFinalWidth = f;
                        navigationBarBackGestureIndicatorDrawable.mWidthChangePerMs =
                                Math.abs(
                                                navigationBarBackGestureIndicatorDrawable
                                                                .mCurrentWidth
                                                        - f)
                                        / 200.0f;
                        float f2 = message.arg2;
                        navigationBarBackGestureIndicatorDrawable.mFinalHeight = f2;
                        navigationBarBackGestureIndicatorDrawable.mHeightChangePerMs =
                                Math.abs(
                                                navigationBarBackGestureIndicatorDrawable
                                                                .mCurrentHeight
                                                        - f2)
                                        / 200.0f;
                        navigationBarBackGestureIndicatorDrawable.mTimeAnimator.start();
                    }
                };
        this.mContext = fragmentActivity;
        this.mDrawLocation = i;
        timeAnimator.setTimeListener(
                new TimeAnimator
                        .TimeListener() { // from class:
                                          // com.samsung.android.settings.navigationbar.NavigationBarBackGestureIndicatorDrawable$$ExternalSyntheticLambda0
                    @Override // android.animation.TimeAnimator.TimeListener
                    public final void onTimeUpdate(TimeAnimator timeAnimator2, long j, long j2) {
                        NavigationBarBackGestureIndicatorDrawable
                                navigationBarBackGestureIndicatorDrawable =
                                        NavigationBarBackGestureIndicatorDrawable.this;
                        float f = j2;
                        float f2 = navigationBarBackGestureIndicatorDrawable.mWidthChangePerMs * f;
                        float f3 = f * navigationBarBackGestureIndicatorDrawable.mHeightChangePerMs;
                        if (j >= 200
                                || f2
                                        >= Math.abs(
                                                navigationBarBackGestureIndicatorDrawable
                                                                .mFinalWidth
                                                        - navigationBarBackGestureIndicatorDrawable
                                                                .mCurrentWidth)) {
                            navigationBarBackGestureIndicatorDrawable.mCurrentWidth =
                                    navigationBarBackGestureIndicatorDrawable.mFinalWidth;
                            navigationBarBackGestureIndicatorDrawable.mCurrentHeight =
                                    navigationBarBackGestureIndicatorDrawable.mFinalHeight;
                            navigationBarBackGestureIndicatorDrawable.mTimeAnimator.end();
                        } else {
                            float f4 = navigationBarBackGestureIndicatorDrawable.mCurrentWidth;
                            float f5 =
                                    f4 < navigationBarBackGestureIndicatorDrawable.mFinalWidth
                                            ? 1.0f
                                            : -1.0f;
                            navigationBarBackGestureIndicatorDrawable.mCurrentWidth =
                                    (f2 * f5) + f4;
                            navigationBarBackGestureIndicatorDrawable.mCurrentHeight =
                                    (f5 * f3)
                                            + navigationBarBackGestureIndicatorDrawable
                                                    .mCurrentHeight;
                        }
                        navigationBarBackGestureIndicatorDrawable.invalidateSelf();
                    }
                });
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        this.mPaint.setAntiAlias(true);
        this.mPaint.setColor(
                this.mContext
                        .getResources()
                        .getColor(R.color.samsung_navigationbar_back_gesture_indicator));
        int i = (int) this.mCurrentWidth;
        int i2 = (int) this.mCurrentHeight;
        int height = canvas.getHeight();
        int width = canvas.getWidth();
        Rect rect = new Rect();
        if (this.mDrawLocation == 30) {
            rect.set(0, height - i, width, height);
        } else {
            rect.set(0, 0, i, height - i2);
            if (this.mDrawLocation == 20) {
                rect.offset(width - i, 0);
            }
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

    public final void setWidth(int i, int i2) {
        if (i == 0 && i2 == 0) {
            sendEmptyMessage(3);
        } else {
            AnonymousClass1 anonymousClass1 = this.mHandler;
            anonymousClass1.sendMessage(anonymousClass1.obtainMessage(1, i, i2));
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {}

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {}
}
