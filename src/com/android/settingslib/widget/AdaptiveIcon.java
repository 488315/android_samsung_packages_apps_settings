package com.android.settingslib.widget;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AdaptiveIcon extends LayerDrawable {
    public final AdaptiveConstantState mAdaptiveConstantState;
    int mBackgroundColor;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    class AdaptiveConstantState extends Drawable.ConstantState {
        public int mColor;
        public Context mContext;
        public Drawable mDrawable;

        @Override // android.graphics.drawable.Drawable.ConstantState
        public final int getChangingConfigurations() {
            return 0;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public final Drawable newDrawable() {
            AdaptiveIcon adaptiveIcon = new AdaptiveIcon(this.mContext, this.mDrawable);
            adaptiveIcon.setBackgroundColor(this.mColor);
            return adaptiveIcon;
        }
    }

    public AdaptiveIcon(Context context, Drawable drawable) {
        super(new Drawable[] {new AdaptiveIconShapeDrawable(context.getResources()), drawable});
        this.mBackgroundColor = -1;
        int dimensionPixelSize =
                context.getResources()
                        .getDimensionPixelSize(R.dimen.dashboard_tile_foreground_image_inset);
        setLayerInset(
                1, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
        AdaptiveConstantState adaptiveConstantState = new AdaptiveConstantState();
        adaptiveConstantState.mContext = context;
        adaptiveConstantState.mDrawable = drawable;
        this.mAdaptiveConstantState = adaptiveConstantState;
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public final Drawable.ConstantState getConstantState() {
        return this.mAdaptiveConstantState;
    }

    public final void setBackgroundColor(int i) {
        this.mBackgroundColor = i;
        getDrawable(0).setColorFilter(i, PorterDuff.Mode.SRC_ATOP);
        Preference$$ExternalSyntheticOutline0.m(
                new StringBuilder("Setting background color "),
                this.mBackgroundColor,
                "AdaptiveHomepageIcon");
        this.mAdaptiveConstantState.mColor = i;
    }
}
