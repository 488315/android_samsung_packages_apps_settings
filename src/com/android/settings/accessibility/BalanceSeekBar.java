package com.android.settings.accessibility;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.provider.Settings;
import android.util.AttributeSet;
import android.widget.SeekBar;

import com.android.settings.Utils;

import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BalanceSeekBar extends SeekBar {
    static final float SNAP_TO_PERCENTAGE = 0.03f;
    public int mCenter;
    public final Paint mCenterMarkerPaint;
    public final Rect mCenterMarkerRect;
    public final Context mContext;
    public int mLastProgress;
    public final Object mListenerLock;
    public SeekBar.OnSeekBarChangeListener mOnSeekBarChangeListener;
    public final AnonymousClass1 mProxySeekBarListener;
    public float mSnapThreshold;

    public BalanceSeekBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.seekBarStyle);
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    public final synchronized void onDraw(Canvas canvas) {
        int height = (canvas.getHeight() - getPaddingBottom()) / 2;
        canvas.save();
        canvas.translate(
                ((canvas.getWidth() - this.mCenterMarkerRect.right) - getPaddingEnd()) / 2,
                height - (this.mCenterMarkerRect.bottom / 2));
        canvas.drawRect(this.mCenterMarkerRect, this.mCenterMarkerPaint);
        canvas.restore();
        super.onDraw(canvas);
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar
    public synchronized void setMax(int i) {
        super.setMax(i);
        this.mCenter = i / 2;
        this.mSnapThreshold = i * SNAP_TO_PERCENTAGE;
    }

    @Override // android.widget.SeekBar
    public void setOnSeekBarChangeListener(
            SeekBar.OnSeekBarChangeListener onSeekBarChangeListener) {
        synchronized (this.mListenerLock) {
            this.mOnSeekBarChangeListener = onSeekBarChangeListener;
        }
    }

    public BalanceSeekBar(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public BalanceSeekBar(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mListenerLock = new Object();
        this.mLastProgress = -1;
        SeekBar.OnSeekBarChangeListener onSeekBarChangeListener =
                new SeekBar
                        .OnSeekBarChangeListener() { // from class:
                                                     // com.android.settings.accessibility.BalanceSeekBar.1
                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public final void onProgressChanged(SeekBar seekBar, int i3, boolean z) {
                        if (z) {
                            BalanceSeekBar balanceSeekBar = BalanceSeekBar.this;
                            int i4 = balanceSeekBar.mCenter;
                            if (i3 != i4) {
                                float f = i3;
                                float f2 = balanceSeekBar.mSnapThreshold;
                                if (f > i4 - f2 && f < i4 + f2) {
                                    seekBar.setProgress(i4);
                                    i3 = i4;
                                }
                            }
                            BalanceSeekBar balanceSeekBar2 = BalanceSeekBar.this;
                            if (i3 != balanceSeekBar2.mLastProgress) {
                                if (i3 == balanceSeekBar2.mCenter
                                        || i3 == balanceSeekBar2.getMin()
                                        || i3 == BalanceSeekBar.this.getMax()) {
                                    seekBar.performHapticFeedback(4);
                                }
                                BalanceSeekBar.this.mLastProgress = i3;
                            }
                            Settings.System.putFloatForUser(
                                    BalanceSeekBar.this.mContext.getContentResolver(),
                                    "master_balance",
                                    (i3 - r0.mCenter) * 0.01f,
                                    -2);
                        }
                        synchronized (BalanceSeekBar.this.mListenerLock) {
                            try {
                                SeekBar.OnSeekBarChangeListener onSeekBarChangeListener2 =
                                        BalanceSeekBar.this.mOnSeekBarChangeListener;
                                if (onSeekBarChangeListener2 != null) {
                                    onSeekBarChangeListener2.onProgressChanged(seekBar, i3, z);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public final void onStartTrackingTouch(SeekBar seekBar) {
                        synchronized (BalanceSeekBar.this.mListenerLock) {
                            try {
                                SeekBar.OnSeekBarChangeListener onSeekBarChangeListener2 =
                                        BalanceSeekBar.this.mOnSeekBarChangeListener;
                                if (onSeekBarChangeListener2 != null) {
                                    onSeekBarChangeListener2.onStartTrackingTouch(seekBar);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public final void onStopTrackingTouch(SeekBar seekBar) {
                        synchronized (BalanceSeekBar.this.mListenerLock) {
                            try {
                                SeekBar.OnSeekBarChangeListener onSeekBarChangeListener2 =
                                        BalanceSeekBar.this.mOnSeekBarChangeListener;
                                if (onSeekBarChangeListener2 != null) {
                                    onSeekBarChangeListener2.onStopTrackingTouch(seekBar);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                };
        this.mContext = context;
        Resources resources = getResources();
        this.mCenterMarkerRect =
                new Rect(
                        0,
                        0,
                        resources.getDimensionPixelSize(
                                com.android.settings.R.dimen.balance_seekbar_center_marker_width),
                        resources.getDimensionPixelSize(
                                com.android.settings.R.dimen.balance_seekbar_center_marker_height));
        Paint paint = new Paint();
        this.mCenterMarkerPaint = paint;
        paint.setColor(Utils.isNightMode(context) ? -1 : EmergencyPhoneWidget.BG_COLOR);
        paint.setStyle(Paint.Style.FILL);
        setProgressTintList(ColorStateList.valueOf(0));
        super.setOnSeekBarChangeListener(onSeekBarChangeListener);
    }
}
