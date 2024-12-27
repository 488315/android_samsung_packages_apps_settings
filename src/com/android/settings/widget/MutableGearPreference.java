package com.android.settings.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settingslib.utils.ColorUtil;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class MutableGearPreference extends GearPreference {
    public final int mDisabledAlphaValue;
    public ImageView mGear;

    public MutableGearPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDisabledAlphaValue = (int) (ColorUtil.getDisabledAlpha(context) * 255.0f);
    }

    @Override // com.android.settings.widget.GearPreference,
              // com.android.settingslib.RestrictedPreference,
              // com.android.settingslib.widget.TwoTargetPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        ImageView imageView = (ImageView) preferenceViewHolder.findViewById(R.id.settings_button);
        this.mGear = imageView;
        boolean z = this.mGearState;
        if (imageView != null) {
            imageView.setEnabled(z);
            this.mGear.setImageAlpha(z ? 255 : this.mDisabledAlphaValue);
        }
        this.mGearState = z;
    }
}
