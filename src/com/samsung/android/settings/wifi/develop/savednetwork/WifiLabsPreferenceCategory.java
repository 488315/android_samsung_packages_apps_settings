package com.samsung.android.settings.wifi.develop.savednetwork;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R$styleable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiLabsPreferenceCategory extends PreferenceCategory {
    public String mTitle;

    public WifiLabsPreferenceCategory(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes =
                    context.obtainStyledAttributes(attributeSet, R$styleable.SecCategory);
            seslSetSubheaderRoundedBackground(obtainStyledAttributes.getInt(0, 15));
            obtainStyledAttributes.recycle();
        }
    }

    @Override // androidx.preference.PreferenceCategory, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View view = preferenceViewHolder.itemView;
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            if (!TextUtils.isEmpty(this.mTitle)) {
                textView.setVisibility(0);
                return;
            }
            ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
            layoutParams.height = 0;
            textView.setLayoutParams(layoutParams);
            textView.setVisibility(8);
        }
    }

    public WifiLabsPreferenceCategory(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public WifiLabsPreferenceCategory(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public WifiLabsPreferenceCategory(Context context) {
        super(context);
    }
}
