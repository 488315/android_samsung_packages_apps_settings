package com.android.settings.fuelgauge.batteryusage;

import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AnomalyAppItemPreference extends PowerGaugePreference {
    public CharSequence mAnomalyHintText;

    @Override // com.android.settings.fuelgauge.batteryusage.PowerGaugePreference,
              // com.android.settingslib.widget.AppPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        LinearLayout linearLayout =
                (LinearLayout) preferenceViewHolder.findViewById(R.id.warning_chip);
        if (TextUtils.isEmpty(this.mAnomalyHintText)) {
            linearLayout.setVisibility(8);
        } else {
            ((TextView) linearLayout.findViewById(R.id.warning_info))
                    .setText(this.mAnomalyHintText);
            linearLayout.setVisibility(0);
        }
    }
}
