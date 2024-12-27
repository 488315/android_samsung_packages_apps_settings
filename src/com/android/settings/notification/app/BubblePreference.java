package com.android.settings.notification.app;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settingslib.RestrictedPreferenceHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BubblePreference extends Preference implements RadioGroup.OnCheckedChangeListener {
    public final RestrictedPreferenceHelper mHelper;
    public int mSelectedPreference;
    public boolean mSelectedVisible;

    public BubblePreference(Context context) {
        this(context, null);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        boolean z = this.mHelper.mDisabledByAdmin;
        View findViewById = preferenceViewHolder.findViewById(R.id.summary);
        if (z) {
            this.mHelper.onBindViewHolder(preferenceViewHolder);
            findViewById.setVisibility(0);
        } else {
            findViewById.setVisibility(8);
        }
        preferenceViewHolder.itemView.setClickable(false);
        RadioButton radioButton =
                (RadioButton)
                        preferenceViewHolder.findViewById(com.android.settings.R.id.bubble_all);
        radioButton.setChecked(this.mSelectedPreference == 1);
        radioButton.setTag(1);
        radioButton.setVisibility(z ? 8 : 0);
        RadioButton radioButton2 =
                (RadioButton)
                        preferenceViewHolder.findViewById(
                                com.android.settings.R.id.bubble_selected);
        radioButton2.setChecked(this.mSelectedPreference == 2);
        radioButton2.setTag(2);
        radioButton2.setVisibility((!this.mSelectedVisible || z) ? 8 : 0);
        RadioButton radioButton3 =
                (RadioButton)
                        preferenceViewHolder.findViewById(com.android.settings.R.id.bubble_none);
        radioButton3.setChecked(this.mSelectedPreference == 0);
        radioButton3.setTag(0);
        radioButton3.setVisibility(z ? 8 : 0);
        ((RadioGroup) preferenceViewHolder.findViewById(com.android.settings.R.id.radio_group))
                .setOnCheckedChangeListener(this);
    }

    @Override // android.widget.RadioGroup.OnCheckedChangeListener
    public final void onCheckedChanged(RadioGroup radioGroup, int i) {
        View findViewById = radioGroup.findViewById(i);
        if (findViewById == null || findViewById.getTag() == null) {
            return;
        }
        Integer num = (Integer) findViewById.getTag();
        num.intValue();
        callChangeListener(num);
    }

    public BubblePreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BubblePreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public BubblePreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        RestrictedPreferenceHelper restrictedPreferenceHelper =
                new RestrictedPreferenceHelper(context, this, attributeSet);
        this.mHelper = restrictedPreferenceHelper;
        restrictedPreferenceHelper.mDisabledSummary = true;
        setLayoutResource(com.android.settings.R.layout.bubble_preference);
    }
}
