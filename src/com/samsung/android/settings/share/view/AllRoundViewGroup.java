package com.samsung.android.settings.share.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.appcompat.util.SeslRoundedCorner;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AllRoundViewGroup extends AnimateFrameLayout {
    public final SeslRoundedCorner mSeslAllRoundedCorner;

    public AllRoundViewGroup(Context context) {
        this(context, null);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        SeslRoundedCorner seslRoundedCorner = this.mSeslAllRoundedCorner;
        canvas.getClipBounds(seslRoundedCorner.mRoundedCornerBounds);
        seslRoundedCorner.drawRoundedCornerInternal$1(canvas);
    }

    public AllRoundViewGroup(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AllRoundViewGroup(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        int color = context.getColor(R.color.sec_share_round_background_color);
        SeslRoundedCorner seslRoundedCorner = new SeslRoundedCorner(context);
        this.mSeslAllRoundedCorner = seslRoundedCorner;
        seslRoundedCorner.setRoundedCorners(15);
        seslRoundedCorner.setRoundedCornerColor(15, color);
    }
}
