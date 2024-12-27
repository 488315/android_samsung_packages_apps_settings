package com.android.settings.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class TickButtonPreference extends Preference {
    public ImageView mCheckIcon;
    public boolean mIsSelected;

    public TickButtonPreference(Context context) {
        super(context);
        this.mIsSelected = false;
        setWidgetLayoutResource(R.layout.preference_check_icon);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        this.mCheckIcon = (ImageView) preferenceViewHolder.findViewById(R.id.check_icon);
        setSelected(this.mIsSelected);
    }

    public final void setSelected(boolean z) {
        ImageView imageView = this.mCheckIcon;
        if (imageView != null) {
            imageView.setVisibility(z ? 0 : 4);
        }
        this.mIsSelected = z;
    }

    public TickButtonPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsSelected = false;
        setWidgetLayoutResource(R.layout.preference_check_icon);
    }
}
