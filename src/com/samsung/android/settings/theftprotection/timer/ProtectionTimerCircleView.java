package com.samsung.android.settings.theftprotection.timer;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.settings.R;

import com.samsung.android.settings.theftprotection.TheftProtectionConstants;
import com.samsung.android.settings.theftprotection.logging.Log;

import java.lang.ref.WeakReference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ProtectionTimerCircleView extends ImageView {
    public static long sDrawInterval;
    public static long sInputMillis;
    public static int sMinute;
    public static long sRemainMillis;
    public static int sSecond;
    public final int mAlpha;
    public float mCenterX;
    public float mCenterY;
    public Paint mCircleBGPaint;
    public int mCircleBackgroundColor;
    public int mCircleDrawColor;
    public int mCircleOnGoingColor;
    public Paint mCirclePaint;
    public float mCircleRadius;
    public Paint mEndEffectPaint;
    public final NoLeakHandler mHandler;
    public Paint mPointerPaint;
    public Paint mStartEffectPaint;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class NoLeakHandler extends Handler {
        public final WeakReference mView;

        public NoLeakHandler(ProtectionTimerCircleView protectionTimerCircleView) {
            this.mView = new WeakReference(protectionTimerCircleView);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            ProtectionTimerCircleView protectionTimerCircleView =
                    (ProtectionTimerCircleView) this.mView.get();
            if (protectionTimerCircleView != null) {
                protectionTimerCircleView.invalidate();
                sendEmptyMessageDelayed(0, ProtectionTimerCircleView.sDrawInterval);
            }
        }
    }

    public ProtectionTimerCircleView(Context context) {
        super(context);
        this.mCirclePaint = null;
        this.mStartEffectPaint = null;
        this.mEndEffectPaint = null;
        this.mCircleBGPaint = null;
        this.mPointerPaint = null;
        this.mHandler = new NoLeakHandler(this);
        this.mAlpha = 255;
    }

    public static void updateTime(long j, long j2) {
        long j3 = 800 + j2;
        long j4 = TheftProtectionConstants.MINUTE_MILLIS;
        sMinute = (int) (j3 / j4);
        sSecond = (int) ((j3 % j4) / TheftProtectionConstants.SECOND_MILLIS);
        sInputMillis = j - 180;
        sRemainMillis = j2 < 180 ? 0L : j2 - 180;
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Log.d("ProtectionTimerCircleView", "onConfigurationChanged() newConfig = " + configuration);
        setCircleSize();
        setCircleStrokeWidth();
        invalidate();
    }

    @Override // android.widget.ImageView, android.view.View
    public final void onDraw(Canvas canvas) {
        int saveLayer =
                canvas.saveLayer(0.0f, 0.0f, this.mCenterX * 2.0f, this.mCenterY * 2.0f, null);
        if (sMinute == 0 && sSecond == 0) {
            this.mCircleDrawColor = this.mCircleBackgroundColor;
        } else {
            this.mCircleDrawColor = this.mCircleOnGoingColor;
        }
        this.mCircleBGPaint.setAlpha(this.mAlpha);
        canvas.drawCircle(this.mCenterX, this.mCenterY, this.mCircleRadius, this.mCircleBGPaint);
        this.mCirclePaint.setAlpha(this.mAlpha);
        this.mCirclePaint.setColor(this.mCircleDrawColor);
        float f = this.mCenterX;
        float f2 = this.mCircleRadius;
        float f3 = this.mCenterY;
        canvas.drawArc(
                f - f2,
                f3 - f2,
                f + f2,
                f3 + f2,
                -90.0f,
                (sRemainMillis / sInputMillis) * 360.0f,
                false,
                this.mCirclePaint);
        canvas.restoreToCount(saveLayer);
    }

    public final void setCircleSize() {
        Log.d("ProtectionTimerCircleView", "setCircleSize()");
        int dimensionPixelSize =
                getResources().getDimensionPixelSize(R.dimen.protection_timer_circle_stroke_width);
        this.mCenterX =
                getResources().getDimension(R.dimen.protection_timer_circle_bg_width) / 2.0f;
        this.mCenterY =
                getResources().getDimension(R.dimen.protection_timer_circle_bg_height) / 2.0f;
        this.mCircleRadius = getResources().getDimension(R.dimen.protection_timer_circle_bg_radius);
        Paint paint = this.mCirclePaint;
        if (paint != null) {
            paint.setStrokeWidth(dimensionPixelSize);
        }
        Paint paint2 = this.mStartEffectPaint;
        if (paint2 != null) {
            paint2.setStrokeWidth(dimensionPixelSize);
        }
        Paint paint3 = this.mEndEffectPaint;
        if (paint3 != null) {
            paint3.setStrokeWidth(dimensionPixelSize);
        }
        Paint paint4 = this.mCircleBGPaint;
        if (paint4 != null) {
            paint4.setStrokeWidth(dimensionPixelSize);
        }
        Log.d("ProtectionTimerCircleView", "updateLayoutForRotate()");
        Resources resources = getResources();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) getLayoutParams();
        layoutParams.width = (int) resources.getDimension(R.dimen.protection_timer_circle_bg_width);
        layoutParams.height =
                (int) resources.getDimension(R.dimen.protection_timer_circle_bg_height);
        setLayoutParams(layoutParams);
    }

    public final void setCircleStrokeWidth() {
        int dimensionPixelSize =
                getResources().getDimensionPixelSize(R.dimen.protection_timer_circle_stroke_width);
        Paint paint = this.mCirclePaint;
        if (paint != null) {
            paint.setStrokeWidth(dimensionPixelSize);
        }
        Paint paint2 = this.mStartEffectPaint;
        if (paint2 != null) {
            paint2.setStrokeWidth(dimensionPixelSize);
        }
        Paint paint3 = this.mEndEffectPaint;
        if (paint3 != null) {
            paint3.setStrokeWidth(dimensionPixelSize);
        }
        Paint paint4 = this.mCircleBGPaint;
        if (paint4 != null) {
            paint4.setStrokeWidth(dimensionPixelSize);
        }
    }

    public ProtectionTimerCircleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCirclePaint = null;
        this.mStartEffectPaint = null;
        this.mEndEffectPaint = null;
        this.mCircleBGPaint = null;
        this.mPointerPaint = null;
        this.mHandler = new NoLeakHandler(this);
        this.mAlpha = 255;
    }

    public ProtectionTimerCircleView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mCirclePaint = null;
        this.mStartEffectPaint = null;
        this.mEndEffectPaint = null;
        this.mCircleBGPaint = null;
        this.mPointerPaint = null;
        this.mHandler = new NoLeakHandler(this);
        this.mAlpha = 255;
    }
}
