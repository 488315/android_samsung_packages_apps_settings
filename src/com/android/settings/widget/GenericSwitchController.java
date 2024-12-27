package com.android.settings.widget;

import androidx.preference.Preference;

import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.PrimarySwitchPreference;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedSwitchPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class GenericSwitchController extends SwitchWidgetController
        implements Preference.OnPreferenceChangeListener {
    public SettingsMetricsFeatureProvider mMetricsFeatureProvider;
    public Preference mPreference;

    public GenericSwitchController(PrimarySwitchPreference primarySwitchPreference) {
        this.mPreference = primarySwitchPreference;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
    }

    @Override // com.android.settings.widget.SwitchWidgetController
    public final boolean isChecked() {
        Preference preference = this.mPreference;
        if (preference instanceof PrimarySwitchPreference) {
            return ((PrimarySwitchPreference) preference).isChecked();
        }
        if (preference instanceof RestrictedSwitchPreference) {
            return ((RestrictedSwitchPreference) preference).mChecked;
        }
        return false;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        SwitchWidgetController.OnSwitchChangeListener onSwitchChangeListener = this.mListener;
        if (onSwitchChangeListener == null) {
            return false;
        }
        boolean onSwitchToggled =
                onSwitchChangeListener.onSwitchToggled(((Boolean) obj).booleanValue());
        if (onSwitchToggled) {
            this.mMetricsFeatureProvider.logClickedPreference(
                    preference, preference.getExtras().getInt("category"));
        }
        return onSwitchToggled;
    }

    @Override // com.android.settings.widget.SwitchWidgetController
    public final void setChecked(boolean z) {
        Preference preference = this.mPreference;
        if (preference instanceof PrimarySwitchPreference) {
            ((PrimarySwitchPreference) preference).setChecked(z);
        } else if (preference instanceof RestrictedSwitchPreference) {
            ((RestrictedSwitchPreference) preference).setChecked(z);
        }
    }

    @Override // com.android.settings.widget.SwitchWidgetController
    public final void setDisabledByAdmin(RestrictedLockUtils.EnforcedAdmin enforcedAdmin) {
        Preference preference = this.mPreference;
        if (preference instanceof PrimarySwitchPreference) {
            ((PrimarySwitchPreference) preference).setDisabledByAdmin(enforcedAdmin);
        } else if (preference instanceof RestrictedSwitchPreference) {
            ((RestrictedSwitchPreference) preference).setDisabledByAdmin(enforcedAdmin);
        }
    }

    @Override // com.android.settings.widget.SwitchWidgetController
    public final void setEnabled(boolean z) {
        Preference preference = this.mPreference;
        if (preference instanceof PrimarySwitchPreference) {
            ((PrimarySwitchPreference) preference).setSwitchEnabled(z);
        } else if (preference instanceof RestrictedSwitchPreference) {
            ((RestrictedSwitchPreference) preference).setEnabled(z);
        }
    }

    @Override // com.android.settings.widget.SwitchWidgetController
    public final void startListening() {
        this.mPreference.setOnPreferenceChangeListener(this);
    }

    @Override // com.android.settings.widget.SwitchWidgetController
    public final void stopListening() {
        this.mPreference.setOnPreferenceChangeListener(null);
    }
}
