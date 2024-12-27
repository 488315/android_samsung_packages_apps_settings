package com.android.settings.sim.smartForwarding;

import android.os.Bundle;

import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.android.settings.R;
import com.android.settingslib.core.instrumentation.Instrumentable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class MDNHandlerHeaderFragment extends PreferenceFragmentCompat
        implements Preference.OnPreferenceChangeListener, Instrumentable {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1571;
    }

    @Override // androidx.preference.PreferenceFragmentCompat
    public final void onCreatePreferences(Bundle bundle, String str) {
        setPreferencesFromResource(R.xml.smart_forwarding_mdn_handler_header, str);
        EditTextPreference editTextPreference =
                (EditTextPreference) findPreference("slot0_phone_number");
        editTextPreference.mOnBindEditTextListener = this;
        editTextPreference.setOnPreferenceChangeListener(this);
        String phoneNumber = SmartForwardingUtils.getPhoneNumber(getContext(), 0);
        editTextPreference.setSummary(phoneNumber);
        editTextPreference.setText(phoneNumber);
        EditTextPreference editTextPreference2 =
                (EditTextPreference) findPreference("slot1_phone_number");
        editTextPreference2.setOnPreferenceChangeListener(this);
        editTextPreference2.mOnBindEditTextListener = this;
        String phoneNumber2 = SmartForwardingUtils.getPhoneNumber(getContext(), 1);
        editTextPreference2.setSummary(phoneNumber2);
        editTextPreference2.setText(phoneNumber2);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        preference.setSummary(obj.toString());
        return true;
    }
}
