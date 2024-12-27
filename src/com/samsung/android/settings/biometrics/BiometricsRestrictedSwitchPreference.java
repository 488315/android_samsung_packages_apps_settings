package com.samsung.android.settings.biometrics;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.preference.PreferenceViewHolder;

import com.android.settings.biometrics.ParentalControlsUtils;
import com.android.settings.enterprise.ActionDisabledByAdminDialogHelper;
import com.android.settingslib.RestrictedLockUtils;

import com.samsung.android.settings.widget.SecRestrictedSwitchPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BiometricsRestrictedSwitchPreference extends SecRestrictedSwitchPreference {
    public BiometricsRestrictedSwitchPreference(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // com.samsung.android.settings.widget.SecRestrictedSwitchPreference,
              // androidx.preference.SecSwitchPreference,
              // androidx.preference.SwitchPreferenceCompat, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View findViewById = preferenceViewHolder.findViewById(R.id.switch_widget);
        if (findViewById != null) {
            findViewById.setVisibility(0);
        }
        TextView textView = (TextView) preferenceViewHolder.findViewById(R.id.summary);
        if (textView != null) {
            textView.setVisibility(8);
        }
    }

    @Override // com.samsung.android.settings.widget.SecRestrictedSwitchPreference,
              // androidx.preference.Preference
    public final void performClick() {
        if (!this.mHelper.mDisabledByAdmin) {
            super.performClick();
            return;
        }
        RestrictedLockUtils.EnforcedAdmin parentConsentRequired =
                ParentalControlsUtils.parentConsentRequired(getContext(), 270);
        if (parentConsentRequired == null) {
            super.performClick();
        } else {
            Log.d("BiometricsRestrictedSwitchPreference", "Request for Parental Consent!!");
            new ActionDisabledByAdminDialogHelper((Activity) getContext(), "disallow_biometric")
                    .prepareDialogBuilder("disallow_biometric", parentConsentRequired)
                    .show();
        }
    }

    public BiometricsRestrictedSwitchPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public BiometricsRestrictedSwitchPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BiometricsRestrictedSwitchPreference(Context context) {
        super(context, null);
    }
}
