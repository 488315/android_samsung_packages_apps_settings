package com.samsung.android.settings.security;

import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

import com.samsung.android.settings.widget.SecRadioButtonPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class PowerOffLockOptionFragment extends SettingsPreferenceFragment {
    public SecRadioButtonPreference mAlwaysUnlock;
    public SecRadioButtonPreference mDontRequireUnlock;
    public SecRadioButtonPreference mOnlyRequireLockscreen;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 4602;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.sec_power_off_lock_type);
        this.mAlwaysUnlock = (SecRadioButtonPreference) findPreference("always_require_unlock");
        this.mOnlyRequireLockscreen =
                (SecRadioButtonPreference) findPreference("only_require_lockscreen");
        this.mDontRequireUnlock = (SecRadioButtonPreference) findPreference("dont_require_unlock");
        int i = Settings.System.getInt(getContentResolver(), "power_off_lock_option", 1);
        if (i == 0) {
            this.mDontRequireUnlock.setChecked(true);
        } else if (i == 1) {
            this.mAlwaysUnlock.setChecked(true);
        } else {
            if (i != 2) {
                return;
            }
            this.mOnlyRequireLockscreen.setChecked(true);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        boolean z;
        int i = 2;
        Log.d("PowerOffLockOptionFragment", "onPreferenceTreeClick : " + preference.getKey());
        if (!(preference instanceof SecRadioButtonPreference)) {
            return super.onPreferenceTreeClick(preference);
        }
        SecRadioButtonPreference secRadioButtonPreference = (SecRadioButtonPreference) preference;
        SecRadioButtonPreference secRadioButtonPreference2 = null;
        for (int i2 = 0; i2 < getPreferenceScreen().getPreferenceCount(); i2++) {
            if (getPreferenceScreen().getPreference(i2) instanceof SecRadioButtonPreference) {
                secRadioButtonPreference2 =
                        (SecRadioButtonPreference) getPreferenceScreen().getPreference(i2);
            }
            if (secRadioButtonPreference2 != null) {
                if (secRadioButtonPreference2.getKey().equals(secRadioButtonPreference.getKey())) {
                    secRadioButtonPreference.setChecked(true);
                } else {
                    secRadioButtonPreference2.setChecked(false);
                }
            }
        }
        String key = secRadioButtonPreference.getKey();
        key.getClass();
        switch (key.hashCode()) {
            case -1921120820:
                if (key.equals("dont_require_unlock")) {
                    z = false;
                    break;
                }
                z = -1;
                break;
            case -1526760476:
                if (key.equals("only_require_lockscreen")) {
                    z = true;
                    break;
                }
                z = -1;
                break;
            case 622791566:
                if (key.equals("always_require_unlock")) {
                    z = 2;
                    break;
                }
                z = -1;
                break;
            default:
                z = -1;
                break;
        }
        switch (z) {
            case false:
                i = 0;
                break;
            case true:
                break;
            case true:
                i = 1;
                break;
            default:
                i = -1;
                break;
        }
        Settings.System.putInt(getContentResolver(), "power_off_lock_option", i);
        return true;
    }
}
