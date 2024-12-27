package com.android.settings.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settingslib.RestrictedPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class GearPreference extends RestrictedPreference implements View.OnClickListener {
    public boolean mGearState;
    public OnGearClickListener mOnGearClickListener;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnGearClickListener {
        void onGearClick(GearPreference gearPreference);
    }

    public GearPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mGearState = true;
    }

    @Override // com.android.settingslib.widget.TwoTargetPreference
    public int getSecondTargetResId() {
        return R.layout.preference_widget_gear;
    }

    @Override // com.android.settingslib.RestrictedPreference,
              // com.android.settingslib.widget.TwoTargetPreference, androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View findViewById = preferenceViewHolder.findViewById(R.id.settings_button);
        if (this.mOnGearClickListener != null) {
            findViewById.setVisibility(0);
            findViewById.setOnClickListener(this);
        } else {
            findViewById.setVisibility(8);
            findViewById.setOnClickListener(null);
        }
        findViewById.setEnabled(this.mGearState);
    }

    public void onClick(View view) {
        OnGearClickListener onGearClickListener;
        if (view.getId() != R.id.settings_button
                || (onGearClickListener = this.mOnGearClickListener) == null) {
            return;
        }
        onGearClickListener.onGearClick(this);
    }

    @Override // com.android.settingslib.widget.TwoTargetPreference
    public boolean shouldHideSecondTarget() {
        return this.mOnGearClickListener == null;
    }

    public GearPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mGearState = true;
    }
}
