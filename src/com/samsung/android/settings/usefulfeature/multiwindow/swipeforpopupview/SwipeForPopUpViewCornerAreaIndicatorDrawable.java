package com.samsung.android.settings.usefulfeature.multiwindow.swipeforpopupview;

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
/* loaded from: classes3.dex */
public final class SwipeForPopUpViewCornerAreaIndicatorDrawable extends Drawable {
    public float mCurrentHeight;
    public float mCurrentWidth;
    public final int mDrawLocation;
    public float mFinalHeight;
    public float mFinalWidth;
    public final AnonymousClass1 mHandler;
    public float mHeightChangePerMs;
    public final int mIndicatorColor;
    public final Paint mPaint = new Paint();
    public final TimeAnimator mTimeAnimator;
    public float mWidthChangePerMs;

    /* JADX WARN: Type inference failed for: r1v0, types: [com.samsung.android.settings.usefulfeature.multiwindow.swipeforpopupview.SwipeForPopUpViewCornerAreaIndicatorDrawable$1] */
    public SwipeForPopUpViewCornerAreaIndicatorDrawable(Context context, int i) {
        TimeAnimator timeAnimator = new TimeAnimator();
        this.mTimeAnimator = timeAnimator;
        this.mHandler =
                new Handler(
                        Looper
                                .getMainLooper()) { // from class:
                                                    // com.samsung.android.settings.usefulfeature.multiwindow.swipeforpopupview.SwipeForPopUpViewCornerAreaIndicatorDrawable.1
                    @Override // android.os.Handler
                    public final void handleMessage(Message message) {
                        int i2 = message.what;
                        if (i2 != 1) {
                            if (i2 != 3) {
                                return;
                            }
                            removeMessages(1);
                            sendMessageDelayed(obtainMessage(1, 0), 750L);
                            return;
                        }
                        SwipeForPopUpViewCornerAreaIndicatorDrawable
                                swipeForPopUpViewCornerAreaIndicatorDrawable =
                                        SwipeForPopUpViewCornerAreaIndicatorDrawable.this;
                        swipeForPopUpViewCornerAreaIndicatorDrawable.mTimeAnimator.end();
                        swipeForPopUpViewCornerAreaIndicatorDrawable.mFinalWidth =
                                ((Integer) message.obj).intValue();
                        swipeForPopUpViewCornerAreaIndicatorDrawable.mWidthChangePerMs =
                                Math.abs(
                                                swipeForPopUpViewCornerAreaIndicatorDrawable
                                                                .mCurrentWidth
                                                        - swipeForPopUpViewCornerAreaIndicatorDrawable
                                                                .mFinalWidth)
                                        / 200.0f;
                        swipeForPopUpViewCornerAreaIndicatorDrawable.mFinalHeight =
                                ((Integer) message.obj).intValue();
                        swipeForPopUpViewCornerAreaIndicatorDrawable.mHeightChangePerMs =
                                Math.abs(
                                                swipeForPopUpViewCornerAreaIndicatorDrawable
                                                                .mCurrentHeight
                                                        - swipeForPopUpViewCornerAreaIndicatorDrawable
                                                                .mFinalHeight)
                                        / 200.0f;
                        swipeForPopUpViewCornerAreaIndicatorDrawable.mTimeAnimator.start();
                    }
                };
        this.mDrawLocation = i;
        this.mIndicatorColor =
                context.getResources()
                        .getColor(R.color.sec_swipe_for_popup_view_corner_area_indicator_color);
        timeAnimator.setTimeListener(
                new TimeAnimator
                        .TimeListener() { // from class:
                                          // com.samsung.android.settings.usefulfeature.multiwindow.swipeforpopupview.SwipeForPopUpViewCornerAreaIndicatorDrawable$$ExternalSyntheticLambda0
                    @Override // android.animation.TimeAnimator.TimeListener
                    public final void onTimeUpdate(TimeAnimator timeAnimator2, long j, long j2) {
                        SwipeForPopUpViewCornerAreaIndicatorDrawable
                                swipeForPopUpViewCornerAreaIndicatorDrawable =
                                        SwipeForPopUpViewCornerAreaIndicatorDrawable.this;
                        float f = j2;
                        float f2 =
                                swipeForPopUpViewCornerAreaIndicatorDrawable.mWidthChangePerMs * f;
                        float f3 =
                                f * swipeForPopUpViewCornerAreaIndicatorDrawable.mHeightChangePerMs;
                        if (j >= 200
                                || f2
                                        >= Math.abs(
                                                swipeForPopUpViewCornerAreaIndicatorDrawable
                                                                .mFinalWidth
                                                        - swipeForPopUpViewCornerAreaIndicatorDrawable
                                                                .mCurrentWidth)) {
                            swipeForPopUpViewCornerAreaIndicatorDrawable.mCurrentWidth =
                                    swipeForPopUpViewCornerAreaIndicatorDrawable.mFinalWidth;
                            swipeForPopUpViewCornerAreaIndicatorDrawable.mCurrentHeight =
                                    swipeForPopUpViewCornerAreaIndicatorDrawable.mFinalHeight;
                            swipeForPopUpViewCornerAreaIndicatorDrawable.mTimeAnimator.end();
                        } else {
                            float f4 = swipeForPopUpViewCornerAreaIndicatorDrawable.mCurrentWidth;
                            float f5 =
                                    f4 < swipeForPopUpViewCornerAreaIndicatorDrawable.mFinalWidth
                                            ? 1.0f
                                            : -1.0f;
                            swipeForPopUpViewCornerAreaIndicatorDrawable.mCurrentWidth =
                                    (f2 * f5) + f4;
                            swipeForPopUpViewCornerAreaIndicatorDrawable.mCurrentHeight =
                                    (f5 * f3)
                                            + swipeForPopUpViewCornerAreaIndicatorDrawable
                                                    .mCurrentHeight;
                        }
                        swipeForPopUpViewCornerAreaIndicatorDrawable.invalidateSelf();
                    }
                });
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        this.mPaint.setAntiAlias(true);
        this.mPaint.setColor(this.mIndicatorColor);
        int i = (int) this.mCurrentWidth;
        int i2 = (int) this.mCurrentHeight;
        int width = canvas.getWidth();
        Rect rect = new Rect();
        rect.set(0, 0, i, i2);
        if (this.mDrawLocation == 20) {
            rect.offset(width - i, 0);
        }
        canvas.drawRect(rect, this.mPaint);
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return 0;
    }

    @VisibleForTesting
    public int getSize() {
        return (int) this.mFinalWidth;
    }

    public final void setSize(int i) {
        if (i == 0) {
            sendEmptyMessage(3);
        } else {
            AnonymousClass1 anonymousClass1 = this.mHandler;
            anonymousClass1.sendMessage(anonymousClass1.obtainMessage(1, Integer.valueOf(i)));
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {}

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {}
}
