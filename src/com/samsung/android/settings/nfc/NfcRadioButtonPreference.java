package com.samsung.android.settings.nfc;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.preference.CheckBoxPreference;
import androidx.preference.PreferenceViewHolder;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class NfcRadioButtonPreference extends CheckBoxPreference {
    public NfcRadioButtonPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, R.attr.checkBoxPreferenceStyle, 0);
        setLayoutResource(com.android.settings.R.layout.sec_nfc_radio_button_preference_row);
        setWidgetLayoutResource(com.android.settings.R.layout.preference_widget_radiobutton);
    }

    @Override // androidx.preference.CheckBoxPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        TextView textView = (TextView) preferenceViewHolder.findViewById(R.id.title);
        if (textView != null) {
            textView.setSingleLine(false);
        }
    }

    @Override // androidx.preference.TwoStatePreference, androidx.preference.Preference
    public final void onClick() {}
}
