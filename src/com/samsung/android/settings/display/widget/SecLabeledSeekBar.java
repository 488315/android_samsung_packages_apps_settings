package com.samsung.android.settings.display.widget;

import android.R;
import android.content.Context;
import android.graphics.Rect;
import android.os.Debug;
import android.util.AttributeSet;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityEvent;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecLabeledSeekBar extends SeekBar {
    public final LabeledSeekBarExploreByTouchHelper mAccessHelper;
    public String[] mLabels;
    public int mLastProgress;
    public SeekBar.OnSeekBarChangeListener mOnSeekBarChangeListener;
    public final AnonymousClass1 mProxySeekBarListener;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class LabeledSeekBarExploreByTouchHelper extends ExploreByTouchHelper {
        public final boolean mIsLayoutRtl;

        public LabeledSeekBarExploreByTouchHelper(SecLabeledSeekBar secLabeledSeekBar) {
            super(secLabeledSeekBar);
            this.mIsLayoutRtl =
                    secLabeledSeekBar.getResources().getConfiguration().getLayoutDirection() == 1;
        }

        public final int getHalfVirtualViewWidth$1() {
            SecLabeledSeekBar secLabeledSeekBar = SecLabeledSeekBar.this;
            return Math.max(
                    0,
                    ((secLabeledSeekBar.getWidth() - secLabeledSeekBar.getPaddingStart())
                                    - secLabeledSeekBar.getPaddingEnd())
                            / (secLabeledSeekBar.getMax() * 2));
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final int getVirtualViewAt(float f, float f2) {
            SecLabeledSeekBar secLabeledSeekBar = SecLabeledSeekBar.this;
            int min =
                    Math.min(
                            (Math.max(
                                                    0,
                                                    (((int) f)
                                                                    - secLabeledSeekBar
                                                                            .getPaddingStart())
                                                            / getHalfVirtualViewWidth$1())
                                            + 1)
                                    / 2,
                            secLabeledSeekBar.getMax());
            return this.mIsLayoutRtl ? secLabeledSeekBar.getMax() - min : min;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final void getVisibleVirtualViews(List list) {
            int max = SecLabeledSeekBar.this.getMax();
            for (int i = 0; i <= max; i++) {
                ((ArrayList) list).add(Integer.valueOf(i));
            }
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final boolean onPerformActionForVirtualView(int i, int i2) {
            if (i == -1 || i2 != 16) {
                return false;
            }
            SecLabeledSeekBar.this.setProgress(i);
            sendEventForVirtualView(i, 1);
            return true;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final void onPopulateEventForHost(AccessibilityEvent accessibilityEvent) {
            accessibilityEvent.setClassName(RadioGroup.class.getName());
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final void onPopulateEventForVirtualView(
                int i, AccessibilityEvent accessibilityEvent) {
            accessibilityEvent.setClassName(SeekBar.class.getName());
            accessibilityEvent.setContentDescription(Integer.toString(i));
            accessibilityEvent.setChecked(i == SecLabeledSeekBar.this.getProgress());
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final void onPopulateNodeForHost(
                AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            accessibilityNodeInfoCompat.setClassName(RadioGroup.class.getName());
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final void onPopulateNodeForVirtualView(
                int i, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            accessibilityNodeInfoCompat.setClassName(SeekBar.class.getName());
            boolean z = this.mIsLayoutRtl;
            SecLabeledSeekBar secLabeledSeekBar = SecLabeledSeekBar.this;
            int max = z ? secLabeledSeekBar.getMax() - i : i;
            int i2 = max * 2;
            int paddingStart =
                    secLabeledSeekBar.getPaddingStart() + (getHalfVirtualViewWidth$1() * (i2 - 1));
            int paddingStart2 =
                    secLabeledSeekBar.getPaddingStart() + (getHalfVirtualViewWidth$1() * (i2 + 1));
            if (max == 0) {
                paddingStart = 0;
            }
            if (max == secLabeledSeekBar.getMax()) {
                paddingStart2 = secLabeledSeekBar.getWidth();
            }
            Rect rect = new Rect();
            rect.set(paddingStart, 0, paddingStart2, secLabeledSeekBar.getHeight());
            accessibilityNodeInfoCompat.setBoundsInParent(rect);
            accessibilityNodeInfoCompat.addAction(16);
            accessibilityNodeInfoCompat.setContentDescription(Integer.toString(i));
            accessibilityNodeInfoCompat.setClickable(true);
            accessibilityNodeInfoCompat.setCheckable(true);
            accessibilityNodeInfoCompat.setChecked(i == secLabeledSeekBar.getProgress());
        }
    }

    public SecLabeledSeekBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.seekBarStyle);
    }

    @Override // android.view.View
    public final boolean dispatchHoverEvent(MotionEvent motionEvent) {
        return this.mAccessHelper.dispatchHoverEvent(motionEvent)
                || super.dispatchHoverEvent(motionEvent);
    }

    @Override // android.widget.SeekBar
    public final void setOnSeekBarChangeListener(
            SeekBar.OnSeekBarChangeListener onSeekBarChangeListener) {
        this.mOnSeekBarChangeListener = onSeekBarChangeListener;
        Log.i(
                "ScreenZoomEvent",
                "setOnSeekBarChangeListener: l="
                        + onSeekBarChangeListener
                        + " callers="
                        + Debug.getCallers(7));
    }

    @Override // android.widget.ProgressBar
    public final synchronized void setProgress(int i) {
        try {
            LabeledSeekBarExploreByTouchHelper labeledSeekBarExploreByTouchHelper =
                    this.mAccessHelper;
            if (labeledSeekBarExploreByTouchHelper != null) {
                labeledSeekBarExploreByTouchHelper.invalidateRoot();
            }
            super.setProgress(i);
        } catch (Throwable th) {
            throw th;
        }
    }

    public SecLabeledSeekBar(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SecLabeledSeekBar(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mLastProgress = -1;
        SeekBar.OnSeekBarChangeListener onSeekBarChangeListener =
                new SeekBar
                        .OnSeekBarChangeListener() { // from class:
                                                     // com.samsung.android.settings.display.widget.SecLabeledSeekBar.1
                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public final void onProgressChanged(SeekBar seekBar, int i3, boolean z) {
                        Log.i(
                                "ScreenZoomEvent",
                                "SecLabeledSeekBar.onProgressChanged: progress="
                                        + i3
                                        + " fromUser="
                                        + z);
                        Log.i("ScreenZoomEvent", "listener >>>");
                        SeekBar.OnSeekBarChangeListener onSeekBarChangeListener2 =
                                SecLabeledSeekBar.this.mOnSeekBarChangeListener;
                        if (onSeekBarChangeListener2 != null) {
                            onSeekBarChangeListener2.onProgressChanged(seekBar, i3, z);
                            SecLabeledSeekBar secLabeledSeekBar = SecLabeledSeekBar.this;
                            secLabeledSeekBar.mAccessHelper.invalidateRoot();
                            secLabeledSeekBar.mAccessHelper.sendEventForVirtualView(i3, 1);
                        } else {
                            Log.i("ScreenZoomEvent", "listener=null");
                        }
                        Log.i("ScreenZoomEvent", "listener <<<");
                        if (i3 != SecLabeledSeekBar.this.mLastProgress) {
                            if (z) {
                                seekBar.performHapticFeedback(
                                        HapticFeedbackConstants.semGetVibrationIndex(41));
                            }
                            SecLabeledSeekBar.this.mLastProgress = i3;
                            MainClearConfirm$$ExternalSyntheticOutline0.m(
                                    i3, "onProgressChanged: update progress=", "ScreenZoomEvent");
                        }
                    }

                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public final void onStartTrackingTouch(SeekBar seekBar) {
                        Log.i(
                                "ScreenZoomEvent",
                                "SecLabeledSeekBar.onStartTrackingTouch: progress="
                                        + SecLabeledSeekBar.this.mLastProgress);
                        Log.i("ScreenZoomEvent", "listener >>>");
                        SeekBar.OnSeekBarChangeListener onSeekBarChangeListener2 =
                                SecLabeledSeekBar.this.mOnSeekBarChangeListener;
                        if (onSeekBarChangeListener2 != null) {
                            onSeekBarChangeListener2.onStartTrackingTouch(seekBar);
                        } else {
                            Log.i("ScreenZoomEvent", "listener=null");
                        }
                        Log.i("ScreenZoomEvent", "listener <<<");
                    }

                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public final void onStopTrackingTouch(SeekBar seekBar) {
                        Log.i(
                                "ScreenZoomEvent",
                                "SecLabeledSeekBar.onStopTrackingTouch: progress="
                                        + SecLabeledSeekBar.this.mLastProgress);
                        Log.i("ScreenZoomEvent", "listener >>>");
                        SeekBar.OnSeekBarChangeListener onSeekBarChangeListener2 =
                                SecLabeledSeekBar.this.mOnSeekBarChangeListener;
                        if (onSeekBarChangeListener2 != null) {
                            onSeekBarChangeListener2.onStopTrackingTouch(seekBar);
                        } else {
                            Log.i("ScreenZoomEvent", "listener=null");
                        }
                        Log.i("ScreenZoomEvent", "listener <<<");
                    }
                };
        LabeledSeekBarExploreByTouchHelper labeledSeekBarExploreByTouchHelper =
                new LabeledSeekBarExploreByTouchHelper(this);
        this.mAccessHelper = labeledSeekBarExploreByTouchHelper;
        ViewCompat.setAccessibilityDelegate(this, labeledSeekBarExploreByTouchHelper);
        super.setOnSeekBarChangeListener(onSeekBarChangeListener);
    }
}
