package com.android.settings.accessibility;

import android.content.Context;
import android.provider.Settings;
import android.util.AttributeSet;

import androidx.core.content.res.TypedArrayUtils;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settings.widget.SeekBarPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BalanceSeekBarPreference extends SeekBarPreference {
    public final Context mContext;
    public BalanceSeekBar mSeekBar;

    public BalanceSeekBarPreference(Context context, AttributeSet attributeSet) {
        super(
                context,
                attributeSet,
                TypedArrayUtils.getAttr(
                        context, R.attr.preferenceStyle, android.R.attr.preferenceStyle));
        this.mContext = context;
        setLayoutResource(R.layout.preference_balance_slider);
    }

    @Override // com.android.settings.widget.SeekBarPreference,
              // com.android.settingslib.RestrictedPreference,
              // com.android.settingslib.widget.TwoTargetPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        this.mSeekBar =
                (BalanceSeekBar) preferenceViewHolder.findViewById(android.R.id.textClassifier);
        if (this.mSeekBar == null) {
            return;
        }
        float floatForUser =
                Settings.System.getFloatForUser(
                        this.mContext.getContentResolver(), "master_balance", 0.0f, -2);
        this.mSeekBar.setMax(200);
        this.mSeekBar.setProgress(((int) (floatForUser * 100.0f)) + 100);
        this.mSeekBar.setEnabled(isEnabled());
    }
}
