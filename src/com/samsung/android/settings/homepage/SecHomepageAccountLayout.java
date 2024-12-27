package com.samsung.android.settings.homepage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.util.SeslRoundedCorner;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecHomepageAccountLayout extends FrameLayout {
    public View mContainerLayout;
    public View mIconBgView;
    public View mIconFrame;
    public final Paint mRectPaint;
    public View mTextFrame;

    public SecHomepageAccountLayout(Context context) {
        this(context, null);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        canvas.drawRect(
                0.0f, 0.0f, getWidth(), getHeight() - this.mTextFrame.getHeight(), this.mRectPaint);
        super.dispatchDraw(canvas);
        SeslRoundedCorner seslRoundedCorner = new SeslRoundedCorner(getContext());
        seslRoundedCorner.setRoundedCorners(3);
        seslRoundedCorner.setRoundedCornerColor(
                3, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        seslRoundedCorner.drawRoundedCorner(this.mTextFrame, canvas);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mIconFrame = findViewById(R.id.icon_frame);
        this.mIconBgView = findViewById(R.id.icon_bg);
        this.mContainerLayout = findViewById(R.id.container_layout);
        this.mTextFrame = findViewById(R.id.text_frame);
    }

    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int dimensionPixelSize =
                this.mIconBgView.getMeasuredHeight() < this.mTextFrame.getMeasuredHeight()
                        ? 0
                        : getResources()
                                .getDimensionPixelSize(
                                        R.dimen
                                                .sec_top_level_account_preference_icon_frame_padding_bottom_with_profile_picture);
        if (this.mIconFrame.getPaddingBottom() != dimensionPixelSize) {
            View view = this.mIconFrame;
            view.setPadding(
                    view.getPaddingStart(),
                    this.mIconFrame.getPaddingTop(),
                    this.mIconFrame.getPaddingEnd(),
                    dimensionPixelSize);
            super.onMeasure(i, i2);
        }
    }

    public SecHomepageAccountLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SecHomepageAccountLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Paint paint = new Paint();
        this.mRectPaint = paint;
        paint.setColor(context.getColor(R.color.sec_round_and_bgcolor));
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }
}
