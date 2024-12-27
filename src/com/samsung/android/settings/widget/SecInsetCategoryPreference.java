package com.samsung.android.settings.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settings.R$styleable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SecInsetCategoryPreference extends PreferenceCategory {
    public int mHeight;
    public boolean mRemovable;

    public SecInsetCategoryPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mHeight = 0;
        this.mRemovable = true;
        this.mHeight =
                (int) context.getResources().getDimension(R.dimen.sec_widget_inset_category_height);
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes =
                    context.obtainStyledAttributes(attributeSet, R$styleable.SecInsetCategory);
            this.mHeight = obtainStyledAttributes.getDimensionPixelSize(0, this.mHeight);
            this.mRemovable = obtainStyledAttributes.getBoolean(1, this.mRemovable);
            obtainStyledAttributes.recycle();
            TypedArray obtainStyledAttributes2 =
                    context.obtainStyledAttributes(attributeSet, R$styleable.SecCategory);
            seslSetSubheaderRoundedBackground(obtainStyledAttributes2.getInt(0, 15));
            obtainStyledAttributes2.recycle();
        }
    }

    @Override // androidx.preference.PreferenceCategory, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.height = this.mHeight;
        preferenceViewHolder.itemView.setLayoutParams(layoutParams);
        preferenceViewHolder.itemView.setImportantForAccessibility(2);
    }

    public final void setHeight(int i) {
        if (i >= 0) {
            this.mHeight = i;
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.preference.Preference, java.lang.Comparable
    public final int compareTo(Preference preference) {
        double order = getOrder();
        double order2 = preference.getOrder();
        return order != order2 ? order > order2 ? 1 : -1 : super.compareTo(preference);
    }

    public SecInsetCategoryPreference(Context context) {
        this(context, null);
    }
}
