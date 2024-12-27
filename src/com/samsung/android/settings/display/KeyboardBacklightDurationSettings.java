package com.samsung.android.settings.display;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.SecPreferenceCategory;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.settings.widget.SecRadioButtonPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class KeyboardBacklightDurationSettings extends SettingsPreferenceFragment
        implements SecRadioButtonPreference.OnClickListener {
    public Context mContext;
    public String[] mDurationTitle;
    public String[] mDurationValues;
    public SecPreferenceCategory mPreferenceCategory;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 46;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.sec_keyboard_backlight_duration_settings);
        this.mContext = getContext();
        this.mPreferenceCategory =
                (SecPreferenceCategory) findPreference("keyboard_backlight_duration_list");
        this.mDurationValues =
                this.mContext.getResources().getStringArray(R.array.key_backlight_values);
        this.mDurationTitle =
                this.mContext.getResources().getStringArray(R.array.key_backlight_entries);
        int i =
                Settings.System.getInt(
                        this.mContext.getContentResolver(), "key_backlight_timeout", 3000);
        for (int i2 = 0; i2 < this.mDurationValues.length; i2++) {
            SecRadioButtonPreference secRadioButtonPreference =
                    new SecRadioButtonPreference(this.mPreferenceCategory.getContext());
            secRadioButtonPreference.setKey(this.mDurationValues[i2]);
            secRadioButtonPreference.setTitle(this.mDurationTitle[i2]);
            secRadioButtonPreference.setChecked(Integer.parseInt(this.mDurationValues[i2]) == i);
            secRadioButtonPreference.mListener = this;
            this.mPreferenceCategory.addPreference(secRadioButtonPreference);
            LayoutPreference layoutPreference =
                    new LayoutPreference(this.mContext, R.layout.sec_button_preference_divider);
            if (i2 != this.mDurationValues.length - 1) {
                this.mPreferenceCategory.addPreference(layoutPreference);
            } else {
                secRadioButtonPreference.seslSetRoundedBg(12);
            }
        }
    }

    @Override // com.samsung.android.settings.widget.SecRadioButtonPreference.OnClickListener
    public final void onRadioButtonClicked(SecRadioButtonPreference secRadioButtonPreference) {
        Settings.System.putInt(
                this.mContext.getContentResolver(),
                "key_backlight_timeout",
                Integer.parseInt(secRadioButtonPreference.getKey()));
        String key = secRadioButtonPreference.getKey();
        for (int i = 0; i < this.mPreferenceCategory.getPreferenceCount(); i++) {
            Preference preference = this.mPreferenceCategory.getPreference(i);
            if (preference instanceof SecRadioButtonPreference) {
                ((SecRadioButtonPreference) preference)
                        .setChecked(TextUtils.equals(preference.getKey(), key));
                return;
            }
        }
    }
}
