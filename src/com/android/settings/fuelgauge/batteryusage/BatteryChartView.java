package com.android.settings.fuelgauge.batteryusage;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.fuelgauge.batteryusage.BatteryChartViewModel;
import com.android.settingslib.Utils;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BatteryChartView extends AppCompatImageView implements View.OnClickListener {
    public static final int DIVIDER_COLOR = Color.parseColor("#CDCCC5");
    public BatteryChartAccessibilityNodeProvider mAccessibilityNodeProvider;
    public final List mAxisLabelsBounds;
    public int mDefaultTextColor;
    public final int mDividerHeight;
    public final Paint mDividerPaint;
    public final int mDividerWidth;
    public int mHoveredIndex;
    public final Rect mIndent;
    public final Set mLabelDrawnIndexes;
    public final int mLayoutDirection;
    public OnSelectListener mOnSelectListener;
    public final Rect[] mPercentageBounds;
    public final String[] mPercentages;
    public final int mTextPadding;
    public Paint mTextPaint;
    float mTouchUpEventX;
    public Drawable mTransomIcon;
    public int mTransomIconSize;
    public int mTransomLineDefaultColor;
    public Paint mTransomLinePaint;
    public int mTransomLineSelectedColor;
    public float mTransomPadding;
    public Paint mTransomSelectedSlotPaint;
    public int mTransomTop;
    public final int mTransomViewHeight;
    public final int mTrapezoidColor;
    public final float mTrapezoidHOffset;
    public final int mTrapezoidHoverColor;
    public final Paint mTrapezoidPaint;
    TrapezoidSlot[] mTrapezoidSlots;
    public final int mTrapezoidSolidColor;
    public final float mTrapezoidVOffset;
    public BatteryChartViewModel mViewModel;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class BatteryChartAccessibilityNodeProvider extends AccessibilityNodeProvider {
        public int mAccessibilityFocusNodeViewId = Integer.MIN_VALUE;

        public BatteryChartAccessibilityNodeProvider() {
        }

        @Override // android.view.accessibility.AccessibilityNodeProvider
        public final AccessibilityNodeInfo createAccessibilityNodeInfo(int i) {
            if (i == -1) {
                AccessibilityNodeInfo accessibilityNodeInfo = new AccessibilityNodeInfo(BatteryChartView.this);
                for (int i2 = 0; i2 < BatteryChartView.this.mViewModel.mLevels.size() - 1; i2++) {
                    accessibilityNodeInfo.addChild(BatteryChartView.this, i2);
                }
                return accessibilityNodeInfo;
            }
            if (!BatteryChartView.isTrapezoidIndexValid(BatteryChartView.this.mViewModel, i)) {
                RecordingInputConnection$$ExternalSyntheticOutline0.m(i, "Invalid virtual view id:", "BatteryChartView");
                return null;
            }
            AccessibilityNodeInfo accessibilityNodeInfo2 = new AccessibilityNodeInfo(BatteryChartView.this, i);
            String contentDescription = BatteryChartView.this.mViewModel.getContentDescription(i);
            String slotBatteryLevelText = BatteryChartView.this.mViewModel.getSlotBatteryLevelText(i);
            BatteryChartView.this.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo2);
            accessibilityNodeInfo2.setClickable(BatteryChartView.isValidToDraw(BatteryChartView.this.mViewModel, i));
            accessibilityNodeInfo2.setText(contentDescription);
            accessibilityNodeInfo2.setContentDescription(((ImageView) BatteryChartView.this).mContext.getString(R.string.battery_usage_time_info_and_battery_level, contentDescription, slotBatteryLevelText));
            accessibilityNodeInfo2.setAccessibilityFocused(i == this.mAccessibilityFocusNodeViewId);
            Rect rect = new Rect();
            BatteryChartView.this.getBoundsOnScreen(rect, true);
            float f = rect.left;
            rect.left = Math.round(BatteryChartView.this.mTrapezoidSlots[i].mLeft + f);
            rect.right = Math.round(f + BatteryChartView.this.mTrapezoidSlots[i].mRight);
            accessibilityNodeInfo2.setBoundsInScreen(rect);
            return accessibilityNodeInfo2;
        }

        @Override // android.view.accessibility.AccessibilityNodeProvider
        public final boolean performAction(int i, int i2, Bundle bundle) {
            if (i == -1) {
                return BatteryChartView.this.performAccessibilityAction(i2, bundle);
            }
            if (i2 == 16) {
                BatteryChartView batteryChartView = BatteryChartView.this;
                int i3 = BatteryChartView.DIVIDER_COLOR;
                batteryChartView.onTrapezoidClicked(batteryChartView, i);
                return true;
            }
            if (i2 == 64) {
                this.mAccessibilityFocusNodeViewId = i;
                BatteryChartView batteryChartView2 = BatteryChartView.this;
                int i4 = BatteryChartView.DIVIDER_COLOR;
                return batteryChartView2.sendAccessibilityEvent(i, NetworkAnalyticsConstants.DataPoints.FLAG_UID);
            }
            if (i2 != 128) {
                return BatteryChartView.this.performAccessibilityAction(i2, bundle);
            }
            if (this.mAccessibilityFocusNodeViewId == i) {
                this.mAccessibilityFocusNodeViewId = Integer.MIN_VALUE;
            }
            BatteryChartView batteryChartView3 = BatteryChartView.this;
            int i5 = BatteryChartView.DIVIDER_COLOR;
            return batteryChartView3.sendAccessibilityEvent(i, 65536);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnSelectListener {
        void onSelect(int i);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static final class TrapezoidSlot {
        public float mLeft;
        public float mRight;

        public final String toString() {
            return String.format(Locale.US, "TrapezoidSlot[%f,%f]", Float.valueOf(this.mLeft), Float.valueOf(this.mRight));
        }
    }

    public BatteryChartView(Context context) {
        super(context, null);
        this.mPercentages = getPercentages();
        this.mIndent = new Rect();
        this.mPercentageBounds = new Rect[]{new Rect(), new Rect(), new Rect()};
        this.mAxisLabelsBounds = new ArrayList();
        this.mLabelDrawnIndexes = new ArraySet();
        this.mLayoutDirection = getContext().getResources().getConfiguration().getLayoutDirection();
        this.mHoveredIndex = -2;
        this.mTouchUpEventX = Float.MIN_VALUE;
    }

    private static String[] getPercentages() {
        return new String[]{Utils.formatPercentage(100.0d, true), Utils.formatPercentage(50.0d, true), Utils.formatPercentage(0.0d, true)};
    }

    public static boolean isTrapezoidIndexValid(BatteryChartViewModel batteryChartViewModel, int i) {
        return batteryChartViewModel != null && i >= 0 && i < batteryChartViewModel.mLevels.size() - 1;
    }

    public static boolean isValidToDraw(BatteryChartViewModel batteryChartViewModel, int i) {
        if (isTrapezoidIndexValid(batteryChartViewModel, i) && ((Integer) batteryChartViewModel.mLevels.get(i)).intValue() != -1) {
            if (((Integer) batteryChartViewModel.mLevels.get(i + 1)).intValue() != -1) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x01d5  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x01fd A[SYNTHETIC] */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void draw(android.graphics.Canvas r21) {
        /*
            Method dump skipped, instructions count: 1094
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settings.fuelgauge.batteryusage.BatteryChartView.draw(android.graphics.Canvas):void");
    }

    public final void drawAxisLabelText(Canvas canvas, int i, Rect rect, float f) {
        this.mTextPaint.setColor(this.mTrapezoidSolidColor);
        this.mTextPaint.setTextAlign(Paint.Align.CENTER);
        if (isRTL$1()) {
            i = this.mViewModel.mAxisLabelPosition == BatteryChartViewModel.AxisLabelPosition.BETWEEN_TRAPEZOIDS ? (r0.mLevels.size() - i) - 1 : (r0.mLevels.size() - i) - 2;
        }
        canvas.drawText(this.mViewModel.getText(i), rect.centerX(), f, this.mTextPaint);
        ((ArraySet) this.mLabelDrawnIndexes).add(Integer.valueOf(i));
    }

    public final void drawAxisLabelsBetweenStartIndexAndEndIndex(Canvas canvas, Rect[] rectArr, int i, int i2, float f) {
        int i3 = i2 - i;
        if (i3 <= 1) {
            return;
        }
        if (i3 % 2 == 0) {
            int i4 = (i + i2) / 2;
            if (hasOverlap(rectArr, i, i4) || hasOverlap(rectArr, i4, i2)) {
                return;
            }
            drawAxisLabelText(canvas, i4, rectArr[i4], f);
            ((ArraySet) this.mLabelDrawnIndexes).add(Integer.valueOf(i4));
            drawAxisLabelsBetweenStartIndexAndEndIndex(canvas, rectArr, i, i4, f);
            drawAxisLabelsBetweenStartIndexAndEndIndex(canvas, rectArr, i4, i2, f);
            return;
        }
        int round = Math.round(i3 / 3.0f) + i;
        int round2 = Math.round((i3 * 2) / 3.0f) + i;
        if (hasOverlap(rectArr, i, round) || hasOverlap(rectArr, round, round2) || hasOverlap(rectArr, round2, i2)) {
            return;
        }
        drawAxisLabelText(canvas, round, rectArr[round], f);
        ((ArraySet) this.mLabelDrawnIndexes).add(Integer.valueOf(round));
        drawAxisLabelText(canvas, round2, rectArr[round2], f);
        ((ArraySet) this.mLabelDrawnIndexes).add(Integer.valueOf(round2));
        drawAxisLabelsBetweenStartIndexAndEndIndex(canvas, rectArr, i, round, f);
        drawAxisLabelsBetweenStartIndexAndEndIndex(canvas, rectArr, round, round2, f);
        drawAxisLabelsBetweenStartIndexAndEndIndex(canvas, rectArr, round2, i2, f);
    }

    @Override // android.view.View
    public AccessibilityNodeProvider getAccessibilityNodeProvider() {
        if (this.mViewModel == null) {
            return super.getAccessibilityNodeProvider();
        }
        if (this.mAccessibilityNodeProvider == null) {
            this.mAccessibilityNodeProvider = new BatteryChartAccessibilityNodeProvider();
        }
        return this.mAccessibilityNodeProvider;
    }

    public final Rect[] getAxisLabelDisplayAreas(int i, float f, float f2, float f3, boolean z) {
        Rect[] rectArr = new Rect[i];
        for (int i2 = 0; i2 < i; i2++) {
            float width = ((Rect) ((ArrayList) this.mAxisLabelsBounds).get(i2)).width();
            float f4 = (i2 * f2) + f;
            if (z) {
                if (i2 == 0) {
                    f4 += width * 0.5f;
                }
                if (i2 == i - 1) {
                    f4 -= width * 0.5f;
                }
            }
            float f5 = f4 - (0.5f * width);
            float f6 = ((Rect) ((ArrayList) this.mAxisLabelsBounds).get(i2)).top + f3;
            rectArr[i2] = new Rect(Math.round(f5), Math.round(f6), Math.round(width + f5), Math.round(((Rect) ((ArrayList) this.mAxisLabelsBounds).get(i2)).height() + f6));
        }
        return rectArr;
    }

    public final int getTrapezoidIndex(float f) {
        if (this.mTrapezoidSlots == null) {
            return -2;
        }
        int i = 0;
        while (true) {
            TrapezoidSlot[] trapezoidSlotArr = this.mTrapezoidSlots;
            if (i >= trapezoidSlotArr.length) {
                return -2;
            }
            TrapezoidSlot trapezoidSlot = trapezoidSlotArr[i];
            float f2 = trapezoidSlot.mLeft;
            float f3 = this.mTrapezoidHOffset;
            if (f >= f2 - f3 && f <= trapezoidSlot.mRight + f3) {
                return i;
            }
            i++;
        }
    }

    public final boolean hasOverlap(Rect[] rectArr, int i, int i2) {
        return (((float) this.mTextPadding) * 2.3f) + ((float) rectArr[i].right) > ((float) rectArr[i2].left);
    }

    public final boolean isRTL$1() {
        return this.mLayoutDirection == 1;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        float f = this.mTouchUpEventX;
        if (f == Float.MIN_VALUE) {
            Log.w("BatteryChartView", "invalid motion event for onClick() callback");
        } else {
            onTrapezoidClicked(view, getTrapezoidIndex(f));
        }
    }

    @Override // android.view.View
    public final void onHoverChanged(boolean z) {
        super.onHoverChanged(z);
        if (z) {
            return;
        }
        this.mHoveredIndex = -2;
        invalidate();
    }

    @Override // android.view.View
    public final boolean onHoverEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 7 || action == 9) {
            int trapezoidIndex = getTrapezoidIndex(motionEvent.getX());
            if (this.mHoveredIndex != trapezoidIndex) {
                this.mHoveredIndex = trapezoidIndex;
                invalidate();
                if (isTrapezoidIndexValid(this.mViewModel, this.mHoveredIndex)) {
                    sendAccessibilityEvent(this.mHoveredIndex, 128);
                }
            }
            return true;
        }
        if (action != 10) {
            return super.onTouchEvent(motionEvent);
        }
        int i = this.mHoveredIndex;
        if (i != -2) {
            if (isTrapezoidIndexValid(this.mViewModel, i)) {
                sendAccessibilityEvent(this.mHoveredIndex, 256);
            }
            this.mHoveredIndex = -2;
            invalidate();
        }
        return true;
    }

    @Override // android.widget.ImageView, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        Paint paint = this.mTextPaint;
        if (paint == null) {
            this.mIndent.set(0, 0, 0, 0);
            return;
        }
        paint.setTextAlign(Paint.Align.LEFT);
        int i3 = 0;
        while (true) {
            String[] strArr = this.mPercentages;
            if (i3 >= strArr.length) {
                break;
            }
            Paint paint2 = this.mTextPaint;
            String str = strArr[i3];
            paint2.getTextBounds(str, 0, str.length(), this.mPercentageBounds[i3]);
            i3++;
        }
        this.mIndent.top = this.mPercentageBounds[0].height() + this.mTransomViewHeight;
        int width = this.mPercentageBounds[0].width() + this.mTextPadding;
        if (isRTL$1()) {
            this.mIndent.left = width;
        } else {
            this.mIndent.right = width;
        }
        if (this.mViewModel != null) {
            int i4 = 0;
            for (int i5 = 0; i5 < this.mViewModel.mLevels.size(); i5++) {
                String text = this.mViewModel.getText(i5);
                this.mTextPaint.getTextBounds(text, 0, text.length(), (Rect) ((ArrayList) this.mAxisLabelsBounds).get(i5));
                i4 = Math.max(i4, -((Rect) ((ArrayList) this.mAxisLabelsBounds).get(i5)).top);
            }
            this.mIndent.bottom = Math.round(this.mTextPadding * 2.0f) + i4;
        }
        Log.d("BatteryChartView", "setIndent:" + this.mPercentageBounds[0]);
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 1) {
            this.mTouchUpEventX = motionEvent.getX();
        } else if (action == 3) {
            this.mTouchUpEventX = Float.MIN_VALUE;
        }
        return super.onTouchEvent(motionEvent);
    }

    public final void onTrapezoidClicked(View view, int i) {
        if (isValidToDraw(this.mViewModel, i)) {
            OnSelectListener onSelectListener = this.mOnSelectListener;
            if (onSelectListener != null) {
                if (i == this.mViewModel.mSelectedIndex) {
                    i = -1;
                }
                onSelectListener.onSelect(i);
            }
            view.performHapticFeedback(6);
        }
    }

    public final boolean sendAccessibilityEvent(int i, int i2) {
        ViewParent parent = getParent();
        if (parent == null || !AccessibilityManager.getInstance(((ImageView) this).mContext).isEnabled()) {
            return false;
        }
        AccessibilityEvent accessibilityEvent = new AccessibilityEvent(i2);
        accessibilityEvent.setSource(this, i);
        accessibilityEvent.setEnabled(true);
        accessibilityEvent.setClassName(getAccessibilityClassName());
        accessibilityEvent.setPackageName(getContext().getPackageName());
        return parent.requestSendAccessibilityEvent(this, accessibilityEvent);
    }

    public void setCompanionTextView(TextView textView) {
        if (textView != null) {
            textView.draw(new Canvas());
            TextPaint paint = textView.getPaint();
            this.mTextPaint = paint;
            this.mDefaultTextColor = paint.getColor();
        } else {
            this.mTextPaint = null;
        }
        requestLayout();
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.mOnSelectListener = onSelectListener;
    }

    public void setViewModel(BatteryChartViewModel batteryChartViewModel) {
        if (batteryChartViewModel == null) {
            this.mViewModel = null;
            invalidate();
            return;
        }
        Log.d("BatteryChartView", String.format("setViewModel(): size: %d, selectedIndex: %d, getHighlightSlotIndex: %d", Integer.valueOf(batteryChartViewModel.mLevels.size()), Integer.valueOf(batteryChartViewModel.mSelectedIndex), Integer.valueOf(batteryChartViewModel.mHighlightSlotIndex)));
        this.mViewModel = batteryChartViewModel;
        ((ArrayList) this.mAxisLabelsBounds).clear();
        boolean z = false;
        for (int i = 0; i < this.mViewModel.mLevels.size(); i++) {
            ((ArrayList) this.mAxisLabelsBounds).add(new Rect());
        }
        this.mTrapezoidSlots = new TrapezoidSlot[batteryChartViewModel.mLevels.size() - 1];
        int i2 = 0;
        while (true) {
            TrapezoidSlot[] trapezoidSlotArr = this.mTrapezoidSlots;
            if (i2 >= trapezoidSlotArr.length) {
                break;
            }
            trapezoidSlotArr[i2] = new TrapezoidSlot();
            i2++;
        }
        int i3 = 0;
        while (true) {
            if (i3 >= batteryChartViewModel.mLevels.size() - 1) {
                break;
            }
            if (((Integer) batteryChartViewModel.mLevels.get(i3)).intValue() != -1) {
                if (((Integer) batteryChartViewModel.mLevels.get(i3 + 1)).intValue() != -1) {
                    z = true;
                    break;
                }
            }
            i3++;
        }
        setClickable(z);
        requestLayout();
    }

    public BatteryChartView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mPercentages = getPercentages();
        this.mIndent = new Rect();
        this.mPercentageBounds = new Rect[]{new Rect(), new Rect(), new Rect()};
        this.mAxisLabelsBounds = new ArrayList();
        this.mLabelDrawnIndexes = new ArraySet();
        this.mLayoutDirection = getContext().getResources().getConfiguration().getLayoutDirection();
        this.mHoveredIndex = -2;
        this.mTouchUpEventX = Float.MIN_VALUE;
        setBackgroundColor(0);
        int colorAttrDefaultColor = Utils.getColorAttrDefaultColor(context, android.R.attr.colorAccent);
        this.mTrapezoidSolidColor = colorAttrDefaultColor;
        this.mTrapezoidColor = Utils.getDisabled(context, colorAttrDefaultColor);
        this.mTrapezoidHoverColor = Utils.getColorAttrDefaultColor(context, android.R.^attr-private.materialColorSurface);
        Resources resources = getContext().getResources();
        this.mDividerWidth = resources.getDimensionPixelSize(R.dimen.chartview_divider_width);
        this.mDividerHeight = resources.getDimensionPixelSize(R.dimen.chartview_divider_height);
        Paint paint = new Paint();
        this.mDividerPaint = paint;
        paint.setAntiAlias(true);
        this.mDividerPaint.setColor(DIVIDER_COLOR);
        this.mDividerPaint.setStyle(Paint.Style.STROKE);
        this.mDividerPaint.setStrokeWidth(this.mDividerWidth);
        Log.i("BatteryChartView", "mDividerWidth:" + this.mDividerWidth);
        Log.i("BatteryChartView", "mDividerHeight:" + this.mDividerHeight);
        this.mTrapezoidHOffset = resources.getDimension(R.dimen.chartview_trapezoid_margin_start);
        this.mTrapezoidVOffset = resources.getDimension(R.dimen.chartview_trapezoid_margin_bottom);
        Paint paint2 = new Paint();
        this.mTrapezoidPaint = paint2;
        paint2.setAntiAlias(true);
        this.mTrapezoidPaint.setColor(this.mTrapezoidSolidColor);
        this.mTrapezoidPaint.setStyle(Paint.Style.FILL);
        this.mTrapezoidPaint.setPathEffect(new CornerPathEffect(resources.getDimensionPixelSize(R.dimen.chartview_trapezoid_radius)));
        this.mTextPadding = resources.getDimensionPixelSize(R.dimen.chartview_text_padding);
        this.mTransomViewHeight = resources.getDimensionPixelSize(R.dimen.chartview_transom_layout_height);
        setOnClickListener(this);
        setClickable(false);
        requestLayout();
    }
}
