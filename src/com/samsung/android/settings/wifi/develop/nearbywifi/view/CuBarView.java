package com.samsung.android.settings.wifi.develop.nearbywifi.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class CuBarView extends LinearLayout {
    public boolean isYaxis;
    public final TextView mChannel;
    public final TextView mCuBar;
    public final TextView mCuValue;
    public final TextView mFreq;
    public final Paint[] mPaintLines;

    public CuBarView(Context context) {
        super(context);
        this.isYaxis = false;
        Paint[] paintArr = {r1, r1};
        this.mPaintLines = paintArr;
        LayoutInflater.from(getContext())
                .inflate(R.layout.sec_wifi_development_nearbywifi_cu_bar_view, this);
        this.mCuValue = (TextView) findViewById(R.id.cu_value);
        this.mCuBar = (TextView) findViewById(R.id.cu_bar);
        this.mChannel = (TextView) findViewById(R.id.channel_value);
        this.mFreq = (TextView) findViewById(R.id.channel_freq);
        Paint paint = new Paint();
        paint.setColor(-3355444);
        paintArr[0].setStrokeWidth(1.0f);
        Paint paint2 = paintArr[0];
        Paint.Style style = Paint.Style.STROKE;
        paint2.setStyle(style);
        Paint paint3 = new Paint();
        paint3.setColor(-7829368);
        paintArr[1].setStrokeWidth(1.0f);
        paintArr[1].setStyle(style);
        setWillNotDraw(false);
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float height = getHeight();
        float width = getWidth();
        float height2 = (height - this.mChannel.getHeight()) - this.mFreq.getHeight();
        TypedValue.applyDimension(1, 140.0f, getResources().getDisplayMetrics());
        float applyDimension =
                TypedValue.applyDimension(1, 14.0f, getResources().getDisplayMetrics());
        if (this.isYaxis) {
            return;
        }
        for (int i = 0; i < 11; i++) {
            canvas.drawLine(
                    0.0f,
                    height2,
                    width,
                    height2,
                    this.mPaintLines[i % 5 == 0 ? (char) 1 : (char) 0]);
            height2 -= applyDimension;
        }
    }

    public final void setHeight(int i) {
        this.mCuBar.setHeight(
                (int)
                        ((i * 1.4f * getContext().getResources().getDisplayMetrics().density)
                                + 0.5f));
        this.mCuBar.setWidth(
                (int) TypedValue.applyDimension(1, 30.0f, getResources().getDisplayMetrics()));
    }
}
