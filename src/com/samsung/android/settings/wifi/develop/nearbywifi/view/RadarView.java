package com.samsung.android.settings.wifi.develop.nearbywifi.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.samsung.android.settings.wifi.develop.nearbywifi.model.RadarUnit;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class RadarView extends View {
    public GradientDrawable mCircle;
    public List mRadarUnits;
    public Paint mTextPaint;
    public Paint mTextPaint2;
    public float mTextSizeInPx;

    public RadarView(Context context) {
        super(context);
        init();
    }

    public final float getPixelsFromDp(float f) {
        return TypedValue.applyDimension(1, f, getResources().getDisplayMetrics());
    }

    public final void init() {
        GradientDrawable gradientDrawable = new GradientDrawable();
        this.mCircle = gradientDrawable;
        gradientDrawable.setShape(1);
        this.mCircle.setBounds(0, 0, (int) getPixelsFromDp(8.0f), (int) getPixelsFromDp(8.0f));
        this.mTextSizeInPx = getPixelsFromDp(12.0f);
        Paint paint = new Paint();
        this.mTextPaint = paint;
        paint.setColor(-7829368);
        this.mTextPaint.setTextSize(this.mTextSizeInPx);
        Paint paint2 = this.mTextPaint;
        Paint.Align align = Paint.Align.CENTER;
        paint2.setTextAlign(align);
        Paint paint3 = new Paint();
        this.mTextPaint2 = paint3;
        paint3.setColor(-12303292);
        this.mTextPaint2.setTextSize(this.mTextSizeInPx);
        this.mTextPaint2.setTextAlign(align);
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        int i;
        double d;
        int i2;
        super.onDraw(canvas);
        if (this.mRadarUnits == null) {
            return;
        }
        int width = getWidth();
        int height = getHeight() - ((int) getPixelsFromDp(20.0f));
        int i3 = width / 2;
        if (width > height * 2) {
            d = height / 5.0d;
            i = 0;
        } else {
            double d2 = width / 2.0d;
            i = (int) ((height - d2) / 2.0d);
            d = d2 / 5.0d;
        }
        for (RadarUnit radarUnit : this.mRadarUnits) {
            double cos = Math.cos(radarUnit.radian);
            double d3 = radarUnit.radius;
            float width2 =
                    (float) ((((cos * d3) * d) + i3) - (this.mCircle.getBounds().width() / 2));
            float sin =
                    (float)
                            ((((5.0d - (Math.sin(radarUnit.radian) * d3)) * d) + i)
                                    - this.mCircle.getBounds().height());
            this.mCircle.setColor(
                    Color.rgb(
                            (int) (((139.0d * d3) / 5.0d) + 116.0d),
                            (int) (((166.0d * d3) / 5.0d) + 89.0d),
                            (int) (((d3 * 3.0d) / 5.0d) + 252.0d)));
            this.mCircle.setStroke(1, Color.parseColor("#6149D2"));
            canvas.translate(width2, sin);
            this.mCircle.draw(canvas);
            canvas.translate(-width2, -sin);
        }
        String[] strArr = {"-51", "-63", "-75", "-87"};
        String[] strArr2 = {"Best", "Good", "Bad", "Worst"};
        int i4 = 0;
        while (true) {
            if (i4 >= 4) {
                break;
            }
            int i5 = i4 + 1;
            canvas.drawText(
                    strArr[i4],
                    (float) ((i5 * d) + i3),
                    (float) ((d * 5.0d) + i + this.mTextSizeInPx),
                    this.mTextPaint);
            i4 = i5;
        }
        int i6 = 0;
        for (i2 = 4; i6 < i2; i2 = 4) {
            int i7 = i6 + 1;
            double d4 = i7;
            canvas.drawText(
                    strArr2[3 - i6],
                    (float) ((Math.cos(1.5707963267948966d) * d4 * d) + i3),
                    (float) ((Math.sin(1.5707963267948966d) * d4 * d) + i + getPixelsFromDp(15.0f)),
                    this.mTextPaint2);
            i6 = i7;
        }
    }

    public RadarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public RadarView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    public RadarView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init();
    }
}
