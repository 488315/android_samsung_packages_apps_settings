package com.android.settings.notification.zen;

import android.content.Context;

import androidx.preference.Preference;
import androidx.preference.SecSwitchPreference;

import com.android.settingslib.core.lifecycle.Lifecycle;

import com.samsung.android.settings.Rune;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenModeVisEffectPreferenceController extends AbstractZenModePreferenceController
        implements Preference.OnPreferenceChangeListener {
    public final int mEffect;
    public final String mKey;
    public final int mMetricsCategory;
    public final int[] mParentSuppressedEffects;

    public ZenModeVisEffectPreferenceController(
            Context context, Lifecycle lifecycle, String str, int i, int i2, int[] iArr) {
        super(context, str, lifecycle);
        this.mKey = str;
        this.mEffect = i;
        this.mMetricsCategory = i2;
        this.mParentSuppressedEffects = iArr;
    }

    @Override // com.android.settings.notification.zen.AbstractZenModePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return this.mKey;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        if (this.mEffect == 8) {
            return Rune.supportLedIndicator();
        }
        return true;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        ZenModeBackend zenModeBackend = this.mBackend;
        int i = zenModeBackend.mPolicy.suppressedVisualEffects;
        int i2 = this.mEffect;
        if (((i & i2) != 0) != booleanValue) {
            this.mMetricsFeatureProvider.action(this.mContext, this.mMetricsCategory, booleanValue);
            zenModeBackend.saveVisualEffectsPolicy(i2, booleanValue);
        }
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        boolean z;
        super.updateState(preference);
        ZenModeBackend zenModeBackend = this.mBackend;
        boolean z2 = (zenModeBackend.mPolicy.suppressedVisualEffects & this.mEffect) != 0;
        int[] iArr = this.mParentSuppressedEffects;
        if (iArr != null) {
            z = false;
            for (int i : iArr) {
                z |= (i & zenModeBackend.mPolicy.suppressedVisualEffects) != 0;
            }
        } else {
            z = false;
        }
        if (!z) {
            SecSwitchPreference secSwitchPreference = (SecSwitchPreference) preference;
            secSwitchPreference.setEnabled(true);
            secSwitchPreference.setChecked(z2);
        } else {
            SecSwitchPreference secSwitchPreference2 = (SecSwitchPreference) preference;
            secSwitchPreference2.setChecked(z);
            onPreferenceChange(preference, Boolean.valueOf(z));
            secSwitchPreference2.setEnabled(false);
        }
    }
}
