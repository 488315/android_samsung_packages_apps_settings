package com.android.launcher3.icons;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public final class BitmapInfo {
    public static final Bitmap LOW_RES_ICON = Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8);
    public BitmapInfo badgeInfo;
    public final int color;
    public int flags;
    public final Bitmap icon;
    public Bitmap mMono;
    public Bitmap mWhiteShadowLayer;

    public BitmapInfo(Bitmap bitmap, int i) {
        this.icon = bitmap;
        this.color = i;
    }

    public final Object clone() {
        BitmapInfo bitmapInfo = new BitmapInfo(this.icon, this.color);
        bitmapInfo.mMono = this.mMono;
        bitmapInfo.mWhiteShadowLayer = this.mWhiteShadowLayer;
        bitmapInfo.flags = this.flags;
        bitmapInfo.badgeInfo = this.badgeInfo;
        return bitmapInfo;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final FastBitmapDrawable newIcon(Context context, int i) {
        FastBitmapDrawable fastBitmapDrawable;
        Drawable userBadgeDrawable;
        if (LOW_RES_ICON == this.icon) {
            fastBitmapDrawable = new PlaceHolderIconDrawable(this, context);
        } else if ((i & 1) == 0 || this.mMono == null) {
            fastBitmapDrawable = new FastBitmapDrawable(this.icon, this.color);
        } else {
            int i2 = ThemedIconDrawable.$r8$clinit;
            Resources resources = context.getResources();
            int[] iArr = {
                resources.getColor(R.color.themed_icon_background_color),
                resources.getColor(R.color.themed_icon_color)
            };
            fastBitmapDrawable =
                    new ThemedIconDrawable.ThemedConstantState(this, iArr[0], iArr[1])
                            .newDrawable();
        }
        GraphicsUtils$$ExternalSyntheticLambda0 graphicsUtils$$ExternalSyntheticLambda0 =
                GraphicsUtils.sOnNewBitmapRunnable;
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(new int[] {R.attr.disabledIconAlpha});
        float f = obtainStyledAttributes.getFloat(0, 1.0f);
        obtainStyledAttributes.recycle();
        fastBitmapDrawable.mDisabledAlpha = f;
        fastBitmapDrawable.mCreationFlags = i;
        if ((i & 2) == 0) {
            boolean z = (i & 1) != 0;
            boolean z2 = (i & 4) != 0;
            BitmapInfo bitmapInfo = this.badgeInfo;
            int i3 = z;
            if (bitmapInfo != null) {
                if (z2) {
                    i3 = (z ? 1 : 0) | 4;
                }
                userBadgeDrawable = bitmapInfo.newIcon(context, i3);
            } else {
                if (!z2) {
                    int i4 = this.flags;
                    if ((i4 & 2) != 0) {
                        userBadgeDrawable =
                                new UserBadgeDrawable(
                                        context,
                                        R.drawable.ic_instant_app_badge,
                                        R.color.badge_tint_instant,
                                        z);
                    } else if ((i4 & 1) != 0) {
                        userBadgeDrawable =
                                new UserBadgeDrawable(
                                        context,
                                        R.drawable.ic_work_app_badge,
                                        R.color.badge_tint_work,
                                        z);
                    } else if ((i4 & 4) != 0) {
                        userBadgeDrawable =
                                new UserBadgeDrawable(
                                        context,
                                        R.drawable.ic_clone_app_badge,
                                        R.color.badge_tint_clone,
                                        z);
                    } else if ((i4 & 8) != 0) {
                        userBadgeDrawable =
                                new UserBadgeDrawable(
                                        context,
                                        R.drawable.ic_private_profile_app_badge,
                                        R.color.badge_tint_private,
                                        z);
                    }
                }
                userBadgeDrawable = null;
            }
            if (userBadgeDrawable != null) {
                Drawable drawable = fastBitmapDrawable.mBadge;
                if (drawable != null) {
                    drawable.setCallback(null);
                }
                fastBitmapDrawable.mBadge = userBadgeDrawable;
                userBadgeDrawable.setCallback(fastBitmapDrawable);
                Rect bounds = fastBitmapDrawable.getBounds();
                Drawable drawable2 = fastBitmapDrawable.mBadge;
                if (drawable2 != null) {
                    int width = bounds.width();
                    float f2 = BaseIconFactory.LEGACY_ICON_SCALE;
                    int i5 = (int) (width * 0.444f);
                    int i6 = bounds.right;
                    int i7 = bounds.bottom;
                    drawable2.setBounds(i6 - i5, i7 - i5, i6, i7);
                }
                fastBitmapDrawable.updateFilter();
            }
        }
        return fastBitmapDrawable;
    }
}
