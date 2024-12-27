package com.samsung.android.settings.theftprotection;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

import com.samsung.android.settings.widget.SecUnclickablePreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class MandatoryBiometricDescriptionPreference extends SecUnclickablePreference {
    public MandatoryBiometricDescriptionPreference(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // com.samsung.android.settings.widget.SecUnclickablePreference,
              // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        TextView textView = (TextView) preferenceViewHolder.findViewById(R.id.title);
        if (textView != null) {
            TheftProtectionUtils.setLinkableText(textView);
            textView.setBackground(null);
            textView.setClickable(false);
        }
    }

    public MandatoryBiometricDescriptionPreference(
            Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public MandatoryBiometricDescriptionPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MandatoryBiometricDescriptionPreference(Context context) {
        super(context);
    }
}
