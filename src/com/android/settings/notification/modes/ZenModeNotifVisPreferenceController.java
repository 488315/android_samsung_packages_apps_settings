package com.android.settings.notification.modes;

import android.R;
import android.content.Context;
import android.service.notification.ZenPolicy;

import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenModeNotifVisPreferenceController extends AbstractZenModePreferenceController
        implements Preference.OnPreferenceChangeListener {
    protected int mEffect;
    protected int[] mParentSuppressedEffects;

    public ZenModeNotifVisPreferenceController(
            Context context, String str, int i, int[] iArr, ZenModesBackend zenModesBackend) {
        super(context, str, zenModesBackend);
        this.mEffect = i;
        this.mParentSuppressedEffects = iArr;
    }

    @Override // com.android.settings.notification.modes.AbstractZenModePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        if (!super.isAvailable()) {
            return false;
        }
        if (this.mEffect == 1) {
            return this.mContext
                    .getResources()
                    .getBoolean(R.bool.config_knownNetworksEnabledForService);
        }
        return true;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        final boolean z = !((Boolean) obj).booleanValue();
        return savePolicy(
                new Function() { // from class:
                                 // com.android.settings.notification.modes.ZenModeNotifVisPreferenceController$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj2) {
                        ZenModeNotifVisPreferenceController zenModeNotifVisPreferenceController =
                                ZenModeNotifVisPreferenceController.this;
                        return ((ZenPolicy.Builder) obj2)
                                .showVisualEffect(zenModeNotifVisPreferenceController.mEffect, z);
                    }
                });
    }

    @Override // com.android.settings.notification.modes.AbstractZenModePreferenceController
    public final void updateState(Preference preference, ZenMode zenMode) {
        boolean z;
        boolean z2 = !zenMode.getPolicy().isVisualEffectAllowed(this.mEffect, false);
        int[] iArr = this.mParentSuppressedEffects;
        if (iArr != null) {
            z = false;
            for (int i : iArr) {
                if (!zenMode.getPolicy().isVisualEffectAllowed(i, true)) {
                    z = true;
                }
            }
        } else {
            z = false;
        }
        if (!z) {
            preference.setEnabled(true);
            ((TwoStatePreference) preference).setChecked(z2);
        } else {
            ((TwoStatePreference) preference).setChecked(true);
            onPreferenceChange(preference, Boolean.TRUE);
            preference.setEnabled(false);
        }
    }
}
