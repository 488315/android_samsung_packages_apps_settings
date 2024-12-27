package com.google.android.material.tabs;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.content.res.AppCompatResources;

import com.google.android.material.R$styleable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class TabItem extends View {
    public final int customLayout;
    public final Drawable icon;
    public final CharSequence text;

    public TabItem(Context context) {
        this(context, null);
    }

    public TabItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        int resourceId;
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R$styleable.TabItem);
        this.text = obtainStyledAttributes.getText(2);
        this.icon =
                (!obtainStyledAttributes.hasValue(0)
                                || (resourceId = obtainStyledAttributes.getResourceId(0, 0)) == 0)
                        ? obtainStyledAttributes.getDrawable(0)
                        : AppCompatResources.getDrawable(context, resourceId);
        this.customLayout = obtainStyledAttributes.getResourceId(1, 0);
        obtainStyledAttributes.recycle();
    }
}
