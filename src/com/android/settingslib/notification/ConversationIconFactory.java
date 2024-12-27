package com.android.settingslib.notification;

import android.content.Context;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.android.launcher3.icons.BaseIconFactory;
import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ConversationIconFactory extends BaseIconFactory {
    public static final float CIRCLE_RADIUS;
    public static final float RING_STROKE_WIDTH;
    public final int mImportantConversationColor;
    public final LauncherApps mLauncherApps;
    public final PackageManager mPackageManager;

    static {
        float sqrt = (float) Math.sqrt(288.0d);
        float f = sqrt / 2.0f;
        CIRCLE_RADIUS = ((10.0f - f) / 2.0f) + f;
        RING_STROKE_WIDTH = (20.0f - sqrt) / 2.0f;
    }

    public ConversationIconFactory(
            Context context, LauncherApps launcherApps, PackageManager packageManager, int i) {
        super(context, context.getResources().getConfiguration().densityDpi, i);
        this.mLauncherApps = launcherApps;
        this.mPackageManager = packageManager;
        this.mImportantConversationColor =
                context.getResources().getColor(R.color.important_conversation, null);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ConversationIconDrawable extends Drawable {
        public Drawable mBadgeIcon;
        public Drawable mBaseIcon;
        public int mIconSize;
        public Paint mPaddingPaint;
        public Paint mRingPaint;
        public boolean mShowRing;

        @Override // android.graphics.drawable.Drawable
        public final void draw(Canvas canvas) {
            float width = getBounds().width() / 56.0f;
            int i = (int) (52.0f * width);
            int i2 = (int) (40.0f * width);
            int i3 = (int) (46.0f * width);
            float f = (int) (ConversationIconFactory.RING_STROKE_WIDTH * width);
            this.mPaddingPaint.setStrokeWidth(f);
            float f2 = (int) (ConversationIconFactory.CIRCLE_RADIUS * width);
            Drawable drawable = this.mBaseIcon;
            if (drawable != null) {
                drawable.setBounds(0, 0, i, i);
                this.mBaseIcon.draw(canvas);
            } else {
                Log.w("ConversationIconFactory", "ConversationIconDrawable has null base icon");
            }
            if (this.mBadgeIcon != null) {
                float f3 = i3;
                canvas.drawCircle(f3, f3, f2, this.mPaddingPaint);
                this.mBadgeIcon.setBounds(i2, i2, i, i);
                this.mBadgeIcon.draw(canvas);
            } else {
                Log.w("ConversationIconFactory", "ConversationIconDrawable has null badge icon");
            }
            if (this.mShowRing) {
                this.mRingPaint.setStrokeWidth(f);
                float f4 = i3;
                canvas.drawCircle(f4, f4, f2, this.mRingPaint);
            }
        }

        @Override // android.graphics.drawable.Drawable
        public final int getIntrinsicHeight() {
            return this.mIconSize;
        }

        @Override // android.graphics.drawable.Drawable
        public final int getIntrinsicWidth() {
            return this.mIconSize;
        }

        @Override // android.graphics.drawable.Drawable
        public final int getOpacity() {
            return 0;
        }

        @Override // android.graphics.drawable.Drawable
        public final void setAlpha(int i) {}

        @Override // android.graphics.drawable.Drawable
        public final void setColorFilter(ColorFilter colorFilter) {}
    }
}
