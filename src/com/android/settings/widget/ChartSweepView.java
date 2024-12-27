package com.android.settings.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.DynamicLayout;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.android.settings.R;
import com.android.settings.R$styleable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ChartSweepView extends View {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AnonymousClass1 mClickListener;
    public final Rect mContentOffset;
    public int mFollowAxis;
    public int mLabelColor;
    public DynamicLayout mLabelLayout;
    public int mLabelMinSize;
    public float mLabelSize;
    public int mLabelTemplateRes;
    public long mLabelValue;
    public final Rect mMargins;
    public ChartSweepView[] mNeighbors;
    public int mSafeRegion;
    public final Drawable mSweep;
    public final Point mSweepOffset;
    public final Rect mSweepPadding;
    public int mTouchMode;
    public long mValue;

    public ChartSweepView(Context context) {
        this(context, null);
    }

    private Rect getParentContentRect() {
        View view = (View) getParent();
        return new Rect(
                view.getPaddingLeft(),
                view.getPaddingTop(),
                view.getWidth() - view.getPaddingRight(),
                view.getHeight() - view.getPaddingBottom());
    }

    private float getTargetInset() {
        float f;
        int i;
        if (this.mFollowAxis == 1) {
            int intrinsicHeight = this.mSweep.getIntrinsicHeight();
            f = (((intrinsicHeight - r3) - r1.bottom) / 2.0f) + this.mSweepPadding.top;
            i = this.mSweepOffset.y;
        } else {
            int intrinsicWidth = this.mSweep.getIntrinsicWidth();
            f = (((intrinsicWidth - r3) - r1.right) / 2.0f) + this.mSweepPadding.left;
            i = this.mSweepOffset.x;
        }
        return f + i;
    }

    private long getValidAfterDynamic() {
        return Long.MIN_VALUE;
    }

    private long getValidBeforeDynamic() {
        return Long.MAX_VALUE;
    }

    @Override // android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.mSweep.isStateful()) {
            this.mSweep.setState(getDrawableState());
        }
    }

    public ChartAxis getAxis() {
        return null;
    }

    public int getFollowAxis() {
        return this.mFollowAxis;
    }

    public long getLabelValue() {
        return this.mLabelValue;
    }

    public Rect getMargins() {
        return this.mMargins;
    }

    public float getPoint() {
        if (isEnabled()) {
            throw null;
        }
        return 0.0f;
    }

    public final float getTouchDistanceFromTarget(MotionEvent motionEvent) {
        return this.mFollowAxis == 0
                ? Math.abs(motionEvent.getX() - (getX() + getTargetInset()))
                : Math.abs(motionEvent.getY() - (getY() + getTargetInset()));
    }

    public long getValue() {
        return this.mValue;
    }

    public final void invalidateLabelTemplate() {
        if (this.mLabelTemplateRes != 0) {
            CharSequence text = getResources().getText(this.mLabelTemplateRes);
            TextPaint textPaint = new TextPaint(1);
            textPaint.density = getResources().getDisplayMetrics().density;
            textPaint.setCompatibilityScaling(
                    getResources().getCompatibilityInfo().applicationScale);
            textPaint.setColor(this.mLabelColor);
            this.mLabelLayout =
                    DynamicLayout.Builder.obtain(new SpannableStringBuilder(text), textPaint, 1024)
                            .setAlignment(Layout.Alignment.ALIGN_RIGHT)
                            .setIncludePad(false)
                            .setUseLineSpacingFromFallbacks(true)
                            .build();
            this.mLabelValue = this.mValue;
        } else {
            this.mLabelLayout = null;
        }
        invalidate();
        requestLayout();
    }

    @Override // android.view.View
    public final void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.mSweep;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        int i;
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        if (!isEnabled() || this.mLabelLayout == null) {
            i = 0;
        } else {
            int save = canvas.save();
            float f = this.mLabelSize - 1024.0f;
            Rect rect = this.mContentOffset;
            canvas.translate(rect.left + f, rect.top + 0.0f);
            this.mLabelLayout.draw(canvas);
            canvas.restoreToCount(save);
            i = ((int) this.mLabelSize) + this.mSafeRegion;
        }
        if (this.mFollowAxis == 1) {
            Drawable drawable = this.mSweep;
            int i2 = this.mSweepOffset.y;
            drawable.setBounds(
                    i, i2, width + this.mContentOffset.right, drawable.getIntrinsicHeight() + i2);
        } else {
            Drawable drawable2 = this.mSweep;
            int i3 = this.mSweepOffset.x;
            drawable2.setBounds(
                    i3, i, drawable2.getIntrinsicWidth() + i3, height + this.mContentOffset.bottom);
        }
        this.mSweep.draw(canvas);
    }

    @Override // android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.mFollowAxis == 1) {
            this.mLabelSize =
                    Layout.getDesiredWidth(
                            this.mLabelLayout.getText(), this.mLabelLayout.getPaint());
        }
        this.mLabelSize = Math.max(this.mLabelSize, this.mLabelMinSize);
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        if (!isEnabled() || this.mLabelLayout == null) {
            Point point = this.mSweepOffset;
            point.x = 0;
            point.y = 0;
            setMeasuredDimension(this.mSweep.getIntrinsicWidth(), this.mSweep.getIntrinsicHeight());
        } else {
            int intrinsicHeight = this.mSweep.getIntrinsicHeight();
            int height = this.mLabelLayout.getHeight();
            Point point2 = this.mSweepOffset;
            point2.x = 0;
            point2.y = 0;
            point2.y = (int) ((height / 2) - getTargetInset());
            setMeasuredDimension(
                    this.mSweep.getIntrinsicWidth(), Math.max(intrinsicHeight, height));
        }
        if (this.mFollowAxis == 1) {
            int intrinsicHeight2 = this.mSweep.getIntrinsicHeight();
            Rect rect = this.mSweepPadding;
            int i3 = rect.top;
            int i4 = (intrinsicHeight2 - i3) - rect.bottom;
            Rect rect2 = this.mMargins;
            rect2.top = -((i4 / 2) + i3);
            rect2.bottom = 0;
            rect2.left = -rect.left;
            rect2.right = rect.right;
        } else {
            int intrinsicWidth = this.mSweep.getIntrinsicWidth();
            Rect rect3 = this.mSweepPadding;
            int i5 = rect3.left;
            int i6 = (intrinsicWidth - i5) - rect3.right;
            Rect rect4 = this.mMargins;
            rect4.left = -((i6 / 2) + i5);
            rect4.right = 0;
            rect4.top = -rect3.top;
            rect4.bottom = rect3.bottom;
        }
        this.mContentOffset.set(0, 0, 0, 0);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        if (this.mFollowAxis == 0) {
            int i7 = measuredWidth * 3;
            setMeasuredDimension(i7, measuredHeight);
            Rect rect5 = this.mContentOffset;
            rect5.left = (i7 - measuredWidth) / 2;
            int i8 = this.mSweepPadding.bottom * 2;
            rect5.bottom -= i8;
            this.mMargins.bottom += i8;
        } else {
            int i9 = measuredHeight * 2;
            setMeasuredDimension(measuredWidth, i9);
            this.mContentOffset.offset(0, (i9 - measuredHeight) / 2);
            int i10 = this.mSweepPadding.right * 2;
            this.mContentOffset.right -= i10;
            this.mMargins.right += i10;
        }
        Point point3 = this.mSweepOffset;
        Rect rect6 = this.mContentOffset;
        point3.offset(rect6.left, rect6.top);
        Rect rect7 = this.mMargins;
        Point point4 = this.mSweepOffset;
        rect7.offset(-point4.x, -point4.y);
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0073, code lost:

       if (r13.getX() < r12.mLabelLayout.getWidth()) goto L32;
    */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0075, code lost:

       r5 = true;
    */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00a1, code lost:

       if (r13.getY() < r12.mLabelLayout.getHeight()) goto L32;
    */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onTouchEvent(android.view.MotionEvent r13) {
        /*
            Method dump skipped, instructions count: 249
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.widget.ChartSweepView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        setFocusable(z);
        requestLayout();
    }

    public void setFollowAxis(int i) {
        this.mFollowAxis = i;
    }

    public void setLabelColor(int i) {
        this.mLabelColor = i;
        invalidateLabelTemplate();
    }

    public void setLabelMinSize(int i) {
        this.mLabelMinSize = i;
        invalidateLabelTemplate();
    }

    public void setLabelTemplate(int i) {
        this.mLabelTemplateRes = i;
        invalidateLabelTemplate();
    }

    public void setNeighbors(ChartSweepView... chartSweepViewArr) {
        this.mNeighbors = chartSweepViewArr;
    }

    public void setSafeRegion(int i) {
        this.mSafeRegion = i;
    }

    public void setValue(long j) {
        this.mValue = j;
        this.mLabelValue = j;
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
        Drawable drawable = this.mSweep;
        if (drawable != null) {
            drawable.setVisible(i == 0, false);
        }
    }

    @Override // android.view.View
    public final boolean verifyDrawable(Drawable drawable) {
        return drawable == this.mSweep || super.verifyDrawable(drawable);
    }

    public ChartSweepView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ChartSweepView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Rect rect = new Rect();
        this.mSweepPadding = rect;
        this.mContentOffset = new Rect();
        this.mSweepOffset = new Point();
        this.mMargins = new Rect();
        Paint paint = new Paint();
        this.mTouchMode = 0;
        this.mNeighbors = new ChartSweepView[0];
        View.OnClickListener onClickListener =
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.widget.ChartSweepView.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        ChartSweepView chartSweepView = ChartSweepView.this;
                        int i2 = ChartSweepView.$r8$clinit;
                        chartSweepView.getClass();
                    }
                };
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R$styleable.ChartSweepView, i, 0);
        int color = obtainStyledAttributes.getColor(1, -16776961);
        Drawable drawable = obtainStyledAttributes.getDrawable(6);
        Drawable drawable2 = this.mSweep;
        if (drawable2 != null) {
            drawable2.setCallback(null);
            unscheduleDrawable(this.mSweep);
        }
        if (drawable != null) {
            drawable.setCallback(this);
            if (drawable.isStateful()) {
                drawable.setState(getDrawableState());
            }
            drawable.setVisible(getVisibility() == 0, false);
            this.mSweep = drawable;
            drawable.setTint(color);
            drawable.getPadding(rect);
        } else {
            this.mSweep = null;
        }
        invalidate();
        setFollowAxis(obtainStyledAttributes.getInt(0, -1));
        setNeighborMargin(obtainStyledAttributes.getDimensionPixelSize(4, 0));
        setSafeRegion(obtainStyledAttributes.getDimensionPixelSize(5, 0));
        setLabelMinSize(obtainStyledAttributes.getDimensionPixelSize(2, 0));
        setLabelTemplate(obtainStyledAttributes.getResourceId(3, 0));
        setLabelColor(color);
        setBackgroundResource(R.drawable.data_usage_sweep_background);
        paint.setColor(-65536);
        paint.setStrokeWidth(1.0f);
        paint.setStyle(Paint.Style.STROKE);
        obtainStyledAttributes.recycle();
        setClickable(true);
        setOnClickListener(onClickListener);
        setWillNotDraw(false);
    }

    @Override // android.view.View
    public final void addOnLayoutChangeListener(
            View.OnLayoutChangeListener onLayoutChangeListener) {}

    @Override // android.view.View
    public final void removeOnLayoutChangeListener(
            View.OnLayoutChangeListener onLayoutChangeListener) {}

    public void setDragInterval(long j) {}

    public void setNeighborMargin(float f) {}
}
