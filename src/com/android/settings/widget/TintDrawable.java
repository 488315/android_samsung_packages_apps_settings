package com.android.settings.widget;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.DrawableWrapper;
import android.util.AttributeSet;

import com.android.settings.R$styleable;

import org.xmlpull.v1.XmlPullParser;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class TintDrawable extends DrawableWrapper {
    public int[] mThemeAttrs;
    public ColorStateList mTint;

    public TintDrawable() {
        super(null);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
        int[] iArr = this.mThemeAttrs;
        if (iArr != null) {
            TypedArray resolveAttributes = theme.resolveAttributes(iArr, R$styleable.TintDrawable);
            if (resolveAttributes.hasValue(1)) {
                setDrawable(resolveAttributes.getDrawable(1));
            }
            if (resolveAttributes.hasValue(0)) {
                this.mTint = resolveAttributes.getColorStateList(0);
            }
            resolveAttributes.recycle();
        }
        if (getDrawable() == null || this.mTint == null) {
            return;
        }
        getDrawable().mutate().setTintList(this.mTint);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final boolean canApplyTheme() {
        int[] iArr = this.mThemeAttrs;
        return (iArr != null && iArr.length > 0) || super.canApplyTheme();
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final void inflate(
            Resources resources,
            XmlPullParser xmlPullParser,
            AttributeSet attributeSet,
            Resources.Theme theme) {
        TypedArray obtainAttributes =
                DrawableWrapper.obtainAttributes(
                        resources, theme, attributeSet, R$styleable.TintDrawable);
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        this.mThemeAttrs = obtainAttributes.extractThemeAttrs();
        if (obtainAttributes.hasValue(1)) {
            setDrawable(obtainAttributes.getDrawable(1));
        }
        if (obtainAttributes.hasValue(0)) {
            this.mTint = obtainAttributes.getColorStateList(0);
        }
        obtainAttributes.recycle();
        if (getDrawable() == null || this.mTint == null) {
            return;
        }
        getDrawable().mutate().setTintList(this.mTint);
    }
}
