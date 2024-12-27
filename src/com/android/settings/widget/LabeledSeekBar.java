package com.android.settings.widget;

import android.R;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class LabeledSeekBar extends SeekBar {
    public final LabeledSeekBarExploreByTouchHelper mAccessHelper;
    public String[] mLabels;
    public int mLastProgress;
    public SeekBar.OnSeekBarChangeListener mOnSeekBarChangeListener;
    public final AnonymousClass1 mProxySeekBarListener;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class LabeledSeekBarExploreByTouchHelper extends ExploreByTouchHelper {
        public final boolean mIsLayoutRtl;

        public LabeledSeekBarExploreByTouchHelper(LabeledSeekBar labeledSeekBar) {
            super(labeledSeekBar);
            this.mIsLayoutRtl =
                    labeledSeekBar.getResources().getConfiguration().getLayoutDirection() == 1;
        }

        public final int getHalfVirtualViewWidth() {
            LabeledSeekBar labeledSeekBar = LabeledSeekBar.this;
            return Math.max(
                    0,
                    ((labeledSeekBar.getWidth() - labeledSeekBar.getPaddingStart())
                                    - labeledSeekBar.getPaddingEnd())
                            / (labeledSeekBar.getMax() * 2));
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final int getVirtualViewAt(float f, float f2) {
            LabeledSeekBar labeledSeekBar = LabeledSeekBar.this;
            int min =
                    Math.min(
                            (Math.max(
                                                    0,
                                                    (((int) f) - labeledSeekBar.getPaddingStart())
                                                            / getHalfVirtualViewWidth())
                                            + 1)
                                    / 2,
                            labeledSeekBar.getMax());
            return this.mIsLayoutRtl ? labeledSeekBar.getMax() - min : min;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final void getVisibleVirtualViews(List list) {
            int max = LabeledSeekBar.this.getMax();
            for (int i = 0; i <= max; i++) {
                ((ArrayList) list).add(Integer.valueOf(i));
            }
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final boolean onPerformActionForVirtualView(int i, int i2) {
            if (i == -1 || i2 != 16) {
                return false;
            }
            LabeledSeekBar.this.setProgress(i);
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
            accessibilityEvent.setClassName(RadioButton.class.getName());
            LabeledSeekBar labeledSeekBar = LabeledSeekBar.this;
            accessibilityEvent.setContentDescription(labeledSeekBar.mLabels[i]);
            accessibilityEvent.setChecked(i == labeledSeekBar.getProgress());
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final void onPopulateNodeForHost(
                AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            accessibilityNodeInfoCompat.setClassName(RadioGroup.class.getName());
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final void onPopulateNodeForVirtualView(
                int i, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            accessibilityNodeInfoCompat.setClassName(RadioButton.class.getName());
            boolean z = this.mIsLayoutRtl;
            LabeledSeekBar labeledSeekBar = LabeledSeekBar.this;
            int max = z ? labeledSeekBar.getMax() - i : i;
            int i2 = max * 2;
            int paddingStart =
                    labeledSeekBar.getPaddingStart() + (getHalfVirtualViewWidth() * (i2 - 1));
            int paddingStart2 =
                    labeledSeekBar.getPaddingStart() + (getHalfVirtualViewWidth() * (i2 + 1));
            if (max == 0) {
                paddingStart = 0;
            }
            if (max == labeledSeekBar.getMax()) {
                paddingStart2 = labeledSeekBar.getWidth();
            }
            Rect rect = new Rect();
            rect.set(paddingStart, 0, paddingStart2, labeledSeekBar.getHeight());
            accessibilityNodeInfoCompat.setBoundsInParent(rect);
            accessibilityNodeInfoCompat.addAction(16);
            accessibilityNodeInfoCompat.setContentDescription(labeledSeekBar.mLabels[i]);
            accessibilityNodeInfoCompat.setClickable(true);
            accessibilityNodeInfoCompat.setCheckable(true);
            accessibilityNodeInfoCompat.setChecked(i == labeledSeekBar.getProgress());
        }
    }

    public LabeledSeekBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.seekBarStyle);
    }

    @Override // android.view.View
    public final boolean dispatchHoverEvent(MotionEvent motionEvent) {
        return this.mAccessHelper.dispatchHoverEvent(motionEvent)
                || super.dispatchHoverEvent(motionEvent);
    }

    public void setLabels(String[] strArr) {
        this.mLabels = strArr;
    }

    @Override // android.widget.SeekBar
    public void setOnSeekBarChangeListener(
            SeekBar.OnSeekBarChangeListener onSeekBarChangeListener) {
        this.mOnSeekBarChangeListener = onSeekBarChangeListener;
    }

    @Override // android.widget.ProgressBar
    public synchronized void setProgress(int i) {
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

    public LabeledSeekBar(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public LabeledSeekBar(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mLastProgress = -1;
        SeekBar.OnSeekBarChangeListener onSeekBarChangeListener =
                new SeekBar
                        .OnSeekBarChangeListener() { // from class:
                                                     // com.android.settings.widget.LabeledSeekBar.1
                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public final void onProgressChanged(SeekBar seekBar, int i3, boolean z) {
                        SeekBar.OnSeekBarChangeListener onSeekBarChangeListener2 =
                                LabeledSeekBar.this.mOnSeekBarChangeListener;
                        if (onSeekBarChangeListener2 != null) {
                            onSeekBarChangeListener2.onProgressChanged(seekBar, i3, z);
                            LabeledSeekBar labeledSeekBar = LabeledSeekBar.this;
                            labeledSeekBar.mAccessHelper.invalidateRoot();
                            labeledSeekBar.mAccessHelper.sendEventForVirtualView(i3, 1);
                        }
                        if (i3 != LabeledSeekBar.this.mLastProgress) {
                            seekBar.performHapticFeedback(4);
                            LabeledSeekBar.this.mLastProgress = i3;
                        }
                    }

                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public final void onStartTrackingTouch(SeekBar seekBar) {
                        SeekBar.OnSeekBarChangeListener onSeekBarChangeListener2 =
                                LabeledSeekBar.this.mOnSeekBarChangeListener;
                        if (onSeekBarChangeListener2 != null) {
                            onSeekBarChangeListener2.onStartTrackingTouch(seekBar);
                        }
                    }

                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public final void onStopTrackingTouch(SeekBar seekBar) {
                        SeekBar.OnSeekBarChangeListener onSeekBarChangeListener2 =
                                LabeledSeekBar.this.mOnSeekBarChangeListener;
                        if (onSeekBarChangeListener2 != null) {
                            onSeekBarChangeListener2.onStopTrackingTouch(seekBar);
                        }
                    }
                };
        LabeledSeekBarExploreByTouchHelper labeledSeekBarExploreByTouchHelper =
                new LabeledSeekBarExploreByTouchHelper(this);
        this.mAccessHelper = labeledSeekBarExploreByTouchHelper;
        ViewCompat.setAccessibilityDelegate(this, labeledSeekBarExploreByTouchHelper);
        super.setOnSeekBarChangeListener(onSeekBarChangeListener);
    }
}
