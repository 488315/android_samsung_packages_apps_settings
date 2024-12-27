package com.android.settings.development;

import android.net.ConnectivitySettingsManager;
import android.util.Log;

import androidx.preference.ListPreference;
import androidx.preference.Preference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class IngressRateLimitPreferenceController extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "ingress_rate_limit";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        ConnectivitySettingsManager.setIngressRateLimitInBytesPerSecond(this.mContext, -1L);
        ((ListPreference) this.mPreference).setValue(String.valueOf(-1));
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        try {
            ConnectivitySettingsManager.setIngressRateLimitInBytesPerSecond(
                    this.mContext, Long.parseLong(obj.toString()));
            return true;
        } catch (IllegalArgumentException e) {
            Log.e("IngressRateLimitPreferenceController", "invalid rate limit", e);
            return false;
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        String valueOf =
                String.valueOf(
                        ConnectivitySettingsManager.getIngressRateLimitInBytesPerSecond(
                                this.mContext));
        ListPreference listPreference = (ListPreference) preference;
        for (CharSequence charSequence : listPreference.mEntryValues) {
            if (valueOf.contentEquals(charSequence)) {
                listPreference.setValue(valueOf);
                return;
            }
        }
    }
}
